package com.xyz.abc.expenses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.xyz.abc.expenses.MainActivity.adapter;
import static com.xyz.abc.expenses.MainActivity.lv;


public class MyListView extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> Date;
    private final ArrayList<String> Reason;
    private final ArrayList<Integer> Amount;
    private final ArrayList<String> Id;
    public MyListView(Activity context, ArrayList<String> Date, ArrayList<String> Reason, ArrayList<Integer> Amount,ArrayList<String> Id) {
        super(context, R.layout.mylist, Date);
        this.context=context;
        this.Date=Date;
        this.Reason=Reason;
        this.Amount=Amount;
        this.Id = Id;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        Button Del = rowView.findViewById(R.id.button2);
        TextView dateText = (TextView) rowView.findViewById(R.id.date);
        TextView  reasonText=  rowView.findViewById(R.id.reason);
        final TextView amountText = (TextView) rowView.findViewById(R.id.amount);

        Del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.db.deleteData(Id.get(position));
                Date.remove(position);
                Reason.remove(position);
                Amount.remove(position);
                Id.remove(position);
                MyListView adapter = new MyListView(context,Date,Reason,Amount,Id);
                MainActivity.lv.setAdapter(adapter);

            }
        });


        dateText.setText(Date.get(position));
        reasonText.setText(Reason.get(position));
        amountText.setText(String.valueOf(Amount.get(position)));

        return rowView;

    };
}
