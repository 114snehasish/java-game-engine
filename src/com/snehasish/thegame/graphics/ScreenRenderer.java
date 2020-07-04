package com.snehasish.thegame.graphics;

import java.util.Random;

/**
 * @author 114snehasish
 * @Createdon: 04-07-2020 06:20 PM
 * <p>
 * This Class represents our custom renderer
 */
public class ScreenRenderer {

    private int width;                                                  //width of the rendered screen. This is not equal to the actual game window. this will automatically scale up according to the 'SCALE' variable present in 'Game' class
    private int height;                                                 //height of the rendered screen. This is not equal to the actual game window. this will automatically scale up according to the 'SCALE' variable present in 'Game' class
    public int[] pixels;                                                //store the color information for the array of pixels in the renderer. 1 pixel in the renderer is equal to 9 physical pixels if the 'SCALE' variable is set to 3 in 'Game' class

    //START of list of variables that is used for renderBouncyPixel()
    private int posX = 1;
    private int posY = 1;
    private int drag = 10;
    private int counter = 0;
    private int dirX = 1;
    private int dirY = 1;
    //END of list of variables that is used for renderBouncyPixel()

    //START of list of variables that is used for renderStaticSignal()
    private Random random = new Random();
    //END of list of variables that is used for renderStaticSignal()

    public ScreenRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    //clear the previous frame. Basically color every pixel black
    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = 0x000000;
            }
        }
    }

    //generates a random color for every pixel, creating a static TV signal
    public void renderStaticSignal(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = randomColor();
            }
        }
    }

    //generates a random integer number for a color
    private int randomColor() {
        return random.nextInt(0xffffff+1);
    }

    //pixel bouncy renderer
    public void renderBouncyPixel() {
        checkXBounds();                                         //Check if current X position is out of bounds
        checkYBounds();                                         //Check if current Y position is out of bounds
        move();                                                 //Update the position X and Y to move
        doRender();                                             //Color the pixel of current position
    }

    private void doRender() {
        try {
            pixels[posX + posY * width] = 0xff3691;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("height: " + height);
            System.out.println("width: " + width);
            System.out.println("PosX: " + posX);
            System.out.println("PosY: " + posY);
            System.out.println("DirX: " + dirX);
            System.out.println("DirY: " + dirY);
            System.exit(1);
        }
    }

    //Update the position X and Y to move. The speed is controlled by variable 'drag'. more the drag is, slow the pixel will move.
    private void move() {
        if (counter == drag) {
            //position X and Y will increase or decrease by one depending on the value of direction of X and Y respectively
            //The directions are calculated in checkXBounds() and checkYBounds() respectively
            posX += dirX;
            posY += dirY;
            counter = 0;
        }
        counter++;
    }

    //Check if current Y position is out of bounds
    private void checkYBounds() {
        //if the Y position is more than height or less than 0, then revert the Y direction and set counter value to
        //drag so that the new Y position is updated immediately
        if (posY >= height - 1 || posY <= 0) {
            dirY *= -1;
            counter = drag;
        }
    }

    //Check if current X position is out of bounds
    private void checkXBounds() {
        //if the X position is more than width or less than 0, then revert the X direction and set counter value to
        //drag so that the new X position is updated immediately
        if (posX >= width - 1 || posX <= 0) {
            dirX *= -1;
            counter = drag;
        }
    }

    //render a random color
    public void renderRandomColor() {
        int randomColor=randomColor();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = randomColor;
            }
        }
    }
}
