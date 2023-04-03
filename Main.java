package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("지뢰찾기!");
        System.out.print("가로 크기를 설정하세요: ");
        int width = sc.nextInt();
        
        System.out.print("세로 크기를 설정하세요: ");
        int height = sc.nextInt();
        
        System.out.print("지뢰 개수를 설정하세요: ");
        int mineCount = sc.nextInt();
        
        Board board = new Board(width, height, mineCount);
        Game game = new Game(board);
        
        game.start();
        
        sc.close();
    }
}