<?php

class DbOperations
{

	private $con;

	function __construct()
	{

		require_once dirname(__FILE__) . '/DbConnect.php';

		$db = new DbConnect();

		$this->con = $db->connect();

	}

	public function getFaculties()
	{
		$sql = "select * from faculty";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['faculty'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['faculty'], array(
						'faculty_id' => $row['faculty_id'],
						'faculty_name' => $row['faculty_name']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function getDepartments($id)
	{
		$sql = "SELECT * FROM Departments WHERE faculty_id = $id";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['Departments'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['Departments'], array(
						'faculty_id' => $row['faculty_id'],
						'Dep_name' => $row['Dep_name'],
						'dep_id' => $row['dep_id']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function getClasses($id)
	{
		$sql = "SELECT * FROM classroom WHERE faculty_id = $id";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['classroom'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['classroom'], array(
						'class_type' => $row['class_type'],
						'class_number' => $row['class_number'],
						'faculty_id' => $row['faculty_id']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function getPeriods()
	{
		$sql = "SELECT * FROM table_period";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['table_period'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['table_period'], array(
						'From_T' => $row['From_T'],
						'Period_id' => $row['Period_id'],
						'To_T' => $row['To_T']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function getCourses()
	{
		$sql = "SELECT * FROM course";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['course'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['course'], array(
						'crs_code' => $row['crs_code'],
						'crs_name' => $row['crs_name'],
						'credits' => $row['credits'],
						'crs_desc' => $row['crs_desc']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function getInstructors()
	{
		$sql = "SELECT * FROM instructor";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['instructor'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['instructor'], array(
						'Job_Tittle' => $row['Job_Tittle'],
						'Instructor_id' => $row['Instructor_id'],
						'Salary' => $row['Salary'],
						'Full_Name' => $row['Full_Name']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function getStudents()
	{
		$sql = "SELECT * FROM student";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['student'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['student'], array(
						'stu_id' => $row['stu_id'],
						'group_id' => $row['group_id'],
						'stu_gpa' => $row['stu_gpa'],
						'gender' => $row['gender'],
						'std_name' => $row['std_name'],
						'Nationality' => $row['Nationality'],
						'city' => $row['city'],
						'country' => $row['country'],
						'dep_id' => $row['dep_id'],
						'Religion' => $row['Religion']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}


	public function getGroups($id)
	{
		$sql = "SELECT * FROM group_std WHERE dep_id = $id";
		if (!$this->con->query($sql)) {
			return "Error in connecting to Database.";
		} else {
			$result = $this->con->query($sql);
			if ($result->num_rows > 0) {
				$return_arr['group_std'] = array();
				while ($row = $result->fetch_array()) {
					array_push($return_arr['group_std'], array(
						'group_id' => $row['group_id'],
						'dep_id' => $row['dep_id']
					)
					);
				}
				echo json_encode($return_arr);
			}
		}
	}

	public function add_faculty($name)
	{
		if ($this->isfacultyExist($name)) {
			return 0;
		} else {
			$stmt = $this->con->prepare("INSERT INTO `Faculty` (`faculty_id`, `faculty_name`) VALUES (NULL, ?);");
			$stmt->bind_param("s", $name);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function add_group($id)
	{
		if ($this->getDepartmetByID($id)) {
			$stmt = $this->con->prepare("INSERT INTO `group_std` (`dep_id`, `group_id`) VALUES (?, NULL);");
			$stmt->bind_param("i", $id);
			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}

	}

	public function Add_lecture_To_Lecturer($faculty_id, $crs_code, $Period_id, $class_number, $group_id, $Instructor_id)
	{
		if (!$this->getLecturerByID($Instructor_id)) {
			return 3;
		} else {
			if (
				$this->getPeriodByID($Period_id) 
				&& $this->getCoursBycode($crs_code) 
			) {
				$stmt = $this->con->prepare("INSERT INTO `lecture` (`Lec_id`, `crs_code`, `group_id`, `class_number`, `faculty_id`, `Instructor_id`, `Period_id`) VALUES (null, $crs_code, $group_id, $class_number, $faculty_id, $Instructor_id, $Period_id)");
				if ($stmt->execute()) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return 0;
			}
		}

	}

	public function add_course_TO_dep($id, $id2)
	{
		if ($this->getDepartmetByID($id)) {
			$stmt = $this->con->prepare("INSERT INTO `depcourse` (`dep_id`, `crs_code`) VALUES ($id, $id2);");
			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}

	}

	public function add_department($name, $id)
	{
		if ($this->isDepartmentExist($name)) {
			return 0;
		} else {
			$stmt = $this->con->prepare("INSERT INTO `departments` (`Dep_name`, `dep_id`, `faculty_id`) VALUES (?, NULL, ?);");
			$stmt->bind_param("si", $name, $id);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function RegisterCourseFor_Student($id,$code)
	{
		if (!$this->getCoursBycode($code)) {
			return 0;
		} else {
			$stmt = $this->con->prepare("INSERT INTO `enrollment` (`mark`, `stu_id`, `crs_code`) VALUES (0, $id, $code)");
			 
			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function add_ClassRoom($name, $id)
	{
		if ($this->isfacultyExistbyID($id)) {
			$stmt = $this->con->prepare("INSERT INTO `classroom` (`class_type`, `class_number`, `faculty_id`) VALUES (?, NULL, ?);");
			$stmt->bind_param("si", $name, $id);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}
	public function add_Lecturer($name, $jobTitle, $salary)
	{
		if ($this->isLecturerExist($name)) {
			return 0;
		} else {
			$stmt = $this->con->prepare("INSERT INTO `instructor` (`Job_Tittle`, `Instructor_id`, `Salary`, `Full_Name`) VALUES (?, NULL, ?, ?);");
			$stmt->bind_param("sis", $jobTitle, $salary, $name);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function add_Student(
		$gender,
		$dep_id,
		$std_name,
		$Nationality,
		$Religion,
		$city,
		$country,
		$group_id
	) {
		if ($this->isStudentExist($std_name)) {
			return 0;
		} else {
			$stmt = $this->con->prepare(
				"INSERT INTO `student` 
				(`stu_id`, `gender`, `dep_id`, 
			     `stu_gpa`, `std_name`, `Nationality`,
				 `Religion`, `city`, `country`, `group_id`)
				 VALUES (NULL, ?, ?, 0, ?, ?, ?, ?, ?, ?);"
			);
			$stmt->bind_param(
				"sisssssi",
				$gender,
				$dep_id,
				$std_name,
				$Nationality,
				$Religion,
				$city,
				$country,
				$group_id
			);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function add_period($from, $to)
	{
		if ($this->isPeriodExist($from, $to)) {
			return 0;
		} else {
			$stmt = $this->con->prepare("INSERT INTO `table_period` (`From_T`, `Period_id`, `To_T`) VALUES (?, NULL, ?);");
			$stmt->bind_param("ss", $from, $to);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function add_Course($name, $credits, $desc)
	{
		if ($this->isCourseExist($name)) {
			return 0;
		} else {
			$stmt = $this->con->prepare("INSERT INTO `course` (`crs_name`, `crs_code`, `credits`, `crs_desc`) VALUES (?, 1, ?, ?);");
			$stmt->bind_param("sis", $name, $credits, $desc);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public function Connect_Course_with_dep($dep_id, $crs_code)
	{
		$stmt = $this->con->prepare("INSERT INTO `depcourse` (`dep_id`, `crs_code`) VALUES (?, ?)");
		$stmt->bind_param("ii", $dep_id, $crs_code);

		if ($stmt->execute()) {
			return 1;
		} else {
			return 2;
		}
	}

	public function update_Lecturer($name, $jobTitle, $salary, $id)
	{
		if ($this->getLecturerByID($id)) {
			$stmt = $this->con->prepare("UPDATE instructor SET Salary = ? ,Job_Tittle=?,Full_Name=? WHERE Instructor_id = ?;");
			$stmt->bind_param("issi", $salary, $jobTitle, $name, $id);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}


	public function update_Period($from, $to, $id)
	{
		if ($this->getPeriodByID($id)) {
			if (!$this->isPeriodExist($from, $to)) {
				$stmt = $this->con->prepare("UPDATE table_period SET From_T = ? ,To_T=? WHERE Period_id = ?;");
				$stmt->bind_param("ssi", $from, $to, $id);

				if ($stmt->execute()) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return 3;
			}
		} else {
			return 0;
		}
	}

	public function update_Course($crs_name, $crs_desc, $credits, $id)
	{
		if ($this->getCoursBycode($id)) {
			$stmt = $this->con->prepare("UPDATE course SET credits = ? ,crs_desc=?,crs_name=? WHERE crs_code = ?;");
			$stmt->bind_param("issi", $credits, $crs_desc, $crs_name, $id);

			if ($stmt->execute()) {
				return 1;
			} else {
				return 2;
			}
		} else {
			return 0;
		}
	}


	public function getLecturerByID($ID)
	{
		$stmt = $this->con->prepare("SELECT * FROM instructor WHERE Instructor_id = ?");
		$stmt->bind_param("i", $ID);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc();
	}


	public function getDepartmetByID($ID)
	{
		$stmt = $this->con->prepare("SELECT * FROM departments WHERE dep_id = ?");
		$stmt->bind_param("i", $ID);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc();
	}



	public function getPeriodByID($ID)
	{
		$stmt = $this->con->prepare("SELECT * FROM table_period WHERE Period_id = ?");
		$stmt->bind_param("i", $ID);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc();
	}


	public function getCoursBycode($code)
	{
		$stmt = $this->con->prepare("SELECT * FROM course WHERE crs_code = ?");
		$stmt->bind_param("i", $code);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc();
	}

	private function isDepartmentExist($name)
	{
		$stmt = $this->con->prepare("SELECT dep_id FROM departments WHERE Dep_name = ?");
		$stmt->bind_param("s", $name);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	private function isCourseExist($name)
	{
		$stmt = $this->con->prepare("SELECT crs_code FROM course WHERE crs_name = ?");
		$stmt->bind_param("s", $name);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	private function isLecturerExist($name)
	{
		$stmt = $this->con->prepare("SELECT Instructor_id FROM instructor WHERE Full_Name = ?");
		$stmt->bind_param("s", $name);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	private function isStudentExist($name)
	{
		$stmt = $this->con->prepare("SELECT stu_id FROM student WHERE std_name = ?");
		$stmt->bind_param("s", $name);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}


	private function isPeriodExist($from, $to)
	{
		$stmt = $this->con->prepare("SELECT Period_id FROM table_period WHERE From_T = ? And To_T = ?");
		$stmt->bind_param("ss", $from, $to);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	private function isfacultyExist($name)
	{
		$stmt = $this->con->prepare("SELECT faculty_id FROM Faculty WHERE faculty_name = ?");
		$stmt->bind_param("s", $name);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	private function isfacultyExistbyID($id)
	{
		$stmt = $this->con->prepare("SELECT faculty_id FROM Faculty WHERE faculty_id = ?");
		$stmt->bind_param("s", $id);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

}