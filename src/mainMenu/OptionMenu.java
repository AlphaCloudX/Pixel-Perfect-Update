/**
 * David, Ethan, Michael
 * 1/16/2023
 * File that is responsible for running the option menu screen
 */
package mainMenu;

import engine.GamePanel;
import util.MouseInputs;

import java.awt.*;

import util.FontRenderer;

public class OptionMenu {

    //Attributes
    //Button array
    public static Button selection[] = new Button[6];
    //powerup selected
    public static int powerSelected;
    //description of powerup
    public static String powerDescription = "";
    //Keep track if the buttons have been initialized
    public static boolean buttonsNotInitialized = true;
    //play music true or false
    public static boolean music = true;
    //play sfx true or false
    public static boolean sfx = true;
    //menu to return to
    public static int returnMenu;

    /**
     * Initiate the buttons and their attributes
     */
    public static void initiateButtons() {
        //normal buttons
        selection[0] = new Button(755, 395, 40, 40);
        selection[1] = new Button(755, 545, 40, 40);
        selection[2] = new Button(200, 200, 175, 50, "Back");
        selection[3] = new Button(510, 660, 80, 100);
        selection[4] = new Button(615, 660, 80, 100);
        selection[5] = new Button(720, 660, 80, 100);

    }

    /**
     * Draw the options menu
     *
     * @param g2d the tool used for drawing
     */
    public static void drawOptionMenu(Graphics2D g2d) {

        //Initiate the buttons if not initialized 
        if (buttonsNotInitialized) {
            initiateButtons();
            buttonsNotInitialized = false;
        }

        //set the color of g2d to white
        g2d.setColor(Color.white);

        //Set the rendering and font size to match with other menus 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        FontRenderer.setFont(g2d);
        FontRenderer.setFontsize(65);

        //Draw the 3 labels for user input so user know what they are clicking on
        g2d.drawString("Music", 200, 440);
        g2d.drawString("SFX", 200, 590);
        g2d.drawString("Mod", 200, 740);
        //Draw a label that describes their powerup
        g2d.drawString(powerDescription, 200, 840);

        //Display the power up options using g2d
        displayPowerUps(g2d);

        //for the 2 normal buttons on menu
        for (int i = 0; i < 2; i++) {
            //Set the color to white 
            g2d.setColor(Color.white);
            //Draw the outlines for the "on/off" switch for sound
            g2d.drawRect(700, 390, 100, 50);
            g2d.drawRect(700, 540, 100, 50);
            //Draw the text (the only one is back) of the buttons
            selection[i].drawText(Color.white, g2d);

            //If the player's mouse is in bound of the buttons
            if (selection[i].inBound(0)) {
                //check if they are clicking
                click(i);
            }

            //If the music is on, and the loop is on the music button, or if the sfx is on, and the loop is on the sfx button
            if ((i == 0 && music) || (i == 1 && sfx)) {
                //Draw the button as white to show that its enabled
                selection[i].drawButton(Color.white, g2d);
                //If the corresponding sound is toggled off
            } else {
                //Draw the button as grey to show that its disabled 
                selection[i].drawButton(Color.gray, g2d);
            }

        }

        //If the user's mouse is inbound of the backbutton
        if (selection[2].inBound(1)) {
            //draw an outline and turn text green if inbound
            selection[2].drawText(Color.green, g2d);
            selection[2].drawTextOutline(Color.white, g2d);
            //check if the user is clicking
            click(2);
        } else {
            //Draw the back button as white text 
            selection[2].drawText(Color.white, g2d);
        }

    }

    /**
     * Display the power up options to user
     *
     * @param g2d
     */
    public static void displayPowerUps(Graphics2D g2d) {
        //Declaring variable
        int numOfPower;

        //If completed level 3
        if (GamePanel.player.getLevelProgression() > 3) {
            //They have unlocked 1 power
            numOfPower = 1;
            //If they have not completed level 3
        } else {
            //No power ups are unlocked
            numOfPower = 0;
        }

        //Change the power description based on what power the player has selected
        if (powerSelected == 1) {
            //Set Player Speed to 6
            powerDescription = "Speed +";
            GamePanel.playerSpeed = 6;
        } else {
            powerDescription = "";
            //Reset Player Speed
            GamePanel.playerSpeed = 4;
        }

        //Draw the default powerup button 
        selection[3].drawButton(powerUpColor(0), g2d);
        //Set color based on if its chosen
        g2d.setColor(powerTextColor(0));
        //Draw label on it showing that it has no effects
        g2d.drawString("-", 535, 735);

        //If the player has unlocked 1 power ups
        if (numOfPower == 1) {
            //draw the first power up
            selection[4].drawButton(powerUpColor(1), g2d);
            //Set color based on if its chosen
            g2d.setColor(powerTextColor(1));
            //Draw label on it showing that its Power Up A
            g2d.drawString("A", 630, 735);
        }

        //Check for all the buttons being displayed (for power ups)
        for (int i = 3; i < 4 + numOfPower; i++) {
            //if the user's mouse is in bound
            if (selection[i].inBound(0)) {
                //draw an outline
                selection[i].drawButtonOutline(Color.white, g2d);
                //check if player is clicking
                click(i);
            }
        }

    }

    /**
     * Determine what the color of the button should be for power ups
     *
     * @param power the power up that is having its color chosen
     * @return what color it should be
     */
    public static Color powerUpColor(int power) {
        //If the power is selected
        if (powerSelected == power) {
            //it should be white
            return Color.white;
            //If the power is not selected
        } else {
            //It should be grey
            return Color.gray;
        }
    }

    /**
     * Determine what the color of the text on the button should be for power
     * ups
     *
     * @param power the power up that is having its color chosen
     * @return what color it should be
     */
    public static Color powerTextColor(int power) {
        //If the power is selected
        if (powerSelected == power) {
            //text should be black
            return Color.black;
            //If the power is not selected
        } else {
            //text should be white
            return Color.white;
        }
    }

    /**
     * Do certain things if the user click a button
     *
     * @param i which button is being clicked
     */
    public static void click(int i) {
        //If the user clicked
        if (MouseInputs.clicked) {
            //If its first button
            if (i == 0) {
                //If music enabled, disable it
                if (music) {
                    music = false;
                    //shift the x pos so it's at the toggled off spot
                    selection[0].setX(705);
                    //If music disabled, enable it
                } else {
                    music = true;
                    //shift the x pos so it's at the toggled on spot
                    selection[0].setX(755);
                }
                //If ites the second button
            } else if (i == 1) {
                //If sfx enabled, disable it
                if (sfx) {
                    sfx = false;
                    //shift the x pos so it's at the toggled off spot
                    selection[1].setX(705);
                    //If sfx disabled, enable it
                } else {
                    sfx = true;
                    //shift the x pos so it's at the toggled on spot
                    selection[1].setX(755);
                }
                //If the third button is clicked, set the menu being display to the return menu
            } else if (i == 2) {
                GamePanel.menu = returnMenu;
                //If the forth button clicked
            } else if (i == 3) {
                //Set the power selected to 0
                powerSelected = 0;
                //if btn no. 5
            } else if (i == 4) {
                //Set the power selected to 1
                powerSelected = 1;
                //if btn no. 6
            } else if (i == 5) {
                //Set the power selected to 2
                powerSelected = 2;
            }
        }

    }

}
