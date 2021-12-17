package dungeon.dungeonview;

import dungeon.dungeoncontroller.DungeonGuiController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class handles the mouse click events for moving a player in the dungeon.
 */
public class DungeonMouseAdaptor extends MouseAdapter {

  DungeonGuiController mouseListener;

  /**
   * Initializes the gui based controller for listening to mouse events.
   *
   * @param mouseListener gui based controller
   */
  public DungeonMouseAdaptor(DungeonGuiController mouseListener) {
    this.mouseListener = mouseListener;
  }

  /**
   * Overrides the mouseClicked method of the MouseAdaptor class. Converts the raw cell position
   * obtained from the mouse click in the view to an appropriate one and relays it to the graphical
   * controller
   *
   * @param e the mouse event related to clicks
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    mouseListener.handleCellClick((int) Math.floor(e.getY() / 128d),
        (int) Math.floor(e.getX() / 128d));
  }
}
