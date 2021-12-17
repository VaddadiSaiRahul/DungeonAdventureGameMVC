package dungeon.dungeoncontroller;

import dungeon.dungeonmodel.DungeonModel;
import dungeon.location.Location;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class implements a console controller for the dungeon adventure game.
 */
public class DungeonConsoleController implements IDungeonController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the console controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable or Appendable cannot be null");
    }

    this.out = out;
    this.scan = new Scanner(in);
  }


  @Override
  public void playGame(DungeonModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Null Model");
    }

    Location state = model.getPlayer().getCurrentLocation();

    try {
      out.append("\nStart state : ").append(model.getStartState().getPosition().toString())
          .append(" \nEnd state : ").append(model.getEndState().getPosition().toString())
          .append("\n");
    } catch (IOException ioException) {
      throw new IllegalStateException("Append failed", ioException);
    }

    while (!(state.equals(model.getEndState()))) {
      try {
        out.append(state.toString()).append("\n");
        out.append("\nMove, Pickup, or Shoot (M-P-S-q)? ");
        char choice = scan.next().charAt(0);

        switch (choice) {
          case 'M': {
            try {
              out.append("Where to ? ");

              String direction = scan.next();//String direction = scan.nextLine();
              try {
                out.append(model.movePlayer(direction));
              } catch (IllegalStateException illegalStateException) {
                out.append(
                    "\nChomp, chomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");
                return;
              }
              state = model.getPlayer().getCurrentLocation();

            } catch (IllegalArgumentException iae) {
              out.append("Invalid direction").append("\n");
            }
          }
          break;

          case 'P' : {
            try {
              out.append("What ? ");
              String item = scan.next();//String item = scan.nextLine();
              try {
                model.playerPickupItem(item);
                out.append("You picked up ").append(item).append("\n");
              } catch (NoSuchElementException noSuchElementException) {
                out.append("No arrows available").append("\n");
              } catch (IllegalArgumentException iae) {
                out.append("treasure not available").append("\n");
              }
            } catch (IllegalStateException illegalStateException) {
              out.append("Invalid Pickup Item").append("\n");
            }

          }
          break;

          case 'S' : {
            out.append("No. of caves ? ");
            int distance;
            try {
              distance = Integer.parseInt(scan.next());
            } catch (InputMismatchException inputMismatchException) {
              out.append("Invalid input");
              continue;
            }

            out.append("Where to (").append(state.possibleMovesHelper()).append(" )? ");
            String direction = scan.next();
            try {
              out.append(model.playerShootArrow(distance, direction));
            } catch (IllegalArgumentException illegalArgumentException) {
              out.append("Invalid direction").append("\n");
            } catch (IllegalStateException illegalStateException) {
              out.append("You are out of arrows, explore to find more").append("\n");
            } catch (NoSuchElementException noSuchElementException) {
              out.append("You shoot an arrow into the darkness").append("\n");
            }
          }
          break;

          case 'q' : {
            return;
          }

          default : {
            try {
              throw new IllegalArgumentException("Invalid choice");
            } catch (IllegalArgumentException iae) {
              out.append("Invalid choice").append("\n");
            }
          }
          break;
        }
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed", ioe);
      }
    }

    try {
      if (state.getOtyugh() == null) {
        out.append("\nYay! you successfully escaped the dungeon");
      } else {
        if (state.getOtyugh().isHit()) {
          if (Math.random() < 0.5) {
            out.append("\nChomp, chomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");
          } else {
            out.append("\nYay! you successfully escaped the dungeon");
          }
        } else {
          out.append("\nChomp, chomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");
        }
      }
    } catch (IOException ioException) {
      throw new IllegalStateException("Append failed", ioException);
    }

  }

  @Override
  public Scanner getReadable() {
    return this.scan;
  }



}