package com.antoniordo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> allLines;
        try {
            allLines = Files.readAllLines(Paths.get(Paths.get("src", "test", "resources", "kpm-export.txt").toString()));
            System.out.println(allLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
