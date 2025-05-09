package com.antoniordo.passwordmanager;

import java.time.LocalDate;

import com.antoniordo.kpmexporter.NordPassCvsExporter;
import com.antoniordo.kpmexporter.KpmTextDataReader;

public class InteractiveKpmToCsvExport {

    public static void main(String[] args) {
        
        var baseDrirectory = "/mnt/c/Users/anolivei";
        var kpmTextFilePath = "%s/Downloads/kpm-export.txt".formatted(baseDrirectory);
        var KpmRecords = KpmTextDataReader.readFromFile(kpmTextFilePath);
        var filePrefix = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        NordPassCvsExporter.exportToCvs(
            KpmRecords,
            "%s/Downloads/%s-kpm-export.csv".formatted(baseDrirectory, filePrefix)
        );

    }

}
