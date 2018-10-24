package com.xyz.abc.expenses;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.xyz.abc.expenses.Entry.view;

public class MonthOverViewAdapter extends RecyclerView.Adapter<MonthOverViewAdapter.ViewHolder> {

    ArrayList<String> Date = new ArrayList<String>();
    ArrayList<String> Amount = new ArrayList<String>();
    Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    MonthOverViewAdapter(Context context, ArrayList<String> Date, ArrayList<String> Amount){
        this.Date = Date;
        this.Amount = Amount;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.month_overview_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String date = Date.get(position);
        String amount = Amount.get(position);
        holder.datetv.setText(date);
        holder.amounttv.setText(amount);

    }

    @Override
    public int getItemCount() {
        return Date.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener   {
        TextView datetv,amounttv;
        public ViewHolder(View itemView) {
            super(itemView);
            datetv = itemView.findViewById(R.id.datetvo) ;
            amounttv = itemView.findViewById(R.id.amounttvo);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
