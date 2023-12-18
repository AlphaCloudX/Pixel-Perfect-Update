/**
 * Michael, David, Ethan
 * 1/16/2023
 * File that is responsible for running the level from the arraylist and drawing everything to the panel
 */
package engine.game;

import engine.GamePanel;
import tile.Tile;

import java.awt.*;
import java.util.ArrayList;

public class RunningLevel {

    /**
     * Draw level
     *
     * @param g2d Graphics2d object
     * @param gameTiles gametiles array
     * @param xExtra x Scale factor
     * @param yExtra y Scale Factor
     */
    public static void drawLevel(Graphics2D g2d, ArrayList<ArrayList<Tile>> gameTiles, double xExtra, double yExtra) {

        //Draw Background
        g2d.drawImage(DrawLevel.assets.get("background"), 0, 0, (int) (GamePanel.width / xExtra), (int) (GamePanel.height / yExtra), null);

        //Draw all the tiles  in the arraylist
        for (ArrayList<Tile> row : gameTiles) {
            for (Tile column : row) {
                column.drawTile(g2d);
            }
        }

        //Draw the player on the screen
        GamePanel.player.draw(g2d);

        //Draw Background
        g2d.drawImage(DrawLevel.assets.get("vignete"), 0, 0, (int) (GamePanel.width / xExtra), (int) (GamePanel.height / yExtra), null);
    }

    /**
     * Method to return the tile at a given position
     *
     * @param x x pos in array
     * @param y y pos in array
     * @return Tile at pos
     */
    public static Tile getTileAtPos(int x, int y) {
        return DrawLevel.gameTiles.get(y).get(x);
    }

}
