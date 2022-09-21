package com.llucrosell.entity;

import java.awt.*;

public abstract class Entity {
    protected int x, y;
    protected int worldX, worldY;
    protected float dx, dy;
    protected int direction;
    protected float maxSpeed, acc, deacc;
    protected Rectangle hitbox;

    public Rectangle getHitbox() { return hitbox; }

    public abstract void tick();
    public abstract void update();
    public abstract void render(Graphics2D g2);
}
