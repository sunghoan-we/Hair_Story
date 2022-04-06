package com.example.hairstory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.R;
import com.example.hairstory.admin.branch.ListBranch;
import com.example.hairstory.admin.staff.ListStaff;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.member.memberinfo.RegisterMemberMembership;
import com.example.hairstory.member.reservation.ReservationMemberMake;
import com.example.hairstory.member.search.SearchMemberShops;
import com.example.hairstory.superadmin.mastermenu.ListMasterMenus;
import com.example.hairstory.superadmin.shopadmin.ListShopAdmins;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginMember extends AppCompatActivity {
    String tag_string_req = "req_login";

    EditText inputUserId;
    EditText inputPassword;
    Button btnLogin;
    RadioGroup rdbRoleGroup;
    String role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_member);

        inputUserId = findViewById(R.id.inputUserId);
        inputPassword = findViewById(R.id.inputPassword);
        rdbRoleGroup = findViewById(R.id.rdbRoleGroup);
        rdbRoleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rdbCustomer:
                        role = null;
                        break;
                    case R.id.rdbSuperAdmin:
                        role = "01";
                        break;
                    case R.id.rdbShopAdmin:
                        role = "02";
                        break;
                    case R.id.rdbBranchAdmin:
                        role = "03";
                        break;
                }
            }
        });
        findViewById(R.id.btn_login_member_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginMember.this, RegisterMemberMembership.class));
            }
        });
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputUserId.getText() == null || inputUserId.getText().toString().equals("")){
                    Toast.makeText(LoginMember.this, "Please Input User ID..", Toast.LENGTH_SHORT).show();
                    inputUserId.requestFocus();
                    return;
                }

                if (inputPassword.getText() == null || inputPassword.getText().toString().equals("")){
                    Toast.makeText(LoginMember.this, "Please Input Password..", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }

                doLogin(inputUserId.getText().toString(), inputPassword.getText().toString() , role);
            }
        });
    }

    private void doLogin(String userId, String password, String role) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        JSONObject user = jObj.getJSONObject("user");
                        editor.putString("userId", user.getString("userId"));
                        editor.putString("fName", user.getString("f_name"));
                        editor.putString("lName", user.getString("l_name"));
                        editor.putString("phoneNum", user.getString("phone_num"));
                        editor.putString("email", user.getString("email"));
                        editor.putString("dateOfBirth", user.getString("date_of_birth"));
                        editor.putString("regDate", user.getString("reg_date"));
                        editor.putString("updateDate", user.getString("update_date"));

                        if (role != null && !role.equals("")){
                            editor.putString("role", user.getString("role"));
                            switch (user.getString("role")) {
                                case "02":
                                    editor.putString("shopId", user.getString("shop_id"));
                                    editor.putString("shopName", user.getString("shop_name"));
                                    break;
                                case "03":
                                    editor.putString("shopId", user.getString("shop_id"));
                                    editor.putString("shopName", user.getString("shop_name"));
                                    editor.putString("branchId", user.getString("branch_id"));
                                    editor.putString("branchName", user.getString("branch_name"));
                                    break;
                            }
                        }
                        editor.commit();

                        Intent intent;
                        if ("01".equals(role)) {
                            // Launch main activity
                            intent = new Intent(LoginMember.this,
                                    ListShopAdmins.class);
                        } else if ("02".equals(role)) {
                            intent = new Intent(LoginMember.this,
                                    ListBranch.class);
                        } else if ("03".equals(role)) {
                            intent = new Intent(LoginMember.this,
                                    ListStaff.class);
                        } else {
                            intent = new Intent(LoginMember.this,
                                    SearchMemberShops.class);
                        }
                        startActivity(intent);
                        finish();
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
                params.put("userId", userId);
                params.put("password", password);
                if (role != null) {
                    params.put("role", role);
                }
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}