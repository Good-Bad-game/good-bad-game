package com.example.good_bad_game.readyroom;

public class Room {
    private  String id;
    private String match_room;

    public String getRoomNumber(){return id;}
    public String getRoomTitle(){return match_room;}

    public Room(String num, String title){
        this.id = num;
        this.match_room = title;
    }

    public Room(String title){
        this.match_room = title;
    }
}
