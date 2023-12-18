/**
 * David Zhang
 * Dec 28 2022
 * General tile Class
 */
package tile;

import engine.game.DrawLevel;

import java.awt.*;

public class Tile {

    //Attributes for a tile
    //Tile Name
    private String tileName;
    //Tile x and y pos
    private int x;
    private int y;
    //Tile dimensions
    private int length;
    private int width;
    //Tile properties
    private boolean isPassable;
    private boolean hazard;

    /**
     * Primary constructor for a tile
     *
     * @param tileName tile name
     * @param x tile x pos in array
     * @param y tile y pos in array
     * @param length tile length
     * @param width tile width
     * @param isPassable tile property 1
     * @param hazard tile property 2
     */
    public Tile(String tileName, int x, int y, int length, int width, boolean isPassable, boolean hazard) {
        this.tileName = tileName;
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.isPassable = isPassable;
        this.hazard = hazard;
    }

    /**
     * Get the tile name
     *
     * @return String tile name
     */
    public String getTileName() {
        return tileName;
    }

    /**
     * Set a tiles name
     *
     * @param tileName tile name
     */
    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    /**
     * Get the x pos
     *
     * @return x pos value
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x pos
     *
     * @param x x pos value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y pos
     *
     * @return y pos value
     */
    public int getY() {
        return y;
    }

    /**
     * Get the y pos
     *
     * @param y y pos value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the length
     *
     * @return length value
     */
    public int getLength() {
        return length;
    }

    /**
     * Set the length
     *
     * @param length length value
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Get the width
     *
     * @return width value
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width
     *
     * @param width width value
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Is a tile passable
     *
     * @return true or false
     */
    public boolean isPassable() {
        return isPassable;
    }

    /**
     * Set if a tile is passable
     *
     * @param passable true or false
     */
    public void setPassable(boolean passable) {
        this.isPassable = passable;
    }

    /**
     * Is a tile hazardous
     *
     * @return true or false
     */
    public boolean isHazard() {
        return hazard;
    }

    /**
     * Set if a tile hazardous
     *
     * @param hazard true or false
     */
    public void setHazard(boolean hazard) {
        this.hazard = hazard;
    }

    /**
     * Method to draw a tile at a given position
     *
     * @param g2d Graphics2d object
     */
    public void drawTile(Graphics2D g2d) {
        g2d.drawImage(DrawLevel.assets.get(this.tileName), this.getX(), this.getY(), this.length, this.width, null);

    }

    /**
     * To String method
     *
     * @return String of the object
     */
    public String toString() {
        return "Tile{"
                + "tileName='" + tileName + '\''
                + ", x=" + x
                + ", y=" + y
                + ", length=" + length
                + ", width=" + width
                + ", climbable=" + isPassable
                + ", hazard=" + hazard
                + '}';
    }

    /**
     * Clone method
     *
     * @return clone of tile
     */
    public Tile clone() {
        return new Tile(this.tileName, this.x, this.y, this.length, this.width, this.isPassable, this.hazard);
    }

    /**
     * Equals method
     *
     * @param t tile
     * @return true or false
     */
    public boolean equals(Tile t) {
        return this.tileName.equals(t.getTileName())
                && this.x == t.getX()
                && this.y == t.getY()
                && this.length == t.getLength()
                && this.width == t.getWidth()
                && this.isPassable == t.isPassable()
                && this.hazard == t.isHazard();
    }

}
