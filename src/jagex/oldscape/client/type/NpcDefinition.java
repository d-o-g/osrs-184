package jagex.oldscape.client.type;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.Node;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;
import jagex.oldscape.client.*;
import jagex.oldscape.client.scene.entity.*;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class NpcDefinition extends DoublyLinkedNode {

  public static final ReferenceCache<NpcDefinition> cache = new ReferenceCache<>(64);
  public static final ReferenceCache<Model> models = new ReferenceCache<>(50);
  public static ReferenceTable table;
  public static ReferenceTable modelTable;

  public int[] transformIds;
  public String name;
  public int id;
  public int size;
  public int idleStance;
  public int walkStance;
  public int turnLeftStance;
  public int turnRightStance;
  public String[] actions;
  public boolean renderedOnMinimap;
  public int turnAroundStance;
  public int walkLeftStance;
  public int combatLevel;
  public boolean renderingPrioritized;
  public int walkRightStance;
  public int prayerIcon;
  public int rotation;
  public boolean interactable;
  public boolean clickable;
  public boolean follower;
  public IterableNodeTable<? super Node> parameters;
  public int varbitIndex;
  public int[] transformedModelIds;
  public int varpIndex;
  public int[] modelIds;
  public int scaleXY;
  public short[] textures;
  public int scaleZ;
  public short[] colors;
  public short[] newTextures;
  public short[] newColors;
  public int ambience;
  public int contrast;

  public NpcDefinition() {
    name = "null";
    size = 1;
    idleStance = -1;
    turnLeftStance = -1;
    turnRightStance = -1;
    walkStance = -1;
    turnAroundStance = -1;
    walkLeftStance = -1;
    walkRightStance = -1;
    actions = new String[5];
    renderedOnMinimap = true;
    combatLevel = -1;
    scaleXY = 128;
    scaleZ = 128;
    renderingPrioritized = false;
    ambience = 0;
    contrast = 0;
    prayerIcon = -1;
    rotation = 32;
    varbitIndex = -1;
    varpIndex = -1;
    interactable = true;
    clickable = true;
    follower = false;
  }

  public static NpcDefinition get(int id) {
    NpcDefinition var2 = cache.get(id);
    if (var2 != null) {
      return var2;
    }
    byte[] var3 = table.unpack(9, id);
    var2 = new NpcDefinition();
    var2.id = id;
    if (var3 != null) {
      var2.decode(new Buffer(var3));
    }

    var2.init();
    cache.put(var2, id);
    return var2;
  }

  public final NpcDefinition transform() {
    int index = -1;
    if (varbitIndex != -1) {
      index = Varbit.getValue(varbitIndex);
    } else if (varpIndex != -1) {
      index = Vars.values[varpIndex];
    }

    int id;
    if (index >= 0 && index < transformIds.length - 1) {
      id = transformIds[index];
    } else {
      id = transformIds[transformIds.length - 1];
    }

    return id != -1 ? get(id) : null;
  }

  public void decodeOpcode(Buffer buffer, int opcode) {
    int var3;
    int var4;
    if (opcode == 1) {
      var3 = buffer.g1();
      modelIds = new int[var3];

      for (var4 = 0; var4 < var3; ++var4) {
        modelIds[var4] = buffer.g2();
      }
    } else if (opcode == 2) {
      name = buffer.gstr();
    } else if (opcode == 12) {
      size = buffer.g1();
    } else if (opcode == 13) {
      idleStance = buffer.g2();
    } else if (opcode == 14) {
      walkStance = buffer.g2();
    } else if (opcode == 15) {
      turnLeftStance = buffer.g2();
    } else if (opcode == 16) {
      turnRightStance = buffer.g2();
    } else if (opcode == 17) {
      walkStance = buffer.g2();
      turnAroundStance = buffer.g2();
      walkLeftStance = buffer.g2();
      walkRightStance = buffer.g2();
    } else if (opcode >= 30 && opcode < 35) {
      actions[opcode - 30] = buffer.gstr();
      if (actions[opcode - 30].equalsIgnoreCase("Hidden")) {
        actions[opcode - 30] = null;
      }
    } else if (opcode == 40) {
      var3 = buffer.g1();
      textures = new short[var3];
      newTextures = new short[var3];

      for (var4 = 0; var4 < var3; ++var4) {
        textures[var4] = (short) buffer.g2();
        newTextures[var4] = (short) buffer.g2();
      }
    } else if (opcode == 41) {
      var3 = buffer.g1();
      colors = new short[var3];
      newColors = new short[var3];

      for (var4 = 0; var4 < var3; ++var4) {
        colors[var4] = (short) buffer.g2();
        newColors[var4] = (short) buffer.g2();
      }
    } else if (opcode == 60) {
      var3 = buffer.g1();
      transformedModelIds = new int[var3];

      for (var4 = 0; var4 < var3; ++var4) {
        transformedModelIds[var4] = buffer.g2();
      }
    } else if (opcode == 93) {
      renderedOnMinimap = false;
    } else if (opcode == 95) {
      combatLevel = buffer.g2();
    } else if (opcode == 97) {
      scaleXY = buffer.g2();
    } else if (opcode == 98) {
      scaleZ = buffer.g2();
    } else if (opcode == 99) {
      renderingPrioritized = true;
    } else if (opcode == 100) {
      ambience = buffer.g1b();
    } else if (opcode == 101) {
      contrast = buffer.g1b();
    } else if (opcode == 102) {
      prayerIcon = buffer.g2();
    } else if (opcode == 103) {
      rotation = buffer.g2();
    } else if (opcode != 106 && opcode != 118) {
      if (opcode == 107) {
        interactable = false;
      } else if (opcode == 109) {
        clickable = false;
      } else if (opcode == 111) {
        follower = true;
      } else if (opcode == 249) {
        parameters = IterableNodeTable.decode(buffer, parameters);
      }
    } else {
      varbitIndex = buffer.g2();
      if (varbitIndex == 65535) {
        varbitIndex = -1;
      }

      varpIndex = buffer.g2();
      if (varpIndex == 65535) {
        varpIndex = -1;
      }

      var3 = -1;
      if (opcode == 118) {
        var3 = buffer.g2();
        if (var3 == 65535) {
          var3 = -1;
        }
      }

      var4 = buffer.g1();
      transformIds = new int[var4 + 2];

      for (int var5 = 0; var5 <= var4; ++var5) {
        transformIds[var5] = buffer.g2();
        if (transformIds[var5] == 65535) {
          transformIds[var5] = -1;
        }
      }

      transformIds[var4 + 1] = var3;
    }

  }

  public final UnlitModel getBaseModel() {
    if (transformIds != null) {
      NpcDefinition transformed = transform();
      return transformed != null ? transformed.getBaseModel() : null;
    }

    if (transformedModelIds == null) {
      return null;
    }

    boolean fail = false;
    for (int id : transformedModelIds) {
      if (!modelTable.load(id, 0)) {
        fail = true;
      }
    }

    if (fail) {
      return null;
    }

    UnlitModel[] models = new UnlitModel[transformedModelIds.length];
    for (int var5 = 0; var5 < transformedModelIds.length; ++var5) {
      models[var5] = UnlitModel.unpack(modelTable, transformedModelIds[var5], 0);
    }

    UnlitModel base;
    if (models.length == 1) {
      base = models[0];
    } else {
      base = new UnlitModel(models, models.length);
    }

    if (textures != null) {
      for (int i = 0; i < textures.length; ++i) {
        base.texturize(textures[i], newTextures[i]);
      }
    }

    if (colors != null) {
      for (int i = 0; i < colors.length; ++i) {
        base.colorize(colors[i], newColors[i]);
      }
    }

    return base;
  }

  public final Model getModel(AnimationSequence anim, int animFrame, AnimationSequence stance, int stanceFrame) {
    if (transformIds != null) {
      NpcDefinition transformed = transform();
      return transformed != null ? transformed.getModel(anim, animFrame, stance, stanceFrame) : null;
    }

    //look up model from the in memory cche
    Model staticModel = models.get(id);

    //not present to we need to load it from the cache and process colors/textures/lighting
    if (staticModel == null) {
      boolean var6 = false;

      for (int modelId : modelIds) {
        if (!modelTable.load(modelId, 0)) {
          var6 = true;
        }
      }

      if (var6) {
        return null;
      }

      UnlitModel[] unlit = new UnlitModel[modelIds.length];

      for (int i = 0; i < modelIds.length; ++i) {
        unlit[i] = UnlitModel.unpack(modelTable, modelIds[i], 0);
      }

      UnlitModel base;
      if (unlit.length == 1) {
        base = unlit[0];
      } else {
        base = new UnlitModel(unlit, unlit.length);
      }

      if (textures != null) {
        for (int i = 0; i < textures.length; ++i) {
          base.texturize(textures[i], newTextures[i]);
        }
      }

      if (colors != null) {
        for (int i = 0; i < colors.length; ++i) {
          base.colorize(colors[i], newColors[i]);
        }
      }

      staticModel = base.light(ambience + 64, contrast * 5 + 850, -30, -50, -30);
      models.put(staticModel, id);
    }

    //apply stance, animation, rotations etc
    Model model;
    if (anim != null && stance != null) {
      model = anim.applyStanceAndAnimation(staticModel, animFrame, stance, stanceFrame);
    } else if (anim != null) {
      model = anim.applySequence(staticModel, animFrame);
    } else if (stance != null) {
      model = stance.applySequence(staticModel, stanceFrame);
    } else {
      model = staticModel.method1291(true);
    }

    //apply scaling
    if (scaleXY != 128 || scaleZ != 128) {
      model.scale(scaleXY, scaleZ, scaleXY);
    }

    return model;
  }

  public void init() {

  }

  public void decode(Buffer buffer) {
    while (true) {
      int opcode = buffer.g1();
      if (opcode == 0) {
        return;
      }

      decodeOpcode(buffer, opcode);
    }
  }

  public boolean validate() {
    if (transformIds == null) {
      return true;
    }
    int var1 = -1;
    if (varbitIndex != -1) {
      var1 = Varbit.getValue(varbitIndex);
    } else if (varpIndex != -1) {
      var1 = Vars.values[varpIndex];
    }

    if (var1 >= 0 && var1 < transformIds.length) {
      return transformIds[var1] != -1;
    }
    return transformIds[transformIds.length - 1] != -1;
  }

  public int getIntegerParameter(int id, int defaultValue) {
    return IterableNodeTable.getIntParameter(parameters, id, defaultValue);
  }

  public String getStringParameter(int id, String defaultValue) {
    return IterableNodeTable.getStringParameter(parameters, id, defaultValue);
  }
}
