<?php
require_once '../../common/DB_Admin_Functions.php';
$db = new DB_Admin_Functions();
 
// $_POST['adminId'] = 'branchAdmin02';


// json response array
$response = array("error" => FALSE);

if (isset($_POST['adminId'])) {
 
    // receiving the post params
    $adminId = $_POST['adminId'];
    $role = "03";
 
    // get the admin Info
    $branchAdmin = $db->getBranchAdmin($adminId, $role);
    if ($branchAdmin != false) {
        // Branch Admin is found
        $response["error"] = FALSE;
        $response["branchAdmin"]["admin_id"] = $branchAdmin["admin_id"];
        $response["branchAdmin"]["password"] = $branchAdmin["password"];
        $response["branchAdmin"]["f_name"] = $branchAdmin["f_name"];
        $response["branchAdmin"]["l_name"] = $branchAdmin["l_name"];
        $response["branchAdmin"]["role"] = $branchAdmin["role"];
        $response["branchAdmin"]["phone_num"] = $branchAdmin["phone_num"];
        $response["branchAdmin"]["email"] = $branchAdmin["email"];
        $response["branchAdmin"]["date_of_birth"] = $branchAdmin["date_of_birth"];
        $response["branchAdmin"]["reg_date"] = $branchAdmin["reg_date"];
        $response["branchAdmin"]["update_date"] = $branchAdmin["update_date"];
        $response["branchAdmin"]["branch_id"] = $branchAdmin["branch_id"];
        $response["branchAdmin"]["branch_name"] = $branchAdmin["branch_name"];
        echo json_encode($response);
    } else {
        // Branch Admin is not found
        $response["error"] = TRUE;
        $response["error_msg"] = "No Branch Admin!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters adminId or role is missing!";
    echo json_encode($response);
}

?>