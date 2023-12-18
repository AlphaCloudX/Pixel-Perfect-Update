/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for running the main menu screen
 */
package mainMenu;

import engine.GamePanel;
import util.MouseInputs;

import java.awt.*;

import util.FontRenderer;

public class MainMenu {

    //All possible buttons
    private static final String[] buttonNames = {"Start", "Options", "Credits", "Exit", "Playing as: " + GamePanel.player.getName()};
    //All the lengths of buttons
    private static final int[] buttonLengths = {170, 270, 250, 130, 202};
    //All the main buttons
    private static final Button[] selection = new Button[5];
    //Keep track if the buttons have been added to the selection, only needs to be added once
    private static boolean buttonsNotInitialized = true;
    //if the user is in the credits menu
    private static boolean displayCredits = false;

    /**
     * Draw the main menu screen
     *
     * @param g2d
     */
    public static void drawMainMenu(Graphics2D g2d) {
        //Load all the buttons on the main menu once
        if (buttonsNotInitialized) {
            initiateButtons();
        }

        //Set the font to white
        g2d.setColor(Color.white);

        //Setup the font
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Set the font
        FontRenderer.setFont(g2d);
        FontRenderer.setFontsize(65);

        //If the user enters the credit menu
        if (displayCredits) {
            //0.5 opacity black background
            //Draw an outlined rect with text inside for credits
            g2d.setColor(new Color(0f, 0f, 0f, .5f));
            g2d.fillRect(200, 100, 1520, 880);
            //Change the color to blue and draw a outline of the rectangle
            g2d.setColor(Color.blue);
            g2d.drawRect(200, 100, 1520, 880);
            //Display the developers of the game 
            g2d.drawString("-- Developers --", 700, 250);
            g2d.drawString("Michael", 850, 450);
            g2d.drawString("Ethan", 870, 575);
            g2d.drawString("David", 870, 700);

            //Check if the user wants to exit the credits page
            if (MouseInputs.mouseX < 200 || MouseInputs.mouseX > 1780 || MouseInputs.mouseY < 100 || MouseInputs.mouseY > 980) {
                //Check if they clicked to in the given boundaries
                if (MouseInputs.clicked) {
                    displayCredits = false;
                }
            }
        } else {
            //Draw the game title
            changeFont(80, g2d);
            g2d.drawString("Pixel Perfect", 200, 200);
            //Change the 4th button's text to include the player's name
            selection[4].setTitle("Playing as: " + GamePanel.player.getName());
            //Decrease the font size and draw the button (its to change saves)
            changeFont(30, g2d);
            selection[4].drawText(Color.white, g2d);

            //for the 4 normal buttons on menu
            for (int i = 0; i < selection.length; i++) {
                //If it is display the 4th button, then use a smaller font
                if (i == 4) {
                    changeFont(30, g2d);
                    //else, use the "normal" font size
                } else {
                    changeFont(65, g2d);
                }
                //Check if the mouse is in a given box
                if (selection[i].inBound(1)) {

                    //If the mouse is in a box highlight the text
                    selection[i].drawText(Color.green, g2d);

                    //Draw an outline 
                    selection[i].drawTextOutline(Color.white, g2d);
                    click(i);
                } else {
                    //Set the font to white
                    selection[i].drawText(Color.white, g2d);
                }

            }

        }

    }

    /**
     * Method that adds all the needed buttons to the array
     */
    public static void initiateButtons() {
        //All Button names

        //Add normal buttons to array
        for (int i = 0; i < buttonNames.length; i++) {
            selection[i] = new Button(200, 390 + (150 * i), buttonLengths[i], 50, buttonNames[i]);
        }
        selection[4] = new Button(202, 130, 350, 25, buttonNames[4]);
        //All buttons added to array
        buttonsNotInitialized = false;

    }

    /**
     * Handle the given outputs depending on the button being pressed
     *
     * @param buttonID the button currently being checked
     */
    public static void click(int buttonID) {
        //if the user clicked
        if (MouseInputs.clicked) {
            //Start Button
            if (buttonNames[buttonID].equals(buttonNames[0])) {
                //Change the menu to display level menu
                GamePanel.menu = 2;
                //options
            } else if (buttonNames[buttonID].equals(buttonNames[1])) {
                //Display the options menu and set the return to this menu so it returns to MainMenu when clicking back
                OptionMenu.returnMenu = 1;
                GamePanel.menu = 3;
                //Credits
            } else if (buttonNames[buttonID].equals(buttonNames[2])) {
                //display the credits 
                displayCredits = true;
                //Exit
            } else if (buttonNames[buttonID].equals(buttonNames[3])) {
                //Terminate the applcation
                System.exit(0);
                //Change Profile
            } else if (buttonNames[buttonID].equals(buttonNames[4])) {
                //Display the login menu
                GamePanel.menu = 0;
            }
        }
    }

    /**
     * Change the font of the Graphics2D tool
     *
     * @param i the desired font
     * @param g2d the g2d to have its font changed
     */
    public static void changeFont(int i, Graphics2D g2d) {
        //Set the font size to parameter  
        FontRenderer.setFontsize(i);
        //Set the font to the g2d
        FontRenderer.setFont(g2d);
    }
}
