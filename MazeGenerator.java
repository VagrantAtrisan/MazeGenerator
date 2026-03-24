import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator{

    public static void binaryTree(Maze maze, MazeDraw drawer, int sleepTime) {
	Random rand = new Random();
	int size = maze.getSize();
	
	for (int row = 0; row < size; row++) {
	    for (int col = 0; col < size; col++) {

		boolean hasNorth = maze.isValid(row - 1, col);
		boolean hasEast= maze.isValid(row, col + 1);
		int coinFlip = rand.nextInt(2);

		if (!hasNorth && !hasEast) {
		    drawer.drawCell(row, col, Color.LIGHT_GRAY);
		    continue;
		} else if (!hasNorth) { //if N blocked, go E
		    maze.removeWall(row, col, 1);
		} else if (!hasEast) { //if E blocked, go N
		    maze.removeWall(row, col, 0);
		} else {
		    maze.removeWall(row, col, coinFlip);
		}
		drawer.drawCell(row, col, Color.LIGHT_GRAY);
		drawer.sleep(sleepTime);
	    }
	}
	drawer.drawStart();
	drawer.drawEnd();
    }

    public static void sidewinder(Maze maze, MazeDraw drawer, int sleepTime) {
	int size = maze.getSize();
	ArrayList<Integer> run = new ArrayList<Integer>();
	Random rand = new Random();
	for (int row = 0; row < size; row++) {
	    run.clear();
	    for (int col = 0; col < size; col ++) {
		boolean hasNorth = maze.isValid(row - 1, col);
		boolean hasEast = maze.isValid(row, col + 1);
		int coinFlip = rand.nextInt(2);

		if (!hasNorth && hasEast) { //top row exception
		    maze.removeWall(row, col, 1);
		    drawer.drawCell(row, col, Color.LIGHT_GRAY);
		    drawer.sleep(sleepTime);
		    continue;
		}
		if (!hasNorth && !hasEast) { //additonal top row exception
		    drawer.drawCell(row, col, Color.LIGHT_GRAY);
		    continue;
		}
		if (!hasEast || coinFlip == 0) { //must carve N from run
		    run.add(col);
		    int randIndex = rand.nextInt(run.size());
		    int carveCol = run.get(randIndex);
		    maze.removeWall(row, carveCol, 0);
		    drawer.drawCell(row, carveCol, Color.LIGHT_GRAY);
		    drawer.sleep(sleepTime);
		    run.clear();

		} else { //continue run E
		    run.add(col);
		    maze.removeWall(row, col, 1);
		}

		drawer.drawCell(row, col, Color.LIGHT_GRAY);
		drawer.sleep(sleepTime);
	    }
	}
	drawer.drawStart();
	drawer.drawEnd();
    }

    public static void recursiveBacktracker(Maze maze, MazeDraw drawer, int sleepTime) {
	Random rand = new Random();
	maze.setVisited(0, 0);
	drawer.drawCell(0, 0, Color.LIGHT_GRAY);
	drawer.sleep(sleepTime);
	recurse(maze, drawer, sleepTime, 0, 0, rand);
	drawer.drawStart();
    }

    private static void recurse(Maze maze, MazeDraw drawer, int sleepTime, int row, int col, Random rand) {
	int[][] neighbors = maze.getUnvisitedNeighbors(row, col);
	while (neighbors.length > 0) {
	    int randIndex = rand.nextInt(neighbors.length);
	    int nextRow = neighbors[randIndex][0];
	    int nextCol = neighbors[randIndex][1];
	    int dir     = neighbors[randIndex][2];

	    if (!maze.isVisited(nextRow, nextCol)) {
		maze.removeWall(row, col, dir);
		maze.setVisited(nextRow, nextCol);
		drawer.drawCell(nextRow, nextCol, Color.LIGHT_GRAY);
		drawer.sleep(sleepTime);
		recurse(maze, drawer, sleepTime, nextRow, nextCol, rand);
	    }
	    neighbors = maze.getUnvisitedNeighbors(row, col);
	}
    }

    public static void huntAndKill(Maze maze, MazeDraw drawer, int sleepTime) {
	Random rand = new Random();
	int size = maze.getSize();

	// start at [0][0]
	int row = 0;
	int col = 0;
	maze.setVisited(row, col);
	drawer.drawCell(row, col, Color.LIGHT_GRAY);
	drawer.sleep(sleepTime);

	while (true) {
	    // KILL phase - walk randomly to unvisited neighbor
	    int[][] unvisited = maze.getUnvisitedNeighbors(row, col);
	    if (unvisited.length > 0) {
		int randIndex = rand.nextInt(unvisited.length);
		int nextRow = unvisited[randIndex][0];
		int nextCol = unvisited[randIndex][1];
		int dir     = unvisited[randIndex][2];
		maze.removeWall(row, col, dir);
		maze.setVisited(nextRow, nextCol);
		drawer.drawCell(row, col, Color.LIGHT_GRAY);
		drawer.drawCell(nextRow, nextCol, Color.LIGHT_GRAY);
		drawer.sleep(sleepTime);

		row = nextRow;
		col = nextCol;
	    } else {
		// HUNT phase - scan for unvisited cell with visited neighbor
		boolean found = false;
		for (int r = 0; r < size && !found; r++) {
		    for (int c = 0; c < size && !found; c++) {
			if (!maze.isVisited(r, c)) {
			    int[][] neighbors = maze.getNeighbors(r, c);
			    for (int i = 0; i < neighbors.length && !found; i++) {
				int nRow = neighbors[i][0];
				int nCol = neighbors[i][1];
				int dir  = neighbors[i][2];
				if (maze.isVisited(nRow, nCol)) {
				    // connect unvisited cell to visited neighbor
				    maze.removeWall(r, c, dir);
				    maze.setVisited(r, c);
				    drawer.drawCell(r, c, Color.LIGHT_GRAY);
				    drawer.sleep(sleepTime);
				    row = r;
				    col = c;
				    found = true;
				}
			    }
			}
		    }
		}
		// if no unvisited cells found, maze is complete
		if (!found) {
		    break;
		}
	    }
	}
    }
    public static void prims(Maze maze, MazeDraw drawer, int sleepTime) {
	Random rand = new Random();

	// frontier list stores {row, col, fromRow, fromCol}
	// tracking where each frontier cell was added from
	ArrayList<int[]> frontier = new ArrayList<int[]>();

	// start at [0][0]
	maze.setVisited(0, 0);
	drawer.drawCell(0, 0, Color.LIGHT_GRAY);
	drawer.sleep(sleepTime);

	// add neighbors of start to frontier
	int[][] startNeighbors = maze.getUnvisitedNeighbors(0, 0);
	for (int i = 0; i < startNeighbors.length; i++) {
	    frontier.add(new int[]{startNeighbors[i][0], startNeighbors[i][1], 0, 0});
	}

	while (frontier.size() > 0) {
	    // pick random frontier cell
	    int randIndex = rand.nextInt(frontier.size());
	    int[] current = frontier.get(randIndex);
	    int row     = current[0];
	    int col     = current[1];
	    int fromRow = current[2];
	    int fromCol = current[3];

	    // remove from frontier
	    frontier.remove(randIndex);

	    // skip if already visited
	    if (maze.isVisited(row, col)) {
		continue;
	    }

	    // connect to visited neighbor it came from
	    int dir = getDirection(fromRow, fromCol, row, col);
	    maze.removeWall(fromRow, fromCol, dir);
	    maze.setVisited(row, col);
	    drawer.drawCell(row, col, Color.LIGHT_GRAY);
	    drawer.drawCell(fromRow, fromCol, Color.LIGHT_GRAY);
	    drawer.sleep(sleepTime);

	    // add unvisited neighbors to frontier
	    int[][] neighbors = maze.getUnvisitedNeighbors(row, col);
	    for (int i = 0; i < neighbors.length; i++) {
		if (!maze.isVisited(neighbors[i][0], neighbors[i][1])) {
		    frontier.add(new int[]{neighbors[i][0], neighbors[i][1], row, col});
		}
	    }
	}
    }

    // helper to get direction from one cell to an adjacent cell
    private static int getDirection(int fromRow, int fromCol, int toRow, int toCol) {
	if (toRow == fromRow - 1) { return 0; } // North
	if (toCol == fromCol + 1) { return 1; } // East
	if (toRow == fromRow + 1) { return 2; } // South
	if (toCol == fromCol - 1) { return 3; } // West
	return -1;
    }
}