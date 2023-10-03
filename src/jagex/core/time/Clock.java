package jagex.core.time;

public abstract class Clock {

  private static long previousTimestamp = 0L;
  private static long timeCorrection = 0L;

  Clock() {

  }

  public static Clock create() {
    try {
      return new NanoClock();
    } catch (Throwable throwable) {
      return new MillisClock();
    }
  }

  public static synchronized long now() {
    long currentTimestamp = System.currentTimeMillis();
    if (currentTimestamp < previousTimestamp) {
      timeCorrection += previousTimestamp - currentTimestamp;
    }

    previousTimestamp = currentTimestamp;
    return currentTimestamp + timeCorrection;
  }

  public abstract int sleep(int desired, int minimum);

  public abstract void update();
}