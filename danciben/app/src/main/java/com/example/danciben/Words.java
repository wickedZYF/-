package com.example.danciben;

import android.provider.BaseColumns;

public class Words {
    public static class WordItem {
        public String id;
        public String word;
        public WordItem(String id, String word) {
            this.id = id;
            this.word = word;
        }
        @Override
        public String toString() {
            return word;
        }
    }


    public static class WordDescription {
        public String id;
        public String word;
        public String meaning;
        public String sample;
        public WordDescription(String id, String word,String meaning, String sample) {
            this.id = id;
            this.word = word;
            this.meaning = meaning;
            this.sample = sample;
        }
    }

    public Words() {
    }

        public static abstract class Word implements BaseColumns {
            public static final String TABLE_NAME = "words";
            public static final String COLUMN_NAME_WORD = "word";
            public static final String COLUMN_NAME_MEANING = "meaning";
            public static final String COLUMN_NAME_SAMPLE = "sample";
         }

}



