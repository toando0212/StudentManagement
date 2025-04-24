package com.example.studentmanagement;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Button btnAdd;
    private final String url = "http://172.16.3.194:5000/students";

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchView   = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        loadStudents(null);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    loadStudents(query);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error in search", e);
                    Toast.makeText(MainActivity.this, "Lỗi khi tìm kiếm: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        adapter = new StudentAdapter(studentList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload mỗi khi quay về
        loadStudents(null);
    }

    private void loadStudents(@Nullable String nameFilter) {
        String url = "http://172.16.3.194:5000/students";
        if (nameFilter != null && !nameFilter.isEmpty()) {
            try {
                // encode với Charset name để tương thích Android API cũ
                String q = URLEncoder.encode(nameFilter, "UTF-8");
                url += "?name=" + q;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    studentList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Student s = new Student();
                            s.id     = obj.getInt("id");
                            s.ma_sv  = obj.getString("ma_sv");
                            s.ho_ten = obj.getString("ho_ten");
                            s.lop    = obj.getString("lop");
                            s.nganh  = obj.getString("nganh");
                            s.khoa   = obj.getString("khoa");
                            s.truong = obj.getString("truong");
                            studentList.add(s);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show()
        );
        queue.add(req);
    }


}
