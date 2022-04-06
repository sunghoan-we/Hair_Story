package com.example.hairstory.admin.staff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class RegisterStaff extends CommonHeader {


    String tag_string_req = "register_staff";


    EditText editRegStaffFName;
    EditText editRegStaffLName;
    RadioButton rbnRegStaffMale;
    RadioButton rbnRegStaffFemale;
    RadioButton rbnRegStaffOthers;
    EditText editRegStaffTel1;
    EditText editRegStaffTel2;
    EditText editRegStaffTel3;
    EditText editRegStaffEmail;
    EditText editRegStaffDateOfBirth;
    EditText editRegStaffPhoto;
    EditText editRegStaffBranchId;
    TextView txtRegStaffShopName;

    Button btnRegStaffRegister;
    Button btnRegStaffReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_staff);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Register Staff");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterStaff.this, ListStaff.class);
                startActivity(intent);
            }
        });


        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);
        String shopName = preferences.getString(SHOP_NAME, null);
////TODO
//        shopName = "Shop01";

        editRegStaffFName = findViewById(R.id.editRegStaffFName);
        editRegStaffLName = findViewById(R.id.editRegStaffLName);
        rbnRegStaffMale = findViewById(R.id.rbnRegStaffMale);
        rbnRegStaffFemale = findViewById(R.id.rbnRegStaffFemale);
        rbnRegStaffOthers = findViewById(R.id.rbnRegStaffOthers);
        editRegStaffTel1 = findViewById(R.id.editRegStaffTel1);
        editRegStaffTel2 = findViewById(R.id.editRegStaffTel2);
        editRegStaffTel3 = findViewById(R.id.editRegStaffTel3);
        editRegStaffEmail = findViewById(R.id.editRegStaffEmail);
        editRegStaffDateOfBirth = findViewById(R.id.editRegStaffDateOfBirth);
        editRegStaffPhoto = findViewById(R.id.editRegStaffPhoto);

        editRegStaffBranchId = findViewById(R.id.editRegStaffBranchId);

        btnRegStaffRegister = findViewById(R.id.btnRegStaffRegister);
        btnRegStaffRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender;
                if (rbnRegStaffMale.isChecked()) {
                    gender = "M";
                } else if (rbnRegStaffFemale.isChecked()) {
                    gender = "F";
                } else {
                    gender = "O";
                }
                registerStaff(editRegStaffFName.getText().toString(), editRegStaffLName.getText().toString(), gender,
                        StringUtil.combineTelNum(editRegStaffTel1.getText().toString(), editRegStaffTel2.getText().toString(), editRegStaffTel3.getText().toString()),
                        editRegStaffEmail.getText().toString(), editRegStaffDateOfBirth.getText().toString(),
                        editRegStaffPhoto.getText().toString(), editRegStaffBranchId.getText().toString());


            }
        });

        btnRegStaffReset = findViewById(R.id.btnRegStaffReset);
        btnRegStaffReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRegStaffFName.setText("");
                editRegStaffLName.setText("");
                rbnRegStaffMale.setChecked(true);
                rbnRegStaffFemale.setChecked(false);
                rbnRegStaffOthers.setChecked(false);
                editRegStaffTel1.setText("");
                editRegStaffTel2.setText("");
                editRegStaffTel3.setText("");
                editRegStaffEmail.setText("");
                editRegStaffDateOfBirth.setText("");
                editRegStaffPhoto.setText("");
                editRegStaffBranchId.setText("");
                editRegStaffFName.requestFocus();
            }
        });
    }


    private void registerStaff(String fName, String lName, String gender, String phoneNum, String email, String dateOfBirth,  String photo, String branchId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_REGISTER_STAFF, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Staff Admin is registered successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterStaff.this, ListStaff.class);
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