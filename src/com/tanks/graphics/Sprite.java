package com.tanks.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {

    private SpriteSheet sheet;
    private Graphics2D graphics;

    public Sprite(SpriteSheet sheet, Graphics2D graphics) {
        this.sheet = sheet;
        this.graphics= graphics;
    }

    public void render(float x, float y) {
        BufferedImage image = sheet.getSprite(0);

        graphics.drawImage(image, (int) x, (int) y, (image.getWidth()), (image.getHeight()), null);
    }

}