package com.example.hairstory.member.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.hairstory.member.search.SearchMemberDesigners;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReservationMemberMake extends CommonHeader {

    ImageView headerBackButton;
    TextView headerPageName;

    TextView inputResMemberName;
    TextView inputResDate;
    TextView inputResDesignerName;
    TextView inputResShopName;
    TextView inputResBranchName;
    Spinner inputResSex, inputResCategory, inputResMenu, inputResTime;
    TextView inputResEstTime;
    Button MakeReservation;

    String CustomerID, ShopID, ShopName, BranchID, BranchName, DesignerID, DesignerName;

    String tag_string_req = "Make a Reservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_member_make);

        headerBackButton = findViewById(R.id.btn_superAdmin_header_back);
        headerPageName = findViewById(R.id.txt_superAdmin_header_name);

        headerPageName.setText(tag_string_req);

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        CustomerID = preferences.getString(USER_ID, null);

        // get intent information from previous page
        Intent sMakeReservation_intent = getIntent();
        ShopID = sMakeReservation_intent.getStringExtra("sendShopId");
        ShopName = sMakeReservation_intent.getStringExtra("sendShopName");
        BranchID = sMakeReservation_intent.getStringExtra("sendBranchId");
        BranchName = sMakeReservation_intent.getStringExtra("sendBranchName");
        DesignerID = sMakeReservation_intent.getStringExtra("sendDesignerId");
        DesignerName = sMakeReservation_intent.getStringExtra("sendDesignerName");

        headerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonFunctions.changeIntent(v.getContext(), SearchMemberDesigners.class);

            }
        });

        inputResMemberName = findViewById(R.id.input_reservation_member_make_cName);
        inputResDate = findViewById(R.id.input_reservation_member_make_date);
        inputResDesignerName = findViewById(R.id.input_reservation_member_make_dName);
        inputResShopName = findViewById(R.id.input_reservation_member_make_sName);
        inputResBranchName = findViewById(R.id.input_reservation_member_make_bName);
        inputResSex = findViewById(R.id.spn_reservation_member_make_sCate);
        inputResCategory = findViewById(R.id.spn_reservation_member_make_sMenu);
        inputResMenu = findViewById(R.id.spn_reservation_member_make_menus);
        inputResEstTime = findViewById(R.id.input_reservation_member_make_eTime);
        inputResTime = findViewById(R.id.spn_reservation_member_make_rTime);

        inputResShopName.setText(ShopName);
        inputResBranchName.setText(BranchName);
        inputResDesignerName.setText(DesignerName);
        inputResMemberName.setText(CustomerID);



        MakeReservation = findViewById(R.id.btn_reservation_member_make_submit);

        MakeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest strReq = new StringRequest(Request.Method.POST, DBAccessConfig.URL_REGISTER_MEMBER_RESERVATION, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("DEBUG", response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            Log.d("DEBUG", response);

                            if (!error) {

                                Toast.makeText(getApplicationContext(), "Great! Your (" + inputResMemberName.getText().toString() + ") reservation is successfully booked!", Toast.LENGTH_LONG).show();

                                // if user completes to make own account, then page will be changed to login page
//                                CommonFunctions.changeIntentWithValues(v.getContext(), DetailMemberMembership.class, "sendMemberID", inputMemberID.getText().toString());

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
                        params.put("reservation_rDate", inputResDate.getText().toString());
                        params.put("reservation_sTime", inputResTime.getSelectedItem().toString());
                        params.put("reservation_eTime", "11:00");
                        params.put("reservation_sId", DesignerID);
                        params.put("reservation_cId", CustomerID);

                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            }
        });
    }
}