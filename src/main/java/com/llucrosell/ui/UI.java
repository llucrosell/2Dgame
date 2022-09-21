package com.llucrosell.ui;

import com.llucrosell.GamePanel;

import java.awt.*;

public class UI {
    private GamePanel gp;

    private final int debug_tailx = GamePanel.SCREEN_WIDTH - 50;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    public void debugInfo(Graphics2D g2) {
        systemDebugInfo(g2);
        playerDebugInfo(g2);
    }

    private void systemDebugInfo(Graphics2D g2) {
        String text = "";
        int textY = 100;
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        text = "FPS: " + GamePanel.oldFrameCount;
        g2.drawString(
                text,
                getXtextAlignRight(text, debug_tailx, g2),
                textY
        );
    }

    private void playerDebugInfo(Graphics2D g2) {
        // PLAYER POSITION
        String text = "";
        int textY = 200;
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        text = "X Y: " + gp.getPlayer().playerLocation().x + " " + gp.getPlayer().playerLocation().y;
        g2.drawString(
                text,
                getXtextAlignRight(text, debug_tailx, g2),
                textY
        );
        textY += 50;
        text = "World: " + gp.getPlayer().playerWorldLocation().x + " " + gp.getPlayer().playerWorldLocation().y;
        g2.drawString(
                text,
                getXtextAlignRight(text, debug_tailx, g2),
                textY
        );
        textY += 50;
        text = "Chunk: " + gp.getPlayer().playerChunkLocation().x + " " + gp.getPlayer().playerChunkLocation().y;
        g2.drawString(
                text,
                getXtextAlignRight(text, debug_tailx, g2),
                textY
        );
    }

    public int getXtextAlignCenter(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (GamePanel.SCREEN_WIDTH / 2 - length / 2);
    }

    public int getXtextAlignRight(String text, int tailX, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (tailX - length);
    }
}
