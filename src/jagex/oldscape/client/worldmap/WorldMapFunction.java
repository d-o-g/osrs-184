package jagex.oldscape.client.worldmap;

import jagex.oldscape.EnumType;
import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.jagex3.graphics.Sprite;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class WorldMapFunction extends DoublyLinkedNode {

  public static final ReferenceCache cache = new ReferenceCache(256);
  public static WorldMapFunction[] loaded;
  public static int count;
  public static ReferenceTable table;

  public final int objectId;
  public int spriteId;
  public String aString1474;
  public HAlign hAlign;
  public int anInt379;
  public VAlign vAlign;
  public int fontSize;
  public String[] menuActions;
  public int category;
  public String aString1476;
  int[] anIntArray1469;
  int anInt375;
  int anInt696;
  int anInt788;
  int anInt702;
  int anInt666;
  int[] anIntArray787;
  byte[] aByteArray1471;

  public WorldMapFunction(int objectId) {
    spriteId = -1;
    anInt375 = -1;
    fontSize = 0;
    menuActions = new String[5];
    anInt696 = Integer.MAX_VALUE;
    anInt702 = Integer.MAX_VALUE;
    anInt788 = Integer.MIN_VALUE;
    anInt666 = Integer.MIN_VALUE;
    hAlign = HAlign.anEnum_Sub9_1485;
    vAlign = VAlign.anEnum_Sub8_1481;
    category = -1;
    this.objectId = objectId;
  }

  public static WorldMapFunction get(int var0) {
    return var0 >= 0 && var0 < loaded.length && loaded[var0] != null ? loaded[var0] : new WorldMapFunction(var0);
  }

  public Sprite getSprite() {
    int id = this.spriteId;
    return this.getSprite(id);
  }

  Sprite getSprite(int var1) {
    if (var1 < 0) {
      return null;
    }
    Sprite var2 = (Sprite) cache.get(var1);
    if (var2 != null) {
      return var2;
    }
    var2 = Sprite.get(table, var1, 0);
    if (var2 != null) {
      cache.put(var2, var1);
    }

    return var2;
  }

  void decode(Buffer var1, int var2) {
    if (var2 == 1) {
      this.spriteId = var1.method1051();
    } else if (var2 == 2) {
      this.anInt375 = var1.method1051();
    } else if (var2 == 3) {
      this.aString1474 = var1.gstr();
    } else if (var2 == 4) {
      this.anInt379 = var1.g3();
    } else if (var2 == 5) {
      var1.g3();
    } else if (var2 == 6) {
      this.fontSize = var1.g1();
    } else {
      int var3;
      if (var2 == 7) {
        var3 = var1.g1();
        if ((var3 & 1) == 0) {

        }

        if ((var3 & 2) == 2) {

        }
      } else if (var2 == 8) {
        var1.g1();
      } else if (var2 >= 10 && var2 <= 14) {
        this.menuActions[var2 - 10] = var1.gstr();
      } else if (var2 == 15) {
        var3 = var1.g1();
        this.anIntArray1469 = new int[var3 * 2];

        for (int var4 = 0; var4 < var3 * 2; ++var4) {
          this.anIntArray1469[var4] = var1.g2b();
        }

        var1.g4();
        int var4 = var1.g1();
        this.anIntArray787 = new int[var4];

        for (int var5 = 0; var5 < this.anIntArray787.length; ++var5) {
          this.anIntArray787[var5] = var1.g4();
        }

        this.aByteArray1471 = new byte[var3];

        for (int var5 = 0; var5 < var3; ++var5) {
          this.aByteArray1471[var5] = var1.g1b();
        }
      } else if (var2 != 16) {
        if (var2 == 17) {
          this.aString1476 = var1.gstr();
        } else if (var2 == 18) {
          var1.method1051();
        } else if (var2 == 19) {
          this.category = var1.g2();
        } else if (var2 == 21) {
          var1.g4();
        } else if (var2 == 22) {
          var1.g4();
        } else if (var2 == 23) {
          var1.g1();
          var1.g1();
          var1.g1();
        } else if (var2 == 24) {
          var1.g2b();
          var1.g2b();
        } else if (var2 == 25) {
          var1.method1051();
        } else if (var2 == 28) {
          var1.g1();
        } else if (var2 == 29) {
          this.hAlign = (HAlign) EnumType.getByOrdinal(HAlign.getValues(), var1.g1());
        } else if (var2 == 30) {
          VAlign[] var6 = new VAlign[]{VAlign.anEnum_Sub8_1480, VAlign.anEnum_Sub8_1479, VAlign.anEnum_Sub8_1481};
          this.vAlign = (VAlign) EnumType.getByOrdinal(var6, var1.g1());
        }
      }
    }

  }

  public int method1082() {
    return this.objectId;
  }

  public void decode(Buffer var1) {
    while (true) {
      int var2 = var1.g1();
      if (var2 == 0) {
        return;
      }

      this.decode(var1, var2);
    }
  }

  public void method775() {
    if (this.anIntArray1469 != null) {
      for (int var1 = 0; var1 < this.anIntArray1469.length; var1 += 2) {
        if (this.anIntArray1469[var1] < this.anInt696) {
          this.anInt696 = this.anIntArray1469[var1];
        } else if (this.anIntArray1469[var1] > this.anInt788) {
          this.anInt788 = this.anIntArray1469[var1];
        }

        if (this.anIntArray1469[var1 + 1] < this.anInt702) {
          this.anInt702 = this.anIntArray1469[var1 + 1];
        } else if (this.anIntArray1469[var1 + 1] > this.anInt666) {
          this.anInt666 = this.anIntArray1469[var1 + 1];
        }
      }
    }

  }
}
