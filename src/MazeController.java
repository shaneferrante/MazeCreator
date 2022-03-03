import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class MazeController implements ActionListener {

  public static final int windowX = 800;
  public static final int windowY = 800;
  public static final int sizeX = 40;
  public static final int sizeY = 40;

  public static final int DELAY = 10;

  private Maze m;
  private MazeSolver solver;
  private Window w;
  private Timer t;

  public MazeController() {
    m = new Maze(sizeX, sizeY);
    m.generateMaze();
    solver = new BFSSolver(m);
    w = new Window(windowX, windowY, m, solver);
    t = new Timer(DELAY, this);
    t.start();
  }

  public static void main(String[] args) {
    MazeController mc = new MazeController();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    solver.update();
    w.repaint();
    if (solver.isSolved()) {
      t.stop();
    }
  }
}
