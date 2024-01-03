
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['gender'])
	and isset($_POST['dep_id'])
    and isset($_POST['std_name'])
    and isset($_POST['Nationality'])
    and isset($_POST['Religion'])
    and isset($_POST['city'])
    and isset($_POST['country'])
    and isset($_POST['group_id'])
	)
		{ 

		$db = new DbOperations(); 

		$result = $db->add_Student($_POST['gender']
		,$_POST['dep_id'],$_POST['std_name'],
		$_POST['Nationality'],$_POST['Religion'],
		$_POST['city'],$_POST['country'],$_POST['group_id']
	);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Student added successfully";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "It seems Student already added , please choose a different name";						
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
