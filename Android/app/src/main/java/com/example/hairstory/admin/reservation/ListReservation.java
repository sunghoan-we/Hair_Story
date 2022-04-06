package com.example.hairstory.admin.reservation;

import android.os.Bundle;

import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;

public class ListReservation extends CommonHeader {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("List Reservation");

    }
}