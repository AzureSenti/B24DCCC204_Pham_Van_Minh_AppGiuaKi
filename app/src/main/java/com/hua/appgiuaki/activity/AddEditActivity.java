package com.hua.appgiuaki.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.hua.appgiuaki.R;
import com.hua.appgiuaki.manager.EmployeeManager;
import com.hua.appgiuaki.model.*;

public class AddEditActivity extends AppCompatActivity {
    private RadioGroup rgType;
    private EditText etMaNV, etHoTen, etLuongCoBan, etExtra;
    private TextView tvExtraLabel;
    private Button btnSave, btnCancel;

    private String mode; // "add" or "edit"
    private int employeeIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        mappingViews();
        getIntentData();
        setupTypeChange();
        setupButtons();
    }

    private void mappingViews() {
        rgType = findViewById(R.id.rgType);
        etMaNV = findViewById(R.id.etMaNV);
        etHoTen = findViewById(R.id.etHoTen);
        etLuongCoBan = findViewById(R.id.etLuongCoBan);
        etExtra = findViewById(R.id.etExtra);
        tvExtraLabel = findViewById(R.id.tvExtraLabel);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        if ("edit".equals(mode)) {
            employeeIndex = intent.getIntExtra("employee_index", -1);
            if (employeeIndex != -1) {
                loadEmployeeData(employeeIndex);
            }
        } else {
            // mặc định chọn Staff
            ((RadioButton)findViewById(R.id.rbStaff)).setChecked(true);
            updateExtraLabel();
        }
    }

    private void loadEmployeeData(int index) {
        Employee emp = EmployeeManager.getInstance().getList().get(index);
        etMaNV.setText(emp.getMaNV());
        etHoTen.setText(emp.getHoTen());
        etLuongCoBan.setText(String.valueOf(emp.getLuongCoBan()));

        if (emp instanceof Staff) {
            ((RadioButton)findViewById(R.id.rbStaff)).setChecked(true);
            etExtra.setText(String.valueOf(((Staff) emp).getSoNgayCong()));
        } else if (emp instanceof Manager) {
            ((RadioButton)findViewById(R.id.rbManager)).setChecked(true);
            etExtra.setText(String.valueOf(((Manager) emp).getPhuCapChucVu()));
        } else if (emp instanceof Intern) {
            ((RadioButton)findViewById(R.id.rbIntern)).setChecked(true);
            etExtra.setText(String.valueOf(((Intern) emp).getSoGioLam()));
        }
        updateExtraLabel();
    }

    private void setupTypeChange() {
        rgType.setOnCheckedChangeListener((group, checkedId) -> updateExtraLabel());
    }

    private void updateExtraLabel() {
        int id = rgType.getCheckedRadioButtonId();
        if (id == R.id.rbStaff) {
            tvExtraLabel.setText("Số ngày công (0-26):");
            etExtra.setHint("Nhập số ngày công");
        } else if (id == R.id.rbManager) {
            tvExtraLabel.setText("Phụ cấp chức vụ:");
            etExtra.setHint("Nhập phụ cấp");
        } else if (id == R.id.rbIntern) {
            tvExtraLabel.setText("Số giờ làm:");
            etExtra.setHint("Nhập số giờ");
        }
    }

    private void setupButtons() {
        btnSave.setOnClickListener(v -> saveEmployee());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveEmployee() {
        // Lấy dữ liệu chung
        String ma = etMaNV.getText().toString().trim();
        String ten = etHoTen.getText().toString().trim();
        String luongStr = etLuongCoBan.getText().toString().trim();
        String extraStr = etExtra.getText().toString().trim();

        if (TextUtils.isEmpty(ma) || TextUtils.isEmpty(ten) || TextUtils.isEmpty(luongStr) || TextUtils.isEmpty(extraStr)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double luongCoBan;
        try {
            luongCoBan = Double.parseDouble(luongStr);
            if (luongCoBan <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Lương cơ bản phải là số > 0", Toast.LENGTH_SHORT).show();
            return;
        }

        int checkedId = rgType.getCheckedRadioButtonId();
        Employee newEmp = null;
        try {
            if (checkedId == R.id.rbStaff) {
                int ngayCong = Integer.parseInt(extraStr);
                if (ngayCong < 0 || ngayCong > 26) throw new IllegalArgumentException("Số ngày công 0-26");
                newEmp = new Staff(ma, ten, luongCoBan, ngayCong);
            } else if (checkedId == R.id.rbManager) {
                double phuCap = Double.parseDouble(extraStr);
                newEmp = new Manager(ma, ten, luongCoBan, phuCap);
            } else if (checkedId == R.id.rbIntern) {
                int gioLam = Integer.parseInt(extraStr);
                if (gioLam < 0) throw new IllegalArgumentException("Số giờ làm >= 0");
                newEmp = new Intern(ma, ten, luongCoBan, gioLam);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá trị không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        if ("add".equals(mode)) {
            EmployeeManager.getInstance().add(newEmp);
        } else {
            EmployeeManager.getInstance().update(employeeIndex, newEmp);
        }

        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
        finish();
    }
}