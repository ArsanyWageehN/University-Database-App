<?php

require_once '../includes/DbOperations.php';

$result = array();


$db = new DbOperations();

$result = $db->getStudents();

if ($result) {
    $result['error'] = true;
    $result['message'] = "Nothing here";
}  
