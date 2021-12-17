package dungeontest.dungeoncontrollertest;

import static org.junit.Assert.assertEquals;

import dungeon.dungeoncontroller.DungeonGuiController;
import dungeon.dungeoncontroller.IDungeonGuiController;
import dungeon.dungeonmodel.Dungeon;
import dungeon.dungeonmodel.DungeonModel;
import dungeon.dungeonview.DungeonView;
import dungeon.dungeonview.MockView;
import org.junit.Test;

/**
 * Tests the graphical controller using a mock view.
 */
public class DungeonGuiControllerTest {

  /**
   * This method validates the log of actions taken by the graphical controller .
   */
  @Test
  public void testGraphicalController() {
    StringBuilder log = new StringBuilder();
    DungeonView view = new MockView(log);
    DungeonModel model = new Dungeon(true, 5, 5, 0);
    model.enterPlayer();
    model.addOtyughs(3);
    model.addTreasure(50);
    IDungeonGuiController controller = new DungeonGuiController(model, view);
    controller.playGame();

    assertEquals("click listener controller added\n"
        + "key listener controller added\n"
        + "gui visible", log.toString());
  }

}
