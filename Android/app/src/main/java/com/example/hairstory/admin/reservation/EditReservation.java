package com.example.hairstory.admin.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;

public class EditReservation extends CommonHeader {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Edit Reservation");

        btnAdminHeaderBack = findViewById(R.id.btnAdminHeaderBack);
        btnAdminHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditReservation.this, DetailReservation.class);
                startActivity(intent);
            }
        });
    }
}