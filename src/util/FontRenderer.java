/**
 * David, Michael, Ethan
 * 1/16/2023
 * File that is responsible for rendering the font 
 */
package util;

import engine.GamePanel;

import java.awt.*;

public class FontRenderer {

    //Default Paths
    private static float fontsize = 15;
    public static String fontPath = "game_files/assets/font/Renogare.ttf";

    /**
     * Method to set the font to a certain one using the path
     *
     * @param g2d Graphics2d Object
     */
    public static void setFont(Graphics2D g2d) {

        try {
            //Create the new font
            Font font = Font.createFont(Font.TRUETYPE_FONT, GamePanel.defaultClassPath.getResourceAsStream(fontPath)).deriveFont(getFontsize());

            //Set the font to the current instance
            g2d.setFont(font);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the current font size
     *
     * @return font size
     */
    public static float getFontsize() {
        return fontsize;
    }

    /**
     * Set the new font size
     *
     * @param fontsize font size
     */
    public static void setFontsize(float fontsize) {
        FontRenderer.fontsize = fontsize;
    }

}
