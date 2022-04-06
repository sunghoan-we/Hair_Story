<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['menu_name'];

if (isset($_POST['menu_name'])) {
 
    // receiving the post params
    $menuName = $_POST['menu_name'];
    
 
    // get the user by email and password
    $menu = $db->getMenuDetailInfo($menuName);
 
    if ($menu != false) {
        // use is found
        $response["error"] = FALSE;
        
        $response["menuId"] = $menu["menu_id"];
        $response["menuName"] = $menu["menu_name"];
        
        echo json_encode($response);
    } 
    
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter master menu id is missing!";
    echo json_encode($response);
}
