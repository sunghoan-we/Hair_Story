<?php
 
class DB_Common_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
 
    /**
     * Get user by user id, password and role
     */
    public function getLoginInfo($userId, $password, $role) {
        $showFlag="1";
        if (isset($role)) {
            if ($role == "01") {
                $stmt = $this->conn->prepare("SELECT * FROM ADMIN WHERE admin_id = ? and `password` = ? and `role` = ?");
                $stmt->bind_param('sss', $userId, $password, $role);
            } else if ($role == "02") {
                $stmt = $this->conn->prepare("SELECT A.*, B.shop_id, C.shop_name FROM ADMIN A INNER JOIN SHOP_FOR_ADMIN B ON A.admin_id = B.admin_id INNER JOIN SHOP C on C.shop_id = B.shop_id WHERE A.admin_id = ? and `password` = ? and `role` = ?");
                $stmt->bind_param('sss', $userId, $password, $role);

            } else if ($role == "03") {
                $stmt = $this->conn->prepare("SELECT A.*, B.branch_id, C.branch_name, D.shop_id, D.shop_name FROM ADMIN A INNER JOIN BRANCH_FOR_ADMIN B ON A.admin_id = B.admin_id  INNER JOIN BRANCH C on C.branch_id = B.branch_id INNER JOIN SHOP D on D.shop_id = C.shop_id WHERE A.admin_id = ? and `password` = ? and `role` = ?");
                $stmt->bind_param('sss', $userId, $password, $role);
            } 

            //https://www.php.net/manual/en/mysqli-stmt.bind-param.php

        } else {
            $stmt = $this->conn->prepare("SELECT * FROM CUSTOMER WHERE customer_id = ? and `password` = ?");
            $stmt->bind_param('ss', $userId, $password);
        } 

        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return NULL;
        }
    }
 
 
}
 
?>