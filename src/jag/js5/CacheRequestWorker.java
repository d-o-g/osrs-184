package jag.js5;

import jag.commons.collection.NodeDeque;
import jag.game.GameShell;
import jag.game.client;
import jag.opcode.Buffer;

public class CacheRequestWorker implements Runnable {

    public static final NodeDeque<CacheRequest> requests = new NodeDeque<>();
    public static final NodeDeque<CacheRequest> read = new NodeDeque<>();
    public static final Object mutex = new Object();
    public static int state = 0;
    public static Thread thread;

    public CacheRequestWorker() {

    }

    public static void openURL(String var0, boolean var1, boolean var2) {
        GameShell.openURL(var0, var1, var2);
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
