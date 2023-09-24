package jagex.oldscape.client;

import jagex.datastructure.Node;
import jagex.datastructure.instrusive.hashtable.NodeTable;

public class Inventory extends Node {

  public static NodeTable<Inventory> cache = new NodeTable<>(32);

  public int[] ids;
  public int[] stackSizes;

  public Inventory() {
    ids = new int[]{-1};
    stackSizes = new int[]{0};
  }

  public static int getCountIncludingStacks(int key, int itemId) {
    Inventory inventory = cache.lookup(key);
    if (inventory == null) {
      return 0;
    } else if (itemId == -1) {
      return 0;
    }

    int total = 0;
    for (int i = 0; i < inventory.stackSizes.length; ++i) {
      if (inventory.ids[i] == itemId) {
        total += inventory.stackSizes[i];
      }
    }
    return total;
  }

  public static int getItemIdAt(int key, int index) {
    Inventory inventory = cache.lookup(key);
    if (inventory == null) {
      return -1;
    }
    return index >= 0 && index < inventory.ids.length ? inventory.ids[index] : -1;
  }

  public static void addItem(int key, int index, int id, int stack) {
    Inventory inventory = cache.lookup(key);
    if (inventory == null) {
      inventory = new Inventory();
      cache.put(inventory, key);
    }

    if (inventory.ids.length <= index) {
      int[] ids = new int[index + 1];
      int[] stacks = new int[index + 1];

      for (int i = 0; i < inventory.ids.length; ++i) {
        ids[i] = inventory.ids[i];
        stacks[i] = inventory.stackSizes[i];
      }

      for (int i = inventory.ids.length; i < index; ++i) {
        ids[i] = -1;
        stacks[i] = 0;
      }

      inventory.ids = ids;
      inventory.stackSizes = stacks;
    }

    inventory.ids[index] = id;
    inventory.stackSizes[index] = stack;
  }

  public static void clear(int key) {
    Inventory inventory = cache.lookup(key);
    if (inventory != null) {
      for (int i = 0; i < inventory.ids.length; ++i) {
        inventory.ids[i] = -1;
        inventory.stackSizes[i] = 0;
      }

    }
  }

  public static int getItemStackSizeAt(int key, int index) {
    Inventory inventory = cache.lookup(key);
    if (inventory == null) {
      return 0;
    }

    return index >= 0 && index < inventory.stackSizes.length ? inventory.stackSizes[index] : 0;
  }

  public static void delete(int key) {
    Inventory inv = cache.lookup(key);
    if (inv != null) {
      inv.unlink();
    }
  }
}
