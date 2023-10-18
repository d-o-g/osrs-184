package jagex.core.time;

public class MillisClock extends Clock {

  private final long[] records;
  private int sleepTime;
  private int elapsed;
  private int counter;
  private long lastUpdateTime;
  private int index;

  MillisClock() {
    this.records = new long[10];
    this.sleepTime = 256;
    this.elapsed = 1;
    this.counter = 0;
    this.lastUpdateTime = Clock.now();

    for (int i = 0; i < 10; ++i) {
      this.records[i] = this.lastUpdateTime;
    }
  }

  @Override
  public int sleep(int desired, int minimum) {
    int prevSleepTime = this.sleepTime;
    int prevTimeElapsed = this.elapsed;
    this.sleepTime = 300;
    this.elapsed = 1;
    this.lastUpdateTime = Clock.now();

    if (this.records[this.index] == 0L) {
      this.sleepTime = prevSleepTime;
      this.elapsed = prevTimeElapsed;
    } else if (this.lastUpdateTime > this.records[this.index]) {
      this.sleepTime = (int) ((desired * 2560L) / (this.lastUpdateTime - this.records[this.index]));
    }

    if (this.sleepTime < 25) {
      this.sleepTime = 25;
    }

    if (this.sleepTime > 256) {
      this.sleepTime = 256;
      this.elapsed = (int) ((long) desired - (this.lastUpdateTime - this.records[this.index]) / 10L);
    }

    if (this.elapsed > desired) {
      this.elapsed = desired;
    }

    this.records[this.index] = this.lastUpdateTime;
    this.index = (this.index + 1) % 10;

    if (this.elapsed > 1) {
      for (int i = 0; i < 10; ++i) {
        if (this.records[i] != 0L) {
          this.records[i] += this.elapsed;
        }
      }
    }

    if (this.elapsed < minimum) {
      this.elapsed = minimum;
    }

    long sleepDuration = this.elapsed;
    if (sleepDuration > 0L) {
      if (sleepDuration % 10L == 0L) {
        long adjustedSleepDuration = sleepDuration - 1L;

        try {
          Thread.sleep(adjustedSleepDuration);
        } catch (InterruptedException ignored) {
        }

        try {
          Thread.sleep(1L);
        } catch (InterruptedException ignored) {
        }
      } else {
        try {
          Thread.sleep(sleepDuration);
        } catch (InterruptedException ignored) {
        }
      }
    }

    int iterations = 0;
    while (this.counter < 256) {
      ++iterations;
      this.counter += this.sleepTime;
    }

    this.counter &= 255;
    return iterations;
  }

  @Override
  public void update() {
    for (int i = 0; i < 10; ++i) {
      this.records[i] = 0L;
    }
  }
}