<?php

class DB_SuperAdmin_Functions
{

    private $conn;

    // constructor
    function __construct()
    {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct()
    {
    }

    public function getCurrentDateTime() {
        $DateAndTime = date('Y-m-d h:i:s', time());  
        return $DateAndTime;
    }

    // for listing up all menus from menu table of database
    public function getMenuList()
    {

        $stmt = $this->conn->prepare("SELECT * FROM MASTER_MENU WHERE SHOW_FLAG = '1'");


        if ($stmt->execute()) {
            $menu = $stmt->get_result();
            $stmt->close();

            return $menu;
        } else {
            return NULL;
        }
    }

    // for getting detailed information of master menu -> Detail_Master_Menus.php
    public function getMenuDetailInfo($menuName)
    {

        $stmt = $this->conn->prepare("SELECT * FROM MASTER_MENU WHERE MENU_NAME = ? AND SHOW_FLAG = '1'");
        $stmt->bind_param('s', $menuName);

        if ($stmt->execute()) {
            $menu = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $menu;
        } else {
            return NULL;
        }
    }

    // this function works when super admin does register new menu
    public function RegisterNewMenu($getMenuName)
    {
        $inputMenuName = $getMenuName;
        $stmt = $this->conn->prepare("INSERT INTO MASTER_MENU (MENU_NAME) VALUES (?)");
        $stmt->bind_param('s', $inputMenuName);

        $stmt->execute();
        $stmt->close();
        return $inputMenuName;
    }

    // to delete current menu with given menu id
    public function DeleteCurrentMenu($getMenuId)
    {
        $inputMenuId = $getMenuId;
        $stmt = $this->conn->prepare("UPDATE MASTER_MENU SET SHOW_FLAG='0' WHERE MENU_ID = ?");
        $stmt->bind_param('s', $inputMenuId);
        $stmt->execute();
        $stmt->close();
        return $inputMenuId;
    }

    // to edit current menu with given menu id
    public function EditCurrentMenu($getMenuId, $getMenuName)
    {
        $inputMenuId = $getMenuId;
        $inputMenuName = $getMenuName;
        $stmt = $this->conn->prepare("UPDATE MASTER_MENU SET MENU_NAME = ? WHERE MENU_ID = ?");
        $stmt->bind_param('ss', $inputMenuName, $inputMenuId);
        $stmt->execute();
        $stmt->close();
        return $inputMenuId;
    }


    /* 
    for management of shop admins
     */

    // for listing up all shop admin list from admin table of database
    public function getShopAdminList()
    {

        $roleparam = "02";
        $stmt = $this->conn->prepare("SELECT * FROM ADMIN WHERE role = ? AND SHOW_FLAG = '1'");
        $stmt->bind_param('s', $roleparam);


        if ($stmt->execute()) {
            $user = $stmt->get_result();
            $stmt->close();

            return $user;
        } else {
            return NULL;
        }
    }


    // for getting detailed information of shop admin menu -> Detail_Shop_Admins.php
    public function getShopAdminDetailInfo($getShopAdminID)
    {
        $inputShopAdminID = $getShopAdminID;
        $roleparam = "02";
        $stmt = $this->conn->prepare("SELECT * FROM ADMIN WHERE admin_id = ? AND role = ? AND SHOW_FLAG = '1'");
        $stmt->bind_param('ss', $inputShopAdminID, $roleparam);

        if ($stmt->execute()) {
            $menu = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $menu;
        } else {
            return NULL;
        }
    }

    // this function works when super admin does register new menu
    public function RegisterNewShopAdmin(
        $getShopAdminID,
        $getShopAdminPW,
        $getShopAdminFName,
        $getShopAdminLName,
        $getShopAdminDOB,
        $getShopAdminContact,
        $getShopAdminEmail        
    ) {
        $inputShopAdminID = $getShopAdminID;
        $inputShopAdminPW = $getShopAdminPW;
        $inputShopAdminFName = $getShopAdminFName;
        $inputShopAdminLName = $getShopAdminLName;
        $inputShopAdminDOB = $getShopAdminDOB;
        $inputShopAdminContact = $getShopAdminContact;
        $inputShopAdminEmail = $getShopAdminEmail;
        
        $currentDateTime = $this->getCurrentDateTime();        

        $roleparam = "02";

        $stmt = $this->conn->prepare("INSERT INTO ADMIN (ADMIN_ID, PASSWORD, F_NAME, L_NAME, ROLE, PHONE_NUM, EMAIL, DATE_OF_BIRTH, REG_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param(
            'ssssssssss',
            $inputShopAdminID,
            $inputShopAdminPW,
            $inputShopAdminFName,
            $inputShopAdminLName,
            $roleparam,
            $inputShopAdminContact,
            $inputShopAdminEmail,
            $inputShopAdminDOB,
            $currentDateTime,
            $currentDateTime
        );

        $stmt->execute();
        $stmt->close();
        return $inputShopAdminID;
    }

    // to delete current menu with given menu id
    public function DeleteCurrentShopAdmin($getShopAdminID)
    {
        $inputShopAdminID = $getShopAdminID;
        $stmt = $this->conn->prepare("UPDATE ADMIN SET SHOW_FLAG='0' WHERE ADMIN_ID = ?");
        $stmt->bind_param('s', $inputShopAdminID);
        $stmt->execute();
        $stmt->close();
        return $inputShopAdminID;
    }


    // to edit current menu with given menu id
    public function EditCurrentShopAdmin(
        $getShopAdminID,
        $getShopAdminPW,
        $getShopAdminContact,
        $getShopAdminEmail
    ) {
        $inputShopAdminID = $getShopAdminID;
        $inputShopAdminPW = $getShopAdminPW;
        $inputShopAdminContact = $getShopAdminContact;
        $inputShopAdminEmail = $getShopAdminEmail;

        $currentDateTime = $this->getCurrentDateTime();        

        $stmt = $this->conn->prepare("UPDATE ADMIN SET PHONE_NUM = ?, PASSWORD = ?, EMAIL = ?, UPDATE_DATE = ? WHERE ADMIN_ID = ?");
        $stmt->bind_param('sssss', $inputShopAdminContact, $inputShopAdminPW, $inputShopAdminEmail, $currentDateTime, $inputShopAdminID);
        $stmt->execute();
        $stmt->close();
        return $inputShopAdminID;
    }
}
