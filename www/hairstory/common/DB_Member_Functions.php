<?php

class DB_Member_Functions
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

    public function getCurrentDateTime()
    {
        $DateAndTime = date('Y-m-d h:i:s', time());
        return $DateAndTime;
    }

    public function RegisterNewMember(
        $getMemberID,
        $getMemberPW,
        $getMemberFName,
        $getMemberLName,
        $getMemberDOB,
        $getMemberContact,
        $getMemberEmail
    ) {
        $inputMemberID = $getMemberID;
        $inputMemberPW = $getMemberPW;
        $inputMemberFName = $getMemberFName;
        $inputMemberLName = $getMemberLName;
        $inputMemberDOB = $getMemberDOB;
        $inputMemberContact = $getMemberContact;
        $inputMemberEmail = $getMemberEmail;

        $currentDateTime = $this->getCurrentDateTime();

        $stmt = $this->conn->prepare("INSERT INTO CUSTOMER (CUSTOMER_ID, PASSWORD, F_NAME, L_NAME, PHONE_NUM, EMAIL, DATE_OF_BIRTH, REG_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param(
            'sssssssss',
            $inputMemberID,
            $inputMemberPW,
            $inputMemberFName,
            $inputMemberLName,
            $inputMemberContact,
            $inputMemberEmail,
            $inputMemberDOB,
            $currentDateTime,
            $currentDateTime
        );

        $stmt->execute();
        $stmt->close();
        return $inputMemberID;
    }


    // for getting detailed information of customer -> Detail_Member_Membership.php
    public function getMemberDetailInfo($getMemberID)
    {
        $inputMemberID = $getMemberID;
        $stmt = $this->conn->prepare("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ? AND SHOW_FLAG = '1'");
        $stmt->bind_param('s', $inputMemberID);

        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return NULL;
        }
    }

    // to delete current member with given menu id
    public function DeleteCurrentMember($getMemberID)
    {
        $inputMemberID = $getMemberID;
        $stmt = $this->conn->prepare("UPDATE CUSTOMER SET SHOW_FLAG='0' WHERE CUSTOMER_ID = ?");
        $stmt->bind_param('s', $inputMemberID);
        $stmt->execute();
        $stmt->close();
        return $inputMemberID;
    }


    // to edit current member with given menu id
    public function EditCurrentMember(
        $getMemberID,
        $getMemberPW,
        $getMemberContact,
        $getMemberEmail
    ) {
        $inputMemberID = $getMemberID;
        $inputMemberPW = $getMemberPW;
        $inputMemberContact = $getMemberContact;
        $inputMemberEmail = $getMemberEmail;

        $currentDateTime = $this->getCurrentDateTime();

        $stmt = $this->conn->prepare("UPDATE CUSTOMER SET PHONE_NUM = ?, PASSWORD = ?, EMAIL = ?, UPDATE_DATE = ? WHERE CUSTOMER_ID = ?");
        $stmt->bind_param('sssss', $inputMemberContact, $inputMemberPW, $inputMemberEmail, $currentDateTime, $inputMemberID);
        $stmt->execute();
        $stmt->close();
        return $inputMemberID;
    }

    // to get searched shop list
    public function getShopList()
    {
        $stmt = $this->conn->prepare("SELECT * FROM SHOP WHERE SHOW_FLAG = '1'");

        if ($stmt->execute()) {
            $shop = $stmt->get_result();
            $stmt->close();

            return $shop;
        } else {
            return NULL;
        }
    }

    // to get searched branch list
    public function getBranchList($getShopID)
    {
        $inputShopID = $getShopID;
        $stmt = $this->conn->prepare("SELECT * FROM BRANCH WHERE SHOP_ID = ? AND SHOW_FLAG = '1'");
        $stmt->bind_param('s', $inputShopID);

        if ($stmt->execute()) {
            $branch = $stmt->get_result();
            $stmt->close();

            return $branch;
        } else {
            return NULL;
        }
    }

     // to get searched designer list
     public function getDesignerList($getBranchID)
     {
         $inputDesignerID = $getBranchID;
         $stmt = $this->conn->prepare("SELECT * FROM STAFF WHERE BRANCH_ID = ? AND SHOW_FLAG = '1'");
         $stmt->bind_param('s', $inputDesignerID);
 
         if ($stmt->execute()) {
             $designer = $stmt->get_result();
             $stmt->close();
 
             return $designer;
         } else {
             return NULL;
         }
     }


    //  $reservationSTime,
    //  $reservationETime,
    //  $reservationSId,
    //  $reservationcId        


     // to register new reservation
     public function RegisterNewReservation(
        $getReservationRDate,
        $getReservationSTime,
        $getReservationETime,
        $getReservationSId,
        $getReservationCId        
    ) {
        $inputResRDate = $getReservationRDate;
        $inputResSTime = $getReservationSTime;
        $inputResETime = $getReservationETime;
        $inputResSId = $getReservationSId;
        $inputResCId = $getReservationCId;        

        $currentDateTime = $this->getCurrentDateTime();

        $stmt = $this->conn->prepare("INSERT INTO RESERVATION (RESERVATION_DATE, START_TIME, END_TIME, TOTAL_PRICE, RESERVATION_TYPE, STAFF_ID, CUSTOMER_ID, REG_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param(
            'sssssssss',
            $inputResRDate,
            $inputResSTime,
            $inputResETime,
            "0",
            "1",
            $inputResSId,
            $inputResCId,
            $currentDateTime,
            $currentDateTime            
        );

        $stmt->execute();
        $stmt->close();
        return $inputResCId;
    }
}
