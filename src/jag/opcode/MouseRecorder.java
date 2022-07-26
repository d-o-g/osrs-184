package jag.opcode;

import jag.SerializableLong;
import jag.commons.input.Mouse;
import jag.game.InterfaceComponent;
import jag.game.SubInterface;
import jag.game.client;
import jag.js5.ReferenceTable;
import jag.script.ScriptEvent;

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

    public static void method265(InterfaceComponent[] var0, int var1) {
        for (InterfaceComponent var3 : var0) {
            if (var3 != null) {
                if (var3.type == 0) {
                    if (var3.subcomponents != null) {
                        method265(var3.subcomponents, var1);
                    }

                    SubInterface var4 = client.subInterfaces.lookup(var3.uid);
                    if (var4 != null) {
                        InterfaceComponent.method118(var4.id, var1);
                    }
                }

                ScriptEvent var5;
                if (var1 == 0 && var3.anObjectArray1397 != null) {
                    var5 = new ScriptEvent();
                    var5.component = var3;
                    var5.args = var3.anObjectArray1397;
                    ScriptEvent.fire(var5);
                }

                if (var1 == 1 && var3.anObjectArray1393 != null) {
                    if (var3.subComponentIndex >= 0) {
                        InterfaceComponent var6 = InterfaceComponent.lookup(var3.uid);
                        if (var6 == null || var6.subcomponents == null || var3.subComponentIndex >= var6.subcomponents.length || var3 != var6.subcomponents[var3.subComponentIndex]) {
                            continue;
                        }
                    }

                    var5 = new ScriptEvent();
                    var5.component = var3;
                    var5.args = var3.anObjectArray1393;
                    ScriptEvent.fire(var5);
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
        InterfaceComponent.valid = new boolean[InterfaceComponent.aReferenceTable1375.childrenCount()];
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
