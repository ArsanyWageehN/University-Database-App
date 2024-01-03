
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){ 
		$db = new DbOperations(); 

		$result = $db->add_group($_POST['dep_id']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Group added successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}  
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
