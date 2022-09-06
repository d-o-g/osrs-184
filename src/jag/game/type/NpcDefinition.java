package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.IterableNodeTable;
import jag.commons.collection.Node;
import jag.commons.collection.ReferenceCache;
import jag.game.*;
import jag.game.scene.entity.Model;
import jag.game.scene.entity.PlayerEntity;
import jag.game.scene.entity.UnlitModel;
import jag.graphics.JagGraphics3D;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

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

    public static void method505(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = var6 - 334;
        if (var7 < 0) {
            var7 = 0;
        } else if (var7 > 100) {
            var7 = 100;
        }

        int var8 = (client.aShort920 - client.aShort913) * var7 / 100 + client.aShort913;
        int var9 = var5 * var8 / 256;
        var7 = 2048 - var3 & 2047;
        var8 = 2048 - var4 & 2047;
        int var10 = 0;
        int var11 = 0;
        int var12 = var9;
        int var13;
        int var14;
        int var15;
        if (var7 != 0) {
            var13 = JagGraphics3D.SIN_TABLE[var7];
            var14 = JagGraphics3D.COS_TABLE[var7];
            var15 = var11 * var14 - var9 * var13 >> 16;
            var12 = var14 * var9 + var13 * var11 >> 16;
            var11 = var15;
        }

        if (var8 != 0) {
            var13 = JagGraphics3D.SIN_TABLE[var8];
            var14 = JagGraphics3D.COS_TABLE[var8];
            var15 = var10 * var14 + var13 * var12 >> 16;
            var12 = var12 * var14 - var10 * var13 >> 16;
            var10 = var15;
        }

        Camera.x = var0 - var10;
        Camera.z = var1 - var11;
        Camera.y = var2 - var12;
        Camera.pitch = var3;
        Camera.yaw = var4;
        if (Camera.oculusOrbMode == 1 && client.rights >= 2 && client.engineCycle % 50 == 0 && (Camera.oculusOrbAbsoluteX >> 7 != PlayerEntity.local.absoluteX >> 7 || Camera.oculusOrbAbsoluteY >> 7 != PlayerEntity.local.absoluteY >> 7)) {
            var13 = PlayerEntity.local.floorLevel;
            var14 = client.baseX + (Camera.oculusOrbAbsoluteX >> 7);
            var15 = client.baseY + (Camera.oculusOrbAbsoluteY >> 7);
            client.teleport(var14, var15, var13, true);
        }

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

    public void decode(Buffer buffer, int opcode) {
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
            NpcDefinition var1 = transform();
            return var1 == null ? null : var1.getBaseModel();
        }
        if (transformedModelIds == null) {
            return null;
        }
        boolean var2 = false;

        for (int id : transformedModelIds) {
            if (!modelTable.load(id, 0)) {
                var2 = true;
            }
        }

        if (var2) {
            return null;
        }
        UnlitModel[] var4 = new UnlitModel[transformedModelIds.length];

        for (int var5 = 0; var5 < transformedModelIds.length; ++var5) {
            var4[var5] = UnlitModel.method982(modelTable, transformedModelIds[var5], 0);
        }

        UnlitModel var6;
        if (var4.length == 1) {
            var6 = var4[0];
        } else {
            var6 = new UnlitModel(var4, var4.length);
        }

        int var7;
        if (textures != null) {
            for (var7 = 0; var7 < textures.length; ++var7) {
                var6.texturize(textures[var7], newTextures[var7]);
            }
        }

        if (colors != null) {
            for (var7 = 0; var7 < colors.length; ++var7) {
                var6.colorize(colors[var7], newColors[var7]);
            }
        }

        return var6;
    }

    public final Model getModel(AnimationSequence anim, int animFrame, AnimationSequence stance, int stanceFrame) {
        if (transformIds != null) {
            NpcDefinition trans = transform();
            return trans == null ? null : trans.getModel(anim, animFrame, stance, stanceFrame);
        }

        Model staticModel = models.get(id);
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
                unlit[i] = UnlitModel.method982(modelTable, modelIds[i], 0);
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

            decode(buffer, opcode);
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

    public int method511(int var1, int var2) {
        return IterableNodeTable.getIntParameter(parameters, var1, var2);
    }

    public String method504(int var1, String var2) {
        return IterableNodeTable.getStringParameter(parameters, var1, var2);
    }
}
