package jag.game.scene;

import jag.commons.collection.Node;
import jag.game.scene.entity.*;

public final class Tile extends Node {

    public final int sceneX;
    public final int sceneY;
    public final int renderLevel;

    public final int[] entityMarkerSideMasks;

    public final EntityMarker[] markers;

    public PickableStack pickableStack;
    public BoundaryDecor boundaryDecor;
    public Boundary boundary;
    public TileDecor decor;

    public TilePaint paint;
    public TileModel model;

    public Tile underneath;

    public int floorLevel;
    public int minFloorLevel;
    public int entityMarkerCount;
    public int entityMarkerSideFlags;
    public int entityMarkerSideRenderConfig;
    public int anInt563;
    public int anInt1149;
    public int anInt575;

    public boolean drawEntityMarkers;
    public boolean aBoolean1151;
    public boolean aBoolean665;

    public Tile(int floorLevel, int sceneX, int sceneY) {
        markers = new EntityMarker[5];
        entityMarkerSideMasks = new int[5];
        entityMarkerSideFlags = 0;
        renderLevel = this.floorLevel = floorLevel;
        this.sceneX = sceneX;
        this.sceneY = sceneY;
    }
}
