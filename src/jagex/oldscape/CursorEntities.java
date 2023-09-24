package jagex.oldscape;

public class CursorEntities {

  public static final long[] uids = new long[1000];
  public static int count = 0;
  public static boolean mouseInViewport = false;
  public static int viewportMouseX = 0;
  public static int anInt654;
  public static int viewportMouseY = 0;
  public static boolean aBoolean660 = false;
  public static int anInt659;
  public static int anInt657;
  public static int anInt658;
  public static int anInt662;
  public static int anInt655;

  public static void method238(int var0, int var1) {
    viewportMouseX = var0;
    viewportMouseY = var1;
    mouseInViewport = true;
    count = 0;
    aBoolean660 = false;
  }

  public static void method419() {
    mouseInViewport = false;
    count = 0;
  }
}
