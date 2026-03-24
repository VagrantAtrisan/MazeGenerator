# MazeGenerator Documentation

## Overview
The MazeGenerator repository contains algorithms to generate and solve mazes. It implements various techniques for both tasks, allowing users to understand the complexities of maze construction and solution finding.

## Maze Generation Algorithms

### 1. Recursive Backtracking
- **Description**: A depth-first search algorithm that recursively visits cells, marking them as part of the maze, and backtracks upon reaching a dead end.
- **Complexity**: O(N) where N is the number of cells in the maze.

### 2. Prim's Algorithm
- **Description**: Treats the maze as a graph, where the cells are nodes and the walls are edges. It randomly selects walls and connects them to the maze until all cells are included.
- **Complexity**: O(E log V), where E is the number of edges and V is the number of vertices.


## Maze Solving Algorithms

### 1. Depth-First Search
- **Description**: Explores the maze systematically until the solution is found. It can be implemented using recursion or a stack.
- **Complexity**: O(N)

### 2. Breadth-First Search
- **Description**: Explores all possible paths level by level. It guarantees the shortest path, if one exists.
- **Complexity**: O(N)


## Conclusion
The MazeGenerator repository provides a robust way to experiment with maze generation and solving techniques. Explore the various algorithms to see the differences in maze structure and solution paths!  
