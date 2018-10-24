package com.xyz.abc.expenses;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import static com.xyz.abc.expenses.MainActivity.db;
import static com.xyz.abc.expenses.MyRecyclerViewAdapter.po;

public class EditPopUp extends AppCompatActivity {
    EditText edr,eda;
    String [] temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pop_up);
        edr = findViewById(R.id.editText3);
        eda = findViewById(R.id.editText4);
        temp = db.ReasonAndAmount(po);
        edr.setText(temp[0]);
        eda.setHint(temp[1]);





    }
    void done(View view){

        String s[] = new String[]{edr.getText().toString(), eda.getText().toString()};
        try{
            Integer.parseInt(s[1]);
            db.updateData(po,s[0],s[1]);
        }
        catch (Exception e){
            db.updateData(po,s[0],temp[1]);
        }
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edr.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(eda.getWindowToken(), 0);
        RuntimeData.Parentstr = new StringBuilder(db.ParentStr);
        Intent intent = new Intent(this,MainActivity.class);
        try {
            day_entries.redraw();
        }catch (Exception e){
            Toast.makeText(this,"So it was come to this",Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
        finish();



    }
}
