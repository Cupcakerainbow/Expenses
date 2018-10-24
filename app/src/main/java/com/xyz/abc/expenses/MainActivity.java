package com.xyz.abc.expenses;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static DataBaseHelper db;
    String date;
    String reason;
    Integer amount;
    static MyListView adapter;
    static RecyclerView recyclerView;
    Fragment fragment;

    MyRecyclerViewAdapter ad;

    TextView parentView;
    TextView totalVIew;
    static ListView lv;
    DrawerLayout md;
    Toolbar toolbar;
    RuntimeData RD = new RuntimeData();

    static int days_total;
    static String CHANNEL_ID = "my";
    Button Today,Monthly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Today = (Button) findViewById(R.id.button6);
        Monthly = findViewById(R.id.button7);
        /*Animation logoMoveAnimation = AnimationUtils.loadAnimation(this,
                R.anim.myanim);
        Today.startAnimation(logoMoveAnimation);*/

        RD.Parentstr = RD.Parent();
        db = new DataBaseHelper(this);
        fragment = new Fragment();
        fragment = new Entry();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
        fragment = new EntryBarFrag();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment2, fragment);
        ft.commit();
        RuntimeData.year = Integer.parseInt(RuntimeData.Parentstr.substring(6));


        setContentView(R.layout.activity_main);
        /*lv = findViewById(R.id.list0);

        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();


        ParentStr = Parent();

        Cursor res = db.getParentData(ParentStr.toString());
        int c = 0;
        while (res.moveToNext()) {
            Id.add(res.getString(0));
            date0.add(res.getString(1));
            reason0.add(res.getString(2));
            amount0.add(res.getInt(3));
            c++;
        }





        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ad = new MyRecyclerViewAdapter(this, date0,reason0,amount0,Id);
        //ad.setClickListener(this);
        recyclerView.setAdapter(ad);
        //MyListView adapter = new MyListView(this, date0, reason0, amount0, Id);
        //lv.setAdapter(adapter);

        parentView = findViewById(R.id.textView);
        totalVIew = findViewById(R.id.textView4);
        parentView.setText(ParentStr);
        daysTotal();
        totalVIew.setText(days_total + "");*/
        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 5);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        RuntimeData.context = getApplicationContext();*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"On Stop",Toast.LENGTH_LONG).show();
        //startService(new Intent(this, NotificationService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Entry.redraw();
        }
        catch (Exception e){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Entry.redraw();
        }
        catch (Exception e){

        }
    }

    void changeParentIncrement(View view) {
        String dt = RD.Parentstr.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(this,dt.equals(RuntimeData.Parent().toString())+"",Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,RuntimeData.Parentstr.toString()+" "+RuntimeData.Parent().toString(),Toast.LENGTH_SHORT).show();
        if(!dt.equals(RuntimeData.Parent().toString())) {
            c.add(Calendar.DATE, 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        RD.Parentstr.replace(0, RD.Parentstr.length(), sdf1.format(c.getTime()));
        Entry.redraw();



    }

    void changeParentDecrement(View view) {
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
        Entry.redraw();



    }

    int daysTotal() {
        days_total = 0;
        Cursor res = db.getParentData(RD.Parentstr.toString());
        while (res.moveToNext()) {
            days_total += res.getInt(3);
        }
        return days_total;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                md.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   void NotificationSend(View view){
        createNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu)
                .setContentTitle("test")
                .setContentText("test test")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, mBuilder.build());
    }
    void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "myChannel";
            String description = "a channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked  on row number " + position, Toast.LENGTH_SHORT).show();
    }

    void commitToPreference(){ }
    void getinfo(View view) {
        ArrayList<String> date0 = new ArrayList<String>();
        ArrayList<String> reason0 = new ArrayList<String>();
        ArrayList<Integer> amount0 = new ArrayList<Integer>();
        ArrayList<String> Id = new ArrayList<String>();

        EditText amountet = findViewById(R.id.editText2);
        EditText reasoner = findViewById(R.id.editText);
        //String reason = (reasoner.getText().toString()).trim();
        //Toast.makeText(this,"reason_______________"+Entry.getDateTime(),Toast.LENGTH_LONG).show();

       // Integer amount = Integer.parseInt((amountet.getText().toString()).trim());

        //db.insertData(RuntimeData.Parent().toString(), reason, amountet.getText().toString(), RuntimeData.Parentstr.toString());
        Entry.redraw();



        //NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        try {
            String reason = (reasoner.getText().toString()).trim();
            Integer amount = Integer.parseInt((amountet.getText().toString()).trim());
            if(!reason.equals("")){

            db.insertData(Entry.getDateTime(), reason, amountet.getText().toString(), RuntimeData.Parentstr.toString());
            Entry.redraw();}
            else{
                Toast.makeText(this, "Reason not Entered", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }


    }
    void FragMonth(View view){
        fragment = new Monthly();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fragment);
        ft.commit();
        fragment = new Default();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment2,fragment);
        ft.commit();
    }
    void FragDaily(View view){
        fragment = new Entry();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fragment);
        ft.commit();
        fragment = new EntryBarFrag();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment2,fragment);
        ft.commit();
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("FragmentC") != null) {
            // I'm viewing Fragment C
            fragment = new Monthly();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment,fragment);
            ft.commit();
            fragment = new Default();
            ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment2,fragment);
            ft.commit();
            //getSupportFragmentManager().popBackStack("hmmm0",
              //      FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }
}