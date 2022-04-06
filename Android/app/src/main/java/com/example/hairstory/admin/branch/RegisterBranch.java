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

public class RegisterBranch extends CommonHeader {
    String tag_string_req = "register_branch";

    EditText txtBranchName;
    EditText txtBranchAddress;
    EditText txtBranchTelNum1;
    EditText txtBranchTelNum2;
    EditText txtBranchTelNum3;
    EditText txtFrom;
    EditText txtTo;

    Button btnBranchRegister;
    Button btnBranchReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_branch);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Register Branch");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterBranch.this, ListBranch.class);
                startActivity(intent);
            }
        });

        txtBranchName = findViewById(R.id.txtBranchName);
        txtBranchAddress = findViewById(R.id.txtBranchAddress);
        txtBranchTelNum1 = findViewById(R.id.txtBranchTelNum1);
        txtBranchTelNum2 = findViewById(R.id.txtBranchTelNum2);
        txtBranchTelNum3 = findViewById(R.id.txtBranchTelNum3);
        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);
        btnBranchRegister = findViewById(R.id.btnBranchRegister);
        btnBranchRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBranch(shopId, txtBranchName.getText().toString(),
                        txtBranchAddress.getText().toString(),
                        StringUtil.combineTelNum(txtBranchTelNum1.getText().toString(), txtBranchTelNum2.getText().toString(), txtBranchTelNum3.getText().toString()),
                        txtFrom.getText().toString(), txtTo.getText().toString());
            }
        });

        btnBranchReset = findViewById(R.id.btnBranchReset);
        btnBranchReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBranchName.setText("");
                txtBranchAddress.setText("");
                txtBranchTelNum1.setText("");
                txtBranchTelNum2.setText("");
                txtBranchTelNum3.setText("");
                txtFrom.setText("");
                txtTo.setText("");
                txtBranchName.requestFocus();
            }
        });
    }


    private void registerBranch(String shopId, String branchName, String address, String contact, String from, String to) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_REGISTER_BRANCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Branch is registered successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterBranch.this, ListBranch.class);
                        startActivity(intent);
                    } else {
                        // Error in register Branch. Get the error message
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
                params.put("shopId", shopId);
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