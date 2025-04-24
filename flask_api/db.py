import mysql.connector

def connect_db():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        password="0212",   # dùng mật khẩu bạn đặt
        database="sinhvien_db"
    )
