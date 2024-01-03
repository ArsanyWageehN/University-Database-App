<?php

require_once '../includes/DbOperations.php';

$result = array();


$db = new DbOperations();

$result = $db->getCourses();

if ($result) {
    $result['error'] = true;
    $result['message'] = "Nothing here";
}  
