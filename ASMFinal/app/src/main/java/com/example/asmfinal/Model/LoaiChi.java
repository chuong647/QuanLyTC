package com.example.asmfinal.Model;

public class LoaiChi {
    private String maloai;
    private String tenloai;
    private String trangthai;

    public LoaiChi() {
    }

    public LoaiChi(String maloai, String tenloai, String trangthai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.trangthai = trangthai;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
