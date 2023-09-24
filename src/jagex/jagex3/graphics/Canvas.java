package jagex.jagex3.graphics;

import jagex.jagex3.sound.DefaultAudioSystemProvider;
import jagex.oldscape.stockmarket.StockmarketListingNameComparator;
import jagex.oldscape.client.type.ItemDefinition;
import jagex.jagex3.js5.ReferenceTable;
import jagex.oldscape.script.ScriptEvent;
import jagex.statics.Statics5;
import jagex.oldscape.client.worldmap.WorldMapLabelIcon;

import java.awt.*;

public final class Canvas extends java.awt.Canvas {

  public final Component component;

  public Canvas(Component component) {
    this.component = component;
  }

  public static Sprite[] method643(ReferenceTable var0, int var1, int var2) {
    return !SpriteProvider.loadSprites(var0, var1, var2) ? null : Sprite.method194();
  }

  public static void method640(String var0, boolean var1) {
    var0 = var0.toLowerCase();
    short[] var2 = new short[16];
    int var3 = 0;

    for (int var4 = 0; var4 < Statics5.anInt838; ++var4) {
      ItemDefinition var5 = ItemDefinition.get(var4);
      if ((!var1 || var5.stockMarketable) && var5.noteTemplateId == -1 && var5.name.toLowerCase().contains(var0)) {
        if (var3 >= 250) {
          DefaultAudioSystemProvider.anInt142 = -1;
          WorldMapLabelIcon.grandExchangeSearchResults = null;
          return;
        }

        if (var3 >= var2.length) {
          short[] var8 = new short[var2.length * 2];

          System.arraycopy(var2, 0, var8, 0, var3);

          var2 = var8;
        }

        var2[var3++] = (short) var4;
      }
    }

    WorldMapLabelIcon.grandExchangeSearchResults = var2;
    ScriptEvent.anInt748 = 0;
    DefaultAudioSystemProvider.anInt142 = var3;
    String[] var6 = new String[DefaultAudioSystemProvider.anInt142];

    for (int var7 = 0; var7 < DefaultAudioSystemProvider.anInt142; ++var7) {
      var6[var7] = ItemDefinition.get(var2[var7]).name;
    }

    short[] var10 = WorldMapLabelIcon.grandExchangeSearchResults;
    StockmarketListingNameComparator.method328(var6, var10, 0, var6.length - 1);
  }

  public void update(Graphics g) {
    this.component.update(g);
  }

  public void paint(Graphics g) {
    this.component.paint(g);
  }
}
