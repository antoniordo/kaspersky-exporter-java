package com.antoniordo.kpmexporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import com.antoniordo.kpmexporter.data.KpmData;
import com.antoniordo.kpmexporter.data.KpmWebSite;

public class KpmTextDataReader {

    private static final String WEBSITES_SECTION_KEYWORD = "Websites";
    private static final String APPLICATIONS_SECTION_KEYWORD = "Applications";

    private static final String WEBSITE_NAME_FIELD = "Website name";
    private static final String WEBSITE_FIELD_URL_FIELD = "Website URL";
    private static final String LOGIN_FIELD = "Login";
    private static final String PASSWORD_FIELD = "Password";
    private static final String COMMENT_FIELD = "Comment";

    private static final String END_OF_RECORD_MARKER = "---";

    /**
     * Read Kaspersky exported text file to create a list of KpmData objects.
     * @param filePath the path to the file to read
     * @return a list of KpmData objects
     */
    public static List<KpmData> readFromFile(String filePath) {
        List<KpmData> result = new ArrayList<>();
        List<String> allLines;
        try {
            allLines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var webSitesLines = allLines.subList(allLines.indexOf(WEBSITES_SECTION_KEYWORD) +1,
                                             allLines.indexOf(APPLICATIONS_SECTION_KEYWORD));
        result.addAll(processWebSitesLines(webSitesLines));
        return result;
    }

    private static List<KpmData> processWebSitesLines(List<String> webSitesLines) {
        List<KpmData> webSites = new ArrayList<>();
        KpmWebSite.KpmRecordBuilder recordBuilder = KpmWebSite.newBuilder();
        var iterator = webSitesLines.iterator();
        while (iterator.hasNext()) {
            var line = iterator.next();
            if (line.isBlank()) {
                continue;
            }
            var splited = line.split(":", 2);
            String key = splited[0];
            String value = String.valueOf(splited[1]).strip();
            switch (key) {
                case WEBSITE_NAME_FIELD -> recordBuilder.websiteName(value);
                case WEBSITE_FIELD_URL_FIELD -> recordBuilder.websiteURL(value);
                case LOGIN_FIELD -> recordBuilder.login(value);
                case PASSWORD_FIELD -> recordBuilder.password(value);
                case COMMENT_FIELD -> {
                    recordBuilder.comment(flattenLinesUtilEndOfRecordMarker(iterator, value));
                    webSites.add(recordBuilder.build());
                    recordBuilder = KpmWebSite.newBuilder();
                }
            }
        }
        return webSites;
    }

    private static String flattenLinesUtilEndOfRecordMarker(Iterator<String> iterator, String startText) {
        StringJoiner commentText = new StringJoiner("\n");
        commentText.add(startText);
        var commentLine = startText;
        while (iterator.hasNext()) {
            commentLine = iterator.next();
            if (commentLine.startsWith(END_OF_RECORD_MARKER)) {
                break;
            } else if (commentLine.isBlank()) {
                continue;
            } else {
                commentText.add(commentLine);
            }
        }
        return commentText.toString();
    }

}
