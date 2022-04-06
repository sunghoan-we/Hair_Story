<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();

$_POST['shop_id'];

$resultBranchList["error"] = FALSE;

$index = 1;

if (isset($_POST['shop_id'])) {

    $shopId = $_POST['shop_id'];

    $branchList = $db->getBranchList($shopId);

    while ($row = $branchList->fetch_assoc()) {

        $resultBranchList[$index]['Branch_ID'] = $row['branch_id'];
        $resultBranchList[$index]['Branch_Name'] = $row['branch_name'];
        $resultBranchList[$index]['Branch_Address'] = $row['address'];
        $resultBranchList[$index]['Branch_Contact'] = $row['contact'];
        $resultBranchList[$index]['Branch_OpenTime'] = $row['open_time'];
        $resultBranchList[$index]['Branch_CloseTime'] = $row['close_time'];
        $resultBranchList[$index]['Branch_RegDate'] = $row['reg_date'];

        $index++;
    }

    echo json_encode($resultBranchList);

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter shop id is missing!";
    echo json_encode($response);
}
