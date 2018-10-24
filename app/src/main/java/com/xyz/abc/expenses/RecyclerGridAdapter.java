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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.xyz.abc.expenses.Entry.view;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {
    private String Title[];
    private String SubTitle[];
    private LayoutInflater mLayoutInflater;
    private ItemClickListener mClickListener;

    RecyclerGridAdapter(Context context, String Title[] , String SubTitle[]){
        this.mLayoutInflater = LayoutInflater.from(context);
        this.Title = Title;
        this.SubTitle = SubTitle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.grid_rec_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.Titletv.setText(Title[position]);
        holder.SubTitletv.setText(SubTitle[position]);

    }

    @Override
    public int getItemCount() {
        return Title.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Titletv;
        TextView SubTitletv;

        public ViewHolder(View itemView) {
            super(itemView);
            Titletv = itemView.findViewById(R.id.heading_text);
            SubTitletv = itemView.findViewById(R.id.subtitile_text);
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
