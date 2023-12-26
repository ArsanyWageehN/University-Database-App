<?php

require_once '../includes/DbOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	if (
		isset($_POST['crs_name'])
		and isset($_POST['dep_id'])
		and isset($_POST['crs_code'])
	) {

		$db = new DbOperations();

		$result = $db->Connect_Course_with_dep($_POST['dep_id'], $_POST['crs_code']);
		if ($result == 1) {
			$response['error'] = false;
			$response['message'] = "Course added successfully";
		} elseif ($result == 2) {
			$response['error'] = true;
			$response['message'] = "Some error occurred please try again";
		} elseif ($result == 0) {
			$response['error'] = true;
			$response['message'] = "It seems Course already added , please choose a different name";
		}

	} else {
		$response['error'] = true;
		$response['message'] = "Required fields are missing";
	}
} else {
	$response['error'] = true;
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
?>