package dungeon.position;

/**
 * This class implements the functionalities of a cave's position which includes getting the x and y
 * coordinates and displaying the x-y coordinates.
 */
public class PositionImpl implements Position {

  private int x;
  private int y;

  /**
   * Initializes the x and y coordinates of a position.
   *
   * @param x x-coordinate of the position
   * @param y y-coordinate of the position.
   * @throws IllegalArgumentException if x-coordinate < 0 or y-coordinate < 0 or both less than 0.
   */
  public PositionImpl(int x, int y) {
    if (x < 0) {
      throw new IllegalArgumentException("X-Coordinate cannot be less than 0");
    }

    if (y < 0) {
      throw new IllegalArgumentException("Y-Coordinate cannot be less than 0");
    }

    if (x < 0 && y < 0) {
      throw new IllegalArgumentException("Coordinates cannot be less than 0");
    }

    this.x = x;
    this.y = y;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", x, y);
  }
}
