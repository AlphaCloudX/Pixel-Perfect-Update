/**
 * David, Michael, Ethan
 * 1/16/2023
 * File for creating button objects with ability for custom images
 */
package mainMenu;

import java.awt.*;
import java.awt.image.BufferedImage;

import util.*;

public class Button {

    //Button x pos
    private int x;
    //Button y pos
    private int y;
    //Button length
    private int length;
    //Button width
    private int width;
    //Title of the button
    private String title;

    //Images used for button
    private BufferedImage image;
    private BufferedImage originalImage;

    /**
     * Constructor for creating a blank button
     *
     * @param x x pos
     * @param y y pos
     * @param length length of button
     * @param width width of button
     */
    public Button(int x, int y, int length, int width) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        title = "";
    }

    /**
     * Constructor for creating a button with a title
     *
     * @param x x pos
     * @param y y pos
     * @param length length of button
     * @param width width of button
     * @param title title of the button
     */
    public Button(int x, int y, int length, int width, String title) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.title = title;
    }

    /**
     * Constructor for creating a button with a title
     *
     * @param x x pos
     * @param y y pos
     * @param length length of button
     * @param width width of button
     * @param title title of the button
     */
    public Button(int x, int y, int length, int width, String title, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.image = image;
        this.originalImage = image;
    }

    /**
     * Set x pos
     *
     * @param x x pos
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y pos
     *
     * @param y y pos
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set the length
     *
     * @param length length of button
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Set the width
     *
     * @param width width of button
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the title text
     *
     * @param t text of title
     */
    public void setTitle(String t) {
        title = t;
    }

    /**
     * Get the x pos
     *
     * @return x pos of button
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y pos
     *
     * @return y pos of button
     */
    public int getY() {
        return y;
    }

    /**
     * Get the length
     *
     * @return length of button
     */
    public int getLength() {
        return length;
    }

    /**
     * Get the width
     *
     * @return width of button
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the title
     *
     * @return title of button
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the image
     *
     * @return image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set the image
     *
     * @param image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Reset the image to the original
     */
    public void resetImage() {
        this.image = this.originalImage;
    }

    /**
     * Draw the button to the panel
     *
     * @param color colour of the button
     * @param g2d Graphics2d object
     */
    public void drawButton(Color color, Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(x, y, length, width);
    }

    /**
     * Draw the text to the button
     *
     * @param color colour of the button
     * @param g2d Graphics2d object
     */
    public void drawText(Color color, Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawString(title, x, y);
    }

    /**
     * Draw outline around the button
     *
     * @param color colour of the button
     * @param g2d Graphics2d object
     */
    public void drawButtonOutline(Color color, Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawRect(x, y, length, width);

    }

    /**
     * Draw outline around the text
     *
     * @param color colour of the button
     * @param g2d Graphics2d object
     */
    public void drawTextOutline(Color color, Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawRect(x, y - width, length, width);

    }

    /**
     * Method to know if a mouse is inside a button
     *
     * @param type type of bounder box to check for 0 is rectangle box, 1 is
     * text
     * @return true or false
     */
    public boolean inBound(int type) {
        //its a button
        if (type == 0) {
            return MouseInputs.mouseX > x && MouseInputs.mouseX < x + length && MouseInputs.mouseY > y && MouseInputs.mouseY < y + width;
        } else { // its text
            return MouseInputs.mouseX > x && MouseInputs.mouseX < x + length && MouseInputs.mouseY < y && MouseInputs.mouseY > y - width;
        }

    }

    /**
     * Method to know if a mouse is inside a button, this method is used when
     * the game is being scaled
     *
     * @param type type of bounder box to check for 0 is rectangle box, 1 is
     * text
     * @return true or false
     */
    public boolean inBoundScaled(int type, double xExtra, double yExtra) {
        //its a button
        if (type == 0) {
            return MouseInputs.mouseX > x * xExtra && MouseInputs.mouseX < x * xExtra + length * xExtra && MouseInputs.mouseY > y * yExtra && MouseInputs.mouseY < y * yExtra + width * yExtra;
        } else { // its text
            return MouseInputs.mouseX > x * xExtra && MouseInputs.mouseX < x * xExtra + length * xExtra && MouseInputs.mouseY < y * yExtra && MouseInputs.mouseY > y * yExtra - width * yExtra;
        }

    }

    /**
     * Method to draw the button using a certain image
     *
     * @param g2d the tool used to draw the image
     */
    public void draw(Graphics2D g2d) {
        //Draw the image at x, y, and scale it to length and width
        g2d.drawImage(this.image, x, y, length, width, null);
    }

    /**
     * return all the attributes as string
     *
     * @return
     */
    public String toString() {
        //return string with all attributes with labels
        return "X Pos: " + x
                + "Y Pos: " + y
                + "Length: " + length
                + "Width: " + width
                + "Title: " + title
                + "Pathway: " + this.image;
    }

    /**
     * return an identical button
     *
     * @return a button with same attributes
     */
    public Button Clone() {
        return new Button(x, y, length, width, title, image);
    }

    /**
     * check if two buttons are equal
     *
     * @param b the other button
     * @return the equality between two buttons
     */
    public boolean equals(Button b) {
        //They are considered to be equal if they have the same dimensions, title, and image pathway
        return this.length == b.length
                && this.width == b.width
                && this.title.equals(b.title)
                && this.image.equals(b.image);
    }

}
