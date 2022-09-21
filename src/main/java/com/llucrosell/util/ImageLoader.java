package com.llucrosell.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        BufferedImage img = null;
        File f = new File(path);
        try{
            img = ImageIO.read(f);
        } catch(IOException e) {
            System.out.println("[ERROR]: Cannot load the image " + path);
            e.printStackTrace();
        }
        return img;
    }
}
