package com.example.aura.submission3_kamus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.aura.submission3_kamus.helper.Config;
import com.example.aura.submission3_kamus.model.KamusModel;

import java.util.ArrayList;

public class KamusDataHelper {
    private static String English = Config.DATABASE_ENGLISH;
    private static String Indonesia = Config.DATABASE_INDONESIA;

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    public KamusDataHelper(Context context) {
        this.context = context;
    }

    public KamusDataHelper open() throws SQLException {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {dbHelper.close();}

    public Cursor searchQueryByName(String query, boolean english){

        String DATABASE_TABLE = english ? English : Indonesia;
        return db.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + Config.DATABASE_FIELD_WORD + " LIKE '%" + query.trim() + "%'", null);
    }

    public ArrayList<KamusModel> getDataByName(String search, boolean english) {
        KamusModel kamusModel;

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = searchQueryByName(search, english);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Config.DATABASE_FIELD_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(Config.DATABASE_FIELD_WORD)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(Config.DATABASE_FIELD_TRANSLATION)));
                arrayList.add(kamusModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryAllData(boolean english) {
        String DATABASE_TABLE = english ? English : Indonesia;
        return db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY " + Config.DATABASE_FIELD_ID + " ASC", null);
    }

    public ArrayList<KamusModel> getDataALl(boolean english) {
        KamusModel kamusModel;

        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData(english);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Config.DATABASE_FIELD_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(Config.DATABASE_FIELD_WORD)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(Config.DATABASE_FIELD_TRANSLATION)));
                arrayList.add(kamusModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public long insert(KamusModel kamusDataModel, boolean english) {
        String DATABASE_TABLE = english ? English : Indonesia;
        ContentValues initialValues = new ContentValues();
        initialValues.put(Config.DATABASE_FIELD_WORD, kamusDataModel.getWord());
        initialValues.put(Config.DATABASE_FIELD_TRANSLATION, kamusDataModel.getTranslate());
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public void insertTransaction(ArrayList<KamusModel> kamusDataModels, boolean english) {
        String DATABASE_TABLE = english ? English : Indonesia;
        String sql = "INSERT INTO " + DATABASE_TABLE + " (" +
                Config.DATABASE_FIELD_WORD + ", " +
                Config.DATABASE_FIELD_TRANSLATION + ") VALUES (?, ?)";

        db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(sql);
        for (int i = 0; i < kamusDataModels.size(); i++) {
            stmt.bindString(1, kamusDataModels.get(i).getWord());
            stmt.bindString(2, kamusDataModels.get(i).getTranslate());
            stmt.execute();
            stmt.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(KamusModel kamusDataModel, boolean english) {
        String DATABASE_TABLE = english ? English : Indonesia;
        ContentValues args = new ContentValues();
        args.put(Config.DATABASE_FIELD_WORD, kamusDataModel.getWord());
        args.put(Config.DATABASE_FIELD_TRANSLATION, kamusDataModel.getTranslate());
        db.update(DATABASE_TABLE, args, Config.DATABASE_FIELD_ID+ "= '" + kamusDataModel.getId() + "'", null);
    }

    public void delete(int id, boolean english) {
        String DATABASE_TABLE = english ? English : Indonesia;
        db.delete(DATABASE_TABLE, Config.DATABASE_FIELD_ID + " = '" + id + "'", null);
    }
}
