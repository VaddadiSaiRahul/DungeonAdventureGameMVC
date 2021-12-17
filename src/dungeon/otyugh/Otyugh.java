package dungeon.otyugh;

/**
 * This represents an Otyugh residing in a particular cave of the dungeon.
 */
public interface Otyugh {

  /**
   * Checks if the Otyugh has been hit a single time or not.
   *
   * @return true if hit once else returns false
   */
  boolean isHit();

  /**
   * Updates the number of hits an Otyugh has taken by 1 if a player's arrow hits it.
   */
  void updateHit();

  /**
   * Checks if the Otyugh has been killed or not. It is killed if the number of hits it has taken is
   * equal to 2.
   *
   * @return true if the otyugh is dead else returns false
   */
  boolean isDead();
}
