package dungeon.kruskal;

import dungeon.position.Position;
import dungeon.position.PositionImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class implements the Kruskal's algorithm for finding a minimum spanning tree for the
 * dungeon. This includes generating vertices, potential edges and minimum spanning tree.
 */
public class KruskalImpl implements Kruskal {

  @Override
  public Map<Position, List<Position>> getMST(int rows, int cols,
      boolean nonWrapping,
      int interconnectivity) {
    List<Position> vertices = generateVertices(rows, cols);
    Map<Position, List<Position>> potentialEdges = generatePotentialEdges(vertices, rows,
        cols,
        nonWrapping);
    return kruskal(vertices, potentialEdges, interconnectivity, nonWrapping);
  }

  @Override
  public List<Position> generateVertices(int rows, int cols) {
    List<Position> vertices = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        vertices.add(new PositionImpl(i, j));
      }
    }
    return vertices;
  }

  @Override
  public Map<Position, List<Position>> generatePotentialEdges(
      List<Position> vertices,
      int rows, int cols, boolean nonWrapping) {
    Map<Position, List<Position>> potentialEdges = new HashMap<>();

    for (Position position : vertices) {
      potentialEdges.put(position, new ArrayList<>());
    }

    for (Position position : vertices) {
      int i = position.getX();
      int j = position.getY();

      // first row
      if (i == 0) {
        if (j != cols - 1) {
          potentialEdges.get(position).add(vertices.get(cols * i + j + 1));
          potentialEdges.get(position).add(vertices.get(cols * (i + 1) + j));
          if (j == 0) {
            if (!(nonWrapping)) {
              potentialEdges.get(position).add(vertices.get(cols * (rows - 1) + j));
              potentialEdges.get(position).add(vertices.get(cols * i + cols - 1));
            }
          } else {
            if (!(nonWrapping)) {
              potentialEdges.get(position).add(vertices.get(cols * (rows - 1) + j));
            }
          }
        }

        //last column of first row
        else {
          potentialEdges.get(position).add(vertices.get(cols * (i + 1) + j));
          if (!(nonWrapping)) {
            potentialEdges.get(position).add(vertices.get(cols * (rows - 1) + j));
          }
        }
      }

      // first column
      else if (i > 0 && j == 0) {
        if (i == rows - 1) {
          potentialEdges.get(position).add(vertices.get(cols * i + j + 1));
          if (!(nonWrapping)) {
            potentialEdges.get(position).add(vertices.get(cols * i + cols - 1));
          }
        } else {
          potentialEdges.get(position).add(vertices.get(cols * i + j + 1));
          potentialEdges.get(position).add(vertices.get(cols * (i + 1) + j));
          if (!(nonWrapping)) {
            potentialEdges.get(position).add(vertices.get(cols * i + cols - 1));
          }
        }
      }

      //last row
      else if (i == rows - 1 && j > 0) {
        if (j != cols - 1) {
          potentialEdges.get(position).add(vertices.get(cols * i + j + 1));
        }
      }

      // last column
      else if (j == cols - 1 && i > 0) {
        potentialEdges.get(position).add(vertices.get(cols * (i + 1) + j));
      }

      // all the other cases
      else {
        potentialEdges.get(position).add(vertices.get(cols * i + j + 1));
        potentialEdges.get(position).add(vertices.get(cols * (i + 1) + j));
      }
    }
    return potentialEdges;
  }

  /**
   * Implements the kruskal algorithm for finding the minimum spanning tree from location grid.
   *
   * @param vertices          list of positions of locations present in dungeon.
   * @param potentialEdges    potential edges which consist of mapping of a source vertex to its
   *                          connected destination vertices
   * @param interconnectivity degree of interconnectivity of dungeon
   * @param nonWrapping       whether the dungeon is non-wrapping or not
   * @return list of potential edges obtained from the minimum spanning tree
   */
  private Map<Position, List<Position>> kruskal(List<Position> vertices,
      Map<Position, List<Position>> potentialEdges, int interconnectivity,
      boolean nonWrapping) {
    List<Set<Position>> locationSet = new ArrayList<>(); // location set
    Map<Position, List<Position>> minimumSpanEdges = new HashMap<>(); // new edges
    Map<Position, List<Position>> leftoverEdges = new HashMap<>(); // leftover edges

    // store each vertex in a separate list
    for (Position position : vertices) {
      Set<Position> location = new HashSet<>();
      location.add(position);
      locationSet.add(location);
    }

    for (Map.Entry<Position, List<Position>> set : potentialEdges.entrySet()) {
      Position src = set.getKey();

      for (Position dest : set.getValue()) {
        int srcIndex = getVertexLocation(locationSet, src);
        int destIndex = getVertexLocation(locationSet, dest);

        if (srcIndex == destIndex) {
          if (!(leftoverEdges.containsKey(src))) {
            leftoverEdges.put(src, new ArrayList<>());
          }
          leftoverEdges.get(src).add(dest);
        } else {
          if (!(minimumSpanEdges.containsKey(src))) {
            minimumSpanEdges.put(src, new ArrayList<>());
          }
          minimumSpanEdges.get(src).add(dest); //add to new edges
          locationSet.get(srcIndex).addAll(locationSet.get(destIndex)); //union
          locationSet.remove(destIndex);
        }
      }
    }
    // adding left over edges to the minimum span edges if interconnectivity > 0
    if (!(nonWrapping)) {
      int count = 0;
      for (Map.Entry<Position, List<Position>> set : leftoverEdges.entrySet()) {
        Position src = set.getKey();
        for (Position dest : set.getValue()) {
          if (count == interconnectivity) {
            return minimumSpanEdges;
          }
          if (!(minimumSpanEdges.containsKey(src))) {
            minimumSpanEdges.put(src, new ArrayList<>());
          }
          minimumSpanEdges.get(src).add(dest);
          count += 1;
        }
      }
    }
    return minimumSpanEdges;
  }

  /**
   * Gets the set index of the given vertex from a list of location sets.
   *
   * @param locationSet list of location sets
   * @param vertex      given vertex whose set index is to be found
   * @return set index of the given vertex
   */
  private int getVertexLocation(List<Set<Position>> locationSet, Position vertex) {
    for (int i = 0; i < locationSet.size(); i++) {
      if (locationSet.get(i).contains(vertex)) {
        return i;
      }
    }
    throw new NoSuchElementException("Vertex: " + vertex.getX() + "," + vertex.getY() + " "
        + "doesn't exist");
  }

}


