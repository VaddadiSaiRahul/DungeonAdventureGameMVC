package dungeon.location;

import dungeon.otyugh.Otyugh;
import dungeon.player.Move;
import dungeon.position.Position;
import dungeon.treasure.Treasure;
import java.util.List;

/**
 * This interface represents a location in a dungeon. It consists of a cave and a set of tunnels.
 */
public interface Location {

  /**
   * Adds the given treasure to list of treasures present in the current location.
   *
   * @param treasure given treasure
   */
  void addTreasure(Treasure treasure);

  /**
   * Gets the next location from the given location upon taking a move (North, South, West, East).
   *
   * @param move direction to move - North, South, West, East
   * @return the next location
   */
  Location getNextLocation(Move move);

  /**
   * Get the list of treasures present in the current location.
   *
   * @return list of treasures
   */
  List<Treasure> getTreasure();

  /**
   * Get the position of the current location.
   *
   * @return position of the current location
   */
  Position getPosition();

  /**
   * Get the list of tunnels present in the current location.
   *
   * @return list of tunnels present in the current location
   */
  List<Tunnel> getTunnel();

  /**
   * Adds the given tunnel to a list of tunnels present in the current location.
   *
   * @param tunnel the given tunnel to be added in the current location
   */
  void addTunnel(Tunnel tunnel);

  /**
   * Get the list of possible moves from the current location.
   *
   * @return list of possible moves from the current location
   */
  List<String> getPossibleMoves();

  /**
   * Mutable method which assigns the list of treasures present in the current location to the
   * player.
   *
   * @return list of treasures present in the current location
   */
  List<Treasure> pickupTreasure();

  /**
   * Checks the given treasure in the current location and returns it.
   *
   * @param treasure the given treasure to look for
   * @return the treasure in the current location
   */
  Treasure pickupTreasure(String treasure);

  /**
   * Add an Otyugh to the current location.
   */
  void addOtyugh(Otyugh otyugh);

  /**
   * Get the number of otyughs present in the current location.
   *
   * @return the number of otyughs present in the current location.
   */
  Otyugh getOtyugh();

  /**
   * Mutating method which returns all the arrows from the current location to the player and resets
   * the number of arrows to 0.
   *
   * @return the number of arrows present in the current location.
   */
  int pickupArrow();

  /**
   * Adds an arrow to the current location (can be a cave or a tunnel).
   */
  void addArrow();

  /**
   * Gets the stringed representation of possible moves from the current location in a particular
   * format.
   *
   * @return stringed representation of list of possible moves from the current location
   */
  String possibleMovesHelper();

  /**
   * Removes an Otyugh from the current location.
   */
  void removeOtyugh();

  /**
   * Gets the count of arrows from the current location.
   *
   * @return the number of arrows in the current location
   */
  int getArrowCount();
}
