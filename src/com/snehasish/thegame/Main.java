package com.snehasish.thegame;

/**
 * @author 114snehasish
 * @Createdon: 04-07-2020 02:33 PM
 */

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is the entry class. the class with the main method. It gets an instance of the Game class and runs it.
 */
public class Main {
    public static void main(String[] args) {
        int choice = 2;
        try {
            choice = getUserChoice();
        } catch (InputMismatchException e) {

        }
        Game theGame = new Game();
        theGame.start(choice);
    }

    private static int getUserChoice() throws InputMismatchException {
        System.out.println("Enter the renderer you want to see:");
        System.out.println("1. Random color");
        System.out.println("2. Static TV signal");
        System.out.println("3. Bouncing pixel");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.close();
        return choice;
    }
}
