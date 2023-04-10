package com.mibo.quanlykho.Models;

public class SanPham {

    public static String ngNhap="NgayNhap", ngXuat="NgayXuat",sl="soLuong";

    private String Name;
    private int GiaNhap;
    private int SoLuong;
    private String HSD;
    private String Img;
    private String ThuongHieu;
    private String XuatXu;
    private String NgayNhap;
    private String NgayXuat;

    public SanPham(){}

    public SanPham(String name,int soLuong) {
        Name = name;
        SoLuong = soLuong;
    }

    public SanPham(String name, int giaNhap, int soLuong, String HSD, String img, String thuongHieu, String xuatXu) {
        Name = name;
        GiaNhap = giaNhap;
        SoLuong = soLuong;
        this.HSD = HSD;
        Img = img;
        ThuongHieu = thuongHieu;
        XuatXu = xuatXu;
    }

    public static String getNgNhap() {
        return ngNhap;
    }

    public static void setNgNhap(String ngNhap) {
        SanPham.ngNhap = ngNhap;
    }

    public static String getNgXuat() {
        return ngXuat;
    }

    public static void setNgXuat(String ngXuat) {
        SanPham.ngXuat = ngXuat;
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

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
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

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public String getNgayXuat() {
        return NgayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        NgayXuat = ngayXuat;
    }
}
