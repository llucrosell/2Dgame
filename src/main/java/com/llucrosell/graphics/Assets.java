package com.llucrosell.graphics;

import com.llucrosell.world.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Assets {

    private static BufferedImage[][] player_idle, player_walk;
    public static HashMap<String, Animation> playerAnimation;
    public static Tile[] tiles;

    private static String path;

    public static int PLAYER_UP = 3;
    public static int PLAYER_DOWN = 0;
    public static int PLAYER_LEFT = 1;
    public static int PLAYER_RIGHT = 2;


    public static void init() {
        System.out.print("[ASSETS]: Loading player sprites ... ");
        loadPlayerSprites();
        if(compilePlayerSprites(playerAnimation)) {
            System.out.println("DONE");
        } else {
            System.out.println("ERROR");
        }

        System.out.print("[ASSETS]: Loading world tiles ... ");
        loadTextures();
        if(compileTextureSprites(tiles)) {
            System.out.println("DONE");
        } else {
            System.out.println("ERROR");
        }
    }

    private static boolean compilePlayerSprites(HashMap<String, Animation> map) {
        return (
                        map.get("idle_up") != null &&
                        map.get("idle_down") != null &&
                        map.get("idle_left") != null &&
                        map.get("idle_right") != null &&

                        map.get("walk_up") != null &&
                        map.get("walk_down") != null &&
                        map.get("walk_left") != null &&
                        map.get("walk_right") != null
                );
    }

    private static boolean compileTextureSprites(Tile[] tiles) {
        int counter = 0;
        for(Tile tile : tiles) {
            if(tile.image != null) {
                counter++;
            }
        }
        return counter == tiles.length;
    }

    private static BufferedImage loadSheet(String path, String name) {
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

    private static void loadPlayerSprites() {
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

    private static void loadPlayerSpritesFromSheet(BufferedImage[][] array, String name) {
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

    private static void loadTextures() {
        path = "src/main/resources/textures/";
        tiles = new Tile[36];
        loadTexturesFromSheet(tiles,16,"world.png");
    }

    private static void loadTexturesFromSheet(Tile[] array, int pxSize, String name) {
        BufferedImage sheet = loadSheet(path, name);
        if(sheet != null) {
            int xSize = sheet.getWidth() / pxSize;
            int ySize = sheet.getHeight() / pxSize;
            int counter = 0;
            for(int row = 0; row < ySize; row++) {
                for(int col = 0; col < xSize; col++) {
                    array[col + row * ySize] = new Tile(
                            counter,
                            sheet.getSubimage(col * pxSize, row * pxSize, pxSize, pxSize)
                    );
                    counter++;
                }
            }
        } else {
            System.out.println("[ERROR]: Textures sheet is null!");
        }
    }
}
