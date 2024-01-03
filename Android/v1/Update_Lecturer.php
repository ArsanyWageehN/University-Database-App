<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if( isset($_POST['Salary'])
    and isset($_POST['Job_Tittle'])
	and isset($_POST['Full_Name'])
	and isset($_POST['Instructor_id'])){
		$db = new DbOperations(); 
 
		$result = $db->update_Lecturer($_POST['Full_Name'],$_POST['Job_Tittle'],$_POST['Salary'],$_POST['Instructor_id']);
 
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Lecturer Updated successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
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