package com.example.dude.projectworkz1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class inside_group extends AppCompatActivity {

    TextView group_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_group);
        group_name=findViewById(R.id.group_name);
        group_name.setText("Welcome to group: "+getIntent().getStringExtra("group_name"));
    }
}
