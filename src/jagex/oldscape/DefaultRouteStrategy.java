package jagex.oldscape;

public class DefaultRouteStrategy extends RouteStrategy {

  public DefaultRouteStrategy() {

  }

  public static int convertHslToRgb(int hue, int sat, int light) {
    if (light > 179) {
      sat /= 2;
    }

    if (light > 192) {
      sat /= 2;
    }

    if (light > 217) {
      sat /= 2;
    }

    if (light > 243) {
      sat /= 2;
    }

    return (sat / 32 << 7) + (hue / 4 << 10) + light / 2;
  }

  public boolean isDestination(int x, int y) {
    return x == destinationX && y == destinationY;
  }
}
