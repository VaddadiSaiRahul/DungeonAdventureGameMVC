package dungeon.player;

import dungeon.location.Location;
import dungeon.treasure.Treasure;
import java.util.List;

/**
 * This interface represents the functionalities of a player.
 */
public interface Player {

  /**
   * Gets the description of the player which includes the player's name and the treasures collected
   * until this point.
   *
   * @return string representation of the player's description
   */
  String getDescription();

  /**
   * Gets the description of the player's current location which includes the current location's
   * characteristics and the set of possible moves from the current location.
   *
   * @return string representation of the description of player's current location
   */
  String getLocationDescription();

  /**
   * Gets the list of treasures a player has collected until the current location.
   *
   * @return string representation of list of treasures the player has collected
   */
  List<Treasure> getTreasure();

  /**
   * Moves the player from their current location to the given location.
   *
   * @param location the given location
   */
  void move(Location location);

  /**
   * Moves the player from the current location in the given direction to the next location.
   * {@throws IllegalArgumentException} if the given direction is invalid {@throws
   * IllegalStateException} if the player is dead after moving to the new location
   *
   * @param direction the given direction
   * @return an empty string or You are in a tunnel, you picked up Arrow
   */
  String move(String direction);

  /**
   * Get the name of the player.
   *
   * @return the name of the player
   */
  String getName();

  /**
   * Adds the treasure from a player's current location to the player's collection of treasures.
   */
  void pickupTreasure();

  /**
   * Allows the player to pick up the given treasure from the current location.
   *
   * @param treasure the given treasure
   */
  void pickupTreasure(String treasure);

  /**
   * Get the current position of the player.
   *
   * @return current position of the player
   */
  Location getCurrentLocation();


  /**
   * Allows the player to pick up an arrow from the current location.
   */
  void pickupArrow();

  /**
   * Shoot an arrow to a given distance and in a given direction. {@throws IllegalStateException} if
   * the number of arrows are 0.
   *
   * @param distance  the given distance i.e. the number of caves
   * @param direction the given direction - NORTH, SOUTH, EAST OR WEST
   * @return an empty string or You shoot an arrow into the darkness, or You killed an Otyugh
   */
  String shoot(int distance, Move direction);

  /**
   * Get the number of arrows from the player's current location.
   *
   * @return the number of arrows from the player's current location
   */
  int getArrows();
}
