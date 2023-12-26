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
					));
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
					));
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
					));
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

	public function add_Student($gender, $dep_id,
		$std_name, $Nationality, $Religion, $city, $country, $group_id)
	{
		if ($this->isStudentExist($std_name)) {
			return 0;
		} else {
			$stmt = $this->con->prepare(
				"INSERT INTO `student` 
				(`stu_id`, `gender`, `dep_id`, 
			     `stu_gpa`, `std_name`, `Nationality`,
				 `Religion`, `city`, `country`, `group_id`)
				 VALUES (NULL, ?, ?, 0, ?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("sisssssi", $gender, $dep_id,
				$std_name, $Nationality, $Religion, $city, $country, $group_id);

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

}