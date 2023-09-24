package jagex.datastructure.instrusive.hashtable;

import jagex.datastructure.Node;

public class ObjectNode extends Node {

  public final Object value;

  public ObjectNode(Object value) {
    this.value = value;
  }
}
