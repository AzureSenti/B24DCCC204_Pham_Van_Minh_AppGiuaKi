package com.hua.appgiuaki.model;

import android.annotation.SuppressLint;

public class Manager extends Employee {
    private double phuCapChucVu;

    public Manager(String maNV, String hoTen, double luongCoBan, double phuCapChucVu) {
        super(maNV, hoTen, luongCoBan);
        this.phuCapChucVu = phuCapChucVu;
    }

    public double getPhuCapChucVu() {
        return phuCapChucVu;
    }

    public void setPhuCapChucVu(double phuCapChucVu) {
        this.phuCapChucVu = phuCapChucVu;
    }

    @Override
    public double tinhLuong() {
        return getLuongCoBan() + phuCapChucVu;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String hienThiThongTin() {
        return String.format("Manager - Mã: %s, Tên: %s, Lương CB: %.0f, Phụ cấp: %.0f, Lương thực: %.0f",
                getMaNV(), getHoTen(), getLuongCoBan(), phuCapChucVu, tinhLuong());
    }

    @Override
    public String getLoaiNhanVien() {
        return "Manager";
    }
}