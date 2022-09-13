package com.llucrosell;

import com.llucrosell.util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private final int ORIGINAL_SIZE = 16;        // 16x16 tile
    private final int SCALE = 3;
    private final int TILE_SIZE = ORIGINAL_SIZE * SCALE;    // 48x48 tile

    private final int SCREEN_MAX_COL = 16;
    private final int SCREEN_MAX_ROW = 12;
    private final int SCREEN_WIDTH = TILE_SIZE * SCREEN_MAX_COL;    // 768 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_MAX_ROW;   // 576 pixels

    // SYSTEM
    private Thread gameThread;
    private KeyHandler keyH = new KeyHandler();

    // Set player's default position

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

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(100, 100, TILE_SIZE, TILE_SIZE);
        g2.dispose();
    }

    @Override
    public void run() {
        while(gameThread != null) {
            // 1.- UPDATE
            update();
            // 2.- DRAW
            repaint();
        }
    }
}
