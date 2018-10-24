package com.xyz.abc.expenses;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RuntimeData {
    static Context context;
    static StringBuilder Parentstr;
    static String monthSeleced;
    static Integer year;
    static String parentdata;
    static Date d = new Date(2018-06-30);
    static StringBuilder Parent() {



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        StringBuilder s = new StringBuilder();
        s.append(dateFormat.format(date));



        return s;


    }
}
