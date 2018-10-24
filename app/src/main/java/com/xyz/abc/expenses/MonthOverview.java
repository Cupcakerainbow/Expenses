package com.xyz.abc.expenses;

import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MonthOverview extends Fragment implements MonthOverViewAdapter.ItemClickListener {
    RecyclerView mRecyclerView;
    MonthOverViewAdapter mOverview;
    TextView Month;
    ArrayList<String> parent00 = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_month_overview, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> date00 = new ArrayList<String>();
        ArrayList<String> amount00 = new ArrayList<String>();

        date00.add("Date");
        amount00.add("Amount");

        Month = view.findViewById(R.id.textView6);
        Month.setText(IntToMonth(RuntimeData.monthSeleced));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();


        cal.set(Calendar.YEAR,RuntimeData.year);
        cal.set(Calendar.DAY_OF_YEAR,1);

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");


        cal.add(Calendar.MONTH,Integer.parseInt(RuntimeData.monthSeleced));
        String idk =  sdf1.format(cal.getTime());
        //Toast.makeText(getContext(),idk,Toast.LENGTH_SHORT).show();

        while(sdf.format(cal.getTime()).substring(2).equals(idk.substring(2))){

            Integer amtemp=0;
            //ArrayList<String> date0 = new ArrayList<String>();
            ArrayList<String> amount0 = new ArrayList<String>();
            String temp = sdf1.format(cal.getTime());

            Cursor res = MainActivity.db.getMonthData(temp);
            while (res.moveToNext()){
                //date0.add(res.getString(4).substring(0,2));
                amount0.add(res.getString(3));
            }

            for(String str : amount0){
                amtemp+=Integer.parseInt(str);
            }
            if(amtemp!=0) {
                amount00.add(amtemp.toString());
                date00.add(temp.substring(0,2)+"-"+InttoWeek(cal.get(Calendar.DAY_OF_WEEK)).substring(0,3));
                parent00.add(temp);
            }
            RuntimeData.parentdata=parent00.toString();
            cal.add(Calendar.DATE,1);
            Toast.makeText(getContext(),"Run",Toast.LENGTH_SHORT).show();


        }

        //Toast.makeText(getContext(),temp,Toast.LENGTH_SHORT).show();
        mRecyclerView = view.findViewById(R.id.month_overview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(date00.size()==1){
            date00.remove(0);
            amount00.remove(0);
            date00.add("Nothing To");
            amount00.add("Show");
        }

        mOverview = new MonthOverViewAdapter(getContext(),date00,amount00);
        mOverview.setClickListener(this);
        mRecyclerView.setAdapter(mOverview);

    }
    String IntToMonth(Integer i){
        String Months[] ={"January","February","March","April","May","June","July","August","September","October","November","December"};
        return Months[i];

    }
    String IntToMonth(String i){
        String Months[] ={"January","February","March","April","May","June","July","August","September","October","November","December"};
        return Months[Integer.parseInt(i)];

    }
    String InttoWeek(Integer i){
        String a[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        return a[i-1];
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(getContext(),"CLick",Toast.LENGTH_SHORT).show();
        try {
            RuntimeData.parentdata=parent00.get(position-1);
            // Toast.makeText(getContext(),RuntimeData.parentdata,Toast.LENGTH_SHORT).show();
            Fragment fragment = new day_entries();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment,fragment);
            ft.commit();

        }catch (Exception e){
            Toast.makeText(getContext(),"Not sure what you are trying to do",Toast.LENGTH_LONG).show();
        }


    }
}
