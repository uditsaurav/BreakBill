package com.example.dude.projectworkz1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.dude.projectworkz1.FriendsHome.setdate;

public class FriendsHome extends AppCompatActivity {
    TextView friend_name_textview, friend_email_textview;
    String friend_name, my_email, friend_id, my_name, my_id, chat_id;
    int dues;
    ListView l;
    String date;
    ArrayList<Integer> paid = new ArrayList<>();
    ArrayList<Integer> expenses = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_home);
        l = findViewById(R.id.listView);
        friend_name_textview = findViewById(R.id.frndname);
        friend_email_textview = findViewById(R.id.frndemail);

        my_id = getIntent().getStringExtra("my_id");
        my_name = getIntent().getStringExtra("my_name");
        friend_id = getIntent().getStringExtra("friend_id");
        friend_name = getIntent().getStringExtra("friend_name");
        my_email = getIntent().getStringExtra("my_email");
        chat_id = getIntent().getStringExtra("chat_id");

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("chats").child(chat_id);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dues = 0;
                description.clear();
                paid.clear();
                expenses.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Chat c = d.getValue(Chat.class);
                    if (my_id.equalsIgnoreCase(c.getP1())) {
                        add(c.getDesc(), c.getA1(), c.getA2());
                        dues = dues + (c.getA1() - c.getA2());
                    } else {
                        add(c.getDesc(), c.getA3(), c.getA4());
                        dues = dues + (c.getA3() - c.getA4());
                    }
                }
                ((TextView) findViewById(R.id.dues)).setText((dues + ""));
                if (dues < 0) {
                    ((TextView) findViewById(R.id.dues)).setTextColor(Color.parseColor("#ed2222"));
                    findViewById(R.id.settlleup).setEnabled(false);
                } else if (dues > 0) {
                    ((TextView) findViewById(R.id.dues)).setTextColor(Color.parseColor("#34ed23"));
                    findViewById(R.id.settlleup).setEnabled(true);
                } else {
                    ((TextView) findViewById(R.id.dues)).setTextColor(Color.parseColor("#34ed23"));
                    findViewById(R.id.settlleup).setEnabled(false);
                }
                FrndAdapter adapter = new FrndAdapter(getApplicationContext(), description, paid, expenses);
                l.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        friend_name_textview.setText(friend_name);
    }


    public void add(String des, int pay, int exp) {
        paid.add(pay);
        expenses.add(exp);
        description.add(des);
    }

    public static String setdate() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + (month + 1) + "/" + year;
        return date;
    }


    public void settlerequest(View view) {
        Notification n = new Notification(my_id, my_name, chat_id, "requested for settlement of" + dues);
        DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("notifications").child(friend_id);
        String key = mref1.push().getKey();
        n.notification_id = key;
        mref1.child(key).setValue(n);
    }

    public void settleup(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
        alertDialog2.setTitle("Confirm Settle...");
        alertDialog2.setMessage("Are you sure wanna settle up?");
        alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("chats").child(chat_id);
                mref.setValue(null);
                Notification n=new Notification(my_id,my_name,chat_id,"acknowledged/settled your payment of"+dues);

                DatabaseReference mref1=FirebaseDatabase.getInstance().getReference("notifications").child(friend_id);
                String key=mref1.push().getKey();
                n.notification_id=key;

                mref1.child(key).setValue(n);
            }
        });
        alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog2.show();

    }

    public void addbill(View view) {
        Intent i = new Intent(this, addAbill.class);
        i.putExtra("friend_name", friend_name);
        i.putExtra("my_id", my_id);
        i.putExtra("chat_id", chat_id);
        i.putExtra("friend_id", friend_id);
        startActivity(i);
    }

    public void paymentway(View view) {
        Intent i = new Intent(this, PaymentGate.class);
        startActivity(i);
    }
}

class FrndAdapter extends ArrayAdapter<String> {
    Context c;
    ArrayList<Integer> paid = new ArrayList<>();
    ArrayList<Integer> expenses = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();

    FrndAdapter(Context c, ArrayList<String> desc, ArrayList<Integer> paid, ArrayList<Integer> expenses) {
        super(c, R.layout.single_row_friendshome, R.id.des, desc);
        this.c = c;
        this.paid = paid;
        this.expenses = expenses;
        this.desc = desc;
    }

    class MyViewHolder {
        TextView mydes;
        TextView paiddes;
        TextView mytranx;
        TextView mynet;

        public MyViewHolder(View v) {
            this.mydes = v.findViewById(R.id.des);
            this.paiddes = v.findViewById(R.id.amo);
            this.mytranx = v.findViewById(R.id.tranxdescription);
            this.mynet = v.findViewById(R.id.net);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        MyViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_friendshome, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.mydes.setText(desc.get(position));
        holder.paiddes.setText("You paid" + "   Rs " + paid.get(position) + "  on  " + setdate());
        if ((int) (paid.get(position)) - (int) (expenses.get(position)) >= 0) {
            holder.mytranx.setTextColor(Color.parseColor("#34ed23"));
            holder.mytranx.setText("You Lent");
            holder.mynet.setText("Rs " + ((int) (paid.get(position)) - (int) (expenses.get(position))));
        } else {
            holder.mytranx.setTextColor(Color.parseColor("#ed2222"));
            holder.mytranx.setText("You Borrowed");
            holder.mynet.setText("Rs " + ((int) (expenses.get(position)) - (int) (paid.get(position))));
        }

        return row;
    }

}
