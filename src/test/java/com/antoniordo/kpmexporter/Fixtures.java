package com.antoniordo.kpmexporter;

import java.nio.file.Paths;
import java.util.List;

import com.antoniordo.kpmexporter.data.KpmApplication;
import com.antoniordo.kpmexporter.data.KpmData;
import com.antoniordo.kpmexporter.data.KpmOtherAccount;
import com.antoniordo.kpmexporter.data.KpmWebSite;

public class Fixtures {

    private Fixtures() {}

    public static final String KPM_RECORDS_FILE = Paths.get("src", "test", "resources", "kpm-export.txt").toString();

    public static final List<KpmData> KPM_RECORDS = List.of(
        new KpmWebSite("Some web site",
                "somewebsite.com",
                "someone",
                "Cq6WRMCbX89suncN",
                "The account for Some App"),
        new KpmWebSite("Big Tech Account",
                "https://account.bigtech.com",
                "main.user@bigtech.com",
                "SuTnwVxtWU5WqC4Q", ""),
        new KpmWebSite("Recover Account",
                "https://account.bigtech.com",
                "recoveraccount@bigtech.com",
                "AS5JYj,d9gw.mkcdz!H_Y3WQ",
                """
                A multiline text:
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                incididunt ut labore et dolore magna aliqua\
                """),
        new KpmApplication("Bank App",
                "josesilva",
                "12321",
                "Main bank application"),
        new KpmApplication("Social Security",
                "12345678",
                "654321X",
                """
                Another multiline comment
                Lorem ipsum dolor sit amet\
                """),
        new KpmOtherAccount("My Other Account",
                            "91DD4A34F2D46D7A",
                            "a-+E]Ki!m.::3xU",
                            """
                            uid:
                            Luke Skywalker <luke.sky@starwars.com>\
                            """)
    );

}
