package jagex.oldscape.client;

import jagex.datastructure.Node;
import jagex.oldscape.client.minimenu.ContextMenu;

public class SubInterface extends Node {

  public int type; //0 = modal, 1 = overlay
  public int id;

  public boolean aBoolean790;

  public SubInterface() {
    this.aBoolean790 = false;
  }

  public static void process() {
    for (SubInterface sub = client.subInterfaces.head(); sub != null; sub = client.subInterfaces.next()) {
      int groupIndex = sub.id;
      if (Component.load(groupIndex)) {
        boolean format = true;
        Component[] group = client.interfaces[groupIndex];
        for (Component component : group) {
          if (component != null) {
            format = component.if3;
            break;
          }
        }

        if (!format) {
          int uid = (int) sub.key;
          Component component = Component.lookup(uid);
          if (component != null) {
            Component.invalidate(component);
          }
        }
      }
    }

  }

  public static SubInterface create(int key, int id, int type) {
    SubInterface sub = new SubInterface();
    sub.id = id;
    sub.type = type;
    client.subInterfaces.put(sub, key);
    Component.loadAnimable(id);
    Component component = Component.lookup(key);
    Component.invalidate(component);
    if (client.pleaseWaitComponent != null) {
      Component.invalidate(client.pleaseWaitComponent);
      client.pleaseWaitComponent = null;
    }

    ContextMenu.method317();
    Component.revalidateScroll(client.interfaces[key >> 16], component, false);
    Component.loadAndInitialize(id);
    if (client.rootInterfaceIndex != -1) {
      Component.executeCloseListeners(client.rootInterfaceIndex, 1);
    }

    return sub;
  }
}
