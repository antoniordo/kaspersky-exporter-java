package com.antoniordo.kpmexporter;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class NordPassCvsExporterTest {

    @Test
    void exportToCvs(@TempDir Path tempDir) {
        
        var expected = NordPassCvsExporter.CSV_HEADER + "\n";
        expected += """
        "Some web site","somewebsite.com","someone","Cq6WRMCbX89suncN","The account for Some App",,,,,,,,,,,,,,
        "Big Tech Account","https://account.bigtech.com","main.user@bigtech.com","SuTnwVxtWU5WqC4Q","",,,,,,,,,,,,,,
        "Recover Account","https://account.bigtech.com","recoveraccount@bigtech.com","AS5JYj,d9gw.mkcdz!H_Y3WQ","A multiline text:
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
        incididunt ut labore et dolore magna aliqua",,,,,,,,,,,,,,
        "Bank App",,"josesilva","12321","Main bank application",,,,,,,,,,,,,,
        "Social Security",,"12345678","654321X","Another multiline comment
        Lorem ipsum dolor sit amet",,,,,,,,,,,,,,
        "My Other Account",,"91DD4A34F2D46D7A","a-+E]Ki!m.::3xU","uid:
        Luke Skywalker <luke.sky@starwars.com>",,,,,,,,,,,,,,
        """;

        NordPassCvsExporter.exportToCvs(Fixtures.KPM_RECORDS, tempDir.resolve("exported.csv").toString());
        String actual = readStringFromFile(tempDir.resolve("exported.csv"));

        assertThat(actual).isEqualTo(expected);

    }

    private String readStringFromFile(Path exportedCsvPath) {
        String result;
        try {
            result = Files.readString(exportedCsvPath);
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;
    }

}
