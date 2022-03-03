import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Maze {

  public final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  final int sizeX;
  final int sizeY;
  final Square[][] squares;
  boolean[][] visited;
  Stack<Square> stack;

  class Square {

    int x;
    int y;
    boolean[] walls;

    public Square(int x, int y) {
      this.x = x;
      this.y = y;
      walls = new boolean[4];
      for (int i = 0; i < 4; i++) walls[i] = true;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Square square = (Square) o;
      return x == square.x && y == square.y && Arrays.equals(walls, square.walls);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(x, y);
      result = 31 * result + Arrays.hashCode(walls);
      return result;
    }
  }

  public Maze(int sizeX, int sizeY) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    squares = new Square[sizeX][sizeY];
    for (int i = 0; i < sizeX; i++) {
      for (int j = 0; j < sizeY; j++) {
        squares[i][j] = new Square(i, j);
      }
    }
  }

  public void generateMaze() {
    visited = new boolean[sizeX][sizeY];
    stack = new Stack<>();
    stack.push(squares[0][0]);
    while (!stack.isEmpty()) {
      dfs(stack.peek());
    }
  }

  public List<Integer> shuffle(int num) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      list.add(i);
    }
    Collections.shuffle(list);
    return list;
  }

  public void dfs(Square s) {
    int x = s.x;
    int y = s.y;
    visited[x][y] = true;
    List<Integer> list = shuffle(4);
    for (int i : list) {
      if (isValid(x, y, i)) {
        Square otherSquare = getSquareAt(x, y, i);
        Square thisSquare = squares[x][y];
        thisSquare.walls[i] = false;
        otherSquare.walls[oppositeDirection(i)] = false;
        stack.push(otherSquare);
        return;
      }
    }
    stack.pop();
  }

  public boolean isValid(int x, int y, int direction) {
    try {
      Square other = getSquareAt(x, y, direction);
      return !visited[other.x][other.y];
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public int oppositeDirection(int d) {
    return (d+2)%4;
  }

  public Square getSquareAt(int x, int y, int direction) {
    int newX = x + directions[direction][0];
    int newY = y + directions[direction][1];
    if (clamp(0, sizeX-1, newX) && clamp(0, sizeY-1, newY)) {
      return squares[newX][newY];
    } else {
      throw new IllegalArgumentException("Square is out of bounds");
    }
  }

  public boolean clamp(int lo, int hi, int num) {
    return num >= lo && num <= hi;
  }

  public boolean isValidMove(Maze.Square s, int direction, boolean[][] vis) {
    if (s.walls[direction]) return false;
    int nextX = s.x + directions[direction][0];
    int nextY = s.y + directions[direction][1];
    if (!clamp(0, sizeX-1, nextX)) return false;
    if (!clamp(0, sizeY-1, nextY)) return false;
    if (vis[nextX][nextY]) return false;
    return true;
  }

}
