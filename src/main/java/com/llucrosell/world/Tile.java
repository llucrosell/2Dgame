package com.llucrosell.world;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public int code;

    public Tile(int code, BufferedImage image) {
        this.code = code;
        this.image = image;
    }
}
