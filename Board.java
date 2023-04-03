package minesweeper;

import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int mineCount;
    private boolean[][] mines;
    private int[][] counts;
    private boolean open;

    public Board(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.mines = new boolean[width][height];
        this.counts = new int[width][height];
        this.open = false;
        generateMines();
        generateCounts();
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

    private void generateCounts() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!mines[x][y]) {
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
                    counts[x][y] = count;
                }
            }
        }
    }

    public void print(boolean[][] revealed) {
    	//printAll();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (revealed[x][y]) {
                    if (mines[x][y]) {
                        System.out.print("[*]");
                    } else {
                        int count = counts[x][y];
                        System.out.print("["+count+"]");
                    }
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }  
    
    public void printAll() {
    	if(!open) return;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (true) {
                    if (mines[x][y]) {
                        System.out.print("[*]");
                    } else {
                        int count = counts[x][y];
                        System.out.print("["+count+"]");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();
    }  

    public boolean isZero(int x, int y) {
        return counts[x][y] == 0;
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
    
    public boolean isOpen() {
    	return open;
    }
    
    public void setOpen(boolean bool) {
    	if(bool) {
    		open = true;
    	}else {
    		open = false;
    	}
    }
    
}
