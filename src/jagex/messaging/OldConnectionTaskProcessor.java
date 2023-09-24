package jagex.messaging;

import jagex.jagex3.js5.ReferenceTable;

import java.io.DataInputStream;
import java.net.*;

public class OldConnectionTaskProcessor implements Runnable {
  public static String javaVendor;
  public static String javaVerson;
  public static ReferenceTable aReferenceTable854;
  final Thread thread;
  boolean aBoolean849;
  OldConnectionTask aOldConnectionTask_852;
  OldConnectionTask aOldConnectionTask_851;

  public OldConnectionTaskProcessor() {
    aOldConnectionTask_852 = null;
    aOldConnectionTask_851 = null;
    aBoolean849 = false;
    javaVendor = "Unknown";
    javaVerson = "1.6";

    try {
      javaVendor = System.getProperty("java.vendor");
      javaVerson = System.getProperty("java.version");
    } catch (Exception ignored) {
    }

    aBoolean849 = false;
    thread = new Thread(this);
    thread.setPriority(10);
    thread.setDaemon(true);
    thread.start();
  }

  public static double method700(double var0, double var2, double var4) {
    double var6 = (var0 - var2) / var4;
    double var8 = Math.exp(-var6 * var6 / 2.0D) / Math.sqrt(6.283185307179586D);
    return var8 / var4;
  }

  final OldConnectionTask method701(int var1, int var2, Object var4) {
    OldConnectionTask var5 = new OldConnectionTask();
    var5.anInt883 = var1;
    var5.anInt884 = var2;
    var5.anObject888 = var4;
    synchronized (this) {
      if (aOldConnectionTask_851 != null) {
        aOldConnectionTask_851.aOldConnectionTask_887 = var5;
        aOldConnectionTask_851 = var5;
      } else {
        aOldConnectionTask_851 = aOldConnectionTask_852 = var5;
      }

      notify();
      return var5;
    }
  }

  public final OldConnectionTask method697(Runnable var1, int var2) {
    return method701(2, var2, var1);
  }

  public final void kill() {
    synchronized (this) {
      aBoolean849 = true;
      notifyAll();
    }

    try {
      thread.join();
    } catch (InterruptedException ignored) {
    }

  }

  public final OldConnectionTask create(String var1, int var2) {
    return method701(1, var2, var1);
  }

  public final void run() {
    while (true) {
      OldConnectionTask var2;
      synchronized (this) {
        while (true) {
          if (aBoolean849) {
            return;
          }

          if (aOldConnectionTask_852 != null) {
            var2 = aOldConnectionTask_852;
            aOldConnectionTask_852 = aOldConnectionTask_852.aOldConnectionTask_887;
            if (aOldConnectionTask_852 == null) {
              aOldConnectionTask_851 = null;
            }
            break;
          }

          try {
            wait();
          } catch (InterruptedException ignored) {
          }
        }
      }

      try {
        int var5 = var2.anInt883;
        if (var5 == 1) {
          var2.result = new Socket(InetAddress.getByName((String) var2.anObject888), var2.anInt884);
        } else if (var5 == 2) {
          Thread var3 = new Thread((Runnable) var2.anObject888);
          var3.setDaemon(true);
          var3.start();
          var3.setPriority(var2.anInt884);
          var2.result = var3;
        } else if (var5 == 4) {
          var2.result = new DataInputStream(((URL) var2.anObject888).openStream());
        }

        var2.state = 1;
      } catch (ThreadDeath var6) {
        throw var6;
      } catch (Throwable var7) {
        var2.state = 2;
      }
    }
  }
}
