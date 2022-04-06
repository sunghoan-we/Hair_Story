<?php
require_once '../../common/DB_SuperAdmin_Functions.php';
$db = new DB_SuperAdmin_Functions();

$menuList = $db->getMenuList();

$resultMenuList["error"] = FALSE;

$index = 1;


while ($row = $menuList->fetch_assoc()) {

    $resultMenuList[$index]['Menu_ID'] = $row['menu_id'];
    $resultMenuList[$index]['Menu_Name'] = $row['menu_name'];

    $index ++;
}

echo json_encode($resultMenuList);
