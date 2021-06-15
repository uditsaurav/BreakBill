package com.example.dude.projectworkz1;

public class User {

    public String email,name,uid;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name) {
        this.email=email;
        this.name=name;
    }

    public String getemail()
    {
        return this.email;
    }
    public String getname()
    {
        return this.name;
    }

    public String getUid() {
        return uid;
    }
}
