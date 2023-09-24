package jagex.jagex3.util;

import netscape.javascript.JSObject;

import java.applet.Applet;

public class JSObjectUtil {

  public static void eval(Applet var0, String var1) {
    JSObject.getWindow(var0).eval(var1);
  }

  public static Object call(Applet var0, String var1) {
    return JSObject.getWindow(var0).call(var1, (Object[]) null);
  }
}
