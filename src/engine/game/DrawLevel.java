/**
 * Michael, David, Ethan
 * 1/16/2023
 * File that is responsible for reading the level data and drawing everything to the screen
 */
package engine.game;

import engine.GamePanel;

import static engine.GamePanel.defaultClassPath;

import engine.game.util.LevelProperties;
import engine.game.util.TileProperties;
import mainMenu.PlayMenu;
import tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawLevel {

    //Is the level loaded already
    public static boolean levelLoaded = false;

    //Store the level info
    public static ArrayList<ArrayList<Tile>> gameTiles = new ArrayList<>();

    //The default folder the level is located in
    public static String levelPath = "game_files/world_data";

    //Used to access the players path
    public static String tilesAssetPath;

    //Game scaling factors
    private static double xExtra;
    private static double yExtra;

    //Store the Images for each level
    public static HashMap<String, BufferedImage> assets;

    // Required paths for each level
    public static HashMap<String, String> levelProperties;

    /**
     * Method to draw the level onto the screen
     *
     * @param g2d Graphics2d object
     * @param gameLevel the game level being drawn
     */
    public static void drawLevel(Graphics2D g2d, int gameLevel) {
        //If the level has not yet been loaded
        //Load the level data only once per level
        if (!levelLoaded) {
            //Clear the images loaded
            assets = new HashMap<>();

            //Clear the level
            gameTiles = new ArrayList<>();

            //Path to the level properties, later read as local file path
            String levelPropertiesPath = levelPath + "/level" + gameLevel + "_paths.properties";

            // Get the required paths for each level
            levelProperties = LevelProperties.readLevelProperties(levelPropertiesPath);
            System.out.println("LEVEL PROPERTIES: " + levelProperties);

            //Cache the hashmap values into variables for easy referencing
            tilesAssetPath = levelProperties.get("tiles");
            System.out.println("Tile Asset Path: " + tilesAssetPath);

            //Path to the tile properties
            String tilePropertiesFile = levelProperties.get("tile.properties");
            System.out.println("Tile Property Path: " + tilePropertiesFile);

            //Read all of the tiles properties
            //TileProperties.readTileProperties(tilePropertiesFile);
            TileProperties.readTileProperties(defaultClassPath.getResourceAsStream(tilePropertiesFile));
            System.out.println("Tile Properties File: " + TileProperties.tileProperties.toString());

            //path the level data(map), read as local file path below
            //Load the world into the game tiles arraylist
            LoadingWorld.loadLevel(defaultClassPath.getResourceAsStream(levelPath + "/level" + gameLevel + ".csv"), tilesAssetPath);
            //System.out.println("Loaded World: " + DrawLevel.gameTiles.toString());

            //game scale
            xExtra = GamePanel.width / (gameTiles.get(0).size() * GamePanel.gameTileSizeX * 1.0);
            yExtra = GamePanel.height / (gameTiles.size() * GamePanel.gameTileSizeY * 1.0);
            System.out.println("Game Scaled: [" + xExtra + ", " + yExtra + "]");

            //Level has been loaded so the files do not have to be reread
            levelLoaded = true;
            System.out.println("Level Started");

            //The player has entered the game
            GamePanel.inGame = true;

        } else {
            //Scale the g2d so it fits the level and screen
            g2d.scale(xExtra, yExtra);

            //Draw level
            RunningLevel.drawLevel(g2d, gameTiles, xExtra, yExtra);

            //Draw the hud
            PlayMenu.drawPlayMenu(g2d, xExtra, yExtra);
        }

    }

}
