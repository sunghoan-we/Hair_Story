<?php

require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
//$_POST['staffId'] = '1';

// json response array
$response = array("error" => FALSE);

if (isset($_POST['staffId'])) {
 
    // receiving the post params
    $staffId = $_POST['staffId'];
 
    // get the staff Info
    $staff = $db->getStaff($staffId);
    if ($staff != false) {
        // staff is found
        $response["error"] = FALSE;
        $response["staff"]["staff_id"] = $staff["staff_id"];
        $response["staff"]["f_name"] = $staff["f_name"];
        $response["staff"]["l_name"] = $staff["l_name"];            
        $response["staff"]["gender"] = $staff["gender"];
        $response["staff"]["phone_num"] = $staff["phone_num"];
        $response["staff"]["email"] = $staff["email"];
        $response["staff"]["date_of_birth"] = $staff["date_of_birth"];
        $response["staff"]["reg_date"] = $staff["reg_date"];
        $response["staff"]["branch_id"] = $staff["branch_id"];
        $response["staff"]["branch_name"] = $staff["branch_name"];
        echo json_encode($response);
    } else {
        // Staff is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "No Staff!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters staff Id is missing!";
    echo json_encode($response);
}

?>