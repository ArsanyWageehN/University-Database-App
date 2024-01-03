<?php

require_once '../includes/DbOperations.php';

$result = array();


$db = new DbOperations();

$result = $db->getInstructors();

if ($result) {
    $result['error'] = true;
    $result['message'] = "Nothing here";
}  
