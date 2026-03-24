public class Maze {

    private int size; // # x # diemnsion of maze
    private boolean[][][] walls;// [row][col][4] where 0-3 are N, E, S, W
    private boolean[][] visited; // for [row][col]

    public Maze (int size) {
	this.size = size;
	walls = new boolean[size][size][4];
	for (int row = 0; row < size; row++) {
	    for (int col = 0; col < size; col++) {
		for (int w = 0; w < 4; w++) {
		    walls[row][col][w] = true;
		}
	    }
	}
	visited = new boolean[size][size]; //default initialized as false
    }

    public void reset() {
	for (int row = 0; row < size; row++) {
	    for (int col = 0; col < size; col++) {
		visited[row][col] = false;
	    }
	}
    }

    public int getSize() {
	return size;
    }

    public boolean isVisited(int row, int col) {
	return visited[row][col];
    }

    public void setVisited(int row, int col) {
	visited[row][col] = true;
    }

    //helper function for solving maze
    public boolean hasWall(int row, int col, int direction) {
	return walls[row][col][direction];
    }

    //removes the selected wall from a cell along with the corresponding wall
    //    in the adjacent cell.
    public void removeWall(int row, int col, int direction) {
	//[direction] N = 0, E = 1, S = 2, W = 3
	walls[row][col][direction] = false;
	if (direction == 0 && isValid(row - 1, col)) { walls[row - 1][col][2] = false; }
	if (direction == 1 && isValid(row, col + 1)) { walls[row][col + 1][3] = false; }
	if (direction == 2 && isValid(row + 1, col)) { walls[row + 1][col][0] = false; }
	if (direction == 3 && isValid(row, col - 1)) { walls[row][col - 1][1] = false; }
    }

    //returns if a checked cell is valid
    public boolean isValid(int row, int col) {
	if (row < 0 || row > size - 1|| col < 0 || col > size - 1) {
	    return false;
	}
	return true;
    }

    //return int[][] where each entry is {neighborRow, neighborCol, direction}
    public int[][] getNeighbors(int row, int col){
	int[][] neighbors = new int[4][3];// 4 neighbors, with {row, col, dir}
	int count = 0;

	// Direction N = 0, E = 1, S = 2, W = 3
	int[] dirRow = {-1, 0, 1, 0};
	int[] dirCol = {0, 1, 0, -1};

	for (int dir = 0; dir < 4; dir++) {
	    int neighborRow = row + dirRow[dir];
	    int neighborCol = col + dirCol[dir];
	    if (isValid(neighborRow, neighborCol)) {
		neighbors[count][0] = neighborRow;
		neighbors[count][1] = neighborCol;
		neighbors[count][2] = dir;
		count++;
	    }
	}
	int[][] result = new int[count][3];
	for (int i = 0; i < count; i++ ) {
	    result[i] = neighbors[i];
	}
	return result;
    }

    public int[][] getUnvisitedNeighbors(int row, int col){
	int[][] all = getNeighbors(row, col);
	int count = 0;
	for (int i= 0; i < all.length; i++) {
	    if (!isVisited(all[i][0], all[i][1])) {
		count++;
	    }
	}
	int[][] result = new int[count][3];
	int index = 0;
	for (int i = 0; i < all.length; i++ ) {
	    if (!isVisited(all[i][0], all[i][1])) {
		result[index] = all[i];
		index++;
	    }
	}
	return result;
    }
    public int[][] getOpenUnvisitedNeighbors(int row, int col) {
	int[][] all = getNeighbors(row, col);
	int count = 0;
	for (int i = 0; i < all.length; i++) {
	    if (!isVisited(all[i][0], all[i][1]) && !hasWall(row, col, all[i][2])) {
		count++;
	    }
	}
	int[][] result = new int[count][3];
	int index = 0;
	for (int i = 0; i < all.length; i++) {
	    if (!isVisited(all[i][0], all[i][1]) && !hasWall(row, col, all[i][2])) {
		result[index] = all[i];
		index++;
	    }
	}
	return result;
    }
}