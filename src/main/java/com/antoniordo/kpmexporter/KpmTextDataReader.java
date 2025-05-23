package com.antoniordo.kpmexporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import com.antoniordo.kpmexporter.data.KpmApplication;
import com.antoniordo.kpmexporter.data.KpmData;
import com.antoniordo.kpmexporter.data.KpmNote;
import com.antoniordo.kpmexporter.data.KpmOtherAccount;
import com.antoniordo.kpmexporter.data.KpmWebSite;

public class KpmTextDataReader {

    private static final String APPLICATIONS_SECTION_KEYWORD = "Applications";
    private static final String OTHER_ACCOUNTS_SECTION_KEYWORD = "Other Accounts";
    private static final String NOTES_SECTION_KEYWORD = "Notes";

    private static final String WEBSITE_NAME_FIELD = "Website name";
    private static final String WEBSITE_FIELD_URL_FIELD = "Website URL";
    private static final String LOGIN_FIELD = "Login";
    private static final String PASSWORD_FIELD = "Password";
    private static final String COMMENT_FIELD = "Comment";
    private static final String APPLICATION_FIELD = "Application";
    private static final String ACCOUNT_NAME_FIELD = "Account name";
    private static final String NAME_FIELD = "Name";
    private static final String TEXT_FIELD = "Text";

    private static final String END_OF_RECORD_MARKER = "---";

    /**
     * Read Kaspersky exported text file to create a list of KpmData objects.
     * @param filePath the path to the file to read
     * @return a list of KpmData objects
     */
    public static List<KpmData> readFromFile(String filePath) {
        List<String> allLines = readAllLines(filePath);
        var webSitesLines = allLines.subList(1,
                                             allLines.indexOf(APPLICATIONS_SECTION_KEYWORD));
        var applicationsLines = allLines.subList(allLines.indexOf(APPLICATIONS_SECTION_KEYWORD) + 1,
                                                 allLines.indexOf(OTHER_ACCOUNTS_SECTION_KEYWORD));
        var otherAccountsLines = allLines.subList(allLines.indexOf(OTHER_ACCOUNTS_SECTION_KEYWORD) + 1,
                                                  allLines.indexOf(NOTES_SECTION_KEYWORD));
        var notesLines = allLines.subList(allLines.indexOf(NOTES_SECTION_KEYWORD) + 1, allLines.size());
        List<KpmData> result = new ArrayList<>();
        result.addAll(processWebSitesLines(webSitesLines));
        result.addAll(processApplications(applicationsLines));
        result.addAll(processOtherAccounts(otherAccountsLines));
        result.addAll(processNotes(notesLines));
        return result;
    }

    private static List<String> readAllLines(String filePath) {
        List<String> allLines;
        try {
            allLines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allLines;
    }

    private static Collection<KpmData> processWebSitesLines(List<String> webSitesLines) {
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

    private static Collection<KpmData> processApplications(List<String> applicationsLines) {
        List<KpmData> applications = new ArrayList<>();
        var recordBuilder = KpmApplication.newBuilder();
        var iterator = applicationsLines.iterator();
        while (iterator.hasNext()) {
            var line = iterator.next();
            if (line.isBlank()) {
                continue;
            }
            var splited = line.split(":", 2);
            String key = splited[0];
            String value = String.valueOf(splited[1]).strip();
            switch (key) {
                case APPLICATION_FIELD -> recordBuilder.application(value);
                case LOGIN_FIELD -> recordBuilder.login(value);
                case PASSWORD_FIELD -> recordBuilder.password(value);
                case COMMENT_FIELD -> {
                    recordBuilder.comment(flattenLinesUtilEndOfRecordMarker(iterator, value));
                    applications.add(recordBuilder.build());
                    recordBuilder = KpmApplication.newBuilder();
                }
            }
        }
        return applications;
    }

    private static Collection<KpmData> processOtherAccounts(List<String> otherAccountsLines) {
        List<KpmData> others = new ArrayList<>();
        var recordBuilder = KpmOtherAccount.newBuilder();
        var iterator = otherAccountsLines.iterator();
        while (iterator.hasNext()) {
            var line = iterator.next();
            if (line.isBlank()) {
                continue;
            }
            var splited = line.split(":", 2);
            String key = splited[0];
            String value = String.valueOf(splited[1]).strip();
            switch (key) {
                case ACCOUNT_NAME_FIELD -> recordBuilder.accountName(value);
                case LOGIN_FIELD -> recordBuilder.login(value);
                case PASSWORD_FIELD -> recordBuilder.password(value);
                case COMMENT_FIELD -> {
                    recordBuilder.comment(flattenLinesUtilEndOfRecordMarker(iterator, value));
                    others.add(recordBuilder.build());
                    recordBuilder = KpmOtherAccount.newBuilder();
                }
            }
        }
        return others;
    }

    private static Collection<? extends KpmData> processNotes(List<String> notesLines) {
        List<KpmData> notes = new ArrayList<>();
        var recordBuilder = KpmNote.newBuilder();
        var iterator = notesLines.iterator();
        while (iterator.hasNext()) {
            var line = iterator.next();
            if (line.isBlank()) {
                continue;
            }
            var splited = line.split(":", 2);
            String key = splited[0];
            String value = String.valueOf(splited[1]).strip();
            switch (key) {
                case NAME_FIELD -> recordBuilder.name(value);
                case TEXT_FIELD -> {
                    recordBuilder.text(flattenLinesUtilEndOfRecordMarker(iterator, value));
                    notes.add(recordBuilder.build());
                    recordBuilder = KpmNote.newBuilder();
                }
            }
        }
        return notes;
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
