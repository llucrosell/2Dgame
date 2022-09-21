package com.llucrosell.util;

import com.llucrosell.world.Chunk;
import com.llucrosell.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public static int tileMapWidth;
    public static int tileMapHeight;
    public static int tileWidth;
    public static int tileHeight;
    private static Point spawn;
    private final ArrayList<Chunk> chunkArray;


    public MapLoader(String path, String name) {
        System.out.print("[MAP LOADER]: Reading " + name + " ...");

        String content = readTextFile(path , name);

        // Tile width and height
        JSONObject globalJSON = getObjectFromJSON(content);

        // Layers
        JSONArray layers = getArrayFromJSON(globalJSON.get("layers").toString());
        chunkArray = new ArrayList<>();

        // Tile width and height
        MapLoader.tileWidth = getIntegerFromJSON(globalJSON, "tilewidth");
        MapLoader.tileHeight = getIntegerFromJSON(globalJSON, "tileheight");
        // Init layers
        for(int i = 0; i < layers.size(); i++) {
            JSONObject layerData = getObjectFromJSON(layers.get(i).toString());
            switch(i) {
                case 0 -> {     // background layer
                    int width = getIntegerFromJSON(layerData, "width");         // Tile Map Width
                    int height = getIntegerFromJSON(layerData, "height");       // Tile Map Height

                    JSONArray chunks = getArrayFromJSON(layerData.get("chunks").toString());
                    for(int k = 0; k < chunks.size(); k++) {
                        JSONObject chunkData = getObjectFromJSON(chunks.get(k).toString());
                        int chunk_width = getIntegerFromJSON(chunkData, "width");
                        int chunk_height = getIntegerFromJSON(chunkData, "height");
                        int chunk_x = getIntegerFromJSON(chunkData, "x");
                        int chunk_y = getIntegerFromJSON(chunkData, "y");
                        int[] chunkCodes = new int[chunk_width * chunk_height];
                        JSONArray sprites = getArrayFromJSON(chunkData.get("data").toString());
                        for(int j = 0; j < sprites.size(); j++) {
                            int spriteCode = Integer.parseInt(sprites.get(j).toString());
                            if(spriteCode > 0) {
                                chunkCodes[j] = spriteCode - 1;
                            }
                        }
                        chunkArray.add(new Chunk(chunk_width, chunk_height, chunk_x, chunk_y, chunkCodes));
                    }
                    tileMapWidth = width;
                    tileMapHeight = height;
                }
                case 1 -> {     // collisions layer
                    JSONArray objects = getArrayFromJSON(layerData.get("objects").toString());
                    for(int j = 0; j < objects.size(); j++) {
                        JSONObject collisionData = getObjectFromJSON(objects.get(j).toString());
                        float width = getFloatFromJSON(collisionData, "width");
                        float height = getFloatFromJSON(collisionData, "height");
                        float x = getFloatFromJSON(collisionData, "x");
                        float y = getFloatFromJSON(collisionData, "y");
                        World.getChunkAt((int) x, (int) y, chunkArray).addCollision(new Rectangle((int) x, (int) y, (int) width, (int) height));
                    }
                }
                case 2 -> {     // spawn layer
                    JSONArray objects = getArrayFromJSON(layerData.get("objects").toString());
                    JSONObject spawnData = getObjectFromJSON(objects.get(0).toString());
                    float x = getFloatFromJSON(spawnData, "x");
                    float y = getFloatFromJSON(spawnData, "y");
                    spawn = new Point((int) x, (int) y);
                }
            }
        }
        System.out.println("DONE");
    }

    public ArrayList<Chunk> getChunksFromJSON() {
        return chunkArray;
    }

    public static Point getSpawn() {
        return spawn;
    }

    public String readTextFile(String path, String name) {
        String data = "";
        try{
            File f = new File(path + name);
            Scanner reader = new Scanner(f);
            while(reader.hasNextLine()) {
                data += reader.nextLine() + "\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found in route " + path + name);
            e.printStackTrace();
        }
        return data;
    }

    private JSONObject getObjectFromJSON(final String codeJSON) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try{
            Object retrieved = parser.parse(codeJSON);
            obj = (JSONObject) retrieved;
        } catch(ParseException e) {
            System.out.println("Position: " + e.getPosition());
            System.out.println(e);
        }
        return obj;
    }

    private JSONArray getArrayFromJSON(final String codeJSON) {
        JSONParser parser = new JSONParser();
        JSONArray array = null;
        try{
            Object retrieved = parser.parse(codeJSON);
            array = (JSONArray) retrieved;
        } catch(ParseException e) {
            System.out.println("Position: " + e.getPosition());
            System.out.println(e);
        }
        return array;
    }

    private int getIntegerFromJSON(final JSONObject jsonObject, final String key) {
        return Integer.parseInt(jsonObject.get(key).toString());
    }

    private float getFloatFromJSON(final JSONObject jsonObject, final String key) {
        return Float.parseFloat(jsonObject.get(key).toString());
    }
}
