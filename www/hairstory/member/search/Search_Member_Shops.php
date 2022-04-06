<?php
require_once '../../common/DB_Member_Functions.php';
$db = new DB_Member_Functions();

$shopList = $db->getShopList();

$resultShopList["error"] = FALSE;

$index = 1;

while ($row = $shopList->fetch_assoc()) {

    $resultShopList[$index]['Shop_ID'] = $row['shop_id'];
    $resultShopList[$index]['Shop_Name'] = $row['shop_name'];
    $resultShopList[$index]['Shop_RegDate'] = $row['reg_date'];    

    $index++;
}

echo json_encode($resultShopList);
?>