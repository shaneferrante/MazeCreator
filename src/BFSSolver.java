import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSSolver implements MazeSolver {

  private Maze maze;
  private Queue<Maze.Square> looking;
  private Maze.Square[][] from;
  private boolean[][] visited;
  private Maze.Square goal;
  private Maze.Square start;

  public BFSSolver(Maze m) {
    solveMaze(m);
  }

  @Override
  public void solveMaze(Maze maze) {
    this.maze = maze;
    visited = new boolean[maze.sizeX][maze.sizeY];
    looking = new LinkedList<>();
    goal = maze.squares[maze.sizeX-1][maze.sizeY-1];
    start = maze.squares[0][0];
    from = new Maze.Square[maze.sizeX][maze.sizeY];
    addLooking(start);
    visited[0][0] = true;
  }

  private void addLooking(Maze.Square s) {
    List<Integer> l = maze.shuffle(4);
    for (int i : l) {
      if (maze.isValidMove(s, i, visited)) {
        Maze.Square next = maze.getSquareAt(s.x, s.y, i);
        looking.add(next);
        from[next.x][next.y] = s;
      }
    }
  }

  @Override
  public void update() {
    Maze.Square current = looking.poll();
    visited[current.x][current.y] = true;
    addLooking(current);
  }

  @Override
  public boolean[][] getVisited() {
    return visited;
  }

  @Override
  public Maze.Square getCurrent() {
    return looking.peek();
  }

  @Override
  public Maze.Square getGoal() {
    return goal;
  }

  @Override
  public List<Maze.Square> getLooking() {
    return looking.stream().toList();
  }

  @Override
  public boolean isSolved() {
    return getCurrent().equals(goal);
  }

  @Override
  public List<Maze.Square> getPath() {
    Maze.Square current = getCurrent();
    List<Maze.Square> l = new ArrayList<>();
    l.add(current);
    while (!current.equals(maze.squares[0][0])) {
      current = from[current.x][current.y];
      l.add(current);
      if (current == null) break;
    }
    return l;
  }
}
