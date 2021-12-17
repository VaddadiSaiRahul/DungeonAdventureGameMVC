package dungeon.location;

import dungeon.otyugh.Otyugh;
import dungeon.player.Move;
import dungeon.position.Position;
import dungeon.treasure.Treasure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class implements the functionalities of a cave location. This includes adding treasure to
 * the cave, allowing a player to pickup treasure, adding a tunnel to the cave, getting the possible
 * moves a player can take from the cave, getting the next location a player can be in on taking a
 * move, getting the treasure present in the cave.
 */
public class Cave implements Location {

  private final Position position;
  private final List<Tunnel> tunnel;
  private Otyugh otyugh;
  private int arrows;
  private final List<Treasure> treasure;

  /**
   * Initializes the position, list of tunnels and list of treasures of a cave.
   *
   * @param position position of the cave
   * @param tunnel   list of tunnels connected to the cave
   * @param treasure list of treasures to be added to the cave
   */
  public Cave(Position position, List<Tunnel> tunnel, List<Treasure> treasure) {
    this.tunnel = tunnel;
    this.position = position;
    this.treasure = treasure;
    this.arrows = 0;
    this.otyugh = null;
  }

  @Override
  public void addTreasure(Treasure treasure) {
    this.treasure.add(treasure);
  }

  @Override
  public void addTunnel(Tunnel tunnel) {
    this.tunnel.add(tunnel);
  }

  @Override
  public Location getNextLocation(Move move) {
    for (Tunnel tunnel : tunnel) {
      if (tunnel.getDirection().toString().equals(move.toString())) {
        return tunnel.getDestination();
      }
    }
    throw new IllegalArgumentException("Move: " + move.toString() + " not possible for Cave: (" +
        position.getX() + "," + position.getY() + ")");
  }

  @Override
  public List<Treasure> getTreasure() {
    List<Treasure> treasureList = this.treasure;
    return treasureList;
  }

  @Override
  public Position getPosition() {
    Position position = this.position;
    return position;
  }

  @Override
  public List<Tunnel> getTunnel() {
    List<Tunnel> tunnelList = this.tunnel;
    return tunnelList;
  }

  @Override
  public List<String> getPossibleMoves() {
    List<String> moves = new ArrayList<>();
    for (Tunnel tunnel : this.tunnel) {
      moves.add(tunnel.getDirection().toString());
    }
    return moves;
  }

  @Override
  public List<Treasure> pickupTreasure() {
    List<Treasure> treasures = new ArrayList<>(this.treasure);
    this.treasure.clear();
    return treasures;
  }

  @Override
  public Treasure pickupTreasure(String treasure) {
    if (this.treasure.isEmpty()) {
      throw new IllegalArgumentException("No treasure available");
    }

    for (Treasure treasureTemp : this.treasure) {
      if (treasureTemp.toString().equals(treasure)) {
        this.treasure.remove(treasureTemp);
        return treasureTemp;
      }
    }

    throw new IllegalArgumentException("Item not available");
  }

  @Override
  public void addOtyugh(Otyugh otyugh) {
    if (this.otyugh == null) {
      this.otyugh = otyugh;
    } else {
      throw new IllegalStateException("There's already an otyugh in this cave");
    }
  }

  @Override
  public Otyugh getOtyugh() {
    return this.otyugh;
  }

  // mutable method
  @Override
  public int pickupArrow() {
    if (this.arrows == 0) {
      throw new NoSuchElementException("No arrows available");
    }
    int temp = this.arrows;
    this.arrows = 0;
    return temp;
  }


  @Override
  public void addArrow() {
    this.arrows += 1;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int nearbyOtyughCount = 0;
    int farOtyughCount = 0;

    List<String> currPossibleMoves = this.getPossibleMoves();
    for (String currMove : currPossibleMoves) {
      Location location;

      switch (currMove) {
        case "NORTH":
          location = this.getNextLocation(Move.NORTH);
          break;
        case "SOUTH":
          location = this.getNextLocation(Move.SOUTH);
          break;
        case "EAST":
          location = this.getNextLocation(Move.EAST);
          break;
        case "WEST":
          location = this.getNextLocation(Move.WEST);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + currMove);
      }

      if (location.getOtyugh() != null) {
        nearbyOtyughCount += 1;
      }

      List<String> nextPossibleMoves = location.getPossibleMoves();
      //System.out.println(currPossibleMoves);
      for (String nextMove : nextPossibleMoves) {
        Location nextLocation;

        switch (nextMove) {
          case "NORTH":
            nextLocation = location.getNextLocation(Move.NORTH);
            break;
          case "SOUTH":
            nextLocation = location.getNextLocation(Move.SOUTH);
            break;
          case "EAST":
            nextLocation = location.getNextLocation(Move.EAST);
            break;
          case "WEST":
            nextLocation = location.getNextLocation(Move.WEST);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + currMove);
        }

        if (nextLocation.getOtyugh() != null) {
          farOtyughCount += 1;
        }
      }
    }

    if (farOtyughCount == 1 && nearbyOtyughCount == 0) {
      sb.append("\nA less pungent smell is coming from nearby");
    }

    if (farOtyughCount > 1 || nearbyOtyughCount >= 1) {
      sb.append("\nA terrible smell is coming from nearby");
    }

    sb.append("\nYou are in a cave");

    if (this.treasure.size() > 0 || this.arrows > 0) {
      sb.append("\nYou find");

      if (this.arrows > 0) {
        sb.append(" ").append(this.arrows).append(" Arrow");
      }

      if (this.treasure.size() > 0) {
        Map<String, Integer> treasureCount = new HashMap<>();

        for (Treasure treasure : this.treasure) {
          if (treasureCount.containsKey(treasure.toString())) {
            treasureCount.put(treasure.toString(), treasureCount.get(treasure.toString()) + 1);
          } else {
            treasureCount.put(treasure.toString(), 1);
          }
        }

        for (String treasure : treasureCount.keySet()) {
          if (treasureCount.get(treasure) > 0) {
            sb.append(" ").append(treasureCount.get(treasure)).append(" ").append(treasure);
          }
        }
      }
      sb.append(" here");

    }

    sb.append("\nDoors lead to the").append(possibleMovesHelper());
    return sb.toString();
  }

  @Override
  public String possibleMovesHelper() {
    StringBuilder sb = new StringBuilder();

    List<String> possibleMoves = this.getPossibleMoves();

    for (int i = 0; i < possibleMoves.size(); i++) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append(" ");

      switch (possibleMoves.get(i)) {
        case "NORTH":
          sb.append("N");
          break;
        case "EAST":
          sb.append("E");
          break;
        case "SOUTH":
          sb.append("S");
          break;
        case "WEST":
          sb.append("W");
          break;
        default:
          throw new IllegalArgumentException("Invalid direction");
      }
    }

    return sb.toString();

  }

  @Override
  public void removeOtyugh() {
    this.otyugh = null;
  }

  @Override
  public int getArrowCount() {
    int arrowCount = this.arrows;
    return arrowCount;
  }


}
