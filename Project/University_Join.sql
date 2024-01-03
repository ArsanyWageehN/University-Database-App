SELECT faculty.faculty_name, departments.Dep_name, course.crs_name
FROM faculty
INNER JOIN departments ON faculty.faculty_id = departments.dep_id
INNER JOIN course ON departments.dep_id = course.crs_code;


SELECT instructor.Full_Name,instructor.Job_Tittle,course.crs_name,Lecture.Lec_id 
FROM instructor
INNER JOIN course ON instructor.Instructor_id=course.crs_code
INNER JOIN lecture ON course.crs_code=Lecture.Lec_id;


SELECT stu_id, std_name,stu_gpa from student WHERE stu_gpa>3.4;


UPDATE student 
SET stu_gpa=3.7 
WHERE stu_id=2;

SELECT *FROM student WHERE stu_id=2;
SELECT * FROM university.enrollment;
DELETE FROM enrollment WHERE stu_id=3;
DELETE FROM phone WHERE stu_id=3;
DELETE FROM student WHERE stu_id=3;
