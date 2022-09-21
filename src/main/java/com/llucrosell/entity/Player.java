package com.llucrosell.entity;

import com.llucrosell.GamePanel;
import com.llucrosell.graphics.Animation;
import com.llucrosell.graphics.Assets;
import com.llucrosell.util.AABB;
import com.llucrosell.util.KeyHandler;
import com.llucrosell.util.MapLoader;
import com.llucrosell.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends Entity{

    private final GamePanel gp;
    private BufferedImage currentFrame;
    public final int SCREEN_X = GamePanel.SCREEN_WIDTH / 2 - GamePanel.PLAYER_SIZE / 2;
    public final int SCREEN_Y = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.PLAYER_SIZE / 2;
    private int chunkX, chunkY;
    private final Random random;


    public Player(GamePanel gp) {
        this.gp = gp;
        random = new Random();

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = MapLoader.getSpawn().x * 3 + random.nextInt(GamePanel.TILE_SIZE);
        y = MapLoader.getSpawn().y * 3 + random.nextInt(GamePanel.TILE_SIZE);
        dx = 0;
        dy = 0;
        maxSpeed = 2.5f;
        acc = 0.4f;
        deacc = 0.2f;
        hitbox = new Rectangle(
                SCREEN_X + GamePanel.PLAYER_SIZE / 2 - GamePanel.TILE_SIZE / 2,
                SCREEN_Y + GamePanel.PLAYER_SIZE / 2 - GamePanel.TILE_SIZE / 2,
                GamePanel.TILE_SIZE,
                GamePanel.TILE_SIZE
        );
        direction = Assets.PLAYER_DOWN;
        currentFrame = Assets.playerAnimation.get("idle_down").getCurrentFrame();
    }

    public Point playerLocation() { return new Point(x, y); }
    public Point playerWorldLocation() { return new Point(worldX, worldY); }
    public Point playerChunkLocation() { return new Point(chunkX, chunkY); }


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
        x += dx;
        y += dy;
        worldX = x / GamePanel.TILE_SIZE;
        worldY = y / GamePanel.TILE_SIZE;
        chunkX = worldX / GamePanel.CHUNK_SIZE;
        chunkY = worldY / GamePanel.CHUNK_SIZE;
        if(x < 0) chunkX--;
        if(y < 0) chunkY--;

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

        AABB.getCollision(this, World.getChunkAt(new Point(chunkX, chunkY)).getCollisions());

        tick();
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(
                currentFrame,
                SCREEN_X,
                SCREEN_Y,
                GamePanel.PLAYER_SIZE,
                GamePanel.PLAYER_SIZE,
                null
        );
        if(KeyHandler.debug) {
            g2.setColor(new Color(200, 10, 10));
            g2.setStroke(new BasicStroke());
            g2.drawRect(
                    hitbox.x,
                    hitbox.y,
                    hitbox.width,
                    hitbox.height
            );
        }
    }
}
