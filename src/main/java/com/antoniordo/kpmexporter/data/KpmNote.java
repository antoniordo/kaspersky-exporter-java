package com.antoniordo.kpmexporter.data;

public record KpmNote(String name, String text) implements KpmData {

    public static KpmNoteBuilder newBuilder() {
        return new KpmNoteBuilder();
    }

    public static class KpmNoteBuilder {
        private String name;
        private String text;

        public KpmNoteBuilder name(String name) {
            this.name = name;
            return this;
        }

        public KpmNoteBuilder text(String text) {
            this.text = text;
            return this;
        }

        public KpmNote build() {
            return new KpmNote(name, text);
        }
    }

}
