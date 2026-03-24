import java.util.Scanner;

public class MazeApp {
    public static final int GEN_SLEEP = 1;
    public static final int SOLVE_SLEEP = 5;
    public static final int QUIT = 0;

    public static void main(String[] args) {
	int genChoice, mazeSize, solveChoice;
	printWelcome();
	Scanner console = new Scanner(System.in);

	genChoice = getGenChoice(console);
	while (genChoice != QUIT) {
	    mazeSize = getMazeSize(console);
	    if (mazeSize == QUIT) { break; }

	    solveChoice = getSolveChoice(console);
	    if (solveChoice == QUIT) { break; }

	    Maze maze = new Maze(mazeSize);
	    MazeDraw drawer = new MazeDraw(maze);
	    runGenerator(genChoice, maze, drawer, GEN_SLEEP);
	    maze.reset();
	    drawer.drawStart();
	    drawer.drawEnd();
	    runSolver(solveChoice, maze, drawer, SOLVE_SLEEP);

	    // inner solve loop — re-solve same maze
	    solveChoice = getSolveChoice(console);
	    while (solveChoice != QUIT) {
		maze.reset();
		drawer.reset();
		runSolver(solveChoice, maze, drawer, SOLVE_SLEEP);
		solveChoice = getSolveChoice(console);
	    }

	    genChoice = getGenChoice(console);
	}
	System.out.println("Thanks for using MazeGen & Solve. Goodbye!");
    }

    public static void printWelcome() {
	    System.out.println("========================================================");
	    System.out.println("                    MazeGen & Solve");
	    System.out.println("========================================================");
	    System.out.println("Welcome to the Maze Generator & Solver application!");
	    System.out.println();
	    System.out.println("This program generates and solves mazes using various");
	    System.out.println("algorithms, animating each step of the process.");
	    System.out.println();
	    System.out.println("--------------------------------------------------------");
	    System.out.println("GENERATION ALGORITHMS:");
	    System.out.println("--------------------------------------------------------");
	    System.out.println("  1 - Recursive Backtracker");
	    System.out.println("      Carves deep winding passages using a depth-first");
	    System.out.println("      search. Produces mazes with long corridors and");
	    System.out.println("      few dead ends.");
	    System.out.println();
	    System.out.println("  2 - Prim's Algorithm");
	    System.out.println("      Grows the maze outward from a single point,");
	    System.out.println("      producing mazes with many short dead ends and");
	    System.out.println("      a more organic branching appearance.");
	    System.out.println();
	    System.out.println("  3 - Hunt-and-Kill");
	    System.out.println("      Alternates between random walks and scanning");
	    System.out.println("      for unvisited cells. Produces mazes similar to");
	    System.out.println("      Recursive Backtracker with a different texture.");
	    System.out.println();
	    System.out.println("  4 - Binary Tree");
	    System.out.println("      The simplest generator. Each cell carves either");
	    System.out.println("      North or East, creating a strong diagonal bias");
	    System.out.println("      toward the top-right corner.");
	    System.out.println();
	    System.out.println("  5 - Sidewinder");
	    System.out.println("      Groups cells into horizontal runs before carving");
	    System.out.println("      North. Produces a strong top-row corridor with");
	    System.out.println("      varied passage lengths.");
	    System.out.println();
	    System.out.println("--------------------------------------------------------");
	    System.out.println("SOLVING ALGORITHMS:");
	    System.out.println("--------------------------------------------------------");
	    System.out.println("  1 - Depth-First Search (DFS)");
	    System.out.println("      Explores as far as possible before backtracking.");
	    System.out.println("      Finds a solution but not necessarily the shortest.");
	    System.out.println();
	    System.out.println("  2 - Breadth-First Search (BFS)");
	    System.out.println("      Explores all paths simultaneously level by level.");
	    System.out.println("      Guarantees the shortest possible solution path.");
	    System.out.println();
	    System.out.println("--------------------------------------------------------");
	    System.out.println("CONTROLS:");
	    System.out.println("--------------------------------------------------------");
	    System.out.println("  - Enter 0 at any generation prompt to quit.");
	    System.out.println("  - Enter 0 at the solving prompt to generate a new maze.");
	    System.out.println("  - After solving, you may re-solve the same maze with");
	    System.out.println("    a different algorithm to compare results.");
	    System.out.println("========================================================");
	    System.out.println();
    }

    public static int getGenChoice(Scanner console) {
	System.out.println("Select a Generation Algorithm:");
	System.out.println("  1 - Recursive Backtracker");
	System.out.println("  2 - Prim's Algorithm");
	System.out.println("  3 - Hunt-and-Kill");
	System.out.println("  4 - Binary Tree");
	System.out.println("  5 - Sidewinder");
	System.out.println("  0 - Quit");
	System.out.print("Enter Choice: ");
	int choice = console.nextInt();
	while (choice < 0 || choice > 5) {
	    System.out.println("Invalid choice. Please enter a number between 0 and 5: ");
	    choice = console.nextInt();
	}
	System.out.println();
	return choice;
    }

    public static int getMazeSize(Scanner console) {
	System.out.print("Enter maze size (0 to quit): ");
	int choice = console.nextInt();
	while (choice < 0) {
	    System.out.println("Please enter a positive number: ");
	    choice = console.nextInt();
	}
	System.out.println();
	return choice;
    }

    public static int getSolveChoice(Scanner console) {
	System.out.println("Select a Solving Algorithm:");
	System.out.println("  1 - Depth-First Search");
	System.out.println("  2 - Breadth-First Search");
	System.out.println("  0 - New Maze");
	System.out.print("Enter Choice: ");
	int choice = console.nextInt();
	while (choice < 0 || choice > 2) {
	    System.out.println("Invalid choice. Please enter a number between 0 and 2: ");
	    choice = console.nextInt();
	}
	System.out.println();
	return choice;
    }

    public static void runGenerator(int choice, Maze maze, MazeDraw drawer, int sleepTime) {
	if (choice == 1) {
	    MazeGenerator.recursiveBacktracker(maze, drawer, sleepTime);
	} else if (choice == 2) {
	    MazeGenerator.prims(maze, drawer, sleepTime);
	} else if (choice == 3) {
	    MazeGenerator.huntAndKill(maze, drawer, sleepTime);
	} else if (choice == 4) {
	    MazeGenerator.binaryTree(maze, drawer, sleepTime);
	} else if (choice == 5) {
	    MazeGenerator.sidewinder(maze, drawer, sleepTime);
	}
    }

    public static void runSolver(int choice, Maze maze, MazeDraw drawer, int sleepTime) {
	    if (choice == 1) {
	        MazeSolver.dfs(maze, drawer, sleepTime);
	    } else if (choice == 2) {
	        MazeSolver.bfs(maze, drawer, sleepTime);
	    }
	}
}