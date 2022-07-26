package jag.graphics;

import java.awt.*;
import java.awt.image.*;
import java.util.Hashtable;

public final class ImageGraphicsProvider extends GraphicsProvider {

    public final Image image;
    public Component target;

    public ImageGraphicsProvider(int width, int height, Component target) {
        super.width = width;
        super.height = height;
        super.anIntArray1818 = new int[height * width + 1];
        DataBufferInt data = new DataBufferInt(super.anIntArray1818, super.anIntArray1818.length);
        DirectColorModel model = new DirectColorModel(32, 16711680, 65280, 255);
        WritableRaster raster = Raster.createWritableRaster(model.createCompatibleSampleModel(super.width, super.height), data, null);
        image = new BufferedImage(model, raster, false, new Hashtable<>());
        setTarget(target);
        method1318();
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        try {
            Shape shape = g.getClip();
            g.clipRect(x, y, width, height);
            g.drawImage(image, 0, 0, target);
            g.setClip(shape);
        } catch (Exception e) {
            target.repaint();
        }

    }

    public void drawGame(Graphics g, int width, int height) {
        try {
            g.drawImage(image, width, height, target);
        } catch (Exception e) {
            target.repaint();
        }
    }

    public void setTarget(Component target) {
        this.target = target;
    }

    public void drawGame(int width, int height) {
        drawGame(target.getGraphics(), width, height);
    }

    public void drawComponent(int var1, int var2, int var3, int var4) {
        draw(target.getGraphics(), var1, var2, var3, var4);
    }
}
