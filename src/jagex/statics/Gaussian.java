package jagex.statics;

public class Gaussian {

  public static double[] generate(double mean, double dev, int count) {
    int len = count * 2 + 1;
    double[] gaussian = new double[len];
    int start = -count;

    for (int i = 0; start <= count; ++i) {
      gaussian[i] = calculate(start, mean, dev);
      ++start;
    }

    return gaussian;
  }

  private static double calculate(double x, double mean, double dev) {
    double exponent = (x - mean) / dev;
    double value = Math.exp(-exponent * exponent / 2.0D) / Math.sqrt(6.283185307179586D);
    return value / dev;
  }
}
