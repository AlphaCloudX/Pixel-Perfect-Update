package mainMenu.login;

import entity.Player;

import javax.swing.*;
import java.util.Scanner;
import java.io.*;

public class ReadLoginInfo {

    //Save path for the player data
    public static String playerSavePath;
    public static PrintWriter pw;


    /**
     * Method to get the file path of the players saves
     */
    public static void getFile() {

        //Create a new file chooser object
        JFileChooser fileChooser = new JFileChooser();

        //Set the approve button to be replaced with load save so it fits the game better
        fileChooser.setApproveButtonText("Load Save");

        //Set approved
        int approved = JFileChooser.CANCEL_OPTION;

        //Run the Menu until the save is loaded
        //if the user presses cancel and wants to go back, this loop allows that option, otherwise the menu does not return
        while (approved != JFileChooser.APPROVE_OPTION) {

            //Run the dialogue window asking for the file path
            approved = fileChooser.showOpenDialog(null);

            //If the cancel button is pressed load the 2nd window asking if they would like to exit or continue
            if (approved != JFileChooser.APPROVE_OPTION) {

                // Possible buttons the user can click on if they press the cancel button
                String[] options = new String[]{"Continue", "Exit Game"};

                // If the user presses cancel in the above
                int returnValue = JOptionPane.showOptionDialog(null, "You must choose a file to start the game", "File not found", JOptionPane.WARNING_MESSAGE, 0, null, options, options[0]);

                // If the user wants to quit the game
                if (returnValue == 1) {
                    //Exit the application
                    System.exit(-1);
                }

            }

        }

        //Cache the save path of the players files
        playerSavePath = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            //Attempt to create a new save file
            new File(playerSavePath).createNewFile();
            pw = new PrintWriter(new FileWriter(playerSavePath, true));
            //Load all the players data from the path
            loadArrayList();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Method to read all the player data from the saves and organize into an arraylist of player data that can then be indexed
     */
    public static void loadArrayList() {

        //Caching variables when reading the players saves
        String playerName;
        int levelProgress;
        //long time;
        double time;

        try {
            //Create file and scanner object
            File f = new File(playerSavePath);
            Scanner s = new Scanner(f);

            //Read each of the saves
            while (s.hasNextLine()) {
                //Find the player name
                playerName = s.nextLine();

                //Find what levels they completed
                levelProgress = Integer.parseInt(s.nextLine());

                //Find what their time for the final level is
                //time = Long.parseLong(s.nextLine());
                time = Double.parseDouble(s.nextLine());

                //Add the current save to the player list so it can be indexed for the right player
                LoginMenu.allPlayers.add(new Player(playerName, levelProgress, time));

            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }


    public static void clearFile() {
        try {
            //clear the file
            new PrintWriter(playerSavePath).close();
            pw = new PrintWriter(new FileWriter(playerSavePath, true));

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
