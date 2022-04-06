package com.example.hairstory.member.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstory.R;
import com.example.hairstory.member.reservation.ReservationMemberMake;

import java.util.ArrayList;

public class recViewAdapter_Search_Designers extends RecyclerView.Adapter<recViewAdapter_Search_Designers.ViewHolder> {

    private ArrayList<String> designerIds = null;
    private ArrayList<String> designerFNames = null;
    private ArrayList<String> designerLNames = null;
    private ArrayList<String> designerEmails = null;
    private String shopID, shopName, branchID, branchName = null;
    private String designerID, designerName = null;

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView designer_Name;
        TextView designer_Email;
        //        TextView designer_BranchName;
//        TextView designer_Menu;
        TextView make_Reservation;

        ViewHolder(View itemView) {
            super(itemView);

            designer_Name = itemView.findViewById(R.id.data_search_member_designers_dName);
            designer_Email = itemView.findViewById(R.id.data_search_member_designers_email);
//            designer_BranchName = itemView.findViewById(R.id.data_search_member_designers_bName);
//            designer_Menu = itemView.findViewById(R.id.data_search_member_designers_dMenu);
            make_Reservation = itemView.findViewById(R.id.btn_search_member_designers_reservation);


            // 클릭시 이벤트 링크
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),
                            ReservationMemberMake.class);

                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("sendShopId", shopID);
                    intent.putExtra("sendShopName", shopName);
                    intent.putExtra("sendBranchId", branchID);
                    intent.putExtra("sendBranchName", branchName);
                    intent.putExtra("sendDesignerId", designerID);
                    intent.putExtra("sendDesignerName", designer_Name.getText().toString());

                    itemView.getContext().startActivity(intent);
                }

            });
        }
    }

    public recViewAdapter_Search_Designers(ArrayList<String> list_Id,
                                           ArrayList<String> list_FName,
                                           ArrayList<String> list_LName,
                                           ArrayList<String> list_Contact,
                                           ArrayList<String> list_Email,
                                           ArrayList<String> list_DOB,
                                           ArrayList<String> list_BranchId,
                                           ArrayList<String> list_RegDate,
                                           ArrayList<String> list_UpDate,
                                           String shop_Id,
                                           String shop_Name,
                                           String branch_Id,
                                           String branch_Name) {

        designerIds = list_Id;
        designerFNames = list_FName;
        designerLNames = list_LName;
        designerEmails = list_Email;

        shopID = shop_Id;
        shopName = shop_Name;
        branchID = branch_Id;
        branchName = branch_Name;

    }

    @Override
    public recViewAdapter_Search_Designers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recview_search_designers, parent, false);
        recViewAdapter_Search_Designers.ViewHolder vh = new recViewAdapter_Search_Designers.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(recViewAdapter_Search_Designers.ViewHolder holder, int position) {

        String text1 = designerFNames.get(position) + " " + designerLNames.get(position);
        holder.designer_Name.setText(text1);

        String text2 = designerEmails.get(position);
        holder.designer_Email.setText(text2);

        String text3 = designerIds.get(position);
        designerID = text3;



    }

    @Override
    public int getItemCount() {
        return designerFNames.size();
    }
}
