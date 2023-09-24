package jagex.oldscape;

import java.security.SecureRandom;
import java.util.concurrent.*;

public class SecureRandomService {

  public static int anInt457;
  public static int anInt458;

  public static SecureRandom instance;

  final Future<SecureRandom> future;
  ExecutorService executor;

  public SecureRandomService() {
    executor = Executors.newSingleThreadExecutor();
    future = executor.submit(new SecureRandomCallable());
  }

  public static SecureRandom provide() {
    SecureRandom random = new SecureRandom();
    random.nextInt();
    return random;
  }

  public boolean isDone() {
    return future.isDone();
  }

  public SecureRandom get() {
    try {
      return future.get();
    } catch (Exception e) {
      return provide();
    }
  }

  public void kill() {
    executor.shutdown();
    executor = null;
  }
}
