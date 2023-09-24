package jagex.oldscape.client;

import jagex.datastructure.Node;

public class HitUpdate extends Node {

  public int startCycle;
  public int startWidth;
  public int currentWidth;
  public int currentCycle;

  public HitUpdate(int startCycle, int startWidth, int width, int cycle) {
    this.startCycle = startCycle;
    this.startWidth = startWidth;
    this.currentWidth = width;
    this.currentCycle = cycle;
  }

  public void set(int startCycle, int startWidth, int width, int cycle) {
    this.startCycle = startCycle;
    this.startWidth = startWidth;
    this.currentWidth = width;
    this.currentCycle = cycle;
  }
}
