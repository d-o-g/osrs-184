package jagex.oldscape.client.scene;

public final class TilePaint {

  public final int anInt2002;
  public final int anInt2003;
  public final int rgb;
  public final int anInt2001;
  public final int anInt2000;
  public final int texture;

  public boolean flatShade;

  public TilePaint(int var1, int var2, int var3, int var4, int texture, int rgb, boolean flatShade) {
    anInt2002 = var1;
    anInt2003 = var2;
    anInt2001 = var3;
    anInt2000 = var4;
    this.texture = texture;
    this.rgb = rgb;
    this.flatShade = flatShade;
  }
}
