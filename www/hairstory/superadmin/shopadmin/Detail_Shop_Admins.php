<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['shop_admin_id'];

if (isset($_POST['shop_admin_id'])) {
 
    // receiving the post params
    $shopAdminId = $_POST['shop_admin_id'];
    
 
    // getting shop admin information by calling function inside of SuperAdmin_Functions
    $shopAdmin = $db->getShopAdminDetailInfo($shopAdminId);
 
    if ($shopAdmin != false) {
        
        $response["error"] = FALSE;
        
        $response["shopAdminId"] = $shopAdmin["admin_id"];
        $response["shopAdminFName"] = $shopAdmin["f_name"];
        $response["shopAdminLName"] = $shopAdmin["l_name"];
        $response["shopAdminEmail"] = $shopAdmin["email"];
        $response["shopAdminPNumber"] = $shopAdmin["phone_num"];        
        $response["shopAdminRegDate"] = $shopAdmin["reg_date"];        
        
        echo json_encode($response);
    } 

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter shop admin id is missing!";
    echo json_encode($response);
}
