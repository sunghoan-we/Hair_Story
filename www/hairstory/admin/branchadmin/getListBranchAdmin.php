<?php

require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
// $_POST['shopId'] = "1";

// json response array
$response = array("error" => FALSE);

if (isset($_POST['shopId'])) {
 
    // receiving the post params
    $shopId = $_POST['shopId'];
 
    // get the branch admins
    $listBranchAdmin = $db->getListBranchAdmin($shopId);
    if ($listBranchAdmin != false) {
        // Branch admins is found
        $response["error"] = FALSE;
        $index =0;
        while($row = $listBranchAdmin->fetch_assoc()) {
            $response["branch_admin"][$index]["admin_id"] = $row["admin_id"];
            $response["branch_admin"][$index]["f_name"] = $row["f_name"];
            $response["branch_admin"][$index]["l_name"] = $row["l_name"];            
            $response["branch_admin"][$index]["branch_id"] = $row["branch_id"];
            $response["branch_admin"][$index]["branch_name"] = $row["branch_name"];
            $index++;
        }
        echo json_encode($response);
    } else {
        // BranchAdmin is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "No Branch Admin!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters shop Id is missing!";
    echo json_encode($response);
}

?>