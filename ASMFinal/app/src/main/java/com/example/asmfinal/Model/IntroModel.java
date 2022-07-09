package com.example.asmfinal.Model;

public class IntroModel {
    String tilte, loinoi;
    int hinh;

    public IntroModel(String tilte, String loinoi, int hinh) {
        this.tilte = tilte;
        this.loinoi = loinoi;
        this.hinh = hinh;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getLoinoi() {
        return loinoi;
    }

    public void setLoinoi(String loinoi) {
        this.loinoi = loinoi;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }
}
