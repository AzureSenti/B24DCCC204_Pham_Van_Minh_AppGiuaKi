package com.hua.appgiuaki.manager;

import com.hua.appgiuaki.model.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeManager {
    private static EmployeeManager instance;
    private List<Employee> list;

    private EmployeeManager() {
        list = new ArrayList<>();
        // Có thể thêm dữ liệu mẫu
    }

    public static EmployeeManager getInstance() {
        if (instance == null) {
            instance = new EmployeeManager();
        }
        return instance;
    }

    public List<Employee> getList() {
        return list;
    }

    public void add(Employee e) {
        list.add(e);
    }

    public void update(int index, Employee e) {
        list.set(index, e);
    }

    public void delete(int index) {
        list.remove(index);
    }

    // Tìm theo họ tên (tương đối)
    public List<Employee> searchByName(String keyword) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list) {
            if (e.getHoTen().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    // Lọc theo loại nhân viên
    public List<Employee> filterByType(String type) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list) {
            if (e.getLoaiNhanVien().equalsIgnoreCase(type)) {
                result.add(e);
            }
        }
        return result;
    }

    // Sắp xếp lương giảm dần
    public List<Employee> sortBySalaryDesc() {
        List<Employee> sortedList = new ArrayList<>(list);
        Collections.sort(sortedList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e2.tinhLuong(), e1.tinhLuong());
            }
        });
        return sortedList;
    }

    // Nhân viên có lương cao nhất
    public Employee getHighestSalaryEmployee() {
        if (list.isEmpty()) return null;
        Employee max = list.get(0);
        for (Employee e : list) {
            if (e.tinhLuong() > max.tinhLuong()) {
                max = e;
            }
        }
        return max;
    }
}