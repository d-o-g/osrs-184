package jagex.oldscape.shared.pow;

import jagex.messaging.Buffer;

import java.util.concurrent.Callable;

public class ProofOfWorkService implements Callable<Buffer> {

  private final Buffer buffer;
  private final BufferFunction func;
  private final ProofOfWorkExecutor service;

  public ProofOfWorkService(ProofOfWorkExecutor service, Buffer buffer, BufferFunction func) {
    this.service = service;
    this.buffer = buffer;
    this.func = func;
  }

  public Buffer call() {
    return func.apply(buffer);
  }
}
