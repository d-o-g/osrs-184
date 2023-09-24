package jagex.oldscape.client.social;

import jagex.oldscape.client.client;

public class AssociateComparatorByWorldAndName<T extends Associate<T>> extends AssociateComparator<T> {

  public final boolean aBoolean764;

  public AssociateComparatorByWorldAndName(boolean var1) {
    this.aBoolean764 = var1;
  }

  int method604(T var1, T var2) {
    if (client.currentWorld == var1.world && var2.world == client.currentWorld) {
      return this.aBoolean764 ? var1.getDisplayName().compare0(var2.getDisplayName()) : var2.getDisplayName().compare0(var1.getDisplayName());
    }
    return this.method1135(var1, var2);
  }

  public int compare(T var1, T var2) {
    return this.method604(var1, var2);
  }
}
