package com.llucrosell.world;

import com.llucrosell.GamePanel;

import java.awt.*;
import java.util.ArrayList;


public class World {
    public static ArrayList<Chunk> chunks;

    public World(ArrayList<Chunk> chunks) {
        World.chunks = chunks;
    }

    /**
     * Used for searching a specific chunk during the map loading.
     * @param x The position 'x' of the chunk.
     * @param y The position 'y' of the chunk.
     * @param chunks An array of chunks loaded from the JSON file.
     * @return The chunk that are looking for, or return null if not.
     */
    public static Chunk getChunkAt(int x, int y, ArrayList<Chunk> chunks) {
        float worldX = (x * 3f) / GamePanel.TILE_SIZE;
        float worldY = (y * 3f) / GamePanel.TILE_SIZE;
        for(Chunk chunk : chunks) {
            if(worldX >= chunk.getX() && worldX < (chunk.getX() + chunk.getWidth()) && worldY >= chunk.getY() && worldY < (chunk.getY() + chunk.getHeight())) {
                return chunk;
            }
        }
        return null;
    }

    /**
     * Used for searching a specific chunk during the normal flow of the game.
     * @param chunkPosition Point that specifies the position 'x,y' of a chunk.
     * @return The chunk that are looking for, or return null if not.
     */
    public static Chunk getChunkAt(Point chunkPosition) {
        for(Chunk chunk : chunks) {
            if(chunk.getPosition().equals(chunkPosition)) {
                return chunk;
            }
        }
        return null;
    }
}
