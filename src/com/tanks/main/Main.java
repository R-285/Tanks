package com.tanks.main;

import com.tanks.game.Game;

public class Main {



    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.start();
        Game tanks = new Game();
        tanks.start();

    }
}