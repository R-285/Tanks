package com.tanks.game.Bullets;

import com.tanks.IO.Input;
import com.tanks.game.Player;
import com.tanks.game.level.Level;
import com.tanks.game.level.TileType;
import com.tanks.graphics.TextureAtlas;

import java.awt.*;

public class EnemyBullet extends Bullet {

    private static final int    PLAYER_SCALE = 48;
    private static final int    SPEED = 8;
    private Player              player;

    public EnemyBullet(float x, float y, TextureAtlas atlas, Player player, Graphics2D graphics) {
        super(x, y, SPEED, graphics, atlas);

        this.player = player;

        newThread = new Thread(animationBulletDestruction, "It's Time Die, Bullet");
    }

    private boolean isIntersection(int x, int y, int xp, int yp) {
        return (Math.abs(x - xp) < SPRITE_SCALE) && (Math.abs(y - yp) < SPRITE_SCALE);
    }


    @Override
    public void update(Input input) {
        if (isExist) {

            //Player kill
            if ((Math.abs(x + (float) SPRITE_SCALE / 2 - (player.getX() + (float) PLAYER_SCALE / 2)) < (float) (SPRITE_SCALE + PLAYER_SCALE) / 2) &&
                    (Math.abs(y + (float) SPRITE_SCALE / 2 - (player.getY() + (float) PLAYER_SCALE / 2)) < (float) (SPRITE_SCALE + PLAYER_SCALE) / 2)) {
                isExist = false;
                animationBulletDestruction.setXY(x, y);
                newThread = new Thread(animationBulletDestruction);
                newThread.start();
                player.IDed();

            }

            else {
                switch (vector) {
                    case TOP:
                        if (((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE))).ordinal() % 2 != 0)
                                || ((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE) + 1).ordinal()) % 2 != 0)) {

                            isExist = false;
                            animationBulletDestruction.setXY(x, y);
                            newThread = new Thread(animationBulletDestruction);
                            newThread.start();

                            if ((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE))) == TileType.BRICK)
                                Level.bulletUpdate((int) (y / TILE_SCALE), (int) (x / TILE_SCALE));

                            if ((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE) + 1) == TileType.BRICK))
                                Level.bulletUpdate((int) (y / TILE_SCALE), (int) (x / TILE_SCALE) + 1);

                        }
                        y -= SPEED;
                        break;
                    case RIGHT:
                        if (((Level.getTileType((int) (y / TILE_SCALE), (int) ((x + SPRITE_SCALE) / TILE_SCALE))).ordinal() % 2 != 0)
                                || ((Level.getTileType((int) (y / TILE_SCALE) + 1, (int) ((x + SPRITE_SCALE) / TILE_SCALE)).ordinal()) % 2 != 0)) {
                            isExist = false;
                            animationBulletDestruction.setXY(x, y);
                            newThread = new Thread(animationBulletDestruction);
                            newThread.start();

                            if ((Level.getTileType((int) (y / TILE_SCALE), (int) ((x + SPRITE_SCALE) / TILE_SCALE))) == TileType.BRICK)
                                Level.bulletUpdate((int) (y / TILE_SCALE), (int) ((x + SPRITE_SCALE) / TILE_SCALE));
                            else  if ((Level.getTileType((int) (y / TILE_SCALE), (int) ((x + SPRITE_SCALE) / TILE_SCALE))) == TileType.HEADQUARTERS_SHADOW)
                                player.headquartersDestroy();
                            if ((Level.getTileType((int) (y / TILE_SCALE) + 1, (int) ((x + SPRITE_SCALE) / TILE_SCALE)) == TileType.BRICK))
                                Level.bulletUpdate((int) (y / TILE_SCALE) + 1, (int) ((x + SPRITE_SCALE) / TILE_SCALE));
                            else if ((Level.getTileType((int) (y / TILE_SCALE) + 1, (int) ((x + SPRITE_SCALE) / TILE_SCALE)) == TileType.HEADQUARTERS_SHADOW))
                                player.headquartersDestroy();
                        }
                        x += SPEED;
                        break;
                    case DOWN:
                        if (((Level.getTileType((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE))).ordinal() % 2 != 0)
                                || ((Level.getTileType((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE) + 1).ordinal()) % 2 != 0)) {
                            isExist = false;
                            animationBulletDestruction.setXY(x, y);
                            newThread = new Thread(animationBulletDestruction);
                            newThread.start();
                            if ((Level.getTileType((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE))) == TileType.BRICK)
                                Level.bulletUpdate((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE));
                            else if ((Level.getTileType((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE))) == TileType.HEADQUARTERS_SHADOW)
                                player.headquartersDestroy();
                            if ((Level.getTileType((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE) + 1)) == TileType.BRICK)
                                Level.bulletUpdate((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE) + 1);
                            else if ((Level.getTileType((int) ((y + SPRITE_SCALE) / TILE_SCALE), (int) (x / TILE_SCALE) + 1)) == TileType.HEADQUARTERS_SHADOW)
                                player.headquartersDestroy();
                        }
                        y += SPEED;
                        break;
                    case LEFT:
                        if (((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE))).ordinal() % 2 != 0)
                                || ((Level.getTileType((int) (y / TILE_SCALE) + 1, (int) (x / TILE_SCALE)).ordinal()) % 2 != 0)) {
                            isExist = false;
                            animationBulletDestruction.setXY(x, y);
                            newThread = new Thread(animationBulletDestruction);
                            newThread.start();

                            if ((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE))) == TileType.BRICK)
                                Level.bulletUpdate((int) (y / TILE_SCALE), (int) (x / TILE_SCALE));
                            else  if ((Level.getTileType((int) (y / TILE_SCALE), (int) (x / TILE_SCALE))) == TileType.HEADQUARTERS_SHADOW)
                                player.headquartersDestroy();

                            if ((Level.getTileType((int) (y / TILE_SCALE) + 1, (int) (x / TILE_SCALE)) == TileType.BRICK))
                                Level.bulletUpdate((int) (y / TILE_SCALE) + 1, (int) (x / TILE_SCALE));
                            else if ((Level.getTileType((int) (y / TILE_SCALE) + 1, (int) (x / TILE_SCALE)) == TileType.HEADQUARTERS_SHADOW))
                                player.headquartersDestroy();
                        }
                        x -= SPEED;
                        break;
                    default:
                        System.out.println("Error");
                }
            }
            if (player.playerBullet.isExist()) {
                if (isIntersection((int) x, (int) y, (int) player.playerBullet.getX(), (int) player.playerBullet.getY())) {
                    isExist = false;
                    animationBulletDestruction.setXY(x, y);
                    newThread = new Thread(animationBulletDestruction);
                    newThread.start();
                    player.playerBullet.setExist();
                }
            }
        }
    }
}