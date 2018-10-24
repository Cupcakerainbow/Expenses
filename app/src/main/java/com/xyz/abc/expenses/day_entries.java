package com.xyz.abc.expenses;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class day_entries extends Fragment {
    static RecyclerView mRecyclerView;
    static LinearLayoutManager mLayoutManager;
    static MyRecyclerViewAdapter mAdapter;
    static Context context ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_entries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view_fake);
        mLayoutManager = new LinearLayoutManager(this.getActivity());

        //Log.d("debugMode", "The application stopped after this");
        mRecyclerView.setLayoutManager(mLayoutManager);
        context=getContext();
        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();
        TextView lol = view.findViewById(R.id.textView9);
        lol.setText("Entries on "+ RuntimeData.parentdata);
        Cursor res = MainActivity.db.getParentData(RuntimeData.parentdata);

        while (res.moveToNext()) {
            Id.add(res.getString(0));
            date0.add(res.getString(1));
            reason0.add(res.getString(2));
            amount0.add(res.getInt(3));

        }



        mAdapter = new MyRecyclerViewAdapter(getContext(),date0,reason0,amount0,Id);
        mRecyclerView.setAdapter(mAdapter);


    }
    static void redraw(){
        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();
        //TextView lol = view.findViewById(R.id.textView9);
        //lol.setText("Entries on "+ RuntimeData.parentdata);
        Cursor res = MainActivity.db.getParentData(RuntimeData.parentdata);

        while (res.moveToNext()) {
            Id.add(res.getString(0));
            date0.add(res.getString(1));
            reason0.add(res.getString(2));
            amount0.add(res.getInt(3));

        }



        mAdapter = new MyRecyclerViewAdapter(context,date0,reason0,amount0,Id);
        mRecyclerView.setAdapter(mAdapter);

    }
}
