package com.mibo.quanlykho.Models;

public class SanPham {

    public static String ngNhap="NgayNhap", ngXuat="NgayXuat",sl="soLuong",Img="Img";

    private String Name;
    private int GiaNhap;
    private int SoLuong;
    private String HSD;
    private String ThuongHieu;
    private String XuatXu;

    public SanPham(){}

    public SanPham(String name, int soLuong) {
        Name = name;
        SoLuong = soLuong;
    }

    public SanPham(String name, int giaNhap, int soLuong, String HSD, String thuongHieu, String xuatXu) {
        Name = name;
        GiaNhap = giaNhap;
        SoLuong = soLuong;
        this.HSD = HSD;
        ThuongHieu = thuongHieu;
        XuatXu = xuatXu;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        GiaNhap = giaNhap;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHSD() {
        return HSD;
    }

    public void setHSD(String HSD) {
        this.HSD = HSD;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        ThuongHieu = thuongHieu;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String xuatXu) {
        XuatXu = xuatXu;
    }

}
