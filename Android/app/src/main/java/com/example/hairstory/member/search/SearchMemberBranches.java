package com.example.hairstory.member.search;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.example.hairstory.common.util.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchMemberBranches extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;
    TextView shopName;

    String tag_string_req = "Search Branches";
    String shop_id, shop_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_member_branches);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        shopName = findViewById(R.id.data_search_member_branches_sName);

        headerPageName.setText(tag_string_req);


        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), SearchMemberShops.class);

            }
        });

        // get intent information from previous page
        Intent sSearchBranches_intent = getIntent();
        shop_id = sSearchBranches_intent.getStringExtra("sendShopID");
        shop_name = sSearchBranches_intent.getStringExtra("sendShopName");

        shopName.setText(shop_name);

        // add recyclerview layout
        RecyclerView recyclerView = findViewById(R.id.rcv_search_member_branches);

        // set layout design as linear view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // run db with appropriate php address
        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_SEARCH_MEMBER_BRANCHES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
//                Log.d("DEBUG", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        ArrayList<String> branchIds = new ArrayList<>();
                        ArrayList<String> branchNames = new ArrayList<>();
                        ArrayList<String> branchAddresses = new ArrayList<>();
                        ArrayList<String> branchContacts = new ArrayList<>();
                        ArrayList<String> branchOpenTimes = new ArrayList<>();
                        ArrayList<String> branchCloseTimes = new ArrayList<>();
                        ArrayList<String> branchRegDates = new ArrayList<>();


                        for (int i = 1; i < jObj.length(); i++) {

                            JSONObject branchList = jObj.getJSONObject(String.valueOf(i));

                            branchIds.add(branchList.getString("Branch_ID"));
                            branchNames.add(branchList.getString("Branch_Name"));
                            branchAddresses.add(branchList.getString("Branch_Address"));
                            branchContacts.add(branchList.getString("Branch_Contact"));
                            branchOpenTimes.add(branchList.getString("Branch_OpenTime"));
                            branchCloseTimes.add(branchList.getString("Branch_CloseTime"));
                            branchRegDates.add(branchList.getString("Branch_RegDate"));

                        }

                        recViewAdapter_Search_Branches newAdapter = new recViewAdapter_Search_Branches(branchIds,
                                branchNames,
                                branchAddresses,
                                branchContacts,
                                branchOpenTimes,
                                branchCloseTimes,
                                branchRegDates,
                                shop_id,
                                shop_name
                        );
                        recyclerView.setAdapter(newAdapter);

                    } else {
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("shop_id", shop_id);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}