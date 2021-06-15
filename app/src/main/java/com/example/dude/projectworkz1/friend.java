package com.example.dude.projectworkz1;

public class friend {

    public String friend_id, friend_name, chat_id;

    public friend() {

    }

    public friend(String friend_id, String friend_name, String my_id) {
        this.friend_id = friend_id;
        this.friend_name = friend_name;
        this.chat_id = my_id + friend_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public String getChat_id() {
        return chat_id;
    }
}
