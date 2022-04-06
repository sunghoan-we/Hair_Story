package com.example.hairstory.admin.branchadmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstory.R;
import com.example.hairstory.admin.branch.DetailBranch;
import com.example.hairstory.common.dto.BranchAdmin;
import com.example.hairstory.common.dto.Branch;

import java.util.List;


public class BranchAdminAdapter extends RecyclerView.Adapter<BranchAdminAdapter.BranchAdminViewHolder> {
    List<BranchAdmin> branchAdminList;
    Context context;

    public BranchAdminAdapter(List<BranchAdmin> branchAdminList, Context context) {
        Log.d("DEBUG", "BranchAdminAdapter");
        this.branchAdminList = branchAdminList;
        this.context = context;
    }

    public List<BranchAdmin> getBranchAdminList() {
        return branchAdminList;
    }

    public void setBranchAdminList(List<BranchAdmin> branchAdminList) {
        this.branchAdminList = branchAdminList;
    }

    @NonNull
    @Override
    public BranchAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("DEBUG", "onCreateViewHolder");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_branch_admin,
                parent,false);

        BranchAdminViewHolder branchAdminViewHolder = new BranchAdminViewHolder(itemView);
        branchAdminViewHolder.branchName = itemView.findViewById(R.id.txtListBranchAdminBranchName);
        branchAdminViewHolder.branchAdminId = itemView.findViewById(R.id.txtListBranchAdminId);
        branchAdminViewHolder.branchAdminName = itemView.findViewById(R.id.txtListBranchAdminName);

        branchAdminViewHolder.branchAdminLine = itemView.findViewById(R.id.branchAdminLine);
        return branchAdminViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BranchAdminViewHolder holder, int position) {
        Log.d("DEBUG", "onBindViewHolder");

        holder.branchName.setText(branchAdminList.get(position).getBranch().getBranchName());
        String branchAdminId = String.valueOf(branchAdminList.get(position).getUserID());
        holder.branchAdminId.setText(branchAdminId);
        holder.branchAdminName.setText(branchAdminList.get(position).getfName()+" "+branchAdminList.get(position).getlName());

        holder.branchAdminLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on click action here
                //-- use context to start the new Activity
                Intent intent = new Intent(context, DetailBranchCommon.class);
                intent.putExtra("branchAdminId", branchAdminId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchAdminList.size();
    }

    public class BranchAdminViewHolder extends RecyclerView.ViewHolder{

        TextView branchName;
        TextView branchAdminId;
        TextView branchAdminName;
        ConstraintLayout branchAdminLine;
        public BranchAdminViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

