package com.hua.appgiuaki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.hua.appgiuaki.R;
import com.hua.appgiuaki.manager.EmployeeManager;
import com.hua.appgiuaki.model.Employee;
import com.hua.appgiuaki.model.Intern;
import com.hua.appgiuaki.model.Manager;
import com.hua.appgiuaki.model.Staff;

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
            tvInfo.setText(android.text.Html.fromHtml(formatEmployeeDetail(emp),
                    android.text.Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvInfo.setText("Không tìm thấy nhân viên");
        }

        btnBack.setOnClickListener(v -> finish());
    }

    // Hàm tạo chuỗi HTML đẹp hơn
    private String formatEmployeeDetail(Employee emp) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Mã nhân viên:</b> ").append(emp.getMaNV()).append("<br><br>");
        sb.append("<b>Họ tên:</b> ").append(emp.getHoTen()).append("<br><br>");
        sb.append("<b>Loại nhân viên:</b> <font color='#00BCD4'>").append(emp.getLoaiNhanVien()).append("</font><br><br>");

        // Thông tin riêng từng loại
        if (emp instanceof Staff) {
            Staff s = (Staff) emp;
            sb.append("<b>Số ngày công:</b> ").append(s.getSoNgayCong()).append(" / 26<br><br>");
            sb.append("<b>Lương cơ bản:</b> ").append(String.format("%,.0f", emp.getLuongCoBan())).append(" VND<br><br>");
        } else if (emp instanceof Manager) {
            Manager m = (Manager) emp;
            sb.append("<b>Phụ cấp chức vụ:</b> ").append(String.format("%,.0f", m.getPhuCapChucVu())).append(" VND<br><br>");
            sb.append("<b>Lương cơ bản:</b> ").append(String.format("%,.0f", emp.getLuongCoBan())).append(" VND<br><br>");
        } else if (emp instanceof Intern) {
            Intern i = (Intern) emp;
            sb.append("<b>Số giờ làm:</b> ").append(i.getSoGioLam()).append(" giờ<br><br>");
            sb.append("<b>Đơn giá:</b> 30,000 VND/giờ<br><br>");
        }

        // Lương thực nhận được highlight
        sb.append("<font color='#FF4081'><b>Lương thực nhận:</b></font> " +
                "<font color='#FF4081'><b>").append(String.format("%,.0f", emp.tinhLuong())).append(" VND</b></font><br>");
        return sb.toString();
    }
}