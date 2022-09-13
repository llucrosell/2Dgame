package com.llucrosell.entity;

import com.llucrosell.GamePanel;
import com.llucrosell.util.KeyHandler;

import java.awt.*;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        speed = 4;
    }

    @Override
    public void update() {
        if(keyH.up) {
            worldY -= speed;
        }
        if(keyH.down) {
            worldY += speed;
        }
        if(keyH.left) {
            worldX -= speed;
        }
        if(keyH.right) {
            worldX += speed;
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.fillRect(worldX, worldY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }
}
