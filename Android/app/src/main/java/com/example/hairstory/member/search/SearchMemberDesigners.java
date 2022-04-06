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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchMemberDesigners extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;
    TextView branchName;

    String tag_string_req = "Search Designers";

    String branch_id, branch_name, shop_id, shop_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_member_designers);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);
        branchName = findViewById(R.id.label_search_member_designers_bName);

        // get intent information from previous page
        Intent sSearchDesigners_intent = getIntent();
        shop_id = sSearchDesigners_intent.getStringExtra("sendShopId");
        shop_name = sSearchDesigners_intent.getStringExtra("sendShopName");
        branch_id = sSearchDesigners_intent.getStringExtra("sendBranchId");
        branch_name = sSearchDesigners_intent.getStringExtra("sendBranchName");


        branchName.setText(branch_name);



        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CommonFunctions.changeIntentWithValues(v.getContext(), SearchMemberBranches.class, "sendShopID", shop_id, "sendShopName", shop_name);
                Intent intent = new Intent(v.getContext(),
                        SearchMemberBranches.class);

                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("sendShopID", shop_id);
                intent.putExtra("sendShopName", shop_name);


                v.getContext().startActivity(intent);

            }
        });



        // add recyclerview layout
        RecyclerView recyclerView = findViewById(R.id.rcv_search_member_designers);

        // set layout design as linear view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // run db with appropriate php address
        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_SEARCH_MEMBER_DESIGNERS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
//                Log.d("DEBUG", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        ArrayList<String> designerIds = new ArrayList<>();
                        ArrayList<String> designerFNames = new ArrayList<>();
                        ArrayList<String> designerLNames = new ArrayList<>();
                        ArrayList<String> designerContacts = new ArrayList<>();
                        ArrayList<String> designerEmails = new ArrayList<>();
                        ArrayList<String> designerDOBs = new ArrayList<>();
                        ArrayList<String> designerBranchIDs = new ArrayList<>();
                        ArrayList<String> designerRegDates = new ArrayList<>();
                        ArrayList<String> designerUpDates = new ArrayList<>();



                        for (int i = 1; i < jObj.length(); i++) {

                            JSONObject branchList = jObj.getJSONObject(String.valueOf(i));

                            designerIds.add(branchList.getString("Designer_ID"));
                            designerFNames.add(branchList.getString("Designer_FName"));
                            designerLNames.add(branchList.getString("Designer_LName"));
                            designerContacts.add(branchList.getString("Designer_Contact"));
                            designerEmails.add(branchList.getString("Designer_Email"));
                            designerDOBs.add(branchList.getString("Designer_DOB"));
                            designerBranchIDs.add(branchList.getString("Designer_BranchID"));
                            designerRegDates.add(branchList.getString("Designer_RegDate"));
                            designerUpDates.add(branchList.getString("Designer_UpDate"));

                        }

                        recViewAdapter_Search_Designers newAdapter = new recViewAdapter_Search_Designers(designerIds,
                                designerFNames,
                                designerLNames,
                                designerContacts,
                                designerEmails,
                                designerDOBs,
                                designerBranchIDs,
                                designerRegDates,
                                designerUpDates,
                                shop_id, shop_name,
                                branch_id, branch_name
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
                params.put("branch_id", branch_id);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}