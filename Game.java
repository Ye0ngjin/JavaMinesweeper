package minesweeper;

import java.util.Arrays;
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
            System.out.print("x좌표를 입력하세요: ");
            int x = scanner.nextInt() - 1;
            System.out.print("y좌표를 입력하세요: ");
            int y = scanner.nextInt() - 1;

            if (x < 0 || x >= board.getWidth() || y < 0 || y >= board.getHeight()) {
                System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
            } else if (revealed[x][y]) {
                System.out.println("이미 선택한 칸입니다. 다시 선택해주세요.");
            } else {
                revealed[x][y] = true;
                if (board.isMine(x, y)) {
                    System.out.println("지뢰를 밟았습니다. 게임오버!");
                    gameover = true;
                    // 게임오버 시 revealed 배열을 모두 true로 변경
                    for (int i = 0; i < revealed.length; i++) {
                        Arrays.fill(revealed[i], true);
                    }
                    // 보드 출력
                    board.print(revealed);
                } else {
                    System.out.println("지뢰가 아닙니다.");
                }
            }
        }
        System.out.println("게임이 종료되었습니다.");
    }

}