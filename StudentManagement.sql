CREATE DATABASE sinhvien_db;
USE sinhvien_db;

CREATE TABLE sinhvien (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ma_sv VARCHAR(20),
  ho_ten VARCHAR(100),
  lop VARCHAR(20),
  nganh VARCHAR(50),
  khoa VARCHAR(50),
  truong VARCHAR(100)
);

INSERT INTO sinhvien (ma_sv, ho_ten, lop, nganh, khoa, truong)
VALUES ('B21DCCN001', 'Nguyen Van A', 'D21CQCN01', 'CNTT', 'Công nghệ thông tin', 'Học viện Kỹ thuật Mật mã');
SELECT * FROM sinhvien_db.sinhvien;