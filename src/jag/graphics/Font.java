package jag.graphics;

public final class Font extends BaseFont {

    public static Font p11_2;
    public static Font p12full;
    public static Font b12full;
    public static Font p11;

    public Font(byte[] var1, int[] var2, int[] var3, int[] var4, int[] var5, byte[][] var7) {
        super(var1, var2, var3, var4, var5, var7);
    }

    public Font(byte[] var1) {
        super(var1);
    }

    void method272(byte[] var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = var3 * JagGraphics.drawingAreaWidth + var2;
        int var8 = JagGraphics.drawingAreaWidth - var4;
        int var9 = 0;
        int var10 = 0;
        int var11;
        if (var3 < JagGraphics.drawingAreaTop) {
            var11 = JagGraphics.drawingAreaTop - var3;
            var5 -= var11;
            var3 = JagGraphics.drawingAreaTop;
            var10 += var11 * var4;
            var7 += var11 * JagGraphics.drawingAreaWidth;
        }

        if (var3 + var5 > JagGraphics.drawingAreaRight) {
            var5 -= var3 + var5 - JagGraphics.drawingAreaRight;
        }

        if (var2 < JagGraphics.drawingAreaLeft) {
            var11 = JagGraphics.drawingAreaLeft - var2;
            var4 -= var11;
            var2 = JagGraphics.drawingAreaLeft;
            var10 += var11;
            var7 += var11;
            var9 += var11;
            var8 += var11;
        }

        if (var2 + var4 > JagGraphics.drawingAreaBottom) {
            var11 = var2 + var4 - JagGraphics.drawingAreaBottom;
            var4 -= var11;
            var9 += var11;
            var8 += var11;
        }

        if (var4 > 0 && var5 > 0) {
            BaseFont.method1156(JagGraphics.drawingAreaPixels, var1, var6, var10, var7, var4, var5, var8, var9);
        }
    }

    void method273(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        int var8 = var3 * JagGraphics.drawingAreaWidth + var2;
        int var9 = JagGraphics.drawingAreaWidth - var4;
        int var10 = 0;
        int var11 = 0;
        int var12;
        if (var3 < JagGraphics.drawingAreaTop) {
            var12 = JagGraphics.drawingAreaTop - var3;
            var5 -= var12;
            var3 = JagGraphics.drawingAreaTop;
            var11 += var12 * var4;
            var8 += var12 * JagGraphics.drawingAreaWidth;
        }

        if (var3 + var5 > JagGraphics.drawingAreaRight) {
            var5 -= var3 + var5 - JagGraphics.drawingAreaRight;
        }

        if (var2 < JagGraphics.drawingAreaLeft) {
            var12 = JagGraphics.drawingAreaLeft - var2;
            var4 -= var12;
            var2 = JagGraphics.drawingAreaLeft;
            var11 += var12;
            var8 += var12;
            var10 += var12;
            var9 += var12;
        }

        if (var2 + var4 > JagGraphics.drawingAreaBottom) {
            var12 = var2 + var4 - JagGraphics.drawingAreaBottom;
            var4 -= var12;
            var10 += var12;
            var9 += var12;
        }

        if (var4 > 0 && var5 > 0) {
            BaseFont.method1158(JagGraphics.drawingAreaPixels, var1, var6, var11, var8, var4, var5, var9, var10, var7);
        }
    }
}
