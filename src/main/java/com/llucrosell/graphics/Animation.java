package com.llucrosell.graphics;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;
    private final long speed;
    private int index;
    private long timer, lastTime;
    private boolean hasFinished = false;

    public Animation(long speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer > speed) {
            index++;
            timer = 0;
            if(index >= frames.length) {
                index = 0;
                hasFinished = true;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
    public void resetAnimation() {
        hasFinished = false;
        index = 0;
        timer = 0;
    }

    public long getSpeed() { return speed; }
    public int getIndex() { return index; }
    public int getFramesLength() { return frames.length; }
    public boolean getFlag() { return hasFinished; }
}
