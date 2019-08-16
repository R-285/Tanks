package com.tanks.animations.destruction;

import com.tanks.graphics.TextureAtlas;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AnimationBulletDestruction implements Runnable {

    private static final String ATLAS_FILE_NAME = "final_textureAtlas4.0.png";
    private static final int ENEMY_EXPLOSION = 48;
    private static final int TIME_OF_SPRITE = 45;


    private AnimationDestructionType stateAnimation;
    private Map<AnimationDestructionType, AnimationD> tiles;
    private boolean isExist;


    public AnimationBulletDestruction(Graphics2D graphics) {
        isExist = false;
        TextureAtlas atlas = new TextureAtlas(ATLAS_FILE_NAME);
        tiles = new HashMap<>();
        tiles.put(AnimationDestructionType.SMALL_ENEMY, new AnimationD(atlas.cut(16 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, ENEMY_EXPLOSION, ENEMY_EXPLOSION), graphics));
        tiles.put(AnimationDestructionType.MEDIUM_ENEMY, new AnimationD(atlas.cut(17 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, ENEMY_EXPLOSION, ENEMY_EXPLOSION), graphics));
        tiles.put(AnimationDestructionType.BIG_ENEMY, new AnimationD(atlas.cut(18 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, ENEMY_EXPLOSION, ENEMY_EXPLOSION), graphics));

    }

    private int x, y;

    public void setXY( float x, float y){
        this.x = (int) x - 17;
        this.y = (int) y - 17;
    }

    @Override
    public void run() {
        try {

            isExist = true;
            stateAnimation = AnimationDestructionType.SMALL_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            stateAnimation = AnimationDestructionType.MEDIUM_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            stateAnimation = AnimationDestructionType.BIG_ENEMY;
            Thread.sleep(TIME_OF_SPRITE - 20);

            stateAnimation = AnimationDestructionType.MEDIUM_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            stateAnimation = AnimationDestructionType.SMALL_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            isExist = false;

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        if (isExist) tiles.get(stateAnimation).render(x, y);
    }

}