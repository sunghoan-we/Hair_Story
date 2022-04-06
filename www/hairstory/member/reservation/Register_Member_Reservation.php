<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();

// json response array
$response = array("error" => FALSE);

$_POST['reservation_rDate'];
$_POST['reservation_sTime'];
$_POST['reservation_eTime'];
$_POST['reservation_sId'];
$_POST['reservation_cId'];

if (isset($_POST['reservation_cId'])) {

    // receiving the post params
    $reservationRDate = $_POST['reservation_rDate'];
    $reservationSTime = $_POST['reservation_sTime'];
    $reservationETime = $_POST['reservation_eTime'];
    $reservationSId = $_POST['reservation_sId'];
    $reservationcId = $_POST['reservation_cId'];    

    // execute query with given input value
    $addReservation = $db->RegisterNewReservation(
        $reservationRDate,
        $reservationSTime,
        $reservationETime,
        $reservationSId,
        $reservationcId        
    );

    if ($addReservation != false) {
        $response["error"] = FALSE;

        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (reservation info) are missing!";
    echo json_encode($response);
}
