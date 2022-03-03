import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Window extends JPanel {

  private int sizeX;
  private int sizeY;
  private Maze maze;

  private int stepX;
  private int stepY;

  private MazeSolver solver;

  public Window(int sizeX, int sizeY, Maze maze, MazeSolver solver) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.maze = maze;
    this.setPreferredSize(new Dimension(sizeX, sizeY));
    JFrame frame = new JFrame();
    frame.setTitle("Maze Generator");
    frame.add(this);
    frame.setResizable(false);
    frame.pack();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    stepX = sizeX/maze.sizeX;
    stepY = sizeY/maze.sizeY;
    this.solver = solver;
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, sizeX, sizeY);
    drawSolver(g);
    drawMaze(g);
  }

  private void drawSolver(Graphics g) {
    drawVisited(g);
    drawPath(g);
    drawLooking(g);
    drawGoal(g);
    drawCurrent(g);
  }

  private void drawPath(Graphics g) {
    List<Maze.Square> path = solver.getPath();
    for (Maze.Square s : path) {
      fillSquareCoords(g, s.x, s.y, new Color(0, 150, 0));
    }
    Maze.Square prev = path.get(0);
    for (Maze.Square s : path) {
      drawLineMid(g, s, prev, Color.BLACK);
      prev = s;
    }
  }

  private void drawLineMid(Graphics g, Maze.Square s1, Maze.Square s2, Color c) {
    g.setColor(c);
    g.drawLine(s1.x*stepX+stepX/2, s1.y*stepY+stepY/2,
               s2.x*stepX+stepX/2, s2.y*stepY+stepY/2);
  }

  private void drawVisited(Graphics g) {
    for (int i = 0; i < maze.sizeX; i++) {
      for (int j = 0; j < maze.sizeY; j++) {
        if (solver.getVisited()[i][j]) {
          fillSquareCoords(g, i, j, new Color(100, 0, 100));
        }
      }
    }
  }

  private void drawLooking(Graphics g) {
    List<Maze.Square> path = solver.getLooking();
    for (Maze.Square s : path) {
      fillSquareCoords(g, s.x, s.y, Color.BLUE);
    }
  }

  private void drawCurrent(Graphics g) {
    fillSquareCoords(g, solver.getCurrent().x, solver.getCurrent().y, Color.RED);
  }

  private void drawGoal(Graphics g) {
    fillSquareCoords(g, solver.getGoal().x, solver.getGoal().y, Color.yellow);
  }

  private void fillSquareCoords(Graphics g, int x, int y, Color c) {
    g.setColor(c);
    g.fillRect(x*stepX, y*stepY, stepX, stepY);
  }

  private void drawMaze(Graphics g) {
    for (int i = 0; i < maze.sizeX; i++) {
      for (int j = 0; j < maze.sizeY; j++) {
        drawSquare(g, maze.squares[i][j]);
      }
    }
  }

  class Point {
    int x, y;
    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public void drawSquare(Graphics g, Maze.Square s) {
    g.setColor(Color.WHITE);
    Point TL = new Point(stepX*s.x, stepY*s.y);
    Point TR = new Point(TL.x+stepX, TL.y);
    Point BL = new Point(TL.x, TL.y+stepY);
    Point BR = new Point(TL.x+stepX, TL.y+stepY);
    if (s.walls[0]) {
      drawLine(g, BL, BR);
    }
    if (s.walls[1]) {
      drawLine(g, BR, TR);
    }
    if (s.walls[2]) {
      drawLine(g, TL, TR);
    }
    if (s.walls[3]) {
      drawLine(g, TL, BL);
    }
  }

  public void drawLine(Graphics g, Point x, Point y) {
    g.drawLine(x.x, x.y, y.x, y.y);
  }

}
