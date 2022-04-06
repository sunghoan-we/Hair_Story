package com.example.hairstory.superadmin.mastermenu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
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

public class ListMasterMenus extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    Button addNewMenu;

    String tag_string_req = "List Master Menus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_master_menu);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), LoginMember.class);

            }
        });

        addNewMenu = findViewById(R.id.btn_list_masterMenu_AddNewMenu);

        addNewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), RegisterMasterMenu.class);

            }
        });

        // add recyclerview layout
        RecyclerView recyclerView = findViewById(R.id.rcv_list_masterMenu_List);

        // set layout design as grid view (2 columns for each row)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // run db with appropriate php address
        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_LIST_MASTER_MENU, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
//                Log.d("DEBUG", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        ArrayList<String> menuIds = new ArrayList<>();
                        ArrayList<String> menuNames = new ArrayList<>();

                        for (int i = 1; i < jObj.length(); i++) {

                            JSONObject menuList = jObj.getJSONObject(String.valueOf(i));

                            menuIds.add(menuList.getString("Menu_ID"));
                            menuNames.add(menuList.getString("Menu_Name"));
                        }

                        recViewAdapter_List_Master_Menu newAdapter = new recViewAdapter_List_Master_Menu(menuIds, menuNames);
                        recyclerView.setAdapter(newAdapter);

                    } else {
                        // Error in login. Get the error message
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
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }


}