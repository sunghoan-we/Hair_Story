package com.example.hairstory.superadmin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hairstory.R;

public class SuperAdminHeader extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_admin_header);
    }

    public void onClickMenu(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        getMenuInflater().inflate(R.menu.super_admin_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
//////TODO modify the destination class
////                    case R.id.m1:
////                        Toast.makeText(view.getContext(),"m1",Toast.LENGTH_SHORT).show();
//////                        startActivity(new Intent(SuperAdminHeader.this, HomeActivity.class));
////                        break;
////                    case R.id.m2:
//////                        startActivity(new Intent(SuperAdminHeader.this, IncomeListActivity.class));
////                        break;
////                    case R.id.m3:
//////                        startActivity(new Intent(SuperAdminHeader.this, ExpenseTrackerListActivity.class));
////                        break;
////                    case R.id.m4:
//////                        startActivity(new Intent(SuperAdminHeader.this, BigExpenseListActivity.class));
////                        break;
////                    case R.id.m5:
//////                        startActivity(new Intent(SuperAdminHeader.this, ReportActivity.class));
////                        break;
////                    case R.id.m6:
//////                        preferences.edit().clear().commit();
//////                        startActivity(new Intent(SuperAdminHeader.this, LoginActivity.class));
//                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

}