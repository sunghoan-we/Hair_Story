package com.example.hairstory.admin.branch;

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

public class DetailBranch extends CommonHeader {
    String tag_string_req = "detail_branch";

    String branchId;
    TextView txtBranchName;
    TextView txtAddress;
    TextView txtContact;
    TextView txtOpenTime;
    TextView txtCloseTime;
    TextView txtRegDate;

    Button btnModify;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_branch);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Detail Branch");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBranch.this, ListBranch.class);
                startActivity(intent);
            }
        });


        branchId = getIntent().getStringExtra("branchId");
////TODO
//        branchId="1";

        txtBranchName =findViewById(R.id.txtBranchName);
        txtAddress =findViewById(R.id.txtAddress);
        txtContact =findViewById(R.id.txtContact);
        txtOpenTime =findViewById(R.id.txtOpenTime);
        txtCloseTime =findViewById(R.id.txtCloseTime);
        txtRegDate =findViewById(R.id.txtRegDate);

        btnModify = findViewById(R.id.btnModify);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBranch.this, EditBranch.class);
                intent.putExtra("branchId", branchId);
                startActivity(intent);
            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBranch(branchId);
            }
        });

        getBranchAdmin(branchId);
    }


    private void getBranchAdmin(String branchId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_BRANCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        JSONObject branchAdmin = jObj.getJSONObject("branch");
                        txtBranchName.setText(branchAdmin.getString("branch_name"));
                        txtAddress.setText(branchAdmin.getString("address"));
                        txtContact.setText(branchAdmin.getString("contact"));
                        txtOpenTime.setText(branchAdmin.getString("open_time"));
                        txtCloseTime.setText(branchAdmin.getString("close_time"));
                        txtRegDate.setText(branchAdmin.getString("reg_date"));

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
                params.put("branchId", branchId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void deleteBranch(String branchId) {

        Log.d("DEBUG", branchId);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_DELETE_BRANCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Branch is deleted successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailBranch.this, ListBranch.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in delete Branch. Get the error message
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
                params.put("branchId", branchId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



}