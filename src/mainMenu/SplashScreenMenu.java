/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for running the splash screen
 */
package mainMenu;

import javax.imageio.ImageIO;
import java.awt.*;

import engine.GamePanel;
import engine.game.LoadingWorld;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashScreenMenu {

    //Background image
    private static BufferedImage img;

    //If initialized
    private static boolean imageInit = false;

    /**
     * Initiate the images to be loaded for the loading screen
     */
    public static void initiateImage() {
        //Create Image Object
        try {
            img = ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/backgrounds/SplashScreenV1.png"));
        } catch (IOException ex) {
            Logger.getLogger(LoadingWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Image has been created, no need to run again
        imageInit = true;
    }

    /**
     * Draw the splash screen
     *
     * @param g2d
     */
    public static void drawSplashScreen(Graphics2D g2d) {
        if (!imageInit) {
            //Initialize the image if it hasn't been already
            initiateImage();
        }

        //Draw image
        g2d.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null);
    }

}
