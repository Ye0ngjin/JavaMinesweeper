package minesweeper;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        	board.printAll();
            board.print(revealed);
            playOneTurn();
        }

        System.out.println("게임이 종료되었습니다.");
    }
    //수정할 것 - 전부 받고 받은 것이 숫자이면 x, y에 넣고
    //아니면 "exit" - 종료, p - 깃발, o - 모두 열기(그냥 배열 출력으로 하자, 치트키 모드? 같은 걸로), c - 원래대로( o 가 켜 있는 상태에서만 동작 )
    //나머지가 입력되면 "다시 입력해주세요" 출력
    private void playOneTurn() {
        int x = -1;
        int y = -1;

//        while (true) {
//            System.out.print("Input: ");
//            String input = scanner.nextLine().toLowerCase();
//
//            if (input.equals("exit") || input.equals("종료")) {
//                System.out.println("게임을 종료합니다.");
//                break;
//            } else if (input.equals("p") || input.equals("o") || input.equals("c")) {
//                System.out.println("입력받은 키: " + input);
//            } else {
//                try {
//                    Integer.parseInt(input);
//                    System.out.println("Valid input: " + input);
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input");
//                }
//            }
//        }

        System.out.println("종료-\"exit\" or \"종료\", 플래그 - \"p\", 배열보기 - \"o\", 배열닫기 - \"c\", 기본모드는 한칸 열기");
        System.out.print("x좌표를 입력하세요: ");
        String input = scanner.nextLine().toLowerCase();

        switch (input) {
        case "exit":
        case "종료":
        	gameover = true;
        	System.out.println("게임을 종료합니다.");
        	break;
        case "p":
        	break;
        case "o":
        	board.setOpen(true);
        	break;
        case "c":
        	board.setOpen(false);
        	break;
        case "":
        	System.out.println("공백");
        	break;
        default:
        	if(!Pattern.matches("[0-9]+",input)) break;
            while (x < 0 || x >= board.getWidth()) {
                try {
                	//System.out.print("x좌표를 입력하세요: ");
                	x = Integer.parseInt(input) - 1;
                	//System.out.println(x);
                    if (x < 0 || x >= board.getWidth()) {
                        System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
                        input = scanner.nextLine().toLowerCase();
                        //System.out.println(input);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                    input = scanner.nextLine().toLowerCase();
                    System.out.println(input);
                    //scanner.next();
                }
            }
            
            while (y < 0 || y >= board.getHeight()) {
                try {
                	System.out.print("y좌표를 입력하세요: ");
                	y = scanner.nextInt() - 1;
                    if ( y < 0 || y >= board.getHeight()) {
                        System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
                    scanner.next();
                }
            }
            if(scanner.hasNextLine()) {
            	scanner.nextLine();
            }
            selectCheck(x, y);
        }

    }
    
    private void selectCheck(int x, int y) {
        if (revealed[x][y]) {
            System.out.println("이미 선택한 칸입니다. 다시 선택해주세요.");
        } else {
        	//선택한 칸으로 변경
            revealed[x][y] = true;
            if (board.isMine(x, y)) {
                System.out.println("지뢰를 밟았습니다. 게임오버!");
                board.printAll();
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