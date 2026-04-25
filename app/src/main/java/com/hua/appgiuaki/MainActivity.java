package com.hua.appgiuaki;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.hua.appgiuaki.activity.AddEditActivity;
import com.hua.appgiuaki.activity.DetailActivity;
import com.hua.appgiuaki.adapter.EmployeeAdapter;
import com.hua.appgiuaki.manager.EmployeeManager;
import com.hua.appgiuaki.model.Employee;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private EmployeeAdapter adapter;
    private List<Employee> currentList; // danh sách hiển thị hiện tại
    private EmployeeManager manager;

    private EditText etSearch;
    private Button btnSearch;
    private Spinner spType;
    private Button btnFilter;
    private Button btnSortSalary;
    private Button btnHighestSalary;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = EmployeeManager.getInstance();
        mappingViews();
        setupListView();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList(manager.getList());
    }

    private void mappingViews() {
        lv = findViewById(R.id.lvEmployees);
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        spType = findViewById(R.id.spTypeFilter);
        btnFilter = findViewById(R.id.btnFilter);
        btnSortSalary = findViewById(R.id.btnSortSalary);
        btnHighestSalary = findViewById(R.id.btnHighestSalary);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void setupListView() {
        currentList = manager.getList(); // mặc định hiển thị toàn bộ
        adapter = new EmployeeAdapter(this, currentList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Employee emp = currentList.get(position);
            int realIndex = manager.getList().indexOf(emp); // tìm vị trí thực trong danh sách gốc
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("employee_index", realIndex);
            startActivity(intent);
        });

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            // Xóa hoặc sửa: hiển thị dialog
            showOptionsDialog(position);
            return true;
        });
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });

        btnSearch.setOnClickListener(v -> {
            String keyword = etSearch.getText().toString().trim();
            if (keyword.isEmpty()) {
                refreshList(manager.getList());
            } else {
                refreshList(manager.searchByName(keyword));
            }
        });

        btnFilter.setOnClickListener(v -> {
            String type = spType.getSelectedItem().toString();
            if (type.equals("Tất cả")) {
                refreshList(manager.getList());
            } else {
                refreshList(manager.filterByType(type));
            }
        });

        btnSortSalary.setOnClickListener(v -> {
            refreshList(manager.sortBySalaryDesc());
        });

        btnHighestSalary.setOnClickListener(v -> {
            Employee emp = manager.getHighestSalaryEmployee();
            if (emp != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Nhân viên lương cao nhất")
                        .setMessage(emp.hienThiThongTin())
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                Toast.makeText(this, "Danh sách trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList(List<Employee> list) {
        currentList = list;
        adapter = new EmployeeAdapter(this, currentList);
        lv.setAdapter(adapter);
    }

    private void showOptionsDialog(int position) {
        final int realIndex = manager.getList().indexOf(currentList.get(position));
        String[] options = {"Sửa", "Xóa"};
        new AlertDialog.Builder(this)
                .setItems(options, (dialog, which) -> {
                    if (which == 0) { // Sửa
                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        intent.putExtra("mode", "edit");
                        intent.putExtra("employee_index", realIndex);
                        startActivity(intent);
                    } else { // Xóa
                        confirmDelete(realIndex);
                    }
                }).show();
    }

    private void confirmDelete(int index) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa nhân viên này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    manager.delete(index);
                    refreshList(manager.getList());
                    Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}