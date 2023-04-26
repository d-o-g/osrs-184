package jag.game.scene;

import jag.game.client;
import jag.graphics.Sprite;

public class HintArrow {

    public static Sprite[] sprites;
    public static Sprite[] overheadSprites;

    public static int state = 0;
    public static int type = 0;

    public static int npc = 0;
    public static int player = 0;

    public static int x = 0;
    public static int y = 0;
    public static int z = 0;

    public static int insetX = 0;
    public static int insetY = 0;

    public static void draw(int baseX, int baseY) {
        if (type == 2) {
            SceneGraph.absoluteToViewport((x - client.baseX << 7) + insetX, (y - client.baseY << 7) + insetY, z * 4);
            if (client.viewportRenderX > -1 && client.ticks % 20 < 10) {
                overheadSprites[0].renderAlphaAt(baseX + client.viewportRenderX - 12, client.viewportRenderY + baseY - 28);
            }
        }
    }
}
