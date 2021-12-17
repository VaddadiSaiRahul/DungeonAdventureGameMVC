package dungeon.driver;

import dungeon.dungeoncontroller.DungeonConsoleController;
import dungeon.dungeoncontroller.DungeonGuiController;
import dungeon.dungeoncontroller.IDungeonGuiController;
import dungeon.dungeonmodel.Dungeon;
import dungeon.dungeonmodel.DungeonModel;
import dungeon.dungeonview.DungeonView;
import dungeon.dungeonview.DungeonViewImpl;
import dungeon.dungeonview.ReadonlyDungeonModel;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * This class represents a player navigating through a dungeon.
 */
public class Driver {

  /**
   * Displays a player's information as they navigate through the dungeon.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    if (args[0].equals("console")) {

      System.out.println("\n*******    CONSOLE BASED DUNGEON GAME      ********\n");

      Readable in = new InputStreamReader(System.in);
      Appendable out = System.out;

      Scanner sc = new Scanner(System.in);
      System.out.print("Enter number of rows and columns in dungeon: ");
      int rows = sc.nextInt();
      int cols = sc.nextInt();
      System.out.println();
      System.out.print("Enter interconnectivity of the dungeon: ");
      int interconnectivity = sc.nextInt();
      System.out.println();
      System.out.print("Enter if dungeon is non-wrapping or not (true/false): ");
      boolean nonWrapping = sc.nextBoolean();
      System.out.println();
      System.out.print("Enter percentage of caves that will have treasure: ");
      int percentage = sc.nextInt();
      System.out.println();
      System.out.print("Enter number of Otyughs(1-" + (rows * cols - 1) + "): ");
      int otyughs = sc.nextInt();

      DungeonModel model = new Dungeon(nonWrapping, rows, cols, interconnectivity);

      model.addOtyughs(otyughs);
      model.addTreasure(80);
      model.enterPlayer();

      new DungeonConsoleController(in, out).playGame(model);
    }

    else if (args[0].equals("gui")) {
      DungeonModel model = new Dungeon(Boolean.parseBoolean(args[1]), Integer.parseInt(args[2]),
          Integer.parseInt(args[3]), Integer.parseInt(args[4]));

      ReadonlyDungeonModel readonlyDungeonModel = model;

      model.addOtyughs(Integer.parseInt(args[5]));
      model.addTreasure(Integer.parseInt(args[6]));
      model.enterPlayer();

      DungeonView view = new DungeonViewImpl(readonlyDungeonModel);
      IDungeonGuiController controller = new DungeonGuiController(model, view);
      controller.playGame();
    }

  }
}
