package minesweeper;

import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int mineCount;
    private int flagCount;
    private int unrevealedCount;
    private boolean[][] mines;
    private int[][] counts;
    private boolean openMode;
    private boolean flagMode;
    private boolean[][] flags;

    public Board(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.flagCount = 0;
        this.unrevealedCount = width*height;
        this.mines = new boolean[width][height];
        this.counts = new int[width][height];
        this.openMode = false;
        this.flagMode = false;
        this.flags = new boolean[width][height];
        generateMines();
        generateCounts();
        generateFlags();
    }

    public void reset() {
    	mines = new boolean[width][height];
    	counts = new int[width][height];
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
    
	private void generateFlags() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				flags[x][y] = false;
			}
		}
	}

    public void print(boolean[][] revealed) {
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
                	if(flags[x][y]) {
                		System.out.print("[F]");
                	}else {
                        System.out.print("[ ]");
                	}

                }
            }
            System.out.println();
        }
    }  
    
    public void printAll() {
    	if(!openMode) return;
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
    
    public int getMineCount() {
    	return mineCount;
    }
    
    public int getFlagCount() {
    	return flagCount;
    }
    
    public int getUnrevealedCount() {
    	return unrevealedCount;
    }
    
    public boolean isOpenMode() {
    	return openMode;
    }
    
    public void setOpenMode(boolean bool) {
    	if(bool) {
    		openMode = true;
    	}else {
    		openMode = false;
    	}
    }
    
    public boolean isFlagMode() {
    	return flagMode;
    }
    
    public void setFlagMode(boolean bool) {
    	if(bool) {
    		flagMode = true;
    	}else {
    		flagMode = false;
    	}
    }
    
    public boolean isFlag(int x, int y) {
        return flags[x][y];
    }
    
	public void setFlags(int x, int y) {
		flags[x][y] = !flags[x][y];
	}
    
	public void addFlagCount() {
		this.flagCount++;
	}
	
	public void subFlagCount() {
		this.flagCount--;
	}
	
	public void subUnrevealedCount() {
		this.unrevealedCount--;
	}
}
