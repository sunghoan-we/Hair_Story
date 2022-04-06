<?php
require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
// $_POST['shopId'] = "1";
// $_POST['fromDate'] = '2021/12/01';
// $_POST['toDate'] = '2021/12/01';

// json response array
$response = array("error" => FALSE);

if (isset($_POST['shopId'])) {
 
    // receiving the post params
    $shopId = $_POST['shopId'];
    $fromDate = isset($_POST['fromDate']) ? $_POST['fromDate']." 00:00:00" : null;
    $toDate = isset($_POST['toDate']) ? $_POST['toDate']." 23:59:59" : null;

    // get the ReportData
    $reportData = $db->getReportData($shopId, $fromDate, $toDate);
    if ($reportData != false) {
        // ReportData is found
        $response["error"] = FALSE;
        $index =0;
        while($row = $reportData->fetch_assoc()) {
            $response["report"][$index]["branch_name"] = $row["branch_name"];
            $response["report"][$index]["amount"] = $row["amount"];
            $index++;
        }
        echo json_encode($response);
    } else {
        // ReportData is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "No Data!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}
?>