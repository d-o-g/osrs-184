package jag.game;

import jag.*;
import jag.audi.DefaultAudioSystemProvider;
import jag.commons.*;
import jag.commons.input.*;
import jag.commons.time.Clock;
import jag.game.scene.CollisionMap;
import jag.game.scene.entity.EntityUID;
import jag.game.type.AnimationSequence;
import jag.game.type.TileOverlay;
import jag.graphics.*;
import jag.opcode.OldConnectionTaskProcessor;
import jag.statics.*;
import jag.worldmap.*;
import netscape.javascript.JSObject;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;
import java.util.Date;

public abstract class GameShell extends Applet implements Runnable, FocusListener, WindowListener {

    public static final long[] engineTicks = new long[32];
    public static final long[] clientTicks = new long[32];

    public static final int refreshRate = 20;

    public static OldConnectionTaskProcessor taskProcessor;
    public static GameShell shell = null;
    public static Clock clock;
    public static Applet applet;

    public static volatile boolean aBoolean1314 = true;
    public static boolean canDestroy = false;

    public static long aLong1315 = 0L;
    public static long aLong1298 = -1L;
    public static long aLong1319 = -1L;

    public static int anInt1292 = 0;
    public static int anInt1316 = 0;
    public static int anInt1297 = 1;
    public static int anInt1306 = 500;
    public static int anInt1309;
    public static int anInt1288;

    final EventQueue systemEventQueue;

    protected int width_;
    protected int height_;

    volatile long aLong1304;

    volatile boolean revalidateCanvas;
    volatile boolean focused;
    boolean aBoolean1300;
    boolean aBoolean1307;

    int anInt1320;
    int screenX;
    int screenY;
    int anInt1317;
    int anInt1301;
    int anInt1291;

    Clipboard clipboard;
    MouseWheel mouseWheel;
    Frame frame;
    Canvas canvas;

    protected GameShell() {
        aBoolean1307 = false;
        screenX = 0;
        screenY = 0;
        focused = true;
        aBoolean1300 = false;
        revalidateCanvas = false;
        aLong1304 = 0L;

        EventQueue systemEventQueue = null;
        try {
            systemEventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        } catch (Throwable ignored) {

        }

        this.systemEventQueue = systemEventQueue;
        WorldMapDecor.method382(new DefaultAudioSystemProvider());
    }

    public static void method925(InterfaceComponent[] var0, InterfaceComponent var1, boolean var2) {
        int var3 = var1.viewportWidth != 0 ? var1.viewportWidth : var1.width;
        int var4 = var1.viewportHeight != 0 ? var1.viewportHeight : var1.height;
        InterfaceComponent.method610(var0, var1.uid, var3, var4, var2);
        if (var1.subcomponents != null) {
            InterfaceComponent.method610(var1.subcomponents, var1.uid, var3, var4, var2);
        }

        SubInterface var5 = client.subInterfaces.lookup(var1.uid);
        if (var5 != null) {
            InterfaceComponent.method1112(var5.id, var3, var4, var2);
        }

        if (var1.contentType == 1337) {
        }

    }

    public static void drawScrollbar(int var0, int var1, int var2, int var3, int var4) {
        SerializableString.scrollbars[0].renderAt(var0, var1);
        SerializableString.scrollbars[1].renderAt(var0, var3 + var1 - 16);
        JagGraphics.fillRect(var0, var1 + 16, 16, var3 - 32, client.anInt980);
        int var5 = var3 * (var3 - 32) / var4;
        if (var5 < 8) {
            var5 = 8;
        }

        int var6 = (var3 - 32 - var5) * var2 / (var4 - var3);
        JagGraphics.fillRect(var0, var6 + var1 + 16, 16, var5, client.anInt987);
        JagGraphics.drawVerticalLine(var0, var6 + var1 + 16, var5, client.anInt990);
        JagGraphics.drawVerticalLine(var0 + 1, var6 + var1 + 16, var5, client.anInt990);
        JagGraphics.drawHorizontalLine(var0, var6 + var1 + 16, 16, client.anInt990);
        JagGraphics.drawHorizontalLine(var0, var6 + var1 + 17, 16, client.anInt990);
        JagGraphics.drawVerticalLine(var0 + 15, var6 + var1 + 16, var5, client.anInt983);
        JagGraphics.drawVerticalLine(var0 + 14, var6 + var1 + 17, var5 - 1, client.anInt983);
        JagGraphics.drawHorizontalLine(var0, var6 + var5 + var1 + 15, 16, client.anInt983);
        JagGraphics.drawHorizontalLine(var0 + 1, var5 + var6 + var1 + 14, 15, client.anInt983);
    }

    public static void openURL(String var0, boolean var1, boolean var3) {
        if (var1) {
            if (!var3 && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI(var0));
                    return;
                } catch (Exception ignored) {

                }
            }

            if (WorldMapController.aString325.startsWith("win") && !var3) {
                Statics46.method297(var0, 0);
                return;
            }

            if (WorldMapController.aString325.startsWith("mac")) {
                method191(var0, 1);
                return;
            }

            Statics46.method297(var0, 2);
        } else {
            Statics46.method297(var0, 3);
        }

    }

    public static boolean method191(String var0, int var1) {
        if (var1 == 0) {
            try {
                if (!WorldMapController.aString325.startsWith("win")) {
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
                Applet var5 = applet;
                Object[] var6 = new Object[]{(new URL(applet.getCodeBase(), var0)).toString()};
                Object var3 = JSObject.getWindow(var5).call("openjs", var6);
                return var3 != null;
            } catch (Throwable var9) {
                return false;
            }
        }
        if (var1 == 2) {
            try {
                applet.getAppletContext().showDocument(new URL(applet.getCodeBase(), var0), "_blank");
                return true;
            } catch (Exception var10) {
                return false;
            }
        }
        if (var1 == 3) {
            try {
                JSObjectUtil.call(applet, "loggedout");
            } catch (Throwable ignored) {
            }

            try {
                applet.getAppletContext().showDocument(new URL(applet.getCodeBase(), var0), "_top");
                return true;
            } catch (Exception var11) {
                return false;
            }
        }
        throw new IllegalArgumentException();
    }

    public static String method611(String var0, boolean var1) {
        String var2 = var1 ? "https://" : "http://";
        if (client.gameType == 1) {
            var0 = var0 + "-wtrc";
        } else if (client.gameType == 2) {
            var0 = var0 + "-wtqa";
        } else if (client.gameType == 3) {
            var0 = var0 + "-wtwip";
        } else if (client.gameType == 5) {
            var0 = var0 + "-wti";
        } else if (client.gameType == 4) {
            var0 = "local";
        }

        String var3 = "";
        if (Statics57.aString1162 != null) {
            var3 = "/p=" + Statics57.aString1162;
        }

        String var4 = "runescape.com";
        return var2 + var0 + "." + var4 + "/l=" + WorldMapLabelSize.aClientLocale_525 + "/a=" + WorldMapCacheArea.anInt130 + var3 + "/";
    }

    public static void method1417(String var0) {
        Statics57.aString1162 = var0;

        try {
            String var1 = client.instance.getParameter(Integer.toString(18));
            String var2 = client.instance.getParameter(Integer.toString(13));
            String var3 = var1 + "settings=" + var0 + "; version=1; path=/; domain=" + var2;
            if (var0.length() == 0) {
                var3 = var3 + "; Expires=Thu, 01-Jan-1970 00:00:00 GMT; Max-Age=0";
            } else {
                String var4 = var3 + "; Expires=";
                long var5 = Clock.now() + 94608000000L;
                Jagcalendar.CALENDAR.setTime(new Date(var5));
                int var7 = Jagcalendar.CALENDAR.get(7);
                int var8 = Jagcalendar.CALENDAR.get(5);
                int var9 = Jagcalendar.CALENDAR.get(2);
                int var10 = Jagcalendar.CALENDAR.get(1);
                int var11 = Jagcalendar.CALENDAR.get(11);
                int var12 = Jagcalendar.CALENDAR.get(12);
                int var13 = Jagcalendar.CALENDAR.get(13);
                String var14 = Jagcalendar.DAYS[var7 - 1] + ", " + var8 / 10 + var8 % 10 + "-" + Jagcalendar.MONTHS[0][var9] + "-" + var10 + " " + var11 / 10 + var11 % 10 + ":" + var12 / 10 + var12 % 10 + ":" + var13 / 10 + var13 % 10 + " GMT";
                var3 = var4 + var14 + "; Max-Age=" + 94608000L;
            }

            JSObjectUtil.eval(client.instance, "document.cookie=\"" + var3 + "\"");
        } catch (Throwable ignored) {
        }

    }

    public static void updateClock() {
        clock.update();

        int var0;
        for (var0 = 0; var0 < 32; ++var0) {
            clientTicks[var0] = 0L;
        }

        for (var0 = 0; var0 < 32; ++var0) {
            engineTicks[var0] = 0L;
        }

        anInt1309 = 0;
    }

    public final void destroy() {
        if (this == shell && !canDestroy) {
            aLong1315 = Clock.now();
            long var1 = 4999L;

            try {
                Thread.sleep(var1);
            } catch (InterruptedException ignored) {
            }

            try {
                Thread.sleep(1L);
            } catch (InterruptedException ignored) {
            }

            finallyDestroy();
        }
    }

    protected final boolean method937() {
        String var1 = getDocumentBase().getHost().toLowerCase();
        if (!var1.equals("jagex.com") && !var1.endsWith(".jagex.com")) {
            if (!var1.equals("runescape.com") && !var1.endsWith(".runescape.com")) {
                if (var1.endsWith("127.0.0.1")) {
                    return true;
                }
                while (var1.length() > 0 && var1.charAt(var1.length() - 1) >= '0' && var1.charAt(var1.length() - 1) <= '9') {
                    var1 = var1.substring(0, var1.length() - 1);
                }

                if (var1.endsWith("192.168.1.")) {
                    return true;
                }
                error("invalidhost");
                return false;
            }
            return true;
        }
        return true;
    }

    public final synchronized void paint(Graphics var1) {
        if (this == shell && !canDestroy) {
            focused = true;
            if (Clock.now() - aLong1304 > 1000L) {
                Rectangle var2 = var1.getClipBounds();
                if (var2 == null || var2.width >= client.canvasWidth && var2.height >= client.canvasHeight) {
                    revalidateCanvas = true;
                }
            }

        }
    }

    Bounds method927() {
        Container var1 = getContainer();
        int var2 = Math.max(var1.getWidth(), anInt1301);
        int var3 = Math.max(var1.getHeight(), anInt1291);
        if (frame != null) {
            Insets var4 = frame.getInsets();
            var2 -= var4.left + var4.right;
            var3 -= var4.top + var4.bottom;
        }

        return new Bounds(var2, var3);
    }

    Container getContainer() {
        return (frame != null ? frame : this);
    }

    protected void error(String var1) {
        if (!aBoolean1307) {
            aBoolean1307 = true;
            System.out.println("error_game_" + var1);

            try {
                getAppletContext().showDocument(new URL(getCodeBase(), "error_game_" + var1 + ".ws"), "_self");
            } catch (Exception ignored) {
            }

        }
    }

    final void method934() {
        aBoolean1300 = true;
    }

    final synchronized void finallyDestroy() {
        if (!canDestroy) {
            canDestroy = true;

            try {
                canvas.removeFocusListener(this);
            } catch (Exception ignored) {

            }

            try {
                releaseThreads();
            } catch (Exception ignored) {

            }

            if (frame != null) {
                try {
                    System.exit(0);
                } catch (Throwable ignored) {

                }
            }

            if (taskProcessor != null) {
                try {
                    taskProcessor.kill();
                } catch (Exception ignored) {

                }
            }

            onExit();
        }
    }

    final synchronized void initCanvas() {
        Container container = getContainer();
        if (canvas != null) {
            canvas.removeFocusListener(this);
            container.remove(canvas);
        }

        client.canvasWidth = Math.max(container.getWidth(), anInt1301);
        client.canvasHeight = Math.max(container.getHeight(), anInt1291);
        Insets insets;
        if (frame != null) {
            insets = frame.getInsets();
            client.canvasWidth -= insets.right + insets.left;
            client.canvasHeight -= insets.bottom + insets.top;
        }

        canvas = new jag.graphics.Canvas(this);
        container.setBackground(Color.BLACK);
        container.setLayout(null);
        container.add(canvas);
        canvas.setSize(client.canvasWidth, client.canvasHeight);
        canvas.setVisible(true);
        canvas.setBackground(Color.BLACK);
        if (container == frame) {
            insets = frame.getInsets();
            canvas.setLocation(insets.left + screenX, insets.top + screenY);
        } else {
            canvas.setLocation(screenX, screenY);
        }

        canvas.addFocusListener(this);
        canvas.requestFocus();
        focused = true;
        if (client.graphicsProvider != null && client.canvasWidth == client.graphicsProvider.width && client.canvasHeight == client.graphicsProvider.height) {
            ((ImageGraphicsProvider) client.graphicsProvider).setTarget(canvas);
            client.graphicsProvider.drawGame(0, 0);
        } else {
            client.graphicsProvider = new ImageGraphicsProvider(client.canvasWidth, client.canvasHeight, canvas);
        }

        revalidateCanvas = false;
        aLong1304 = Clock.now();
    }

    protected abstract void releaseThreads();

    protected abstract void method745();

    final void method933() {
        Container var1 = getContainer();
        if (var1 != null) {
            Bounds var2 = method927();
            width_ = Math.max(var2.width, anInt1301);
            height_ = Math.max(var2.height, anInt1291);
            if (width_ <= 0) {
                width_ = 1;
            }

            if (height_ <= 0) {
                height_ = 1;
            }

            client.canvasWidth = Math.min(width_, anInt1320);
            client.canvasHeight = Math.min(height_, anInt1317);
            screenX = (width_ - client.canvasWidth) / 2;
            screenY = 0;
            canvas.setSize(client.canvasWidth, client.canvasHeight);
            client.graphicsProvider = new ImageGraphicsProvider(client.canvasWidth, client.canvasHeight, canvas);
            if (var1 == frame) {
                Insets var3 = frame.getInsets();
                canvas.setLocation(var3.left + screenX, screenY + var3.top);
            } else {
                canvas.setLocation(screenX, screenY);
            }

            focused = true;
            method737();
        }
    }

    protected final void method929() {
        EntityUID.anImage546 = null;
        AnimationSequence.aFont1227 = null;
        TileOverlay.aFontMetrics1467 = null;
    }

    protected abstract void onExit();

    protected final void method935() {
        try {
            if (shell != null) {
                ++anInt1316;
                if (anInt1316 >= 3) {
                    error("alreadyloaded");
                    return;
                }

                getAppletContext().showDocument(getDocumentBase(), "_self");
                return;
            }

            shell = this;
            client.canvasWidth = 765;
            client.canvasHeight = 503;
            client.build = 184;
            Jagexception.applet = this;
            if (taskProcessor == null) {
                taskProcessor = new OldConnectionTaskProcessor();
            }

            taskProcessor.method697(this, 1);
        } catch (Exception var5) {
            client.sendError(null, var5);
            error("crash");
        }

    }

    protected final void method931(int var1, String var2, boolean var3) {
        try {
            Graphics var4 = canvas.getGraphics();
            if (AnimationSequence.aFont1227 == null) {
                AnimationSequence.aFont1227 = new java.awt.Font("Helvetica", 1, 13);
                TileOverlay.aFontMetrics1467 = canvas.getFontMetrics(AnimationSequence.aFont1227);
            }

            if (var3) {
                var4.setColor(Color.black);
                var4.fillRect(0, 0, client.canvasWidth, client.canvasHeight);
            }

            Color var5 = new Color(140, 17, 17);

            try {
                if (EntityUID.anImage546 == null) {
                    EntityUID.anImage546 = canvas.createImage(304, 34);
                }

                Graphics var6 = EntityUID.anImage546.getGraphics();
                var6.setColor(var5);
                var6.drawRect(0, 0, 303, 33);
                var6.fillRect(2, 2, var1 * 3, 30);
                var6.setColor(Color.black);
                var6.drawRect(1, 1, 301, 31);
                var6.fillRect(var1 * 3 + 2, 2, 300 - var1 * 3, 30);
                var6.setFont(AnimationSequence.aFont1227);
                var6.setColor(Color.white);
                var6.drawString(var2, (304 - TileOverlay.aFontMetrics1467.stringWidth(var2)) / 2, 22);
                var4.drawImage(EntityUID.anImage546, client.canvasWidth / 2 - 152, client.canvasHeight / 2 - 18, null);
            } catch (Exception var9) {
                int var7 = client.canvasWidth / 2 - 152;
                int var8 = client.canvasHeight / 2 - 18;
                var4.setColor(var5);
                var4.drawRect(var7, var8, 303, 33);
                var4.fillRect(var7 + 2, var8 + 2, var1 * 3, 30);
                var4.setColor(Color.black);
                var4.drawRect(var7 + 1, var8 + 1, 301, 31);
                var4.fillRect(var1 * 3 + var7 + 2, var8 + 2, 300 - var1 * 3, 30);
                var4.setFont(AnimationSequence.aFont1227);
                var4.setColor(Color.white);
                var4.drawString(var2, var7 + (304 - TileOverlay.aFontMetrics1467.stringWidth(var2)) / 2, var8 + 22);
            }
        } catch (Exception var10) {
            canvas.repaint();
        }

    }

    void processGraphics() {
        Container container = getContainer();
        long time = Clock.now();
        long var4 = clientTicks[Varcs.clientTickIndex];
        clientTicks[Varcs.clientTickIndex] = time;
        Varcs.clientTickIndex = Varcs.clientTickIndex + 1 & 31;
        if (var4 != 0L && time > var4) {
            int var6 = (int) (time - var4);
            anInt1292 = ((var6 >> 1) + 32000) / var6;
        }

        if (++anInt1306 - 1 > 50) {
            anInt1306 -= 50;
            focused = true;
            canvas.setSize(client.canvasWidth, client.canvasHeight);
            canvas.setVisible(true);
            if (container == frame) {
                Insets var7 = frame.getInsets();
                canvas.setLocation(screenX + var7.left, var7.top + screenY);
            } else {
                canvas.setLocation(screenX, screenY);
            }
        }

        if (revalidateCanvas) {
            method932();
        }

        method939();
        draw(focused);
        if (focused) {
            resetDrawing();
        }

        focused = false;
    }

    protected abstract void cycle();

    void processEngine() {
        long time = Clock.now();
        long tick = engineTicks[WorldMapGroundDecorType.anInt310];
        engineTicks[WorldMapGroundDecorType.anInt310] = time;
        WorldMapGroundDecorType.anInt310 = WorldMapGroundDecorType.anInt310 + 1 & 31;
        if (tick != 0L && time > tick) {

        }

        synchronized (this) {
            client.hasFocus = aBoolean1314;
        }

        cycle();
    }

    final void method945(Object var1) {
        if (systemEventQueue != null) {
            for (int var2 = 0; var2 < 50 && systemEventQueue.peekEvent() != null; ++var2) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException ignored) {
                }
            }

            if (var1 != null) {
                systemEventQueue.postEvent(new ActionEvent(var1, 1001, "dummy"));
            }

        }
    }

    final void method932() {
        Statics51.method343(canvas);
        CollisionMap.method155(canvas);
        if (mouseWheel != null) {
            mouseWheel.method224(canvas);
        }

        initCanvas();
        Canvas var1 = canvas;
        var1.setFocusTraversalKeysEnabled(false);
        var1.addKeyListener(Keyboard.instance);
        var1.addFocusListener(Keyboard.instance);
        Statics48.method95(canvas);
        if (mouseWheel != null) {
            mouseWheel.method223(canvas);
        }

        method934();
    }

    protected MouseWheelProvider method943() {
        if (mouseWheel == null) {
            mouseWheel = new MouseWheel();
            mouseWheel.method223(canvas);
        }

        return mouseWheel;
    }

    final void method939() {
        Bounds bounds = method927();
        if (bounds.width != width_ || height_ != bounds.height || aBoolean1300) {
            method933();
            aBoolean1300 = false;
        }

    }

    protected abstract void draw(boolean var1);

    public final void method947(int var1, int var2) {
        if (anInt1320 != var1 || var2 != anInt1317) {
            method934();
        }

        anInt1320 = var1;
        anInt1317 = var2;
    }

    protected void updateClipboard() {
        clipboard = getToolkit().getSystemClipboard();
    }

    protected abstract void method737();

    void resetDrawing() {
        int screenX = this.screenX;
        int screenY = this.screenY;
        int rx = width_ - client.canvasWidth - screenX;
        int ry = height_ - client.canvasHeight - screenY;
        if (screenX > 0 || rx > 0 || screenY > 0 || ry > 0) {
            try {
                Container container = getContainer();
                int left = 0;
                int top = 0;
                if (container == frame) {
                    Insets insets = frame.getInsets();
                    left = insets.left;
                    top = insets.top;
                }

                Graphics g = container.getGraphics();
                g.setColor(Color.black);
                if (screenX > 0) {
                    g.fillRect(left, top, screenX, height_);
                }

                if (screenY > 0) {
                    g.fillRect(left, top, width_, screenY);
                }

                if (rx > 0) {
                    g.fillRect(left + width_ - rx, top, rx, height_);
                }

                if (ry > 0) {
                    g.fillRect(left, top + height_ - ry, width_, ry);
                }
            } catch (Exception ignored) {

            }
        }
    }

    protected void copyToClipboard(String string) {
        clipboard.setContents(new StringSelection(string), null);
    }

    public final boolean hasFrame() {
        return frame != null;
    }

    public void run() {
        try {
            if (OldConnectionTaskProcessor.javaVendor != null) {
                String vendor = OldConnectionTaskProcessor.javaVendor.toLowerCase();
                if (vendor.contains("sun") || vendor.contains("apple")) {
                    String version = OldConnectionTaskProcessor.javaVerson;
                    if (version.equals("1.1") || version.startsWith("1.1.") || version.equals("1.2") || version.startsWith("1.2.") || version.equals("1.3") || version.startsWith("1.3.") || version.equals("1.4") || version.startsWith("1.4.") || version.equals("1.5") || version.startsWith("1.5.") || version.equals("1.6.0")) {
                        error("wrongjava");
                        return;
                    }

                    if (version.startsWith("1.6.0_")) {
                        int i = 6;
                        while (i < version.length() && WorldMapIcon.method196(version.charAt(i))) {
                            ++i;
                        }

                        String minor = version.substring(6, i);
                        if (Strings.parseInt(minor) && BaseFont.method1501(minor) < 10) {
                            error("wrongjava");
                            return;
                        }
                    }

                    anInt1297 = 5;
                }
            }

            setFocusCycleRoot(true);
            initCanvas();
            method745();
            clock = Clock.create();

            while (aLong1315 == 0L || (Clock.now() < aLong1315)) {
                anInt1309 = clock.sleep(refreshRate, anInt1297);

                for (int var5 = 0; var5 < anInt1309; ++var5) {
                    processEngine();
                }
                processGraphics();
                method945(canvas);
            }
        } catch (Exception var6) {
            client.sendError(null, var6);
            error("crash");
        }

        finallyDestroy();
    }

    public final void start() {
        if (this == shell && !canDestroy) {
            aLong1315 = 0L;
        }
    }

    public final void stop() {
        if (this == shell && !canDestroy) {
            aLong1315 = Clock.now() + 4000L;
        }
    }

    public final void update(Graphics g) {
        paint(g);
    }

    public final void focusLost(FocusEvent e) {
        aBoolean1314 = false;
    }

    public final void windowClosed(WindowEvent e) {
    }

    public final void windowDeiconified(WindowEvent e) {
    }

    public final void windowIconified(WindowEvent e) {
    }

    public final void windowOpened(WindowEvent e) {
    }

    public abstract void init();

    public final void windowClosing(WindowEvent e) {
        destroy();
    }

    public final void windowActivated(WindowEvent e) {
    }

    public final void windowDeactivated(WindowEvent e) {
    }

    public final void focusGained(FocusEvent e) {
        aBoolean1314 = true;
        focused = true;
    }
}
