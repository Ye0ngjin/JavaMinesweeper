package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome to Minesweaper!");
        System.out.print("Enter board width: ");
        int width = sc.nextInt();
        
        System.out.print("Enter board height: ");
        int height = sc.nextInt();
        
        System.out.print("Enter mine count: ");
        int mineCount = sc.nextInt();
        
        Board board = new Board(width, height, mineCount);
        Game game = new Game(board);
        
        game.start();
        
        sc.close();
    }
}