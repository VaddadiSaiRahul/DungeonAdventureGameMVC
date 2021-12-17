package dungeon.dungeonview;

import dungeon.location.Location;
import dungeon.player.Player;
import java.util.List;

/**
 * Represents the read only model of the dungeon. It contains only accessor (non-mutator) methods to
 * be used by the view.
 */
public interface ReadonlyDungeonModel {

  /**
   * Gets the current state of the dungeon - each location's characteristics such as cave position,
   * set of treasures the cave possesses and the list of tunnels attached to it.
   *
   * @return String representation of current state of the dungeon
   */
  String getState();

  /**
   * Description of the player which includes player's name and treasures collected until now. It
   * throws an illegal state exception if the enterPlayer method hasn't been invoked first because
   * the player won't be created.
   *
   * @return name of the player and treasures collected
   */
  String getPlayerDescription();

  /**
   * Provides a description of the player that includes his name and a description of what treasure
   * the player has collected.
   *
   * @return string representation of the player's description
   */
  String getPlayerLocationDescription();

  /**
   * Get the solution of Breadth-First Search (BFS) graph traversal from start state of the dungeon
   * to end state.
   *
   * @return solution of BFS traversal
   */
  Location getStartState();

  /**
   * Get the start state i.e. starting location of the dungeon.
   *
   * @return start location of the dungeon
   */
  Location getEndState();

  /**
   * Get the end state i.e. ending location of the dungeon.
   *
   * @return end location of the dungeon
   */
  List<Location> getBFSTraversal();

  /**
   * Get the location grid of dungeon.
   *
   * @return location grid of dungeon
   */
  Location[][] getLocationGrid();

  /**
   * Get the number of rows in dungeon.
   *
   * @return number of rows in dungeon
   */
  int getRows();

  /**
   * Get the number of columns in dungeon.
   *
   * @return number of columns in dungeon
   */
  int getColumns();

  /**
   * Get the dungeon's degree of interconnectivity.
   *
   * @return degree of interconnectivity
   */
  int getInterconnectivity();

  /**
   * Get the player in the dungeon.
   *
   * @return player in the dungeon
   */
  Player getPlayer();

  /**
   * Gets the total count of treasures in the dungeon.
   *
   * @return total count of treasures in the dungeon
   */
  int getTreasureCount();

  /**
   * Gets the total count of arrows in the dungeon.
   *
   * @return total count of arrows in the dungeon
   */
  int getArrowCount();

  /**
   * Returns a boolean value indicating if a dungeon is wrapping or not.
   *
   * @return true if non-wrapping else false
   */
  boolean isNonWrapping();

  /**
   * Returns the percentage of treasures and arrows combined in the dungeon.
   *
   * @return percentage of items (treasures and arrows)
   */
  int getPercentageOfItems();

  /**
   * Returns the number of Otyughs in the dungeon.
   *
   * @return number of Otyughs in the dungeon.
   */
  int getOtyughCount();
}

