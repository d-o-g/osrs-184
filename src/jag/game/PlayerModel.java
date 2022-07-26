package jag.game;

import jag.audi.DefaultAudioSystemProvider;
import jag.commons.collection.ReferenceCache;
import jag.game.scene.entity.Model;
import jag.game.scene.entity.UnlitModel;
import jag.game.type.AnimationSequence;
import jag.game.type.IdentikitDefinition;
import jag.game.type.ItemDefinition;
import jag.game.type.NpcDefinition;
import jag.js5.Js5Worker;
import jag.opcode.Buffer;
import jag.worldmap.HAlign;

public class PlayerModel {

    public static final int[] equipmentIndexes = new int[]{8, 11, 4, 6, 9, 7, 10};
    public static final ReferenceCache<Model> models = new ReferenceCache<>(260);

    public static short[][] colors;
    public static short[] colors2;

    public static int identikitCount;

    public int[] appearance;
    public int[] equipment;

    public int transformedNpcId;

    public boolean female;

    public long hash;
    public long modelHash;

    public static int method1426(int var0) {
        return (int) Math.pow(2.0D, 7.0F + (float) var0 / 256.0F);
    }

    public void update(int[] equipment, int[] appearance, boolean female, int transformedNpcId) {
        if (equipment == null) {
            equipment = new int[12];

            for (int equipmentIndex = 0; equipmentIndex < 7; ++equipmentIndex) {
                for (int identikitIndex = 0; identikitIndex < identikitCount; ++identikitIndex) {
                    IdentikitDefinition def = IdentikitDefinition.get(identikitIndex);
                    if (def != null && !def.hidden && equipmentIndex + (female ? 7 : 0) == def.index) {
                        equipment[equipmentIndexes[equipmentIndex]] = identikitIndex + 256;
                        break;
                    }
                }
            }
        }

        this.equipment = equipment;
        this.appearance = appearance;
        this.female = female;
        this.transformedNpcId = transformedNpcId;
        computeHash();
    }

    public void computeHash() {
        long var1 = hash;
        int var3 = equipment[5];
        int var4 = equipment[9];
        equipment[5] = var4;
        equipment[9] = var3;
        hash = 0L;

        int var5;
        for (var5 = 0; var5 < 12; ++var5) {
            hash <<= 4;
            if (equipment[var5] >= 256) {
                hash += equipment[var5] - 256;
            }
        }

        if (equipment[0] >= 256) {
            hash += equipment[0] - 256 >> 4;
        }

        if (equipment[1] >= 256) {
            hash += equipment[1] - 256 >> 8;
        }

        for (var5 = 0; var5 < 5; ++var5) {
            hash <<= 3;
            hash += this.appearance[var5];
        }

        hash <<= 1;
        hash += this.female ? 1 : 0;
        equipment[5] = var3;
        equipment[9] = var4;
        if (var1 != 0L && hash != var1) {
            models.remove(var1);
        }
    }

    public Model getModel(AnimationSequence var1, int var2, AnimationSequence var3, int var4) {
        if (transformedNpcId != -1) {
            return NpcDefinition.get(transformedNpcId).getModel(var1, var2, var3, var4);
        }

        long var6 = hash;
        int[] equipment = this.equipment;
        if (var1 != null && (var1.offHand >= 0 || var1.mainHand >= 0)) {
            equipment = new int[12];

            System.arraycopy(this.equipment, 0, equipment, 0, 12);

            if (var1.offHand >= 0) {
                var6 += var1.offHand - this.equipment[5] << 8;
                equipment[5] = var1.offHand;
            }

            if (var1.mainHand >= 0) {
                var6 += var1.mainHand - this.equipment[3] << 16;
                equipment[3] = var1.mainHand;
            }
        }

        Model var9 = models.get(var6);
        if (var9 == null) {
            boolean var11 = false;

            int var13;
            for (int var12 = 0; var12 < 12; ++var12) {
                var13 = equipment[var12];
                if (var13 >= 256 && var13 < 512 && !IdentikitDefinition.get(var13 - 256).method1114()) {
                    var11 = true;
                }

                if (var13 >= 512 && !ItemDefinition.get(var13 - 512).method518(female)) {
                    var11 = true;
                }
            }

            if (var11) {
                if (modelHash != -1L) {
                    var9 = models.get(modelHash);
                }

                if (var9 == null) {
                    return null;
                }
            }

            if (var9 == null) {
                UnlitModel[] var14 = new UnlitModel[12];
                var13 = 0;

                int var16;
                for (int var15 = 0; var15 < 12; ++var15) {
                    var16 = equipment[var15];
                    UnlitModel var17;
                    if (var16 >= 256 && var16 < 512) {
                        var17 = IdentikitDefinition.get(var16 - 256).method978();
                        if (var17 != null) {
                            var14[var13++] = var17;
                        }
                    }

                    if (var16 >= 512) {
                        var17 = ItemDefinition.get(var16 - 512).getEquipmentModel(female);
                        if (var17 != null) {
                            var14[var13++] = var17;
                        }
                    }
                }

                UnlitModel var19 = new UnlitModel(var14, var13);

                for (var16 = 0; var16 < 5; ++var16) {
                    if (appearance[var16] < colors[var16].length) {
                        var19.texturize(HAlign.aShortArray1482[var16], colors[var16][appearance[var16]]);
                    }

                    if (appearance[var16] < DefaultAudioSystemProvider.aShortArrayArray145[var16].length) {
                        var19.texturize(colors2[var16], DefaultAudioSystemProvider.aShortArrayArray145[var16][appearance[var16]]);
                    }
                }

                var9 = var19.light(64, 850, -30, -50, -30);
                models.put(var9, var6);
                modelHash = var6;
            }
        }

        if (var1 == null && var3 == null) {
            return var9;
        }

        Model var18;
        if (var1 != null && var3 != null) {
            var18 = var1.applyStanceAndAnimation(var9, var2, var3, var4);
        } else if (var1 != null) {
            var18 = var1.applySequence(var9, var2);
        } else {
            var18 = var3.applySequence(var9, var4);
        }

        return var18;
    }

    UnlitModel method1427() {
        if (transformedNpcId != -1) {
            return NpcDefinition.get(transformedNpcId).getBaseModel();
        }
        boolean var1 = false;

        int var3;
        for (int var2 = 0; var2 < 12; ++var2) {
            var3 = equipment[var2];
            if (var3 >= 256 && var3 < 512 && !IdentikitDefinition.get(var3 - 256).method882()) {
                var1 = true;
            }

            if (var3 >= 512 && !ItemDefinition.get(var3 - 512).method530(female)) {
                var1 = true;
            }
        }

        if (var1) {
            return null;
        }
        UnlitModel[] var4 = new UnlitModel[12];
        var3 = 0;

        int var6;
        for (int var5 = 0; var5 < 12; ++var5) {
            var6 = equipment[var5];
            UnlitModel var7;
            if (var6 >= 256 && var6 < 512) {
                var7 = IdentikitDefinition.get(var6 - 256).method1113();
                if (var7 != null) {
                    var4[var3++] = var7;
                }
            }

            if (var6 >= 512) {
                var7 = ItemDefinition.get(var6 - 512).method521(female);
                if (var7 != null) {
                    var4[var3++] = var7;
                }
            }
        }

        UnlitModel var8 = new UnlitModel(var4, var3);

        for (var6 = 0; var6 < 5; ++var6) {
            if (appearance[var6] < colors[var6].length) {
                var8.texturize(HAlign.aShortArray1482[var6], colors[var6][appearance[var6]]);
            }

            if (appearance[var6] < DefaultAudioSystemProvider.aShortArrayArray145[var6].length) {
                var8.texturize(colors2[var6], DefaultAudioSystemProvider.aShortArrayArray145[var6][appearance[var6]]);
            }
        }

        return var8;
    }

    public void method1428(int var1, boolean var2) {
        if (var1 != 1 || !female) {
            int var3 = equipment[equipmentIndexes[var1]];
            if (var3 != 0) {
                var3 -= 256;

                IdentikitDefinition var4;
                do {
                    if (!var2) {
                        --var3;
                        if (var3 < 0) {
                            var3 = identikitCount - 1;
                        }
                    } else {
                        ++var3;
                        if (var3 >= identikitCount) {
                            var3 = 0;
                        }
                    }

                    var4 = IdentikitDefinition.get(var3);
                } while (var4 == null || var4.hidden || var4.index != var1 + (female ? 7 : 0));

                equipment[equipmentIndexes[var1]] = var3 + 256;
                computeHash();
            }
        }
    }

    public void method1432(int var1, boolean var2) {
        int var3 = appearance[var1];
        if (!var2) {
            do {
                --var3;
                if (var3 < 0) {
                    var3 = colors[var1].length - 1;
                }
            } while (!Js5Worker.method1094(var1, var3));
        } else {
            do {
                ++var3;
                if (var3 >= colors[var1].length) {
                    var3 = 0;
                }
            } while (!Js5Worker.method1094(var1, var3));
        }

        appearance[var1] = var3;
        computeHash();
    }

    public void setGender(boolean female) {
        if (this.female != female) {
            update(null, appearance, female, -1);
        }
    }

    public void writeAppearanceTo(Buffer buffer) {
        buffer.p1(female ? 1 : 0);

        for (int i = 0; i < 7; ++i) {
            int equipment = this.equipment[equipmentIndexes[i]];
            if (equipment == 0) {
                buffer.p1(-1);
            } else {
                buffer.p1(equipment - 256);
            }
        }

        for (int i = 0; i < 5; ++i) {
            buffer.p1(appearance[i]);
        }
    }

    public int hash() {
        return transformedNpcId == -1
                ? (equipment[0] << 15)
                + equipment[1]
                + (equipment[11] << 5)
                + (equipment[8] << 10)
                + (appearance[0] << 25)
                + (appearance[4] << 20)
                : 0x12345678 + NpcDefinition.get(transformedNpcId).id;
    }

    public static class Colors {

        public static final short[] aShortArray1225 = new short[]{6798, 8741, 25238, 4626, 4550};
        public static final short[][] aShortArrayArray1226 = new short[][]{{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574, 17050, 0, 127, -31821, -17991}};
        public static final short[] aShortArray1224 = new short[]{-10304, 9104, -1, -1, -1};
        public static final short[][] aShortArrayArray1223 = new short[][]{{6554, 115, 10304, 28, 5702, 7756, 5681, 4510, -31835, 22437, 2859, -11339, 16, 5157, 10446, 3658, -27314, -21965, 472, 580, 784, 21966, 28950, -15697, -14002}, {9104, 10275, 7595, 3610, 7975, 8526, 918, -26734, 24466, 10145, -6882, 5027, 1457, 16565, -30545, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460, -14019}, new short[0], new short[0], new short[0]};

    }
}
