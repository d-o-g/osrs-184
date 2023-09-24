package jagex.oldscape.client.worldmap;

import jagex.oldscape.client.type.EnumDefinition;

public class WorldMapCacheFeature {

  public static final WorldMapCacheFeature DETAILS = new WorldMapCacheFeature("details");
  public static final WorldMapCacheFeature COMPOSITEMAP = new WorldMapCacheFeature("compositemap");
  public static final WorldMapCacheFeature COMPOSITETEXTURE = new WorldMapCacheFeature("compositetexture");
  public static final WorldMapCacheFeature LABELS = new WorldMapCacheFeature("labels");

  static final WorldMapCacheFeature AREA = new WorldMapCacheFeature("area");

  public static int anInt296;

  public final String name;

  WorldMapCacheFeature(String var1) {
    this.name = var1;
  }

  public static void method178() {
    EnumDefinition.sendFullIgnoreListMessage();
  }

}
