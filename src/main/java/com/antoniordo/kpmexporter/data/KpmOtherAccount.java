package com.antoniordo.kpmexporter.data;

public record KpmOtherAccount(String accountName,
                              String login,
                              String password,
                              String comment) implements KpmData {

}
