package jagex.core.time;

public class NanoClock extends Clock {

  private long lastUpdateTime;

  NanoClock() {
    this.lastUpdateTime = System.nanoTime();
  }

  @Override
  public int sleep(int desired, int minimum) {
    long minSleepTimeNanos = (long) minimum * 1_000_000L;
    long timeElapsed = lastUpdateTime - System.nanoTime();
    if (timeElapsed < minSleepTimeNanos) {
      timeElapsed = minSleepTimeNanos;
    }

    long sleepTimeMillis = timeElapsed / 1_000_000L;

    if (sleepTimeMillis > 0L) {
      if (sleepTimeMillis % 10L == 0L) {
        long adjustedSleepTimeMillis = sleepTimeMillis - 1L;

        try {
          Thread.sleep(adjustedSleepTimeMillis);
        } catch (InterruptedException ignored) {
        }

        try {
          Thread.sleep(1L);
        } catch (InterruptedException ignored) {
        }
      } else {
        try {
          Thread.sleep(sleepTimeMillis);
        } catch (InterruptedException ignored) {
        }
      }
    }

    lastUpdateTime = System.nanoTime();

    int iterations = 0;
    for (int i = 0; i < 10 && (i < 1 || lastUpdateTime < System.nanoTime()); i++) {
      iterations++;
      lastUpdateTime += 1_000_000L * (long) desired;
    }

    if (lastUpdateTime < System.nanoTime()) {
      lastUpdateTime = System.nanoTime();
    }

    return iterations;
  }

  @Override
  public void update() {
    lastUpdateTime = System.nanoTime();
  }
}