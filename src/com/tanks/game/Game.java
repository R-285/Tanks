package com.tanks.game;

import com.tanks.IO.Input;
import com.tanks.display.Display;
import com.tanks.game.Enemy.EnemyManager;
import com.tanks.game.level.Level;
import com.tanks.graphics.TextureAtlas;
import com.tanks.utils.Audio;
import com.tanks.utils.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Game implements Runnable {

    private static final int     UPDATE_RATE = 60;
    private static final float   UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    private static final long    IDLE_TIME = 1; //ms
    private static final String  TITLE = "Tanks";
    public static final String  ATLAS_FILE_NAME = "final_textureAtlas4.0.png";
    private static final String AUDIO_PAUSE_MUSIC = "pause.wav";


    public static boolean running;
    private Thread gameThread;
    private static Input input;
    private Level lvl;
    private EnemyManager enemyManager;
    private static Audio audioOfPause;

    private static Player player;



    private static final int WIDTH = 768;
    private static final int HEIGHT = 672;
    private static final int CLEAR_COLOR = 0xff000000;
    private static final int NUM_BUFFERS = 3;
    //---------------------------------------------------------------------

    public Game() {

        running = false;

        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        Graphics2D graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);

        audioOfPause = new Audio(AUDIO_PAUSE_MUSIC);
        audioOfPause.setVolume(1);

        TextureAtlas atlas = new TextureAtlas(ATLAS_FILE_NAME);
        lvl = new Level(atlas, graphics);
        player = new Player(240, 600, 2.25f, atlas, graphics, this);
        enemyManager = new EnemyManager(player, atlas, graphics, this);
    }


    public synchronized void start() {
        if (running) return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    static void pauseGame(){
        running = false;
        EnemyManager.audioOfLvl.stop();
        audioOfPause.sound();
        try {
            Thread.sleep(100);
            while (! input.getKey(KeyEvent.VK_ENTER)) {
                Thread.sleep(100);
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = true;
        lastTime = Time.get();
        EnemyManager.audioOfLvl.sound();
    }

    public synchronized void stop() {
        try {
            gameThread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cleanUp();
        Display.destroy();
    }

    private void update() {
        if(running) {
            player.update(input);
            enemyManager.update(input);
        }

    }

    private void render() {
        if(running) {
            Display.clear();
            player.render();
            enemyManager.render();
            lvl.render();
            Display.swapBuffers();
        }
    }


    private static long lastTime = Time.get();

    public void run() {
//I create these variable here because next "WHILE" runes 60 times pre second.
        int fps = 0;
        int upd = 0;
        int updl = 0;
        long count = 0;

        long now;
        long elapsedTime;
        boolean render;


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

    private void cleanUp() {
        Display.destroy();
    }
}