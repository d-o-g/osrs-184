package jag.audi;

import jag.commons.collection.Node;
import jag.commons.collection.NodeDeque;
import jag.game.type.ObjectDefinition;
import jag.worldmap.WorldMapLabelSize;

public final class ObjectSound extends Node {

    public static final NodeDeque<ObjectSound> OBJECT_SOUNDS = new NodeDeque<>();

    public ObjectDefinition definition;
    public PcmStream_Sub2 aClass5_Sub6_Sub2_370;
    public PcmStream_Sub2 aClass5_Sub6_Sub2_369;
    public int ambientSoundId;
    public int anInt372;
    public int anInt368;
    public int anInt367;
    public int[] effects;
    public int anInt380;
    public int anInt377;
    public int anInt375;
    public int anInt112;
    public int anInt378;
    public int anInt366;

    public ObjectSound() {
    }

    public void apply() {
        int id = ambientSoundId;
        ObjectDefinition definition = this.definition.transform();
        if (definition != null) {
            ambientSoundId = definition.ambientSoundId;
            anInt372 = definition.anInt1344 * 128;
            anInt368 = definition.anInt1508;
            anInt367 = definition.anInt1510;
            effects = definition.soundEffects;
        } else {
            ambientSoundId = -1;
            anInt372 = 0;
            anInt368 = 0;
            anInt367 = 0;
            effects = null;
        }

        if (id != ambientSoundId && aClass5_Sub6_Sub2_370 != null) {
            WorldMapLabelSize.aClass5_Sub6_Sub1_528.removeDelegate(aClass5_Sub6_Sub2_370);
            aClass5_Sub6_Sub2_370 = null;
        }

    }
}
