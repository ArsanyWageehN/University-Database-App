<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['Instructor_id'])){
		$db = new DbOperations(); 

		if($db->getLecturerByID($_POST['Instructor_id'])){
			$user = $db->getLecturerByID($_POST['Instructor_id']);
			$response['error'] = false; 
			$response['Instructor_id'] = $user['Instructor_id'];
			$response['Job_Tittle'] = $user['Job_Tittle'];
			$response['Salary'] = $user['Salary'];
			$response['Full_Name'] = $user['Full_Name'];
		}else{
			$response['error'] = true; 
			$response['message'] = "This ID does not contain a lecturer";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);
?>