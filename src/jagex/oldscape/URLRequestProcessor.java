package jagex.oldscape;

import jagex.oldscape.client.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class URLRequestProcessor implements Runnable {

  public static String[] aStringArray794;

  final Thread thread;

  final java.util.Queue queue;

  volatile boolean killed;

  public URLRequestProcessor() {
    queue = new java.util.LinkedList();
    thread = new Thread(this);
    thread.setPriority(1);
    thread.start();
  }

  public static char toTitleCase(char c) {
    return c != 181 && c != 402 ? Character.toTitleCase(c) : c;
  }

  public URLRequest enqueue(URL url) {
    URLRequest request = new URLRequest(url);
    synchronized (this) {
      queue.add(request);
      notify();
      return request;
    }
  }

  public void release() {
    killed = true;

    try {
      synchronized (this) {
        notify();
      }

      thread.join();
    } catch (InterruptedException ignored) {

    }
  }

  public void run() {
    while (!killed) {
      try {
        URLRequest current;
        synchronized (this) {
          current = (URLRequest) queue.poll();
          if (current == null) {
            try {
              wait();
            } catch (InterruptedException ignored) {

            }
            continue;
          }
        }

        DataInputStream input = null;
        URLConnection connection = null;

        try {
          connection = current.target.openConnection();
          connection.setConnectTimeout(5000);
          connection.setReadTimeout(5000);
          connection.setUseCaches(false);
          connection.setRequestProperty("Connection", "close");
          int len = connection.getContentLength();
          if (len >= 0) {
            byte[] data = new byte[len];
            input = new DataInputStream(connection.getInputStream());
            input.readFully(data);
            current.data = data;
          }

          current.complete = true;
        } catch (IOException e) {
          current.complete = true;
        } finally {
          if (input != null) {
            input.close();
          }

          if (connection instanceof HttpURLConnection) {
            ((HttpURLConnection) connection).disconnect();
          }

        }
      } catch (Exception e) {
        client.sendError(null, e);
      }
    }

  }
}
