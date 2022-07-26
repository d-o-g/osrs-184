package jag.worldmap;

import jag.game.client;
import jag.graphics.*;

public final class WorldMapElement {

    public static IndexedSprite aDoublyNode_Sub24_Sub4_363;
    final int[] tileColors;

    public WorldMapElement() {
        tileColors = new int[4096];
    }

    public WorldMapElement(int[] tileColors) {
        this.tileColors = tileColors;
    }

    public static void method242(String var0, boolean var1) {
        if (client.displayLoadingMessages) {
            byte var2 = 4;
            int var3 = var2 + 6;
            int var4 = var2 + 6;
            int var5 = Font.p12full.method1144(var0, 250);
            int var6 = Font.p12full.method1150(var0, 250) * 13;
            JagGraphics.fillRect(var3 - var2, var4 - var2, var2 + var5 + var2, var2 + var6 + var2, 0);
            JagGraphics.method1372(var3 - var2, var4 - var2, var2 + var5 + var2, var2 + var2 + var6, 16777215);
            Font.p12full.method1149(var0, var3, var4, var5, var6, 16777215, -1, 1, 1, 0);
            int var7 = var3 - var2;
            int var8 = var4 - var2;
            int var9 = var5 + var2 + var2;
            int var10 = var2 + var6 + var2;

            for (int var11 = 0; var11 < client.renderedComponentCount; ++var11) {
                if (client.interfacePositionsX[var11] + client.interfaceWidths[var11] > var7 && client.interfacePositionsX[var11] < var9 + var7 && client.interfaceHeights[var11] + client.interfacePositionsY[var11] > var8 && client.interfacePositionsY[var11] < var8 + var10) {
                    client.renderedComponents[var11] = true;
                }
            }

            if (var1) {
                client.graphicsProvider.drawGame(0, 0);
            } else {
                for (int var15 = 0; var15 < client.renderedComponentCount; ++var15) {
                    if (client.interfacePositionsX[var15] + client.interfaceWidths[var15] > var3 && client.interfacePositionsX[var15] < var3 + var5 && client.interfacePositionsY[var15] + client.interfaceHeights[var15] > var4 && client.interfacePositionsY[var15] < var6 + var4) {
                        client.aBooleanArray1087[var15] = true;
                    }
                }
            }

        }
    }

    public static boolean method243() {
        return client.aBoolean1037;
    }

    public int getTileColor(int x, int y) {
        return tileColors[x + y * 64];
    }
}
