package com.mibo.quanlykho.Models;

public class Thong_tin_lich_su_sp {
    private String nameSP;
    private String giaSP;
    private String soLuongSP;
    private String timeSP;
    private String nhanVien;

    public Thong_tin_lich_su_sp(String nameSP, String giaSP, String soLuongSP, String timeSP, String nhanVien) {
        this.nameSP = nameSP;
        this.giaSP = giaSP;
        this.soLuongSP = soLuongSP;
        this.timeSP = timeSP;
        this.nhanVien = nhanVien;
    }

    public String getNameSP() {
        return nameSP;
    }

    public void setNameSP(String nameSP) {
        this.nameSP = nameSP;
    }

    public String getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(String giaSP) {
        this.giaSP = giaSP;
    }

    public String getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(String soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    public String getTimeSP() {
        return timeSP;
    }

    public void setTimeSP(String timeSP) {
        this.timeSP = timeSP;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }
}
