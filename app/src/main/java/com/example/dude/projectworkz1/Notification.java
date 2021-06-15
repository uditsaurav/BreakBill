package com.example.dude.projectworkz1;

public class Notification {

    String requester,friend_name,chat_id,notification_id,message;

    public Notification()
    {

    }

    public Notification(String friend_id,String friend_name,String chat_id,String message)
    {
        this.message=message;
        this.requester=friend_id;
        this.friend_name=friend_name;
        this.chat_id=chat_id;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public String getMessage() {
        return message;
    }

    public String getRequester() {
        return requester;
    }
}