package com.antoniordo.kpmexporter;

import java.util.Collection;

import com.antoniordo.kpmexporter.data.KpmData;
import com.antoniordo.kpmexporter.data.KpmWebSite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NordPassCvsExporter {

    public static final String CSV_HEADER = "name,url,username,password,note,cardholdername,cardnumber,cvc," +
                                             "expirydate,zipcode,folder,full_name,phone_number,email,address1," +
                                             "address2,city,country,state";

    /**
     * Export KpmData to a CSV file in the NordPass format.
     * @param kpmData the collection of KpmData to export
     * @param cvsFilePath the path to the CSV file to write the file will be created if it does not exist
     */
    public static void exportToCvs(Collection<KpmData> kpmData, String cvsFilePath) {
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
            case KpmWebSite kws -> String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",,,,,,,,,,,,,,",
                                                 kws.websiteName(),
                                                 kws.websiteURL(),
                                                 kws.login(),
                                                 kws.password(),
                                                 kws.comment());
            default -> throw new IllegalArgumentException("Unsupported KpmData type: " + record.getClass().getName());
        };
    }
    
}
