package com.example.hairstory.admin.branchadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailBranchCommon extends CommonHeader {

    String tag_string_req = "detail_branch_admin";

    TextView txtDetailBranchAdminShopName;
    TextView txtDetailBranchAdminBranchName;
    TextView txtDetailBranchAdminID;
    TextView txtDetailBranchAdminName;
    TextView txtDetailBranchAdminEmail;
    TextView txtDetailBranchAdminContact;
    TextView txtDetailBranchAdminDateOfBirth;
    TextView txtDetailBranchAdminRegDate;

    Button btnBranchAdminModify;
    Button btnBranchAdminDelete;

    String branchAdminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_branch_admin);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Detail Branch Admin");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBranchCommon.this, ListBranchCommon.class);
                startActivity(intent);
            }
        });


        branchAdminId = getIntent().getStringExtra("branchAdminId");
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopName = preferences.getString(SHOP_NAME, null);
////TODO
//        branchAdminId = "branchAdmin01";
//        shopName = "Shop01";



        txtDetailBranchAdminShopName = findViewById(R.id.txtDetailBranchAdminShopName);
        txtDetailBranchAdminBranchName = findViewById(R.id.txtDetailBranchAdminBranchName);
        txtDetailBranchAdminID = findViewById(R.id.txtDetailBranchAdminID);
        txtDetailBranchAdminName = findViewById(R.id.txtDetailBranchAdminName);
        txtDetailBranchAdminEmail = findViewById(R.id.txtDetailBranchAdminEmail);
        txtDetailBranchAdminContact = findViewById(R.id.txtDetailBranchAdminContact);
        txtDetailBranchAdminDateOfBirth = findViewById(R.id.txtDetailBranchAdminDateOfBirth);
        txtDetailBranchAdminRegDate = findViewById(R.id.txtDetailBranchAdminRegDate);

        btnBranchAdminModify = findViewById(R.id.btnBranchAdminModify);
        btnBranchAdminModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBranchCommon.this, EditBranchCommon.class);
                intent.putExtra("branchAdminId", branchAdminId);
                startActivity(intent);
            }
        });
        btnBranchAdminDelete = findViewById(R.id.btnBranchAdminDelete);
        btnBranchAdminDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBranchAdmin(branchAdminId);
            }
        });

        txtDetailBranchAdminShopName.setText(shopName);
        getBranchAdmin(branchAdminId);

    }

    private void getBranchAdmin(String branchAdminId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_BRANCH_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        JSONObject branchAdmin = jObj.getJSONObject("branchAdmin");

                        txtDetailBranchAdminBranchName.setText(branchAdmin.getString("branch_name"));
                        txtDetailBranchAdminID.setText(branchAdmin.getString("admin_id"));
                        txtDetailBranchAdminName.setText(branchAdmin.getString("f_name")+" "+branchAdmin.getString("l_name"));
                        txtDetailBranchAdminEmail.setText(branchAdmin.getString("email"));
                        txtDetailBranchAdminContact.setText(branchAdmin.getString("phone_num"));
                        txtDetailBranchAdminDateOfBirth.setText(branchAdmin.getString("date_of_birth"));
                        txtDetailBranchAdminRegDate.setText(branchAdmin.getString("reg_date"));

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
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("adminId", branchAdminId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void deleteBranchAdmin(String branchAdminId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_DELETE_BRANCH_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Branch Admin is updated successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailBranchCommon.this, ListBranchCommon.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in delete Branch Admin. Get the error message
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
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();

                params.put("adminId", branchAdminId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}