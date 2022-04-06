package com.example.hairstory.admin.sales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;

public class RegisterSales extends CommonHeader {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sales);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Register Sales");

        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSales.this, ListSales.class);
                startActivity(intent);
            }
        });
    }
}