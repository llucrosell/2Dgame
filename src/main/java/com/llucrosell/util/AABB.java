package com.llucrosell.util;


import com.llucrosell.GamePanel;
import com.llucrosell.entity.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * Axis-Aligned Bounding Box, or more commonly known as Collisions Class.
 */
public class AABB {
    public static void getCollision(Player p, ArrayList<Rectangle> collisions) {
        for (Rectangle r : collisions) {
            int worldX = r.x * GamePanel.TILE_SCALE;
            int worldY = r.y * GamePanel.TILE_SCALE;
            int worldW = r.width * GamePanel.TILE_SCALE;
            int worldH = r.height * GamePanel.TILE_SCALE;
            int screenX = worldX - p.playerLocation().x + p.SCREEN_X + GamePanel.TILE_SIZE * 2;
            int screenY = worldY - p.playerLocation().y + p.SCREEN_Y + GamePanel.TILE_SIZE * 2;
            /*
            return (p.getHitbox().x < screenX + worldW &&
                    p.getHitbox().x + p.getHitbox().width > screenX &&
                    p.getHitbox().y < screenY + worldH &&
                    p.getHitbox().y + p.getHitbox().height > screenY);
        } */
            if(p.getHitbox().intersects(new Rectangle(screenX, screenY, worldW, worldH))){
                System.out.println("Collision at " + p.playerWorldLocation().x + "," + p.playerWorldLocation().y);
            }
        }
    }
}
