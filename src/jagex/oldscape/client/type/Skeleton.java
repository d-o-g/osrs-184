package jagex.oldscape.client.type;

import jagex.datastructure.Node;
import jagex.messaging.Buffer;

public class Skeleton extends Node {

  public final int id;
  public final int count;
  public final int[] transformTypes;
  public final int[][] labels;

  Skeleton(int id, byte[] data) {
    this.id = id;

    Buffer buffer = new Buffer(data);
    count = buffer.g1();
    transformTypes = new int[count];
    labels = new int[count][];

    for (int i = 0; i < count; ++i) {
      transformTypes[i] = buffer.g1();
    }

    for (int i = 0; i < count; ++i) {
      labels[i] = new int[buffer.g1()];
    }

    for (int i = 0; i < count; ++i) {
      for (int j = 0; j < labels[i].length; ++j) {
        labels[i][j] = buffer.g1();
      }
    }
  }
}
