package com.antoniordo.kpmexporter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.antoniordo.kpmexporter.data.KpmData;

public class Main {

    public static void main(String[] args) {
        
        if (args.length != 2) {
            System.err.println("Usage: java -jar kpm-exporter.jar <kaspersky-passwords-text-file> <output-file>");
            System.exit(1);
        }
        String inputFile = args[0];
        String outputFile = args[1];
        if (!Files.exists(Paths.get(inputFile))) {
            System.err.println("Input file does not exist: " + inputFile);
            System.exit(1);
        }

        List<KpmData> kpmData = KpmTextDataReader.readFromFile(inputFile);
        NordPassCsvExporter.exportToCsv(kpmData, outputFile);
        System.out.println("Exported " + kpmData.size() + " items to " + outputFile);

    }

}
