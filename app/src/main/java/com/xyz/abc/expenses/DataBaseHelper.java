package com.xyz.abc.expenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.net.PortUnreachableException;

public class DataBaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME ="myDB.db";
    static String TABLE_NAME = "mytable";
    Context context;

    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "REASON";
    public static final String COL_4 = "AMOUNT";
    public static final String COL_5 = "PARENT";


    public String ParentStr;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT ,REASON TEXT,AMOUNT INTEGER,PARENT TEXT)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public boolean insertData(String Date , String Reason , String Amount,String Parent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Date);
        contentValues.put(COL_3,Reason);
        contentValues.put(COL_4,Amount);
        contentValues.put(COL_5,Parent);
        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if(result==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getParentData(String Parent){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("Select * from "+TABLE_NAME+" WHERE PARENT = ?" , new String[]{Parent});
            return res;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME,"ID=?",new String[]{id});
        return i;
    }
    public String[] ReasonAndAmount(String Id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME+" WHERE ID = ?" , new String[]{Id});
        res.moveToNext();

        return new String[]{res.getString(2),res.getString(3)};
    }
    public boolean updateData(String Id ,String Reason ,String Amount){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,Reason);
        contentValues.put(COL_4,Amount);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{Id});
        return true;

    }
    public Cursor getMonthData(String month){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME+" WHERE PARENT LIKE ?" , new String[]{'%'+month});
        return res;
    }





}
