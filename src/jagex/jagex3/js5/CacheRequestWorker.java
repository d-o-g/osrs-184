package jagex.jagex3.js5;

import jagex.datastructure.instrusive.linklist.NodeDeque;
import jagex.oldscape.client.client;

public class CacheRequestWorker implements Runnable {

  public static final NodeDeque<CacheRequest> requests = new NodeDeque<>();
  public static final NodeDeque<CacheRequest> read = new NodeDeque<>();
  public static final Object mutex = new Object();
  public static int state = 0;
  public static Thread thread;

  public CacheRequestWorker() {

  }

  public static void release() {
    synchronized (mutex) {
      if (state != 0) {
        state = 1;

        try {
          mutex.wait();
        } catch (InterruptedException ignored) {

        }
      }

    }
  }

  public static void method77(int var0, ResourceCache var1, Archive var2) {
    byte[] var3 = null;
    synchronized (requests) {
      for (CacheRequest req = requests.head(); req != null; req = requests.next()) {
        if ((long) var0 == req.key && var1 == req.cache && req.type == 0) {
          var3 = req.data;
          break;
        }
      }
    }

    if (var3 != null) {
      var2.method486(var1, var0, var3, true);
    } else {
      byte[] var4 = var1.read(var0);
      var2.method486(var1, var0, var4, true);
    }
  }

  public void run() {
    try {
      while (true) {
        CacheRequest request;
        synchronized (requests) {
          request = requests.head();
        }

        if (request != null) {
          if (request.type == 0) {
            request.cache.write((int) request.key, request.data, request.data.length);
            synchronized (requests) {
              request.unlink();
            }
          } else if (request.type == 1) {
            request.data = request.cache.read((int) request.key);
            synchronized (requests) {
              read.add(request);
            }
          }

          synchronized (mutex) {
            if (state <= 1) {
              state = 0;
              mutex.notifyAll();
              return;
            }

            state = 600;
          }
        } else {
          long var7 = 99L;

          try {
            Thread.sleep(var7);
          } catch (InterruptedException ignored) {
          }

          try {
            Thread.sleep(1L);
          } catch (InterruptedException ignored) {
          }

          synchronized (mutex) {
            if (state <= 1) {
              state = 0;
              mutex.notifyAll();
              return;
            }

            --state;
          }
        }
      }
    } catch (Exception var17) {
      client.sendError(null, var17);
    }
  }
}
