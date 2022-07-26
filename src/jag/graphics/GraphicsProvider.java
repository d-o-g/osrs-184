package jag.graphics;

public abstract class GraphicsProvider {
    public static IndexedSprite titlebuttonSprite;
    public int[] anIntArray1818;
    public int width;
    public int height;

    protected GraphicsProvider() {
    }

    public final void method1318() {
        JagGraphics.setTarget(this.anIntArray1818, this.width, this.height);
    }

    public abstract void drawGame(int var1, int var2);

    public abstract void drawComponent(int var1, int var2, int var3, int var4);
}
