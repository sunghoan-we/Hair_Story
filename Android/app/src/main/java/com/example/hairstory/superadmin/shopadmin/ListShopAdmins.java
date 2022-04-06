package com.example.hairstory.superadmin.shopadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.LoginMember;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.util.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListShopAdmins extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    Button addNewShopAdmin;

    String tag_string_req = "List Shop Admins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shop_admins);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), LoginMember.class);

            }
        });

        addNewShopAdmin = findViewById(R.id.btn_list_shopAdmin_AddNewShopAdmin);

        addNewShopAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), RegisterShopAdmins.class);

            }
        });

        // add recyclerview layout
        RecyclerView recyclerView = findViewById(R.id.rcv_list_shopAdmin_List);

        // set layout design as linear view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // run db with appropriate php address
        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_LIST_SHOP_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
//                Log.d("DEBUG", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        ArrayList<String> shopAdminIndexes = new ArrayList<>();
                        ArrayList<String> shopAdminIds = new ArrayList<>();
                        ArrayList<String> shopAdminFNames = new ArrayList<>();
                        ArrayList<String> shopAdminLNames = new ArrayList<>();
                        ArrayList<String> shopAdminEmails = new ArrayList<>();
                        ArrayList<String> shopAdminRegDates = new ArrayList<>();


                        for (int i = 1; i < jObj.length(); i++) {

                            JSONObject shopAdminList = jObj.getJSONObject(String.valueOf(i));

                            shopAdminIndexes.add(String.valueOf(i));
                            shopAdminIds.add(shopAdminList.getString("Shop_Admin_ID"));
                            shopAdminFNames.add(shopAdminList.getString("Shop_Admin_fName"));
                            shopAdminLNames.add(shopAdminList.getString("Shop_Admin_lName"));
                            shopAdminEmails.add(shopAdminList.getString("Shop_Admin_email"));
                            shopAdminRegDates.add(shopAdminList.getString("Shop_Admin_regDate"));

                        }

                        recViewAdapter_List_Shop_Admin newAdapter = new recViewAdapter_List_Shop_Admin(shopAdminIndexes,
                                shopAdminIds,
                                shopAdminFNames,
                                shopAdminLNames,
                                shopAdminEmails,
                                shopAdminRegDates
                        );
                        recyclerView.setAdapter(newAdapter);

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}