package com.llucrosell.entity;

import java.awt.*;

public abstract class Entity {
    public int worldX, worldY;
    public int speed;

    public abstract void update();
    public abstract void render(Graphics2D g2);
}
