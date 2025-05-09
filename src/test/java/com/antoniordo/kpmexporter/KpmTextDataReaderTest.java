package com.antoniordo.kpmexporter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.antoniordo.kpmexporter.data.KpmData;

public class KpmTextDataReaderTest {

    @Test
    void readFromFile() {
        var actual = KpmTextDataReader.readFromFile(Fixtures.KPM_RECORDS_FILE);
        List<KpmData> expected = Fixtures.KPM_RECORDS;
        assertThat(actual).isEqualTo(expected);
    }

}
