package dungeontest.dungeonmodeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import dungeon.dungeonmodel.Dungeon;
import dungeon.dungeonmodel.DungeonModel;
import dungeon.kruskal.Kruskal;
import dungeon.kruskal.KruskalImpl;
import dungeon.location.Location;
import dungeon.location.Tunnel;
import dungeon.player.Move;
import dungeon.position.Position;
import dungeon.position.PositionImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionalities of a dungeon model.
 */
public class DungeonModelTest {

  private Kruskal kruskal;
  private DungeonModel model1;
  private DungeonModel model2;

  /**
   * Instantiate 2 dungeon models - non-wrapping and wrapping and enter the player in the dungeon.
   */
  @Before
  public void setUp() {
    kruskal = new KruskalImpl();
    model1 = new Dungeon(true, 4, 6, 0);
    model2 = new Dungeon(false, 4, 6, 4);
    model1.enterPlayer();
    model2.enterPlayer();
  }

  /**
   * Test if all caves are connected to at most 4 locations.
   */
  @Test
  public void testNumberOfCaveLocations() {
    Location[][] grid = model1.getLocationGrid();
    for (int i = 0; i < model1.getRows(); i++) {
      for (int j = 0; j < model1.getColumns(); j++) {
        assertTrue(grid[i][j].getTunnel().size() <= 4);
      }
    }
  }

  /**
   * Test if there is a path from a cave to every other cave in the dungeon.
   */
  @Test
  public void testPathToCaves() {
    Map<Position, List<Position>> minimumSpanGrid = kruskal.getMST(model1.getRows(),
        model1.getColumns(), true, model1.getInterconnectivity());

    Set<Position> positionSet = new HashSet<>();

    for (Map.Entry<Position, List<Position>> set : minimumSpanGrid.entrySet()) {
      positionSet.add(set.getKey());
      positionSet.addAll(set.getValue());
    }

    assertEquals(model1.getRows() * model1.getColumns(), positionSet.size());
  }

  /**
   * Test if the distance between start and end location is at least 5.
   */
  @Test
  public void testStartAndEndDistance() {
    Position startPos = model1.getStartState().getPosition();
    Position endPos = model1.getEndState().getPosition();
    int distance = Math.abs(endPos.getX() - startPos.getX())
        + Math.abs(endPos.getY() - startPos.getY());
    assertTrue(distance >= 5);
  }

  /**
   * Test if the wrapping dungeon has interconnectivity greater than 0.
   */
  @Test
  public void testWrappingDungeon1() {
    assertTrue(model2.getInterconnectivity() > 0);
  }

  /**
   * Test if an illegal argument exception is thrown if a wrapping dungeons interconnectivity is 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testWrappingDungeon2() {
    DungeonModel model3 = new Dungeon(false, 4, 6, 0);
  }

  /**
   * Test if the wrapping dungeon has correct number of potential edges.
   */
  @Test
  public void testWrappingDungeon3() {
    int size = kruskal.generatePotentialEdges(kruskal.generateVertices(model2.getRows(),
        model2.getColumns()), model2.getRows(), model2.getColumns(), true).size();
    assertTrue(size >= model2.getRows() * model2.getColumns()
        &&
        size <= model2.getRows() * model2.getRows() * model2.getColumns() * model2.getColumns());
  }

  /**
   * Test if the non-wrapping dungeon has interconnectivity = 0.
   */
  @Test
  public void testNonWrappingDungeon1() {
    assertEquals(0, model1.getInterconnectivity());
  }

  /**
   * Test if an illegal argument exception is thrown if a non-wrapping dungeon has interconnectivity
   * greater than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNonWrappingDungeon2() {
    DungeonModel model4 = new Dungeon(true, 4, 6, 5);
  }

  /**
   * Test if the non-wrapping dungeon has correct number of potential edges.
   */
  @Test
  public void testNonWrappingDungeon3() {
    int size = kruskal.generatePotentialEdges(kruskal.generateVertices(model1.getRows(),
        model1.getColumns()), model1.getRows(), model1.getColumns(), true).size();
    assertTrue(size >= 0 && size <= model2.getRows() * model2.getColumns());
  }

  /**
   * Test if the degree of interconnectivity between the wrapping and non-wrapping dungeons are
   * different.
   */
  @Test
  public void testDifferentInterconnectivityDegree() {
    assertNotEquals(model1.getInterconnectivity(), model2.getInterconnectivity());
  }

  /**
   * Test if an illegal argument exception is thrown if interconnectivity is < 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeInterconnectivity() {
    DungeonModel model5 = new Dungeon(true, 5, 5, -2);
  }

  /**
   * Test if an illegal argument exception is thrown if given a negative dungeon row value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRow() {
    DungeonModel model6 = new Dungeon(false, -5, 3, -2);
  }

  /**
   * Test if an illegal argument exception is thrown if given a negative dungeon column value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeColumn() {
    DungeonModel model7 = new Dungeon(false, 5, -3, -2);
  }

  /**
   * Test if the treasure has been added to at least specified percentage of caves.
   */
  @Test
  public void testAddTreasure() {
    model1.addTreasure(25);
    Location[][] grid = model1.getLocationGrid();
    int count = 0;

    for (int i = 0; i < model1.getRows(); i++) {
      for (int j = 0; j < model1.getColumns(); j++) {
        count += grid[i][j].getTreasure().size();
      }
    }

    int percentage = (count * 100 / (model1.getRows() * model1.getColumns()));
    assertEquals(25, percentage);
  }

  /**
   * Test if a cave has none or many treasure.
   */
  @Test
  public void testCaveTreasure() {
    assertTrue(model1.getStartState().getTreasure().size() >= 0);
  }

  /**
   * Test if the player picks up treasure from the current location.
   */
  @Test
  public void testPickupTreasure() {
    model1.addTreasure(80);
    Location[][] grid = model1.getLocationGrid();
    Location location = null;
    int size = 0;
    int flag = 0;

    // find the location which has at least 1 treasure
    for (int i = 0; i < model1.getRows(); i++) {
      for (int j = 0; j < model1.getColumns(); j++) {
        size = grid[i][j].getTreasure().size();
        if (size > 0) {
          location = grid[i][j];
          flag = 1;
          break;
        }
      }
      if (flag == 0) {
        break;
      }
    }

    // player has 0 treasures initially
    assertEquals(0, model1.getPlayer().getTreasure().size());
    // move the player to that location initialized above
    model1.getPlayer().move(location);
    // make the player pickup treasure from that location
    model1.getPlayer().pickupTreasure();
    // now, the location's treasures become empty
    assertEquals(0, location.getTreasure().size());
    // the player's treasures number equals the number of treasures previously present in that
    // location
    assertEquals(size, model1.getPlayer().getTreasure().size());
  }

  /**
   * Test if the player is initially in the start state of the dungeon.
   */
  @Test
  public void initialPlayerLocation() {
    assertEquals(model1.getStartState().toString(),
        model1.getPlayer().getCurrentLocation().toString());
  }

  /**
   * Test if the player moves from their current location to the given location.
   */
  @Test
  public void testPlayerMove() {
    Location location = model1.getLocationGrid()[1][1];
    model1.getPlayer().move(location);
    assertEquals(location.toString(), model1.getPlayer().getCurrentLocation().toString());
  }

  /**
   * Test if the position of the player is valid i.e. within grid boundaries.
   */
  @Test
  public void testValidPlayerPosition() {
    int x = model1.getPlayer().getCurrentLocation().getPosition().getX();
    int y = model1.getPlayer().getCurrentLocation().getPosition().getY();
    assertTrue(x >= 0 && x <= model1.getRows() && y >= 0 && y <= model1.getColumns());
  }

  /**
   * Test to throw an illegal argument exception if given invalid coordinates.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPosition() {
    Position position = new PositionImpl(-3, -2);
  }

  /**
   * Test to check valid position coordinates.
   */
  @Test
  public void testValidPosition() {
    Position position = new PositionImpl(3, 2);
    assertTrue(position.getX() >= 0 && position.getX() < model1.getRows());
    assertTrue(position.getY() >= 0 && position.getY() < model1.getColumns());
  }


  /**
   * Test to check if the start state is within grid bounds.
   */
  @Test
  public void testValidStartState() {
    int startX = model1.getStartState().getPosition().getX();
    int startY = model1.getStartState().getPosition().getY();
    assertTrue(startX >= 0 && startX <= model1.getRows() &&
        startY >= 0 && startY <= model1.getColumns());
  }

  /**
   * Test to check if the end state is within grid bounds.
   */
  @Test
  public void testValidEndState() {
    int startX = model1.getEndState().getPosition().getX();
    int startY = model1.getEndState().getPosition().getY();
    assertTrue(startX >= 0 && startX <= model1.getRows()
        &&
        startY >= 0
        &&
        startY <= model1.getColumns());
  }

  /**
   * Test if the possible moves a player can take from the current location is at most 4.
   */
  @Test
  public void testPossibleMoves() {
    assertTrue(model1.getStartState().getPossibleMoves().size() >= 1
        &&
        model1.getStartState().getPossibleMoves().size() <= 4);
  }

  /**
   * Test if a move is invalid from the current location (start state).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    String[] directions = {"NORTH", "SOUTH", "EAST", "WEST"};
    String invalidDirection = "";
    Move move = null;

    for (String direction : directions) {
      if (!(model1.getStartState().getPossibleMoves().contains(direction))) {
        invalidDirection = direction;
        switch (invalidDirection) {
          case "NORTH" :
            move = Move.NORTH;
            break;
          case "SOUTH" :
            move = Move.SOUTH;
            break;
          case "EAST" :
            move = Move.EAST;
            break;
          case "WEST" :
            move = Move.WEST;
            break;
          default:
            throw new IllegalArgumentException("Invalid Direction");
        }
        break;
      }
    }

    model1.getStartState().getNextLocation(move);
  }

  /**
   * Test if there is always an Otyugh at the end cave.
   */
  @Test
  public void testEndCaveOtyugh() {
    model1.addOtyughs(15);
    assertNotNull(model1.getEndState().getOtyugh());
  }

  /**
   * Test if the start cave does not contain any Otyugh.
   */
  @Test
  public void testStartCaveNoOtyugh() {
    model1.addOtyughs(20);
    assertNull(model1.getStartState().getOtyugh());
  }

  /**
   * Test {@throws IllegalArgumentException} if the number of Otyughs are > product of rows and
   * columns minus 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOtyughs1() {
    model2.addOtyughs(50);
  }

  /**
   * Test {@throws IllegalArgumentException} if the number of Otyughs are < 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOtyughs2() {
    model2.addOtyughs(-2);
  }

  /**
   * Test {@throws IllegalArgumentException} if the number of Otyughs are == 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOtyughs3() {
    model2.addOtyughs(0);
  }

  /**
   * Test if an Otyugh does not occupy tunnel and a NoSuchElementException is thrown when trying to
   * retrieve an Otyugh from a tunnel.
   */
  @Test(expected = NoSuchElementException.class)
  public void testInvalidTunnelOtyugh() {
    model2.addOtyughs(10);
    model2.getStartState().getTunnel().get(0).getOtyugh();
  }

  /**
   * Test if a less pungent smell is detected when there is an Otyugh 2 positions away from the
   * player's current location.
   */
  @Test
  public void testLessPungentSmell() {
    Move move1 = null;
    String direction1 = model1.getStartState().getPossibleMoves().get(0);

    switch (direction1) {
      case "NORTH" :
        move1 = Move.NORTH;
        break;
      case "SOUTH" :
        move1 = Move.SOUTH;
        break;
      case "EAST" :
        move1 = Move.EAST;
        break;
      case "WEST" :
        move1 = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    Move move2 = null;
    String direction2 = model1.getStartState().getNextLocation(move1).getPossibleMoves().get(0);

    switch (direction2) {
      case "NORTH" :
        move2 = Move.NORTH;
        break;
      case "SOUTH" :
        move2 = Move.SOUTH;
        break;
      case "EAST" :
        move2 = Move.EAST;
        break;
      case "WEST" :
        move2 = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    Location location2 = model1.getStartState().getNextLocation(move1).getNextLocation(move2);
    model1.assignOtyugh(location2);

    assertEquals("\nA less pungent smell is coming from nearby\n" +
            "You are in a cave\n" +
            "Doors lead to the" + model1.getStartState().possibleMovesHelper(),
        model1.getStartState().toString());
  }

  /**
   * Test if a pungent smell is detected when there is an Otyugh 1 position away from the player's
   * current location.
   */
  @Test
  public void testMorePungentSmell1() {
    Move move = null;
    String direction = model1.getStartState().getPossibleMoves().get(0);

    switch (direction) {
      case "NORTH" :
        move = Move.NORTH;
        break;
      case "SOUTH" :
        move = Move.SOUTH;
        break;
      case "EAST" :
        move = Move.EAST;
        break;
      case "WEST" :
        move = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    model1.assignOtyugh(model1.getStartState().getNextLocation(move));
    assertEquals("\n"
            + "A terrible smell is coming from nearby\n"
            + "You are in a cave\n"
            + "Doors lead to the" + model1.getStartState().possibleMovesHelper(),
        model1.getStartState().toString());
  }

  /**
   * Test if a pungent smell is detected when there are multiple Otyughs within 2 positions away
   * from the player's current location.
   */
  @Test
  public void testMorePungentSmell2() {
    Move move1 = null;
    String direction1 = model1.getStartState().getPossibleMoves().get(0);

    switch (direction1) {
      case "NORTH" :
        move1 = Move.NORTH;
        break;
      case "SOUTH" :
        move1 = Move.SOUTH;
        break;
      case "EAST" :
        move1 = Move.EAST;
        break;
      case "WEST" :
        move1 = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    Move move2_1 = null;
    Move move2_2 = null;
    String direction2_1 = model1.getStartState().getNextLocation(move1).getPossibleMoves().get(0);
    String direction2_2 = model1.getStartState().getNextLocation(move1).getPossibleMoves().get(1);

    switch (direction2_1) {
      case "NORTH" :
        move2_1 = Move.NORTH;
        break;
      case "SOUTH" :
        move2_1 = Move.SOUTH;
        break;
      case "EAST" :
        move2_1 = Move.EAST;
        break;
      case "WEST" :
        move2_1 = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    switch (direction2_2) {
      case "NORTH" :
        move2_2 = Move.NORTH;
        break;
      case "SOUTH" :
        move2_2 = Move.SOUTH;
        break;
      case "EAST" :
        move2_2 = Move.EAST;
        break;
      case "WEST" :
        move2_2 = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    model1.assignOtyugh(model1.getStartState().getNextLocation(move1).getNextLocation(move2_1));
    model1.assignOtyugh(model1.getStartState().getNextLocation(move1).getNextLocation(move2_2));
    assertEquals("\n"
            + "A terrible smell is coming from nearby\n"
            + "You are in a cave\n"
            + "Doors lead to the" + model1.getStartState().possibleMovesHelper(),
        model1.getStartState().toString());
  }

  /**
   * Test if the player starts with 3 arrows.
   */
  @Test
  public void testArrows1() {
    assertEquals(3, model1.getPlayer().getArrows());
  }

  /**
   * Test if the arrows are present in the same frequency as treasures.
   */
  @Test
  public void testArrowAndTreasureFrequency() {
    assertEquals(model1.getArrowCount(), model1.getTreasureCount());
  }

  /**
   * Test if an arrow travels in a straight line in a cave.
   */
  @Test
  public void testCaveArrow1() {
    for (String direction : model2.getStartState().getPossibleMoves()) {
      Move currMove = null;
      String currDirection = "";

      switch (direction) {
        case "NORTH" :
          currMove = Move.NORTH;
          currDirection = "N";
          break;
        case "SOUTH" :
          currMove = Move.SOUTH;
          currDirection = "S";
          break;
        case "EAST" :
          currMove = Move.EAST;
          currDirection = "E";
          break;
        case "WEST" :
          currMove = Move.WEST;
          currDirection = "W";
          break;
        default:
          throw new IllegalArgumentException("Invalid direction");
      }

      if (model2.getStartState().getNextLocation(currMove).getPossibleMoves().contains(direction)) {
        model2.assignOtyugh(
            model2.getStartState().getNextLocation(currMove).getNextLocation(currMove));
        assertEquals("", model2.playerShootArrow(2, currDirection));
        return;
      }
    }
  }

  /**
   * Test if an arrow is stopped in a cave in which direction is not possible.
   */
  @Test
  public void testCaveArrow2() {
    for (String direction : model2.getStartState().getPossibleMoves()) {
      Move currMove = null;
      String currDirection = "";

      switch (direction) {
        case "NORTH" :
          currMove = Move.NORTH;
          currDirection = "N";
          break;
        case "SOUTH" :
          currMove = Move.SOUTH;
          currDirection = "S";
          break;
        case "EAST" :
          currMove = Move.EAST;
          currDirection = "E";
          break;
        case "WEST" :
          currMove = Move.WEST;
          currDirection = "W";
          break;
        default:
          throw new IllegalArgumentException("Invalid direction");
      }

      if (!(model2.getStartState().getNextLocation(currMove).getPossibleMoves()
          .contains(direction))) {
        assertEquals("\nYou shoot an arrow into the darkness\n",
            model2.playerShootArrow(2, currDirection));
      }
    }
  }

  /**
   * Test if the player kills an Otyugh when shot with an arrow 2 times.
   */
  @Test
  public void testKillOtyugh() {
    Move move = null;
    String direction = model2.getStartState().getPossibleMoves().get(0);
    String currDirection = "";

    switch (direction) {
      case "NORTH" :
        move = Move.NORTH;
        currDirection = "N";
        break;
      case "SOUTH" :
        move = Move.SOUTH;
        currDirection = "S";
        break;
      case "EAST" :
        move = Move.EAST;
        currDirection = "E";
        break;
      case "WEST" :
        move = Move.WEST;
        currDirection = "W";
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    model2.assignOtyugh(model2.getStartState().getNextLocation(move));
    model2.playerShootArrow(1, currDirection);
    assertEquals("\nYou killed an Otyugh\n", model2.playerShootArrow(1, currDirection));
  }

  /**
   * Test if there are arrows in tunnels and caves.
   */
  @Test
  public void testArrowInTunnelAndCaves() {
    model1.addTreasure(50);
    Location[][] location = model1.getLocationGrid();

    int caveArrows = 0;
    int tunnelArrows = 0;

    for (int i = 0; i < model1.getRows(); i++) {
      for (int j = 0; j < model1.getColumns(); j++) {
        caveArrows += location[i][j].getArrowCount();
        for (Tunnel tunnel : location[i][j].getTunnel()) {
          tunnelArrows += tunnel.getArrowCount();
        }
      }
    }

    assertTrue(caveArrows > 0 && tunnelArrows > 0);
  }

  /**
   * Test if the player reaches the end location.
   */
  @Test
  public void testPlayerReachEndLocation() {
    List<Location> locations = new ArrayList<>(model1.getBFSTraversal());

    for (Location location : locations) {
      model1.getPlayer().move(location);
    }

    assertEquals(model1.getEndState(), model1.getPlayer().getCurrentLocation());
  }


  /**
   * Test if there are valid number of otyughs in the dungeon.
   */
  @Test
  public void testGetOtyughCount() {
    model1.addOtyughs(10);
    assertEquals(10, model1.getOtyughCount());
  }

  /**
   * Test if the correct percentage of items are present in the dungeon.
   */
  @Test
  public void testGetPercentageOfItems() {
    model2.addTreasure(80);
    assertEquals(80, model2.getPercentageOfItems());
  }

  /**
   * Test if the dungeon is non-wrapping or not.
   */
  @Test
  public void testIsDungeonNonWrapping() {
    assertTrue(model1.isNonWrapping());
  }


}
