package com.tanks.game.level;

import com.tanks.graphics.TextureAtlas;
import com.tanks.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Level {

    private static final int TILE_SCALE = 24;

    private static int[][] tileMap;
    private static Map<TileType, Tile> tiles;
    private Graphics2D graphics;
    private BufferedImage hpAndLvlImage;

    public Level(TextureAtlas atlas, Graphics2D graphics) {
        this.graphics = graphics;

        tiles = new HashMap<>();
        tiles.put(TileType.BRICK, new Tile(atlas.cut(32 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.BRICK));
        tiles.put(TileType.METAL, new Tile(atlas.cut(32 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.METAL));
        tiles.put(TileType.GRASS, new Tile(atlas.cut(33 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.GRASS));
        tiles.put(TileType.EMPTY, new Tile(atlas.cut(35 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.EMPTY));
        tiles.put(TileType.VOID, new Tile(atlas.cut(48 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.VOID));
        tiles.put(TileType.SHADOW, new Tile(atlas.cut(35 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.SHADOW));
        tiles.put(TileType.NUM_ENEMIES, new Tile(atlas.cut(40 * TILE_SCALE, 24 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.NUM_ENEMIES));
        tiles.put(TileType.HEADQUARTERS_SHADOW, new Tile(atlas.cut(42 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE, TILE_SCALE), TileType.HEADQUARTERS_SHADOW));
        tiles.put(TileType.HEADQUARTERS, new Tile(atlas.cut(38 * TILE_SCALE, 4 * TILE_SCALE, 2 * TILE_SCALE, 2 * TILE_SCALE), TileType.HEADQUARTERS));

        try {
            hpAndLvlImage = ImageIO.read(new File("res/flagAndLevels.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tileMap = Utils.levelParser("res/level.lvl");
    }

    public static synchronized void update(int x, int y, TileType tileType) {
        if ((tileMap[y][x] == TileType.GRASS.numeric()) || (tileMap[y + 1][x] == TileType.GRASS.numeric())
                || (tileMap[y][x + 1] == TileType.GRASS.numeric()) || (tileMap[y + 1][x + 1] == TileType.GRASS.numeric()))
            return;
        tileMap[y][x] = tileType.numeric();
        tileMap[y + 1][x] = tileType.numeric();
        tileMap[y][x + 1] = tileType.numeric();
        tileMap[y + 1][x + 1] = tileType.numeric();
    }

    public static void bulletUpdate(int x, int y) {
        tileMap[x][y] = TileType.EMPTY.numeric();
    }

    public static void numEnemiesUpdate(int x, int y) {
        tileMap[x][y] = TileType.VOID.numeric();
    }

    public static void secondWaveUpdate() {
        for (int k = 0; k < 8; k++) {
            tileMap[11 - k][29] = TileType.NUM_ENEMIES.numeric();
            tileMap[11 - k][30] = TileType.NUM_ENEMIES.numeric();
        }
    }

    private int i, j;

    public synchronized void render() {
        for (i = 0; i < tileMap.length; i++) {
            for (j = 0; j < tileMap[i].length; j++) {
                if ((getTileType(i, j) == TileType.EMPTY) || (getTileType(i, j) == TileType.SHADOW)) continue;
                tiles.get(getTileType(i, j)).render(graphics, j * TILE_SCALE, i * TILE_SCALE);
            }
        }
        tiles.get(TileType.HEADQUARTERS).render(graphics, 14 * TILE_SCALE, 25 * TILE_SCALE);
        graphics.drawImage(hpAndLvlImage, 672, 360, null);
    }

    public static TileType getTileType(int x, int y) {

        return TileType.fromNumeric(tileMap[x][y]);

    }
}
