package com.mibo.quanlykho.Models;

public class phieuNhap {
    private int SoLuong;
    private int GiaNhap;
    private String NgayNhap;
    private String NhanVien;
    private String BarCode;

    public phieuNhap(int soLuong, int giaNhap, String ngayNhap, String nhanVien, String barCode) {
        SoLuong = soLuong;
        GiaNhap = giaNhap;
        NgayNhap = ngayNhap;
        NhanVien = nhanVien;
        BarCode = barCode;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        GiaNhap = giaNhap;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public String getNhanVien() {
        return NhanVien;
    }

    public void setNhanVien(String nhanVien) {
        NhanVien = nhanVien;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }
}
