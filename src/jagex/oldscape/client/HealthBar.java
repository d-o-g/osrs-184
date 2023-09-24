package jagex.oldscape.client;

import jagex.datastructure.instrusive.linklist.LinkedList;
import jagex.datastructure.Node;
import jagex.oldscape.client.type.HealthBarDefinition;

public class HealthBar extends Node {

  public final LinkedList<HitUpdate> hitsplats = new LinkedList<>();

  public final HealthBarDefinition definition;

  public HealthBar(HealthBarDefinition definition) {
    this.definition = definition;
  }

  public static String toString(int var0, boolean var1) {
    if (var1 && var0 >= 0) {
      int var2 = var0;
      String var3;
      if (var1) {
        int var4 = 2;

        for (int var5 = var0 / 10; var5 != 0; ++var4) {
          var5 /= 10;
        }

        char[] var6 = new char[var4];
        var6[0] = '+';

        for (int var7 = var4 - 1; var7 > 0; --var7) {
          int var8 = var2;
          var2 /= 10;
          int var9 = var8 - var2 * 10;
          if (var9 >= 10) {
            var6[var7] = (char) (var9 + 87);
          } else {
            var6[var7] = (char) (var9 + 48);
          }
        }

        var3 = new String(var6);
      } else {
        var3 = Integer.toString(var0, 10);
      }

      return var3;
    }
    return Integer.toString(var0);
  }

  public void update(int cycle, int width, int currWidth, int currCycle) {
    HitUpdate pending = null;
    int var6 = 0;

    for (HitUpdate h = hitsplats.head(); h != null; h = hitsplats.next()) {
      ++var6;
      if (h.startCycle == cycle) {
        h.set(cycle, width, currWidth, currCycle);
        return;
      }

      if (h.startCycle <= cycle) {
        pending = h;
      }
    }

    if (pending == null) {
      if (var6 < 4) {
        hitsplats.addLast(new HitUpdate(cycle, width, currWidth, currCycle));
      }

    } else {
      LinkedList.insertBefore(new HitUpdate(cycle, width, currWidth, currCycle), pending);
      if (var6 >= 4) {
        hitsplats.head().unlink();
      }

    }
  }

  public HitUpdate method695(int var1) {
    HitUpdate var2 = hitsplats.head();
    if (var2 != null && var2.startCycle <= var1) {
      for (HitUpdate var3 = hitsplats.next(); var3 != null && var3.startCycle <= var1; var3 = hitsplats.next()) {
        var2.unlink();
        var2 = var3;
      }

      if (definition.anInt367 + var2.currentCycle + var2.startCycle > var1) {
        return var2;
      }
      var2.unlink();
      return null;
    }
    return null;
  }

  public boolean method693() {
    return hitsplats.isEmpty();
  }
}
