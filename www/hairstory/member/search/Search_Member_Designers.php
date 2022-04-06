<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();

$_POST['branch_id'];

$resultDesignerList["error"] = FALSE;

$index = 1;

if (isset($_POST['branch_id'])) {

    $branchId = $_POST['branch_id'];

    $designerList = $db->getDesignerList($branchId);

    while ($row = $designerList->fetch_assoc()) {

        $resultDesignerList[$index]['Designer_ID'] = $row['staff_id'];
        $resultDesignerList[$index]['Designer_FName'] = $row['f_name'];
        $resultDesignerList[$index]['Designer_LName'] = $row['l_name'];
        $resultDesignerList[$index]['Designer_Contact'] = $row['phone_num'];
        $resultDesignerList[$index]['Designer_Email'] = $row['email'];
        $resultDesignerList[$index]['Designer_DOB'] = $row['date_of_birth'];
        $resultDesignerList[$index]['Designer_BranchID'] = $row['branch_id'];
        $resultDesignerList[$index]['Designer_RegDate'] = $row['reg_date'];
        $resultDesignerList[$index]['Designer_UpDate'] = $row['update_date'];        

        $index++;
    }

    echo json_encode($resultDesignerList);

} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter branch id is missing!";
    echo json_encode($response);
}
