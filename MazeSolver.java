import java.awt.Color;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MazeSolver {

    public static void dfs(Maze maze, MazeDraw drawer, int sleepTime) {
	Stack<int[]> path = new Stack<int[]>();
	int endRow = maze.getSize() - 1;
	int endCol = maze.getSize() - 1;
	Random rand = new Random();

	maze.setVisited(0, 0);
	path.push(new int[]{0, 0});
	drawer.drawCell(0, 0, Color.CYAN);
	drawer.sleep(sleepTime);

	while (!path.isEmpty()) {
	    int[] current = path.peek();
	    int row = current[0];
	    int col = current[1];

	    // check if we reached the end
	    if (row == endRow && col == endCol) {
		tracePath(path, drawer);
		return;
	    }

	    int[][] neighbors = maze.getOpenUnvisitedNeighbors(row, col);

	    if (neighbors.length > 0) {
		// pick random unvisited open neighbor
		int randIndex = rand.nextInt(neighbors.length);
		int nextRow = neighbors[randIndex][0];
		int nextCol = neighbors[randIndex][1];

		maze.setVisited(nextRow, nextCol);
		path.push(new int[]{nextRow, nextCol});
		drawer.drawCell(nextRow, nextCol, Color.CYAN);
		drawer.sleep(sleepTime);
	    } else {
		// backtrack
		path.pop();
		drawer.drawCell(row, col, Color.GRAY);
		drawer.sleep(sleepTime);
	    }
	}
    }

    private static void tracePath(Stack<int[]> path, MazeDraw drawer) {
	// convert stack to array to draw solution path
	int size = path.size();
	int[][] solution = new int[size][2];
	for (int i = size - 1; i >= 0; i--) {
	    solution[i] = path.pop();
	}
	for (int i = 0; i < size; i++) {
	    drawer.drawCell(solution[i][0], solution[i][1], Color.YELLOW);
	    drawer.sleep(30);
	}
    }
    public static void bfs(Maze maze, MazeDraw drawer, int sleepTime) {
	int endRow = maze.getSize() - 1;
	int endCol = maze.getSize() - 1;

	Queue<int[]> queue = new LinkedList<int[]>();
	HashMap<String, int[]> parent = new HashMap<String, int[]>();

	// enqueue start cell
	maze.setVisited(0, 0);
	queue.add(new int[]{0, 0});
	parent.put("0,0", null);
	drawer.drawCell(0, 0, Color.CYAN);
	drawer.sleep(sleepTime);

	while (!queue.isEmpty()) {
	    int[] current = queue.poll();
	    int row = current[0];
	    int col = current[1];

	    drawer.drawCell(row, col, Color.CYAN);
	    drawer.sleep(sleepTime);

	    if (row == endRow && col == endCol) {
		tracePathBFS(parent, endRow, endCol, drawer);
		return;
	    }

	    int[][] neighbors = maze.getOpenUnvisitedNeighbors(row, col);
	    for (int i = 0; i < neighbors.length; i++) {
		int nextRow = neighbors[i][0];
		int nextCol = neighbors[i][1];
		if (!maze.isVisited(nextRow, nextCol)) {
		    maze.setVisited(nextRow, nextCol);
		    queue.add(new int[]{nextRow, nextCol});
		    parent.put(nextRow + "," + nextCol, current);
		}
	    }
	}
    }

    private static void tracePathBFS(HashMap<String, int[]> parent, 
	    int endRow, int endCol, MazeDraw drawer) {
	// trace back from end to start using parent map
	Stack<int[]> path = new Stack<int[]>();
	int[] current = new int[]{endRow, endCol};

	while (current != null) {
	    path.push(current);
	    current = parent.get(current[0] + "," + current[1]);
	}

	while (!path.isEmpty()) {
	    int[] cell = path.pop();
	    drawer.drawCell(cell[0], cell[1], Color.YELLOW);
	    drawer.sleep(15);
	}
    }
}