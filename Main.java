package minesweeper;

import java.util.Scanner;
import java.util.InputMismatchException;

/*지뢰찾기 고쳐야 할 부분

처음에 선택한 부분이 지뢰이면 다시 배치하기

모두 여는 키

모두 닫는 키

플래그 키*/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int width = 0;
        int height = 0;
        int mineCount = 0;
        
        System.out.println("지뢰찾기!");
        
        // 가로 크기, 세로 크기, 지뢰 개수 입력 받기
        System.out.print("가로 크기를 설정하세요: ");
        while (width <= 0) {
            try {
                width = sc.nextInt();
                if (width <= 0) {
                    System.out.println("가로 크기는 1 이상이어야 합니다. 다시 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                sc.next();
            }
        }
        
        if (width == 1) {
            System.out.print("세로 크기를 설정하세요: ");
            while (height <= 1) {
                try {
                    height = sc.nextInt();
                    if (height <= 1) {
                        System.out.println("가로의 크기가 1이므로, 세로 크기는 2 이상이어야 합니다. 다시 입력해주세요.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                    sc.next();
                }
            }
        } else {
            System.out.print("세로 크기를 설정하세요: ");
            while (height <= 0) {
                try {
                    height = sc.nextInt();
                    if (height <= 0) {
                        System.out.println("세로 크기는 1 이상이어야 합니다. 다시 입력해주세요.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                    sc.next();
                }
            }
        }
        
        int maxMineCount = width * height - 1;
        System.out.print("지뢰 개수를 설정하세요 (최대 " + maxMineCount + "개): ");
        while (mineCount <= 0 || mineCount >= width * height) {
            try {
                mineCount = sc.nextInt();
                if (mineCount <= 0) {
                    System.out.println("지뢰 개수는 1 이상이어야 합니다. 다시 입력해주세요.");
                } else if (mineCount >= width * height) {
                    System.out.println("지뢰 개수는 가로 세로 칸 수의 곱보다 작아야 합니다. 다시 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                sc.next();
            }
        }

        Board board = new Board(width, height, mineCount);
        Game game = new Game(board);
 
        game.start();
        
        sc.close();
    }
}