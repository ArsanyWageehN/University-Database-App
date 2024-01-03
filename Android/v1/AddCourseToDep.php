
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){ 
		$db = new DbOperations(); 

		$result = $db->add_course_TO_dep($_POST['dep_id'],$_POST['crs_code']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Course added to department successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}  
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
