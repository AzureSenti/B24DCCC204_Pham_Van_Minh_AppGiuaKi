package com.hua.appgiuaki.model;

public abstract class Employee {
    private String maNV;
    private String hoTen;
    private double luongCoBan;

    public Employee(String maNV, String hoTen, double luongCoBan) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        setLuongCoBan(luongCoBan);
    }

    // Getter & Setter với kiểm tra hợp lệ
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        if (luongCoBan <= 0) {
            throw new IllegalArgumentException("Lương cơ bản phải > 0");
        }
        this.luongCoBan = luongCoBan;
    }

    // Phương thức trừu tượng
    public abstract double tinhLuong();

    public abstract String hienThiThongTin();

    public abstract String getLoaiNhanVien();
}