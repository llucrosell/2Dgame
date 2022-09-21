package com.llucrosell.world;

import com.llucrosell.GamePanel;
import com.llucrosell.graphics.Assets;
import com.llucrosell.util.KeyHandler;

import java.awt.*;


public class TileManager {
    private final GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
    }

    private void renderChunk(Chunk chunk, Graphics2D g2) {
        int x = chunk.getX();
        int y = chunk.getY();
        int width = chunk.getWidth();

        for(int code : chunk.getCodes()) {
            int worldX = x * GamePanel.TILE_SIZE;
            int worldY = y * GamePanel.TILE_SIZE;
            int screenX = worldX - gp.getPlayer().playerLocation().x + gp.getPlayer().SCREEN_X + GamePanel.TILE_SIZE * 2;
            int screenY = worldY - gp.getPlayer().playerLocation().y + gp.getPlayer().SCREEN_Y + GamePanel.TILE_SIZE * 2;

            g2.drawImage(
                    Assets.tiles[code].image,
                    screenX,
                    screenY,
                    GamePanel.TILE_SIZE,
                    GamePanel.TILE_SIZE,
                    null
            );
            if(KeyHandler.debug) {
                g2.setStroke(new BasicStroke());
                g2.setColor(new Color(200, 200, 0));
                g2.drawRect(screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
            }

            x++;
            if(chunk.getX() < 0) {
                if(x == 0) {
                    x = chunk.getX();
                    y++;
                }
            } else {
                if(x == width) {
                    x = chunk.getX();
                    y++;
                }
            }
        }
    }

    private void renderCollisions(Chunk chunk, Graphics2D g2) {
        for(Rectangle r : chunk.getCollisions()) {
            int worldX = r.x * GamePanel.TILE_SCALE;
            int worldY = r.y * GamePanel.TILE_SCALE;
            int screenX = worldX - gp.getPlayer().playerLocation().x + gp.getPlayer().SCREEN_X + GamePanel.TILE_SIZE * 2;
            int screenY = worldY - gp.getPlayer().playerLocation().y + gp.getPlayer().SCREEN_Y + GamePanel.TILE_SIZE * 2;

            g2.setColor(Color.CYAN);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(screenX, screenY, r.width * GamePanel.TILE_SCALE, r.height * GamePanel.TILE_SCALE);
        }
    }

    public void render(Graphics2D g2) {
        if (World.chunks != null) {
            for (Chunk chunk : World.chunks) {
                renderChunk(chunk, g2);
                if(KeyHandler.debug) {
                    renderCollisions(chunk, g2);
                }
            }
        }
    }
}
