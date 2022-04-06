package com.example.hairstory.member.memberinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.hairstory.member.search.SearchMemberShops;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailMemberMembership extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView member_ID;
    TextView member_Name;
    TextView member_Contact;
    TextView member_Email;
    TextView member_DOB;
    TextView member_RegDate;

    Button btnDelete;
    Button btnModify;

    String tag_string_req = "Detail Information of Account";

    String member_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_member_membership);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), SearchMemberShops.class);

            }
        });

        member_ID = findViewById(R.id.txt_detail_member_ID);
        member_Name = findViewById(R.id.txt_detail_member_Name);
        member_Contact = findViewById(R.id.txt_detail_member_Contact);
        member_Email = findViewById(R.id.txt_detail_member_Email);
        member_DOB = findViewById(R.id.txt_detail_member_DOB);
        member_RegDate = findViewById(R.id.txt_detail_member_RegDate);

        btnDelete = findViewById(R.id.btn_detail_member_Delete);
        btnModify = findViewById(R.id.btn_detail_member_Modify);



        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String member_id = preferences.getString(USER_ID, null);

//        Intent dMember_intent = getIntent();
//        member_id = dMember_intent.getStringExtra("sendMemberID");

        Log.d("DEBUG", member_id);

        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DETAIL_MEMBER_MEMBERSHIP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // check response from php-database
                Log.d("DEBUG", response);


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // check json object type response
//                    Log.d("DEBUG", String.valueOf(jObj));


                    if (!error) {

                        member_ID.setText(jObj.getString("memberId"));
                        member_Name.setText(jObj.getString("memberFName") + " " + jObj.getString("memberLName"));
                        member_Email.setText(jObj.getString("memberEmail"));
                        member_Contact.setText(jObj.getString("memberPNumber"));
                        member_DOB.setText(jObj.getString("memberDOB"));
                        member_RegDate.setText(jObj.getString("memberRegDate"));

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
                // Posting parameters to detail shop admin url
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_ID", member_id);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        // when user click modify button
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntentWithValues(v.getContext(), EditMemberMembership.class, "sendMemberID", member_id);

            }
        });


        // when delete button is pushed
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DELETE_MEMBER_MEMBERSHIP, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // check response from php-database
//                Log.d("DEBUG", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            // check json object type response
//                    Log.d("DEBUG", String.valueOf(jObj));
                            if (!error) {

                                Toast.makeText(getApplicationContext(), "The member (" + member_id + ") is successfully Deleted!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntent(v.getContext(), LoginMember.class);

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
                        // Posting parameters to detail menu url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("member_ID", member_id);
                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });

    }
}