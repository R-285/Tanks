package com.tanks.animations.destruction;

import com.tanks.graphics.TextureAtlas;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.tanks.game.Game.ATLAS_FILE_NAME;

public class AnimationDestruction implements Runnable {

    private static final int ENEMY_EXPLOSION = 48;
    private static final int TILE_SCALE = ENEMY_EXPLOSION / 2;
    private static final int PLAYER_EXPLOSION = ENEMY_EXPLOSION * 2;
    private static final int TIME_OF_SPRITE = 45;


    private AnimationDestructionType stateAnimation;

    private static Map<AnimationDestructionType, AnimationD> types;
    private boolean isExist;


    public AnimationDestruction(Graphics2D graphics) {
        isExist = false;
        TextureAtlas atlas = new TextureAtlas(ATLAS_FILE_NAME);
        types = new HashMap<>();
        types.put(AnimationDestructionType.SMALL_ENEMY, new AnimationD(atlas.cut(16 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, ENEMY_EXPLOSION, ENEMY_EXPLOSION), graphics));
        types.put(AnimationDestructionType.MEDIUM_ENEMY, new AnimationD(atlas.cut(17 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, ENEMY_EXPLOSION, ENEMY_EXPLOSION), graphics));
        types.put(AnimationDestructionType.BIG_ENEMY, new AnimationD(atlas.cut(18 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, ENEMY_EXPLOSION, ENEMY_EXPLOSION), graphics));
        types.put(AnimationDestructionType.MEDIUM_PLAYER, new AnimationD(atlas.cut(19 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, PLAYER_EXPLOSION, PLAYER_EXPLOSION), graphics));
        types.put(AnimationDestructionType.BIG_PLAYER, new AnimationD(atlas.cut(19 * ENEMY_EXPLOSION, 8 * ENEMY_EXPLOSION, PLAYER_EXPLOSION, PLAYER_EXPLOSION), graphics));
    }

    private static int x, y;

    public void setXY(int x, int y) {
        AnimationDestruction.x = x;
        AnimationDestruction.y = y;
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
            Thread.sleep(TIME_OF_SPRITE);


            x -= TILE_SCALE;
            y -= TILE_SCALE;
            stateAnimation = AnimationDestructionType.MEDIUM_PLAYER;
            Thread.sleep(TIME_OF_SPRITE);

            stateAnimation = AnimationDestructionType.BIG_PLAYER;
            Thread.sleep(TIME_OF_SPRITE - 20);

            stateAnimation = AnimationDestructionType.MEDIUM_PLAYER;
            Thread.sleep(TIME_OF_SPRITE);


            x += TILE_SCALE;
            y += TILE_SCALE;
            stateAnimation = AnimationDestructionType.BIG_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            stateAnimation = AnimationDestructionType.MEDIUM_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            stateAnimation = AnimationDestructionType.SMALL_ENEMY;
            Thread.sleep(TIME_OF_SPRITE);

            isExist = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void render() {
        if (isExist) types.get(stateAnimation).render(x, y);
    }

}