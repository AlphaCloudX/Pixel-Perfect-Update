/**
 * Michael, David, Ethan
 * 1/16/2023
 * File that is responsible for reading the level properties for each level
 */
package engine.game.util;

import engine.GamePanel;

import java.util.HashMap;
import java.util.Scanner;

public class LevelProperties {

    /**
     * Method to read the level properties file This logs all the required files
     * for a given level
     *
     * @param path String pathway to the level properties
     * @return the level properties hashmap of all needed directories for a
     * level
     */
    public static HashMap<String, String> readLevelProperties(String path) {
        HashMap<String, String> levelProperties = new HashMap<>();

        //Create scanner object to read file properties
        Scanner sc = new Scanner(GamePanel.defaultClassPath.getResourceAsStream(path));

        //Skip the comment
        sc.nextLine();

        //Read each line from the
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] parsedData = data.split(":");

            //Append the data to the hashmap
            levelProperties.put(parsedData[0], parsedData[1].replace("\"", ""));
        }
        sc.close();

        return levelProperties;
    }

}
