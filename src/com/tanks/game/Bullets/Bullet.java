package com.tanks.game.Bullets;

import com.tanks.animations.destruction.AnimationBulletDestruction;
import com.tanks.game.Entity;
import com.tanks.graphics.Sprite;
import com.tanks.graphics.SpriteSheet;
import com.tanks.graphics.TextureAtlas;
import com.tanks.utils.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

abstract class Bullet extends Entity {

    static final int SPRITE_SCALE = 13;

    Thread newThread;
    public boolean isExist;
    private Heading heading;
    private Map<Heading, Sprite> spriteMap;
    AnimationBulletDestruction animationBulletDestruction;

    Bullet(float x, float y, float speed, Graphics2D graphics, TextureAtlas atlas) {
        super(x, y, speed);
        isExist = false;

        spriteMap = new HashMap<>();
        animationBulletDestruction = new AnimationBulletDestruction( graphics );

        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, graphics);
            spriteMap.put(h, sprite);
        }
    }

    enum Heading {
        NORTH(967, 305, TILE_SCALE, SPRITE_SCALE),
        WEST(989, 304, TILE_SCALE, TILE_SCALE),
        SOUTH(1015, 306, TILE_SCALE, TILE_SCALE),
        EAST(1038, 304, TILE_SCALE, TILE_SCALE);

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

    public void start(float x, float y, Vector vector) {
        this.vector = vector;
        isExist = true;
        switch (this.vector) {
            case TOP:
                this.x = x + 16;
                this.y = y - 16;
                heading = Heading.NORTH;
                break;
            case RIGHT:
                this.x = x + 49;
                this.y = y + 16;
                heading = Heading.EAST;
                break;
            case DOWN:
                this.x = x + 16;
                this.y = y + 49;
                heading = Heading.SOUTH;
                break;
            case LEFT:
                this.x = x - 16;
                this.y = y + 16;
                heading = Heading.WEST;
                break;
            default:
                System.out.println("Error");
                break;
        }
    }

    public void render() {
        if (isExist) spriteMap.get(heading).render(x, y);
        animationBulletDestruction.render();
    }

}
