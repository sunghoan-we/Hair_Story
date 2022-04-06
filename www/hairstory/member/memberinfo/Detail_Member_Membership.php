<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();
 
// json response array
$response = array("error" => FALSE);

$_POST['member_ID'];

if (isset($_POST['member_ID'])) {
 
    // receiving the post params
    $memberID = $_POST['member_ID'];
    
 
    // getting member information by calling function inside of Member_Functions
    $DetailMember = $db->getMemberDetailInfo($memberID);
 
    if ($DetailMember != false) {
        
        $response["error"] = FALSE;
        
        $response["memberId"] = $DetailMember["customer_id"];
        $response["memberFName"] = $DetailMember["f_name"];
        $response["memberLName"] = $DetailMember["l_name"];        
        $response["memberPNumber"] = $DetailMember["phone_num"];        
        $response["memberEmail"] = $DetailMember["email"];
        $response["memberDOB"] = $DetailMember["date_of_birth"];
        $response["memberRegDate"] = $DetailMember["reg_date"];        
        
        echo json_encode($response);
    } 

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter member id is missing!";
    echo json_encode($response);
}
