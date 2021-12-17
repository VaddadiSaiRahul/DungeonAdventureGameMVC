package dungeon.location;

import dungeon.otyugh.Otyugh;
import dungeon.player.Move;
import dungeon.position.PositionImpl;
import dungeon.treasure.Treasure;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class implements the functionalities of a tunnel present in a cave.
 */
public class Tunnel implements Location {

  private final Move direction;
  private final Location source;
  private final Location destination;
  private int arrows;

  /**
   * Initializes the source location of the tunnel, destination location of the tunnel and the
   * direction of the tunnel.
   *
   * @param source      source location of the tunnel
   * @param destination destination location of the tunnel
   * @param direction   direction of the tunnel, which is the direction from source to destination
   *                    location
   */
  public Tunnel(Location source, Location destination, Move direction) {
    this.source = source;
    this.destination = destination;
    this.direction = direction;
    this.arrows = 0;
  }

  /**
   * Get the destination location of the tunnel.
   *
   * @return destination location of the tunnel
   */
  public Location getDestination() {
    return this.destination;
  }

  /**
   * Get the direction of the tunnel from source location to the destination location.
   *
   * @return direction of the tunnel from source to destination location
   */
  public Move getDirection() {
    return this.direction;
  }

  /**
   * Get the source location of the tunnel.
   *
   * @return source location of the tunnel
   */
  public Location getSource() {
    return this.source;
  }

  @Override
  public String toString() {
    if (this.arrows != 0) {
      return "\nYou picked up " + this.arrows + " arrows\n";
    }
    return "";
  }

  @Override
  public void addTreasure(Treasure treasure) {
    // the reason for throwing an exception is that you cannot add treasure to a tunnel instead,
    // you can add treasure to the cave the tunnel is connected to.
    throw new IllegalArgumentException("Cannot add treasure to cave!!");
  }

  @Override
  public Location getNextLocation(Move move) {
    if (move.toString().equals(direction.toString())) {
      return this.getDestination();
    }
    throw new IllegalArgumentException("Invalid move for Tunnel");
  }

  @Override
  public List<Treasure> getTreasure() {
    // reason for returning null is a tunnel doesn't have a treasure instead the cave which it is
    // connected to has treasure.
    return null;
  }

  @Override
  public PositionImpl getPosition() {
    // reason for returning null is tunnel doesn't have a position instead the cave which it is
    // connected to has a position.
    return null;
  }

  @Override
  public List<Tunnel> getTunnel() {
    List<Tunnel> tunnel = new ArrayList<>();
    tunnel.add(this);
    return tunnel;
  }

  @Override
  public void addTunnel(Tunnel tunnel) {
    // reason for throwing an exception is a tunnel cannot be added to another tunnel.
    throw new IllegalArgumentException("Cannot add a tunnel to another tunnel");
  }

  @Override
  public List<String> getPossibleMoves() {
    List<String> possibleMoves = new ArrayList<>();
    possibleMoves.add(this.destination.toString());
    return possibleMoves;
  }

  @Override
  public List<Treasure> pickupTreasure() {
    // reason for returning null is a tunnel doesn't have any treasure for a player to pick up.
    // instead, the cave which it is connected to allows a player to pickup treasure.
    return null;
  }

  @Override
  public Treasure pickupTreasure(String treasure) {
    throw new IllegalArgumentException("No Treasures in a Tunnel");
  }

  @Override
  public void addOtyugh(Otyugh otyugh) {
    throw new IllegalArgumentException("Cannot add an Otyugh to a tunnel");
  }

  @Override
  public Otyugh getOtyugh() {
    throw new NoSuchElementException("Otyughs don't exist in caves");
  }

  @Override
  public int pickupArrow() {
    int temp = this.arrows;
    this.arrows = 0;
    return temp;
  }

  @Override
  public void addArrow() {
    this.arrows += 1;
  }

  @Override
  public String possibleMovesHelper() {
    return this.direction.toString();
  }

  @Override
  public void removeOtyugh() {
    throw new IllegalStateException("No otyugh in a tunnel");
  }

  @Override
  public int getArrowCount() {
    int arrowCount = this.arrows;
    return arrowCount;
  }


}
