/**
 * Michael, David, Ethan
 * 1/16/2023
 * File that is responsible for loading the level and its assets which then creates the level as an arraylist
 */
package engine.game;

import engine.GamePanel;
import engine.game.util.TileProperties;

import java.io.IOException;

import tile.Tile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class LoadingWorld {

    /**
     * Load a csv level into a tile array
     *
     * @param levelPath the path to the level folder
     * @param tilesAssetPath the path to the tile assets
     */
    public static void loadLevel(InputStream levelPath, String tilesAssetPath) {

        // Read a particular level
        // Read the csv file to extract game data
        Scanner sc = new Scanner(levelPath);

        //Keep track of tile placement
        int rowCount = 0;
        int columnCount = 0;

        //Loop throughout the whole csv
        while (sc.hasNextLine()) {
            // Create an array for each row being added
            DrawLevel.gameTiles.add(new ArrayList<>());

            //Current row
            String[] currentLine = sc.nextLine().split(",");

            //Loop for each new tile in the row
            for (String tile : currentLine) {
                if (tile.equals("")) {
                    tile = "air";
                }
                //Check if the tile is registered in the tile properties file
                if (!TileProperties.tileProperties.containsKey(tile)) {
                    System.out.println(tile + " is missing");
                    //if the asset is not found use the missing texture
                    tile = "missing_texture";
                }
                //Add the tile to the array
                DrawLevel.gameTiles.get(rowCount).add(new Tile(tile, columnCount * GamePanel.gameTileSizeX, rowCount * GamePanel.gameTileSizeY, GamePanel.gameTileSizeX, GamePanel.gameTileSizeY, TileProperties.isPassable(tile), TileProperties.isHazard(tile)));

                try {
                    DrawLevel.assets.put(tile, ImageIO.read(GamePanel.defaultClassPath.getResource(tilesAssetPath + "/" + tile + ".png")));
                } catch (IOException ex) {
                    Logger.getLogger(LoadingWorld.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Set the x and y pos
                if (tile.equals("start")) {
                    GamePanel.player.setX(columnCount * GamePanel.gameTileSizeX);
                    GamePanel.player.setY((rowCount * GamePanel.gameTileSizeY) + (GamePanel.gameTileSizeY - GamePanel.playerTileSizeY));

                }

                //Update column count for new column in row
                columnCount++;
            }

            //Update the row counts and reset column count for now line
            rowCount++;
            columnCount = 0;

        }

        //Add player to assets
        try {
            DrawLevel.assets.put("player", ImageIO.read(GamePanel.defaultClassPath.getResource(DrawLevel.tilesAssetPath + "/player.png")));
        } catch (IOException ex) {
            Logger.getLogger(LoadingWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Add background to assets
        try {
            DrawLevel.assets.put("background", ImageIO.read(GamePanel.defaultClassPath.getResource(DrawLevel.levelProperties.get("background"))));
        } catch (IOException ex) {
            Logger.getLogger(LoadingWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Add vignette
        try {
            DrawLevel.assets.put("vignete", ImageIO.read(GamePanel.defaultClassPath.getResource(DrawLevel.levelProperties.get("effects"))));
        } catch (IOException ex) {
            Logger.getLogger(LoadingWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
