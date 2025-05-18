package com.antoniordo.kpmexporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import com.antoniordo.kpmexporter.data.KpmApplication;
import com.antoniordo.kpmexporter.data.KpmData;
import com.antoniordo.kpmexporter.data.KpmNote;
import com.antoniordo.kpmexporter.data.KpmOtherAccount;
import com.antoniordo.kpmexporter.data.KpmWebSite;

public class NordPassCsvExporter {

    private static final String NAME_HREADER = "name";
    private static final String URL_HEADER = "url";
    private static final String USERNAME_HEADER = "username";
    private static final String PASSWORD_HEADER = "password";
    private static final String NOTE_HEADER = "note";

    private static final String[] CSV_HEADERS = {
        NAME_HREADER, URL_HEADER, USERNAME_HEADER, PASSWORD_HEADER, NOTE_HEADER,
        "cardholdername", "cardnumber", "cvc", "expirydate", "zipcode", "folder", "full_name", "phone_number",
        "email", "address1", "address2", "city", "country", "state"
    };

    public static final String CSV_HEADER;

    static {
        CSV_HEADER = String.join(",", CSV_HEADERS);
    }

    /**
     * Export KpmData to a CSV file in the NordPass format.
     * @param kpmData the collection of KpmData to export
     * @param cvsFilePath the path to the CSV file to write the file will be created if it does not exist
     */
    public static void exportToCsv(Collection<KpmData> kpmData, String cvsFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cvsFilePath))) {
            writer.write(CSV_HEADER);
            writer.newLine();
            for (KpmData record : kpmData) {
                writer.write(kpmRecordToCsvLine(record));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file: " + e.getMessage(), e);
        }
    }

    private static String kpmRecordToCsvLine(KpmData record) {
        return switch (record) {
            case KpmWebSite kw -> formatCsvLine(Map.of(NAME_HREADER, kw.websiteName(),
                                                       URL_HEADER, kw.websiteURL(),
                                                       USERNAME_HEADER, kw.login(),
                                                       PASSWORD_HEADER, kw.password(),
                                                       NOTE_HEADER, kw.comment()));
            case KpmApplication ka -> formatCsvLine(Map.of(NAME_HREADER, ka.application(),
                                                           USERNAME_HEADER, ka.login(),
                                                           PASSWORD_HEADER, ka.password(),
                                                           NOTE_HEADER, ka.comment()));
            case KpmOtherAccount oa -> formatCsvLine(Map.of(NAME_HREADER, oa.accountName(),
                                                           USERNAME_HEADER, oa.login(),
                                                           PASSWORD_HEADER, oa.password(),
                                                           NOTE_HEADER, oa.comment()));
            case KpmNote kn -> formatCsvLine(Map.of(NAME_HREADER, kn.name(),
                                                    NOTE_HEADER, kn.text()));
            default -> throw new IllegalArgumentException("Unsupported KpmData type: " + record.getClass().getName());
        };
    }

    private static String formatCsvLine(Map<String, String> data) {
        var joiner = new StringJoiner(",");
        for (int i = 0; i < CSV_HEADERS.length; i++) {
            if (data.containsKey(CSV_HEADERS[i])) {
                joiner.add("\"" + data.get(CSV_HEADERS[i]) + "\"");
            } else {
                joiner.add("");
            }
        }
        return joiner.toString();
    }
    
}
