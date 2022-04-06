<?php
require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
// json response array
$response = array("error" => FALSE);

//$_POST['adminId'] = "branchAdmin01";

if (isset($_POST['adminId'])) {
 
    // receiving the post params
    $adminId = $_POST['adminId'];
 
    // delete a branch Admin
    $result = $db->deleteBranchAdmin($adminId);

    if ($result) {
        // branch Admin is deleted
        $response["error"] = FALSE;
        echo json_encode($response);
    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknown error occurred in delete!";
        echo json_encode($response);
    }

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}

?>