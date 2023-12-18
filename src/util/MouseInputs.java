/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for sensing mouse clicks
 */
package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputs implements MouseListener {

    //Keep track of mouse x, y and click values
    public static double mouseX = 0;
    public static double mouseY = 0;
    public static boolean clicked = false;


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    /**
     * Has the mouse been pressed
     */
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }

    @Override
    /**
     * Has the mouse been released
     */
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }

    @Override
    /**
     * Has the mouse entered the screen
     */
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    /**
     * Has the mouse exited the screen
     */
    public void mouseExited(MouseEvent e) {
    }


}
