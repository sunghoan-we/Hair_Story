<?php

require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
// $_POST['branchId'] = 1;
// json response array
$response = array("error" => FALSE);

if (isset($_POST['branchId'])) {
 
    // receiving the post params
    $branchId = $_POST['branchId'];
 
    // get the branch
    $branch = $db->getBranch($branchId);
    if ($branch != false) {
        // Branch is found
        $response["error"] = FALSE;
        $response["branch"]["branch_id"] = $branch["branch_id"];
        $response["branch"]["branch_name"] = $branch["branch_name"];
        $response["branch"]["address"] = $branch["address"];
        $response["branch"]["contact"] = $branch["contact"];
        $response["branch"]["open_time"] = $branch["open_time"];
        $response["branch"]["close_time"] = $branch["close_time"];
        $response["branch"]["shop_id"] = $branch["shop_id"];
        $response["branch"]["reg_date"] = $branch["reg_date"];
        echo json_encode($response);
    } else {
        // Branch is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "No Branch!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters userId or role is missing!";
    echo json_encode($response);
}

?>