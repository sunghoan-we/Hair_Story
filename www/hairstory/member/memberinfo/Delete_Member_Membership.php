<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['member_ID'];

if (isset($_POST['member_ID'])) {
 
    // receiving the post params
    $memberID = $_POST['member_ID']; 
    
    $deleteMember = $db->DeleteCurrentMember($memberID);
 
    if ($deleteMember != false) {        
        $response["error"] = FALSE;        
        
        echo json_encode($response);
    } 
    
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter (member id) is missing!";
    echo json_encode($response);
}
