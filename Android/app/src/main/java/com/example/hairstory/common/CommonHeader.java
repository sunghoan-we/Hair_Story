package com.example.hairstory.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.hairstory.LoginMember;
import com.example.hairstory.R;
import com.example.hairstory.admin.branch.ListBranch;
import com.example.hairstory.admin.branchadmin.ListBranchCommon;
import com.example.hairstory.admin.report.ReportBySales;
import com.example.hairstory.admin.reservation.ListReservation;
import com.example.hairstory.admin.sales.ListSales;
import com.example.hairstory.admin.staff.ListStaff;
import com.example.hairstory.member.memberinfo.DetailMemberMembership;
import com.example.hairstory.member.search.SearchMemberShops;
import com.example.hairstory.superadmin.mastermenu.ListMasterMenus;
import com.example.hairstory.superadmin.shopadmin.ListShopAdmins;

public class CommonHeader extends AppCompatActivity {

    public final static String USER_INFO = "USER_INFO";
    public final static String USER_ID = "userId";
    public final static String SHOP_ID = "shopId";
    public final static String ROLE = "role";
    public final static String SHOP_NAME = "shopName";
    public final static String BRANCH_ID = "branchId";
    public final static String BRANCH_NAME = "branchName";
    public SharedPreferences preferences;
    public TextView txtTitle;
    public ImageView btnAdminHeaderBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_header);


        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String userId = preferences.getString(USER_ID, null);


        if (userId == null || userId.isEmpty()) {
            startActivity(new Intent(this, LoginMember.class));
        }

    }

    public void onClickMenu(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String role = preferences.getString(ROLE, null);
        if("01".equals(role)) {
           getMenuInflater().inflate(R.menu.super_admin_menu, popup.getMenu());
        } else if("02".equals(role)) {
           getMenuInflater().inflate(R.menu.shop_admin_menu, popup.getMenu());
        } else if("03".equals(role)) {
            getMenuInflater().inflate(R.menu.branch_admin_menu, popup.getMenu());
        } else {
            getMenuInflater().inflate(R.menu.member_menu, popup.getMenu());
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
//TODO modify the destination class
//                    case R.id.m1:
//                        Toast.makeText(view.getContext(),"m1",Toast.LENGTH_SHORT).show();
////                        startActivity(new Intent(SuperAdminHeader.this, HomeActivity.class));
//                        break;

                    case R.id.m2:
                        startActivity(new Intent(CommonHeader.this, SearchMemberShops.class));
                        break;
                    case R.id.m3:
                        startActivity(new Intent(CommonHeader.this, DetailMemberMembership.class));
                        break;

                    case R.id.m12:
                        startActivity(new Intent(CommonHeader.this, ListMasterMenus.class));
                        break;
                    case R.id.m13:
                        startActivity(new Intent(CommonHeader.this, ListShopAdmins.class));
                        break;
                    case R.id.m22:
                        startActivity(new Intent(CommonHeader.this, ListBranch.class));
                        break;
                    case R.id.m23:
                        startActivity(new Intent(CommonHeader.this, ListBranchCommon.class));
                        break;
                    case R.id.m24:
                        startActivity(new Intent(CommonHeader.this, ListStaff.class));
                        break;
                    case R.id.m25:
                        startActivity(new Intent(CommonHeader.this, ListReservation.class));
                        break;
                    case R.id.m26:
                        startActivity(new Intent(CommonHeader.this, ListSales.class));
                        break;
                    case R.id.m27:
                        startActivity(new Intent(CommonHeader.this, ReportBySales.class));
                        break;
                    case R.id.m34:
                        startActivity(new Intent(CommonHeader.this, ListStaff.class));
                        break;
                    case R.id.m35:
                        startActivity(new Intent(CommonHeader.this, ListReservation.class));
                        break;
                    case R.id.m36:
                        startActivity(new Intent(CommonHeader.this, ListSales.class));
                        break;
                    case R.id.m9:
                        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(CommonHeader.this, LoginMember.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

}