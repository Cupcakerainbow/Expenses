package com.xyz.abc.expenses;

import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class Monthly extends Fragment implements RecyclerGridAdapter.ItemClickListener {

    RecyclerGridAdapter mGrid;
    RecyclerView mRecyclerView;
    static TextView year_tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String Months[] ={"January","February","March","April","May","June","July","August","September","October","November","December"};
        String Amount[] = MonthlyTotal();
        year_tv = view.findViewById(R.id.year_text);
        year_tv.setText(RuntimeData.year.toString());
        mRecyclerView = view.findViewById(R.id.months_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        for(int i = 0; i<12;i++ ){
            if(Amount[i].equals("0")){
                Amount[i]="No Expenses";

            }
        }
        mGrid = new RecyclerGridAdapter(getContext(),Months,Amount);
        mGrid.setClickListener(this);
        mRecyclerView.setAdapter(mGrid);

        Button back = view.findViewById(R.id.button8);
        Button white = view.findViewById(R.id.button9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevYear();
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextYear();
            }
        });
    }

    String [] MonthlyTotal(){
        String totals[] = new String[12];
        int month=0;
        String temp ;
        Integer temp0;
        for (int i =1 ; i<=12;i++){
            temp0=0;
            temp = i+"/"+ RuntimeData.year.toString();

            Cursor res = MainActivity.db.getMonthData(temp);
            while (res.moveToNext()){
                temp0+=res.getInt(3);
            }
            totals[i-1]=temp0+"";
            //Toast.makeText(getContext(),temp0+"",Toast.LENGTH_SHORT).show();
        }
        return totals;
    }

    @Override
    public void onItemClick(View view, int position) {
        RuntimeData.monthSeleced = position+"";
        Fragment fr = new MonthOverview();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fr,"hmmm");
        ft.addToBackStack("hmmm0");
        ft.commit();
    }
    void nextYear(){
        if(RuntimeData.year==Integer.parseInt(RuntimeData.Parentstr.substring(6))){
            //Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
        }
        else {
            RuntimeData.year++;
        }
        year_tv.setText(RuntimeData.year.toString());
        String Months[] ={"January","February","March","April","May","June","July","August","September","October","November","December"};
        String Amount[] = MonthlyTotal();
        //year_tv = view.findViewById(R.id.year_text);
        //year_tv.setText(RuntimeData.year.toString());
        //mRecyclerView = view.findViewById(R.id.months_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        for(int i = 0; i<12;i++ ){
            if(Amount[i].equals("0")){
                Amount[i]="No Expenses";

            }
        }
        mGrid = new RecyclerGridAdapter(getContext(),Months,Amount);
        mGrid.setClickListener(this);
        mRecyclerView.setAdapter(mGrid);


    }
    void prevYear(){
        RuntimeData.year--;
        year_tv.setText(RuntimeData.year.toString());
        String Months[] ={"January","February","March","April","May","June","July","August","September","October","November","December"};
        String Amount[] = MonthlyTotal();
        //year_tv = view.findViewById(R.id.year_text);
        //year_tv.setText(RuntimeData.year.toString());
        //mRecyclerView = view.findViewById(R.id.months_recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        for(int i = 0; i<12;i++ ){
            if(Amount[i].equals("0")){
                Amount[i]="No Expenses";

            }
        }
        mGrid = new RecyclerGridAdapter(getContext(),Months,Amount);
        mGrid.setClickListener(this);
        mRecyclerView.setAdapter(mGrid);

        // Toast.makeText(getContext(),"yes",Toast.LENGTH_SHORT).show();
    }
}
