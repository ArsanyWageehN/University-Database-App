package com.example.university.oop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.university.Activities.InstuctorDetails;
import com.example.university.Activities.StudentDetails;
import com.example.university.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterStudentsList extends ArrayAdapter<StudentData> implements Serializable {


    private final Context c;
    public AdapterStudentsList(Activity context, ArrayList<StudentData> studentData) {
        super(context, 0, studentData);
        this.c = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.card, parent, false);
        }

        StudentData studentData = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        assert studentData != null;
        nameTextView.setText(studentData.getName());

        Button show = listItemView.findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("key", studentData);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(v.getContext(), StudentDetails.class);
                c.startActivity(intent);
            }
        });

        return listItemView;
    }
}
