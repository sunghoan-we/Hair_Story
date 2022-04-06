<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();

// json response array
$response = array("error" => FALSE);

$_POST['shopAdmin_ID'];
$_POST['shopAdmin_PW'];
$_POST['shopAdmin_FName'];
$_POST['shopAdmin_LName'];
$_POST['shopAdmin_DOB'];
$_POST['shopAdmin_Contact'];
$_POST['shopAdmin_Email'];

if (isset($_POST['shopAdmin_ID'])) {

    // receiving the post params
    $shopAdminID = $_POST['shopAdmin_ID'];
    $shopAdminPW = $_POST['shopAdmin_PW'];
    $shopAdminFName = $_POST['shopAdmin_FName'];
    $shopAdminLName = $_POST['shopAdmin_LName'];
    $shopAdminDOB = $_POST['shopAdmin_DOB'];
    $shopAdminContact = $_POST['shopAdmin_Contact'];
    $shopAdminEmail = $_POST['shopAdmin_Email'];    


    // execute query with given input value
    $addShopAdmin = $db->RegisterNewShopAdmin(
        $shopAdminID,
        $shopAdminPW,
        $shopAdminFName,
        $shopAdminLName,
        $shopAdminDOB,
        $shopAdminContact,
        $shopAdminEmail
    );

    if ($addShopAdmin != false) {
        $response["error"] = FALSE;

        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (shop admin info) are missing!";
    echo json_encode($response);
}
