package com.antoniordo.kpmexporter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class KpmTextDataReaderTest {

    @Test
    void readFromFile() {
        var actual = KpmTextDataReader.readFromFile(Fixtures.KPM_RECORDS_FILE);
        assertThat(actual).isEqualTo(Fixtures.KPM_RECORDS);
    }

}
