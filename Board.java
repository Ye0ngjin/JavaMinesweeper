package minesweeper;

import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int mineCount;
    private boolean[][] mines;

    public Board(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.mines = new boolean[width][height];
        generateMines();
    }

    private void generateMines() {
        Random random = new Random();
        int count = 0;
        while (count < mineCount) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (!mines[x][y]) {
                mines[x][y] = true;
                count++;
            }
        }
    }

    public void print() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (mines[x][y]) {
                    System.out.print("*");
                } else {
                    int count = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            int nx = x + dx;
                            int ny = y + dy;
                            if (nx >= 0 && nx < width && ny >= 0 && ny < height && mines[nx][ny]) {
                                count++;
                            }
                        }
                    }
                    System.out.print(count);
                }
            }
            System.out.println();
        }
    }

    public boolean isMine(int x, int y) {
        return mines[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
