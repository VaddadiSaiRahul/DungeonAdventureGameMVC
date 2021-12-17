package dungeon.dungeoncontroller;

/**
 * This represents a controller interface for handling gui based operations. Operations include
 * handling view level operations like a mouse click for moving the player and key press for picking
 * up items, shooting an arrow and also moving the player through the dungeon.
 */
public interface IDungeonGuiController {

  /**
   * Execute a gui based game of a player navigating through a dungeon through an external user.
   * When the player reaches the end location, the game is over and the playGame method ends. The
   * user can restart or quit the game after this.
   */
  void playGame();

  /**
   * Moves the player to a valid cell, specified by the x and y coordinates of the mouse click given
   * by the user. If it's a valid move, the user advances to the new cell and the view updates else
   * the player doesn't move.
   *
   * @param x x coordinate of the cell
   * @param y y coordinate of the cell
   */
  void handleCellClick(int x, int y);


  /**
   * Moves the player to a valid cell, specified by direction from the key press 'N'(north),
   * 'S'(south), 'E'(east), 'W'(west). A valid move advances the player to the new cell and updates
   * the view else nothing happens.
   *
   * @param direction the direction to move indicated by the key press - 'N', 'S', 'E', 'W'
   */
  void handleKeyPress(String direction);

  /**
   * Shoots an arrow to a specified distance and direction entered from an input prompt text box by
   * the user. The prompt opens when the user presses 'S'.
   *
   * @param distance  number of caves the arrow must travel
   * @param direction the direction - north, south, east or west
   */
  void handleShootKeyPress(int distance, String direction);

  /**
   * Pickup an item specified from the input prompt box entered by the user. The user picks up an
   * arrow if they enter 'A', 'R' for ruby, 'D' for diamond and 'S' for sapphire.
   *
   * @param text the entered option
   */
  void handlePickupKeyPress(String text);

}
