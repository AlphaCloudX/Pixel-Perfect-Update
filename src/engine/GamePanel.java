/**
 * Michael, David, Ethan
 * 1/16/2023
 * File that is responsible for running the visuals and physics of the game
 */
package engine;

import engine.game.DrawLevel;
import engine.game.RunningLevel;
import engine.game.util.TileProperties;
import mainMenu.*;

import mainMenu.login.LoginMenu;
import mainMenu.login.ReadLoginInfo;
import tile.Tile;
import util.KeyboardInputs;
import util.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import entity.Player;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GamePanel extends JPanel implements Runnable {

    //Initialize the keyboard and mouse listeners
    KeyboardInputs keyboardInputs = new KeyboardInputs();
    MouseInputs mouseInputs = new MouseInputs();

    //Store the gameThread and Font
    Thread gameThread;

    //keep track of what menu the user is in
    public static int menu = 0;

    //Time tracker for last level
    public static long timer;
    public static long jumpSfxTimer = 0;
    public static boolean startTimer = false;

    //boolean to keep track of if the splashscreen is displayed or not
    public static boolean splashDisplayed = false;

    //Game Tile Sizes
    public static int gameTileSizeX = 64;
    public static int gameTileSizeY = 64;

    //Player Tile Sizes
    public static int playerTileSizeX = 52;
    public static int playerTileSizeY = 52;

    //Width and height of the window, will be dynamic
    public static int width;
    public static int height;

    //Player object when a game is loaded
    public static Player player;

    //variable that contains the background music for game
    public static Clip clip;
    public static Clip sfxClip;

    //Variables used for drawing the level
    public static boolean inGame = true;

    //players speed
    public static int playerSpeed = 4;

    //Achieved Game Level
    public static int gameLevel = 0;

    //Main menu background
    public static Image background;

    //Default path for local files
    public static ClassLoader defaultClassPath;

    //Keep track if player in loading screen
    public static boolean inLoadingScreen = false;

    //HashMap For button assets
    public static HashMap<String, BufferedImage> buttonAssets;

    //Image for loading screen
    public static Image loadingScreen;

    //level to be timed
    public static int timedLevel = 9;

    /**
     * Primary constructor for the gamepanel
     */
    public GamePanel() {
        //Draw images all to screen then draw the screen(everything is drawn at once for the user)
        setDoubleBuffered(true);

        //Add key listeners to listen for mouse and keyboard inputs
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);

        //Load all assets used for menus;
        buttonAssets = new HashMap<>();

        //Load needed assets for the main menus
        try {
            buttonAssets.put("backButton", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/backButton.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buttonAssets.put("optionButton", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/optionButton.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            buttonAssets.put("frostLevel", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/frostLevel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            buttonAssets.put("jungleLevel", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/jungleLevel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            buttonAssets.put("lockedLevel", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/lockedLevel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            buttonAssets.put("mountainLevel", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/mountainLevel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            buttonAssets.put("okay", ImageIO.read(GamePanel.defaultClassPath.getResource("game_files/assets/resources/buttons/okay.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set this window
        setFocusable(true);

    }

    /**
     * Method for starting the game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Thread that handles the game inputs
     */
    @Override
    public void run() {
        //Read the players save data
        ReadLoginInfo.getFile();

        //Variables for game fps
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        //Game thread that is polling for new updates and physics
        while (true) {

            //Update the information from the screen
            update();

            //Draw the screen with updated info
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;

            //Game delay
            int delay = 6;
            sleep = delay - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            //Add delay
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    private boolean inAir;

    /**
     * Update Method handles all the keyboard updates and level gameplay
     * mechanics
     */
    public void update() {

        if (inGame && DrawLevel.levelLoaded) {

            //Check if the player has died or finished
            hazard();
            finish();

            //Update all the events in this update method
            //Up arrow, w key, spacebar
            if (!inAir && KeyboardInputs.keys[38] || KeyboardInputs.keys[87] || KeyboardInputs.keys[32]) {
                jump();
            }

            //Check if they move left or right
            moveLeft();
            moveRight();

            //Run gravity effect so the player is always being pulled down to the ground
            gravity();

        }

        //update the mouse position
        updateMousePos();
    }

    /**
     * Jump Method When a player presses up arrow the player jumps up
     */
    private void jump() {
        //The player is in air
        inAir = true;

        //Set to defaults
        int jumpHeight = 0;

        //if in the options method, sound effects are on, play the sound. and
        //If it has already been 500ms before the last sound played, play the sound 
        if (OptionMenu.sfx && jumpSfxTimer + 500 < System.currentTimeMillis()) {
            sfxClip.start();
            GamePanel.initiateSfx(new BufferedInputStream(GamePanel.defaultClassPath.getResourceAsStream("game_files/assets/resources/audio/Jump.wav")));
            jumpSfxTimer = System.currentTimeMillis();
        }

        //Find what tiles the player is in
        int blockX1 = player.getX() / gameTileSizeX;
        int blockX2 = (player.getX() + playerTileSizeX) / gameTileSizeX;

        //Check if the players Y position will hit the box above them
        //Find the max height by checking what 2 blocks are above, if no blocks above then set the height to 0
        //4x4 search of above and making sure that there actually exist blocks above
        //Prevent Jumping Outside Of Screen
        //Need to add if statement here to prevent out of array errors in the methods below
        if (player.getY() / gameTileSizeY == 0) {

            //if the player has 1 tile before going out of the screen
        } else if (player.getY() / gameTileSizeY == 1) {
            //Check the left and right tiles above the player
            String bottomLeft = RunningLevel.getTileAtPos(blockX1, (player.getY() / gameTileSizeY) - 1).getTileName();
            String bottomRight = RunningLevel.getTileAtPos(blockX2, (player.getY() / gameTileSizeY) - 1).getTileName();

            //If the tiles are passable they can jump.
            if (TileProperties.isPassable(bottomLeft) && TileProperties.isPassable(bottomRight)) {
                //Set the jump height to 1 tile hight
                jumpHeight = gameTileSizeY;
            }

            //They can jump a max height
        } else {

            //Check the 2x2 grid of tiles above the player
            String topLeft = RunningLevel.getTileAtPos(blockX1, (player.getY() / gameTileSizeY) - 2).getTileName();
            String topRight = RunningLevel.getTileAtPos(blockX2, (player.getY() / gameTileSizeY) - 2).getTileName();

            String bottomLeft = RunningLevel.getTileAtPos(blockX1, (player.getY() / gameTileSizeY) - 1).getTileName();
            String bottomRight = RunningLevel.getTileAtPos(blockX2, (player.getY() / gameTileSizeY) - 1).getTileName();

            //Check if a block is above the player
            //if the block above is passable the max they can jump so far is gameTilesizeY
            if (TileProperties.isPassable(bottomLeft) && TileProperties.isPassable(bottomRight)) {
                jumpHeight = gameTileSizeY;
            }

            //Check if already found a height
            if (jumpHeight == gameTileSizeY) {
                //check if the above above block is passable
                if (TileProperties.isPassable(topLeft) && TileProperties.isPassable(topRight)) {
                    //Jump height can now go up again
                    jumpHeight += gameTileSizeY;
                }

            }

        }

        //Run the jump animation
        for (int i = 0; i < jumpHeight; i++) {

            //Allow the player to move left and right when they jump
            moveRight();
            moveLeft();

            //Set the y so the player is going up on the screen
            player.setY(player.getY() - 1);

            //Add a delay
            try {
                Thread.sleep(1);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for checking if the player moves left
     */
    private void moveLeft() {
        //Left key pressed, a key pressed
        if (KeyboardInputs.keys[37] || KeyboardInputs.keys[65]) {

            //If on ground
            if (!inAir) {
                //The tile on the left of them
                Tile t = RunningLevel.getTileAtPos((player.getX() - playerSpeed) / gameTileSizeX, player.getY() / gameTileSizeY);

                //check if the tile on the left is an obstruction so the player can move left
                if (TileProperties.isPassable(t.getTileName())) {
                    //player moves left by player speed
                    player.setX(player.getX() - playerSpeed);

                }

                //PLayer is in the air
            } else {
                //The tile on the left of them
                Tile t = RunningLevel.getTileAtPos((player.getX() - 2) / gameTileSizeX, player.getY() / gameTileSizeY);

                //check if the player can move left
                if (TileProperties.isPassable(t.getTileName())) {
                    //player moves left by air speed
                    player.setX(player.getX() - 2);
                }

            }
        }

    }

    /**
     * Method for checking if the player moves right
     */
    private void moveRight() {
        //Right key pressed
        if (KeyboardInputs.keys[39] || KeyboardInputs.keys[68]) {

            //If on ground
            if (!inAir) {
                //The tile on the right of them
                Tile t = RunningLevel.getTileAtPos((player.getX() + playerTileSizeX + playerSpeed) / gameTileSizeX, player.getY() / gameTileSizeY);

                //check if the tile on the right is an obstruction so the player can move right
                if (TileProperties.isPassable(t.getTileName())) {
                    //player moves right by player speed
                    player.setX(player.getX() + playerSpeed);

                }
                //PLayer is in the air
            } else {
                //The tile on the right of them
                Tile t = RunningLevel.getTileAtPos((player.getX() + playerTileSizeX + 2) / gameTileSizeX, player.getY() / gameTileSizeY);

                //check if the tile on the right is an obstruction so the player can move right
                if (TileProperties.isPassable(t.getTileName())) {
                    //player moves right by air speed
                    player.setX(player.getX() + 2);

                }
            }
        }
    }

    /**
     * Gravity Effect to bring player down when falling off block or after
     * jumping
     */
    public void gravity() {

        //Loop until they hit the ground
        while (TileProperties.isPassable(RunningLevel.getTileAtPos(player.getX() / gameTileSizeX, (player.getY() / gameTileSizeY) + 1).getTileName()) && TileProperties.isPassable(RunningLevel.getTileAtPos((player.getX() + playerTileSizeX) / gameTileSizeX, (player.getY() / gameTileSizeY) + 1).getTileName())) {
            //move down
            player.setY(player.getY() + 1);

            //add animation delay
            try {
                Thread.sleep(1);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //player has landed
        inAir = false;
    }

    /**
     * Method to check if the level ends when the player hits a hazard
     */
    public void hazard() {

        //Find what tiles the player is in
        if (TileProperties.isHazard(RunningLevel.getTileAtPos(player.getX() / gameTileSizeX, (player.getY() / gameTileSizeY)).getTileName()) || TileProperties.isHazard(RunningLevel.getTileAtPos((player.getX() + playerTileSizeX) / gameTileSizeX, (player.getY() / gameTileSizeY)).getTileName())) {
            //Player hit hazard, display end of game menu
            mainMenu.PlayMenu.levelEndDisplay = true;
            mainMenu.PlayMenu.levelEndMessage = "Level Failure";

            //Game over
            inGame = false;

        }
    }

    /**
     * Method to check if the level ends when the player hits the finish marker
     */
    public void finish() {

        //Find what tiles the player is in
        if (RunningLevel.getTileAtPos(player.getX() / gameTileSizeX, (player.getY() / gameTileSizeY)).getTileName().equals("finish") || RunningLevel.getTileAtPos((player.getX() + playerTileSizeX) / gameTileSizeX, player.getY() / gameTileSizeY).getTileName().equals("finish")) {
            //Player hit finish, display end of game menu
            mainMenu.PlayMenu.levelEndDisplay = true;
            mainMenu.PlayMenu.levelEndMessage = "Level Completed";

            //the level to be timed
            if (gameLevel == timedLevel) {
                mainMenu.PlayMenu.levelEndTime = "Time: " + updateTime() + "s";
                //If completed any levels that unlocks powerups, display to user
            } else if (gameLevel == 3) {
                mainMenu.PlayMenu.levelEndTime = "Mod. A Unlocked";
            }

            //Game completed
            inGame = false;

            //If the player completed the current level, +1 to their progression
            if (gameLevel == player.getLevelProgression()) {
                player.setLevelProgression(player.getLevelProgression() + 1);
                mainMenu.LevelMenu.updatePlayer();
            }
        }
    }

    /**
     * Void Method that updates the mouse position in the update method
     */
    public void updateMousePos() {
        MouseInputs.mouseX = MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().getX();
        MouseInputs.mouseY = MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().getY();
    }

    /**
     * Method that draws everything to the screen
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Cast g as graphics 2d
        Graphics2D g2d = (Graphics2D) g;

        //Enable anti aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Enable music
        if (OptionMenu.music && clip != null && menu != 0) {
            clip.start();
            //Loop music
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //Disable music
        } else if (!OptionMenu.music && clip != null && menu != 0) {
            clip.stop();
        }

        //Login Screen
        if (menu == 0) {
            //Draw background
            g2d.drawImage(background, 0, 0, GamePanel.width, GamePanel.height, null);
            //Draw login screen
            LoginMenu.drawLoginMenu(g2d);

            //Main Menu
        } else if (menu == 1) {
            //Draw background
            g2d.drawImage(background, 0, 0, GamePanel.width, GamePanel.height, null);
            //Draw main menu
            MainMenu.drawMainMenu(g2d);

            //Level Selector Menu
        } else if (menu == 2) {
            //Draw background
            g2d.drawImage(background, 0, 0, GamePanel.width, GamePanel.height, null);
            //Draw level menu
            LevelMenu.drawLevelMenu(g2d);

            //Option Menu
        } else if (menu == 3) {
            //Draw background
            g2d.drawImage(background, 0, 0, GamePanel.width, GamePanel.height, null);
            //Draw option menu
            OptionMenu.drawOptionMenu(g2d);

            //Game Play Menu
        } else if (menu == 4) {
            //Display loading screen until all assets loaded and in game
            if (!DrawLevel.levelLoaded && !inLoadingScreen) {
                //Draw Loading Screen
                g2d.drawImage(loadingScreen, 0, 0, GamePanel.width, GamePanel.height, null);

                //Loading screen displayed
                inLoadingScreen = true;

                //Assets loaded
            } else {
                //Draw the level
                DrawLevel.drawLevel(g2d, gameLevel);

                //Start time for the timed level
                if (gameLevel == timedLevel && !startTimer && inGame && DrawLevel.levelLoaded) {
                    timer = System.currentTimeMillis();
                    startTimer = true;
                }
            }

            //Splash screen menu
        } else if (menu == -1) {
            //Draw the splashscreen
            SplashScreenMenu.drawSplashScreen(g2d);

            //Handle 3 second delay
            if (!startTimer) {
                timer = System.currentTimeMillis();
                startTimer = true;
            }
            if (totalDisplayTime() > 3) {
                menu = 1;
                startTimer = false;
                splashDisplayed = true;
            }
        }
        //Remove un-need parts from memory
        g2d.dispose();
    }

    /**
     * Handle output for timer in the game
     *
     * @return double of time to display
     */
    public static double totalDisplayTime() {
        double totalTime;
        totalTime = (System.currentTimeMillis() - timer) / 1000.0;
        return totalTime;
    }

    /**
     * Update the player's time by parsing into double
     *
     * @return returns the time it took this time
     */
    public static double updateTime() {
        double totalTime;
        //Find the total time taken
        totalTime = (System.currentTimeMillis() - timer) / 1000.0;
        System.out.println("Time: " + totalTime);

        //If meet criteria to update score
        if (player.getTime() > totalTime || player.getTime() == -1) {
            player.setTime(totalTime);
            LevelMenu.updatePlayer();
        }
        //reset timer
        startTimer = false;
        return totalTime;

    }

    /**
     * Method to play a .wav file
     *
     * @param music music to play using input stream method with local directory
     */
    public static void playSfx(InputStream music) {
        //attempt to locate audio input stream
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(music)) {

            //play the clip
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to play music during the game
     *
     * @param music music to play using input stream method with local directory
     */
    public static void initiateMusic(InputStream music) {
        //attempt to locate audio input stream
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(music)) {

            //play the clip
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            //Music Audio Control
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-25.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method to init sfx during the game
     *
     * @param music sfx to play using input stream method with local directory
     */
    public static void initiateSfx(InputStream music) {
        //attempt to locate audio input stream
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(music)) {

            //set the clip 
            sfxClip = AudioSystem.getClip();
            sfxClip.open(audioIn);

            //Music Audio Control
            FloatControl gainControl = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
