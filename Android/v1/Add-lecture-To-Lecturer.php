
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){ 
		$db = new DbOperations(); 

		$result = $db->Add_lecture_To_Lecturer($_POST['faculty_id']
		,$_POST['crs_code']
		,$_POST['Period_id']
		,$_POST['class_number']
		,$_POST['group_id']
		,$_POST['Instructor_id']
	);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Lecture registered successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		} elseif($result == 3){
			$response['error'] = true; 
			$response['message'] = "This ID does not contain a lecturer";						
		}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
