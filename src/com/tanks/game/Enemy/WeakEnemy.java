package com.tanks.game.Enemy;

import com.tanks.IO.Input;
import com.tanks.game.Player;
import com.tanks.game.level.Level;
import com.tanks.game.level.TileType;
import com.tanks.graphics.Sprite;
import com.tanks.graphics.SpriteSheet;
import com.tanks.graphics.TextureAtlas;
import com.tanks.game.Bullets.EnemyBullet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class WeakEnemy extends OvarallEnemy {


    private WeakEnemy.Heading heading;
    private Map<WeakEnemy.Heading, Sprite> spriteMap;

    private enum Heading {
        NORTH_F(8 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST_F(10 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH_F(12 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST_F(14 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),

        NORTH_S(9 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST_S(11 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH_S(13 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST_S(15 * SPRITE_SCALE, 4 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

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

    WeakEnemy(float x, float y, float speed, Player player, TextureAtlas atlas, Graphics2D graphics) {
        super( x, y, speed);
        enemyBullet = new EnemyBullet(0, 0, atlas, player, graphics);
        heading = Heading.SOUTH_F;

        spriteMap = new HashMap<>();

        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, graphics);
            spriteMap.put(h, sprite);
        }
    }

    private int animationCount = 0;
    private float newX, newY;
    @Override
    public void update(Input input) {

        newX = x;
        newY = y;
        if (enemyBullet.isExist)
            enemyBullet.update(input);    //It'S A BULLET TIME!!!
        else {
            if (rnd.nextInt(32) == 1)
                enemyBullet.start(x, y, vector);
        }

        switch (vector) {
            case DOWN:

                if (animationCount++ == SPEED_OF_ANIMATION) {
                    if ((heading == Heading.SOUTH_F)) heading = Heading.SOUTH_S;
                    else heading = Heading.SOUTH_F;
                    animationCount = 0;
                }

                newY += speed;

                if ((x % TILE_SCALE == 0) && (y % TILE_SCALE == 0)) {
                    if (((Level.getTileType((int) (newY / TILE_SCALE) + 2, (int) (newX / TILE_SCALE))).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) (newY / TILE_SCALE) + 2, (int) (newX / TILE_SCALE) + 1).ordinal()) % 2 != 0)) {
                        newEvents(4);
                        return;
                    } else if (newEvents(3)) return;

                }

                if ((newY + HALF_TILE) % TILE_SCALE == 0) {
                    Level.update((int) (x / TILE_SCALE), (int) (y / TILE_SCALE), TileType.EMPTY);
                    Level.update((int) (x / TILE_SCALE), (int) (y / TILE_SCALE) + 1, TileType.SHADOW);
                }


                break;
            case TOP:

                if (animationCount++ == SPEED_OF_ANIMATION) {
                    if ((heading == Heading.NORTH_F)) heading = Heading.NORTH_S;
                    else heading = Heading.NORTH_F;
                    animationCount = 0;
                }

                newY -= speed;

                if ((x % TILE_SCALE == 0) && (y % TILE_SCALE == 0)) {
                    if (((Level.getTileType((int) (newY / TILE_SCALE), (int) (newX / TILE_SCALE))).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) (newY / TILE_SCALE), (int) (newX / TILE_SCALE) + 1).ordinal()) % 2 != 0)) {


                        newEvents(4);
                        return;
                    } else if (newEvents(3)) return;


                }
                if ((newY + HALF_TILE) % TILE_SCALE == 0) {
                    Level.update((int) (x / TILE_SCALE), (int) (y / TILE_SCALE) + 1, TileType.EMPTY);
                    Level.update((int) (x / TILE_SCALE), (int) (y / TILE_SCALE), TileType.SHADOW);
                }
                break;
            case LEFT:

                if (animationCount++ == SPEED_OF_ANIMATION) {
                    if ((heading == Heading.WEST_F)) heading = Heading.WEST_S;
                    else heading = Heading.WEST_F;
                    animationCount = 0;
                }

                newX -= speed;

                if ((x % TILE_SCALE == 0) && (y % TILE_SCALE == 0)) {
                    if (((Level.getTileType((int) (newY / TILE_SCALE), (int) (newX / TILE_SCALE))).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) (newY / TILE_SCALE) + 1, (int) (newX / TILE_SCALE)).ordinal()) % 2 != 0)) {

                        newEvents(4);
                        return;
                    } else if (newEvents(3)) return;

                }
                if ((newX + HALF_TILE) % TILE_SCALE == 0) {
                    Level.update((int) (x / TILE_SCALE) + 1, (int) (y / TILE_SCALE), TileType.EMPTY);
                    Level.update((int) (x / TILE_SCALE), (int) (y / TILE_SCALE), TileType.SHADOW);
                }
                break;
            case RIGHT:

                if (animationCount++ == SPEED_OF_ANIMATION) {
                    if ((heading == Heading.EAST_F)) heading = Heading.EAST_S;
                    else heading = Heading.EAST_F;
                    animationCount = 0;
                }

                newX += speed;

                if ((x % TILE_SCALE == 0) && (y % TILE_SCALE == 0)) {
                    if (((Level.getTileType((int) (newY / TILE_SCALE), (int) (newX / TILE_SCALE) + 2)).ordinal() % 2 != 0)
                            || ((Level.getTileType((int) (newY / TILE_SCALE) + 1, (int) (newX / TILE_SCALE) + 2).ordinal()) % 2 != 0)) {

                        newEvents(4);
                        return;
                    } else if (newEvents(3)) return;

                }
                if ((newX + HALF_TILE) % TILE_SCALE == 0) {
                    Level.update((int) (x / TILE_SCALE), (int) (y / TILE_SCALE), TileType.EMPTY);
                    Level.update((int) (x / TILE_SCALE) + 1, (int) (y / TILE_SCALE), TileType.SHADOW);
                }
                break;
            default:
                System.out.println("Error");
                break;
        }

        y = newY;
        x = newX;
    }

    public void render() {
        spriteMap.get(heading).render(x, y);
        enemyBullet.render();
    }
}
