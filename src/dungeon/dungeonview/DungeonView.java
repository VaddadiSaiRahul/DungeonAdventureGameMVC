package dungeon.dungeonview;

import dungeon.dungeoncontroller.DungeonGuiController;

/**
 * This represents the view for the gui based dungeon adventure game.
 */
public interface DungeonView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param mouseListener the controller
   */
  void addClickListener(DungeonGuiController mouseListener);

  /**
   * Set up the controller to handle key events in this view.
   *
   * @param keyListener the controller
   */
  void addKeyListener(DungeonGuiController keyListener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Displays the result of the player's action resulting from a key press.
   *
   * @param result the result to be displayed based on the action
   */
  void displayKeyPress(String result);
}
