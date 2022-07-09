package com.example.asmfinal.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmfinal.Model.LoaiChi;
import com.example.asmfinal.SQL.Dpheper;

import java.util.ArrayList;

public class DaoLoaiChi {
    Dpheper loaichithu;
    public SQLiteDatabase db;
    public DaoLoaiChi(Context context){
        loaichithu= new Dpheper(context);
    }
    public ArrayList<LoaiChi> readAll(){
        ArrayList<LoaiChi> datachi = new ArrayList<>();
        SQLiteDatabase db = loaichithu.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * From LOAI_TC WHERE TRANGTHAI like'Chi'",null);
        cs.moveToFirst();
        while ((!cs.isAfterLast())){
            String matl =cs.getString(0);
            String tentl =cs.getString(1);
            String trangthai =cs.getString(2);
            datachi.add(new LoaiChi(matl,tentl,trangthai));
            cs.moveToNext();

        }cs.close();
        return datachi;
    }
    public String giaTriten(String id){
        SQLiteDatabase db = loaichithu.getReadableDatabase();
        String ten = "";
        Cursor cursor = db.rawQuery("Select TENLOAI FROM LOAI_TC WHERE MALOAI = '"+id+"'",null);
        if(cursor.moveToFirst()){
            do{
                ten = cursor.getString(0);

            }while (cursor.moveToNext());
        }
        return ten;
    }
    public boolean insert(LoaiChi loai){
        SQLiteDatabase db = loaichithu.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI",loai.getTenloai());
        contentValues.put("TRANGTHAI",loai.getTrangthai());
        long insertloai = db.insert("LOAI_TC",null,contentValues);
        return insertloai>0;
    }
    public boolean update(String maloai,String tenloai ,String trangthai){
        db= loaichithu.getReadableDatabase();
        ContentValues values =  new ContentValues();
        values.put("TENLOAI",tenloai);
        values.put("TRANGTHAI",trangthai);
        int updateloai = db.update("LOAI_TC",values,"MALOAI=?",new String[]{maloai});
        return updateloai>0;
    }
    public boolean delete(String maloai){
        db= loaichithu.getReadableDatabase();
        int xoaloai = db.delete("LOAI_TC","MALOAI=?",new String[]{maloai});
        return xoaloai>0;
    }
}
