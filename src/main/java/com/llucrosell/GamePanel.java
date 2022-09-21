package com.llucrosell;

import com.llucrosell.entity.Player;
import com.llucrosell.graphics.Assets;
import com.llucrosell.util.AABB;
import com.llucrosell.world.TileManager;
import com.llucrosell.ui.UI;
import com.llucrosell.util.MapLoader;
import com.llucrosell.util.KeyHandler;
import com.llucrosell.world.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public static final int ORIGINAL_PLAYER_SIZE = 64;        // 16x16 tile
    public static final int PLAYER_SCALE = 3;
    public static final int PLAYER_SIZE = ORIGINAL_PLAYER_SIZE * PLAYER_SCALE;    // 48x48 tile
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int TILE_SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * TILE_SCALE;

    public static final int CHUNK_SIZE = 16;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    // SYSTEM
    private Thread gameThread;
    private KeyHandler keyH;
    private Player player;
    private TileManager tm;
    private UI ui;
    private MapLoader mapLoader;
    private World world;
    private AABB aabb;
    public static int oldFrameCount;

    // WORLD SETTINGS
    public final Dimension WORLD_DIMENSION;

    public GamePanel(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        mapLoader = new MapLoader("src/main/resources/map/" , "map01.json");
        WORLD_DIMENSION = new Dimension(MapLoader.tileMapWidth, MapLoader.tileMapHeight);

        init();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);

        this.setFocusable(true);

        startGameThread();
    }

    private void init() {
        world = new World(mapLoader.getChunksFromJSON());
        keyH = new KeyHandler(this);
        tm = new TileManager(this);
        Assets.init();
        player = new Player(this);
        ui = new UI(this);
        aabb = new AABB();
    }

    private void startGameThread() {
        gameThread = new Thread(this, "2D Game");
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void input(KeyHandler key) {
        player.input(key);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);

        tm.render(g2);
        player.render(g2);

        if(KeyHandler.debug) {
            ui.debugInfo(g2);
        }

        g2.dispose();
    }

    @Override
    public void run() {
        final double NANOSECOND = 1000000000;
        final double FPS = 75;
        final double DRAW_INTERVAL = NANOSECOND / FPS;

        double delta = 0;
        double last = System.nanoTime();
        double now;
        double timer = 0;
        int frameCount = 0;
        oldFrameCount = 0;

        while(gameThread != null) {
            now = System.nanoTime();
            delta += (now - last) / DRAW_INTERVAL;
            timer += (now - last);
            last = now;

            if(delta >= 1) {
                input(keyH);
                update();
                repaint();
                delta--;
                frameCount++;
            }

            if(timer >= NANOSECOND) {
                if(oldFrameCount != frameCount) {
                    System.out.println("FPS: " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                timer = 0;
            }
        }
    }

    public Player getPlayer() { return player; }
    public KeyHandler getKeyHandler() { return keyH; }
}
