<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if( isset($_POST['From_T']) 
	and isset($_POST['To_T']) 
	and isset($_POST['Period_id'])  ){
		$db = new DbOperations(); 
 
		$result = $db->update_Period($_POST['From_T'],$_POST['To_T'],$_POST['Period_id']);
 
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Period Updated successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "This ID does not contain a Period";						
		}else if($result == 3)
		{
			$response['error'] = true; 
			$response['message'] = "It seems Period already added";	
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);
?>