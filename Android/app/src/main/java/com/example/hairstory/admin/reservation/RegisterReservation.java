package com.example.hairstory.admin.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;

public class RegisterReservation extends CommonHeader {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reservation);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Register Reservation");

        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterReservation.this, ListReservation.class);
                startActivity(intent);
            }
        });
    }
}