package dungeon.kruskal;

import dungeon.position.Position;
import java.util.List;
import java.util.Map;

/**
 * This interface represents the functionalities of Kruskal algorithm.
 */
public interface Kruskal {

  /**
   * Generates a list of positions of locations present in dungeon.
   *
   * @param rows number of rows in dungeon
   * @param cols number of columns in dungeon
   * @return list of positions of locations
   */
  List<Position> generateVertices(int rows, int cols);

  /**
   * Generates a set of potential edges to be used in kruskal algorithm.
   *
   * @param vertices    list of positions of locations present in dungeon.
   * @param rows        number of rows in dungeon
   * @param cols        number of columns in dungeon
   * @param nonWrapping whether the dungeon is non-wrapping or not
   * @return potential edges which consist of mapping of a source vertex to its connected
   *         destination vertices
   */
  Map<Position, List<Position>> generatePotentialEdges(List<Position> vertices,
      int rows, int cols, boolean nonWrapping);

  /**
   * Returns a mapping of each source vertex to its destination vertices after applying kruskals
   * algorithm and generating the minimum spanning tree.
   *
   * @param rows              number of rows in dungeon
   * @param cols              number of columns in dungeon
   * @param nonWrapping       whether the dungeon is non-wrapping or not
   * @param interconnectivity degree of interconnectivity of the dungeon
   * @return potential edges obtained from minimum spanning tree which contains a mapping from
   *         source vertex to its connected destination vertices
   */
  Map<Position, List<Position>> getMST(int rows, int cols,
      boolean nonWrapping,
      int interconnectivity);

}
