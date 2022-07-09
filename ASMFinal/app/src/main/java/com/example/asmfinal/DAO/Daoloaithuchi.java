package com.example.asmfinal.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmfinal.Model.LoaiThuChi;
import com.example.asmfinal.SQL.Dpheper;

import java.util.ArrayList;

public class Daoloaithuchi {
    Dpheper loaichi;
    public SQLiteDatabase db;
    public Daoloaithuchi(Context context){
        loaichi= new Dpheper(context);
    }
    public ArrayList<LoaiThuChi> readAll(){
        ArrayList<LoaiThuChi> dataloai = new ArrayList<>();
        SQLiteDatabase db = loaichi.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM LOAI_TC WHERE TRANGTHAI = '"+"Thu"+"'", null);
        cs.moveToFirst();
        while ((!cs.isAfterLast())){
            String matl =cs.getString(0);
            String tentl =cs.getString(1);
            String trangthai =cs.getString(2);
            dataloai.add(new LoaiThuChi(matl,tentl,trangthai));
            cs.moveToNext();

        }cs.close();
        return dataloai;
    }
    public boolean insert(LoaiThuChi loai){
        SQLiteDatabase db = loaichi.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI",loai.getTenloai());
        contentValues.put("TRANGTHAI",loai.getTrangthai());
        long insertloai = db.insert("LOAI_TC",null,contentValues);
        return insertloai>0;
    }

    public String getTen(String id){
        SQLiteDatabase db = loaichi.getWritableDatabase();
        String ten = "";
        Cursor cursor = db.rawQuery("SELECT TENLOAI FROM LOAI_TC WHERE MALOAI = '"+id+"'", null);
        if (cursor.moveToFirst()){
            do {
                ten = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return ten;

    }
    public boolean update(String maloai,String tenloai ,String trangthai){
        db= loaichi.getReadableDatabase();
        ContentValues values =  new ContentValues();
        values.put("TENLOAI",tenloai);
        values.put("TRANGTHAI",trangthai);
        int updateloai = db.update("LOAI_TC",values,"MALOAI=?",new String[]{maloai});
        return updateloai>0;
    }
    public boolean delete(String maloai){
        db= loaichi.getReadableDatabase();
        int xoaloai = db.delete("LOAI_TC","MALOAI=?",new String[]{maloai});
        return xoaloai>0;
    }
}
