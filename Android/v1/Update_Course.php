<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if( isset($_POST['credits'])
    and isset($_POST['crs_desc'])
	and isset($_POST['crs_name'])
	and isset($_POST['crs_code'])){
		$db = new DbOperations(); 
 
		$result = $db->update_Course($_POST['crs_name'],$_POST['crs_desc'],$_POST['credits'],$_POST['crs_code']);
 
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Course Updated successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "This code does not contain a Course";						
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);
?>