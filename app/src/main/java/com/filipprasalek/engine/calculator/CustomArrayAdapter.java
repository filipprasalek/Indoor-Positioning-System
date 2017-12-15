package com.filipprasalek.engine.calculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.id.text1;
import static android.graphics.Color.BLACK;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        TextView tv = view.findViewById(text1);
        tv.setTextColor(BLACK);

        return view;
    }
}