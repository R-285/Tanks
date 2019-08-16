package com.tanks.animations.spawn;

import com.tanks.graphics.TextureAtlas;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.tanks.animations.spawn.AnimationSpawnType.*;
import static com.tanks.game.Game.ATLAS_FILE_NAME;

public class AnimationSpawn implements Runnable {
    private static final int SLEEP_TIME = 50;
    private static final int STAR_SCALE = 48;

    private static Map<AnimationSpawnType, Animation> types;
    private AnimationSpawnType stateAnimation;
    private boolean isExist;

    public AnimationSpawn(Graphics2D graphics) {
        isExist = false;
        TextureAtlas atlas = new TextureAtlas(ATLAS_FILE_NAME);
        types = new HashMap<>();
        types.put(AnimationSpawnType.SMALL_STAR, new Animation(atlas.cut(16 * STAR_SCALE, 6 * STAR_SCALE, STAR_SCALE, STAR_SCALE), graphics));
        types.put(AnimationSpawnType.MEDIUM_STAR, new Animation(atlas.cut(17 * STAR_SCALE, 6 * STAR_SCALE, STAR_SCALE, STAR_SCALE), graphics));
        types.put(AnimationSpawnType.BIG_STAR, new Animation(atlas.cut(18 * STAR_SCALE, 6 * STAR_SCALE, STAR_SCALE, STAR_SCALE), graphics));
        types.put(AnimationSpawnType.GIANT_STAR, new Animation(atlas.cut(19 * STAR_SCALE, 6 * STAR_SCALE, STAR_SCALE, STAR_SCALE), graphics));
    }

    @Override
    public void run() {

    }

    private int x, y;
    public void animationOfRespawn(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            isExist = true;

            stateAnimation = SMALL_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = MEDIUM_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = BIG_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = GIANT_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = BIG_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = MEDIUM_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = SMALL_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = MEDIUM_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = BIG_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = GIANT_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = BIG_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = MEDIUM_STAR;
            Thread.sleep(SLEEP_TIME);

            stateAnimation = SMALL_STAR;
            Thread.sleep(SLEEP_TIME);

            isExist = false;
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void render() {
        if (isExist) types.get(stateAnimation).render(x, y);
    }

}
