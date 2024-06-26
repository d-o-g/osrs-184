package jagex.oldscape.client.minimenu;

import jagex.oldscape.client.Component;
import jagex.oldscape.script.ScriptEvent;

public class ComponentSelection {

  public static String name = null;
  public static String action = null;

  public static boolean state = false;

  public static int index = -1;
  public static int target = -1;

  public static int targetFlags;
  public static int uid;

  public static void process() {
    if (state) {
      Component component = Component.lookup(uid, index);
      if (component != null && component.targetExitListeners != null) {
        ScriptEvent event = new ScriptEvent();
        event.component = component;
        event.args = component.targetExitListeners;
        ScriptEvent.fire(event);
      }

      state = false;
      Component.invalidate(component);
    }
  }

  public static void select(int uid, int index, int flags, int target) {
    Component component = Component.lookup(uid, index);
    if (component != null && component.targetEnterListeners != null) {
      ScriptEvent event = new ScriptEvent();
      event.component = component;
      event.args = component.targetEnterListeners;
      ScriptEvent.fire(event);
    }

    state = true;
    ComponentSelection.target = target;
    ComponentSelection.uid = uid;
    ComponentSelection.index = index;
    ComponentSelection.targetFlags = flags;
    Component.invalidate(component);
  }

}
