import java.util.List;

public interface MazeSolver {

  void solveMaze(Maze maze);
  void update();
  boolean[][] getVisited();
  Maze.Square getCurrent();
  Maze.Square getGoal();
  List<Maze.Square> getLooking();
  boolean isSolved();
  List<Maze.Square> getPath();

}
