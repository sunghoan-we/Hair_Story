package com.example.hairstory.admin.staff;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.hairstory.common.dto.Branch;
import com.example.hairstory.common.dto.Staff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListStaff extends CommonHeader {

    String tag_string_req = "list_staff";

    List<Staff> listStaff = new ArrayList<Staff>();
    RecyclerView recyclerViewStaff;
    Button btnAddStaff;
    Button btnSearchStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_staff);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("List Staff");

        EditText editListStaffStaffName = findViewById(R.id.editListStaffStaffName);
        EditText editListStaffBranchName = findViewById(R.id.editListStaffBranchName);

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);

        btnAddStaff = findViewById(R.id.btnAddStaff);
        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListStaff.this, RegisterStaff.class);
                startActivity(intent);
            }
        });
        btnSearchStaff = findViewById(R.id.btnSearchStaff);
        btnSearchStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListStaff(shopId, editListStaffStaffName.getText().toString(), editListStaffBranchName.getText().toString());
            }
        });


        recyclerViewStaff = findViewById(R.id.recyclerViewStaff);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerViewStaff.setLayoutManager(lm);

        getListStaff(shopId, editListStaffStaffName.getText().toString(), editListStaffBranchName.getText().toString());
    }



    private void getListStaff(String shopId, String staffName, String branchName) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_LIST_STAFF, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        listStaff.clear();
                        JSONArray arrayStaff = jObj.getJSONArray("staff");
                        for (int i = 0; i < arrayStaff.length(); i++) {

                            JSONObject temp = arrayStaff.getJSONObject(i);
                            Staff staff = new Staff();
                            staff.setUserID(temp.getString("staff_id"));
                            staff.setfName(temp.getString("f_name"));
                            staff.setlName(temp.getString("l_name"));
                            Branch branch = new Branch(temp.getInt("branch_id"), temp.getString("branch_name"));
                            staff.setBranch(branch);
                            listStaff.add(staff);
                        }

                        StaffAdapter staffAdapter = new StaffAdapter(listStaff, ListStaff.this);
                        recyclerViewStaff.setAdapter(staffAdapter);


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
                if (staffName != null && !staffName.isEmpty()) {
                    params.put("staffName", staffName);
                }
                if (branchName != null && !branchName.isEmpty()) {
                    params.put("branchName", branchName);
                }

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}