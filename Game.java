package minesweeper;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean[][] revealed;
    private boolean gameover;
    private Scanner scanner;

    public Game(Board board) {
        this.board = board;
        this.revealed = new boolean[board.getWidth()][board.getHeight()];
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("게임을 시작합니다.");

        while (!gameover) {
            board.print(revealed);
            playOneTurn();
        }

        System.out.println("게임이 종료되었습니다.");
    }

    private void playOneTurn() {
        int x = -1;
        int y = -1;
        while (x < 0 || x >= board.getWidth()) {
            try {
            	System.out.print("x좌표를 입력하세요: ");
            	x = scanner.nextInt() - 1;;
                if (x < 0 || x >= board.getWidth()) {
                    System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                scanner.next();
            }
        }
        
        while (y < 0 || y >= board.getHeight()) {
            try {
            	System.out.print("y좌표를 입력하세요: ");
            	y = scanner.nextInt() - 1;;
                if ( y < 0 || y >= board.getHeight()) {
                    System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                scanner.next();
            }
        }
        
        selectCheck(x, y);
    }
    
    private void selectCheck(int x, int y) {
        if (revealed[x][y]) {
            System.out.println("이미 선택한 칸입니다. 다시 선택해주세요.");
        } else {
        	//선택한 칸으로 변경
            revealed[x][y] = true;
            if (board.isMine(x, y)) {
                System.out.println("지뢰를 밟았습니다. 게임오버!");
                board.print(revealed);
                gameover = true;
                revealAllTiles();
                System.out.println("--------------------------------");
                board.print(revealed);
            } else {
                System.out.println("지뢰가 아닙니다.");
                selectZero(x, y);
            }
        }
    }

    // 선택한 칸의 좌표와 게임판 배열을 전달받아 해당 칸과 주변 칸을 선택하는 함수
    private void selectZero(int x, int y) {
    	//System.out.println("selectZero");
        // 선택한 칸이 count가 0이 아니면 종료
        if (!board.isZero(x, y)) {
        	revealed[x][y] = true;
        	//System.out.println("0이 아님");
            return;
        }
        // 선택한 칸을 선택 상태로 변경
        //System.out.println("0 선택");
        revealed[x][y] = true;
        // 8방향을 검사하며, count가 0인 칸이 있으면 해당 칸도 선택
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // 배열 범위를 벗어나는 경우는 건너뜀
                if (i < 0 || i >= board.getWidth() || j < 0 || j >= board.getHeight()) {
                    continue;
                }
                // 이미 선택한 칸인 경우는 건너뜀
                if (revealed[i][j]) {
                    continue;
                }
                // 재귀 호출을 사용하여 주변 칸을 선택
                selectZero(i, j);
            }
        }
    }
    
    private void revealAllTiles() {
        // 게임오버 시 revealed 배열을 모두 true로 변경
        for (int i = 0; i < revealed.length; i++) {
            Arrays.fill(revealed[i], true);
        }
    }
    
}