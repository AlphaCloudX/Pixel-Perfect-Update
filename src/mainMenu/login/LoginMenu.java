//Dec 20 2022
package mainMenu.login;

import engine.GamePanel;
import entity.Player;
import mainMenu.Button;
import util.FontRenderer;
import util.KeyboardInputs;
import util.MouseInputs;

import java.awt.*;
import java.util.ArrayList;

public class LoginMenu {
    //keep track of the loaded players
    public static ArrayList<Player> allPlayers = new ArrayList<>();

    //Username in the text box
    public static String username = "|";

    //Buttons on screen (create save, load save)
    public static Button[] selection = new Button[2];

    //If the buttons have not been initialized yet
    public static boolean buttonsNotInitialized = true;

    //Warning message displayed to the user
    public static String warning = "";

    /**
     * Method that draws the login screen
     *
     * @param g2d
     */
    public static void drawLoginMenu(Graphics2D g2d) {
        //Initialize the buttons onto the screen
        if (buttonsNotInitialized) {
            initiateButtons();
            buttonsNotInitialized = false;
        }
        FontRenderer.setFontsize(30);

        //Set the colour of the font
        g2d.setColor(Color.white);

        //Set the font
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        FontRenderer.setFont(g2d);


        //Draw the username and warning messages on screen
        g2d.drawString(username, 960 - 15 * username.length(), 500);
        g2d.drawString(warning, 800, 50);

        //Loop through the 2 buttons
        for (int i = 0; i < selection.length; i++) {

            //If the username is the default value
            if (username.equals("|")) {
                //Draw the given button to the screen in the respective x and y pos with grey color
                selection[i].drawText(Color.gray, g2d);

                //If the username has changed
            } else {
                //Draw the given button to the screen in the respective x and y pos with green color
                selection[i].drawText(Color.green, g2d);
            }


            //If the mouse is in the boundry of a given button
            if (selection[i].inBound(1)) {

                //Draw an outline around the button
                selection[i].drawTextOutline(Color.white, g2d);

                //If the user clicks on the button
                click(i);
            }
        }
        //Wait for key presses from the keyboard from supported characters
        checkKeyboardPress();

    }

    /**
     * Method to create the 2 needed buttons for the screen
     */
    public static void initiateButtons() {
        //Create 2 buttons on the screen with the x,y, length, width and name
        selection[0] = new Button(1100, 740, 170, 30, "Load Save");
        selection[1] = new Button(600, 740, 190, 30, "Create Save");

    }


    /**
     * Method to handle when a user clicks on a button
     *
     * @param i
     */
    public static void click(int i) {
        //If the user clicked their mouse
        if (MouseInputs.clicked) {
            Player loadedPlayer = search();
            //Check which button was pressed
            if (i == 0) {

                //If the players save exists
                if (loadedPlayer != null) {
                    //Load the player
                    GamePanel.player = loadedPlayer;
                    //remove the create save button
                    updateText();
                    //display splashscreen if first time else, go to main menu
                    if (GamePanel.splashDisplayed) {
                        GamePanel.menu = 1;
                    } else {
                        GamePanel.menu = -1;
                    }
                } else {
                    //Display a warning that the save is not found
                    warning = "Save not found";
                }
                //create save
            } else if (i == 1) {

                //If the players save does not exist
                if (loadedPlayer == null) {

                    //display splashscreen if first time else, go to main menu
                    if (GamePanel.splashDisplayed) {
                        GamePanel.menu = 1;
                    } else {
                        GamePanel.menu = -1;
                    }

                    //Create a new player
                    GamePanel.player = new Player(username.substring(0, username.length() - 1));

                    //Add to the players
                    allPlayers.add(GamePanel.player);

                    ReadLoginInfo.pw.println(username.substring(0, username.length() - 1));
                    ReadLoginInfo.pw.println(0);
                    ReadLoginInfo.pw.println(-1);
                    ReadLoginInfo.pw.close();
                    //remove the create save button
                    updateText();

                } else {
                    warning = "Save already exists";
                }
            }
        }
    }

    /**
     * Search method to find if a players save exists
     *
     * @return
     */
    public static Player search() {
        //Loop through all the player saves
        for (int i = 0; i < allPlayers.size(); i++) {

            //Check if a players save already exists
            if (allPlayers.get(i).getName().equals(username.substring(0, username.length() - 1))) {

                //If the save exists return that save
                return allPlayers.get(i);
            }
        }
        return null;
    }

    /**
     * Update the position and display of buttons and text after use
     */
    public static void updateText() {
        //"remove" create button so players cannot create new save after in game
        selection[0].setX(825);
        //Center "load" button since there is only 1 button left
        selection[1].setX(-200);
        //clear the warning message
        warning = "";
    }

    /**
     * Check if a user has pressed a supported character and append to the username
     */
    public static void checkKeyboardPress() {

        //Check if they pressed the supported keycodes
        for (int i = 65; i < 91; i++) {

            //If the key is pressed and the username is 10characters or less
            if (KeyboardInputs.keys[i] && username.length() < 11) {

                //Add the new character to the username
                username = username.substring(0, username.length() - 1) + (char) i + "|";

                //reset the keyboard state
                KeyboardInputs.keys[i] = false;
            }
        }

        //if the user presses backspace
        if (KeyboardInputs.keys[8]) {
            //check if there is enough characters to remove
            if (username.length() - 2 >= 0) {
                //remove characters
                username = username.substring(0, username.length() - 2) + "|";

                //otherwise the username is blank
            } else {
                username = "|";
            }
            //Reset the back button state
            KeyboardInputs.keys[8] = false;
        }

    }


}
