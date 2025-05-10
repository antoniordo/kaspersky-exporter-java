package com.antoniordo.kpmexporter.data;

public record KpmApplication(String application,
                             String login,
                             String password,
                             String comment) implements KpmData {

    public static KpmApplicationBuilder newBuilder() {
        return new KpmApplicationBuilder();
    }

    public static class KpmApplicationBuilder {
        private String application;
        private String login;
        private String password;
        private String comment;

        public KpmApplicationBuilder application(String application) {
            this.application = application;
            return this;
        }

        public KpmApplicationBuilder login(String login) {
            this.login = login;
            return this;
        }

        public KpmApplicationBuilder password(String password) {
            this.password = password;
            return this;
        }

        public KpmApplicationBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public KpmApplication build() {
            return new KpmApplication(application, login, password, comment);
        }
    }
                    
}
