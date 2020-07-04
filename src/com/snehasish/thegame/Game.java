package com.snehasish.thegame;

/**
 * @author 114snehasish
 * @Createdon: 04-07-2020 01:28 PM
 */

import com.snehasish.thegame.graphics.ScreenRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * This class is the main class containing the required game loop. It extends Canvas because it defines the main
 * game window and their properties. It implements Runnable as this also acts as the main game Thread.
 */
public class Game extends Canvas implements Runnable {

    //define sie and scale of our game window
    public static final double WIDTH = 300;                         //width of our game window
    public static final double ASPECT_RATIO = 16.0 / 9.0;           //the aspect ratio of our game window
    public static final double HEIGHT = WIDTH / ASPECT_RATIO;       //automatically calculate height based on width and aspect ratio
    public static final int SCALE = 3;                              //scale of our game

    //define the game specific objects
    private Thread gameThread;                                      //the main thread which drives the game loop
    private JFrame gameFrame;                                       //frame that will contain the game window
    private boolean isRunning = false;                              //controls the game's state [running or stopped]
    private int renderChoice = 2;                                   //the choice user has selected. Default to 2[Static TV] in case of invalid choice
    private ScreenRenderer screenRenderer;                          //Custom renderer. replace with your own class for further experiment/enhancement
    private BufferedImage image = new BufferedImage((int) WIDTH,    //Final image that rendered and displayed
            (int) HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    //Game's public constructor
    public Game() {
        System.out.println("Final Width: " + (int) WIDTH * SCALE);
        System.out.println("Final Height: " + (int) HEIGHT * SCALE);
        Dimension dimension = new Dimension((int) WIDTH * SCALE, (int) HEIGHT * SCALE);
        setPreferredSize(dimension);
        screenRenderer = new ScreenRenderer((int) WIDTH, (int) HEIGHT);
        gameFrame = new JFrame();
        gameFrame.setResizable(false);
        gameFrame.setTitle("The Game");
        gameFrame.add(this);                                        //Add the Canvas we just configured to the frame
        gameFrame.pack();                                           //pack the frame with the Canvas
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);                      //position the window in the center
        gameFrame.setVisible(true);
    }

    //method that starts the game
    public synchronized void start(int choice) {
        renderChoice = choice;
        isRunning = true;
        gameThread = new Thread(this, "Game");
        gameThread.start();
    }

    //method that stops the game
    public synchronized void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            update();                                                   //Update the game logic
            render();                                                   //Render the game in the screen
        }
    }

    //execute the game logic. this will be limited to 60 times a second
    private void update() {

    }

    //execute the render pass. the frequency of execution of this method will not be limited
    private void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();            //create the bugger to store the rendered frame
        //If no buffer strategy is created for this component, then create a triple buffer strategy
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        //create a graphics context for draw buffer
        Graphics graphics = bufferStrategy.getDrawGraphics();

        //*************ACTUAL GRAPHICS STAFFS STARTS*****************//
        screenRenderer.clear();
        if (renderChoice == 1)
            screenRenderer.renderRandomColor();
        else if (renderChoice == 2)
            screenRenderer.renderStaticSignal();
        else if (renderChoice == 3)
            screenRenderer.renderBouncyPixel();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screenRenderer.pixels[i];
        }
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        //*************ACTUAL GRAPHICS STAFFS ENDS*******************//

        //dispose the graphics to release resource
        graphics.dispose();

        //show the available buffer
        bufferStrategy.show();
    }
}
