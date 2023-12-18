/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for sensing keyboard inputs 
 */
package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    //Array to keep track of what key is pressed
    // KeyboardInptus.keys[keyCode] == true
    public static boolean[] keys = new boolean[65536];

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /**
     * If a key has been pressed register in boolean array
     */
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    /**
     * If a key has been released register in boolean array
     */
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

    }
}
