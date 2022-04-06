package com.example.hairstory.member.memberinfo;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterMemberMembership extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView inputMemberID;
    TextView inputMemberPassword;
    TextView inputMemberFName;
    TextView inputMemberLName;
    TextView inputMemberDOB;
    TextView inputMemberContact1;
    TextView inputMemberContact2;
    TextView inputMemberContact3;
    TextView inputMemberEmailAdd;
    TextView inputMemberEmailDomain;

    Button MemberSubmit;

    String tag_string_req = "Register New Membership";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member_membership);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), LoginMember.class);

            }
        });

        inputMemberID = findViewById(R.id.input_reg_member_ID);
        inputMemberPassword = findViewById(R.id.input_reg_member_PW);
        inputMemberFName = findViewById(R.id.input_reg_member_FN);
        inputMemberLName = findViewById(R.id.input_reg_member_LN);
        inputMemberDOB = findViewById(R.id.input_reg_member_DOB);
        inputMemberContact1 = findViewById(R.id.input_reg_member_C1);
        inputMemberContact2 = findViewById(R.id.input_reg_member_C2);
        inputMemberContact3 = findViewById(R.id.input_reg_member_C3);
        inputMemberEmailAdd = findViewById(R.id.input_reg_member_Email1);
        inputMemberEmailDomain = findViewById(R.id.input_reg_member_Email2);

        MemberSubmit = findViewById(R.id.btn_reg_member_Submit);

        MemberSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_REGISTER_MEMBER_MEMBERSHIP, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG", response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            Log.d("DEBUG", response);

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "New Account (" + inputMemberID.getText().toString() + ") is successfully created!", Toast.LENGTH_LONG).show();

                                // if user completes to make own account, then page will be changed to login page
                                CommonFunctions.changeIntent(v.getContext(), LoginMember.class);

                            } else {
                                // Error to register menu. Get the error message
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
                        // Posting parameters to register member url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("member_ID", inputMemberID.getText().toString());
                        params.put("member_PW", inputMemberPassword.getText().toString());
                        params.put("member_FName", inputMemberFName.getText().toString());
                        params.put("member_LName", inputMemberLName.getText().toString());
                        params.put("member_DOB", inputMemberDOB.getText().toString());
                        params.put("member_Contact", inputMemberContact1.getText().toString() +
                                inputMemberContact2.getText().toString() +
                                inputMemberContact3.getText().toString());
                        params.put("member_Email", inputMemberEmailAdd.getText().toString() + "@" + inputMemberEmailDomain.getText().toString());

                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });
    }
}