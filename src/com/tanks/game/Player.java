package com.tanks.game;

import com.tanks.IO.Input;
import com.tanks.animations.destruction.AnimationDestruction;
import com.tanks.display.Display;
import com.tanks.game.Bullets.PlayerBullet;
import com.tanks.game.Enemy.EnemyManager;
import com.tanks.game.level.Level;
import com.tanks.graphics.Sprite;
import com.tanks.graphics.SpriteSheet;
import com.tanks.graphics.TextureAtlas;
import com.tanks.utils.Audio;
import com.tanks.utils.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    private static final int SPRITE_SCALE = 48;
    private static final int SPEED_OF_ANIMATION = 2;
    private static final String ENGINE_SOUND = "engine_sound.wav";
    private static final String MISSION_FAILED_SOUND = "mission_failed.wav";
    private static final String MEGUMIN_EXPLOSION_SOUND = "explosion_Megumin.wav";
    private static final String MISSION_FAILED_PICTURE = "res/mission_failed.png";
    private static final String MEGUMIN_EXPLOSION_PICTURE= "res/explosion.png";
    private static final String GAME_OVER = "res/game_over.png";

    private Heading heading;
    private Map<Heading, Sprite> spriteMap;
    private static AnimationDestruction animationDestruction;
    private Audio engineAudio;
    private static Audio missionFailedAudio, explosionSound;
    private static Game game;
    private static BufferedImage missionFailedPicture, explosionImage, gameOverPicture;
    private static Graphics2D graphics;

    public PlayerBullet playerBullet;

    private enum Heading {
        NORTH_F(0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST_F(2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH_F(4 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST_F(6 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),

        NORTH_S(1 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST_S(3 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH_S(5 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST_S(7 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

        private int x, y, h, w;

        Heading(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }

        protected BufferedImage texture(TextureAtlas atlas) {
            return atlas.cut(x, y, w, h);
        }
    }


    Player(float x, float y, float speed, TextureAtlas atlas, Graphics2D graphics, Game game) {
        super(x, y, speed);
        this.game=game;
        this.graphics = graphics;

        engineAudio = new Audio(ENGINE_SOUND);
        engineAudio.setVolume(0.8);
        engineAudio.setPosition(110000);

        missionFailedAudio = new Audio(MISSION_FAILED_SOUND);
        missionFailedAudio.setVolume(1);

        explosionSound = new Audio(MEGUMIN_EXPLOSION_SOUND);
        explosionSound.setVolume(1);

        try {
            missionFailedPicture = ImageIO.read(new File(MISSION_FAILED_PICTURE));
            explosionImage = ImageIO.read(new File(MEGUMIN_EXPLOSION_PICTURE));
            gameOverPicture = ImageIO.read(new File(GAME_OVER));
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerBullet = new PlayerBullet(0, 0, atlas, graphics);
        animationDestruction = new AnimationDestruction(graphics);

        heading = Heading.NORTH_F;
        vector = Vector.TOP;
        spriteMap = new HashMap<>();

        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, graphics);
            spriteMap.put(h, sprite);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private float newX;
    private float newY;
    private byte animationSpeed = 0;
    private int reletion;
    private long timeNow = 0;
    private Input input;

    @Override
    public void update(Input input) {        //!!

        this.input = input;
        if (input.getKey(KeyEvent.VK_ENTER)){
            Game.pauseGame();
        }
        if (input.getKey(KeyEvent.VK_SPACE)) {  //BulletTime!!!
            if (!playerBullet.isExist()) {
                if (System.currentTimeMillis() - playerBullet.getTimeNow() > 200)
                    playerBullet.start(x, y, vector);
            }
        }

        newX = x;
        newY = y;

        if (input.getKey(KeyEvent.VK_UP)) {
            engineAudio.specialSound();
            vector = Vector.TOP;
            newY -= speed;
            reletion = (int) newX / TILE_SCALE;
            x = newX = TILE_SCALE * (((newX / TILE_SCALE - reletion > 0.6) ? (reletion + 1) : reletion));  //that need for "tilemooving"
            if (animationSpeed++ == SPEED_OF_ANIMATION) {
                if ((heading == Heading.NORTH_F)) heading = Heading.NORTH_S;
                else heading = Heading.NORTH_F;
                animationSpeed = 0;
            }

        } else if (input.getKey(KeyEvent.VK_RIGHT)) {
            engineAudio.specialSound();
            vector = Vector.RIGHT;
            newX += speed;
            reletion = (int) newY / TILE_SCALE;
            y = newY = TILE_SCALE * (((newY / TILE_SCALE) - reletion > 0.6) ? (reletion + 1) : reletion);
            if (animationSpeed++ == SPEED_OF_ANIMATION) {
                if (heading == Heading.EAST_S) heading = Heading.EAST_F;
                else heading = Heading.EAST_S;
                animationSpeed = 0;
            }
        } else if (input.getKey(KeyEvent.VK_DOWN)) {
            engineAudio.specialSound();
            vector = Vector.DOWN;
            newY += speed;
            reletion = (int) newX / TILE_SCALE;
            x = newX = TILE_SCALE * (((newX / TILE_SCALE) - reletion > 0.6) ? (reletion + 1) : reletion);
            if (animationSpeed++ == SPEED_OF_ANIMATION) {
                if (heading == Heading.SOUTH_F) heading = Heading.SOUTH_S;
                else heading = Heading.SOUTH_F;
                animationSpeed = 0;
            }
        } else if (input.getKey(KeyEvent.VK_LEFT)) {
            engineAudio.specialSound();
            vector = Vector.LEFT;
            newX -= speed;
            reletion = (int) (newY / TILE_SCALE);
            y = newY = TILE_SCALE * (((newY / TILE_SCALE) - reletion > 0.6) ? (reletion + 1) : reletion);
            if (animationSpeed++ == SPEED_OF_ANIMATION) {
                if (heading == Heading.WEST_F) heading = Heading.WEST_S;
                else heading = Heading.WEST_F;
                animationSpeed = 0;
            }
        }


        if (vector == Vector.LEFT) {
            if (((Level.getTileType((int) (newY / TILE_SCALE), (int) (newX / TILE_SCALE))).ordinal() % 2 != 0)
                    || ((Level.getTileType((((int) (newY / TILE_SCALE)) + 1), (int) (newX / TILE_SCALE)).ordinal()) % 2 != 0))
                newX = x;
        } else if (vector == Vector.RIGHT) {
            if (((Level.getTileType((int) (newY / TILE_SCALE), ((int) (newX / TILE_SCALE) + 2)).ordinal() % 2 != 0)
                    || ((Level.getTileType((((int) (newY / TILE_SCALE)) + 1), ((int) (newX / TILE_SCALE) + 2)).ordinal()) % 2 != 0)))
                newX = x;
        }
        if (vector == Vector.TOP) {
            if (((Level.getTileType((int) (newY / TILE_SCALE), (int) (newX / TILE_SCALE))).ordinal() % 2 != 0)
                    || ((Level.getTileType(((int) (newY / TILE_SCALE)), (int) (newX / TILE_SCALE) + 1).ordinal()) % 2 != 0))
                newY = y;
        } else if (vector == Vector.DOWN) {
            if (((Level.getTileType((int) (newY / TILE_SCALE) + 2, (int) (newX / TILE_SCALE))).ordinal() % 2 != 0)
                    || ((Level.getTileType(((int) (newY / TILE_SCALE)) + 2, (int) (newX / TILE_SCALE) + 1).ordinal()) % 2 != 0))
                newY = y;
        }


        x = newX;
        y = newY;

        if (playerBullet.isExist()) playerBullet.update(input);
    }

    public void IDed() {


        try {
            EnemyManager.stop();
            Thread.sleep(100);
            game.running = false;
            missionFailedAudio.sound();
            graphics.drawImage(missionFailedPicture, 90, 160, null);
            Display.swapBuffers();
            Display.swapBuffers();
            Thread.sleep(7700);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("You was killed");
        game.stop();
    }

    private static boolean finishFlag = false;
    public static void headquartersDestroy() {
        if (!finishFlag) {
            finishFlag = true;
            try {
                EnemyManager.stop();
                Thread.sleep(100);
                game.running = false;

                explosionSound.sound();
                Thread.sleep(5237);
                int height = 21, weight = 35;
                for(int i = 0; i < 25; i++) {
                    graphics.drawImage(explosionImage, 15*TILE_SCALE - weight/2, 648 - height, weight, height, null);
                    Display.swapBuffers();
                    Display.swapBuffers();
                    height += 14;
                    weight += 16;
                    Thread.sleep(5);
                }
                Thread.sleep(500);
                graphics.drawImage(gameOverPicture, 130, 16, null);
                Display.swapBuffers();
                Display.swapBuffers();
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.stop();
        }
    }

    public void render() {

        spriteMap.get(heading).render(x, y);
        playerBullet.render();
        animationDestruction.render();
    }
}