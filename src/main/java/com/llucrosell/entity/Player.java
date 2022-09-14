package com.llucrosell.entity;

import com.llucrosell.GamePanel;
import com.llucrosell.graphics.Animation;
import com.llucrosell.graphics.Assets;
import com.llucrosell.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    private GamePanel gp;
    private BufferedImage currentFrame;



    public Player(GamePanel gp) {
        this.gp = gp;

        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;
        dx = 0;
        dy = 0;
        maxSpeed = 3f;
        acc = 1.5f;
        deacc = 0.2f;
        speed = 4;
        direction = Assets.PLAYER_DOWN;
        currentFrame = Assets.playerAnimation.get("idle_down").getCurrentFrame();
    }

    @Override
    public void tick() {
        for(Animation a : Assets.playerAnimation.values()) {
            a.tick();
        }
    }

    public void input(KeyHandler key) {
        if(key.up) {
            direction = Assets.PLAYER_UP;
            dy -= acc;
            if(dy < -maxSpeed) dy = -maxSpeed;
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) dy = 0;
            }
        }

        if(key.down) {
            direction = Assets.PLAYER_DOWN;
            dy += acc;
            if(dy > maxSpeed) dy = maxSpeed;
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) dy = 0;
            }
        }

        if(key.left) {
            direction = Assets.PLAYER_LEFT;
            dx -= acc;
            if(dx < -maxSpeed) dx = -maxSpeed;
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) dx = 0;
            }
        }

        if(key.right) {
            direction = Assets.PLAYER_RIGHT;
            dx += acc;
            if(dx > maxSpeed) dx = maxSpeed;
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) dx = 0;
            }
        }
    }

    @Override
    public void update() {
        worldX += dx;
        worldY += dy;

        if(direction == Assets.PLAYER_UP) {
            if(dx == 0 && dy == 0) {
                currentFrame = Assets.playerAnimation.get("idle_up").getCurrentFrame();
            } else {
                currentFrame = Assets.playerAnimation.get("walk_up").getCurrentFrame();
            }
        }

        if(direction == Assets.PLAYER_DOWN) {
            if(dx == 0 && dy == 0) {
                currentFrame = Assets.playerAnimation.get("idle_down").getCurrentFrame();
            } else {
                currentFrame = Assets.playerAnimation.get("walk_down").getCurrentFrame();
            }
        }

        if(direction == Assets.PLAYER_LEFT) {
            if(dx == 0 && dy == 0) {
                currentFrame = Assets.playerAnimation.get("idle_left").getCurrentFrame();
            } else {
                currentFrame = Assets.playerAnimation.get("walk_left").getCurrentFrame();
            }
        }

        if(direction == Assets.PLAYER_RIGHT) {
            if(dx == 0 && dy == 0) {
                currentFrame = Assets.playerAnimation.get("idle_right").getCurrentFrame();
            } else {
                currentFrame = Assets.playerAnimation.get("walk_right").getCurrentFrame();
            }
        }

        tick();
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(currentFrame, worldX, worldY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
}
