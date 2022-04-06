<?php
require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
// json response array
$response = array("error" => FALSE);

// $_POST['shopId'] = "1";
// $_POST['branchName'] ="Branch05";
// $_POST['address'] = "test address05";
// $_POST['branchContact'] = "778-003-0004";
// $_POST['openTime'] = "09:00";
// $_POST['closeTime'] = "21:00";


if (isset($_POST['shopId']) && isset($_POST['branchName']) && isset($_POST['address']) 
    && isset($_POST['branchContact']) && isset($_POST['from']) && isset($_POST['to'])) {
 
    // receiving the post params
    $shopId = $_POST['shopId'];
    $branchName = $_POST['branchName'];
    $branchAddress = $_POST['address'];
    $branchContact = $_POST['branchContact'];
    $openTime = $_POST['from'];
    $closeTime = $_POST['to'];
 
    if ($db->isBranchExisted($shopId, $branchName)) {
        // Branch already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "Branch already existed with " . $branchName;
        echo json_encode($response);

    } else {
        // register a branch
        $result = $db->registerBranch($shopId, $branchName, $branchAddress, $branchContact, $openTime, $closeTime);
    
        if ($result) {
            // branch is registered
            $response["error"] = FALSE;
            echo json_encode($response);
        } else {
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}

?>