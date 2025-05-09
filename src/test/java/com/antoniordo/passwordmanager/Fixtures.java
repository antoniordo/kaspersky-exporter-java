package com.antoniordo.passwordmanager;

import java.nio.file.Paths;
import java.util.List;

import com.antoniordo.kpmexporter.data.KpmData;
import com.antoniordo.kpmexporter.data.KpmWebSite;

public class Fixtures {

    private Fixtures() {}

    public static final String KPM_RECORDS_FILE = Paths.get("src", "test", "resources", "kpm-export.txt").toString();

    public static final List<KpmData> KPM_RECORDS = List.of(
            new KpmWebSite("Some App",
                    "someapp.com",
                    "someone",
                    "Cq6WRMCbX89suncN",
                    "The account for Some App"),
            new KpmWebSite("Big Tech Account",
                    "https://account.bigtech.com",
                    "main.user@bigtech.com",
                    "SuTnwVxtWU5WqC4Q", ""),
            new KpmWebSite("Big Tech Recover Account",
                    "https://account.bigtech.com",
                    "recoveraccount@bigtech.com",
                    "AS5JYj,d9gw.mkcdz!H_Y3WQ",
                    """
                    A multiline text:
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                    incididunt ut labore et dolore magna aliqua\
                    """)
    );

}
