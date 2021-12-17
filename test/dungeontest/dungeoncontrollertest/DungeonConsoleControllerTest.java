package dungeontest.dungeoncontrollertest;

import static org.junit.Assert.assertEquals;

import dungeon.dungeoncontroller.IDungeonController;
import dungeon.dungeoncontroller.DungeonConsoleController;
import dungeon.dungeonmodel.Dungeon;
import dungeon.dungeonmodel.DungeonModel;
import dungeon.location.Tunnel;
import dungeon.player.Move;
import dungeon.treasure.Ruby;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;

/**
 * This class tests the controller for the dungeon.
 */
public class DungeonConsoleControllerTest {

  /**
   * Test if the controller works correctly when given a null model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    DungeonModel dungeonModel = null;
    StringReader input = new StringReader("5 5 2 false 90 15");
    Appendable gameLog = new StringBuilder();
    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }

  /**
   * Test if the controller works correctly when given an invalid row as input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRow() {
    String inputString = "-5 5 2 false 90 15";
    String[] inputs = inputString.split(" ");

    DungeonModel dungeonModel = new Dungeon(Boolean.parseBoolean(inputs[3]),
        Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }

  /**
   * Test if the controller works correctly when given an invalid column as input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidColumn() {
    String inputString = "4 -6 0 true 85 20";
    String[] inputs = inputString.split(" ");

    DungeonModel dungeonModel = new Dungeon(Boolean.parseBoolean(inputs[3]),
        Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }

  /**
   * Test if the controller works correctly when given an invalid interconnectivity as input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    String inputString = "5 5 -1 true 30 15";
    String[] inputs = inputString.split(" ");

    DungeonModel dungeonModel = new Dungeon(Boolean.parseBoolean(inputs[3]),
        Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }


  /**
   * Test if the controller works correctly when given an invalid non-wrapping as input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNonWrapping() {
    String inputString = "4 6 0 1 85 20";
    String[] inputs = inputString.split(" ");

    DungeonModel dungeonModel = new Dungeon(Boolean.parseBoolean(inputs[3]),
        Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }

  /**
   * Test if the controller works correctly when given an interconnectivity > 0 for non-wrapping
   * dungeon.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNonWrappingInterconnectivity() {
    String inputString = "4 6 2 true 85 20";
    String[] inputs = inputString.split(" ");

    DungeonModel dungeonModel = new Dungeon(Boolean.parseBoolean(inputs[3]),
        Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }

  /**
   * Test if the controller works correctly when given an interconnectivity > 0 for non-wrapping
   * dungeon.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWrappingInterconnectivity() {
    String inputString = "4 6 0 false 85 20";
    String[] inputs = inputString.split(" ");

    DungeonModel dungeonModel = new Dungeon(Boolean.parseBoolean(inputs[3]),
        Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    controller.playGame(dungeonModel);
  }

  /**
   * Test if the controller works correctly when given an invalid choice.
   */
  @Test
  public void testInvalidChoice() {
    String inputString = "4 6 2 false 85 20 T q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();
    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? Invalid choice\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }


  /**
   * Test if the controller works correctly when given a valid move.
   */
  @Test
  public void testValidMChoice() {
    String inputString = "4 6 2 false 85 5";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    sc.next();//dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String move = dungeonModel.getStartState().getPossibleMoves().get(0);
    Move playerMove = null;
    String direction = null;

    switch (move) {
      case "NORTH" :
        direction = "N";
        playerMove = Move.NORTH;
        break;
      case "SOUTH" :
        direction = "S";
        playerMove = Move.SOUTH;
        break;
      case "EAST" :
        direction = "E";
        playerMove = Move.EAST;
        break;
      case "WEST" :
        direction = "W";
        playerMove = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    inputString = "M " + direction + " q";
    controller = new DungeonConsoleController(new StringReader(inputString), gameLog);

    List<Tunnel> tunnelList = dungeonModel.getStartState().getTunnel();
    String sb = "";

    for (Tunnel tunnel : tunnelList) {
      if (tunnel.getDirection().toString().equals(direction)) {
        if (tunnel.getArrowCount() > 0) {
          sb = "\nYou are in a tunnel\nYou picked up" + tunnel.getArrowCount() + " Arrow";
          break;
        }
      }
    }

    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? Where to ? " + sb +
        dungeonModel.getStartState().getNextLocation(playerMove).toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }


  /**
   * Test if the controller works correctly when given an invalid move.
   */
  @Test
  public void testInvalidMChoice() {
    String inputString = "4 6 2 false 85 5";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String[] directions = {"NORTH", "SOUTH", "EAST", "WEST"};
    Move playerMove = null;
    String move = null;
    String direction = null;

    for (String direction1 : directions) {
      if (!(dungeonModel.getStartState().getPossibleMoves().contains(direction1))) {
        move = direction1;
        break;
      }
    }

    switch (move) {
      case "NORTH" :
        direction = "N";
        playerMove = Move.NORTH;
        break;
      case "SOUTH" :
        direction = "S";
        playerMove = Move.SOUTH;
        break;
      case "EAST" :
        direction = "E";
        playerMove = Move.EAST;
        break;
      case "WEST" :
        direction = "W";
        playerMove = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    inputString = "M " + direction + " q";
    controller = new DungeonConsoleController(new StringReader(inputString), gameLog);

    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? Where to ? Invalid direction" + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the controller works correctly when given a valid move and player gets eaten after
   * moving to the given location.
   */
  @Test
  public void testValidMOtyughEatPlayer() {
    String inputString = "4 6 2 false 85 5";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String move = dungeonModel.getStartState().getPossibleMoves().get(0);
    Move playerMove = null;
    String direction = null;

    switch (move) {
      case "NORTH" :
        direction = "N";
        playerMove = Move.NORTH;
        break;
      case "SOUTH" :
        direction = "S";
        playerMove = Move.SOUTH;
        break;
      case "EAST" :
        direction = "E";
        playerMove = Move.EAST;
        break;
      case "WEST" :
        direction = "W";
        playerMove = Move.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

    if (dungeonModel.getStartState().getNextLocation(playerMove).getOtyugh() == null) {
      dungeonModel.assignOtyugh(dungeonModel.getStartState().getNextLocation(playerMove));
    }

    inputString = "M " + direction + " q";
    controller = new DungeonConsoleController(new StringReader(inputString), gameLog);

    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? Where to ? \nChomp, chomp, chomp, you are eaten by an"
        + " Otyugh!\n"
        + "Better luck next time";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the controller works correctly when a player enters an invalid pickup item.
   */
  @Test
  public void testInvalidPChoice() {
    String inputString = "4 6 2 false 90 5 P invalid q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? What ? " + "Invalid Pickup Item\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }


  /**
   * Test if the controller works correctly when a player moves to a location where the otyugh has
   * been killed.
   */
  @Test
  public void testMAfterKillOtyugh() {
    String inputString = "4 6 2 false 85 5";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String direction = dungeonModel.getStartState().getPossibleMoves().get(0);
    String currDirection = null;
    Move move = null;

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

    if (dungeonModel.getStartState().getNextLocation(move).getOtyugh() == null) {
      dungeonModel.assignOtyugh(dungeonModel.getStartState().getNextLocation(move));
    }

    inputString = "S 1 " + currDirection + " S 1 " + currDirection + " M " + currDirection + " q";

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? No. of caves ? Where to (" +
        dungeonModel.getStartState().possibleMovesHelper() + " )? " +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? " + "No. of caves ? Where to (" +
        dungeonModel.getStartState().possibleMovesHelper() + " )? ";

    controller = new DungeonConsoleController(new StringReader(inputString), gameLog);

    List<Tunnel> tunnelList = dungeonModel.getStartState().getTunnel();
    String sb = "";

    for (Tunnel tunnel : tunnelList) {
      if (tunnel.getDirection().toString().equals(direction)) {
        if (tunnel.getArrowCount() > 0) {
          sb = "\nYou are in a tunnel\nYou picked up" + tunnel.getArrowCount() + " Arrows";
          break;
        }
      }
    }

    controller.playGame(dungeonModel);

    s += sb + "\nYou killed an Otyugh\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? Where to ? " +
        dungeonModel.getStartState().getNextLocation(move).toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the controller works correctly when a player selects 'P' and picks up an arrow which is
   * available.
   */
  @Test
  public void testPArrowAvailable() {
    String inputString = "4 6 2 false 100 5 P Arrow q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();
    dungeonModel.addArrow(dungeonModel.getStartState());

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? What ? ";

    controller.playGame(dungeonModel);

    s += "You picked up Arrow" + "\n" + dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the controller works correctly when a player selects 'P' and tries to pick up an arrow
   * which is not available.
   */
  @Test
  public void testPNoArrow() {
    String inputString = "4 6 2 false 0 5 P Arrow q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? What ? ";

    controller.playGame(dungeonModel);

    s += "No arrows available" + "\n" + dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the controller works correctly when a player selects 'P' and picks up a treasure which
   * is available.
   */
  @Test
  public void testPTreasureAvailable() {
    String inputString = "4 6 2 false 100 5 P Ruby q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();
    dungeonModel.addTreasure(new Ruby(), dungeonModel.getStartState());

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? What ? ";

    controller.playGame(dungeonModel);

    s += "You picked up Ruby" + "\n" + dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }


  /**
   * Test if the controller works correctly when a player selects 'P' and tries to pick up treasure
   * which is not available.
   */
  @Test
  public void testPNoTreasure() {
    String inputString = "4 6 2 false 0 5 P Ruby q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? What ? ";

    controller.playGame(dungeonModel);

    s += "treasure not available" + "\n" + dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the controller works correctly when a player selects 'S' and shoots an arrow resulting
   * in killing of an Otyugh.
   */
  @Test
  public void testSChoiceKillOtyugh() {
    String inputString = "4 6 2 false 85 5";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String direction = dungeonModel.getStartState().getPossibleMoves().get(0);
    String currDirection = null;
    Move move = null;

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

    if (dungeonModel.getStartState().getNextLocation(move).getOtyugh() == null) {
      dungeonModel.assignOtyugh(dungeonModel.getStartState().getNextLocation(move));
    }

    inputString = "S 1 " + currDirection + " S 1 " + currDirection + " q";

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? No. of caves ? Where to (" +
        dungeonModel.getStartState().possibleMovesHelper() + " )? " +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? No. of caves ? Where to (" +
        dungeonModel.getStartState().possibleMovesHelper() + " )? ";

    controller = new DungeonConsoleController(new StringReader(inputString), gameLog);
    controller.playGame(dungeonModel);

    s += "\nYou killed an Otyugh\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }


  /**
   * Test if the controller works correctly when a user selects 'S' and shoots an arrow which misses
   * the Otyugh.
   */
  @Test
  public void testSMissesOtyugh() {
    String inputString = "4 6 2 false 85 5";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();

    String direction = dungeonModel.getStartState().getPossibleMoves().get(0);
    String currDirection = null;
    Move move = null;

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

    if (dungeonModel.getStartState().getNextLocation(move).getOtyugh() == null) {
      dungeonModel.assignOtyugh(dungeonModel.getStartState().getNextLocation(move));
    }

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? No. of caves ? Where to (" +
        dungeonModel.getStartState().possibleMovesHelper() + " )? ";

    inputString = "S 2 " + currDirection + " q";
    controller = new DungeonConsoleController(new StringReader(inputString), gameLog);
    controller.playGame(dungeonModel);

    s += "\nYou shoot an arrow into the darkness\n" +
        dungeonModel.getStartState().toString() + "\n" +
        "\nMove, Pickup, or Shoot (M-P-S-q)? ";

    assertEquals(s, gameLog.toString());
  }

  /**
   * Test if the player wins game after reaching the end location.
   */
  @Test
  public void testWinGame() {
    String inputString = "4 6 2 false 85 5 q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    sc.next();
    dungeonModel.enterPlayer();
    dungeonModel.getPlayer().move(dungeonModel.getEndState());

    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        "\nYay! you successfully escaped the dungeon";

    assertEquals(s, gameLog.toString());

  }


  /**
   * Test if the player loses game after reaching the end location.
   */
  @Test
  public void testLoseGame() {
    String inputString = "4 6 2 false 85 1 q";
    Scanner sc;

    StringReader input = new StringReader(inputString);
    Appendable gameLog = new StringBuilder();

    IDungeonController controller = new DungeonConsoleController(input, gameLog);
    sc = controller.getReadable();

    int rows = Integer.parseInt(sc.next());
    int cols = Integer.parseInt(sc.next());
    int interconnectivity = Integer.parseInt(sc.next());
    boolean nonWrapping = Boolean.parseBoolean(sc.next());

    DungeonModel dungeonModel = new Dungeon(nonWrapping, rows, cols, interconnectivity);
    dungeonModel.addTreasure(Integer.parseInt(sc.next()));
    dungeonModel.addOtyughs(Integer.parseInt(sc.next()));
    dungeonModel.enterPlayer();
    dungeonModel.getPlayer().move(dungeonModel.getEndState());

    controller.playGame(dungeonModel);

    String s = "\nStart state : " + dungeonModel.getStartState().getPosition() + " " +
        "\nEnd state : " + dungeonModel.getEndState().getPosition() + "\n" +
        "\nChomp, chomp, chomp, you are eaten by an Otyugh!\nBetter luck next time";

    assertEquals(s, gameLog.toString());
  }


}
