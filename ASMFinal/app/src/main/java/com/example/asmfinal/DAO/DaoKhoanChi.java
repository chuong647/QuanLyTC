package com.example.asmfinal.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmfinal.Model.KhoanChi;
import com.example.asmfinal.SQL.Dpheper;

import java.util.ArrayList;

public class DaoKhoanChi {
    Dpheper tc;
    public SQLiteDatabase db;
    public DaoKhoanChi(Context context){
        tc = new Dpheper(context);
    }

    public ArrayList<KhoanChi> readAll(){
        ArrayList<KhoanChi> datakhoanchi = new ArrayList<>();
        SQLiteDatabase db = tc.getReadableDatabase();
        Cursor cs = db.rawQuery("Select khoan.MATC,khoan.TENKHOANTC,khoan.NGAY,khoan.TIEN,khoan.GhiChu,khoan.MALOAI,loai.TENLOAI,loai.TRANGTHAI" +
                " From KHOAN_TC khoan inner join LOAI_TC loai on(khoan.MALOAI=loai.MALOAI) AND loai.TRANGTHAI like'%Chi%'",null);
        cs.moveToFirst();
        while ((!cs.isAfterLast())){
            String matc =cs.getString(0);
            String tentc =cs.getString(1);
            String ngay =cs.getString(2);
            double tien =cs.getDouble(3);
            String ghichu =cs.getString(4);
            String maloai =cs.getString(5);
            datakhoanchi.add(new KhoanChi(matc,tentc,ngay,tien,ghichu,maloai));
            cs.moveToNext();

        }cs.close();
        return datakhoanchi;
    }
    public ArrayList<KhoanChi> getthongkechi(){
        ArrayList<KhoanChi> datakhoanthu = new ArrayList<>();
        SQLiteDatabase db = tc.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenkhoantc,tien FROM KHOAN_TC,LOAI_TC WHERE KHOAN_TC.MALOAI=LOAI_TC.maloai And \n" +
                "LOAI_TC.TRANGTHAI LIKE 'Chi'",null);
        cursor.moveToFirst();
        while ((!cursor.isAfterLast())){
            String tentc =cursor.getString(0);
            double tien =cursor.getDouble(1);
            datakhoanthu.add(new KhoanChi(tentc,tien));
            cursor.moveToNext();

        }cursor.close();
        return datakhoanthu;
    }
    public double gettong(){
        double sumtien=0.0;
        SQLiteDatabase db = tc.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(tien) FROM KHOAN_TC,LOAI_TC WHERE KHOAN_TC.MALOAI=LOAI_TC.maloai And \n" +
                "LOAI_TC.TRANGTHAI LIKE 'Chi'",null);
        if(cursor.moveToFirst()){
            do {
                sumtien = cursor.getDouble(0);
            }while (cursor.moveToNext());
        }
        return sumtien;
    }
    public ArrayList<KhoanChi> gettheongay(String ngaydau, String ngaycuoi){
        ArrayList<KhoanChi> datakhoanthu = new ArrayList<>();
        SQLiteDatabase db = tc.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT KHOAN_TC.tenkhoantc,KHOAN_TC.tien FROM KHOAN_TC,LOAI_TC" +
                " WHERE KHOAN_TC.maloai=LOAI_TC.maloai AND KHOAN_TC.NGAY BETWEEN '"+ngaydau+"'AND'"+ngaycuoi+"' AND LOAI_TC.TRANGTHAI LIKE 'Chi';",null);
        cursor.moveToFirst();
        while ((!cursor.isAfterLast())){
            String tentc =cursor.getString(0);
            double tien =cursor.getDouble(1);
            datakhoanthu.add(new KhoanChi(tentc,tien));
            cursor.moveToNext();

        }cursor.close();
        return datakhoanthu;
    }
    public double gettongtienchi(String ngaydau, String ngaycuoi){
        double sumtienngay=0.0;
        SQLiteDatabase db = tc.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(tien) FROM KHOAN_TC,LOAI_TC" +
                " WHERE KHOAN_TC.maloai=LOAI_TC.maloai AND KHOAN_TC.NGAY BETWEEN '"+ngaydau+"'AND'"+ngaycuoi+"' AND LOAI_TC.TRANGTHAI LIKE 'Chi';",null);
        cursor.moveToFirst();
        if(cursor.moveToFirst()){
            do {
                sumtienngay = cursor.getDouble(0);
            }while (cursor.moveToNext());
        }
        return sumtienngay;
    }
    public boolean insert(KhoanChi khoan){
        SQLiteDatabase db = tc.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENKHOANTC",khoan.getTentc());
        contentValues.put("NGAY",khoan.getNgay());
        contentValues.put("TIEN",khoan.getTien());
        contentValues.put("GhiChu",khoan.getGhichu());
        contentValues.put("MALOAI",khoan.getMaloai());
        long insert = db.insert("KHOAN_TC",null,contentValues);
        return insert>0;
    }
    public boolean update(String makhoan,String tenkhoantc ,String ngay, Double tien, String ghichu, String maloai){
        db= tc.getReadableDatabase();
        ContentValues values =  new ContentValues();
        values.put("TENKHOANTC",tenkhoantc);
        values.put("NGAY",ngay);
        values.put("TIEN",tien);
        values.put("GhiChu",ghichu);
        values.put("MALOAI",maloai);
        int update = db.update("KHOAN_TC",values,"MATC=?",new String[]{makhoan});
        return update>0;
    }
    public boolean delete(String makhoan){
        db= tc.getReadableDatabase();
        int xoa = db.delete("KHOAN_TC","MATC=?",new String[]{makhoan});
        return xoa>0;
    }
}
