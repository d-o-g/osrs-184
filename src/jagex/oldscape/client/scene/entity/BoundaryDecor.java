package jagex.oldscape.client.scene.entity;

public final class BoundaryDecor {

  public Entity entity;
  public Entity linkedEntity;

  public long uid;

  public int config;
  public int offsetX;
  public int absoluteX;
  public int absoluteY;
  public int offsetY;
  public int height;
  public int renderConfig;
  public int orientation;

  public BoundaryDecor() {
    uid = 0L;
    config = 0;
  }
}
