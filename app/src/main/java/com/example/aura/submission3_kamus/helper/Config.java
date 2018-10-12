package com.example.aura.submission3_kamus.helper;

public class Config {

    public static final String DATABASE_NAME = "kamus_data.db";
    public static final int DATABASE_VERSION = 1;

    public static String DATABASE_ENGLISH = "KAMUS_BAHASA_ENGLISH";
    public static String DATABASE_INDONESIA = "KAMUS_BAHASA_INDONESIA";
    public static String DATABASE_FIELD_ID = "ID";
    public static String DATABASE_FIELD_WORD = "WORD";
    public static String DATABASE_FIELD_TRANSLATION = "TRANSLATION";

    public static final String TABLE_EN_IND = "CREATE TABLE " + Config.DATABASE_ENGLISH + "("
            + Config.DATABASE_FIELD_ID + " integer primary key autoincrement, "
            + Config.DATABASE_FIELD_WORD + " string, "
            + Config.DATABASE_FIELD_TRANSLATION + " text not null);";

    public static final String TABLE_IND_EN = "CREATE TABLE " + Config.DATABASE_INDONESIA + "("
            + Config.DATABASE_FIELD_ID + " integer primary key autoincrement, "
            + Config.DATABASE_FIELD_WORD + " string, "
            + Config.DATABASE_FIELD_TRANSLATION + " text not null);";

    public static final String BUNDLE_WORD = "word";
    public static final String BUNDLE_TRANSLATE = "translate";
}
