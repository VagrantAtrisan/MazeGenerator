import java.awt.Graphics;
import java.awt.Color;

public class MazeDraw {

    private DrawingPanel panel;
    private Graphics g;
    private Maze maze;
    private int cellSize; // gridspace / (Maze size)

    public static final int PANEL_SIZE = 800;
    public static final int BORDER = 40;
    public static final int GRIDSPACE = 720; 

    public MazeDraw (Maze newMaze) {
	maze = newMaze;
	cellSize = GRIDSPACE / maze.getSize();
	panel = new DrawingPanel(PANEL_SIZE , PANEL_SIZE);
	panel.setBackground(Color.WHITE);
	g = panel.getGraphics();

	drawMaze();
	drawStart();
	drawEnd();
    }

    private void drawMaze() {
	int size = maze.getSize();
	for (int row = 0; row < size; row++) {
	    for (int col = 0; col < size; col++) {
		int pixelX = BORDER + (col * cellSize);
		int pixelY = BORDER + (row * cellSize);
		g.setColor(Color.BLACK);
		// Direction N = 0, E = 1, S = 2, W = 3
		if (maze.hasWall(row, col, 0))  { // If North Wall true, draw line
		    g.drawLine(pixelX, pixelY, pixelX + cellSize, pixelY);
		}
		if (maze.hasWall(row, col, 1))  { // If East Wall true, draw line
		    g.drawLine(pixelX+ cellSize, pixelY, pixelX + cellSize, pixelY + cellSize);
		}
		if (maze.hasWall(row, col, 2))  { // If South Wall true, draw line
		    g.drawLine(pixelX+ cellSize, pixelY+ cellSize, pixelX, pixelY + cellSize);
		}
		if (maze.hasWall(row, col, 3))  { // If West Wall true, draw line
		    g.drawLine(pixelX, pixelY+ cellSize, pixelX, pixelY);
		}
	    }
	}
    }

    public void drawCell(int row, int col, Color color) {
	int pixelX = BORDER + (col * cellSize);
	int pixelY = BORDER + (row * cellSize);

	//fill cell
	g.setColor(color);
	g.fillRect(pixelX, pixelY, cellSize, cellSize);

	// Draw walls =
	g.setColor(Color.BLACK);
	if (maze.hasWall(row, col, 0))  { // If North Wall true, draw line
	    g.drawLine(pixelX, pixelY, pixelX + cellSize, pixelY);
	} else { 
	    g.setColor(color);
	    g.drawLine(pixelX + 1, pixelY, pixelX + cellSize - 1, pixelY);
	    g.setColor(Color.BLACK);
	}
	if (maze.hasWall(row, col, 1))  { // If East Wall true, draw line
	    g.drawLine(pixelX + cellSize, pixelY, pixelX + cellSize, pixelY + cellSize);
	} else {
	    g.setColor(color);
	    g.drawLine(pixelX + cellSize, pixelY + 1, pixelX + cellSize, pixelY + cellSize - 1);
	    g.setColor(Color.BLACK);
	}
	if (maze.hasWall(row, col, 2))  { // If South Wall true, draw line
	    g.drawLine(pixelX + cellSize, pixelY+ cellSize, pixelX, pixelY + cellSize);
	} else {
	    g.setColor(color);
	    g.drawLine(pixelX + cellSize - 1, pixelY + cellSize, pixelX + 1, pixelY + cellSize);
	    g.setColor(Color.BLACK);
	}
	if (maze.hasWall(row, col, 3))  { // If West Wall true, draw line
	    g.drawLine(pixelX, pixelY+ cellSize, pixelX, pixelY);
	} else {
	    g.setColor(color);
	    g.drawLine(pixelX, pixelY + cellSize - 1, pixelX, pixelY + 1);
	    g.setColor(Color.BLACK);
	}
    }

    public void drawStart() {
	drawCell(0,0,Color.GREEN);
    }

    public void drawEnd() {
	int end = maze.getSize() - 1;
	drawCell(end, end, Color.RED);
    }

    public void reset() {
	// clear all cells to white
	g.setColor(Color.WHITE);
	g.fillRect(BORDER, BORDER, GRIDSPACE, GRIDSPACE);
	drawMaze();
	drawStart();
	drawEnd();
    }
    public void sleep(int ms) {
	panel.sleep(ms);
    }
}