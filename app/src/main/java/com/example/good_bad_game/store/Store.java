package com.example.good_bad_game.store;

public class Store {
    String name;
    int image;

    public Store(String name, int image) {
        super();
        this.name = name;
        this.image = image;
    }
    public String getName(){return name;}
    public  int getImage(){return image;}

}
