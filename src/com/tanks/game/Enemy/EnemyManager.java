package com.tanks.game.Enemy;

import com.tanks.IO.Input;
import com.tanks.animations.destruction.AnimationDestruction;
import com.tanks.animations.spawn.AnimationSpawn;
import com.tanks.display.Display;
import com.tanks.game.Game;
import com.tanks.game.Player;
import com.tanks.game.level.Level;
import com.tanks.game.level.TileType;
import com.tanks.graphics.TextureAtlas;
import com.tanks.utils.Audio;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class EnemyManager implements Runnable {

    private static final int            SPRITE_SCALE = 48;
    private static final int            NUM_OF_ENEMIES = 20;
    private static final int            BULLET_SCALE = 13;
    private static final int            TILE_SCALE = SPRITE_SCALE / 2;
    private static final int            HALF_TILE_SCALE = TILE_SCALE / 2;
    private static final String         EXPLOSION_SOUND = "explosion_enemy.wav";
    private static final String         LVL_SOUND_MUSIC = "main_lvl.wav";
    private static final String         MISSION_COMPLETE_SOUND = "mission_complete.wav";
    private static final String         SECOND_WAVE_MUSIC = "SecondWave.wav";
    private static final String         PICTURE_DIRECTION = "res/";
    private static final String         MISSION_COMPLETE_PICTURE = "mission_complete.png";

    private static AnimationDestruction animationDestruction;
    private static Thread               newThread;
    private static ArrayList<OvarallEnemy> managerEnemy = new ArrayList<>();

    private Player                      player;
    private TextureAtlas                atlas;
    private AnimationSpawn              animationSpawn;
    private Graphics2D                  graphics;
    public static Audio                 audioOfLvl;
    private static Audio                audioExplosion,  audioMissionCompete;
    private BufferedImage               imageMissionComplete;
    private Game game;
    private double volume;
    private static boolean finishFlag = true;
    private static int NumEnemyInfo;


    public EnemyManager(Player player, TextureAtlas atlas, Graphics2D graphics, Game game) {
        this.game = game;
        this.player = player;
        this.atlas = atlas;
        this.graphics = graphics;

        volume = 1;
        NumEnemyInfo = 0;

        animationSpawn = new AnimationSpawn(graphics);
        animationDestruction = new AnimationDestruction(graphics);
        newThread = new Thread(animationDestruction);

        audioExplosion = new Audio(EXPLOSION_SOUND);
        audioExplosion.setVolume(volume);

        audioOfLvl = new Audio(LVL_SOUND_MUSIC);
        audioOfLvl.setVolume(volume);
        audioOfLvl.sound();

        audioMissionCompete = new Audio(MISSION_COMPLETE_SOUND);
        audioMissionCompete.setVolume(volume);

        try {
            imageMissionComplete = ImageIO.read(new File(PICTURE_DIRECTION + MISSION_COMPLETE_PICTURE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread myThread = new Thread(this, "ThreadOfEnemy");
        myThread.setDaemon(true);
        myThread.start();
    }

    //x1, y1 - bullet. x2, y2 - tank
    private static boolean isCrossing(float x1, float y1, float x2, float y2){
        boolean horizontal, vertical;
        horizontal = (Math.max(x1, x2) - Math.min(x1 + BULLET_SCALE, x2 + SPRITE_SCALE)) < 0;

        vertical = (Math.max(y1, y2) - Math.min(y1 + BULLET_SCALE, y2 + SPRITE_SCALE)) < 0;
        return(horizontal && vertical);
    }

    public void run() {

        for (int j = 0; j < NUM_OF_ENEMIES; j++) { /////////////////////

            do {
                try {
                    Thread.sleep(450);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            } while ((managerEnemy.size() > 3));
            animationSpawn.animationOfRespawn((((j + 1) % 3) * 12 + 2) * TILE_SCALE, 24);
            if (finishFlag)
                managerEnemy.add(new WeakEnemy((((j + 1) % 3) * 12 + 2) * TILE_SCALE, 24, 1.5f, player, atlas, graphics));
        }

        while (managerEnemy.size() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        secondWave();
    }

    private void secondWave(){
        NumEnemyInfo = 0;
        try {
            for (int i = 0; i < 100; i++) {

                Thread.sleep(20);
                audioOfLvl.setVolume(volume);
                volume -= 0.005;
            }
            audioOfLvl.stop();
            audioOfLvl = new Audio(SECOND_WAVE_MUSIC);
            volume = 0.98;
            audioOfLvl.setVolume(volume);
            audioOfLvl.sound();
            Thread.sleep(10000);
            Level.secondWaveUpdate();
            for (int j = 0; j < NUM_OF_ENEMIES - 4; j++) {
                do {
                    Thread.sleep(50);
                } while ((managerEnemy.size() > 7));
                if ((j % 8) < 3) {
                    animationSpawn.animationOfRespawn(((j % 3) * 12 + 2) * TILE_SCALE, TILE_SCALE);
                    if (finishFlag)
                        managerEnemy.add(new MediumEnemy(((j % 3) * 12 + 2) * TILE_SCALE, TILE_SCALE, 2f, player, atlas, graphics));
                } else if ((j % 8) < 5) {
                    animationSpawn.animationOfRespawn(((j % 2) * 24 + 2) * TILE_SCALE, TILE_SCALE * 8);
                    if (finishFlag)
                        managerEnemy.add(new DifficultEnemy((((j % 2)) * 24 + 2) * TILE_SCALE, TILE_SCALE * 8, 1.5f, player, atlas, graphics));
                } else if ((j % 8) == 5) {
                    animationSpawn.animationOfRespawn((14 + 2) * TILE_SCALE, 12 * TILE_SCALE);
                    if (finishFlag)
                        managerEnemy.add(new WeakEnemy((14 + 2) * TILE_SCALE, 12 * TILE_SCALE, 1.5f, player, atlas, graphics));
                } else {
                    animationSpawn.animationOfRespawn((((j) % 2) * 24 + 2) * TILE_SCALE, 16 * TILE_SCALE);
                    if (finishFlag)
                        managerEnemy.add(new WeakEnemy((((j % 2)) * 24 + 2) * TILE_SCALE, 16 * TILE_SCALE, 1.5f, player, atlas, graphics));
                }
            }

            while (managerEnemy.size() != 0) {
                    Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finishGame();
    }


    public static void SomeTankDed(float x, float y) {
        for (OvarallEnemy k : managerEnemy) {
            if (isCrossing(x, y, k.getX(), k.getY())) {
                if (((k.getY() + HALF_TILE_SCALE) % TILE_SCALE == 0) || ((k.getX() + HALF_TILE_SCALE) % TILE_SCALE == 0)) {
                    switch (k.getVector()) {
                        case TOP:
                        case DOWN:
                            Level.update((int) ((k.getX() + HALF_TILE_SCALE - 1) / TILE_SCALE), (int) ((k.getY() + HALF_TILE_SCALE - 1) / TILE_SCALE) + 1, TileType.EMPTY);
                            break;

                        case RIGHT:
                        case LEFT:
                            Level.update((int) ((k.getX() + HALF_TILE_SCALE - 1) / TILE_SCALE) + 1, (int) ((k.getY() + HALF_TILE_SCALE - 1) / TILE_SCALE), TileType.EMPTY);
                            break;
                        default:
                            System.out.println("Error");
                            break;
                    }
                }
                Level.update((int) ((k.getX() + HALF_TILE_SCALE - 1) / TILE_SCALE), (int) ((k.getY() + HALF_TILE_SCALE - 1) / TILE_SCALE), TileType.EMPTY);  //Delete shadow

                audioExplosion.sound();
                animationDestruction.setXY((int) k.getX(), (int) k.getY());
                newThread = new Thread(animationDestruction);
                newThread.start();
                managerEnemy.remove(k);

                Level.numEnemiesUpdate(11 - NumEnemyInfo/2, 30- (NumEnemyInfo %2));

                NumEnemyInfo++;
                break;
            }
        }
    }


    private void finishGame() {
        if(finishFlag) {
            try {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(25);
                    audioOfLvl.setVolume(volume);
                    volume -= 0.005;
                }
                game.running = false;
                stop();

                audioMissionCompete.sound();
                graphics.drawImage(imageMissionComplete, 60, 153, null);
                Display.swapBuffers();
                Display.swapBuffers();


                Thread.sleep(7500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.stop();
        }
    }

    public static void stop(){
        audioOfLvl.stop();
        finishFlag = false;
    }

    public void update(Input input) {

            for (OvarallEnemy k : managerEnemy) {
                k.update(input);
            }

    }

    public void render() {
        try {
            for (OvarallEnemy k : managerEnemy)
                k.render();
        }catch (ConcurrentModificationException exc){
            System.out.println();
        }
        animationSpawn.render();
        animationDestruction.render();
    }
}
