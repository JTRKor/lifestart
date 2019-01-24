package kr.co.orng15.lifestartapplication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Login_Member (id INTEGER PRIMARY KEY AUTOINCREMENT, checked Integer, member_id TEXT, member_pw TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertLoginMember(Integer checked, String member_id, String member_pw) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "Insert into Login_Member values (null, 1, '"+member_id+"', '"+member_pw+"');";

        db.execSQL(query);
    }

    public void deleteLoginmember() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "delete from Login_Member;";

        db.execSQL(query);
    }

    public String member_id() {
        String member_id = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from Login_Member;";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            member_id = cursor.getString(2);
        }
        cursor.close();
        return member_id;
    }

    public String member_pw() {
        String member_pw = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from Login_Member;";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            member_pw = cursor.getString(3);
        }
        cursor.close();
        return member_pw;
    }

    public Integer checked() {
        Integer checked = -1;
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from Login_Member;";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            checked = cursor.getInt(1);
        }
        cursor.close();
        return checked;
    }

    public Integer checkLoginmember() {
        Integer count = -1;
        SQLiteDatabase db = getReadableDatabase();
        String query = "select count(*) from Login_Member;";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public void updateMemberInfo(String member_pw) {
        SQLiteDatabase db = getReadableDatabase();

    }
}
