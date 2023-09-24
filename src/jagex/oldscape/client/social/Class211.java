package jagex.oldscape.client.social;

import java.util.Comparator;

public class Class211 implements Comparator {

  final boolean aBoolean1759;

  public Class211(boolean var1) {
    this.aBoolean1759 = var1;
  }

  int method1296(Chatter var1, Chatter var2) {
    return this.aBoolean1759 ? var1.getDisplayName().compare0(var2.getDisplayName()) : var2.getDisplayName().compare0(var1.getDisplayName());
  }

  public boolean equals(Object var1) {
    return super.equals(var1);
  }

  public int compare(Object var1, Object var2) {
    return this.method1296((Chatter) var1, (Chatter) var2);
  }
}
