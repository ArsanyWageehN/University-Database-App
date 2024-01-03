<?php

require_once '../includes/DbOperations.php';

$result = array();


$db = new DbOperations();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if ( isset($_POST['dep_id'])) {
        $result = $db->getGroups($_POST['dep_id']);
    } else {
        $response['error'] = true;
        $response['message'] = "Required fields are missing";
    }
} else {
    $response['error'] = true;
    $response['message'] = "Invalid Request";
}


if ($result) {
    $result['error'] = true;
    $result['message'] = "Nothing here";
}
