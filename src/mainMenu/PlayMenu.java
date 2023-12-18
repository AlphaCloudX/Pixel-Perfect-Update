/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for running the hud in game
 */
package mainMenu;

import java.awt.*;

import engine.*;
import engine.game.DrawLevel;
import util.*;

public class PlayMenu {

    //Attributes
    //All buttons
    public static Button[] selection = new Button[2];

    //return button
    public static Button returnBtn;

    //If to display end of level
    public static boolean levelEndDisplay = false;

    //Message for end of level
    public static String levelEndMessage = "";

    //time to display for end of level
    public static String levelEndTime = "";

    //have the needed buttons been initialized
    public static boolean isInit = false;

    /**
     * Initialize the buttons
     */
    public static void init(Graphics2D g2d, double xExtra, double yExtra) {
        //Back Button
        selection[0] = new Button((int) (((GamePanel.width / xExtra) - (85.0 * xExtra)) / 2.0), (int) (25 / yExtra), (int) (50 / xExtra), (int) (50 / yExtra), null, GamePanel.buttonAssets.get("backButton"));

        //Option Button
        selection[1] = new Button((int) (((GamePanel.width / xExtra) + (35.0 * xExtra)) / 2.0), (int) (25 / yExtra), (int) (50 / xExtra), (int) (50 / yExtra), null, GamePanel.buttonAssets.get("optionButton"));

        //End of game return button
        returnBtn = new Button((int) ((GamePanel.width / xExtra) / 2.0 - (50 / xExtra)), (int) ((GamePanel.height / yExtra) / 2.0), (int) (100.0 / xExtra), (int) (50.0 / yExtra), null, GamePanel.buttonAssets.get("okay"));

        isInit = true;
    }

    /**
     * Overlay ontop of the gameplay
     *
     * @param g2d Graphics2d object
     * @param xExtra x Scale Factor
     * @param yExtra y Scale Factor
     */
    public static void drawPlayMenu(Graphics2D g2d, double xExtra, double yExtra) {
        //Initialize the buttons if not initialized 
        if (!isInit) {
            init(g2d, xExtra, yExtra);
        }

        //Setup the font
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Set the font
        FontRenderer.setFont(g2d);
        FontRenderer.setFontsize(40);

        //Loop through all the buttons in the buttons array;
        for (int i = 0; i < selection.length; i++) {

            //If the level ended and the end display is being used
            if (levelEndDisplay) {
                //Set the color to a semi transparent black
                g2d.setColor(new Color(0f, 0f, 0f, .5f));
                //Draw a rectangle in the middle of screen that is filled

                //Scaled width and height
                double scaledGameWidth = GamePanel.width / xExtra;
                double scaledGameHeight = GamePanel.height / yExtra;

                //Draw rect behind the end of game text
                g2d.fillRect((int) (scaledGameWidth - 500) / 2, (int) (scaledGameHeight - 500) / 2, 500, 500);

                //Set color as white and display the level end message to the user
                g2d.setColor(Color.white);
                if (levelEndMessage.equals("Level Completed")) {
                    g2d.drawString(levelEndMessage, (int) (scaledGameWidth / 2 - 190), (int) (scaledGameHeight / 2 - 150));

                } else {
                    g2d.drawString(levelEndMessage, (int) (scaledGameWidth / 2 - 145), (int) (scaledGameHeight / 2 - 150));
                }
                //Draw the end of game time
                g2d.drawString(levelEndTime, (int) (scaledGameWidth / 2 - 150), (int) (scaledGameHeight / 2 - 50));

                //Draw the button to return to the level menu
                returnBtn.draw(g2d);

                //if the user's mouse is inbound of the return button
                if (returnBtn.inBoundScaled(0, xExtra, yExtra)) {
                    //draw the outline for the button
                    returnBtn.drawButtonOutline(Color.red, g2d);
                    //check if they are clicking
                    click(2);
                }

                //if the level is not finished, and the end display is not being used
            } else {
                //draw tutorial text if its the first few levels based on what they are teaching
                g2d.setColor(Color.white);
                if (GamePanel.gameLevel == 0) {
                    g2d.drawString("Use WASD or Arrow Keys to Move to House", (int)(GamePanel.width/xExtra) / 2 - 450, 120);
                } else if (GamePanel.gameLevel == 1) {
                    g2d.drawString("Avoid the Hazards! ", (int)(GamePanel.width/xExtra) / 2 - 200, 120);
                } else if (GamePanel.gameLevel == 4) {
                    g2d.drawString("You cannot strafe when falling!", (int)(GamePanel.width/xExtra) / 2 - 350, 120);
                }

                //Draw the buttons in the selection array with their filepath
                selection[i].draw(g2d);
                //If the user's mouse is inbound of the button
                if (selection[i].inBoundScaled(0, xExtra, yExtra)) {
                    //Draw the outline for the button
                    selection[i].drawButtonOutline(Color.white, g2d);
                    //check if they are clicking
                    click(i);
                }
            }

        }
    }

    /**
     * Change certain values based on what button the user clicked
     *
     * @param i the button that was clicked
     */
    public static void click(int i) {
        //if the mouse is clicked
        if (MouseInputs.clicked) {
            //if its the first button
            if (i == 0) {
                //reset the level when quit
                GamePanel.inGame = false;
                DrawLevel.levelLoaded = false;
                GamePanel.startTimer = false;
                GamePanel.inLoadingScreen = false;
                //set menu to 2 and return to the level menu
                GamePanel.menu = 2;
                //if its the second button
            } else if (i == 1) {
                //set menu to 3, and display the options menu, if player clicks back, return them to the games menu
                GamePanel.menu = 3;
                OptionMenu.returnMenu = 4;
                //If its the button on the end display
            } else if (i == 2) {
                //set menu to 2, and return to the level menu, also disabling the level end display;
                levelEndDisplay = false;
                levelEndTime = "";

                //reset the level when quit
                GamePanel.inGame = false;
                DrawLevel.levelLoaded = false;

                GamePanel.inLoadingScreen = false;
                GamePanel.startTimer = false;

                //Back to play menu
                GamePanel.menu = 2;

            }
        }
    }
}
