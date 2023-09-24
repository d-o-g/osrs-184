package jagex.messaging;

import jagex.oldscape.SerializableLong;
import jagex.jagex3.client.input.mouse.Mouse;
import jagex.oldscape.client.*;
import jagex.jagex3.js5.ReferenceTable;
import jagex.oldscape.script.ScriptEvent;

public class MouseRecorder implements Runnable {

  public static int anInt388;

  public final Object lock;

  public final int[] xs;
  public final int[] ys;

  public final long[] times;

  public boolean enabled;

  public int caret;

  public MouseRecorder() {
    enabled = true;
    lock = new Object();
    caret = 0;
    xs = new int[500];
    ys = new int[500];
    times = new long[500];
  }

  public static void method265(InterfaceComponent[] group, int var1) {
    for (InterfaceComponent component : group) {
      if (component != null) {
        if (component.type == 0) {
          if (component.subcomponents != null) {
            method265(component.subcomponents, var1);
          }

          SubInterface sub = client.subInterfaces.lookup(component.uid);
          if (sub != null) {
            InterfaceComponent.executeCloseListeners(sub.id, var1);
          }
        }

        if (var1 == 0 && component.anObjectArray1397 != null) {
          ScriptEvent event = new ScriptEvent();
          event.component = component;
          event.args = component.anObjectArray1397;
          ScriptEvent.fire(event);
        }

        if (var1 == 1 && component.anObjectArray1393 != null) {
          if (component.subComponentIndex >= 0) {
            InterfaceComponent var6 = InterfaceComponent.lookup(component.uid);
            if (var6 == null || var6.subcomponents == null || component.subComponentIndex >= var6.subcomponents.length || component != var6.subcomponents[component.subComponentIndex]) {
              continue;
            }
          }

          ScriptEvent event = new ScriptEvent();
          event.component = component;
          event.args = component.anObjectArray1393;
          ScriptEvent.fire(event);
        }
      }
    }

  }

  public static void method263(ReferenceTable var0, ReferenceTable var1, ReferenceTable var2, ReferenceTable var3) {
    InterfaceComponent.aReferenceTable1375 = var0;
    OldConnectionTaskProcessor.aReferenceTable854 = var1;
    InterfaceComponent.aReferenceTable364 = var2;
    SerializableLong.aReferenceTable645 = var3;
    client.interfaces = new InterfaceComponent[InterfaceComponent.aReferenceTable1375.childrenCount()][];
    InterfaceComponent.loaded = new boolean[InterfaceComponent.aReferenceTable1375.childrenCount()];
  }

  public void run() {
    while (enabled) {
      synchronized (lock) {
        if (caret < 500) {
          xs[caret] = Mouse.x;
          ys[caret] = Mouse.y;
          times[caret] = Mouse.lastMoveTime;
          ++caret;
        }
      }

      long time = 49L;

      try {
        Thread.sleep(time);
      } catch (InterruptedException ignored) {

      }

      try {
        Thread.sleep(1L);
      } catch (InterruptedException ignored) {
      }
    }

  }
}
