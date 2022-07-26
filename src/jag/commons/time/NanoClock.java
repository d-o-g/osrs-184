package jag.commons.time;

public class NanoClock extends Clock {

    long last;

    NanoClock() {
        last = System.nanoTime();
    }

    public int sleep(int time, int var2) {
        long var3 = (long) var2 * 1000000L;
        long var5 = last - System.nanoTime();
        if (var5 < var3) {
            var5 = var3;
        }

        long var7 = var5 / 1000000L;
        long var9;
        if (var7 > 0L) {
            if (var7 % 10L == 0L) {
                var9 = var7 - 1L;

                try {
                    Thread.sleep(var9);
                } catch (InterruptedException ignored) {
                }

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException ignored) {
                }
            } else {
                try {
                    Thread.sleep(var7);
                } catch (InterruptedException ignored) {
                }
            }
        }

        var9 = System.nanoTime();

        int var13;
        for (var13 = 0; var13 < 10 && (var13 < 1 || last < var9); last += 1000000L * (long) time) {
            ++var13;
        }

        if (last < var9) {
            last = var9;
        }

        return var13;
    }

    public void update() {
        last = System.nanoTime();
    }
}
