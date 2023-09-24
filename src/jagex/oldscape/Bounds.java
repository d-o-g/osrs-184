package jagex.oldscape;

public class Bounds {

  public int x;
  public int y;
  public int width;
  public int height;

  public Bounds(int x, int y, int width, int height) {
    setLocation(x, y);
    setSize(width, height);
  }

  public Bounds(int width, int height) {
    this(0, 0, width, height);
  }

  void addX(Bounds a, Bounds b) {
    b.x = x;
    b.width = width;
    if (x < a.x) {
      b.width -= a.x - x;
      b.x = a.x;
    }

    if (b.getMaxX() > a.getMaxX()) {
      b.width -= b.getMaxX() - a.getMaxX();
    }

    if (b.width < 0) {
      b.width = 0;
    }
  }

  void addY(Bounds a, Bounds b) {
    b.y = y;
    b.height = height;
    if (y < a.y) {
      b.height -= a.y - y;
      b.y = a.y;
    }

    if (b.getMaxY() > a.getMaxY()) {
      b.height -= b.getMaxY() - a.getMaxY();
    }

    if (b.height < 0) {
      b.height = 0;
    }
  }

  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  int getMaxX() {
    return x + width;
  }

  int getMaxY() {
    return y + height;
  }

  public void add(Bounds a, Bounds b) {
    addX(a, b);
    addY(a, b);
  }

  public String toString() {
    return null;
  }
}
