<?php

require_once '../includes/DbOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	if (
		isset($_POST['crs_name'])
		and isset($_POST['credits'])
		and isset($_POST['crs_desc'])
	) {

		$db = new DbOperations();

		$result = $db->add_Course($_POST['crs_name'], $_POST['credits'], $_POST['crs_desc']);
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