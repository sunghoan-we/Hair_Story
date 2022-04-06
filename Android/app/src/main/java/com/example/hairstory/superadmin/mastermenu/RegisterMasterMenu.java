package com.example.hairstory.superadmin.mastermenu;

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

public class RegisterMasterMenu extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView inputMenuName;
    Button MasterMenuSubmit;

    String tag_string_req = "Register New Master Menu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_master_menu);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), ListMasterMenus.class);

            }
        });

        inputMenuName = findViewById(R.id.input_reg_masterMenu_Menu);
        MasterMenuSubmit = findViewById(R.id.btn_reg_masterMenu_Submit);

        MasterMenuSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("DEBUG", inputMenuName.getText().toString());


                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_REGISTER_MASTER_MENU, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            Log.d("DEBUG", response);

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "New Menu (" + inputMenuName.getText().toString() + ") is successfully added!", Toast.LENGTH_LONG).show();

                                CommonFunctions.changeIntent(v.getContext(), ListMasterMenus.class);

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
                        params.put("menu_name", inputMenuName.getText().toString());
                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });


    }
}