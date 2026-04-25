package com.hua.appgiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.hua.appgiuaki.R;
import com.hua.appgiuaki.manager.EmployeeManager;
import com.hua.appgiuaki.model.Employee;

public class DetailActivity extends AppCompatActivity {
    private TextView tvInfo;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvInfo = findViewById(R.id.tvDetailInfo);
        btnBack = findViewById(R.id.btnBack);

        int index = getIntent().getIntExtra("employee_index", -1);
        if (index != -1) {
            Employee emp = EmployeeManager.getInstance().getList().get(index);
            tvInfo.setText(emp.hienThiThongTin());
        } else {
            tvInfo.setText("Không tìm thấy nhân viên");
        }

        btnBack.setOnClickListener(v -> finish());
    }
}