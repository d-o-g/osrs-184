package jagex.oldscape.client.type;

import jagex.oldscape.Identikit;
import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.instrusive.linklist.NodeDeque;
import jagex.oldscape.client.InterfaceComponent;
import jagex.jagex3.graphics.IndexedSprite;
import jagex.jagex3.js5.ReferenceTable;

public class AnimationFrameGroup extends DoublyLinkedNode {

  public static InterfaceComponent dragComponent;
  public static IndexedSprite[] aDoublyNode_Sub24_Sub4Array801;
  public static ReferenceTable aReferenceTable1123;
  public final Identikit[] identikits;

  public AnimationFrameGroup(ReferenceTable table, ReferenceTable skeletons, int group) {
    NodeDeque<Skeleton> entries = new NodeDeque<>();
    int count = table.getFileCount(group);
    int[] files = table.getFileIds(group);

    identikits = new Identikit[count];
    for (int file : files) {
      byte[] data = table.unpack(group, file);
      Skeleton skeleton = null;
      int id = (data[0] & 255) << 8 | data[1] & 255;
      for (Skeleton entry = entries.head(); entry != null; entry = entries.next()) {
        if (id == entry.id) {
          skeleton = entry;
          break;
        }
      }

      if (skeleton == null) {
        skeleton = new Skeleton(id, skeletons.getFile(id, 0));
        entries.add(skeleton);
      }
      identikits[file] = new Identikit(data, skeleton);
    }

  }

  public static AnimationFrameGroup get(int var0) {
    AnimationFrameGroup var1 = AnimationSequence.frames.get(var0);
    if (var1 != null) {
      return var1;
    }
    ReferenceTable var2 = AnimationSequence.aReferenceTable697;
    ReferenceTable var3 = aReferenceTable1123;
    boolean var4 = true;
    int[] var5 = var2.getFileIds(var0);

    for (int aVar5 : var5) {
      byte[] var7 = var2.getFile(var0, aVar5);
      if (var7 == null) {
        var4 = false;
      } else {
        int var8 = (var7[0] & 255) << 8 | var7[1] & 255;
        byte[] var9 = var3.getFile(var8, 0);
        if (var9 == null) {
          var4 = false;
        }
      }
    }

    AnimationFrameGroup var10;
    if (!var4) {
      var10 = null;
    } else {
      try {
        var10 = new AnimationFrameGroup(var2, var3, var0);
      } catch (Exception var12) {
        var10 = null;
      }
    }

    if (var10 != null) {
      AnimationSequence.frames.put(var10, var0);
    }

    return var10;
  }

  public boolean isShowing(int var1) {
    return identikits[var1].showing;
  }
}
