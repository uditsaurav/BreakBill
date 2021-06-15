package com.example.dude.projectworkz1;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reg_user {

    private String received_email,received_name;

    //private boolean is_registered;
    public Reg_user(String received_email, String received_name)
    {
        this.received_email=received_email;
        this.received_name=received_name;
    }
    public void register()
    {
        Log.i("k","in register");
        DatabaseReference mref1= FirebaseDatabase.getInstance().getReference("users");



        mref1.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean registered=false;
                Log.i("k","ok");
                for(DataSnapshot users:dataSnapshot.getChildren())
                {
                    Log.i("k","ok1");
                    User user=users.getValue(User.class);
                    if(user.getemail().equalsIgnoreCase(received_email))
                    {
                        Log.i("k","ok11");
                        registered=true;
                        break;
                    }
                }
                Log.i("k","ok2");
                if(registered==false)
                {
                    Log.i("k","ok3");
                        writeNewUser(received_email,received_name);
                }
            }


            public void onCancelled(DatabaseError databaseError) {
                Log.i("err","database error");
            }
        });

    }
    private void writeNewUser(String email,String name) {
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("users");
        User user = new User(email,name);
        String key=mref.push().getKey();
        user.uid=key;
        mref.child(key).setValue(user);

        //mref.child(key).child("groups").push().setValue("group1");
        //mref.child(key).child("groups").push().setValue("group2");
        //mref.child("users").push().setValue(user);
    }


}
