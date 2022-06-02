package com.example.good_bad_game.readyroom;

public class Room {
    private String id;
    private String match_room;
    private String host;

    public String getRoomNumber(){return id;}
    public String getRoomTitle(){return match_room;}
    public String getHost(){return host;}


    public Room(String id, String title, String host){
        this.id = id;
        this.host = host;
        this.match_room = title;
    }

    public Room(String title){
        this.match_room = title;
    }
}
