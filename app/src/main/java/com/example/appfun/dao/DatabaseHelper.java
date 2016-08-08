package com.example.appfun.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by zx on 2016/1/19.
 */
public class DatabaseHelper {
    private static SQLiteDatabase mDatabase;

    public static final String PACKAGE_NAME = "com.example.appfun";
    public static final String DATABASE_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;

    public static SQLiteDatabase openDatabase(Context context,int dbPath,String dbName) {
        try {
            String databaseFilename = DATABASE_PATH + "/" + dbName;
            File dir = new File(DATABASE_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (!(new File(databaseFilename)).exists()) {
                InputStream inputStream = context.getResources().openRawResource(dbPath);
                FileOutputStream fileOutputStream = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
                inputStream.close();
            }
            mDatabase = SQLiteDatabase.openOrCreateDatabase(
                    databaseFilename, null);
            return mDatabase;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

