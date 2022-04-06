<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();

// json response array
$response = array("error" => FALSE);

$_POST['shopAdmin_ID'];
$_POST['shopAdmin_PW'];
$_POST['shopAdmin_Contact'];
$_POST['shopAdmin_Email'];


if (isset($_POST['shopAdmin_ID'])) {

    // receiving the post params
    $shopAdminID = $_POST['shopAdmin_ID'];
    $shopAdminPW = $_POST['shopAdmin_PW'];
    $shopAdminContact = $_POST['shopAdmin_Contact'];
    $shopAdminEmail = $_POST['shopAdmin_Email'];

    // get the user by email and password
    $editShopAdmin = $db->EditCurrentShopAdmin($shopAdminID, $shopAdminPW, $shopAdminContact, $shopAdminEmail);

    if ($editShopAdmin != false) {
        $response["error"] = FALSE;

        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter (shop admin info) are missing!";
    echo json_encode($response);
}
