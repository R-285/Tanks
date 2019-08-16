package com.tanks.game.level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private TileType type;

    protected Tile(BufferedImage image, TileType type){
        this.type = type;
        this.image = image;
    }

    protected void render(Graphics2D g, int x, int y){
        g.drawImage(image, x, y, null);
    }

    protected TileType type(){
        return type;
    }

}
