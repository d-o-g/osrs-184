package jagex.jagex3.util;

import jagex.jagex3.client.applet.GameShell;
import jagex.oldscape.client.worldmap.WorldMapController;
import netscape.javascript.JSObject;

import java.applet.Applet;
import java.awt.*;
import java.net.URI;
import java.net.URL;

public class Browser {

  public static boolean method191(String var0, int var1) {
    if (var1 == 0) {
      try {
        if (!WorldMapController.osName.startsWith("win")) {
          throw new Exception();
        }
        if (!var0.startsWith("http://") && !var0.startsWith("https://")) {
          throw new Exception();
        }
        String var13 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?&=,.%+-_#:/*";

        for (int var4 = 0; var4 < var0.length(); ++var4) {
          if (var13.indexOf(var0.charAt(var4)) == -1) {
            throw new Exception();
          }
        }

        Runtime.getRuntime().exec("cmd /c start \"j\" \"" + var0 + "\"");
        return true;
      } catch (Throwable var8) {
        return false;
      }
    }
    if (var1 == 1) {
      try {
        Applet var5 = GameShell.applet;
        Object[] var6 = new Object[]{(new URL(GameShell.applet.getCodeBase(), var0)).toString()};
        Object var3 = JSObject.getWindow(var5).call("openjs", var6);
        return var3 != null;
      } catch (Throwable var9) {
        return false;
      }
    }
    if (var1 == 2) {
      try {
        GameShell.applet.getAppletContext().showDocument(new URL(GameShell.applet.getCodeBase(), var0), "_blank");
        return true;
      } catch (Exception var10) {
        return false;
      }
    }
    if (var1 == 3) {
      try {
        JSObjectUtil.call(GameShell.applet, "loggedout");
      } catch (Throwable ignored) {
      }

      try {
        GameShell.applet.getAppletContext().showDocument(new URL(GameShell.applet.getCodeBase(), var0), "_top");
        return true;
      } catch (Exception var11) {
        return false;
      }
    }
    throw new IllegalArgumentException();
  }

  public static void openURL0(String var0, boolean var1, boolean var2) {
    openURL(var0, var1, var2);
  }

  public static void openURL(String url, boolean var1, boolean var3) {
    if (var1) {
      if (!var3 && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
          Desktop.getDesktop().browse(new URI(url));
          return;
        } catch (Exception ignored) {

        }
      }

      if (WorldMapController.osName.startsWith("win") && !var3) {
        method297(url, 0);
        return;
      }

      if (WorldMapController.osName.startsWith("mac")) {
        method191(url, 1);
        return;
      }

      method297(url, 2);
    } else {
      method297(url, 3);
    }

  }

  public static boolean method297(String var0, int var1) {
    return method191(var0, var1);
  }
}
