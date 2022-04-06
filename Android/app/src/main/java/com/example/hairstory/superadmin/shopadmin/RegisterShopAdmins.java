package com.example.hairstory.superadmin.shopadmin;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterShopAdmins extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView inputShopAdminID;
    TextView inputShopAdminPassword;
    TextView inputShopAdminFName;
    TextView inputShopAdminLName;
    TextView inputShopAdminDOB;
    TextView inputShopAdminContact1;
    TextView inputShopAdminContact2;
    TextView inputShopAdminContact3;
    TextView inputShopAdminEmailAdd;
    TextView inputShopAdminEmailDomain;

    Button shopAdminSubmit;

    String tag_string_req = "Register New Shop Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop_admins);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), ListShopAdmins.class);

            }
        });

        inputShopAdminID = findViewById(R.id.input_reg_shopAdmin_ID);
        inputShopAdminPassword = findViewById(R.id.input_reg_shopAdmin_PW);
        inputShopAdminFName = findViewById(R.id.input_reg_shopAdmin_FN);
        inputShopAdminLName = findViewById(R.id.input_reg_shopAdmin_LN);
        inputShopAdminDOB = findViewById(R.id.input_reg_shopAdmin_DOB);
        inputShopAdminContact1 = findViewById(R.id.input_reg_shopAdmin_C1);
        inputShopAdminContact2 = findViewById(R.id.input_reg_shopAdmin_C2);
        inputShopAdminContact3 = findViewById(R.id.input_reg_shopAdmin_C3);
        inputShopAdminEmailAdd = findViewById(R.id.input_reg_shopAdmin_Email1);
        inputShopAdminEmailDomain = findViewById(R.id.input_reg_shopAdmin_Email2);

        shopAdminSubmit = findViewById(R.id.btn_reg_shopAdmin_Submit);


        shopAdminSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_REGISTER_SHOP_ADMIN, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            Log.d("DEBUG", response);

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "New Shop Admin (" + inputShopAdminID.getText().toString() + ") is successfully added!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntent(v.getContext(), ListShopAdmins.class);

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
                        // Posting parameters to detail menu url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("shopAdmin_ID", inputShopAdminID.getText().toString());
                        params.put("shopAdmin_PW", inputShopAdminPassword.getText().toString());
                        params.put("shopAdmin_FName", inputShopAdminFName.getText().toString());
                        params.put("shopAdmin_LName", inputShopAdminLName.getText().toString());
                        params.put("shopAdmin_DOB", inputShopAdminDOB.getText().toString());
                        params.put("shopAdmin_Contact", inputShopAdminContact1.getText().toString() +
                                inputShopAdminContact2.getText().toString() +
                                inputShopAdminContact3.getText().toString());
                        params.put("shopAdmin_Email", inputShopAdminEmailAdd.getText().toString() + "@" + inputShopAdminEmailDomain.getText().toString());

                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });


    }
}