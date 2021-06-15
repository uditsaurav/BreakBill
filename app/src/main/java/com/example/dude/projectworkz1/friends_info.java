package com.example.dude.projectworkz1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class friends_info extends AppCompatActivity {

    String id_friend = "", name_friend = "", my_id, my_name;
    boolean already_friend = false;
    String received_email, received_email1;
    ListView listView;
    List<String> arrayList_friend_name, arrayList_friend_id, arrayList_chat_id;
    public static String id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_info);

        received_email1 = getIntent().getStringExtra("email");
        my_id = "";
        listView = findViewById(R.id.friend_list_view);
        arrayList_friend_name = new ArrayList<String>();
        arrayList_friend_id = new ArrayList<String>();
        arrayList_chat_id = new ArrayList<String>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(friends_info.this, FriendsHome.class);
                intent.putExtra("my_name", getIntent().getStringExtra("username"));
                intent.putExtra("my_id", my_id);
                intent.putExtra("friend_id", arrayList_friend_id.get(position));
                intent.putExtra("friend_name", arrayList_friend_name.get(position));
                intent.putExtra("chat_id", arrayList_chat_id.get(position));
                startActivity(intent);

            }
        });

        DatabaseReference mrefparent = FirebaseDatabase.getInstance().getReference("users");

        mrefparent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotpar) {

                for (DataSnapshot d : dataSnapshotpar.getChildren()) {
                    User u = d.getValue(User.class);
                    if (u.getemail().equalsIgnoreCase(received_email1)) {
                        my_id = u.getUid();
                        my_name = u.getname();
                        break;
                    }
                }

                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("friends").child(my_id);
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayList_friend_id.clear();
                        arrayList_friend_name.clear();
                        arrayList_chat_id.clear();
                        for (DataSnapshot friend_ : dataSnapshot.getChildren()) {
                            friend g = friend_.getValue(friend.class);
                            String friend_name = Objects.requireNonNull(g).getFriend_name();
                            String friend_id = g.getFriend_id();
                            String chat_id = g.getChat_id();
                            arrayList_friend_name.add(friend_name);
                            arrayList_friend_id.add(friend_id);
                            arrayList_chat_id.add(chat_id);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(friends_info.this, android.R.layout.simple_list_item_1, arrayList_friend_name);

                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "oncancld on create", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void addFriend(View view) {
        DatabaseReference mref2 = FirebaseDatabase.getInstance().getReference("users");
        received_email = ((EditText) findViewById(R.id.friend_email)).getText().toString();
        if (received_email.length() == 0) {
            Toast.makeText(getApplicationContext(), "empty text", Toast.LENGTH_SHORT).show();
            return;
        }
        mref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean registered_user = false;
                id_friend = "";
                name_friend = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user.getemail().equalsIgnoreCase(received_email)) {
                        id_friend = user.getUid();
                        name_friend = user.getname();
                        registered_user = true;
                        break;
                    }
                }
                if (registered_user == true) {
                    DatabaseReference mref3 = FirebaseDatabase.getInstance().getReference("friends").child(my_id);
                    mref3.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            already_friend = false;
                            for (DataSnapshot friend1 : dataSnapshot.getChildren()) {
                                friend f = friend1.getValue(friend.class);
                                if (id_friend.equalsIgnoreCase(f.getFriend_id())) {
                                    already_friend = true;
                                }
                            }
                            if (already_friend == false) {
                                friend f1 = new friend(id_friend, name_friend, my_id);
                                DatabaseReference mref4 = FirebaseDatabase.getInstance().getReference("friends").child(my_id);
                                mref4.push().setValue(f1);
                                friend f2 = new friend(my_id, my_name, my_id);
                                f2.chat_id = f1.chat_id;
                                mref4 = FirebaseDatabase.getInstance().getReference("friends").child(id_friend);
                                mref4.push().setValue(f2);
                            } else {
                                Toast.makeText(getApplicationContext(), "ALREADY FRIEND", Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), " not found user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "data base error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
