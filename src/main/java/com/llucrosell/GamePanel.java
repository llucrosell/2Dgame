package com.llucrosell;

import com.llucrosell.entity.Player;
import com.llucrosell.util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private static final int ORIGINAL_SIZE = 16;        // 16x16 tile
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_SIZE * SCALE;    // 48x48 tile

    private final int SCREEN_MAX_COL = 16;
    private final int SCREEN_MAX_ROW = 12;
    private final int SCREEN_WIDTH = TILE_SIZE * SCREEN_MAX_COL;    // 768 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_MAX_ROW;   // 576 pixels

    // SYSTEM
    private Thread gameThread;
    private KeyHandler keyH = new KeyHandler();
    private Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);

        this.setFocusable(true);

        startGameThread();
    }

    private void startGameThread() {
        gameThread = new Thread(this, "2D Game");
        gameThread.start();
    }

    public void update() {
        player.update();
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
