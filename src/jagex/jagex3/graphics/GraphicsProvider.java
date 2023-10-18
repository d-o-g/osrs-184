package jagex.jagex3.graphics;

public abstract class GraphicsProvider {

  public static IndexedSprite titlebuttonSprite;

  public int[] pixels;

  public int width;
  public int height;

  protected GraphicsProvider() {

  }

  public final void method1318() {
    JagGraphics.setTarget(this.pixels, this.width, this.height);
  }

  public abstract void drawGame(int var1, int var2);

  public abstract void drawComponent(int var1, int var2, int var3, int var4);
}
