package jagex.messaging;

import java.io.*;

public class AsyncInputStream implements Runnable {

  final int payloadCapacity;
  final byte[] payload;
  final InputStream input;
  final Thread thread;
  int writeIndex;
  int readIndex;
  IOException error;

  AsyncInputStream(InputStream input, int payloadCapacity) {
    readIndex = 0;
    writeIndex = 0;
    this.input = input;
    this.payloadCapacity = payloadCapacity + 1;
    payload = new byte[this.payloadCapacity];
    thread = new Thread(this);
    thread.setDaemon(true);
    thread.start();
  }

  boolean available(int amount) throws IOException {
    if (amount == 0) {
      return true;
    }
    if (amount > 0 && amount < payloadCapacity) {
      synchronized (this) {
        int available;
        if (readIndex <= writeIndex) {
          available = writeIndex - readIndex;
        } else {
          available = payloadCapacity - readIndex + writeIndex;
        }

        if (available < amount) {
          if (error != null) {
            throw new IOException(error.toString());
          }
          notifyAll();
          return false;
        }

        return true;
      }
    }

    throw new IOException();
  }

  int read() throws IOException {
    synchronized (this) {
      if (writeIndex == readIndex) {
        if (error != null) {
          throw new IOException(error.toString());
        }
        return -1;
      }
      int data = payload[readIndex] & 0xff;
      readIndex = (readIndex + 1) % payloadCapacity;
      notifyAll();
      return data;
    }
  }

  int read(byte[] buffer, int offset, int length) throws IOException {
    if (length >= 0 && offset >= 0 && length + offset <= buffer.length) {
      synchronized (this) {
        int available;
        if (readIndex <= writeIndex) {
          available = writeIndex - readIndex;
        } else {
          available = payloadCapacity - readIndex + writeIndex;
        }

        if (length > available) {
          length = available;
        }

        if (length == 0 && error != null) {
          throw new IOException(error.toString());
        }
        if (length + readIndex <= payloadCapacity) {
          System.arraycopy(payload, readIndex, buffer, offset, length);
        } else {
          int preLength = payloadCapacity - readIndex;
          System.arraycopy(payload, readIndex, buffer, offset, preLength);
          System.arraycopy(payload, 0, buffer, preLength + offset, length - preLength);
        }

        readIndex = (length + readIndex) % payloadCapacity;
        notifyAll();
        return length;
      }
    }
    throw new IOException();
  }

  int available() throws IOException {
    synchronized (this) {
      int available;
      if (readIndex <= writeIndex) {
        available = writeIndex - readIndex;
      } else {
        available = payloadCapacity - readIndex + writeIndex;
      }

      if (available <= 0 && error != null) {
        throw new IOException(error.toString());
      }
      notifyAll();
      return available;
    }
  }

  void close() {
    synchronized (this) {
      if (error == null) {
        error = new IOException("");
      }

      notifyAll();
    }

    try {
      thread.join();
    } catch (InterruptedException ignored) {
    }
  }

  public void run() {
    while (true) {
      int available;
      synchronized (this) {
        while (true) {
          if (error != null) {
            return;
          }

          if (readIndex == 0) {
            available = payloadCapacity - writeIndex - 1;
          } else if (readIndex <= writeIndex) {
            available = payloadCapacity - writeIndex;
          } else {
            available = readIndex - writeIndex - 1;
          }

          if (available > 0) {
            break;
          }

          try {
            wait();
          } catch (InterruptedException ignored) {
          }
        }
      }

      int bytesRead;
      try {
        bytesRead = input.read(payload, writeIndex, available);
        if (bytesRead == -1) {
          throw new EOFException();
        }
      } catch (IOException e) {
        synchronized (this) {
          error = e;
          return;
        }
      }

      synchronized (this) {
        writeIndex = (bytesRead + writeIndex) % payloadCapacity;
      }
    }
  }
}
