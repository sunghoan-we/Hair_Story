package com.example.hairstory.admin.branchadmin;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.dto.BranchAdmin;
import com.example.hairstory.common.dto.Branch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBranchCommon extends CommonHeader {
    String tag_string_req = "list_branch_admin";

    List<BranchAdmin> listBranchAdmin = new ArrayList<BranchAdmin>();
    RecyclerView recyclerViewBranchAdmins;
    Button btnAddBranchAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_branch_admin);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("List Branch Admin");

        btnAddBranchAdmin = findViewById(R.id.btnAddBranchAdmin);
        btnAddBranchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBranchCommon.this, RegisterBranchCommon.class);
                startActivity(intent);
            }
        });
        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String shopId = preferences.getString(SHOP_ID, null);

//// TODO
//        shopId = "1";

        recyclerViewBranchAdmins = findViewById(R.id.recyclerViewBranchAdmins);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerViewBranchAdmins.setLayoutManager(lm);

        getListBranchAdmin(shopId);
    }



    private void getListBranchAdmin(String shopId) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_GET_LIST_BRANCH_ADMIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        JSONArray arrayBranch = jObj.getJSONArray("branch_admin");
                        for (int i = 0; i < arrayBranch.length(); i++) {

                            JSONObject temp = arrayBranch.getJSONObject(i);
                            BranchAdmin branchAdmin = new BranchAdmin();
                            branchAdmin.setUserID(temp.getString("admin_id"));
                            branchAdmin.setfName(temp.getString("f_name"));
                            branchAdmin.setlName(temp.getString("l_name"));
                            Branch branch = new Branch(temp.getInt("branch_id"), temp.getString("branch_name"));
                            branchAdmin.setBranch(branch);
                            listBranchAdmin.add(branchAdmin);
                        }

                        BranchAdminAdapter branchAdminAdapter = new BranchAdminAdapter(listBranchAdmin, ListBranchCommon.this);
                        recyclerViewBranchAdmins.setAdapter(branchAdminAdapter);

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