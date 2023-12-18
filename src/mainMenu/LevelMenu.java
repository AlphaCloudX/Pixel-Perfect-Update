/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for drawing the level selector menu
 */
package mainMenu;

import engine.GamePanel;
import mainMenu.login.LoginMenu;
import util.MouseInputs;
import entity.Player;
import util.FontRenderer;

import java.util.ArrayList;

import mainMenu.login.ReadLoginInfo;

import java.awt.*;

public class LevelMenu {

    //Buttons to pick from
    public static Button[] selection = new Button[11];

    //If the buttons have been registered and created
    public static boolean buttonsNotInitialized = true;

    //All the players used for sorting the top times
    public static Player[] allPlayers;

    //Top 5 scores
    public static ArrayList<String> topFive = new ArrayList<>();

    //All the level names
    public static String[] levelName = {"The Beginning", "Danger", "Hill", "Mount", "Down and Up", "Isolation", "Opposite", "Duality", "Over and Over", "The Finale"};

    /**
     * Initialize the buttons
     */
    public static void init() {
        //Jungle Levels
        selection[0] = new Button(200, 750, 100, 50, null, GamePanel.buttonAssets.get("jungleLevel"));
        selection[1] = new Button(600, 850, 100, 50, null, GamePanel.buttonAssets.get("jungleLevel"));
        selection[2] = new Button(1000, 875, 100, 50, null, GamePanel.buttonAssets.get("jungleLevel"));
        selection[3] = new Button(1400, 825, 100, 50, null, GamePanel.buttonAssets.get("jungleLevel"));
        //Rock Levels
        selection[4] = new Button(1600, 600, 100, 50, null, GamePanel.buttonAssets.get("mountainLevel"));
        selection[5] = new Button(1200, 500, 100, 50, null, GamePanel.buttonAssets.get("mountainLevel"));
        selection[6] = new Button(1600, 325, 100, 50, null, GamePanel.buttonAssets.get("mountainLevel"));
        //Snow levels
        selection[7] = new Button(1725, 175, 100, 50, null, GamePanel.buttonAssets.get("frostLevel"));
        selection[8] = new Button(1500, 100, 100, 50, null, GamePanel.buttonAssets.get("frostLevel"));
        selection[9] = new Button(1650, 15, 100, 50, null, GamePanel.buttonAssets.get("frostLevel"));

        selection[10] = new Button(50, 50, 50, 50, null, GamePanel.buttonAssets.get("backButton"));

        //Convert the arraylist to array so it is more efficient to sort 
        ArrayListToArray();
        //Sort the arraylist containing all the player's based on their time
        quickAscending(allPlayers, 0, allPlayers.length - 1);
        //Update the top five times 
        updateTopFive();
    }

    /**
     * Load the ArrayList to and array
     */
    public static void ArrayListToArray() {
        //Set the allPlayers to the size of the array list 
        allPlayers = new Player[LoginMenu.allPlayers.size()];
        //Loop through all the indexes and set all of them equal to eachother 
        for (int i = 0; i < allPlayers.length; i++) {
            allPlayers[i] = LoginMenu.allPlayers.get(i);
        }
    }

    /**
     * Do a certain thing based on which button is being clicked
     *
     * @param i the button being clicked
     */
    public static void click(int i) {

        //If the user clicked their mouse
        if (MouseInputs.clicked) {
            //If its the last button (the back button)
            if (i == 10) {
                //Set menu to 1 to display main menu
                GamePanel.menu = 1;
                updatePlayer();
                //If its a level button between 0 and 9
            } else if (0 <= i && i <= 9) {
                //set the menu to 4 to display the gameplay
                GamePanel.menu = 4;
                //set the gameLevel variable to the button it clicked 
                GamePanel.gameLevel = i;
                GamePanel.inGame = true;
            }
        }

    }

    /**
     * Draw a brief description for the level
     *
     * @param i the level being selected
     * @param g2d Graphics2D used to draw
     */
    public static void drawLevelDescription(int i, Graphics2D g2d) {
        //Set the length and width of the window
        int length = 300;
        int width = 50;
        //Create a boolean to determine if the player is hovering over the final level
        boolean finalLevel = false;

        // If the button is the back button
        if (i == 10) {
            //return and draw nothing
            return;
            //If the button is connected to the final level 9
        } else if (i == 9) {
            //Increate the length and width because there are more stuff to display
            finalLevel = true;
            length = 300;
            width = 200;
        }

        //Set the color of g2d to grey
        g2d.setColor(Color.gray);

        //Set the x and y value for the description box by subtracting both length and width from the mouse (the x,y is at the top left corner of box)
        int x = (int) MouseInputs.mouseX - length;
        int y = (int) MouseInputs.mouseY - width;

        //If the x value is off screen
        if (x < 0) {
            //Set the x value to the mouse so the box is displayed on the right instead of left 
            x = (int) MouseInputs.mouseX;
        }

        //If the y value is off screen
        if (y < 0) {
            //Set the y value to the mouse so the box is displayed on the bottom instead of top
            y = (int) MouseInputs.mouseY;
        }

        //Draw the box
        g2d.fillRect(x, y, length, width);

        //Change the font size to 20 to fit 
        changeFont(20, g2d);
        //Set color to black
        g2d.setColor(Color.black);
        //Write the level and the level description
        g2d.drawString("Level " + i + " - " + levelName[i], x + 2, y + 30);

        //If its the final level
        if (finalLevel) {
            //Loop through the top five player times and display them to the user
            for (int k = 0; k < topFive.size(); k++) {
                //If the name of the topfive record matches the player's name, turn the text to yellow to highlight 
                if (topFive.get(k).substring(3, GamePanel.player.getName().length() + 3).equals(GamePanel.player.getName())) {
                    g2d.setColor(Color.yellow);
                    //if it doesn't match, use black font
                } else {
                    g2d.setColor(Color.black);
                }

                //Draw the string with increasing y values after every loop 
                g2d.drawString(topFive.get(k), x + 2, y + k * 30 + 60);
            }
        }

    }

    /**
     * Display the level menu
     *
     * @param g2d the tool used to draw
     */
    public static void drawLevelMenu(Graphics2D g2d) {
        //If the buttons are not initialized, do so
        if (buttonsNotInitialized) {
            init();
            buttonsNotInitialized = false;
        }

        //Loop through all the button indexes in selection
        for (int i = 0; i < selection.length; i++) {
            //If the player's level progression is less than the level, and that it is the level button, and not the back button
            if (GamePanel.player.getLevelProgression() < i && i < 10) {
                //Draw the button using the lockedLevel image
                selection[i].setImage(GamePanel.buttonAssets.get("lockedLevel"));
                selection[i].draw(g2d);
                //If the player's level is equal or higher (they've unlocked the level)
            } else {
                //draw the button using intended image pathway
                selection[i].resetImage();
                selection[i].draw(g2d);
            }

            //If the user's mouse is inbound of the button
            if (selection[i].inBound(0)) {
                //If the player is clicking on an unlocked level, or if its the back button
                if (GamePanel.player.getLevelProgression() >= i || i > 9) {
                    //Draw an outline 
                    selection[i].drawButtonOutline(Color.white, g2d);
                    //Draw a level description
                    drawLevelDescription(i, g2d);
                    //Check for mouse clicks 
                    click(i);
                }

            }

        }

    }

    /**
     * Sort the given array in ascending order using quick sort
     *
     * @param array the array that will be sorted
     * @param low the lowest index of the array
     * @param high the highest index of the array
     */
    public static void quickAscending(Player[] array, int low, int high) {

        //If the low index is higher or the same as high index, it means that there is only 1 element in the array
        if (low >= high) {
            //return since it is already in order (1 number is always in order)
            return;
        }

        //Set the pivot as the highest index
        double pivot = array[high].getTime();

        //Create 2 pointers, the first starting at the left-most index, the other starting at the right-most index
        int leftPointer = low;
        int rightPointer = high;

        //While the left pointer is less than the right pointer (they did not cross over eachother)
        while (leftPointer < rightPointer) {

            //Loop until the pointer finds a index with a number that does not belong (loop stops when there is a number higher than pivot on the left side), while continuing to check that the 2 pointers do not cross eachother
            while (array[leftPointer].getTime() <= pivot && leftPointer < rightPointer) {
                //Add 1 to left pointer so it moves towards the right pointer
                leftPointer++;
            }
            //Loop until the pointer finds a index with a number that does not belong (loop stops when there is a number lower than pivot on the right side), while continuing to check that the 2 pointers do not cross eachother
            while (array[rightPointer].getTime() >= pivot && leftPointer < rightPointer) {
                //Subtract 1 from right pointer so it moves towards the right pointer
                rightPointer--;
            }

            //When the 2 indexs that contain numbers that dont belong are found, switch them so they end up on the correct side of the array
            swapIndex(array, leftPointer, rightPointer);
        }

        //After, swap the index of where the pointers met and the highest index (which is the pivot). Since we know that the pivot belongs between the 2 sides, and the middle number is the higher (otherwise the left pointer would have kept moving)
        swapIndex(array, leftPointer, high);

        //Since the array is now organised into 2 different parts, two recursive calls can be made to further sort both of the parts.
        //Quick sort the array to the left of the pointers
        quickAscending(array, low, leftPointer - 1);
        //Quick sort the array to the right of the pointers
        quickAscending(array, leftPointer + 1, high);

    }

    /**
     * Swap the indexes of an array
     *
     * @param array the array the indexes are from
     * @param index1 the first index
     * @param index2 the second index
     */
    public static void swapIndex(Player[] array, int index1, int index2) {
        //Create a variable to hold the value of array at index1
        Player temp = array[index1];
        //Set array at index1 to value of array at index2
        array[index1] = array[index2];
        //Set array at index2 to temp, which is the value of index1
        array[index2] = temp;
    }

    /**
     * Update the top five players currently with the best time
     */
    public static void updateTopFive() {
        //Clear the top five arraylist
        topFive.clear();
        //Start a counter 
        int counter = 0;

        //Loop through the array containing all the players 
        for (int i = 0; i < allPlayers.length; i++) {
            //If the user has a valid time, and that there are still space in the top 5
            if (allPlayers[i].getTime() != -1 && counter < 5) {
                //Increment the counter to show that a player is added
                counter++;
                //Add the player and time to the arrayList
                topFive.add(counter + ". " + allPlayers[i].getName() + " --- " + allPlayers[i].getTime() + "s");
            }
        }

    }

    /**
     * Update the player's information in the array and file
     */
    public static void updatePlayer() {
        //Loop through all the players 
        for (int i = 0; i < allPlayers.length; i++) {
            //After finding the player with the same name 
            if (allPlayers[i].getName().equals(GamePanel.player.getName())) {
                //replace the player at i with the current player in gamePanel (the moest up to date)
                allPlayers[i] = GamePanel.player;
            }
        }
        //Update the player information in the save file 
        updatePlayerFile();
        quickAscending(allPlayers, 0, allPlayers.length - 1);
        updateTopFive();
    }

    /**
     * Change the font size of g2d
     *
     * @param i the desired font size
     * @param g2d where the font size will be changed
     */
    public static void changeFont(int i, Graphics2D g2d) {
        //Set the font size as the parameter to font renderer
        FontRenderer.setFontsize(i);
        //set the font size to the g2d
        FontRenderer.setFont(g2d);
    }

    //rewrite the file with the array 
    public static void updatePlayerFile() {
        //Clear the file 
        ReadLoginInfo.clearFile();
        //Loop through all the players 
        for (int i = 0; i < allPlayers.length; i++) {
            //Write the name, levelProgression, and time for all players 
            ReadLoginInfo.pw.println(allPlayers[i].getName());
            ReadLoginInfo.pw.println(allPlayers[i].getLevelProgression());
            ReadLoginInfo.pw.println(allPlayers[i].getTime());
        }
        //Close the file 
        ReadLoginInfo.pw.close();

    }

}
