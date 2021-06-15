package com.example.dude.projectworkz1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class feedbak extends AppCompatActivity {
    EditText textSubject,textMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbak);
        textSubject=findViewById(R.id.editSub);
        textMessage=findViewById(R.id.editMsg);
    }
    public void feedbackSend(View view) {
        String sub=textSubject.getText().toString();
        String msg=textMessage.getText().toString();

        Intent email=new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL,new String[]{"uditsourav21@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT,sub);
        email.putExtra(Intent.EXTRA_TEXT,msg);

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email,"Choose an Email Client"));
    }
}
