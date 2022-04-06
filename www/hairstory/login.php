<?php

require_once 'common/DB_Common_Functions.php';
$db = new DB_Common_Functions();

// json response array
$response = array("error" => FALSE);
// $_POST['userId']="customer01";
// $_POST['password']="1111";
// $_POST['role']="";

if (isset($_POST['userId']) && isset($_POST['password'])) {

    // receiving the post params
    $userId = $_POST['userId'];
    $password = $_POST['password'];
    $role = $_POST['role']??null;

    // get the user by email and password
    $user = $db->getLoginInfo($userId, $password, $role);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        switch ($role) {
            case "01":
                $response["user"]["userId"] = $user["admin_id"];
                $response["user"]["role"] = $user["role"];
                break;
            case "02":
                $response["user"]["userId"] = $user["admin_id"];
                $response["user"]["shop_id"] = $user["shop_id"];
                $response["user"]["shop_name"] = $user["shop_name"];
                $response["user"]["role"] = $user["role"];
                break;
            case "03":
                $response["user"]["userId"] = $user["admin_id"];
                $response["user"]["shop_id"] = $user["shop_id"];
                $response["user"]["shop_name"] = $user["shop_name"];
                $response["user"]["branch_id"] = $user["branch_id"];
                $response["user"]["branch_name"] = $user["branch_name"];
                $response["user"]["role"] = $user["role"];
                break;
            default:
                $response["user"]["userId"] = $user["customer_id"];
                break;
        }
        $response["user"]["f_name"] = $user["f_name"];
        $response["user"]["l_name"] = $user["l_name"];
        $response["user"]["phone_num"] = $user["phone_num"];
        $response["user"]["email"] = $user["email"];
        $response["user"]["date_of_birth"] = $user["date_of_birth"];
        $response["user"]["reg_date"] = $user["reg_date"];
        $response["user"]["update_date"] = $user["update_date"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters userId or password is missing!";
    echo json_encode($response);
}

?>

