package com.llucrosell.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Assets {

    private BufferedImage[][] player_idle, player_walk;
    public static HashMap<String, Animation> playerAnimation;

    private String path;

    public static int PLAYER_UP = 3;
    public static int PLAYER_DOWN = 0;
    public static int PLAYER_LEFT = 1;
    public static int PLAYER_RIGHT = 2;


    public Assets() {
        System.out.print("[ASSETS]: Loading player sprites... ");

        loadPlayerSprites();

        if(playerAnimation != null) {
            System.out.println("DONE");
        } else {
            System.out.println("ERROR");
        }
    }

    private void loadPlayerSprites() {
        path = "src/main/resources/entity/player/";
        playerAnimation = new HashMap<>();

        player_idle = new BufferedImage[4][4];
        loadPlayerSpritesFromSheet(player_idle, "player_idle.png");
        playerAnimation.put("idle_up"   , new Animation(200, player_idle[PLAYER_UP]));
        playerAnimation.put("idle_down" , new Animation(200, player_idle[PLAYER_DOWN]));
        playerAnimation.put("idle_left" , new Animation(200, player_idle[PLAYER_LEFT]));
        playerAnimation.put("idle_right", new Animation(200, player_idle[PLAYER_RIGHT]));

        player_walk = new BufferedImage[4][4];
        loadPlayerSpritesFromSheet(player_walk, "player_walk.png");
        playerAnimation.put("walk_up"   , new Animation(200, player_walk[PLAYER_UP]));
        playerAnimation.put("walk_down" , new Animation(200, player_walk[PLAYER_DOWN]));
        playerAnimation.put("walk_left" , new Animation(200, player_walk[PLAYER_LEFT]));
        playerAnimation.put("walk_right", new Animation(200, player_walk[PLAYER_RIGHT]));
    }

    private BufferedImage loadSheet(String path, String name) {
        BufferedImage img = null;
        File f = new File(path + name);
        try{
            img = ImageIO.read(f);
        } catch(IOException e) {
            System.out.println("[ERROR]: Cannot read the file!");
            e.printStackTrace();
        }
        return img;
    }

    private void loadPlayerSpritesFromSheet(BufferedImage[][] array, String name) {
        BufferedImage sheet = loadSheet(path, name);
        if(sheet != null) {
            int size = array.length;
            int width = sheet.getWidth() / size;
            int height = sheet.getHeight() / size;
            for(int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    array[col][row] = sheet.getSubimage(row * width, col * height, width, height);
                }
            }
        } else {
            System.out.println("[ERROR]: Sprite sheet is null!");
        }
    }
}
