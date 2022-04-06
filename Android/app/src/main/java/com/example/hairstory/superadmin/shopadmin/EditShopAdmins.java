package com.example.hairstory.superadmin.shopadmin;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditShopAdmins extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView ShopAdminPassword;
    TextView ShopAdminContact1;
    TextView ShopAdminContact2;
    TextView ShopAdminContact3;
    TextView ShopAdminEmailAdd;
    TextView ShopAdminEmailDomain;

    String shopAdmin_ID = "";

    Button btnSubmit;
    Button btnReset;

    String tag_string_req = "Edit Shop Admin Information";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop_admins);

        ShopAdminPassword = findViewById(R.id.input_edit_shopAdmin_password);
        ShopAdminContact1 = findViewById(R.id.input_edit_shopAdmin_contact01);
        ShopAdminContact2 = findViewById(R.id.input_edit_shopAdmin_contact02);
        ShopAdminContact3 = findViewById(R.id.input_edit_shopAdmin_contact03);
        ShopAdminEmailAdd = findViewById(R.id.input_edit_shopAdmin_address);
        ShopAdminEmailDomain = findViewById(R.id.input_edit_shopAdmin_email);

        btnSubmit = findViewById(R.id.btn_edit_shopAdmin_Submit);
        btnReset = findViewById(R.id.btn_edit_shopAdmin_Reset);

        Intent eMenu_intent = getIntent();
        shopAdmin_ID = eMenu_intent.getStringExtra("sendShopAdminID");

        Log.d("DEBUG", shopAdmin_ID);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntentWithValues(v.getContext(), DetailShopAdmins.class, "sendAdminID", shopAdmin_ID);

            }
        });



        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DETAIL_SHOP_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // check response from php-database
//                Log.d("DEBUG", response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // check json object type response
//                    Log.d("DEBUG", String.valueOf(jObj));
                    if (!error) {

                        String getContactNum = jObj.getString("shopAdminPNumber");
                        String getEmailAddress = jObj.getString("shopAdminEmail");

                        ArrayList<String> formatPhoneNum = CommonFunctions.formatPhoneNumber(getContactNum);
                        ArrayList<String> formatEmailAddress = CommonFunctions.formatEmailAddress(getEmailAddress);

                        ShopAdminContact1.setText(formatPhoneNum.get(0));
                        ShopAdminContact2.setText(formatPhoneNum.get(1));
                        ShopAdminContact3.setText(formatPhoneNum.get(2));

                        ShopAdminEmailAdd.setText(formatEmailAddress.get(0));
                        ShopAdminEmailDomain.setText(formatEmailAddress.get(1));

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
                // Posting parameters to detail shop admin url
                Map<String, String> params = new HashMap<String, String>();
                params.put("shop_admin_id", shopAdmin_ID);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        // when Submit button is pushed after admin change the value
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_EDIT_SHOP_ADMIN, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "The shop admin (" + shopAdmin_ID + ") is successfully Updated!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntentWithValues(v.getContext(), DetailShopAdmins.class, "sendAdminID", shopAdmin_ID);

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
                        // Posting parameters to edit shop admin url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("shopAdmin_ID", shopAdmin_ID);
                        params.put("shopAdmin_PW", ShopAdminPassword.getText().toString());
                        params.put("shopAdmin_Contact", ShopAdminContact1.getText().toString() +
                                ShopAdminContact2.getText().toString() +
                                ShopAdminContact3.getText().toString());
                        params.put("shopAdmin_Email", ShopAdminEmailAdd.getText().toString() + "@" + ShopAdminEmailDomain.getText().toString());
                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });
    }
}