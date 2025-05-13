package com.antoniordo.kpmexporter.data;

public record KpmOtherAccount(String accountName,
                              String login,
                              String password,
                              String comment) implements KpmData {

    public static KpmOtherAccountBuilder newBuilder() {
        return new KpmOtherAccountBuilder();
    }

    public static class KpmOtherAccountBuilder {

        private String accountName;
        private String login;
        private String password;
        private String comment;

        public KpmOtherAccountBuilder accountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public KpmOtherAccountBuilder login(String login) {
            this.login = login;
            return this;
        }

        public KpmOtherAccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        public KpmOtherAccountBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public KpmOtherAccount build() {
            return new KpmOtherAccount(accountName, login, password, comment);
        }

    }

}
