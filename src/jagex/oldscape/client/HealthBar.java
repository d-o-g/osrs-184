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
