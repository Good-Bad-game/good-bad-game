package com.example.good_bad_game.myitem;

public class Item {

        private String name;
        private int image;

        public Item(String name, int image) {
            super();
            this.name = name;
            this.image = image;
        }

        public String getName(){return name;}
        public int getImage(){return image;}

}
