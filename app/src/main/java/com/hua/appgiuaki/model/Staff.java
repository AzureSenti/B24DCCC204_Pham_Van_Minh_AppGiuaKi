package com.hua.appgiuaki.model;

import android.annotation.SuppressLint;

public class Staff extends Employee {
    private int soNgayCong;

    public Staff(String maNV, String hoTen, double luongCoBan, int soNgayCong) {
        super(maNV, hoTen, luongCoBan);
        setSoNgayCong(soNgayCong);
    }

    public int getSoNgayCong() {
        return soNgayCong;
    }

    public void setSoNgayCong(int soNgayCong) {
        if (soNgayCong < 0 || soNgayCong > 26) {
            throw new IllegalArgumentException("Số ngày công từ 0 đến 26");
        }
        this.soNgayCong = soNgayCong;
    }

    @Override
    public double tinhLuong() {
        return getLuongCoBan() * soNgayCong / 26.0;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String hienThiThongTin() {
        return String.format("Staff - Mã: %s, Tên: %s, Lương CB: %.0f, Ngày công: %d, Lương thực: %.0f",
                getMaNV(), getHoTen(), getLuongCoBan(), soNgayCong, tinhLuong());
    }

    @Override
    public String getLoaiNhanVien() {
        return "Staff";
    }
}