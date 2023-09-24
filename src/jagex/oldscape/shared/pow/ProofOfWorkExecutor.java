package jagex.oldscape.shared.pow;

import jagex.messaging.Buffer;

import java.util.concurrent.*;

public class ProofOfWorkExecutor {

  private final Buffer buffer;
  private final BufferFunction func;

  private ExecutorService service;
  private Future<Buffer> future;

  public ProofOfWorkExecutor(Buffer buffer, BufferFunction func) {
    service = Executors.newSingleThreadExecutor();
    this.buffer = buffer;
    this.func = func;
    process();
  }

  public boolean complete() {
    return future.isDone();
  }

  public void shutdown() {
    service.shutdown();
    service = null;
  }

  public Buffer call() {
    try {
      return future.get();
    } catch (Exception e) {
      return null;
    }
  }

  public void process() {
    future = service.submit(new ProofOfWorkService(this, buffer, func));
  }
}
