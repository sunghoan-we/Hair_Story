package com.example.hairstory.admin.branchadmin;

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

public class RegisterBranchCommon extends CommonHeader {

    String tag_string_req = "register_branch_admin";

    EditText editRegBranchAdminID;
    EditText editRegBranchAdminPassword;
    EditText editRegBranchAdminFName;
    EditText editRegBranchAdminLName;
    EditText editRegBranchAdminTelNum1;
    EditText editRegBranchAdminTelNum2;
    EditText editRegBranchAdminTelNum3;
    EditText editRegBranchAdminDateOfBirth;
    EditText editRegBranchAdminEmail;
    EditText editRegBranchAdminBranchId;

    Button btnRegBranchAdminRegister;
    Button btnRegBranchAdminReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_branch_admin);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Register Branch Admin");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterBranchCommon.this, ListBranchCommon.class);
                startActivity(intent);
            }
        });


        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);
        String shopName = preferences.getString(SHOP_NAME, null);
////TODO
//        shopName = "Shop01";


        editRegBranchAdminID = findViewById(R.id.editRegBranchAdminID);
        editRegBranchAdminPassword = findViewById(R.id.editRegBranchAdminPassword);
        editRegBranchAdminFName = findViewById(R.id.editRegBranchAdminFName);
        editRegBranchAdminLName = findViewById(R.id.editRegBranchAdminLName);
        editRegBranchAdminTelNum1 = findViewById(R.id.editRegBranchAdminTelNum1);
        editRegBranchAdminTelNum2 = findViewById(R.id.editRegBranchAdminTelNum2);
        editRegBranchAdminTelNum3 = findViewById(R.id.editRegBranchAdminTelNum3);
        editRegBranchAdminDateOfBirth = findViewById(R.id.editRegBranchAdminDateOfBirth);
        editRegBranchAdminEmail = findViewById(R.id.editRegBranchAdminEmail);
        editRegBranchAdminBranchId = findViewById(R.id.editRegBranchAdminBranchId);

        btnRegBranchAdminRegister = findViewById(R.id.btnRegBranchAdminRegister);
        btnRegBranchAdminReset = findViewById(R.id.btnRegBranchAdminReset);

        btnRegBranchAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBranch(editRegBranchAdminID.getText().toString(), editRegBranchAdminPassword.getText().toString(),
                        editRegBranchAdminFName.getText().toString(), editRegBranchAdminLName.getText().toString(),
                        StringUtil.combineTelNum(editRegBranchAdminTelNum1.getText().toString(), editRegBranchAdminTelNum2.getText().toString(), editRegBranchAdminTelNum3.getText().toString()),
                        editRegBranchAdminDateOfBirth.getText().toString(), editRegBranchAdminEmail.getText().toString(), editRegBranchAdminBranchId.getText().toString());


            }
        });

        btnRegBranchAdminReset = findViewById(R.id.btnRegBranchAdminReset);
        btnRegBranchAdminReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRegBranchAdminID.setText("");
                editRegBranchAdminPassword.setText("");
                editRegBranchAdminFName.setText("");
                editRegBranchAdminLName.setText("");
                editRegBranchAdminTelNum1.setText("");
                editRegBranchAdminTelNum2.setText("");
                editRegBranchAdminTelNum3.setText("");
                editRegBranchAdminDateOfBirth.setText("");
                editRegBranchAdminEmail.setText("");
                editRegBranchAdminBranchId.setText("");
                editRegBranchAdminID.requestFocus();
            }
        });
    }


    private void registerBranch(String adminId, String password, String fName, String lName, String phoneNum, String dateOfBirth, String email, String branchId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_REGISTER_BRANCH_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Branch Admin is registered successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterBranchCommon.this, ListBranchCommon.class);
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
                params.put("adminId", adminId);
                params.put("password", password);
                params.put("fName", fName);
                params.put("lName", lName);
                params.put("phoneNum", phoneNum);
                params.put("dateOfBirth", dateOfBirth);
                params.put("email", email);
                params.put("branchId", branchId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}