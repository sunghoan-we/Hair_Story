<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['menu_id'];

if (isset($_POST['menu_id'])) {
 
    // receiving the post params
    $menuId = $_POST['menu_id'];     
    
    $deleteMenu = $db->DeleteCurrentMenu($menuId);
 
    if ($deleteMenu != false) {        
        $response["error"] = FALSE;        
        
        echo json_encode($response);
    } 
    
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter master menu name is missing!";
    echo json_encode($response);
}
