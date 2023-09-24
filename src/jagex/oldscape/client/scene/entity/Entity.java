package jagex.oldscape.client.scene.entity;

import jagex.datastructure.DoublyLinkedNode;

public abstract class Entity extends DoublyLinkedNode {

  public int height;

  protected Entity() {
    height = 1000;
  }

  protected Model getModel() {
    return null;
  }

  public void render(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9) {
    Model model = getModel();
    if (model != null) {
      height = model.height;
      model.render(var1, var2, var3, var4, var5, var6, var7, var8, var9);
    }
  }
}
