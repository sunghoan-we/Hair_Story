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

public class EditMemberMembership extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView memberPassword;
    TextView memberContact1;
    TextView memberContact2;
    TextView memberContact3;
    TextView memberEmailAdd;
    TextView memberEmailDomain;

    String member_ID = "";

    Button btnSubmit;
    Button btnReset;

    String tag_string_req = "Edit Member Account Information";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member_membership);

        memberPassword = findViewById(R.id.input_edit_member_password);
        memberContact1 = findViewById(R.id.input_edit_member_contact01);
        memberContact2 = findViewById(R.id.input_edit_member_contact02);
        memberContact3 = findViewById(R.id.input_edit_member_contact03);
        memberEmailAdd = findViewById(R.id.input_edit_member_address);
        memberEmailDomain = findViewById(R.id.input_edit_member_email);

        btnSubmit = findViewById(R.id.btn_edit_member_Submit);
        btnReset = findViewById(R.id.btn_edit_member_Reset);

        Intent eMenu_intent = getIntent();
        member_ID = eMenu_intent.getStringExtra("sendMemberID");

        Log.d("DEBUG", member_ID);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntentWithValues(v.getContext(), DetailMemberMembership.class, "sendMemberID", member_ID);

            }
        });

        StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_DETAIL_MEMBER_MEMBERSHIP, new Response.Listener<String>() {

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

                        String getContactNum = jObj.getString("memberPNumber");
                        String getEmailAddress = jObj.getString("memberEmail");

                        ArrayList<String> formatPhoneNum = CommonFunctions.formatPhoneNumber(getContactNum);
                        ArrayList<String> formatEmailAddress = CommonFunctions.formatEmailAddress(getEmailAddress);

                        memberContact1.setText(formatPhoneNum.get(0).toString());
                        memberContact2.setText(formatPhoneNum.get(1).toString());
                        memberContact3.setText(formatPhoneNum.get(2).toString());

                        memberEmailAdd.setText(formatEmailAddress.get(0).toString());
                        memberEmailDomain.setText(formatEmailAddress.get(1).toString());

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
                params.put("member_ID", member_ID);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        // when Submit button is pushed after member change the value
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_EDIT_MEMBER_MEMBERSHIP, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG", response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "The member information (" + member_ID + ") is successfully Updated!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntentWithValues(v.getContext(), DetailMemberMembership.class, "sendMemberID", member_ID);

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
                        // Posting parameters to edit shop admin url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("member_ID", member_ID);
                        params.put("member_PW", memberPassword.getText().toString());
                        params.put("member_Contact", memberContact1.getText().toString() +
                                memberContact2.getText().toString() +
                                memberContact3.getText().toString());
                        params.put("member_Email", memberEmailAdd.getText().toString() + "@" + memberEmailDomain.getText().toString());
                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });

    }
}