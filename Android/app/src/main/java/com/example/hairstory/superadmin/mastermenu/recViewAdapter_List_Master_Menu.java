package com.example.hairstory.superadmin.mastermenu;

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

public class recViewAdapter_List_Master_Menu extends RecyclerView.Adapter<recViewAdapter_List_Master_Menu.ViewHolder> {

    private ArrayList<String> menuID = null;
    private ArrayList<String> menuName = null;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView menu_Name;

        ViewHolder(View itemView) {
            super(itemView);

            menu_Name = itemView.findViewById(R.id.label_list_masterMenu_Menu);

            // 클릭시 이벤트 링크
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),
                            DetailMasterMenu.class);

                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("sendMenuName", menu_Name.getText().toString());

                    itemView.getContext().startActivity(intent);
                }


            });
        }
    }

    public recViewAdapter_List_Master_Menu(ArrayList<String> list_ID, ArrayList<String> list_Name) {
        menuID = list_ID;
        menuName = list_Name;
    }

    @Override
    public recViewAdapter_List_Master_Menu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recview_list_master_menu, parent, false);
        recViewAdapter_List_Master_Menu.ViewHolder vh = new recViewAdapter_List_Master_Menu.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(recViewAdapter_List_Master_Menu.ViewHolder holder, int position) {

        String text2 = menuName.get(position);
        holder.menu_Name.setText(text2);
    }

    @Override
    public int getItemCount() {
        return menuID.size();
    }
}
