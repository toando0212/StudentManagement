package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditStudentActivity extends AppCompatActivity {

    EditText edtMaSV, edtHoTen, edtLop, edtNganh, edtKhoa, edtTruong;
    Button btnSave;
    int id;
    String baseUrl = "http://172.16.3.194:5000/students/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student); // dùng lại layout thêm

        // Ánh xạ view
        edtMaSV   = findViewById(R.id.edtMaSV);
        edtHoTen  = findViewById(R.id.edtHoTen);
        edtLop    = findViewById(R.id.edtLop);
        edtNganh  = findViewById(R.id.edtNganh);
        edtKhoa   = findViewById(R.id.edtKhoa);
        edtTruong = findViewById(R.id.edtTruong);
        btnSave   = findViewById(R.id.btnSave);

        // Lấy data từ Intent
        id       = getIntent().getIntExtra("id", -1);
        edtMaSV .setText(getIntent().getStringExtra("ma_sv"));
        edtHoTen.setText(getIntent().getStringExtra("ho_ten"));
        edtLop  .setText(getIntent().getStringExtra("lop"));
        edtNganh.setText(getIntent().getStringExtra("nganh"));
        edtKhoa .setText(getIntent().getStringExtra("khoa"));
        edtTruong.setText(getIntent().getStringExtra("truong"));

        btnSave.setText("Cập nhật");

        btnSave.setOnClickListener(v -> updateStudent());
    }

    private void updateStudent() {
        Map<String, String> params = new HashMap<>();
        params.put("ma_sv", edtMaSV.getText().toString());
        params.put("ho_ten", edtHoTen.getText().toString());
        params.put("lop",    edtLop.getText().toString());
        params.put("nganh",  edtNganh.getText().toString());
        params.put("khoa",   edtKhoa.getText().toString());
        params.put("truong", edtTruong.getText().toString());

        String url = baseUrl + id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params),
                resp -> {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                err -> {
                    err.printStackTrace();
                    Toast.makeText(this, "Lỗi cập nhật!", Toast.LENGTH_SHORT).show();
                }
        );
        queue.add(req);
    }
}
