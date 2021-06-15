package com.example.dude.projectworkz1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_feed extends AppCompatActivity {

    String email,myid;
    List<String> feed;
    ListView feedListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        feedListView=findViewById(R.id.feed_list);
        email=getIntent().getStringExtra("email");
        feed=new ArrayList<String>();
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference("users");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot s:dataSnapshot.getChildren())
                {
                    User u=s.getValue(User.class);
                    if(u.getemail().equalsIgnoreCase(email))
                    {
                        myid=u.getUid();
                        break;
                    }
                }
                DatabaseReference mref1= FirebaseDatabase.getInstance().getReference("activity").child(myid);
                mref1.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        feed.clear();;
                        for(DataSnapshot d:dataSnapshot.getChildren())
                        {
                            Activity_news a=d.getValue(Activity_news.class);
                            feed.add(0,a.getFriend_name()+" "+a.getMessage());
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Activity_feed.this,android.R.layout.simple_list_item_1,feed);

                        feedListView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
