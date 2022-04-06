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

import java.util.HashMap;
import java.util.Map;

public class DetailShopAdmins extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView shopAdmin_ID;
    TextView shopAdmin_Name;
    TextView shopAdmin_Email;
    TextView shopAdmin_Contact;
    TextView shopAdmin_RegDate;

    Button btnDelete;
    Button btnModify;

    String tag_string_req = "Detail Information of Shop Admin";

    String shopAdmin_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop_admins);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), ListShopAdmins.class);

            }
        });

        shopAdmin_ID = findViewById(R.id.txt_detail_shopAdmin_ID);
        shopAdmin_Name = findViewById(R.id.txt_detail_shopAdmin_Name);
        shopAdmin_Email = findViewById(R.id.txt_detail_shopAdmin_Email);
        shopAdmin_Contact = findViewById(R.id.txt_detail_shopAdmin_Contact);
        shopAdmin_RegDate = findViewById(R.id.txt_detail_shopAdmin_RegDate);

        btnDelete = findViewById(R.id.btn_detail_shopAdmin_Delete);
        btnModify = findViewById(R.id.btn_detail_shopAdmin_Modify);

        Intent dShopAdmin_intent = getIntent();

        shopAdmin_id = dShopAdmin_intent.getStringExtra("sendAdminID");

        Log.d("DEBUG", shopAdmin_id);

        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DETAIL_SHOP_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
                Log.d("DEBUG", response);


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // check json object type response
//                    Log.d("DEBUG", String.valueOf(jObj));


                    if (!error) {

                        shopAdmin_ID.setText(jObj.getString("shopAdminId"));
                        shopAdmin_Name.setText(jObj.getString("shopAdminFName") + " " + jObj.getString("shopAdminLName"));
                        shopAdmin_Email.setText(jObj.getString("shopAdminEmail"));
                        shopAdmin_Contact.setText(jObj.getString("shopAdminPNumber"));
                        shopAdmin_RegDate.setText(jObj.getString("shopAdminRegDate"));

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
                params.put("shop_admin_id", shopAdmin_id);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        // when user click modify button
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntentWithValues(v.getContext(), EditShopAdmins.class, "sendShopAdminID", shopAdmin_id);

            }
        });


        // when delete button is pushed
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DELETE_SHOP_ADMIN, new Response.Listener<String>() {

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

                                Toast.makeText(getApplicationContext(), "The shop admin (" + shopAdmin_id + ") is successfully Deleted!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntent(v.getContext(), ListShopAdmins.class);

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
                        params.put("shop_admin_id", shopAdmin_id);
                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });


    }


}