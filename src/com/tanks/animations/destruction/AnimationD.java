package com.tanks.animations.destruction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationD {
    private BufferedImage image;
    private Graphics2D graphics;
    AnimationD(BufferedImage image, Graphics2D graphics) {
        this.image = image;
        this.graphics = graphics;
    }

    public void render(int x, int y) {
        graphics.drawImage(image, x, y, null);
    }
}
