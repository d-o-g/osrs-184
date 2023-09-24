package jagex.oldscape.shared.pow;

import jagex.messaging.Buffer;

public class ProofOfWorkFunction implements BufferFunction {

  @Override
  public Buffer apply(Buffer buffer) {
    Buffer allocated = new Buffer(100);
    bruteforce(buffer, allocated);
    return allocated;
  }

  void bruteforce(Buffer b1, Buffer b2) {
    ProofOfWorkRequest req = new ProofOfWorkRequest(b1);
    ProofOfWork pow = new ProofOfWork();

    long nonce = 0L;
    while (!pow.apply(req.getRequiredZeroBitCount(), req.getSeed(), nonce)) {
      nonce++;
    }

    b2.p8(nonce);
  }
}
