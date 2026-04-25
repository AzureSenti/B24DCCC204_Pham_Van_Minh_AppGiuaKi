package com.hua.appgiuaki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hua.appgiuaki.R;
import com.hua.appgiuaki.model.Employee;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> employees;

    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        }
        Employee emp = employees.get(position);

        TextView tvMa = convertView.findViewById(R.id.tvMa);
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvLoai = convertView.findViewById(R.id.tvLoai);
        TextView tvLuong = convertView.findViewById(R.id.tvLuong);

        tvMa.setText(emp.getMaNV());
        tvTen.setText(emp.getHoTen());
        tvLoai.setText(emp.getLoaiNhanVien());
        tvLuong.setText(String.format("%,.0f VND", emp.tinhLuong()));

        return convertView;
    }
}