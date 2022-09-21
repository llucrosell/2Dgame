package com.llucrosell.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteSheetGenerator {

    public static void main(String[] args) {

        // Can replace with args input
        final String inputPath =  "assets/textures/world";
        final String outputPath = "src/main/resources/textures";
        final String outputFile = "world.png";
        final int margin = 0;
        final int cols = 6;
        final int numSprites = 36;

        File imageFolder = new File(inputPath);
        File[] files = imageFolder.listFiles();

        // Read images
        ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();

        for (File f : files)
        {
            if (f.isFile())
            {
                String fileName = f.getName();
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1
                );

                if (ext.equals("png")) {
                    try {
                        imageList.add(ImageIO.read(f));
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error reading file '" + f + "'.");
                    }
                }
                else {
                    System.out.println("[ERROR]: Unknown image extension.");
                }
            }
        }

        // Find max width and total height
        int totalWidth = 0;
        int totalHeight = 0;
        int counter = 1;

        for (BufferedImage image : imageList)
        {
            totalWidth += image.getWidth() + margin;
            totalHeight += image.getHeight() + margin;

            if(counter % numSprites == 0) {
                totalWidth /= cols;
                totalHeight /= cols;
            }

            counter++;
        }

        System.out.format("Number of images: %s, total height: %spx, width: %spx%n",
                imageList.size(), totalHeight, totalWidth);


        // Create the actual sprite
        BufferedImage sprite = new BufferedImage(totalWidth, totalHeight,
                BufferedImage.TYPE_INT_ARGB);

        int currentY = 0;
        int currentX = 0;
        counter = 0;
        Graphics g = sprite.getGraphics();
        for (BufferedImage image : imageList)
        {
            if(counter % cols == 0 && counter != 0) {
                currentY += image.getHeight() + margin;
                currentX = 0;
            }
            g.drawImage(image, currentX, currentY, null);
            currentX += image.getWidth() + margin;
            counter++;
        }

        System.out.format("Writing sprite in  %s%n", outputPath + "/" + outputFile);
        try {
            ImageIO.write(sprite, "png", new File(outputPath + "/" + outputFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing file '" + outputPath + "/" + outputFile + "' .");
        }
    }
}
