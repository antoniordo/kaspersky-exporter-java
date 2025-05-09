package com.antoniordo.kpmexporter.data;

public record KpmWebSite(String websiteName,
                         String websiteURL,
                         String login,
                         String password,
                         String comment) implements KpmData {
    
        public static KpmRecordBuilder newBuilder() {
            return new KpmRecordBuilder();
        }

        public static class KpmRecordBuilder {
            private String websiteName;
            private String websiteURL;
            private String login;
            private String password;
            private String comment;

            public KpmRecordBuilder websiteName(String websiteName) {
                this.websiteName = websiteName;
                return this;
            }

            public KpmRecordBuilder websiteURL(String websiteURL) {
                this.websiteURL = websiteURL;
                return this;
            }

            public KpmRecordBuilder login(String login) {
                this.login = login;
                return this;
            }

            public KpmRecordBuilder password(String password) {
                this.password = password;
                return this;
            }

            public KpmRecordBuilder comment(String comment) {
                this.comment = comment;
                return this;
            }

            public KpmWebSite build() {
                return new KpmWebSite(websiteName, websiteURL, login, password, comment);
            }
        }

}