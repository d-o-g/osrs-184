package jagex.oldscape;

import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class BootSprites {

  public int compass;
  public int anInt1658;
  public int mapScenes;
  public int skullIcons;
  public int prayerIcons;
  public int anInt1648;
  public int anInt1649;
  public int anInt1657;
  public int anInt1654;
  public int anInt1655;
  public int anInt1651;

  public BootSprites() {
    compass = -1;
    anInt1658 = -1;
    mapScenes = -1;
    skullIcons = -1;
    prayerIcons = -1;
    anInt1648 = -1;
    anInt1649 = -1;
    anInt1657 = -1;
    anInt1654 = -1;
    anInt1655 = -1;
    anInt1651 = -1;
  }

  public void decode(ReferenceTable table) {
    byte[] data = table.unpack(BootSpriteTypes.INSTANCE.id);
    Buffer buffer = new Buffer(data);

    while (true) {
      int opcode = buffer.g1();
      if (opcode == 0) {
        return;
      }

      switch (opcode) {
        case 1: {
          buffer.g3();
          break;
        }

        case 2: {
          compass = buffer.method1051();
          anInt1658 = buffer.method1051();
          mapScenes = buffer.method1051();
          skullIcons = buffer.method1051();
          prayerIcons = buffer.method1051();
          anInt1648 = buffer.method1051();
          anInt1649 = buffer.method1051();
          anInt1657 = buffer.method1051();
          anInt1654 = buffer.method1051();
          anInt1655 = buffer.method1051();
          anInt1651 = buffer.method1051();
          break;
        }
      }
    }
  }
}
