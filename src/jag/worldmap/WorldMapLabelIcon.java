package jag.worldmap;

import jag.graphics.Sprite;

public class WorldMapLabelIcon extends WorldMapIcon {
    public static short[] grandExchangeSearchResults;
    final WorldMapLabel label;
    final int width;
    final int height;
    final int mapFunction;

    WorldMapLabelIcon(WorldMapPosition min, WorldMapPosition max, int mapFunction, WorldMapLabel label) {
        super(min, max);
        this.mapFunction = mapFunction;
        this.label = label;
        WorldMapFunction function = WorldMapFunction.get(getMapFunction());
        Sprite sprite = function.getSprite();
        if (sprite != null) {
            width = sprite.width;
            height = sprite.height;
        } else {
            width = 0;
            height = 0;
        }
    }

    public WorldMapLabel getLabel() {
        return label;
    }

    public int getMapFunction() {
        return mapFunction;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }
}
