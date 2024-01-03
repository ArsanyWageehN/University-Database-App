
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['From_T'])
	and isset($_POST['To_T']) )
		{ 

		$db = new DbOperations(); 

		$result = $db->add_period($_POST['From_T'],$_POST['To_T']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Period added successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "It seems Period already added";						
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
