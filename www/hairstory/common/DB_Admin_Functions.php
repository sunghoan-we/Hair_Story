<?php
 
class DB_Admin_Functions {
 
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
     * Check Branch is existed or not
     */
    public function isBranchExisted($shopId, $branchName) {
        $stmt = $this->conn->prepare("SELECT * from BRANCH WHERE shop_id = ? and branch_name = ? and show_flag='1'");
 
        $stmt->bind_param("is", $shopId, $branchName);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // branch existed 
            $stmt->close();
            return true;
        } else {
            // branch not existed
            $stmt->close();
            return false;
        }
    }

    /**
     * register new Branch
     * returns register status
     */
    public function registerBranch($shopId, $branchName, $branchAddress, $branchContact, $openTime, $closeTime) {
        $stmt = $this->conn->prepare("INSERT INTO BRANCH(branch_name, address, contact, open_time, close_time, shop_id, reg_date) VALUES(?, ?, ?, ?, ?, ?, NOW())");
        $stmt->bind_param("sssssi", $branchName, $branchAddress, $branchContact, $openTime, $closeTime, $shopId);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful register
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * update exsisted Branch
     * returns update status
     */
    public function updateBranch($branchId, $branchName, $branchAddress, $branchContact, $openTime, $closeTime) {
        $stmt = $this->conn->prepare("UPDATE BRANCH SET branch_name=?, address=?, contact=?, open_time=?, close_time=? WHERE branch_id=?");
        $stmt->bind_param("sssssi", $branchName, $branchAddress, $branchContact, $openTime, $closeTime, $branchId);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful update
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete exsisted Branch
     * returns delete status
     */
    public function deleteBranch($branchId) {
        $stmt = $this->conn->prepare("UPDATE BRANCH SET show_flag='0' WHERE branch_id=?");
        $stmt->bind_param("i", $branchId);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful delete
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the list of Branch
     */
    public function getListBranch($shopId) {
 
        $stmt = $this->conn->prepare("SELECT * FROM BRANCH WHERE shop_id = ? and show_flag='1'");
        $stmt->bind_param("i", $shopId);

 
        if ($stmt->execute()) {
            $listBranch = $stmt->get_result();
            $stmt->close();
 
            return $listBranch;
        } else {
            return NULL;
        }
    }

    /**
     * Get Branch
     */
    public function getBranch($branchId) {
 
        $stmt = $this->conn->prepare("SELECT * FROM BRANCH WHERE branch_id = ? and show_flag='1'");
        $stmt->bind_param("i", $branchId);

 
        if ($stmt->execute()) {
            $branch = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $branch;
        } else {
            return NULL;
        }
    }

    /**
     * Get the list of Branch Admin
     */
    public function getListBranchAdmin($shopId) {
 
        $stmt = $this->conn->prepare("SELECT A.*, B.branch_id, C.branch_name FROM ADMIN A INNER JOIN BRANCH_FOR_ADMIN B on A.admin_id = B.admin_id and A.show_flag='1' INNER JOIN BRANCH C on C.branch_id = B.branch_id WHERE C.shop_id = ?");
        $stmt->bind_param("i", $shopId);

 
        if ($stmt->execute()) {
            $listBranch = $stmt->get_result();
            $stmt->close();
 
            return $listBranch;
        } else {
            return NULL;
        }
    }


    /**
     * Get Branch Admin
     */
    public function getBranchAdmin($branchAdminId, $role) {
        $stmt = $this->conn->prepare("SELECT A.*, C.branch_id, C.branch_name  FROM ADMIN A INNER JOIN BRANCH_FOR_ADMIN B ON A.admin_id=B.admin_id and A.show_flag='1' INNER JOIN BRANCH C ON C.branch_id=B.branch_id WHERE A.admin_id = ? and role = ?");
        $stmt->bind_param("ss", $branchAdminId, $role);

 
        if ($stmt->execute()) {
            $branchAdmin = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $branchAdmin;
        } else {
            return NULL;
        }
    }

    /**
     * update exsisted Branch Admin
     * returns update status
     */
    public function updateBranchAdmin($adminId, $password, $fName, $lName, $phoneNum, $email, $dateOfBirth, $branchId){
        $stmt = $this->conn->prepare("UPDATE BRANCH_FOR_ADMIN SET branch_id=? WHERE admin_id =?");
        $stmt->bind_param("is", $branchId, $adminId);
        $result = $stmt->execute();

        // check for successful update
        if (!$result) 
            return false;

        $stmt = $this->conn->prepare("UPDATE ADMIN SET `password`=?, f_name=?, l_name=?, phone_num=?, email=?, date_of_birth=? WHERE admin_id=?");
        $stmt->bind_param("sssssss", $password, $fName, $lName, $phoneNum, $email, $dateOfBirth, $adminId);
        $result = $stmt->execute();
        $stmt->close();

        //check for successful update
        if ($result) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * delete exsisted Branch Admin
     * returns delete status
     */
    public function deleteBranchAdmin($adminId) {
        $stmt = $this->conn->prepare("UPDATE ADMIN SET show_flag='0' WHERE admin_id=?");
        $stmt->bind_param("s", $adminId);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful delete
        if ($result) {
            return true;
        } else {
            return false;
        }
    }




     /**
     * Check Branch Admin is existed or not
     */
    public function isBranchAdminExisted($adminId) {
        $stmt = $this->conn->prepare("SELECT * from ADMIN WHERE admin_id = ?");
 
        $stmt->bind_param("s", $adminId);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // branch Admin existed 
            $stmt->close();
            return true;
        } else {
            // branch Admin not existed
            $stmt->close();
            return false;
        }
    }

    /**
     * register new Branch Admin
     * returns register status
     */
    public function registerBranchAdmin($adminId, $password, $fName, $lName, $role, $phoneNum, $email, $branchId) {
        $stmt = $this->conn->prepare("INSERT INTO BRANCH_FOR_ADMIN (branch_id, admin_id) VALUES(?, ?)");
        $stmt->bind_param("is", $branchId, $adminId);
        $result = $stmt->execute();

        // check for successful register
        if (!$result) 
            return false;

        
        $stmt = $this->conn->prepare("INSERT INTO ADMIN (admin_id, password, f_name, l_name, role, phone_num, email, date_of_birth, reg_date, update_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())");
        $stmt->bind_param("ssssssss", $adminId, $password, $fName, $lName, $role, $phoneNum, $email, $dateOfBirth);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful register
        if ($result) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Get the list of Staff
     */
    public function getListStaff($shopId, $branchId, $staffName, $branchName) {

        $likeStaffName = "%$staffName%";
        $likeBranchName = "%$branchName%";

        if (isset($branchId)) {
            
            $query = "SELECT * FROM STAFF A INNER JOIN BRANCH B ON A.branch_id = B.branch_id AND A.show_flag='1' INNER JOIN SHOP C ON C.shop_id = B.shop_id WHERE C.shop_id = ? and B.branch_id = ?";
            if (isset($staffName) && isset($branchName)) {
                $query .= " AND (A.f_name like ? or A.l_name like ?) AND B.branch_name like ?";
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("iisss", $shopId, $branchId, $likeStaffName, $likeStaffName, $likeBranchName);

            } else if (isset($staffName)) {

                $query .= " AND (A.f_name like ? or A.l_name like ?)";
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("iiss", $shopId, $branchId, $likeStaffName, $likeStaffName);

            } else if (isset($branchName)) {
                $query .= " AND B.branch_name like ?";
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("iis", $shopId, $branchId, $likeBranchName);

            } else {
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("ii", $shopId, $branchId);
            }
    
        } else {
            $query = "SELECT * FROM STAFF A INNER JOIN BRANCH B ON A.branch_id = B.branch_id AND A.show_flag='1' INNER JOIN SHOP C ON C.shop_id = B.shop_id WHERE C.shop_id = ?";

            if (isset($staffName) && isset($branchName)) {
                $query .= " AND (A.f_name like ? or A.l_name like ?) AND B.branch_name like ?";
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("isss", $shopId, $likeStaffName, $likeStaffName, $likeBranchName);

            } else if (isset($staffName)) {

                $query .= " AND (A.f_name like ? or A.l_name like ?)";
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("iss", $shopId, $likeStaffName, $likeStaffName);

            } else if (isset($branchName)) {
                $query .= " AND B.branch_name like ?";
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("is", $shopId, $likeBranchName);

            } else {
                $stmt = $this->conn->prepare($query);
                $stmt->bind_param("i", $shopId);            
            }
        }
 
        if ($stmt->execute()) {
            $listStaff = $stmt->get_result();
            $stmt->close();
 
            return $listStaff;
        } else {
            return NULL;
        }
    }


    /**
     * Get Staff
     */
    public function getStaff($staffId) {
 
        $stmt = $this->conn->prepare("SELECT A.*, B.branch_name  FROM STAFF A INNER JOIN BRANCH B on A.branch_id=B.branch_id AND A.show_flag='1' WHERE A.staff_id = ?");
        $stmt->bind_param("i", $staffId);

 
        if ($stmt->execute()) {
            $staff = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $staff;
        } else {
            return NULL;
        }
    }

    /**
     * update exsisted Staff
     * returns update status
     */
    public function updateStaff($staffId, $fName, $lName, $gender, $phoneNum, $email, $dateOfBirth, $branchId){
        // MENU_FOR_STAFF
        // DAY_OFF_SCHEDULE

        $stmt = $this->conn->prepare("UPDATE STAFF SET f_name=?, l_name=?, gender=?, phone_num=?, email=?, date_of_birth=?, branch_id=? WHERE staff_id=?");
        $stmt->bind_param("ssssssii", $fName, $lName, $gender, $phoneNum, $email, $dateOfBirth, $branchId, $staffId);
        $result = $stmt->execute();
        $stmt->close();

        //check for successful update
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete exsisted Staff
     * returns delete status
     */
    public function deleteStaff($staffId) {
        $stmt = $this->conn->prepare("UPDATE STAFF SET show_flag='0' WHERE staff_id=?");
        $stmt->bind_param("i", $staffId);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful delete
        if ($result) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * register new Branch Admin
     * returns register status
     */
    public function registerStaff($fName, $lName, $gender, $phoneNum, $email, $dateOfBirth, $branchId){

        // MENU_FOR_STAFF
        // DAY_OFF_SCHEDULE
        // STAFF
        $stmt = $this->conn->prepare("INSERT INTO STAFF (f_name, l_name, gender, phone_num, email, date_of_birth, branch_id, reg_date, update_date) VALUES(?, ?, ?, ?, ?, ?, ?, NOW(), NOW())");
        $stmt->bind_param("ssssssi", $fName, $lName, $gender, $phoneNum, $email, $dateOfBirth, $branchId);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful register
        if ($result) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Get ReportData
     */
    public function getReportData($shopId, $fromDate, $toDate){
 
        // if ($type == 1) {

        //     $sql = "SELECT C.branch_name, B.f_name, B.l_name, SUM(A.payment_amount) AS amount FROM PAYMENT A 
        //                 INNER JOIN STAFF B ON A.staff_id=B.staff_id 
        //                 INNER JOIN BRANCH C ON B.branch_id=C.branch_id 
        //                 WHERE C.shop_id=?";
        //     if (isset($fromDate) && isset($toDate)) {
        //         $sql += " and A.reg_date BETWEEN ? AND ? GROUP BY C.branch_name, B.f_name, B.l_name";
        //         $stmt->bind_param("iss", $shopId, $fromDate, $toDate);
        //     } else if (isset($fromDate)) {
        //         $sql += " and A.reg_date >= ? GROUP BY C.branch_name, B.f_name, B.l_name";
        //         $stmt->bind_param("is", $shopId, $fromDate);
        //     } else if (isset($toDate)) {
        //         $sql += " and A.reg_date <= ? GROUP BY C.branch_name, B.f_name, B.l_name";
        //         $stmt->bind_param("is", $shopId, $toDate);
        //     } else {
        //         $sql += " GROUP BY C.branch_name, B.f_name, B.l_name";
        //         $stmt->bind_param("is", $shopId);
        //     }
        // } else ($type == 2) {
            $sql = "SELECT C.branch_name, SUM(A.payment_amount) AS amount FROM PAYMENT A 
                        INNER JOIN STAFF B ON A.staff_id=B.staff_id 
                        INNER JOIN BRANCH C ON B.branch_id=C.branch_id 
                        WHERE C.shop_id=?";

            if (isset($fromDate) && isset($toDate)) {
                $sql .= " and A.reg_date BETWEEN ? AND ? GROUP BY C.branch_name";
                $stmt = $this->conn->prepare($sql);
                $stmt->bind_param("sss", $shopId, $fromDate, $toDate);
            } else if (isset($fromDate)) {
                $sql .= " and A.reg_date >= ? GROUP BY C.branch_name";
                $stmt = $this->conn->prepare($sql);
                $stmt->bind_param("ss", $shopId, $fromDate);
            } else if (isset($toDate)) {
                $sql .= " and A.reg_date <= ? GROUP BY C.branch_name";
                $stmt = $this->conn->prepare($sql);
                $stmt->bind_param("ss", $shopId, $toDate);
            } else {
                $sql .= " GROUP BY C.branch_name";
                $stmt = $this->conn->prepare($sql);
                $stmt->bind_param("s", $shopId);
            }
        // }
 
        if ($stmt->execute()) {
            $reportData = $stmt->get_result();
            $stmt->close();
 
            return $reportData;
        } else {
            return NULL;
        }
    }


}
?>    