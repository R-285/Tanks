package com.tanks.game;

import com.tanks.IO.Input;
import com.tanks.utils.Vector;


public abstract class Entity {

    protected static final int TILE_SCALE = 24;


    protected float         x;
    protected float         y;
    protected float         speed;
    protected Vector vector;

    public Entity(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public abstract void update(Input input);
    public abstract void render();



}
