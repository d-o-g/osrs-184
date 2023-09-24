package jagex.jagex3.client.input.mouse;

import jagex.core.time.Clock;

import java.awt.*;
import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, FocusListener {

  private static final int LEFT_MBUTTON = 1;
  private static final int MIDDLE_MBUTTON = 2;
  private static final int RIGHT_MBUTTON = 3;
  public static volatile int idleTime = 0;
  public static int clickMeta = 0;
  public static int x = 0;
  public static int y = 0;
  public static int pressMeta = 0;
  public static long lastMoveTime = 0L;
  public static int clickX = 0;
  public static int clickY = 0;
  public static long timeOfClick = 0L;
  public static Mouse instance = new Mouse();
  public static volatile int pendingPressMeta = 0;
  public static volatile int pendingX = -1;
  public static volatile int pendingClickX = 0;
  public static volatile int pendingY = -1;
  public static volatile int pendingClickY = 0;
  public static volatile long pendingMoveTime = -1L;
  public static int[] mapRegions;
  public static volatile long pendingTimeOfClick = 0L;
  public static volatile int pendingClickMeta = 0;

  public Mouse() {

  }

  public static void destroy() {
    if (instance != null) {
      synchronized (instance) {
        instance = null;
      }
    }
  }

  public static int getAndIncrementIdleTime() {
    return ++idleTime - 1;
  }

  public static void process() {
    synchronized (instance) {
      pressMeta = pendingPressMeta;
      x = pendingX;
      y = pendingY;
      lastMoveTime = pendingMoveTime;
      clickMeta = pendingClickMeta;
      clickX = pendingClickX;
      clickY = pendingClickY;
      timeOfClick = pendingTimeOfClick;
      pendingClickMeta = 0;
    }
  }

  public static void attachListeners(Component target) {
    target.addMouseListener(instance);
    target.addMouseMotionListener(instance);
    target.addFocusListener(instance);
  }

  public final synchronized void mouseMoved(MouseEvent e) {
    if (instance != null) {
      idleTime = 0;
      pendingX = e.getX();
      pendingY = e.getY();
      pendingMoveTime = e.getWhen();
    }
  }

  final int getClickMeta(MouseEvent event) {
    int button = event.getButton();
    if (event.isAltDown() || button == MIDDLE_MBUTTON) {
      return 4;
    }
    return !event.isMetaDown() && button != RIGHT_MBUTTON ? 1 : 2;
  }

  public final synchronized void mouseReleased(MouseEvent e) {
    if (instance != null) {
      idleTime = 0;
      pendingPressMeta = 0;
    }

    if (e.isPopupTrigger()) {
      e.consume();
    }
  }

  public final void mouseClicked(MouseEvent e) {
    if (e.isPopupTrigger()) {
      e.consume();
    }
  }

  public final synchronized void mouseDragged(MouseEvent e) {
    mouseMoved(e);
  }

  public final void focusGained(FocusEvent e) {

  }

  public final synchronized void focusLost(FocusEvent e) {
    if (instance != null) {
      pendingPressMeta = 0;
    }
  }

  public final synchronized void mouseEntered(MouseEvent e) {
    mouseMoved(e);
  }

  public final synchronized void mouseExited(MouseEvent e) {
    if (instance != null) {
      idleTime = 0;
      pendingX = -1;
      pendingY = -1;
      pendingMoveTime = e.getWhen();
    }
  }

  public final synchronized void mousePressed(MouseEvent e) {
    if (instance != null) {
      idleTime = 0;
      pendingClickX = e.getX();
      pendingClickY = e.getY();
      pendingTimeOfClick = Clock.now();
      pendingClickMeta = getClickMeta(e);
      if (pendingClickMeta != 0) {
        pendingPressMeta = pendingClickMeta;
      }
    }

    if (e.isPopupTrigger()) {
      e.consume();
    }
  }
}
