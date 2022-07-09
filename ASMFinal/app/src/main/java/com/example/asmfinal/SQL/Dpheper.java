package com.example.asmfinal.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dpheper extends SQLiteOpenHelper {
    public  Dpheper(Context context){
        super(context,"Quanlichitieu.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ;

        sql = "CREATE TABLE LOAI_TC(MALOAI integer primary key autoincrement ,"+"TENLOAI text, TRANGTHAI text)";
        db.execSQL(sql);
        sql="INSERT INTO LOAI_TC(TENLOAI,TRANGTHAI)"+"VALUES('Lãi ngân hàng','Thu')";
        db.execSQL(sql);
        sql="INSERT INTO LOAI_TC(TENLOAI,TRANGTHAI)"+"VALUES('Lương','Thu')";
        db.execSQL(sql);
        sql="INSERT INTO LOAI_TC(TENLOAI,TRANGTHAI)"+"VALUES('Bán hàng','Thu')";
        db.execSQL(sql);
        sql="INSERT INTO LOAI_TC(TENLOAI,TRANGTHAI)"+"VALUES('Sinh hoạt hàng ngày','Chi')";
        db.execSQL(sql);
        sql="INSERT INTO LOAI_TC(TENLOAI,TRANGTHAI)"+"VALUES('Đóng tiền học','Chi')";
        db.execSQL(sql);
        sql="INSERT INTO LOAI_TC(TENLOAI,TRANGTHAI)"+"VALUES('Du Lịch','Chi')";
        db.execSQL(sql);
        sql = "CREATE TABLE KHOAN_TC(MATC integer primary key autoincrement,"+"TENKHOANTC text," +
                "NGAY date,"+ "TIEN Float,GhiChu Text,"+"MALOAI integer  references  LOAI_TC(MALOAI))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists LOAI_TC");
        db.execSQL("Drop table if exists KHOAN_TC");

        onCreate(db);
    }
}
