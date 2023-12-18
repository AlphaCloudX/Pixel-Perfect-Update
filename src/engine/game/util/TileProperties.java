/**
 * Michael, David, Ethan
 * 1/16/2023
 * File that is responsible for reading the tile properties for each level
 */
package engine.game.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class TileProperties {

    //Public hashmap to access all properties about a given tile
    public static HashMap<String, Boolean[]> tileProperties = new HashMap<>();

    /**
     * Method to read the tiles properties file This logs all the supported
     * tiles for a given level and their characteristics
     */
    /**
     * Method to read the tiles properties file This logs all the supported
     * tiles for a given level and their characteristics
     *
     * @param path tile properties directory as input stream
     */
    public static void readTileProperties(InputStream path) {
        //Create scanner object to read file properties
        Scanner sc = new Scanner(path);

        //Skip the comment
        sc.nextLine();

        //Read each line from the
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] parsedData = data.split(",");

            //Parse out the info about if that tile is passable and if it is a hazard
            //hazards should be passable so they can interact and damage the player
            boolean isPassable = Boolean.parseBoolean(parsedData[1]);
            boolean isHazard = Boolean.parseBoolean(parsedData[2]);

            //Append the data to the hashmap
            tileProperties.put(parsedData[0], new Boolean[]{isPassable, isHazard});
        }
        sc.close();

    }

    /**
     * Method that returns true if a tile is passable
     *
     * @param t Tile object
     * @return true or false
     */
    public static boolean isPassable(String t) {
        return tileProperties.get(t)[0];
    }

    /**
     * Method that returns true if a tile is a hazard When coming in contact
     * with a hazard tile, the player loses the level
     *
     * @param t Tile object
     * @return true or false
     */
    public static boolean isHazard(String t) {
        return tileProperties.get(t)[1];
    }
}
