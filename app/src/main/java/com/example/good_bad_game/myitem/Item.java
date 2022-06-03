package com.example.good_bad_game.myitem;

public class Item {

        private static String name;
        private static int image;

        public Item(String name, int image) {
            super();
            this.name = name;
            this.image = image;
        }

        public static String getName(){return name;}
        public static int getImage(){return image;}

}
