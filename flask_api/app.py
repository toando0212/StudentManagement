from flask import Flask, request, jsonify
from flask_cors import CORS
from db import connect_db

app = Flask(__name__)
CORS(app)

@app.route("/students", methods=["GET"])
def get_students():
    name = request.args.get("name")  # lấy param ?name=...
    conn = connect_db()
    cursor = conn.cursor(dictionary=True)
    if name:
        sql = "SELECT * FROM sinhvien WHERE ho_ten LIKE %s"
        cursor.execute(sql, (f"%{name}%",))
    else:
        cursor.execute("SELECT * FROM sinhvien")
    results = cursor.fetchall()
    conn.close()
    return jsonify(results)

@app.route("/students", methods=["POST"])
def add_student():
    data = request.get_json()
    conn = connect_db()
    cursor = conn.cursor()
    sql = "INSERT INTO sinhvien (ma_sv, ho_ten, lop, nganh, khoa, truong) VALUES (%s, %s, %s, %s, %s, %s)"
    cursor.execute(sql, (data['ma_sv'], data['ho_ten'], data['lop'], data['nganh'], data['khoa'], data['truong']))
    conn.commit()
    conn.close()
    return jsonify({"message": "Student added"})

@app.route("/students/<int:id>", methods=["PUT"])
def update_student(id):
    data = request.get_json()
    conn = connect_db()
    cursor = conn.cursor()
    sql = "UPDATE sinhvien SET ho_ten=%s, lop=%s, nganh=%s, khoa=%s, truong=%s WHERE id=%s"
    cursor.execute(sql, (data['ho_ten'], data['lop'], data['nganh'], data['khoa'], data['truong'], id))
    conn.commit()
    conn.close()
    return jsonify({"message": "Student updated"})

@app.route("/students/<int:id>", methods=["DELETE"])
def delete_student(id):
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("DELETE FROM sinhvien WHERE id=%s", (id,))
    conn.commit()
    conn.close()
    return jsonify({"message": "Student deleted"})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
