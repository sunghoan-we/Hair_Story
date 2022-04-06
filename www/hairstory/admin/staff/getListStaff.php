<?php

require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
// $_POST['shopId'] = "1";
// $_POST['branchId'] = "1";
// $_POST['staffName'] = "F_staff";
//$_POST['branchName'] = "Branch01";

// json response array
$response = array("error" => FALSE);

if (isset($_POST['shopId'])) {
 
    // receiving the post params
    $shopId = $_POST['shopId'];
    $branchId = $_POST['branchId']??null;
    $staffName = $_POST['staffName']??null;
    $branchName = $_POST['branchName']??null;

    // get the Staff
    $listStaff = $db->getListStaff($shopId, $branchId, $staffName, $branchName);
    if ($listStaff != false) {
        // Staff is found
        $response["error"] = FALSE;
        $index =0;
        while($row = $listStaff->fetch_assoc()) {
            $response["staff"][$index]["staff_id"] = $row["staff_id"];
            $response["staff"][$index]["f_name"] = $row["f_name"];
            $response["staff"][$index]["l_name"] = $row["l_name"];            
            $response["staff"][$index]["gender"] = $row["gender"];
            $response["staff"][$index]["phone_num"] = $row["phone_num"];
            $response["staff"][$index]["email"] = $row["email"];
            $response["staff"][$index]["date_of_birth"] = $row["date_of_birth"];
            $response["staff"][$index]["branch_id"] = $row["branch_id"];
            $response["staff"][$index]["branch_name"] = $row["branch_name"];
            $index++;
        }
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
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}

?>