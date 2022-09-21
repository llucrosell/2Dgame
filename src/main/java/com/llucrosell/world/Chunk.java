package com.llucrosell.world;

import com.llucrosell.GamePanel;

import java.awt.*;
import java.util.ArrayList;


public class Chunk {
    private int width;
    private int height;
    private int x;
    private int y;
    private int[] codes;
    private ArrayList<Rectangle> collisions;

    public Chunk(int width, int height, int x, int y, int[] codes) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.codes = codes;
        collisions = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPosition() { return new Point(x / GamePanel.CHUNK_SIZE, y / GamePanel.CHUNK_SIZE); }

    public int[] getCodes() {
        return codes;
    }

    public ArrayList<Rectangle> getCollisions() {
        return collisions;
    }

    public void addCollision(Rectangle c) {
        this.collisions.add(c);
    }
}
