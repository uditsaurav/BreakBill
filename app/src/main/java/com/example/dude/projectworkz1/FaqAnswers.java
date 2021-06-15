package com.example.dude.projectworkz1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FaqAnswers extends AppCompatActivity {
    TextView textQ, textA;
    String ans, quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_answers);
        textQ = findViewById(R.id.questtn);
        textA = findViewById(R.id.answers);

        int position = getIntent().getIntExtra("questnNo", 10);
        quest = getIntent().getStringExtra("questn");
        if (position == 0) {
            ans = "Yes you can! When dividing a bill,choose 'Split the cost unequally' and there you go." +
                    " You'll be able to assign the cost to each person involved in the transaction";
        } else if (position == 1) {
            ans = "Not for now! but you can do it manually by choosing 'Split the cost unequally'." +
                    " You'll be able to assign the cost to each person involved in the transaction";
        } else if (position == 2) {
            ans = "Of Course, you can be a part of more than one group";
        } else if (position == 3) {
            ans = "No there is no any limits of adding bills at our app,'SPLIT-IT'." +
                    " Since we believe in the phrase 'more we split bills,more the tighten friendship'.";
        } else if (position == 4) {
            ans = "First,You should have an account at PhonePay," +
                    "since this the only way to make payments through our app." +
                    " Then,you are ready to go for payments.Thanks";
        } else if (position == 5) {
            ans = "Yes, you can send any grievance to us at our official mail given at Developer's Corner" +
                    " or can send us feedback on Feedback tab.";
        } else if (position == 6) {
            ans = ";";
        } else if (position == 7) {
            ans = ";";
        } else if (position == 8) {
            ans = ";";
        } else if (position == 9) {
            ans = ";";
        } else if (position == 10) {
            ans = ";";
        }

        textQ.setText(quest);
        textA.setText(ans);
    }

    public void goBack(View view) {
        startActivity(new Intent(this, FAQs.class));
    }
}
