import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFSSolver implements MazeSolver{

  private boolean[][] visited;
  private Stack<Maze.Square> path;
  private Maze.Square start;
  private Maze.Square goal;
  private Maze maze;

  public DFSSolver(Maze maze) {
    this.solveMaze(maze);
  }

  @Override
  public void solveMaze(Maze maze) {
    this.maze = maze;
    visited = new boolean[maze.sizeX][maze.sizeY];
    visited[0][0] = true;
    start = maze.squares[0][0];
    goal = maze.squares[maze.sizeX-1][maze.sizeY-1];
    path = new Stack<>();
    path.push(start);
  }

  @Override
  public void update() {
    List<Maze.Square> moves = getLooking();
    if (moves.size() == 0) {
      path.pop();
    } else {
      Maze.Square next = moves.get((int)(Math.random()*moves.size()));
      path.push(next);
      visited[next.x][next.y] = true;
    }
  }

  @Override
  public boolean[][] getVisited() {
    return visited;
  }

  @Override
  public Maze.Square getCurrent() {
    return path.peek();
  }

  @Override
  public Maze.Square getGoal() {
    return goal;
  }

  @Override
  public List<Maze.Square> getLooking() {
    List<Maze.Square> looking = new ArrayList<>();
    Maze.Square current = path.peek();
    for (int i = 0; i < 4; i++) {
      if (maze.isValidMove(current, i, visited)) {
        looking.add(maze.getSquareAt(current.x, current.y, i));
      }
    }
    return looking;
  }

  @Override
  public boolean isSolved() {
    return path.peek().equals(goal);
  }

  @Override
  public List<Maze.Square> getPath() {
    return path;
  }
}
