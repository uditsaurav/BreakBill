package com.example.dude.projectworkz1;

public class Activity_news {

    public  String friend_id,friend_name,message;

    public Activity_news()
    {
    }

    public Activity_news(String friend_name,String friend_id,String message)
    {
        this.friend_id=friend_id;
        this.friend_name=friend_name;
        this.message=message;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public String getMessage() {
        return message;
    }





}
