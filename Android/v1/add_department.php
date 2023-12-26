<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['Dep_name'])and isset($_POST['faculty_id']))
		{ 

		$db = new DbOperations(); 

		$result = $db->add_department($_POST['Dep_name'],$_POST['faculty_id']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Department added successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "It seems Department already added , please choose a different name";						
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
