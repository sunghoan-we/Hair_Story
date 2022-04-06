<?php
require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
// json response array
$response = array("error" => FALSE);

//$_POST['staffId'] = "2";

if (isset($_POST['staffId'])) {
 
    // receiving the post params
    $staffId = $_POST['staffId'];
 
    // delete a Staff
    $result = $db->deleteStaff($staffId);

    if ($result) {
        // Staff is deleted
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