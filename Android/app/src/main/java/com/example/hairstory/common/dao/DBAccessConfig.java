package com.example.hairstory.common.dao;

public class DBAccessConfig {
    public static String HOST_NNAME = "10.0.2.2";
    // Server user login url
    public static String URL_LOGIN = "http://"+HOST_NNAME+"/hairstory/login.php";


    public static String URL_MENU = "http://"+HOST_NNAME+"/hairstory/superadmin/mastermenu/detail_menus.php";

    // php settings to set up Master menu for Super Admin

    public static String URL_LIST_MASTER_MENU = "http://"+HOST_NNAME+"/hairstory/superadmin/mastermenu/List_Master_Menus.php";
    public static String URL_DETAIL_MASTER_MENU = "http://"+HOST_NNAME+"/hairstory/superadmin/mastermenu/Detail_Master_Menus.php";
    public static String URL_REGISTER_MASTER_MENU = "http://"+HOST_NNAME+"/hairstory/superadmin/mastermenu/Register_Master_Menu.php";
    public static String URL_DELETE_MASTER_MENU = "http://"+HOST_NNAME+"/hairstory/superadmin/mastermenu/Delete_Master_Menu.php";
    public static String URL_EDIT_MASTER_MENU = "http://"+HOST_NNAME+"/hairstory/superadmin/mastermenu/Edit_Master_Menu.php";

    // php settings to set up Shop admin for Super Admin
    public static String URL_LIST_SHOP_ADMIN = "http://"+HOST_NNAME+"/hairstory/superadmin/shopadmin/List_Shop_Admins.php";
    public static String URL_DETAIL_SHOP_ADMIN = "http://"+HOST_NNAME+"/hairstory/superadmin/shopadmin/Detail_Shop_Admins.php";
    public static String URL_REGISTER_SHOP_ADMIN = "http://"+HOST_NNAME+"/hairstory/superadmin/shopadmin/Register_Shop_Admin.php";
    public static String URL_DELETE_SHOP_ADMIN = "http://"+HOST_NNAME+"/hairstory/superadmin/shopadmin/Delete_Shop_Admin.php";
    public static String URL_EDIT_SHOP_ADMIN = "http://"+HOST_NNAME+"/hairstory/superadmin/shopadmin/Edit_Shop_Admin.php";

    // php settings to set up Membership for Member
    public static String URL_REGISTER_MEMBER_MEMBERSHIP = "http://"+HOST_NNAME+"/hairstory/member/memberinfo/Register_Member_Membership.php";
    public static String URL_DETAIL_MEMBER_MEMBERSHIP = "http://"+HOST_NNAME+"/hairstory/member/memberinfo/Detail_Member_Membership.php";
    public static String URL_DELETE_MEMBER_MEMBERSHIP = "http://"+HOST_NNAME+"/hairstory/member/memberinfo/Delete_Member_Membership.php";
    public static String URL_EDIT_MEMBER_MEMBERSHIP = "http://"+HOST_NNAME+"/hairstory/member/memberinfo/Edit_Member_Membership.php";

    // php settings to set up Search for Member
    public static String URL_SEARCH_MEMBER_SHOPS = "http://"+HOST_NNAME+"/hairstory/member/search/Search_Member_Shops.php";
    public static String URL_SEARCH_MEMBER_BRANCHES = "http://"+HOST_NNAME+"/hairstory/member/search/Search_Member_Branches.php";
    public static String URL_SEARCH_MEMBER_DESIGNERS = "http://"+HOST_NNAME+"/hairstory/member/search/Search_Member_Designers.php";

    // php settings to handle reservation for Member
    public static String URL_REGISTER_MEMBER_RESERVATION = "http://"+HOST_NNAME+"/hairstory/member/reservation/Register_Member_Reservation.php";



    // Admin
    // Branch
    public static String URL_REGISTER_BRANCH = "http://"+HOST_NNAME+"/hairstory/admin/branch/registerBranch.php";
    public static String URL_GET_LIST_BRANCH = "http://"+HOST_NNAME+"/hairstory/admin/branch/getListBranch.php";
    public static String URL_GET_BRANCH = "http://"+HOST_NNAME+"/hairstory/admin/branch/getBranch.php";
    public static String URL_UPDATE_BRANCH = "http://"+HOST_NNAME+"/hairstory/admin/branch/updateBranch.php";
    public static String URL_DELETE_BRANCH = "http://"+HOST_NNAME+"/hairstory/admin/branch/deleteBranch.php";

    // Branch ADMIN
    public static String URL_REGISTER_BRANCH_ADMIN = "http://"+HOST_NNAME+"/hairstory/admin/branchadmin/registerBranchAdmin.php";
    public static String URL_GET_LIST_BRANCH_ADMIN = "http://"+HOST_NNAME+"/hairstory/admin/branchadmin/getListBranchAdmin.php";
    public static String URL_GET_BRANCH_ADMIN = "http://"+HOST_NNAME+"/hairstory/admin/branchadmin/getBranchAdmin.php";
    public static String URL_UPDATE_BRANCH_ADMIN = "http://"+HOST_NNAME+"/hairstory/admin/branchadmin/updateBranchAdmin.php";
    public static String URL_DELETE_BRANCH_ADMIN = "http://"+HOST_NNAME+"/hairstory/admin/branchadmin/deleteBranchAdmin.php";

    // Staff
    public static String URL_REGISTER_STAFF = "http://"+HOST_NNAME+"/hairstory/admin/staff/registerStaff.php";
    public static String URL_GET_LIST_STAFF = "http://"+HOST_NNAME+"/hairstory/admin/staff/getListStaff.php";
    public static String URL_GET_STAFF = "http://"+HOST_NNAME+"/hairstory/admin/staff/getStaff.php";
    public static String URL_UPDATE_STAFF = "http://"+HOST_NNAME+"/hairstory/admin/staff/updateStaff.php";
    public static String URL_DELETE_STAFF = "http://"+HOST_NNAME+"/hairstory/admin/staff/deleteStaff.php";

    // Report
    public static String URL_REPORT_BY_SALES = "http://"+HOST_NNAME+"/hairstory/admin/report/getReportData.php";

}