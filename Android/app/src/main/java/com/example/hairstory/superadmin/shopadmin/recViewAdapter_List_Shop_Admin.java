package com.example.hairstory.superadmin.shopadmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hairstory.R;

import java.util.ArrayList;

public class recViewAdapter_List_Shop_Admin extends RecyclerView.Adapter<recViewAdapter_List_Shop_Admin.ViewHolder> {


    private ArrayList<String> shopAdminIndexes = null;
    private ArrayList<String> shopAdminIds = null;
    private ArrayList<String> shopAdminFNames = null;
    private ArrayList<String> shopAdminLNames = null;
    private ArrayList<String> shopAdminEmails = null;
    private ArrayList<String> shopAdminRegDates = null;


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shopAdmin_ID;
        TextView shopAdmin_Name;
        TextView shopAdmin_Email;
        TextView shopAdmin_RegDate;

        ViewHolder(View itemView) {
            super(itemView);

            shopAdmin_ID = itemView.findViewById(R.id.txt_list_shopAdmin_ID);
            shopAdmin_Name = itemView.findViewById(R.id.txt_list_shopAdmin_Name);
            shopAdmin_Email = itemView.findViewById(R.id.txt_list_shopAdmin_email);
            shopAdmin_RegDate = itemView.findViewById(R.id.txt_list_shopAdmin_regDate);


            // 클릭시 이벤트 링크
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(itemView.getContext(),
                            DetailShopAdmins.class);

                    // 뷰홀더 내에서 intent & startActivity 구현
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    // ArrayList형 데이터에서 해당 포지션에 할당되는 값 보내기.
                    intent.putExtra("sendAdminID", shopAdmin_ID.getText().toString());

                    itemView.getContext().startActivity(intent);
                }


            });
        }
    }

    public recViewAdapter_List_Shop_Admin(ArrayList<String> list_Index,
                                          ArrayList<String> list_ID,
                                          ArrayList<String> list_fName,
                                          ArrayList<String> list_lName,
                                          ArrayList<String> list_eMail,
                                          ArrayList<String> list_regDate
    ) {

        shopAdminIndexes = list_Index;
        shopAdminIds = list_ID;
        shopAdminFNames = list_fName;
        shopAdminLNames = list_lName;
        shopAdminEmails = list_eMail;
        shopAdminRegDates = list_regDate;


    }

    @Override
    public recViewAdapter_List_Shop_Admin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recview_list_shop_admins, parent, false);
        recViewAdapter_List_Shop_Admin.ViewHolder vh = new recViewAdapter_List_Shop_Admin.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(recViewAdapter_List_Shop_Admin.ViewHolder holder, int position) {

//        String text1 = shopAdminIndexes.get(position);
//        holder.shopAdmin_Index.setText(text1);

        String text2 = shopAdminIds.get(position);
        holder.shopAdmin_ID.setText(text2);

        String text3 = shopAdminLNames.get(position) + " " + shopAdminFNames.get(position);
        holder.shopAdmin_Name.setText(text3);

        String text4 = shopAdminEmails.get(position);
        holder.shopAdmin_Email.setText(text4);

        String text5 = shopAdminRegDates.get(position);
        holder.shopAdmin_RegDate.setText(text5);

    }

    @Override
    public int getItemCount() {
        return shopAdminIndexes.size();
    }
}
