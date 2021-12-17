package dungeon.dungeoncontroller;

import dungeon.dungeonmodel.DungeonModel;
import java.util.Scanner;

/**
 * This represents an interface for the Dungeon Controller. It handles user's input by executing
 * them using the model and conveys outcomes to the user in some form.
 */
public interface IDungeonController {

  /**
   * Execute a single game of a player navigating through a dungeon given a Dungeon Model. When the
   * player reaches the end location, the game is over and the playGame method ends.
   *
   * @param model a non-null Dungeon Model
   */
  void playGame(DungeonModel model);

  /**
   * Returns the Scanner object which contains the inputs to the dungeon given by the user.
   *
   * @return a scanner object which contains the user inputs
   */
  Scanner getReadable();
}
