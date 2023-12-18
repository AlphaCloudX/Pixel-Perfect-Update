/**
 * David, Michael, Ethan
 * 1/16/2023
 * File for creating player objects to store different player
 */
package entity;

import engine.GamePanel;
import engine.game.DrawLevel;

import java.awt.Graphics2D;

public class Player {

    //Attributes 
    private String name;
    private int levelProgression;
    private double time;
    private int x;
    private int y;

    /**
     * Allows the user to construct a player using name, levelProgression, and
     * time
     *
     * @param name the name of player
     * @param levelProgression the levelProgression of player
     * @param time the best time of player for last level
     */
    public Player(String name, int levelProgression, double time) {
        //Set all attributes to their respective parameters
        this.name = name;
        this.levelProgression = levelProgression;
        this.time = time;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Allows the user to construct a player only using name
     *
     * @param name the name of the player
     */
    public Player(String name) {
        //Set all attributes to their respective parameters
        this.name = name;
        levelProgression = 0;
        time = -1;
        x = 0;
        y = 0;
    }

    /**
     * Draw the player on the screen
     *
     * @param g2d the tool used to draw
     */
    public void draw(Graphics2D g2d) {
        //(GamePanel.gameTileSizeX - GamePanel.playerTileSizeX)
        g2d.drawImage(DrawLevel.assets.get("player"), getX() - 3, getY() + (GamePanel.gameTileSizeY - GamePanel.playerTileSizeY), GamePanel.playerTileSizeX, GamePanel.playerTileSizeY, null);
    }

    /**
     * Set the name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the level progression
     *
     * @param i new level progression
     */
    public void setLevelProgression(int i) {
        levelProgression = i;
    }

    /**
     * Set the time of player
     *
     * @param time new best time
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Set the y cord of player
     *
     * @param y new y cord
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set the x cord of player
     *
     * @param x new x cord
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y cord of player
     *
     * @return y val
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the x cord of player
     *
     * @return x val
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the name of player
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the levelProgession of player
     *
     * @return levelProgression
     */
    public int getLevelProgression() {
        return this.levelProgression;
    }

    /**
     * Get the best time of player
     *
     * @return their best time
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Print all attributes as string
     *
     * @return the string
     */
    public String toString() {
        //Return string with all attributes with labels
        return "Name: " + name
                + "Level Progression: " + levelProgression
                + "XPos: " + x
                + "YPos: " + y
                + "Time: " + time;
    }

    /**
     * return a copy of the player
     *
     * @return a new player object that is the same
     */
    public Player clone() {
        //Return a newly constructed player with identical attributes 
        return new Player(name, levelProgression, time);
    }

    /**
     * Check of two players are the same
     *
     * @param p the other player
     * @return whether they are the same or not
     */
    public boolean equals(Player p) {
        //If the players have the same name, levelProgresiion, and time, they are considered the same
        return this.name.equals(p.name)
                && this.levelProgression == p.levelProgression
                && this.time == p.time;
    }

}
