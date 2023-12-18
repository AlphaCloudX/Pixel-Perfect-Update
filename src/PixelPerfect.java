
/**
 * Pixel Perfect
 * Main Class that runs all needed elements for the game
 */
import engine.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PixelPerfect extends JFrame {

    //name of the game
    public static final String NAME = "Pixel Perfect";

    /**
     * Main method that runs the game All window parameters and game threads are
     * started in this method
     *
     * @param args
     */
    public static void main(String[] args) {
        //Set the background Image
        URL url = PixelPerfect.class.getResource("game_files/assets/resources/backgrounds/background2.png");
        GamePanel.background = new ImageIcon(url).getImage();

        url = PixelPerfect.class.getResource("game_files/assets/resources/backgrounds/loadingScreen.png");
        GamePanel.loadingScreen = new ImageIcon(url).getImage();

        //Set the default Class path for asset loading
        GamePanel.defaultClassPath = PixelPerfect.class.getClassLoader();

        //Default arguments for setting up a window correctly
        JFrame window = new JFrame();

        //Find the users main monitor and get the width and height of the screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GamePanel.width = gd.getDisplayMode().getWidth();
        GamePanel.height = gd.getDisplayMode().getHeight();

        //Set the window size to the right dimensions
        window.setSize(GamePanel.width, GamePanel.height);

        //If the user exits the program, the Exit On close protocal is used to close the panel
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Prevent the user from resizing the screen, will cause extra problems with drawing the levels
        window.setResizable(false);

        //Set the window name to the game name
        window.setTitle(NAME);

        //Create the game panel
        GamePanel gamePanel = new GamePanel();
        //Add the game panel to the window
        window.add(gamePanel);

        //Make the window visible
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Start game thread
        gamePanel.startGameThread();

        //Path for the title music
        String titleMusic = "game_files/assets/resources/audio/mainTheme.wav";
        String titleSfx = "game_files/assets/resources/audio/Jump.wav";

        //calls the play music method
        GamePanel.initiateMusic(new BufferedInputStream(GamePanel.defaultClassPath.getResourceAsStream(titleMusic)));
        GamePanel.initiateSfx(new BufferedInputStream(GamePanel.defaultClassPath.getResourceAsStream(titleSfx)));
    }
}
