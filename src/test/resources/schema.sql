CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) ,
    nickname VARCHAR(50),
    email VARCHAR(50) ,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10),
    remark text,
    telephone VARCHAR(15),
    is_deleted boolean
);

CREATE TABLE students_courses(
course_id INT AUTO_INCREMENT PRIMARY KEY ,
student_id INT NOT NULL,
course_name VARCHAR(50) NOT NULL,
course_start_at TIMESTAMP,
course_end_at TIMESTAMP,
FOREIGN KEY (student_id) REFERENCES students(id)
);