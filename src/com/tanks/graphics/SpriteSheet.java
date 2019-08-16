package com.tanks.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sheet;
    private int scale;
    private int spritesInWidth;

    public SpriteSheet(BufferedImage sheet, int scale) {

        this.sheet = sheet;
        this.scale = scale;
        this.spritesInWidth = sheet.getWidth() / scale;

    }

    BufferedImage getSprite(int index) {

        int x = index % spritesInWidth * scale;
        int y = index / spritesInWidth * scale;
        return sheet.getSubimage(x, y, scale, scale);

    }

}