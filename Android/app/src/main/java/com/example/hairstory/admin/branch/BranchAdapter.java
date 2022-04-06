package com.example.hairstory.admin.branch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstory.R;
import com.example.hairstory.common.dto.Branch;

import java.util.List;


public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {
    List<Branch> branchList;
    Context context;

    public BranchAdapter(List<Branch> branchList, Context context) {
        Log.d("DEBUG", "BranchAdapter");
        this.branchList = branchList;
        this.context = context;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("DEBUG", "onCreateViewHolder");


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_branch,
                parent,false);
        BranchViewHolder branchViewHolder = new BranchViewHolder(itemView);

        branchViewHolder.branchName = itemView.findViewById(R.id.txtListBranchName);
        branchViewHolder.contact = itemView.findViewById(R.id.txtListBranchContact);
        branchViewHolder.branchLine = itemView.findViewById(R.id.branchLine);
        return branchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        Log.d("DEBUG", "onBindViewHolder");
Log.d("DEBUG", branchList.get(position).getBranchName());
Log.d("DEBUG", branchList.get(position).getContact());
        holder.branchName.setText(branchList.get(position).getBranchName());
        holder.contact.setText(branchList.get(position).getContact());
        String branchId = String.valueOf(branchList.get(position).getBranchId());
        holder.branchLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on click action here
                //-- use context to start the new Activity
                Intent intent = new Intent(context, DetailBranch.class);
                intent.putExtra("branchId", branchId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    public class BranchViewHolder extends RecyclerView.ViewHolder{

        TextView branchName;
        TextView contact;
        LinearLayout branchLine;
        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

