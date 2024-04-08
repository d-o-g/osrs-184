package jagex.oldscape.client.social;

import jagex.jagex3.sound.DefaultAudioSystemProvider;
import jagex.oldscape.client.client;
import jagex.oldscape.client.fonts.NamedFont;
import jagex.jagex3.js5.LoadedArchive;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.client.scene.SceneGraphRenderData;

public class BefriendedPlayer extends Associate<BefriendedPlayer> {

  public boolean aBoolean745;
  public boolean aBoolean746;

  BefriendedPlayer() {
  }

  public static void method555() {
    SceneGraphRenderData.underlays = null;
    SceneGraphRenderData.overlays = null;
    DefaultAudioSystemProvider.overlayShapes = null;
    SceneGraph.overlayOrientations = null;
    SceneGraphRenderData.anIntArrayArrayArray393 = null;
    SceneGraphRenderData.shadows = null;
    DefaultAudioSystemProvider.tileLighting = null;
    SceneGraphRenderData.blendHue = null;
    LoadedArchive.tileBlendSaturation = null;
    SceneGraphRenderData.blendLightness = null;
    NamedFont.tileBlendHueWeight = null;
    SceneGraphRenderData.blendColorCount = null;
  }

  int compare(BefriendedPlayer friend) {
    if (super.world == client.currentWorld && client.currentWorld != friend.world) {
      return -1;
    }
    if (client.currentWorld == friend.world && super.world != client.currentWorld) {
      return 1;
    }
    if (super.world != 0 && friend.world == 0) {
      return -1;
    }
    if (friend.world != 0 && super.world == 0) {
      return 1;
    }
    if (this.aBoolean745 && !friend.aBoolean745) {
      return -1;
    }
    if (!this.aBoolean745 && friend.aBoolean745) {
      return 1;
    }
    if (this.aBoolean746 && !friend.aBoolean746) {
      return -1;
    }
    if (!this.aBoolean746 && friend.aBoolean746) {
      return 1;
    }
    return super.world != 0 ? super.index - friend.index : friend.index - super.index;
  }

  public int compare0(BefriendedPlayer var1) {
    return this.compare(var1);
  }

  public int compareTo(BefriendedPlayer var1) {
    return this.compare(var1);
  }
}
