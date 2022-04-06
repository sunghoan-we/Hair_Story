package com.example.hairstory.admin.branch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditBranch extends CommonHeader {
    String tag_string_req = "edit_branch";

    String branchId;
    EditText editBranchName;
    EditText editAddress;
    EditText editTelNum1;
    EditText editTelNum2;
    EditText editTelNum3;
    EditText editOpenTime;
    EditText editCloseTime;

    Button btnSave;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch);
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);

        branchId = getIntent().getStringExtra("branchId");
////TODO
//        branchId="1";

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Edit Branch");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBranch.this, DetailBranch.class);
                intent.putExtra("branchId", branchId);
                startActivity(intent);
            }
        });


        editBranchName =findViewById(R.id.editBranchName);
        editAddress =findViewById(R.id.editAddress);
        editTelNum1 =findViewById(R.id.editTelNum1);
        editTelNum2 =findViewById(R.id.editTelNum2);
        editTelNum3 =findViewById(R.id.editTelNum3);
        editOpenTime =findViewById(R.id.editOpenTime);
        editCloseTime =findViewById(R.id.editCloseTime);

        getBranch(branchId);


        btnSave = findViewById(R.id.btnEditBranchSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBranch(shopId, branchId, editBranchName.getText().toString(), editAddress.getText().toString()
                        , StringUtil.combineTelNum(editTelNum1.getText().toString(),editTelNum2.getText().toString(),editTelNum3.getText().toString())
                        , editOpenTime.getText().toString(), editCloseTime.getText().toString());
            }
        });
        btnCancel = findViewById(R.id.btnEditBranchCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBranch.this, DetailBranch.class);
                intent.putExtra("branchId", branchId);
                startActivity(intent);
            }
        });

    }


    private void getBranch(String branchId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_BRANCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        JSONObject branch = jObj.getJSONObject("branch");
                        editBranchName.setText(branch.getString("branch_name"));
                        editAddress.setText(branch.getString("address"));
                        String[] telNum = StringUtil.splitTelNum(branch.getString("contact"));
                        if (telNum != null && telNum.length == 3) {
                            editTelNum1.setText(telNum[0]);
                            editTelNum2.setText(telNum[1]);
                            editTelNum3.setText(telNum[2]);
                        }
                        editOpenTime.setText(branch.getString("open_time"));
                        editCloseTime.setText(branch.getString("close_time"));

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


    private void updateBranch(String shopId, String branchId, String branchName, String address, String contact, String from, String to) {
        Log.d("DEBUG", shopId);
        Log.d("DEBUG", branchId);
        Log.d("DEBUG", branchName);
        Log.d("DEBUG", address);
        Log.d("DEBUG", contact);
        Log.d("DEBUG", from);
        Log.d("DEBUG", to);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_UPDATE_BRANCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Branch is updated successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditBranch.this, DetailBranch.class);
                        intent.putExtra("branchId", branchId);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in update Branch. Get the error message
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
                params.put("shopId", shopId);
                params.put("branchId", branchId);
                params.put("branchName", branchName);
                params.put("address", address);
                params.put("branchContact", contact);
                params.put("from", from);
                params.put("to", to);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}