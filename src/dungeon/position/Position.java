package dungeon.position;

/**
 * This interface represents the position of a cave.
 */
public interface Position {

  /**
   * Get the x-coordinate of a cave's position.
   *
   * @return x-coordinate of a cave's position
   */
  int getX();

  /**
   * Get the y-coordinate of a cave's position.
   *
   * @return y-coordinate of a cave's position.
   */
  int getY();

  /**
   * Get the string representation of x-y coordinates of a cave - (x,y).
   *
   * @return (x, y) -> x-y coordinates of the cave
   */
  String toString();
}
