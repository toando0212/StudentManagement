package com.example.studentmanagement;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<Student> studentList;
    private Context context;
    private static final String BASE_URL = "http://172.16.3.194:5000/students/";

    public StudentAdapter(ArrayList<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student s = studentList.get(position);
        holder.txtMaSV.setText(s.ma_sv);
        holder.txtHoTen.setText(s.ho_ten);
        holder.txtLop.setText("Lớp: " + s.lop);
        holder.txtNganh.setText("Ngành: " + s.nganh);
        holder.txtKhoa.setText("Khoa: " + s.khoa);
        holder.txtTruong.setText("Trường: " + s.truong);

        // Sửa sinh viên
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditStudentActivity.class);
            intent.putExtra("id", s.id);
            intent.putExtra("ma_sv", s.ma_sv);
            intent.putExtra("ho_ten", s.ho_ten);
            intent.putExtra("lop", s.lop);
            intent.putExtra("nganh", s.nganh);
            intent.putExtra("khoa", s.khoa);
            intent.putExtra("truong", s.truong);
            context.startActivity(intent);
        });

        // Xóa sinh viên với popup xác nhận
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn chắc chắn muốn xóa sinh viên " + s.ho_ten + " (" + s.ma_sv + ")?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        String url = BASE_URL + s.id;
                        RequestQueue queue = Volley.newRequestQueue(context);
                        JsonObjectRequest req = new JsonObjectRequest(
                                Request.Method.DELETE,
                                url,
                                null,
                                response -> {
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    studentList.remove(position);
                                    notifyItemRemoved(position);
                                },
                                error -> Toast.makeText(context, "Lỗi khi xóa", Toast.LENGTH_SHORT).show()
                        );
                        queue.add(req);
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSV, txtHoTen, txtLop, txtNganh, txtKhoa, txtTruong;
        Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSV   = itemView.findViewById(R.id.txtMaSV);
            txtHoTen  = itemView.findViewById(R.id.txtHoTen);
            txtLop    = itemView.findViewById(R.id.txtLop);
            txtNganh  = itemView.findViewById(R.id.txtNganh);
            txtKhoa   = itemView.findViewById(R.id.txtKhoa);
            txtTruong = itemView.findViewById(R.id.txtTruong);
            btnEdit   = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
