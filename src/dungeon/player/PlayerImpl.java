package dungeon.player;

import dungeon.location.Location;
import dungeon.location.Tunnel;
import dungeon.treasure.Treasure;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class implements the functionalities of a Player. This includes moving the player to a given
 * location, picking up treasure from the current location, getting the treasures collected until
 * their current location, getting the current location.
 */
public class PlayerImpl implements Player {

  private final String name;
  private List<Treasure> treasure;
  private Location currLocation;
  private int arrows = 3;

  /**
   * Initializes a player's name, their current location and instantiates a player's treasures.
   *
   * @param name         name of the player
   * @param currLocation the current location of the player i.e. the start state
   */
  public PlayerImpl(String name, Location currLocation) {
    this.name = name;
    this.currLocation = currLocation;
    this.treasure = new ArrayList<>();
  }

  @Override
  public String getDescription() {
    return String.format("Player: %s\nTreasures: %s\nArrows: %s\n", this.name,
        this.treasure.toString(), this.arrows);
  }

  @Override
  public String getLocationDescription() {
    return String.format("%s, Possible moves: %s", currLocation.toString(),
        currLocation.getPossibleMoves());
  }

  @Override
  public List<Treasure> getTreasure() {
    List<Treasure> treasure = new ArrayList<>(this.treasure);
    return treasure;
  }

  @Override
  public void move(Location location) {
    this.currLocation = location;
  }

  @Override
  public String move(String direction) {
    Move move;
    String sb = "";

    switch (direction) {
      case "N" :
        move = Move.NORTH;
        break;
      case "E" :
        move = Move.EAST;
        break;
      case "S" :
        move = Move.SOUTH;
        break;
      case "W" :
        move = Move.WEST;
        break;
      default :
        throw new IllegalArgumentException("Invalid direction");
    }

    List<Tunnel> tunnelList = new ArrayList<>(this.currLocation.getTunnel());
    for (Tunnel tunnel : tunnelList) {
      if (tunnel.getDirection().toString().equals(move.toString())) {
        int tunnelArrows = tunnel.pickupArrow();
        if (tunnelArrows > 0) {
          this.arrows += tunnel.pickupArrow();
          sb = "\nYou are in a tunnel\nYou picked up " + tunnelArrows + " Arrow\n";
          break;
        }
      }
    }

    this.currLocation = this.currLocation.getNextLocation(move);
    if (this.currLocation.getOtyugh() != null) {
      if (!(this.currLocation.getOtyugh().isDead())) {
        if (this.currLocation.getOtyugh().isHit()) {
          if (Math.random() < 0.5) {
            throw new IllegalStateException("Player is dead");
          }
        } else {
          throw new IllegalStateException("Player is dead");
        }
      }
    }

    return sb;

  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void pickupTreasure() {
    this.treasure.addAll(this.currLocation.pickupTreasure());
  }

  @Override
  public void pickupTreasure(String treasure) {
    switch (treasure) {
      case "Ruby" :
        this.treasure.add(this.currLocation.pickupTreasure("Ruby"));
        break;
      case "Diamond" :
        this.treasure.add(this.currLocation.pickupTreasure("Diamond"));
        break;
      case "Sapphire" :
        this.treasure.add(this.currLocation.pickupTreasure("Sapphire"));
        break;
      default :
        throw new IllegalArgumentException("Invalid Treasure");
    }
  }

  @Override
  public Location getCurrentLocation() {
    Location location = this.currLocation;
    return location;
  }


  @Override
  public void pickupArrow() {
    try {
      this.arrows += this.currLocation.pickupArrow();
    } catch (NoSuchElementException noSuchElementException) {
      throw new NoSuchElementException("No arrows available");
    }
  }


  @Override
  public String shoot(int distance, Move direction) {

    if (this.arrows == 0) {
      throw new IllegalStateException("No arrows left");
    }

    int distCount = 0;
    Location nextLocation = this.currLocation;
    this.arrows -= 1;

    while (distCount < distance) {
      if (!(nextLocation.getPossibleMoves().contains(direction.toString()))) {
        return "\nYou shoot an arrow into the darkness\n";
      }

      nextLocation = nextLocation.getNextLocation(direction);
      distCount += 1;
    }

    if (nextLocation.getOtyugh() == null) {
      return "\nYou shoot an arrow into the darkness\n";
    }

    if (!(nextLocation.getOtyugh().isDead())) {
      nextLocation.getOtyugh().updateHit();
    }

    if (nextLocation.getOtyugh().isDead()) {
      nextLocation.removeOtyugh();
      return "\nYou killed an Otyugh\n";
    }
    return "";

  }

  @Override
  public int getArrows() {
    return this.arrows;
  }


}
