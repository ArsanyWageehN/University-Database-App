
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['Job_Tittle'])
	and isset($_POST['Salary'])
    and isset($_POST['Full_Name']))
		{ 

		$db = new DbOperations(); 

		$result = $db->add_Lecturer($_POST['Full_Name'],$_POST['Job_Tittle'],$_POST['Salary']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Lecturer added successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "It seems Lecturer already added , please choose a different name";						
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
