-- Insert data into Faculty table
INSERT INTO Faculty (faculty_name) VALUES
  ('Engineering'),
  ('Science'),
  ('Arts');

-- Insert data into Departments table
INSERT INTO Departments (Dep_name, faculty_id) VALUES
  ('Computer Science', 1),
  ('Physics', 2),
  ('History', 3);

-- Insert data into course table
INSERT INTO course (crs_name, crs_desc, credits, faculty_id) VALUES
  ('Introduction to Programming', 'Basic programming concepts', 3, 1),
  ('Mechanics', 'Classical mechanics principles', 4, 2),
  ('World History Survey', 'Overview of world history', 3, 3);

-- Insert data into DepCourse table
INSERT INTO DepCourse (faculty_id, dep_id, crs_code) VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 3);

-- Insert data into group_std table
INSERT INTO group_std (dep_id) VALUES
  (1),
  (2),
  (3);

-- Insert data into Instructor table
INSERT INTO Instructor (Job_Tittle, Salary, Full_Name) VALUES
  ('Professor', 80000, 'John Doe'),
  ('Assistant Professor', 60000, 'Jane Smith'),
  ('Lecturer', 50000, 'Bob Johnson');

-- Insert data into Table_period table
INSERT INTO Table_period (From_T, To_T) VALUES
  ('08:00:00', '09:30:00'),
  ('10:00:00', '11:30:00'),
  ('13:00:00', '14:30:00');

-- Insert data into student table
INSERT INTO student (gender, dep_id, stu_gpa, std_name, Nationality, Religion, city, country, group_id) VALUES
  ('Male', 1, 3.5, 'Tom Anderson', 'US', 'Christianity', 'New York', 'USA', 1),
  ('Female', 2, 3.8, 'Emily Johnson', 'UK', 'Atheist', 'London', 'UK', 2),
  ('Male', 3, 3.2, 'Daniel Lee', 'Canada', 'Buddhism', 'Toronto', 'Canada', 3);

-- Insert data into phone table
INSERT INTO phone (stu_id, Phone) VALUES
  (1, '+1 123-456-7890'),
  (2, '+44 20 1234 5678'),
  (3, '+1 416-555-1234');

-- Insert data into ClassRoom table
INSERT INTO ClassRoom (class_type, class_number, faculty_id) VALUES
  ('Lecture Hall', 101, 1),
  ('Lab', 201, 2),
  ('Classroom', 301, 3);

-- Insert data into enrollment table
INSERT INTO enrollment (faculty_id, mark, stu_id, crs_code) VALUES
  (1, 85, 1, 1),
  (2, 90, 2, 2),
  (3, 78, 3, 3);

-- Insert data into Lecture table
INSERT INTO Lecture (Lec_id, crs_code, group_id, class_number, faculty_id, Instructor_id, Period_id) VALUES
  (1, 1, 1, 101, 1, 1, 1),
  (2, 2, 2, 201, 2, 2, 2),
  (3, 3, 3, 301, 3, 3, 3);
