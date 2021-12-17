package dungeon.dungeonmodel;

import dungeon.dungeonview.ReadonlyDungeonModel;
import dungeon.location.Location;
import dungeon.treasure.Treasure;

/**
 * This interface represents the functionalities of a dungeon model.
 */
public interface DungeonModel extends ReadonlyDungeonModel {

  /**
   * Adds random treasure to a specified percentage of random caves.
   *
   * @param percentage percentage of caves for the random treasure to be added
   */
  void addTreasure(int percentage);

  /**
   * Adds a particular treasure to a specific location in the dungeon.
   * @param treasure the treasure to be added - Ruby, Emerald or Sapphire
   * @param location the location in which treasure is to be added
   */
  void addTreasure(Treasure treasure, Location location);

  /**
   * Creates a new player and assigns them to a random start state.
   */
  void enterPlayer();

  /**
   * Moves the player from the current location to a new location.
   *
   * @param location new location of the player
   */
  void movePlayer(Location location);

  /**
   * Moves the player in the given direction.
   *
   * @param direction the given direction
   * @return an empty string or You are in a tunnel, you picked up Arrow
   */
  String movePlayer(String direction);

  /**
   * Makes the player pickup treasure from the current location.
   */
  void playerPickupTreasure();

  /**
   * Add the given number of otyughs to random locations in the dungeon.
   *
   * @param number the number of otyughs
   */
  void addOtyughs(int number);

  /**
   * Checks if the current item is an arrow or a treasure and allows the player to pick up either
   * arrow or treasure depending on the item. {@throws IllegalStateException} if the item is neither
   * an arrow nor a treasure and {@throws IllegalArgumentException} if the item is not available in
   * the current location.
   *
   * @param item an item which can be an arrow or a treasure
   */
  void playerPickupItem(String item);

  /**
   * Shoots the arrow to the given distance and in the given direction. {@throws
   * IllegalArgumentException} if the distance <= 0 or if the direction given is invalid. {@throws
   * IllegalStateException} if the player has no arrows to shoot and {@throw NoSuchElementException}
   * if the arrow direction is not possible.
   *
   * @param distance  the distance from the current cave
   * @param direction the direction to shoot the arrow - NORTH, SOUTH, EAST OR WEST
   * @return an empty string or You shoot an arrow into the darkness, or You killed an Otyugh
   */
  String playerShootArrow(int distance, String direction);

  /**
   * Adds an Otyugh to the given location {@throws IllegalArgumentException} if an otyugh is added
   * to a tunnel.
   *
   * @param location the given location
   */
  void assignOtyugh(Location location);

  /**
   * Adds an arrow to the specified location in the dungeon.
   * @param location the specified location in the dungeon
   */
  void addArrow(Location location);
}
