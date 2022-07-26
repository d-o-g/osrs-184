package jag.commons.input;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class MouseWheel implements MouseWheelProvider, MouseWheelListener {

    public static MouseWheelProvider provider;
    public int ptr;

    public MouseWheel() {
        ptr = 0;
    }

    public void method223(Component var1) {
        var1.addMouseWheelListener(this);
    }

    public void method224(Component var1) {
        var1.removeMouseWheelListener(this);
    }

    public synchronized int getAndReset() {
        int old = ptr;
        ptr = 0;
        return old;
    }

    public synchronized void mouseWheelMoved(MouseWheelEvent e) {
        ptr += e.getWheelRotation();
    }
}
