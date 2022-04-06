package com.example.hairstory.member.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.hairstory.member.search.SearchMemberBranches;

import com.example.hairstory.R;

import java.util.ArrayList;

public class recViewAdapter_Search_Branches extends RecyclerView.Adapter<recViewAdapter_Search_Branches.ViewHolder> {

    private ArrayList<String> branchIds = null;
    private ArrayList<String> branchNames = null;
    private ArrayList<String> branchAddresses = null;
    private ArrayList<String> branchContacts = null;
    private ArrayList<String> branchOpenTimes = null;
    private ArrayList<String> branchCloseTimes = null;
    private ArrayList<String> branchRegDates = null;
    private String shopID, shopName = null;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView branch_Id;
        TextView branch_Name;
        TextView branch_Address;
        TextView branch_Contact;
        TextView branch_Time;
        TextView select_Designer;

        ViewHolder(View itemView) {
            super(itemView);

            branch_Id = itemView.findViewById(R.id.data_search_member_branches_bID);
            branch_Name = itemView.findViewById(R.id.data_search_member_branches_bName);
            branch_Address = itemView.findViewById(R.id.data_search_member_branches_address);
            branch_Contact = itemView.findViewById(R.id.data_search_member_branches_contact);
            branch_Time = itemView.findViewById(R.id.data_search_member_branches_time);
            select_Designer = itemView.findViewById(R.id.btn_search_member_branches_reservation);


            // 클릭시 이벤트 링크
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),
                            SearchMemberDesigners.class);

                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("sendShopId", shopID);
                    intent.putExtra("sendShopName", shopName);
                    intent.putExtra("sendBranchId", branch_Id.getText().toString());
                    intent.putExtra("sendBranchName", branch_Name.getText().toString());


                    itemView.getContext().startActivity(intent);
                }

            });
        }
    }

    public recViewAdapter_Search_Branches(ArrayList<String> list_Id,
                                          ArrayList<String> list_Name,
                                          ArrayList<String> list_Address,
                                          ArrayList<String> list_Contact,
                                          ArrayList<String> list_StartTime,
                                          ArrayList<String> list_EndTime,
                                          ArrayList<String> list_RegDate,
                                          String shop_Id,
                                          String shop_Name) {
        branchIds = list_Id;
        branchNames = list_Name;
        branchAddresses = list_Address;
        branchContacts = list_Contact;
        branchOpenTimes = list_StartTime;
        branchCloseTimes = list_EndTime;
        branchRegDates = list_RegDate;

        shopID = shop_Id;
        shopName = shop_Name;

    }

    @Override
    public recViewAdapter_Search_Branches.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recview_search_branches, parent, false);
        recViewAdapter_Search_Branches.ViewHolder vh = new recViewAdapter_Search_Branches.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(recViewAdapter_Search_Branches.ViewHolder holder, int position) {

        String text1 = branchIds.get(position);
        holder.branch_Id.setText(text1);

        String text2 = branchNames.get(position);
        holder.branch_Name.setText(text2);

        String text3 = branchAddresses.get(position);
        holder.branch_Address.setText(text3);

        String text4 = "(" + branchContacts.get(position) + ")";
        holder.branch_Contact.setText(text4);

        String text5 = branchOpenTimes.get(position) + " ~ " + branchCloseTimes.get(position);
        holder.branch_Time.setText(text5);

    }

    @Override
    public int getItemCount() {
        return branchNames.size();
    }
}
