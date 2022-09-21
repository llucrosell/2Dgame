package com.llucrosell.util;

import com.llucrosell.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    private GamePanel gp;

    public boolean up, down, left, right;
    public static boolean debug = false;


    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    private void toggle(int code, boolean pressed) {
        if(code == KeyEvent.VK_W) {
            up = pressed;
        }
        if(code == KeyEvent.VK_S) {
            down = pressed;
        }
        if(code == KeyEvent.VK_A) {
            left = pressed;
        }
        if(code == KeyEvent.VK_D) {
            right = pressed;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // UNUSED ATM
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_H && e.isControlDown()) {
            debug = !debug;
        } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println(gp.getPlayer().playerLocation());
        } else {
            toggle(e.getKeyCode(), false);
        }
    }
}
