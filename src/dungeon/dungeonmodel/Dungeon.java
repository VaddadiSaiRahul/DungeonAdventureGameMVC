package dungeon.dungeonmodel;

import dungeon.kruskal.Kruskal;
import dungeon.kruskal.KruskalImpl;
import dungeon.location.Cave;
import dungeon.location.Location;
import dungeon.location.Tunnel;
import dungeon.otyugh.Otyugh;
import dungeon.otyugh.OtyughImpl;
import dungeon.player.Move;
import dungeon.player.Player;
import dungeon.player.PlayerImpl;
import dungeon.position.Position;
import dungeon.position.PositionImpl;
import dungeon.treasure.Diamond;
import dungeon.treasure.Ruby;
import dungeon.treasure.Sapphire;
import dungeon.treasure.Treasure;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

/**
 * This class implements the functionalities of Dungeon model. This includes building a dungeon,
 * getting the state of the dungeon, adding treasure to a specified percentage of caves, entering
 * the player in the dungeon, moving the player, getting the player's description and their location
 * description, and allowing the player to pickup treasure.
 */
public class Dungeon implements DungeonModel {

  private final Location[][] locationGrid;
  private final int rows;
  private final int cols;
  private final boolean isNonWrapping;
  private int percentageItems;
  private int otyughs;
  private final int interconnectivity;
  private Location start;
  private Location end;
  private Player player;

  /**
   * Initializes the bare dungeon with certain characteristics.
   *
   * @param nonWrapping       whether the dungeon is a non-wrapping dungeon or not
   * @param rows              number of rows in the dungeon
   * @param cols              number of columns in the dungeon
   * @param interconnectivity the degree of interconnectivity of dungeon. It's 0 for non-wrapping
   *                          dungeons while > 0 for wrapping dungeons.
   */
  public Dungeon(boolean nonWrapping, int rows, int cols, int interconnectivity) {
    if (rows < 0) {
      throw new IllegalArgumentException("Rows cannot be negative");
    }

    if (cols < 0) {
      throw new IllegalArgumentException("Columns cannot be negative");
    }

    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be negative");
    }

    if (rows < 5 && cols < 5) {
      throw new IllegalArgumentException("Row or column values must be >=5 since the distance "
          + "between start and end location of dungeon must be >=5");
    }

    if (!(nonWrapping)) {
      if (interconnectivity == 0) {
        throw new IllegalArgumentException("Wrapping Dungeon cannot have interconnectivity = 0");
      }
    } else {
      if (interconnectivity > 0) {
        throw new IllegalArgumentException(
            "Non-Wrapping Dungeon cannot have interconnectivity > 0");
      }
    }

    this.rows = rows;
    this.cols = cols;
    this.interconnectivity = interconnectivity;
    this.isNonWrapping = nonWrapping;
    locationGrid = new Location[rows][cols];
    buildDungeon(nonWrapping, interconnectivity);
    generateStartAndEnd(rows, cols);
  }

  /**
   * Generate the start and end location of the dungeon.
   *
   * @param rows number of rows in the dungeon
   * @param cols number of columns in the dungeon
   */
  private void generateStartAndEnd(int rows, int cols) {

    Random random = new Random();

    int x1 = random.nextInt(rows);
    int y1 = random.nextInt(cols);
    int x2 = random.nextInt(rows);
    int y2 = random.nextInt(cols);

    if ((Math.abs(x2 - x1) + Math.abs(y2 - y1)) >= 5) {
      this.start = locationGrid[x1][y1];
      this.end = locationGrid[x2][y2];
    } else {
      this.generateStartAndEnd(rows, cols);
    }
  }

  /**
   * Builds up the dungeon model with the given minimum spanning grid obtained from kruskal's
   * algorithm.
   *
   * @param nonWrapping       whether the given dungeon is non-wrapping or not.
   * @param interconnectivity the degree of interconnectivity for the dungeon
   */
  private void buildDungeon(boolean nonWrapping, int interconnectivity) {

    Kruskal kruskal = new KruskalImpl();

    // generate list of vertices
    List<Position> vertices = kruskal.generateVertices(rows, cols);

    // map vertices to each cell in location grid, initializing the caves
    for (Position p : vertices) {
      locationGrid[p.getX()][p.getY()] = new Cave(new PositionImpl(p.getX(), p.getY()),
          new ArrayList<>(),
          new ArrayList<>());
    }

    // get the minimum span grid from kruskal algorithm.
    Map<Position, List<Position>> minimumSpanGrid = kruskal.getMST(rows, cols, nonWrapping,
        interconnectivity);

    /* from the set of potential edges obtained from minimum span grid above, create a tunnel
       between src to dest and dest to src
    */
    for (Map.Entry<Position, List<Position>> set : minimumSpanGrid.entrySet()) {
      Position src = set.getKey();
      int srcX = src.getX();
      int srcY = src.getY();
      Location srcCave = locationGrid[srcX][srcY];
      for (Position dest : set.getValue()) {
        Move move1 = null;
        Move move2 = null;
        int destX = dest.getX();
        int destY = dest.getY();

        if (srcY == destY) {
          if (srcX - 1 == destX) {
            move1 = Move.NORTH;
            move2 = Move.SOUTH;
          } else if (destX > srcX) {
            if (srcX + 1 == destX) {
              move1 = Move.SOUTH;
              move2 = Move.NORTH;
            } else {
              move1 = Move.NORTH;
              move2 = Move.SOUTH;
            }
          }
        } else if (srcX == destX) {
          if (srcY - 1 == destY) {
            move1 = Move.WEST;
            move2 = Move.EAST;
          } else if (destY > srcY) {
            if (srcY + 1 == destY) {
              move1 = Move.EAST;
              move2 = Move.WEST;
            } else {
              move1 = Move.WEST;
              move2 = Move.EAST;
            }
          }
        }
        /* we maintain 2 moves because, if a tunnel from src to dest is directed south, then a
           tunnel from dest to src directed north also exists!!
         */
        Location destCave = locationGrid[dest.getX()][dest.getY()];
        Tunnel tunnel1 = new Tunnel(srcCave, destCave, move1);
        Tunnel tunnel2 = new Tunnel(destCave, srcCave, move2);
        srcCave.addTunnel(tunnel1);
        destCave.addTunnel(tunnel2);
      }
    }
  }

  @Override
  public String getState() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        sb.append(locationGrid[i][j].toString());
        sb.append("\n");
      }
    }
    return sb.toString();
  }


  @Override
  public void addTreasure(int percentage) {
    if (percentage < 0) {
      throw new IllegalArgumentException("Percentage cannot be negative");
    }
    this.percentageItems = percentage;

    Random random = new Random();
    List<String> randomTreasure = new ArrayList<>();
    randomTreasure.add("Ruby");
    randomTreasure.add("Sapphire");
    randomTreasure.add("Diamond");

    int actualNumber = (percentage * this.rows * this.cols) / 100;

    for (int i = 0; i < actualNumber; i++) {

      int x = random.nextInt(rows);
      int y = random.nextInt(cols);
      int treasureIndex = random.nextInt(randomTreasure.size());
      if (randomTreasure.get(treasureIndex).equals("Ruby")) {
        locationGrid[x][y].addTreasure(new Ruby());
      } else if (randomTreasure.get(treasureIndex).equals("Sapphire")) {
        locationGrid[x][y].addTreasure(new Sapphire());
      } else {
        locationGrid[x][y].addTreasure(new Diamond());
      }

      // deciding between adding to a cave or one of its tunnels
      String[] locationChoice = {"Cave", "Tunnel"};
      int r1 = random.nextInt(locationChoice.length);

      if (locationChoice[r1].equals("Tunnel")) {
        int r2 = random.nextInt(locationGrid[x][y].getTunnel().size());
        locationGrid[x][y].getTunnel().get(r2).addArrow();
      } else {
        locationGrid[x][y].addArrow();
      }
    }
  }

  @Override
  public void addTreasure(Treasure treasure, Location location) {
    location.addTreasure(treasure);
  }

  @Override
  public void addOtyughs(int number) {
    if (number <= 0 || number > this.rows * this.cols) {
      throw new IllegalArgumentException("Number of Otyughs must be > 0 and <= " +
          this.rows * this.cols);
    }

    this.otyughs = number;
    // add an otyugh at the end location
    this.end.addOtyugh(new OtyughImpl());

    Random random = new Random();

    // add to the remaining random locations
    int i = 0;

    while (i < number - 1) {
      int x = random.nextInt(this.rows);
      int y = random.nextInt(this.cols);
      // if not the start location then add
      if (!(x == this.start.getPosition().getX() && y == this.start.getPosition().getY())) {
        Otyugh otyugh = new OtyughImpl();
        try {
          locationGrid[x][y].addOtyugh(otyugh);
        } catch (IllegalStateException ise) {
          continue;
        }
        i += 1;
      }
    }

  }

  @Override
  public void playerPickupItem(String item) {
    try {
      if (item.equalsIgnoreCase("Arrow")) {
        this.player.pickupArrow();
      } else if (item.equalsIgnoreCase("Ruby") || item.equalsIgnoreCase("Diamond")
          || item.equalsIgnoreCase("Sapphire")) {
        this.player.pickupTreasure(item);
      } else {
        throw new IllegalStateException("Invalid Item");
      }
    } catch (NoSuchElementException noSuchElementException) {
      throw new NoSuchElementException("Item not available");
    }
  }

  @Override
  public String playerShootArrow(int distance, String direction) {
    if (distance <= 0) {
      throw new IllegalArgumentException("distance must be > 0");
    }

    Move move = null;

    try {
      switch (direction) {
        case "N" :
          move = Move.NORTH;
          break;
        case "S" :
          move = Move.SOUTH;
          break;
        case "E" :
          move = Move.EAST;
          break;
        case "W" :
          move = Move.WEST;
          break;
        default : throw new IllegalArgumentException("Invalid direction");
      }
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new IllegalArgumentException("Invalid direction");
    }

    try {
      return this.player.shoot(distance, move);
    } catch (IllegalStateException illegalStateException) {
      throw new IllegalStateException("No arrows");
    } catch (NoSuchElementException noSuchElementException) {
      throw new NoSuchElementException("Arrow direction not possible");
    }
  }

  @Override
  public void enterPlayer() {
    this.player = new PlayerImpl("Zeus", this.start);
  }

  @Override
  public String getPlayerDescription() {
    if (this.player == null) {
      throw new IllegalStateException(
          "Player not created!! Please invoke enterPlayer method first");
    }
    return this.player.getDescription();
  }


  @Override
  public String getPlayerLocationDescription() {
    return this.player.getLocationDescription();
  }

  @Override
  public void movePlayer(Location location) {
    this.player.move(location);
  }

  @Override
  public String movePlayer(String direction) {
    try {
      return this.player.move(direction);
    } catch (IllegalStateException illegalStateException) {
      throw new IllegalStateException("Player is dead");
    }
  }

  @Override
  public void playerPickupTreasure() {
    this.player.pickupTreasure();
  }

  @Override
  public List<Location> getBFSTraversal() {

    Queue<Location> queue = new LinkedList<>();
    queue.add(this.start);
    List<Location> visited = new ArrayList<>();

    while (!(queue.isEmpty())) {
      // dequeue parent
      Location parent = queue.remove();
      if (visited.contains(parent)) {
        continue;
      }

      visited.add(parent);

      // check if goal state
      if (parent.equals(this.end)) {
        break;
      }

      // add its children
      List<Tunnel> parentTunnels = parent.getTunnel();
      for (Tunnel parentTunnel : parentTunnels) {
        Location child = parentTunnel.getDestination();
        queue.add(child);
      }
    }

    return visited;
  }

  @Override
  public Location getStartState() {
    Location startLocation = this.start;
    return startLocation;
  }

  @Override
  public Location getEndState() {
    Location endLocation = this.end;
    return endLocation;
  }

  @Override
  public Location[][] getLocationGrid() {
    Location[][] grid = this.locationGrid;
    return grid;
  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getColumns() {
    return this.cols;
  }

  @Override
  public int getInterconnectivity() {
    return this.interconnectivity;
  }

  @Override
  public Player getPlayer() {
    Player player = this.player;
    return player;
  }

  @Override
  public void assignOtyugh(Location location) {
    if (location instanceof Cave) {
      location.addOtyugh(new OtyughImpl());
    }
  }

  @Override
  public int getTreasureCount() {
    int treasureCount = 0;
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        treasureCount += locationGrid[i][j].getTreasure().size();
      }
    }

    return treasureCount;
  }

  @Override
  public int getArrowCount() {
    int arrowCount = 0;
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        arrowCount += locationGrid[i][j].getArrowCount();
        for (Tunnel tunnel : locationGrid[i][j].getTunnel()) {
          arrowCount += tunnel.getArrowCount();
        }
      }
    }
    return arrowCount;
  }


  @Override
  public void addArrow(Location location) {
    location.addArrow();
  }

  @Override
  public boolean isNonWrapping() {
    return this.isNonWrapping;
  }

  @Override
  public int getPercentageOfItems() {
    return this.percentageItems;
  }

  @Override
  public int getOtyughCount() {
    return this.otyughs;
  }

}
