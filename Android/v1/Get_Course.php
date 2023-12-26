<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['crs_code'])){
		$db = new DbOperations(); 

		if($db->getCoursBycode($_POST['crs_code'])){
			$user = $db->getCoursBycode($_POST['crs_code']);
			$response['error'] = false; 
			$response['crs_code'] = $user['crs_code'];
			$response['credits'] = $user['credits'];
			$response['crs_desc'] = $user['crs_desc'];
			$response['crs_name'] = $user['crs_name'];
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