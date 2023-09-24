package jagex.jagex3.js5;

import jagex.oldscape.client.Server;
import jagex.oldscape.client.client;
import jagex.oldscape.client.social.AssociateComparator_Sub2;
import jagex.jagex3.graphics.Sprite;

//TODO name
public class LoadedArchive {

  public static byte[][] objectFiles;
  public static Sprite[] aSpriteArray429;
  public static int[] tileBlendSaturation;

  public final int entryCount;
  public final Archive archive;
  public int validEntryCount;

  public LoadedArchive(Archive archive) {
    this.validEntryCount = 0;
    this.archive = archive;
    this.entryCount = archive.childrenCount();
  }

  public static int method288(Server var0, Server var1, int var2, boolean var3, int var4, boolean var5) {
    int var6 = AssociateComparator_Sub2.method606(var0, var1, var2, var3);
    if (var6 != 0) {
      return var3 ? -var6 : var6;
    }
    if (var4 == -1) {
      return 0;
    }
    int var7 = AssociateComparator_Sub2.method606(var0, var1, var4, var5);
    return var5 ? -var7 : var7;
  }

  public static void add(Archive archive) {
    LoadedArchive var2 = new LoadedArchive(archive);
    client.archives.add(var2);
    client.archiveEntryCount += var2.entryCount;
  }

  public boolean method291() {
    validEntryCount = 0;

    for (int i = 0; i < entryCount; ++i) {
      if (!archive.isPresent(i) || archive.isValid(i)) {
        ++validEntryCount;
      }
    }

    return validEntryCount >= entryCount;
  }
}
