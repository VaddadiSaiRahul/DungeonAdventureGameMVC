package dungeon.dungeonview;

import dungeon.location.Location;
import dungeon.treasure.Treasure;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * This panel represents the region where the dungeon game must be drawn.
 */
public class DungeonPanel extends JPanel {

  ReadonlyDungeonModel readOnlyDungeonModel;
  JLabel[][] jLabels;
  List<Location> visitedStates;

  /**
   * Initializes the read only dungeon model for use by the panel. Stores the visited states of the
   * player to display in the gui. Initializes the dungeon locations with black cells except for the
   * cell where the player is present.
   *
   * @param readOnlyDungeonModel read only dungeon model for use by panel
   */
  public DungeonPanel(ReadonlyDungeonModel readOnlyDungeonModel) {
    this.readOnlyDungeonModel = readOnlyDungeonModel;
    visitedStates = new ArrayList<>();

    Location[][] dungeon = this.readOnlyDungeonModel.getLocationGrid();
    int rows = readOnlyDungeonModel.getRows();
    int cols = readOnlyDungeonModel.getColumns();
    this.visitedStates.add(readOnlyDungeonModel.getPlayer().getCurrentLocation());

    jLabels = new JLabel[rows][cols];

    setLayout(new GridLayout(rows, cols, 0, 0));
    setSize(rows * 128, cols * 128);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        List<String> moves = dungeon[i][j].getPossibleMoves();
        Collections.sort(moves);
        String imgName = "";
        for (String str : moves) {
          imgName = imgName.concat("" + str.charAt(0));
        }

        if (!(this.visitedStates.contains(dungeon[i][j]))) {
          jLabels[i][j] = new JLabel(
              new ImageIcon(new ImageIcon(".\\res"
                  + "\\dungeon-images\\dungeon-images\\blank.png").getImage()
                  .getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
        } else {
          jLabels[i][j] = new JLabel(
              new ImageIcon(new ImageIcon(".\\res"
                  + "\\dungeon-images\\dungeon-images\\color-cells\\" + imgName
                  + ".png").getImage()
                  .getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
        }

        jLabels[i][j].setBorder(new LineBorder(Color.BLACK));
        this.add(jLabels[i][j]);
      }
    }
  }

  /**
   * Overrides the paint method of the JPanel class. Draws the game images and updates based on the
   * player actions in the view.
   *
   * @param g graphics object
   */
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;

    Location[][] dungeon = this.readOnlyDungeonModel.getLocationGrid();
    int rows = this.readOnlyDungeonModel.getRows();
    int cols = this.readOnlyDungeonModel.getColumns();
    this.visitedStates.add(this.readOnlyDungeonModel.getPlayer().getCurrentLocation());

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {

        List<String> moves = dungeon[i][j].getPossibleMoves();
        Collections.sort(moves);
        String imgName = "";
        for (String str : moves) {
          imgName = imgName.concat("" + str.charAt(0));
        }

        Point point = jLabels[i][j].getLocation();

        if (!(this.visitedStates.contains(dungeon[i][j]))) {
          try {
            g2d.drawImage(ImageIO.read(
                        new File(".\\res"
                            + "\\dungeon-images\\dungeon-images\\blank.png"))
                    .getScaledInstance(128, 128, Image.SCALE_SMOOTH),
                point.x, point.y, null);
          } catch (IOException e) {
            System.out.println("IOException " + e);
          }
        } else {
          try {
            g2d.drawImage(ImageIO.read(
                        new File(".\\res"
                            + "\\dungeon-images\\dungeon-images\\color-cells\\" + imgName
                            + ".png"))
                    .getScaledInstance(128, 128, Image.SCALE_SMOOTH),
                point.x, point.y, null);
          } catch (IOException e) {
            System.out.println("IOException " + e);
          }

          if (dungeon[i][j].getOtyugh() != null) {
            try {
              g2d.drawImage(ImageIO.read(
                      new File(".\\res\\dungeon-images\\dungeon-images\\otyugh_1.png")),
                  point.x + 32, point.y + 32, null);
            } catch (IOException e) {
              System.out.println("IOException " + e);
            }
          }

          if (readOnlyDungeonModel.getPlayer().getCurrentLocation().getPosition().getX() == i &&
              readOnlyDungeonModel.getPlayer().getCurrentLocation().getPosition().getY() == j) {
            if (readOnlyDungeonModel.getPlayer().getCurrentLocation().getOtyugh() == null) {
              try {
                g2d.drawImage(ImageIO.read(
                            new File(".\\res\\dungeon-images\\dungeon-images\\dungeon_master.png"))
                        .getScaledInstance(120, 50, Image.SCALE_SMOOTH),
                    point.x + 10, point.y + 27, null);
              } catch (IOException e) {
                System.out.println("IOException " + e);
              }
            }
          }

          if (dungeon[i][j].getArrowCount() > 0) {
            try {
              g2d.drawImage(ImageIO.read(
                          new File(".\\res"
                              + "\\dungeon-images\\dungeon-images\\arrow-black.png"))
                      .getScaledInstance(30, 6, Image.SCALE_SMOOTH),
                  point.x + 53, point.y + 25, null);
            } catch (IOException e) {
              System.out.println("IOException " + e);
            }
          }

          if (!(dungeon[i][j].getTreasure().isEmpty())) {
            HashSet<Treasure> treasures = new HashSet<>(dungeon[i][j].getTreasure());
            for (Treasure treasure : treasures) {
              try {
                if (treasure.toString().equals("Ruby")) {
                  g2d.drawImage(ImageIO.read(
                              new File(".\\res"
                                  + "\\dungeon-images\\dungeon-images\\ruby.png"))
                          .getScaledInstance(12, 14, Image.SCALE_SMOOTH),
                      point.x + 6, point.y + 55, null);
                } else if (treasure.toString().equals("Diamond")) {
                  g2d.drawImage(ImageIO.read(
                              new File(".\\res"
                                  + "\\dungeon-images\\dungeon-images\\diamond.png"))
                          .getScaledInstance(14, 13, Image.SCALE_SMOOTH),
                      point.x + 58, point.y + 100, null);
                } else if (treasure.toString().equals("Sapphire")) {
                  g2d.drawImage(ImageIO.read(
                              new File(".\\res"
                                  + "\\dungeon-images\\dungeon-images\\emerald.png"))
                          .getScaledInstance(15, 17, Image.SCALE_SMOOTH),
                      point.x + 100, point.y + 52, null);
                }

              } catch (IOException e) {
                System.out.println("IOException " + e);
              }
            }
          }

          if (dungeon[i][j].toString().contains("A less pungent smell is coming from nearby")
              && dungeon[i][j].getOtyugh() == null) {
            try {
              g2d.drawImage(ImageIO.read(
                      new File(".\\res"
                          + "\\dungeon-images\\dungeon-images\\stench01.png")),
                  point.x + 32, point.y + 32, null);
            } catch (IOException e) {
              System.out.println("IOException " + e);
            }
          }

          if (dungeon[i][j].toString().contains("A terrible smell is coming from nearby")
              && dungeon[i][j].getOtyugh() == null) {
            try {
              g2d.drawImage(ImageIO.read(
                      new File(".\\res"
                          + "\\dungeon-images\\dungeon-images\\stench02.png")),
                  point.x + 32, point.y + 32, null);
            } catch (IOException e) {
              System.out.println("IOException " + e);
            }
          }
        }
      }
    }
  }

}
