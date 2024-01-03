use university; 
CREATE TABLE Faculty
(
  faculty_id INT NOT NULL AUTO_INCREMENT,
  faculty_name VARCHAR(50) UNIQUE,
  PRIMARY KEY (faculty_id)
);

CREATE TABLE Departments
(
  Dep_name VARCHAR(50) UNIQUE,
  dep_id INT NOT NULL AUTO_INCREMENT,
  faculty_id INT NOT NULL,
  PRIMARY KEY (dep_id),
  FOREIGN KEY (faculty_id) REFERENCES Faculty(faculty_id)
);
CREATE TABLE course
(
  crs_code INT NOT NULL AUTO_INCREMENT,
  crs_name VARCHAR(50),
  crs_desc VARCHAR(200),
  credits int not null,
  PRIMARY KEY (crs_code,faculty_id),
    faculty_id int,
    FOREIGN KEY(faculty_id) REFERENCES Faculty(faculty_id)
);


CREATE TABLE DepCourse
(
  faculty_id int, 
  dep_id INT NOT NULL,
  crs_code INT NOT NULL,
  PRIMARY KEY (dep_id, crs_code),
  FOREIGN KEY (dep_id) REFERENCES Departments(dep_id),
  FOREIGN KEY (crs_code,faculty_id) REFERENCES course(crs_code,faculty_id)
);

CREATE TABLE group_std
(
  dep_id int ,
  group_id INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (group_id,dep_id),
  FOREIGN KEY (dep_id) REFERENCES Departments(dep_id)
);

CREATE TABLE Instructor
(
   Job_Tittle VARCHAR(50) NOT NULL,
   Instructor_id INT NOT NULL AUTO_INCREMENT,
   Salary int ,
  Full_Name VARCHAR(50) NOT NULL,
  PRIMARY KEY (Instructor_id)
);

CREATE TABLE Table_period
(
  Period_id INT NOT NULL AUTO_INCREMENT,
  From_T TIME NOT NULL,
  To_T TIME NOT NULL,
  PRIMARY KEY (Period_id)
);

CREATE TABLE student
(
   stu_id INT NOT NULL AUTO_INCREMENT,
   gender ENUM('Male','Female'),
   dep_id INT,
  
  stu_gpa DOUBLE DEFAULT NULL ,
  CONSTRAINT chk_gpa_range CHECK (stu_gpa is NULL OR(stu_gpa >= 0.00 AND stu_gpa <= 4.00)),
  std_name VARCHAR(50) NOT NULL,
  Nationality VARCHAR(50) NOT NULL,
  Religion VARCHAR(50) NOT NULL,
  city VARCHAR(80) NOT NULL ,
  country VARCHAR(50) NOT NULL,
  group_id INT NOT NULL,
  PRIMARY KEY (stu_id),
  FOREIGN KEY (dep_id) REFERENCES  Departments(dep_id),
  FOREIGN KEY (group_id,dep_id) REFERENCES group_std(group_id,dep_id) 
);

CREATE TABLE phone(
 stu_id INT NOT NULL,
 phone_id int PRIMARY KEY AUTO_INCREMENT,
 Phone VARCHAR(20),
FOREIGN KEY (stu_id) REFERENCES student (stu_id)
);



CREATE TABLE ClassRoom
(
  class_type VARCHAR(50) NOT NULL,
  class_number INT NOT NULL,
  faculty_id INT NOT NULL,
  PRIMARY KEY (class_number, faculty_id),
  FOREIGN KEY (faculty_id) REFERENCES Faculty(faculty_id)
);

CREATE TABLE enrollment
( 
faculty_id int ,
  mark DOUBLE DEFAULT null,
  stu_id INT NOT NULL,
  crs_code INT NOT NULL,
  PRIMARY KEY (stu_id, crs_code),
  FOREIGN KEY (stu_id) REFERENCES student(stu_id),
  FOREIGN KEY (crs_code,faculty_id) REFERENCES course(crs_code,faculty_id)
); 
CREATE TABLE Lecture
(
  Lec_id INT NOT NULL UNIQUE,
  crs_code INT NOT NULL,
  group_id INT NOT NULL,
  class_number INT NOT NULL,
  faculty_id INT NOT NULL,
  Instructor_id INT NOT NULL,
  Period_id INT NOT NULL,
  PRIMARY KEY (class_number , Period_id,faculty_id),  
  FOREIGN KEY (class_number, faculty_id) REFERENCES ClassRoom(class_number, faculty_id),
  FOREIGN KEY (crs_code,faculty_id) REFERENCES course(crs_code,faculty_id),
  FOREIGN KEY (group_id) REFERENCES group_std(group_id),
  FOREIGN KEY (Instructor_id) REFERENCES Instructor(Instructor_id),
  FOREIGN KEY (Period_id) REFERENCES Table_period(Period_id)
);