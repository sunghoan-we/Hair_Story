package com.example.hairstory.admin.staff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.hairstory.common.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditStaff extends CommonHeader {

    String tag_string_req = "edit_staff";

    String staffId;
    EditText editStaffFName;
    EditText editStaffLName;
    RadioGroup radioGroupEditStaffGender;
    RadioButton rbnEditStaffMale;
    RadioButton rbnEditStaffFemale;
    RadioButton rbnEditStaffOthers;
    EditText editStaffTel1;
    EditText editStaffTel2;
    EditText editStaffTel3;
    EditText editStaffEmail;
    EditText editStaffDateOfBirth;
    EditText editStaffPhoto;
    EditText editStaffBranchId;
    TextView txtEditStaffShopName;

    Button btnEditStaffSave;
    Button btnEditStaffCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);

        staffId = getIntent().getStringExtra("staffId");
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopName = preferences.getString(SHOP_NAME, null);
////TODO
//        staffId = "1";
//        shopName = "Shop01";


        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Edit Staff");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStaff.this, DetailStaff.class);
                intent.putExtra("staffId", staffId);
                startActivity(intent);
            }
        });


        editStaffFName = findViewById(R.id.editStaffFName);
        editStaffLName = findViewById(R.id.editStaffLName);
        radioGroupEditStaffGender = findViewById(R.id.radioGroupEditStaffGender);
        rbnEditStaffMale = findViewById(R.id.rbnEditStaffMale);
        rbnEditStaffFemale = findViewById(R.id.rbnEditStaffFemale);
        rbnEditStaffOthers = findViewById(R.id.rbnEditStaffOthers);

        editStaffTel1 = findViewById(R.id.editStaffTel1);
        editStaffTel2 = findViewById(R.id.editStaffTel2);
        editStaffTel3 = findViewById(R.id.editStaffTel3);
        editStaffEmail = findViewById(R.id.editStaffEmail);
        editStaffDateOfBirth = findViewById(R.id.editStaffDateOfBirth);
        editStaffPhoto = findViewById(R.id.editStaffPhoto);
        editStaffBranchId = findViewById(R.id.editStaffBranchId);
        txtEditStaffShopName = findViewById(R.id.txtEditStaffShopName);


        txtEditStaffShopName.setText(shopName);
        getStaff(staffId);


        btnEditStaffSave = findViewById(R.id.btnEditStaffSave);
        btnEditStaffSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender;
                if (rbnEditStaffMale.isChecked()) {
                    gender = "M";
                } else if (rbnEditStaffFemale.isChecked()) {
                    gender = "F";
                } else {
                    gender = "O";
                }
                updateStaff(staffId, editStaffFName.getText().toString(), editStaffLName.getText().toString(), gender
                        , StringUtil.combineTelNum(editStaffTel1.getText().toString(),editStaffTel2.getText().toString(),editStaffTel3.getText().toString())
                        , editStaffEmail.getText().toString(), editStaffDateOfBirth.getText().toString()
                        , editStaffPhoto.getText().toString(), editStaffBranchId.getText().toString());
            }
        });
        btnEditStaffCancel = findViewById(R.id.btnEditStaffCancel);
        btnEditStaffCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStaff.this, DetailStaff.class);
                intent.putExtra("staffId", staffId);
                startActivity(intent);
            }
        });

    }


    private void getStaff(String branchAdminId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_STAFF, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        JSONObject staff = jObj.getJSONObject("staff");

                        editStaffFName.setText(staff.getString("f_name"));
                        editStaffLName.setText(staff.getString("l_name"));
                        String[] telNum = StringUtil.splitTelNum(staff.getString("phone_num"));
                        if (telNum != null && telNum.length == 3) {
                            editStaffTel1.setText(telNum[0]);
                            editStaffTel2.setText(telNum[1]);
                            editStaffTel3.setText(telNum[2]);
                        }

                        String gender = staff.getString("gender");
                        if (gender.equals("F")) {
                            rbnEditStaffFemale.setChecked(true);
                        } else if (gender.equals("M")) {
                            rbnEditStaffMale.setChecked(true);
                        } else {
                            rbnEditStaffOthers.setChecked(true);
                        }
                        editStaffEmail.setText(staff.getString("email"));
                        editStaffDateOfBirth.setText(staff.getString("date_of_birth"));
                        editStaffBranchId.setText(staff.getString("branch_id"));

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
                params.put("staffId", staffId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void updateStaff(String staffId, String fName, String lName, String gender, String phoneNum, String email, String dateOfBirth,  String photo, String branchId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_UPDATE_STAFF, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Staff is updated successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditStaff.this, DetailStaff.class);
                        intent.putExtra("staffId", staffId);
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

                params.put("staffId", staffId);
                params.put("fName", fName);
                params.put("lName", lName);
                params.put("gender", gender);
                params.put("phoneNum", phoneNum);
                params.put("dateOfBirth", dateOfBirth);
                params.put("email", email);
                params.put("photo", photo);
                params.put("branchId", branchId);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}