<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();

// json response array
$response = array("error" => FALSE);

$_POST['member_ID'];
$_POST['member_PW'];
$_POST['member_FName'];
$_POST['member_LName'];
$_POST['member_DOB'];
$_POST['member_Contact'];
$_POST['member_Email'];

if (isset($_POST['member_ID'])) {

    // receiving the post params
    $memberID = $_POST['member_ID'];
    $memberPW = $_POST['member_PW'];
    $memberFName = $_POST['member_FName'];
    $memberLName = $_POST['member_LName'];
    $memberDOB = $_POST['member_DOB'];
    $memberContact = $_POST['member_Contact'];
    $memberEmail = $_POST['member_Email'];

    // execute query with given input value
    $addMember = $db->RegisterNewMember(
        $memberID,
        $memberPW,
        $memberFName,
        $memberLName,
        $memberDOB,
        $memberContact,
        $memberEmail
    );

    if ($addMember != false) {
        $response["error"] = FALSE;

        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (member info) are missing!";
    echo json_encode($response);
}
