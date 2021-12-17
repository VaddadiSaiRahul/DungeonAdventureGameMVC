package dungeon.otyugh;

/**
 * This class represents an otyugh.
 */
public class OtyughImpl implements Otyugh {
  private int hitsTaken = 0;

  @Override
  public boolean isHit() {
    return this.hitsTaken == 1;
  }

  @Override
  public void updateHit() {
    hitsTaken += 1;
  }

  @Override
  public boolean isDead() {
    return this.hitsTaken == 2;
  }
}
