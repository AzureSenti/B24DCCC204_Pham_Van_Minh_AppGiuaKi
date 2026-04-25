package com.hua.appgiuaki.model;

import android.annotation.SuppressLint;

public class Intern extends Employee {
    private int soGioLam;

    public Intern(String maNV, String hoTen, double luongCoBan, int soGioLam) {
        super(maNV, hoTen, luongCoBan);
        setSoGioLam(soGioLam);
    }

    public int getSoGioLam() {
        return soGioLam;
    }

    public void setSoGioLam(int soGioLam) {
        if (soGioLam < 0) {
            throw new IllegalArgumentException("Số giờ làm >= 0");
        }
        this.soGioLam = soGioLam;
    }

    @Override
    public double tinhLuong() {
        return soGioLam * 30000;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String hienThiThongTin() {
        return String.format("Intern - Mã: %s, Tên: %s, Giờ làm: %d, Lương thực: %.0f",
                getMaNV(), getHoTen(), soGioLam, tinhLuong());
    }

    @Override
    public String getLoaiNhanVien() {
        return "Intern";
    }
}