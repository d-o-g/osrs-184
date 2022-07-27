package jag.game;

import jag.audi.DefaultAudioSystemProvider;
import jag.commons.collection.ReferenceCache;
import jag.game.scene.entity.Model;
import jag.game.scene.entity.UnlitModel;
import jag.game.type.AnimationSequence;
import jag.game.type.IdentikitDefinition;
import jag.game.type.ItemDefinition;
import jag.game.type.NpcDefinition;
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

    public static boolean method1094(int var0, int var1) {
        return var0 != 4 || var1 < 8;
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
        long hash = this.hash;
        int var3 = equipment[5];
        int var4 = equipment[9];
        equipment[5] = var4;
        equipment[9] = var3;
        this.hash = 0L;

        for (int i = 0; i < 12; ++i) {
            this.hash <<= 4;
            if (equipment[i] >= 256) {
                this.hash += equipment[i] - 256;
            }
        }

        if (equipment[0] >= 256) {
            this.hash += equipment[0] - 256 >> 4;
        }

        if (equipment[1] >= 256) {
            this.hash += equipment[1] - 256 >> 8;
        }

        for (int i = 0; i < 5; ++i) {
            this.hash <<= 3;
            this.hash += this.appearance[i];
        }

        this.hash <<= 1;
        this.hash += this.female ? 1 : 0;
        equipment[5] = var3;
        equipment[9] = var4;
        if (hash != 0L && this.hash != hash) {
            models.remove(hash);
        }
    }

    public Model getModel(AnimationSequence animation, int animFrame, AnimationSequence stance, int stanceFrame) {
        if (transformedNpcId != -1) {
            return NpcDefinition.get(transformedNpcId).getModel(animation, animFrame, stance, stanceFrame);
        }

        long hash = this.hash;
        int[] equipment = this.equipment;
        if (animation != null && (animation.offHand >= 0 || animation.mainHand >= 0)) {
            equipment = new int[12];

            System.arraycopy(this.equipment, 0, equipment, 0, 12);

            if (animation.offHand >= 0) {
                hash += (long) animation.offHand - this.equipment[5] << 8;
                equipment[5] = animation.offHand;
            }

            if (animation.mainHand >= 0) {
                hash += (long) animation.mainHand - this.equipment[3] << 16;
                equipment[3] = animation.mainHand;
            }
        }

        Model model = models.get(hash);
        if (model == null) {
            boolean valid = false;

            for (int i = 0; i < 12; ++i) {
                int id = equipment[i];
                if (id >= 256 && id < 512 && !IdentikitDefinition.get(id - 256).method1114()) {
                    valid = true;
                }

                if (id >= 512 && !ItemDefinition.get(id - 512).method518(female)) {
                    valid = true;
                }
            }

            if (valid) {
                if (modelHash != -1L) {
                    model = models.get(modelHash);
                }

                if (model == null) {
                    return null;
                }
            }

            if (model == null) {
                UnlitModel[] parts = new UnlitModel[12];
                int partCount = 0;
                for (int i = 0; i < 12; ++i) {
                    int id = equipment[i];
                    if (id >= 256 && id < 512) {
                        UnlitModel kitModel = IdentikitDefinition.get(id - 256).method978();
                        if (kitModel != null) {
                            parts[partCount++] = kitModel;
                        }
                    }

                    if (id >= 512) {
                        UnlitModel equippedModel = ItemDefinition.get(id - 512).getEquipmentModel(female);
                        if (equippedModel != null) {
                            parts[partCount++] = equippedModel;
                        }
                    }
                }

                UnlitModel fullModel = new UnlitModel(parts, partCount);

                for (int i = 0; i < 5; ++i) {
                    if (appearance[i] < colors[i].length) {
                        fullModel.texturize(HAlign.aShortArray1482[i], colors[i][appearance[i]]);
                    }

                    if (appearance[i] < DefaultAudioSystemProvider.aShortArrayArray145[i].length) {
                        fullModel.texturize(colors2[i], DefaultAudioSystemProvider.aShortArrayArray145[i][appearance[i]]);
                    }
                }

                model = fullModel.light(64, 850, -30, -50, -30);
                models.put(model, hash);
                modelHash = hash;
            }
        }

        if (animation == null && stance == null) {
            return model;
        }

        Model animated;
        if (animation != null && stance != null) {
            animated = animation.applyStanceAndAnimation(model, animFrame, stance, stanceFrame);
        } else if (animation != null) {
            animated = animation.applySequence(model, animFrame);
        } else {
            animated = stance.applySequence(model, stanceFrame);
        }

        return animated;
    }

    UnlitModel method1427() {
        if (transformedNpcId != -1) {
            return NpcDefinition.get(transformedNpcId).getBaseModel();
        }
        boolean var1 = false;

        for (int var2 = 0; var2 < 12; ++var2) {
            int var3 = equipment[var2];
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
        int var3 = 0;
        for (int var5 = 0; var5 < 12; ++var5) {
            int var6 = equipment[var5];
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

        for (int i = 0; i < 5; ++i) {
            if (appearance[i] < colors[i].length) {
                var8.texturize(HAlign.aShortArray1482[i], colors[i][appearance[i]]);
            }

            if (appearance[i] < DefaultAudioSystemProvider.aShortArrayArray145[i].length) {
                var8.texturize(colors2[i], DefaultAudioSystemProvider.aShortArrayArray145[i][appearance[i]]);
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

    public void method1432(int index, boolean var2) {
        int color = appearance[index];
        if (!var2) {
            do {
                --color;
                if (color < 0) {
                    color = colors[index].length - 1;
                }
            } while (!method1094(index, color));
        } else {
            do {
                ++color;
                if (color >= colors[index].length) {
                    color = 0;
                }
            } while (!method1094(index, color));
        }

        appearance[index] = color;

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
