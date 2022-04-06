package com.example.hairstory.admin.staff;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstory.R;
import com.example.hairstory.admin.branch.DetailBranch;
import com.example.hairstory.common.dto.BranchAdmin;
import com.example.hairstory.common.dto.Staff;

import java.util.List;


public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    List<Staff> staffList;
    Context context;

    public StaffAdapter(List<Staff> staffList, Context context) {
        Log.d("DEBUG", "StaffAdapter");
        this.staffList = staffList;
        this.context = context;

    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;

    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("DEBUG", "onCreateViewHolder");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_staff,
                parent,false);

        StaffViewHolder branchAdminViewHolder = new StaffViewHolder(itemView);
        branchAdminViewHolder.branchName = itemView.findViewById(R.id.txtListStaffBranchName);
        branchAdminViewHolder.staffName = itemView.findViewById(R.id.txtListStaffName);

        branchAdminViewHolder.staffLine = itemView.findViewById(R.id.staffLine);
        return branchAdminViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        Log.d("DEBUG", "onBindViewHolder");

        holder.branchName.setText(staffList.get(position).getBranch().getBranchName());
        holder.staffName.setText(staffList.get(position).getfName()+" "+staffList.get(position).getlName());
        String staffId = staffList.get(position).getUserID();
        holder.staffLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on click action here
                //-- use context to start the new Activity
                Intent intent = new Intent(context, DetailStaff.class);
                intent.putExtra("staffId", staffId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class StaffViewHolder extends RecyclerView.ViewHolder{

        TextView branchName;
        TextView staffName;
        ConstraintLayout staffLine;
        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

