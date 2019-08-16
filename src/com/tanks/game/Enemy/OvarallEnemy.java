package com.tanks.game.Enemy;

import com.tanks.IO.Input;
import com.tanks.game.Bullets.EnemyBullet;
import com.tanks.game.Entity;
import com.tanks.utils.Vector;

import java.util.Random;


public abstract class OvarallEnemy extends Entity {
    static final int SPRITE_SCALE = 48;
    static final int SPEED_OF_ANIMATION = 2;
    static final int HALF_TILE = TILE_SCALE / 2;


    Random rnd = new Random(System.currentTimeMillis());
    EnemyBullet enemyBullet;

    OvarallEnemy(float x, float y, float speed) {
        super(x, y, speed);

        vector = Vector.DOWN;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    Vector getVector(){
        return vector;
    }

    boolean newEvents(int n) {
        if (rnd.nextInt(8) == 1) {
            cheangeVector(n);
            return true;
        }
        return false;
    }

    private void cheangeVector(int numOfDirection) {    //if there is a wall of the enemy, then the chance of a 180-fold turn is twice as large

        switch (rnd.nextInt(numOfDirection)) {
            case 0:
                vector = Vector.fromNumeric((vector.numeric() + 1) % 4);    // Clockwise rotation
                break;
            case 1:
                vector = Vector.fromNumeric((vector.numeric() + 3) % 4);    // Anti - Clockwise rotation
                break;
            case 2:
            case 3:
                vector = Vector.fromNumeric((vector.numeric() + 2) % 4);    // 180^o rotation. If numOfDirection == 4 -> chance 180^o rotation = 50%;
                break;
            default:
                System.out.println("Error");
                break;
        }
    }

    public abstract void update(Input input);
}
