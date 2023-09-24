package jagex.oldscape.client.social;

public class Associate<T extends Associate<T>> extends Chatter<T> {

  public int rank;
  public int world;
  public int index;

  public Associate() {
    this.world = -1;
  }

  public boolean isLoggedIn() {
    return world > 0;
  }

  public int getWorld() {
    return world;
  }

  public void set(int var1, int var2) {
    this.world = var1;
    this.index = var2;
  }
}
