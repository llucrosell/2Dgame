package com.llucrosell;

import com.llucrosell.entity.Player;
import com.llucrosell.graphics.Assets;
import com.llucrosell.util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private static final int ORIGINAL_SIZE = 64;        // 16x16 tile
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_SIZE * SCALE;    // 48x48 tile

    private static final int SCREEN_MAX_COL = 16;
    private static final int SCREEN_MAX_ROW = 12;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    // SYSTEM
    private Thread gameThread;
    private KeyHandler keyH;
    private Player player;
    private Assets assets;

    public GamePanel(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        init();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);

        this.setFocusable(true);

        startGameThread();
    }

    private void init() {
        keyH = new KeyHandler();
        assets = new Assets();
        player = new Player(this);
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

        player.render(g2);

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
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                timer = 0;
            }
        }
    }
}
