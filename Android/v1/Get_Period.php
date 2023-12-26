<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['Period_id'])){
		$db = new DbOperations(); 

		if($db->getPeriodByID($_POST['Period_id'])){
			$user = $db->getPeriodByID($_POST['Period_id']);
			$response['error'] = false; 
			$response['Period_id'] = $user['Period_id'];
			$response['From_T'] = $user['From_T'];
			$response['To_T'] = $user['To_T']; 
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