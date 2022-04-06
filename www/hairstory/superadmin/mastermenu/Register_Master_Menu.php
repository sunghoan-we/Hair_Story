<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['menu_name'];

if (isset($_POST['menu_name'])) {
 
    // receiving the post params
    $menuName = $_POST['menu_name'];    
 
    // execute query with given input value
    $addMenu = $db->RegisterNewMenu($menuName);
 
    if ($addMenu != false) {        
        $response["error"] = FALSE;        
        
        echo json_encode($response);
    } 
    
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter master menu name is missing!";
    echo json_encode($response);
}
