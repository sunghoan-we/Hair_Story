package com.example.hairstory.admin.report;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hairstory.R;
import com.example.hairstory.common.CommonHeader;
import com.example.hairstory.common.AppController;
import com.example.hairstory.common.dao.DBAccessConfig;
import com.example.hairstory.common.dto.Report;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportBySales extends CommonHeader {
    String tag_string_req = "report_by_sales";

    List<Report> reportData = new ArrayList<Report>();
    PieChart pieChart;
    PieData pieData;
    String shopId;
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_by_sales);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Report");

        preferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        shopId = preferences.getString(SHOP_ID, null);


        EditText fromDate = findViewById(R.id.editReportFromDate);
        EditText toDate = findViewById(R.id.editReportToDate);

        Button btnShowReport = findViewById(R.id.btnShowReport);
        btnShowReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Todo
//                shopId = "1";
                //getReportData(shopId, "2021/12/01", "2021/12/01");
                getReportData(shopId, fromDate.getText().toString(), toDate.getText().toString());
            }
        });
    }

    private void drawPieChart(List<Report> reportData) {
        pieChart = findViewById(R.id.reportChartArea);
        pieChart.setUsePercentValues(false);
        pieChart.clear();
        pieEntryList.clear();
        if(reportData != null) {
            for (int i=0; i < reportData.size(); i++){
                pieEntryList.add(new PieEntry((float)reportData.get(i).getAmount(), reportData.get(i).getBranchName()));
            }
            PieDataSet pieDataSet  = new PieDataSet(pieEntryList, "");
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            pieData = new PieData(pieDataSet);
            pieData.setValueTextSize(15);
            pieData.setValueTextColor(Color.BLACK);

            pieChart.setData(pieData);
            pieChart.setDrawEntryLabels(true);
            pieChart.setNoDataTextColor(Color.RED);
            pieChart.getDescription().setTextSize(15);
            pieChart.getDescription().setTextColor(Color.BLACK);

        } else {
            pieChart.setData(null);
        }
        pieChart.invalidate();
    }

    private void getReportData(String shopId, String fromDate, String toDate) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                DBAccessConfig.URL_REPORT_BY_SALES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("DEBUG", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    reportData.clear();
                    if (!error) {
                        JSONArray arrayBranch = jObj.getJSONArray("report");
                        Log.d("DEBUG", arrayBranch.toString());
                        for (int i = 0; i < arrayBranch.length(); i++) {

                            JSONObject temp = arrayBranch.getJSONObject(i);
                            Report report = new Report(temp.getString("branch_name"), temp.getDouble("amount"));
                            reportData.add(report);
                        }

                        drawPieChart(reportData);


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
                params.put("shopId", shopId);
                if (fromDate != null && !fromDate.isEmpty()) {
                    params.put("fromDate", fromDate);
                }
                if (toDate != null && !toDate.isEmpty()) {
                    params.put("toDate", toDate);
                }
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}