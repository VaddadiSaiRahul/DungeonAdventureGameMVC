package dungeon.dungeoncontroller;

import dungeon.dungeonmodel.DungeonModel;
import dungeon.dungeonview.DungeonView;
import java.util.NoSuchElementException;

/**
 * This class implements the gui based controller for the dungeon adventure game.
 */
public class DungeonGuiController implements IDungeonGuiController {

  private final DungeonModel model;
  private final DungeonView view;

  /**
   * Initializes the model and view to the gui controller.
   *
   * @param model the model of the game
   * @param view  the view of the game
   */
  public DungeonGuiController(DungeonModel model, DungeonView view) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void playGame() {
    this.view.addClickListener(this);
    this.view.addKeyListener(this);
    this.view.makeVisible();
  }

  @Override
  public void handleCellClick(int x, int y) {
    int playerX = model.getPlayer().getCurrentLocation().getPosition().getX();
    int playerY = model.getPlayer().getCurrentLocation().getPosition().getY();

    String move = "";

    if (playerX == x) {
      if (playerY - 1 == y) {
        move = "W";
      } else if (playerY + 1 == y) {
        move = "E";
      }
    } else if (playerY == y) {
      if (playerX - 1 == x) {
        move = "N";
      } else if (playerX + 1 == x) {
        move = "S";
      }
    }
    moveHandler(move);
    this.view.refresh();
  }

  @Override
  public void handleKeyPress(String direction) {
    moveHandler(direction);
    this.view.refresh();
  }

  @Override
  public void handleShootKeyPress(int distance, String direction) {
    String result_ = "";
    try {
      result_ = this.model.playerShootArrow(distance, direction);
    } catch (IllegalStateException illegalStateException) {
      result_ = "You're out of arrows";
    } catch (NoSuchElementException noSuchElementException) {
      result_ = "Arrow direction not possible";
    } catch (IllegalArgumentException illegalArgumentException) {
      result_ = "Invalid direction";
    }
    this.view.displayKeyPress(result_);

  }

  @Override
  public void handlePickupKeyPress(String text) {
    String result_ = "";
    if (text.equalsIgnoreCase("A")) {
      result_ += "Arrow";
    } else if (text.equalsIgnoreCase("R")) {
      result_ += "Ruby";
    } else if (text.equalsIgnoreCase("D")) {
      result_ += "Diamond";
    } else if (text.equalsIgnoreCase("S")) {
      result_ = "Sapphire";
    }
    try {
      model.playerPickupItem(result_);
      result_ = "You picked up " + result_;
    } catch (NoSuchElementException noSuchElementException) {
      result_ = "Invalid Item";
    } catch (IllegalStateException | IllegalArgumentException illegalException) {
      result_ = "Item not available";
    }
    this.view.displayKeyPress(result_);
  }

  private void moveHandler(String direction) {
    try {
      if (model.getPlayer().getCurrentLocation().getOtyugh() != null || model.getPlayer()
          .getCurrentLocation().equals(model.getEndState())) {
        // do nothing because game ends
      } else {
        model.getPlayer().move(direction);
      }
    } catch (IllegalArgumentException illegalArgumentException) {
      // do nothing invalid direction
    } catch (IllegalStateException illegalStateException) {
      // do nothing player is dead
    }
  }

}
