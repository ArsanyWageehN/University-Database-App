<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['class_type'])and isset($_POST['faculty_id']))
		{ 

		$db = new DbOperations(); 

		$result = $db->add_ClassRoom($_POST['class_type'],$_POST['faculty_id']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "ClassRoom added successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "Wrong ID";						
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
?>
