package com.example.hairstory.superadmin.mastermenu;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.util.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//
//

public class DetailMasterMenu extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView menu_ID;
    TextView menu_Name;
    Button btnDelete;
    Button btnModify;

    String tag_string_req = "Detail Information of Master Menu";

    String menu_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_master_menu);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), ListMasterMenus.class);

            }
        });

        menu_ID = findViewById(R.id.data_detail_masterMenu_mID);
        menu_Name = findViewById(R.id.data_detail_masterMenu_mName);
        btnDelete = findViewById(R.id.btn_detail_masterMenu_Delete);
        btnModify = findViewById(R.id.btn_detail_masterMenu_Modify);

        Intent dMenu_intent = getIntent();

        menu_name = dMenu_intent.getStringExtra("sendMenuName");

        Log.d("DEBUG", String.valueOf(menu_name));

        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DETAIL_MASTER_MENU, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        menu_ID.setText(jObj.getString("menuId"));
                        menu_Name.setText(jObj.getString("menuName"));

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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to detail menu url
                Map<String, String> params = new HashMap<String, String>();
                params.put("menu_name", String.valueOf(menu_name));
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        // when clicking modify button
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntentWithValues(v.getContext(), EditMasterMenu.class, "sendMenuName", menu_name);

            }
        });


        // when delete button is pushed
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DELETE_MASTER_MENU, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // check response from php-database
//                Log.d("DEBUG", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "The menu (" + menu_name + ") is successfully Deleted!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntent(v.getContext(), ListMasterMenus.class);

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

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to detail menu url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("menu_id", menu_ID.getText().toString());
                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });
    }
}