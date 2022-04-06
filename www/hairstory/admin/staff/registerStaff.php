<?php
require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
// json response array
$response = array("error" => FALSE);


// $_POST['fName'] = "Staff03";
// $_POST['lName'] = "LName003";
// $_POST['gender'] = "M";
// $_POST['phoneNum']="123-456-789";
// $_POST['email'] ="mail@test.com";
// $_POST['dateOfBirth']="2013/01/01";
// $_POST['branchId']="2";

if (isset($_POST['fName']) && isset($_POST['lName']) && isset($_POST['gender']) && isset($_POST['phoneNum']) 
    && isset($_POST['email']) && isset($_POST['dateOfBirth']) && isset($_POST['branchId'])) {
 
    // receiving the post params
    $fName = $_POST['fName'];
    $lName = $_POST['lName'];
    $gender = $_POST['gender'];
    $phoneNum = $_POST['phoneNum'];
    $email = $_POST['email'];
    $dateOfBirth = $_POST['dateOfBirth'];
    $branchId = $_POST['branchId'];

 
    // register a branch Admin
    $result = $db->registerStaff($fName, $lName, $gender, $phoneNum, $email, $dateOfBirth, $branchId);

    if ($result) {
        // branch Admin is registered
        $response["error"] = FALSE;
        echo json_encode($response);
    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknown error occurred in registration!";
        echo json_encode($response);
    }

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}

?>