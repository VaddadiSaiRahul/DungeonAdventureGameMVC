package dungeon.dungeonview;

import dungeon.dungeoncontroller.DungeonGuiController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class implements the key press operations for moving a player, picking up items and shooting
 * an arrow. The corresponding keys for the given operations are {N, S, E, W} for movement, 'P' for
 * pickup and 'S' for shoot.
 */
public class DungeonKeyListener implements KeyListener {

  DungeonGuiController keyListener;
  ReadonlyDungeonModel readOnlyDungeonModel;

  /**
   * Initializes the gui based controller and read only model of the dungeon.
   *
   * @param keyListener          gui based controller for listening key press
   * @param readOnlyDungeonModel read only dungeon model
   */
  public DungeonKeyListener(DungeonGuiController keyListener,
      ReadonlyDungeonModel readOnlyDungeonModel) {
    this.keyListener = keyListener;
    this.readOnlyDungeonModel = readOnlyDungeonModel;
  }

  /**
   * Overrides the keyTyped method of the KeyListener interface.
   *
   * @param e key event
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  /**
   * Overrides the keyPressed method of the KeyListener interface. Handles key operations such as
   * moving UP ('N'), DOWN ('S'), LEFT ('W'), RIGHT ('E') using the arrow key press. Handles the
   * shoot operations through 'S'/'s' key press and pickup items operations through 'P'/'p' key
   * press.
   *
   * @param e key event
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keyListener.handleKeyPress("N");
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keyListener.handleKeyPress("S");
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keyListener.handleKeyPress("W");
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keyListener.handleKeyPress("E");
    } else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
      if (readOnlyDungeonModel.getPlayer().getCurrentLocation().getOtyugh() != null
          || readOnlyDungeonModel.getPlayer()
          .getCurrentLocation().equals(readOnlyDungeonModel.getEndState())) {
        // do nothing because game ends
      } else {
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        Object[] inputFields = {"distance", textField1, "direction -> 'S', 'W', 'N', 'E'",
            textField2};
        int option = JOptionPane.showConfirmDialog(null, inputFields, "Shoot",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (option == 0) {
          keyListener.handleShootKeyPress(Integer.parseInt(textField1.getText()),
              textField2.getText());
        }
      }
    } else if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
      if (readOnlyDungeonModel.getPlayer().getCurrentLocation().getOtyugh() != null
          || readOnlyDungeonModel.getPlayer()
          .getCurrentLocation().equals(readOnlyDungeonModel.getEndState())) {
        // do nothing because game ends
      } else {
        JTextField item = new JTextField();
        Object[] inputFields = {
            "Type -> 'A' for Arrow, 'R' for Ruby, 'D' for Diamond, 'S' for Sapphire", item};
        int option = JOptionPane.showConfirmDialog(null, inputFields, "Item",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (option == 0) {
          keyListener.handlePickupKeyPress(item.getText());
        }
      }
    }
  }

  /**
   * Overrides the keyReleased method of the KeyListener interface.
   *
   * @param e key event
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // do nothing
  }
}
