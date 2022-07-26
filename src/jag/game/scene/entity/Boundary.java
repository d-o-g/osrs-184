package jag.game.scene.entity;

public final class Boundary {

    public Entity entity;
    public Entity linkedEntity;

    public long uid;

    public int config;
    public int absoluteX;
    public int absoluteY;
    public int height;
    public int orientation;
    public int linkedOrientation;

    public Boundary() {
        uid = 0L;
        config = 0;
    }
}
