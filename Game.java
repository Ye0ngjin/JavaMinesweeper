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

	// f - 깃발모드 만들기
	// 첫번째로 입력한 곳이 폭탄이면 재배치후 열기
	// 지뢰의 개수와 f의 개수와 열지않은 칸의 개수가 모두 같으면 승리! 표시후 게임 끝내기

	private void playOneTurn() {
		int x = -1;
		int y = -1;

		System.out.println("플래그: "+board.getFlagCount()+", "+"안열린 칸: "+board.getUnrevealedCount()+", "+"지뢰: "+board.getMineCount());
		System.out.println("종료-\"exit\" or \"종료\", 플래그 - \"f\", 배열보기 - \"o\", 배열닫기 - \"c\", 기본모드는 한칸 열기");
		if (board.isFlagMode()) {
			System.out.println("깃발 모드 입니다. 깃발의 위치를 정하세요.");
		}
		System.out.print("x좌표를 입력하세요: ");
		String input = scanner.nextLine().toLowerCase();

		switch (input) {
		case "exit":
		case "종료":
			gameover = true;
			System.out.println("게임을 종료합니다.");
			break;
		case "o":
			board.setOpenMode(true);
			break;
		case "c":
			board.setOpenMode(false);
			break;
		case "":
			System.out.println("공백");
			break;
		case "f":
			if (board.getMineCount() < board.getFlagCount()) {
				break;
			}
			board.setFlagMode(true);
			break;
		default:
			if (!Pattern.matches("[0-9]+", input)) {
				System.out.println("잘못된 입력");
				break;
			}
			while (x < 0 || x >= board.getWidth()) {
				try {
					// System.out.print("x좌표를 입력하세요: ");
					x = Integer.parseInt(input) - 1;
					// System.out.println(x);
					if (x < 0 || x >= board.getWidth()) {
						System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
						input = scanner.nextLine().toLowerCase();
						// System.out.println(input);
					}
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
					input = scanner.nextLine().toLowerCase();
					// System.out.println(input);
					// scanner.next();
				}
			}

			while (y < 0 || y >= board.getHeight()) {
				try {
					System.out.print("y좌표를 입력하세요: ");
					y = scanner.nextInt() - 1;
					if (y < 0 || y >= board.getHeight()) {
						System.out.println("유효하지 않은 좌표입니다. 다시 입력해주세요.");
					}
				} catch (InputMismatchException e) {
					System.out.println("숫자를 입력해주세요. 다시 입력해주세요.");
					scanner.next();
				}
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
			if (board.isFlagMode()) {
				// 여기에 플래그모드가 켜있을때 할 동작을 만들자.
				if (revealed[x][y]) {// 이미 열린 곳일 때
					System.out.println("이미 선택한 칸입니다. 다시 선택해주세요.");
				} else {// 열린 곳이 아닐 때
					board.setFlags(x, y);
					if (board.isFlag(x, y)) {
						if(board.getMineCount() == board.getFlagCount()) {
							board.setFlags(x, y);//플래그 카운트와 지뢰의 갯수가 같을 때, 깃발이 추가되면 취소시키기(이때는 깃발이 제거되는 것만 가능)
							System.out.println("깃발이 지뢰보다 많을 수 없습니다.");
						}else {
							board.addFlagCount();
						}

					} else {
						board.subFlagCount();
					}
				}
				board.setFlagMode(false);
			} else {
				selectCheck(x, y);
			}
			isClear();			
		}
	}

	private void selectCheck(int x, int y) {
		if (revealed[x][y]) {
			System.out.println("이미 선택한 칸입니다. 다시 선택해주세요.");
		} else if (board.isFlag(x, y)) {
			System.out.println("플래그를 선택할 수 없습니다.");
		} else {
			if (board.isMine(x, y)) {
				revealed[x][y] = true;
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
		// 선택한 칸으로 변경
		revealed[x][y] = true;
		board.subUnrevealedCount();
		// System.out.println("selectZero");
		// 선택한 칸이 count가 0이 아니면 종료
		if (!board.isZero(x, y)) {
			// System.out.println("0이 아님");
			return;
		}
		// 선택한 칸을 선택 상태로 변경
		// System.out.println("0 선택");
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
				if (board.isFlag(i, j)) {
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
	
	private void isClear() {
		if(board.getFlagCount() == board.getUnrevealedCount()) {
			board.print(revealed);
			System.out.println("승리!");
			gameover = true;
		}
	}

}