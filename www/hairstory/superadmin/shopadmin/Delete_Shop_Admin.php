<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['shop_admin_id'];

if (isset($_POST['shop_admin_id'])) {
 
    // receiving the post params
    $shopAdminId = $_POST['shop_admin_id']; 
    
    $deleteShopAdmin = $db->DeleteCurrentShopAdmin($shopAdminId);
 
    if ($deleteShopAdmin != false) {        
        $response["error"] = FALSE;        
        
        echo json_encode($response);
    } 
    
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter master menu name is missing!";
    echo json_encode($response);
}
