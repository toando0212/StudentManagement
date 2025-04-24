package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddStudent";
    EditText edtMaSV, edtHoTen, edtLop, edtNganh, edtKhoa, edtTruong;
    Button btnSave;
    // Nếu chạy emulator: http://10.0.2.2:5000/students
    // Nếu adb reverse: http://127.0.0.1:5000/students
    // Nếu LAN IP: http://192.168.x.x:5000/students
    String url = "http://172.16.3.194:5000/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        edtMaSV   = findViewById(R.id.edtMaSV);
        edtHoTen  = findViewById(R.id.edtHoTen);
        edtLop    = findViewById(R.id.edtLop);
        edtNganh  = findViewById(R.id.edtNganh);
        edtKhoa   = findViewById(R.id.edtKhoa);
        edtTruong = findViewById(R.id.edtTruong);
        btnSave   = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveStudent());
    }

    private void saveStudent() {
        Map<String, String> params = new HashMap<>();
        params.put("ma_sv",  edtMaSV.getText().toString().trim());
        params.put("ho_ten", edtHoTen.getText().toString().trim());
        params.put("lop",    edtLop.getText().toString().trim());
        params.put("nganh",  edtNganh.getText().toString().trim());
        params.put("khoa",   edtKhoa.getText().toString().trim());
        params.put("truong", edtTruong.getText().toString().trim());

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                response -> {
                    Toast.makeText(this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
                    finish();  // Quay về MainActivity để tự reload
                },
                error -> {
                    Log.e(TAG, "Volley error", error);
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e(TAG, "Status code: " + statusCode);
                        Log.e(TAG, "Response body: " + body);
                    }
                    Toast.makeText(this, "Lỗi khi thêm!", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}
