package com.example.hairstory.member.search;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.LoginMember;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.util.CommonFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchMemberShops extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    String tag_string_req = "Search Shops";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_member_shops);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), LoginMember.class);

            }
        });

        // add recyclerview layout
        RecyclerView recyclerView = findViewById(R.id.rcv_search_member_shops);

        // set layout design as linear view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // run db with appropriate php address
        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_SEARCH_MEMBER_SHOPS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
//                Log.d("DEBUG", response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        ArrayList<String> shopIds = new ArrayList<>();
                        ArrayList<String> shopNames = new ArrayList<>();
                        ArrayList<String> shopRegDates = new ArrayList<>();


                        for (int i = 1; i < jObj.length(); i++) {

                            JSONObject shopList = jObj.getJSONObject(String.valueOf(i));

                            shopIds.add(shopList.getString("Shop_ID"));
                            shopNames.add(shopList.getString("Shop_Name"));
                            shopRegDates.add(shopList.getString("Shop_RegDate"));

                        }

                        recViewAdapter_Search_Shops newAdapter = new recViewAdapter_Search_Shops(shopIds,
                                shopNames,
                                shopRegDates
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
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}