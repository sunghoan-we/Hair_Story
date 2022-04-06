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

import java.util.ArrayList;

public class recViewAdapter_Search_Shops extends RecyclerView.Adapter<recViewAdapter_Search_Shops.ViewHolder> {

    private ArrayList<String> shopIds = null;
    private ArrayList<String> shopNames = null;
    private ArrayList<String> shopRegDates = null;



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shop_Id;
        TextView shop_Name;
        TextView shop_RegDate;
        TextView select_Branch;

        ViewHolder(View itemView) {
            super(itemView);

            shop_Id = itemView.findViewById(R.id.data_search_member_shops_sID);
            shop_Name = itemView.findViewById(R.id.data_search_member_shops_sName);
            shop_RegDate = itemView.findViewById(R.id.data_search_member_shops_regDate);
            select_Branch = itemView.findViewById(R.id.btn_search_member_shops_reservation);

            // 클릭시 이벤트 링크
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),
                            SearchMemberBranches.class);

                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("sendShopID", shop_Id.getText().toString());
                    intent.putExtra("sendShopName", shop_Name.getText().toString());

                    itemView.getContext().startActivity(intent);
                }


            });
        }
    }

    public recViewAdapter_Search_Shops(ArrayList<String> list_Id, ArrayList<String> list_Name, ArrayList<String> list_RegDate) {
        shopIds = list_Id;
        shopNames = list_Name;
        shopRegDates = list_RegDate;
    }

    @Override
    public recViewAdapter_Search_Shops.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recview_search_shops, parent, false);
        recViewAdapter_Search_Shops.ViewHolder vh = new recViewAdapter_Search_Shops.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(recViewAdapter_Search_Shops.ViewHolder holder, int position) {

        String text1 = shopIds.get(position);
        holder.shop_Id.setText(text1);

        String text2 = shopNames.get(position);
        holder.shop_Name.setText(text2);

        String text3 = shopRegDates.get(position);
        holder.shop_RegDate.setText(text3);
    }

    @Override
    public int getItemCount() {
        return shopNames.size();
    }
}
