package jagex.oldscape.client.social;

import jagex.oldscape.client.client;
import jagex.oldscape.client.type.EnumDefinition;
import jagex.messaging.Buffer;

public class AssociateComparator_Sub4<T extends Associate<T>> extends AssociateComparator<T> {

  public static boolean aBoolean804;
  public static int anInt803;
  public final boolean aBoolean764;

  public AssociateComparator_Sub4(boolean var1) {
    this.aBoolean764 = var1;
  }

  public static EnumDefinition method664(int var0) {
    EnumDefinition var1 = EnumDefinition.cache.get(var0);
    if (var1 != null) {
      return var1;
    }
    byte[] var2 = EnumDefinition.table.unpack(8, var0);
    var1 = new EnumDefinition();
    if (var2 != null) {
      var1.decode(new Buffer(var2));
    }

    EnumDefinition.cache.put(var1, var0);
    return var1;
  }

  int method604(T var1, T var2) {
    if (client.currentWorld == var1.world && var2.world == client.currentWorld) {
      return this.aBoolean764 ? var1.index - var2.index : var2.index - var1.index;
    }
    return this.method1135(var1, var2);
  }

  public int compare(T var1, T var2) {
    return this.method604(var1, var2);
  }
}
