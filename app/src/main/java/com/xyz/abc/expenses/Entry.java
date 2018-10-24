package com.xyz.abc.expenses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;





public class Entry extends Fragment implements View.OnClickListener {
    static DataBaseHelper db;
    static TextView ParentView;
    static View view;
    static int days_total;
    SimpleDateFormat sdf ;

    static RecyclerView mRecyclerView;
    static LinearLayoutManager mLayoutManager;
    static MyRecyclerViewAdapter mAdapter;
    static TextView daytotaltv;
    static TextView day_tv;
    static Calendar cal = Calendar.getInstance();
    static RuntimeData RD = new RuntimeData();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_entry,container,false);

        Button prev = view.findViewById(R.id.button5);
        Button next = view.findViewById(R.id.button4);

        prev.setOnClickListener(this);
        next.setOnClickListener(this);




        return inflater.inflate(R.layout.fragment_entry, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        cal.set(Integer.parseInt(RuntimeData.Parentstr.substring(6)),Integer.parseInt(RuntimeData.Parentstr.substring(3,5)),Integer.parseInt(RuntimeData.Parentstr.substring(0,2)));

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        ParentView = view.findViewById(R.id.textView);
        day_tv =view.findViewById(R.id.textView7);

        //Log.d("debugMode", "The application stopped after this");
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();

        RD.Parentstr=Parent();
        Cursor res = db.getParentData(RD.Parentstr.toString());

        while (res.moveToNext()) {
            Id.add(res.getString(0));
            date0.add(res.getString(1));
            reason0.add(res.getString(2));
            amount0.add(res.getInt(3));

        }

        mAdapter = new MyRecyclerViewAdapter(getContext(),date0,reason0,amount0,Id);
        mRecyclerView.setAdapter(mAdapter);
        ParentView.setText(RD.Parentstr);
        day_tv.setText(InttoWeek(cal.get(Calendar.DAY_OF_WEEK)));
        //EntryBarFrag.Amount.setText(daysTotal());


    }
    private StringBuilder Parent() {



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        StringBuilder s = new StringBuilder();
        s.append(dateFormat.format(date));



        return s;


    }

    static void getinfo(View view) {
        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();

        EditText amountet = view.findViewById(R.id.editText2);
        EditText reasoner = view.findViewById(R.id.editText);
        String reason = (reasoner.getText().toString()).trim();
        //String amount = Integer.parseInt((amountet.getText().toString()).trim());

        db.insertData(getDateTime(), reason, amountet.getText().toString(), RD.Parentstr.toString());
        redraw();



        //NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        try {
            //String reason = (reasoner.getText().toString()).trim();
            //String amount = Integer.parseInt((amountet.getText().toString()).trim());

            db.insertData(getDateTime(), reason, amountet.getText().toString(), RD.Parentstr.toString());
            redraw();

        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
        }


    }
    static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        Date date = new Date();
        StringBuilder s = new StringBuilder(dateFormat.format(date));
        s.delete(6,s.length());
        return s.toString();
    }
    static void redraw() {

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();

        db = new DataBaseHelper(view.getContext());

        Cursor res = db.getParentData(RuntimeData.Parentstr.toString());

        while (res.moveToNext()) {
            Id.add(res.getString(0));
            date0.add(res.getString(1));
            reason0.add(res.getString(2));
            amount0.add(res.getInt(3));

        }

        mAdapter = new MyRecyclerViewAdapter(view.getContext(),date0,reason0,amount0,Id);
        mRecyclerView.setAdapter(mAdapter);
       // Toast.makeText(view.getContext(),"redraw",Toast.LENGTH_LONG).show();
        ParentView.setText(RuntimeData.Parentstr.toString());
        EntryBarFrag.Amount.setText(daysTotal()+"");
        cal.set(Integer.parseInt(RuntimeData.Parentstr.substring(6)),Integer.parseInt(RuntimeData.Parentstr.substring(3,5)),Integer.parseInt(RuntimeData.Parentstr.substring(0,2)));
        day_tv.setText(InttoWeek(cal.get(Calendar.DAY_OF_WEEK)));

    }
    void changeParentIncrement() {
        String dt = RuntimeData.Parentstr.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        RuntimeData.Parentstr.replace(0, RuntimeData.Parentstr.length(), sdf1.format(c.getTime()));
        redraw();
        ParentView.setText(RuntimeData.Parentstr);



    }
    void changeParentDecrement() {
        String dt = RD.Parentstr.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

        RD.Parentstr.replace(0, RD.Parentstr.length(), sdf1.format(c.getTime()));
        redraw();
        ParentView.setText(RD.Parentstr);

    }
    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(),"clcik",Toast.LENGTH_LONG).show();
        switch (view.getId()){
            case R.id.button5:
                changeParentDecrement();
                break;
            case R.id.button4:
                changeParentIncrement();
                Toast.makeText(getContext(),"clcik",Toast.LENGTH_LONG).show();
                break;
        }

    }
    static int daysTotal() {
        days_total = 0;
        try {
            Cursor res = db.getParentData(RD.Parentstr.toString());
            while (res.moveToNext()) {
                days_total += res.getInt(3);
            }
            return days_total;
        }
        catch (Exception e){
            return 0;
        }
    }
    static String InttoWeek(Integer i){
        String a[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        return a[i-1];
    }
}
