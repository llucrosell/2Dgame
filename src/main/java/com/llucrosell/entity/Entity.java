package com.llucrosell.entity;

import java.awt.*;

public abstract class Entity {
    protected int worldX, worldY;
    protected float dx, dy;
    protected int speed;
    protected int direction;
    protected float maxSpeed, acc, deacc;

    public abstract void tick();
    public abstract void update();
    public abstract void render(Graphics2D g2);
}
