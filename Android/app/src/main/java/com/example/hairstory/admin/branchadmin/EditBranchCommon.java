package com.example.hairstory.admin.branchadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.admin.branch.DetailBranch;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditBranchCommon extends CommonHeader {
    String tag_string_req = "edit_branch_admin";

    HashMap<String, Integer> map = new HashMap<String, Integer>();
    String branchAdminId;
    TextView txtEditBranchAdminID;
    EditText editBranchAdminPassword;
    EditText editBranchAdminFName;
    EditText editBranchAdminLName;
    EditText editBranchAdminTelNum1;
    EditText editBranchAdminTelNum2;
    EditText editBranchAdminTelNum3;
    EditText editBranchAdminDateOfBirth;
    EditText editBranchAdminEmail;
    TextView txtEditBranchAdminShopName;
    Spinner spinnerEditBranchAdminBranches;


    Button btnEditBranchAdminSave;
    Button btnEditBranchAdminCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch_admin);

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);

        branchAdminId = getIntent().getStringExtra("branchAdminId");
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopName = preferences.getString(SHOP_NAME, null);
////TODO
//        branchAdminId = "branchAdmin02";
//        shopName = "Shop01";


        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Edit Branch Admin");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBranchCommon.this, DetailBranchCommon.class);
                intent.putExtra("branchAdminId", branchAdminId);
                startActivity(intent);
            }
        });

        txtEditBranchAdminID = findViewById(R.id.txtEditBranchAdminID);
        editBranchAdminPassword = findViewById(R.id.editBranchAdminPassword);
        editBranchAdminFName = findViewById(R.id.editBranchAdminFName);
        editBranchAdminLName = findViewById(R.id.editBranchAdminLName);
        editBranchAdminTelNum1 = findViewById(R.id.editBranchAdminTelNum1);
        editBranchAdminTelNum2 = findViewById(R.id.editBranchAdminTelNum2);
        editBranchAdminTelNum3 = findViewById(R.id.editBranchAdminTelNum3);
        editBranchAdminDateOfBirth = findViewById(R.id.editBranchAdminDateOfBirth);
        editBranchAdminEmail = findViewById(R.id.editBranchAdminEmail);
        txtEditBranchAdminShopName = findViewById(R.id.txtEditBranchAdminShopName);
        spinnerEditBranchAdminBranches = findViewById(R.id.spinnerEditBranchAdminBranches);
        spinnerEditBranchAdminBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int farmSeq = map.get(spinnerEditBranchAdminBranches.getSelectedItem());
                Log.d("DEBUG", String.valueOf(farmSeq));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        txtEditBranchAdminShopName.setText(shopName);
        setListBranch(shopId);
        getBranchAdmin(branchAdminId);

        btnEditBranchAdminSave = findViewById(R.id.btnEditBranchAdminSave);
        btnEditBranchAdminSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", String.valueOf(map.get(spinnerEditBranchAdminBranches.getSelectedItem())));
                updateBranchAdmin(txtEditBranchAdminID.getText().toString(), editBranchAdminPassword.getText().toString()
                        , editBranchAdminFName.getText().toString(), editBranchAdminLName.getText().toString()
                        , StringUtil.combineTelNum(editBranchAdminTelNum1.getText().toString(),editBranchAdminTelNum2.getText().toString(),editBranchAdminTelNum3.getText().toString())
                        , editBranchAdminDateOfBirth.getText().toString(), editBranchAdminEmail.getText().toString(), String.valueOf(map.get(spinnerEditBranchAdminBranches.getSelectedItem())));
            }
        });
        btnEditBranchAdminCancel = findViewById(R.id.btnEditBranchAdminCancel);
        btnEditBranchAdminCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBranchCommon.this, DetailBranch.class);
                intent.putExtra("branchAdminId", branchAdminId);
                startActivity(intent);
            }
        });

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

                        txtEditBranchAdminID.setText(branchAdmin.getString("admin_id"));
                        editBranchAdminPassword.setText(branchAdmin.getString("password"));
                        editBranchAdminFName.setText(branchAdmin.getString("f_name"));
                        editBranchAdminLName.setText(branchAdmin.getString("l_name"));
                        String[] telNum = StringUtil.splitTelNum(branchAdmin.getString("phone_num"));
                        if (telNum != null && telNum.length == 3) {
                            editBranchAdminTelNum1.setText(telNum[0]);
                            editBranchAdminTelNum2.setText(telNum[1]);
                            editBranchAdminTelNum3.setText(telNum[2]);
                        }
                        editBranchAdminDateOfBirth.setText(branchAdmin.getString("date_of_birth"));
                        editBranchAdminEmail.setText(branchAdmin.getString("email"));
                        spinnerEditBranchAdminBranches.setSelection(StringUtil.getIndex(spinnerEditBranchAdminBranches, branchAdmin.getString("branch_name")));




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

    private void updateBranchAdmin(String branchAdminId, String password, String fName, String lName, String phoneNum, String dateOfBirth, String email, String branchId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_UPDATE_BRANCH_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Branch Admin is updated successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditBranchCommon.this, DetailBranchCommon.class);
                        intent.putExtra("branchAdminId", branchAdminId);
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

                params.put("adminId", branchAdminId);
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

    private void setListBranch(String shopId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_LIST_BRANCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        JSONArray arrayBranch = jObj.getJSONArray("branch");
                        List<String> branches = new ArrayList<String>();
                        for (int i = 0; i < arrayBranch.length(); i++) {

                            JSONObject temp = arrayBranch.getJSONObject(i);
                            branches.add(temp.getString("branch_name"));
                            map.put(temp.getString("branch_name"), temp.getInt("branch_id"));
                            Log.d("DEBUG", String.valueOf(temp.getInt("branch_id")));
                        }

                        // Creating adapter for spinner
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EditBranchCommon.this, android.R.layout.simple_spinner_item, branches);
                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // attaching data adapter to spinner
                        spinnerEditBranchAdminBranches.setAdapter(dataAdapter);

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
                params.put("shopId", shopId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}