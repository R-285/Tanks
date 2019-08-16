package com.tanks.game.Bullets;

import com.tanks.IO.Input;
import com.tanks.game.Enemy.EnemyManager;
import com.tanks.game.Player;
import com.tanks.game.level.Level;
import com.tanks.game.level.TileType;
import com.tanks.graphics.TextureAtlas;
import com.tanks.utils.Audio;
import com.tanks.utils.Vector;

import java.awt.*;

public class PlayerBullet extends Bullet {

    private static final String SHOT_AUDIO = "bullet_shot.wav";
    private static final String METAL_AUDIO = "bullet_metal.wav";
    private static final String BRICK_AUDIO = "bullet_brick.wav";
    private static final int SPEED = 10;

    private long timeNow;
    private Audio audioShot, audioBrick, audioMetal;

    public long getTimeNow() {
        return timeNow;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isExist() {
        return isExist;
    }

    void setExist() {
        this.isExist = false;
        timeNow = System.currentTimeMillis();
    }



    public PlayerBullet(float x, float y, TextureAtlas atlas, Graphics2D graphics) {
        super(x, y, SPEED, graphics, atlas);

        audioShot = new Audio(SHOT_AUDIO);
        audioShot.setVolume(1);
        audioBrick = new Audio(BRICK_AUDIO);
        audioBrick.setVolume(1);
        audioMetal = new Audio(METAL_AUDIO);
        audioMetal.setVolume(1);
        newThread = new Thread(animationBulletDestruction, "It's Bullet Time Die");
    }


    public void start(float x, float y, Vector vector) {
        super.start(x, y, vector);
        audioShot.sound();

    }

    float yToTileScale, xToTileScale;
    private boolean temp;

    @Override
    public void update(Input input) {
        if (isExist) {
            yToTileScale = y / TILE_SCALE;
            xToTileScale = x / TILE_SCALE;
            temp = true;
            switch (vector) {
                case TOP:
                    if (((Level.getTileType((int) yToTileScale, (int) xToTileScale)).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) yToTileScale, (int) xToTileScale + 1).ordinal()) % 2 != 0)) {
                        isExist = false;
                        timeNow = System.currentTimeMillis();
                        animationBulletDestruction.setXY(x, y);
                        newThread = new Thread(animationBulletDestruction);
                        newThread.start();

                        switch (Level.getTileType((int) yToTileScale, (int) xToTileScale)) {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale, (int) xToTileScale);
                                audioBrick.sound();
                                temp = false;
                                break;
                            case VOID:
                            case METAL:
                                audioMetal.sound();
                                temp = false;
                                break;
                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                        }

                        switch (Level.getTileType((int) yToTileScale, (int) xToTileScale + 1)) {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale, (int) xToTileScale + 1);
                                if (temp) audioBrick.sound();
                                break;
                            case VOID:
                            case METAL:
                                if (temp) audioMetal.sound();
                                break;
                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                        }
                    }
                    y -= SPEED;
                    break;

                case RIGHT:
                    if ((Level.getTileType((int) yToTileScale, (int) xToTileScale).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) yToTileScale + 1, (int) xToTileScale).ordinal()) % 2 != 0)) {
                        isExist = false;
                        timeNow = System.currentTimeMillis();
                        animationBulletDestruction.setXY(x, y);
                        newThread = new Thread(animationBulletDestruction);
                        newThread.start();

                        switch (Level.getTileType((int) yToTileScale, (int) xToTileScale)) {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale, (int) xToTileScale);
                                audioBrick.sound();
                                temp = false;
                                break;

                            case VOID:
                            case METAL:
                                audioMetal.sound();
                                temp = false;
                                break;
                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                                break;
                            case HEADQUARTERS_SHADOW:
                                Player.headquartersDestroy();
                                break;
                        }

                        switch (Level.getTileType((int) yToTileScale + 1, (int) xToTileScale)) {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale + 1, (int) xToTileScale);
                                if (temp) audioBrick.sound();
                                break;

                            case VOID:
                            case METAL:
                                if (temp) audioMetal.sound();
                                break;

                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                                break;
                            case HEADQUARTERS_SHADOW:
                                Player.headquartersDestroy();
                                break;
                        }
                    }
                    x += SPEED;
                    break;
                case DOWN:
                    if (((Level.getTileType((int) yToTileScale, (int) xToTileScale)).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) yToTileScale, (int) xToTileScale + 1).ordinal()) % 2 != 0)) {
                        isExist = false;
                        timeNow = System.currentTimeMillis();
                        animationBulletDestruction.setXY(x, y);
                        newThread = new Thread(animationBulletDestruction);
                        newThread.start();

                        switch (Level.getTileType((int) (yToTileScale), (int) xToTileScale)) {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale, (int) xToTileScale);
                                audioBrick.sound();
                                temp = false;
                                break;

                            case VOID:
                            case METAL:
                                audioMetal.sound();
                                temp = false;
                                break;
                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                                break;
                            case HEADQUARTERS_SHADOW:
                                Player.headquartersDestroy();
                                break;
                        }

                        switch (Level.getTileType((int) yToTileScale, (int) xToTileScale + 1))  {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale, (int) xToTileScale + 1);
                                if (temp) audioBrick.sound();
                                break;

                            case VOID:
                            case METAL:
                                if (temp) audioMetal.sound();
                                break;

                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                                break;
                            case HEADQUARTERS_SHADOW:
                                Player.headquartersDestroy();
                                break;
                        }
                    }
                    y += SPEED;
                    break;
                case LEFT:
                    if (((Level.getTileType((int) yToTileScale, (int) xToTileScale)).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) yToTileScale + 1, (int) xToTileScale).ordinal()) % 2 != 0)) {
                        isExist = false;
                        timeNow = System.currentTimeMillis();
                        animationBulletDestruction.setXY(x, y);
                        newThread = new Thread(animationBulletDestruction);
                        newThread.start();

                        switch (Level.getTileType((int) yToTileScale, (int) xToTileScale)) {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale, (int) xToTileScale);
                                audioBrick.sound();
                                temp = false;
                                break;

                            case VOID:
                            case METAL:
                                audioMetal.sound();
                                temp = false;
                                break;
                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                                break;
                            case HEADQUARTERS_SHADOW:
                                Player.headquartersDestroy();
                                break;
                        }

                        switch (Level.getTileType((int) yToTileScale + 1, (int) xToTileScale))  {
                            case BRICK:
                                Level.bulletUpdate((int) yToTileScale + 1, (int) xToTileScale);
                                if (temp) audioBrick.sound();
                                break;

                            case VOID:
                            case METAL:
                                if (temp) audioMetal.sound();
                                break;

                            case SHADOW:
                                EnemyManager.SomeTankDed(x, y);
                                break;
                            case HEADQUARTERS_SHADOW:
                                Player.headquartersDestroy();
                                break;
                        }
                    }
                    x -= SPEED;
                    break;
                default:
                    System.out.println("Error");
            }

        }
    }


}
