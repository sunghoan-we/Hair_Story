package com.example.hairstory.admin.sales;

import android.os.Bundle;

import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;

public class ListSales extends CommonHeader {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sales);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("List Sales");

    }
}