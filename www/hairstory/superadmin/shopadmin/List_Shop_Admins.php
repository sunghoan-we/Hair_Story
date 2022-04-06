<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();

$shopAdminList = $db->getShopAdminList();

$resultAdminList["error"] = FALSE;

$index = 1;

while ($row = $shopAdminList->fetch_assoc()) {

    $resultAdminList[$index]['Shop_Admin_ID'] = $row['admin_id'];
    $resultAdminList[$index]['Shop_Admin_fName'] = $row['f_name'];
    $resultAdminList[$index]['Shop_Admin_lName'] = $row['l_name'];
    $resultAdminList[$index]['Shop_Admin_pNumber'] = $row['phone_num'];
    $resultAdminList[$index]['Shop_Admin_email'] = $row['email'];
    $resultAdminList[$index]['Shop_Admin_dob'] = $row['date_of_birth'];
    $resultAdminList[$index]['Shop_Admin_regDate'] = $row['reg_date'];
    $resultAdminList[$index]['Shop_Admin_upDate'] = $row['update_date'];

    $index++;
}

echo json_encode($resultAdminList);
?>