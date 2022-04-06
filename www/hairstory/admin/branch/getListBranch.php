<?php

require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();

// $_POST['shopId'] = 1;

// json response array
$response = array("error" => FALSE);

if (isset($_POST['shopId'])) {
 
    // receiving the post params
    $shopId = $_POST['shopId'];
 
    // get the branchs
    $listBranch = $db->getListBranch($shopId);
    if ($listBranch != false) {
        // Branchs is found
        $response["error"] = FALSE;
        $index =0;
        while($row = $listBranch->fetch_assoc()) {
            $response["branch"][$index]["branch_id"] = $row["branch_id"];
            $response["branch"][$index]["branch_name"] = $row["branch_name"];
            $response["branch"][$index]["address"] = $row["address"];
            $response["branch"][$index]["contact"] = $row["contact"];
            $response["branch"][$index]["open_time"] = $row["open_time"];
            $response["branch"][$index]["close_time"] = $row["close_time"];
            $response["branch"][$index]["shop_id"] = $row["shop_id"];
            $response["branch"][$index]["reg_date"] = $row["reg_date"];
            $index++;
        }
        echo json_encode($response);
    } else {
        // Branch is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "No Branch!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters shop Id is missing!";
    echo json_encode($response);
}

?>