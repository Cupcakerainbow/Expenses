
package com.xyz.abc.expenses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    Context context;
    private ArrayList<String> Date;
    private ArrayList<String> Reason;
    private ArrayList<Integer> Amount;
    private LayoutInflater mInflater;
    private ArrayList<String> Id;
    public static String po;


    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<String> Date, ArrayList<String> Reason ,ArrayList<Integer> Amount ,ArrayList<String> Id) {
        this.context = context ;
        this.mInflater = LayoutInflater.from(context);
        this.Date = Date;
        this.Reason = Reason;
        this.Amount = Amount;
        this.Id = Id;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_text_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String date = Date.get(position);
        String reason = Reason.get(position);
        Integer amount = Amount.get(position);
        holder.datetv.setText(date);
        holder.reasontv.setText(reason);
        holder.amounttc.setText(amount+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context,holder.itemView);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Delete:
                                MainActivity.db.deleteData(Id.get(position));
                                try {
                                    day_entries.redraw();
                                }catch (Exception e){
                                    Toast.makeText(context,"So it was come to this",Toast.LENGTH_SHORT).show();
                                }


                                Entry.redraw();
                                break;
                            case R.id.edit:
                                po = Id.get(position);

                                try {
                                    day_entries.redraw();
                                }catch (Exception e){
                                    Toast.makeText(context,"So it was come to this",Toast.LENGTH_SHORT).show();
                                }

                                MainActivity.db.ParentStr = RuntimeData.Parentstr.toString();
                                Intent intent = new Intent(context,EditPopUp.class);
                                context.startActivity(intent);
                                break;
                            default:
                                break;

                        }


                        return true;
                    }
                });

                popup.show(); //showing popup menu

            }
        });
    }

    @Override
    public int getItemCount() {
        return Date.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView datetv;
        TextView reasontv;
        TextView amounttc;

        ViewHolder(View itemView) {
            super(itemView);
            datetv = itemView.findViewById(R.id.timer);
            reasontv = itemView.findViewById(R.id.reasonr);
            amounttc = itemView.findViewById(R.id.amountr);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }






}