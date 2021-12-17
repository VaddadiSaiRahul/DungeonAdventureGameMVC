package dungeon.dungeonview;

import dungeon.dungeoncontroller.DungeonGuiController;
import java.io.IOException;

/**
 * Mocks the original view to keep a log of the actions taken in the view.
 */
public class MockView implements DungeonView {

  private Appendable log;

  /**
   * Initializes the log for the gui based dungeon game.
   *
   * @param log actions log
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  /**
   * Logs the mouse click actions taken in the view.
   *
   * @param mouseListener the controller for mouse clicks
   */
  @Override
  public void addClickListener(DungeonGuiController mouseListener) {
    try {
      log.append("click listener for controller added\n");
    } catch (IOException ioException) {
      // do nothing
    }
  }

  /**
   * Logs the key listener event taken in the view.
   *
   * @param keyListener the controller for key listener
   */
  @Override
  public void addKeyListener(DungeonGuiController keyListener) {
    try {
      log.append("key listener for controller added\n");
    } catch (IOException ioException) {
      // do nothing
    }
  }

  /**
   * Logs the times when the JPanel is repainted.
   */
  @Override
  public void refresh() {
    try {
      log.append("refresh\n");
    } catch (IOException ioException) {
      // do nothing
    }
  }

  /**
   * Logs the time when JFrame is displayed.
   */
  @Override
  public void makeVisible() {
    try {
      log.append("gui visible\n");
    } catch (IOException ioException) {
      // do nothing
    }
  }

  /**
   * Logs the key press actions taken in the view.
   *
   * @param result the result to be displayed based on the action
   */
  @Override
  public void displayKeyPress(String result) {
    try {
      log.append("key pressed\n");
    } catch (IOException ioException) {
      // do nothing
    }
  }
}
