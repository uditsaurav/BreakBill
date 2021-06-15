package com.example.dude.projectworkz1;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addAbill extends AppCompatActivity {

    TextView friend_name_textview;
    EditText youexpense, frndexpense, youpaid, frndpaid, amount;
    String my_id, friend_id, chat_id, friend_name;
    int tB, yC, yP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_new);

        my_id = getIntent().getStringExtra("my_id");
        friend_id = getIntent().getStringExtra("friend_id");
        chat_id = getIntent().getStringExtra("chat_id");
        friend_name = getIntent().getStringExtra("friend_name");
        friend_name_textview = findViewById(R.id.textfrndNameheading);
        friend_name_textview.setText(friend_name);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width - 15), (int) (height * .7));
        youexpense = findViewById(R.id.youconsumed);
        frndexpense = findViewById(R.id.heconsumed);
        frndpaid = findViewById(R.id.hepaid);
        youpaid = findViewById(R.id.youpaid);
        amount = findViewById(R.id.total_bill);
        checkValidation();
        amountValidation();
    }

    public void save(View view) {
        String desc = ((TextView) findViewById(R.id.description)).getText().toString(), totalBill = ((TextView) findViewById(R.id.total_bill)).getText().toString(),
                youConsumed = ((TextView) findViewById(R.id.youconsumed)).getText().toString(), youPaid = ((TextView) findViewById(R.id.youpaid)).getText().toString();

        tB = Integer.parseInt(totalBill);
        yC = Integer.parseInt(youConsumed);
        yP = Integer.parseInt(youPaid);

        DatabaseReference d = FirebaseDatabase.getInstance().getReference("chats").child(chat_id);
        Chat chat = new Chat(desc, my_id, friend_id, tB, yP, yC);
        d.push().setValue(chat);

        String message;
        if(yP-yC>0)
        {
            message="lent you a sum of "+(yP-yC)+" for "+desc;
        }
        else
        {
            message="borrowed from you a sum of"+(yC-yP)+" for "+desc;
        }

        Activity_news a = new Activity_news(getSharedPreferences("mypref", Context.MODE_PRIVATE).getString("email",""),my_id,message);

        DatabaseReference mref1=FirebaseDatabase.getInstance().getReference("activity");
        mref1.child(friend_id).push().setValue(a);

        finish();

    }

    public void equalsplit(View view) {

        try {
            int a = Integer.parseInt(amount.getText().toString());
            youexpense.setText("" + (a / 2));
            youpaid.setText("" + a);
            checkValidation();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    public void ok(View view) {
    }

    public void checkValidation() {
        youexpense.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int a = Integer.parseInt(amount.getText().toString());
                    int n = Integer.parseInt(editable.toString());
                    if (n > a) {
                        editable.replace(0, editable.length(), amount.getText().toString());
                        frndexpense.setText("0");
                    } else {
                        frndexpense.setText("" + (a - n));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

        });
        youpaid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int a = Integer.parseInt(amount.getText().toString());
                    int p = Integer.parseInt(editable.toString());
                    if (p > a) {
                        editable.replace(0, editable.length(), amount.getText().toString());
                        frndpaid.setText("0");
                    } else {
                        frndpaid.setText("" + (a - p));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void amountValidation() {
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                youexpense.setText("");
                frndexpense.setText("");
                youpaid.setText("");
                frndpaid.setText("");

            }

        });
    }

}
