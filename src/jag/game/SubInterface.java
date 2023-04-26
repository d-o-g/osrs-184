package jag.game;

import jag.commons.collection.Node;
import jag.game.menu.ContextMenu;

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
            if (InterfaceComponent.load(groupIndex)) {
                boolean format = true;
                InterfaceComponent[] group = client.interfaces[groupIndex];
                for (InterfaceComponent component : group) {
                    if (component != null) {
                        format = component.if3;
                        break;
                    }
                }

                if (!format) {
                    int uid = (int) sub.key;
                    InterfaceComponent component = InterfaceComponent.lookup(uid);
                    if (component != null) {
                        InterfaceComponent.invalidate(component);
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
        InterfaceComponent.loadAnimable(id);
        InterfaceComponent component = InterfaceComponent.lookup(key);
        InterfaceComponent.invalidate(component);
        if (client.pleaseWaitComponent != null) {
            InterfaceComponent.invalidate(client.pleaseWaitComponent);
            client.pleaseWaitComponent = null;
        }

        ContextMenu.method317();
        InterfaceComponent.revalidateScroll(client.interfaces[key >> 16], component, false);
        InterfaceComponent.loadAndInitialize(id);
        if (client.rootInterfaceIndex != -1) {
            InterfaceComponent.executeCloseListeners(client.rootInterfaceIndex, 1);
        }

        return sub;
    }
}
