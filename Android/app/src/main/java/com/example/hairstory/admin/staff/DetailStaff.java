package com.example.hairstory.admin.staff;

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

public class DetailStaff extends CommonHeader {

    String tag_string_req = "detail_staff";

    TextView txtDetailStaffShopName;
    TextView txtDetailStaffBranchName;
    TextView txtDetailStaffName;
    TextView txtDetailStaffGender;
    TextView txtDetailStaffContact;
    TextView txtDetailStaffEmail;
    TextView txtDetailStaffDateOfBirth;
    TextView txtDetailStaffRegDate;

    Button btnDetailStaffModify;
    Button btnDetailStaffDelete;

    String staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_staff);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Detail Staff");
        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailStaff.this, ListStaff.class);
                startActivity(intent);
            }
        });


        staffId = getIntent().getStringExtra("staffId");
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopName = preferences.getString(SHOP_NAME, null);
////TODO
//        staffId = "1";
//        shopName = "Shop01";


        txtDetailStaffShopName = findViewById(R.id.txtDetailStaffShopName);
        txtDetailStaffBranchName = findViewById(R.id.txtDetailStaffBranchName);
        txtDetailStaffName =  findViewById(R.id.txtDetailStaffName);
        txtDetailStaffGender = findViewById(R.id.txtDetailStaffGender);
        txtDetailStaffContact = findViewById(R.id.txtDetailStaffContact);
        txtDetailStaffEmail = findViewById(R.id.txtDetailStaffEmail);
        txtDetailStaffDateOfBirth = findViewById(R.id.txtDetailStaffDateOfBirth);
        txtDetailStaffRegDate = findViewById(R.id.txtDetailStaffRegDate);


        btnDetailStaffModify = findViewById(R.id.btnDetailStaffModify);
        btnDetailStaffModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailStaff.this, EditStaff.class);
                intent.putExtra("staffId", staffId);
                startActivity(intent);
            }
        });
        btnDetailStaffDelete = findViewById(R.id.btnDetailStaffDelete);
        btnDetailStaffDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStaff(staffId);
            }
        });

        txtDetailStaffShopName.setText(shopName);
        getStaff(staffId);
    }

    private void getStaff(String staffId) {
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

                        txtDetailStaffBranchName.setText(staff.getString("branch_name"));
                        txtDetailStaffName.setText(staff.getString("f_name")+" "+staff.getString("l_name"));
                        txtDetailStaffGender.setText(staff.getString("gender"));
                        txtDetailStaffContact.setText(staff.getString("phone_num"));
                        txtDetailStaffEmail.setText(staff.getString("email"));
                        txtDetailStaffDateOfBirth.setText(staff.getString("date_of_birth"));
                        txtDetailStaffRegDate.setText(staff.getString("reg_date"));


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

    private void deleteStaff(String staffId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_DELETE_STAFF, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Staff is updated successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailStaff.this, ListStaff.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in delete Staff. Get the error message
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
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}