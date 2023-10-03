package jagex.oldscape.client.social;

public class AssociateComparatorByName<T extends Associate<T>> extends AssociateComparator<T> {

  public final boolean aBoolean764;

  public AssociateComparatorByName(boolean var1) {
    this.aBoolean764 = var1;
  }

  int method604(T var1, T var2) {
    if (var1.world != 0 && var2.world != 0) {
      return this.aBoolean764 ? var1.getDisplayName().compare0(var2.getDisplayName()) : var2.getDisplayName().compare0(var1.getDisplayName());
    }
    return this.method1135(var1, var2);
  }

  public int compare(T var1, T var2) {
    return this.method604(var1, var2);
  }
}
