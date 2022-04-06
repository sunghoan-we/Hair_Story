<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();

// json response array
$response = array("error" => FALSE);

$_POST['member_ID'];
$_POST['member_PW'];
$_POST['member_Contact'];
$_POST['member_Email'];

if (isset($_POST['member_ID'])) {

    // receiving the post params
    $memberID = $_POST['member_ID'];
    $memberPW = $_POST['member_PW'];
    $memberContact = $_POST['member_Contact'];
    $memberEmail = $_POST['member_Email'];

    // get the user by email and password
    $editMember = $db->EditCurrentMember($memberID, $memberPW, $memberContact, $memberEmail);

    if ($editMember != false) {
        $response["error"] = FALSE;

        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter (shop admin info) are missing!";
    echo json_encode($response);
}
