package jagex.jagex3.graphics;

public interface MaterialProvider {

  int rgb(int id);

  int[] pixels(int id);

  boolean isLowDetail();

  boolean method1423(int id);
}
