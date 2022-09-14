package com.llucrosell.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    public boolean up, down, left, right;


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
        toggle(e.getKeyCode(), false);
    }
}
