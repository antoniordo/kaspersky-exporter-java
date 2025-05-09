package com.antoniordo.kpmexporter.data;

public sealed interface KpmData permits KpmWebSite, KpmOtherAccount, KpmNote {

}
