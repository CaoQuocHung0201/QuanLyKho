package com.mibo.quanlykho.Models;

public class phieuXuat {
    private int SoLuong;
    private int GiaXuat;
    private String NgayXuat;
    private String NhanVien;
    private String BarCode;

    public phieuXuat(){}

    public phieuXuat(int soLuong, int giaXuat, String ngayXuat, String nhanVien, String barCode) {
        SoLuong = soLuong;
        GiaXuat = giaXuat;
        NgayXuat = ngayXuat;
        NhanVien = nhanVien;
        BarCode = barCode;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getGiaXuat() {
        return GiaXuat;
    }

    public void setGiaXuat(int giaXuat) {
        GiaXuat = giaXuat;
    }

    public String getNgayXuat() {
        return NgayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        NgayXuat = ngayXuat;
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
