package com.tanks.main;

import com.tanks.IO.Input;
import com.tanks.display.Display;
import com.tanks.utils.Audio;
import com.tanks.utils.Time;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu {

    private static final int WIDTH = 768;
    private static final int HEIGHT = 672;
    private static final int CLEAR_COLOR = 0xff000000;
    private static final int NUM_BUFFERS = 3;
    private static final int UPDATE_RATE = 60;
    private static final long IDLE_TIME = 1; //ms
    private static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    private static final String TITLE = "Tanks";
    private static final String MENU_PICTURE = "menu.png";
    private static final String TANK_PICTURE = "tank_picture.png";
    private static final String INFO_PAGE = "menu_info.png";
    private static final String LOADING_PICTURE = "game_loading.png";
    private static final String SCREEN_SHOT = "screen_shot.png";
    private static final String MENU_MUSIC_DIRECTION = "menu_music.wav";
    private static final String RES_DIRECTION = "res/";

    private boolean running;
    private Input input;
    private Graphics2D graphics;
    private int yMenu, yTank;
    private long timeNow;
    private boolean choiceInMenu; //false - START, true - INFO
    private BufferedImage imageMenu, imageTank, imageInfo, imageLoading, screenImage;
    private Audio audio;
    private double volume;

    Menu() {
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        timeNow = 0;
        volume = 1;
        yMenu = HEIGHT;
        yTank = -100;
        choiceInMenu = false;
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);

        audio = new Audio(MENU_MUSIC_DIRECTION);
        audio.setVolume(volume);
        audio.sound();

        try {
            imageMenu = ImageIO.read(new File(RES_DIRECTION + MENU_PICTURE));
            imageTank = ImageIO.read(new File(RES_DIRECTION + TANK_PICTURE));
            imageInfo = ImageIO.read(new File(RES_DIRECTION + INFO_PAGE));
            imageLoading = ImageIO.read(new File(RES_DIRECTION + LOADING_PICTURE));
            screenImage = ImageIO.read(new File(RES_DIRECTION + SCREEN_SHOT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        running = false;
    }

    public synchronized void start() {

        if (running) return;
        running = true;
        run();
    }


    private void update() {

        if (yMenu > 0) {
            yMenu -= 6;
            if (yMenu == 6) yTank = 377;
            return;
        }

        if (input.getKey(KeyEvent.VK_ESCAPE)) {
            running = false;
        }
        if (input.getKey(KeyEvent.VK_UP)) {
            yTank = 377;
            choiceInMenu = false;
        } else if (input.getKey(KeyEvent.VK_DOWN)) {
            yTank = 451;
            choiceInMenu = true;
        } else if (input.getKey(KeyEvent.VK_ENTER)) {
            if (choiceInMenu) infoFunction();
            else startMainGame();
        }
    }


    private void startMainGame(){
        try {
            running = false;

            Thread.sleep(300);
            yMenu = HEIGHT;
            for(int i = 0; i < 200; i++) {

                graphics.drawImage(imageLoading, 0, yMenu, null);
                Display.swapBuffers();
                Display.swapBuffers();
                Thread.sleep(5);
                if (yMenu > 0) yMenu -= 6;
                if (volume > 0.5) {
                    audio.setVolume(volume-=0.003);
                }
            }
            audio.stop();
            yMenu = HEIGHT;
            for(int i = 0; i < 113; i++) {
                graphics.drawImage(screenImage, 0, yMenu, null);
                Display.swapBuffers();
                Display.swapBuffers();
                Thread.sleep(5);
                yMenu -= 6;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void infoFunction() {
        try {
            running = false;

            graphics.drawImage(imageInfo, 0, 0, null);
            Display.swapBuffers();
            Display.swapBuffers();

            Thread.sleep(300);

            while (!input.getKey(KeyEvent.VK_ENTER)) {
                graphics.drawImage(imageInfo, 0, 0, null);
                Thread.sleep(50);
            }

            yTank = -100;
            choiceInMenu = false;
            yMenu = HEIGHT;
            Thread.sleep(200);
            lastTime = Time.get();

            running = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long lastTime;
    private void render() {
        Display.clear();

        graphics.drawImage(imageMenu, 0, yMenu, null);
        graphics.drawImage(imageTank, 272, yTank, null);  //377  451

        Display.swapBuffers();
    }

    private void run() {
//I create these variable here because next "WHILE" runes 60 times pre second.
        int fps = 0;
        int upd = 0;
        int updl = 0;
        long count = 0;

        long now;
        long elapsedTime;
        boolean render;

        lastTime = Time.get();
        float delta = 0;
//////////////////////////////////////////////////////////////////////////////

        while (running) {
            now = Time.get();
            elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) updl++;
                else render = true;
            }

            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (count >= Time.SECOND) {
                Display.setTitle(TITLE + "  | fps: " + fps + ", Upd: " + upd + ", updl: " + updl);

                fps = 0;
                upd = 0;
                updl = 0;
                count = 0;
            }
        }
    }
}