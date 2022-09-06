package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.IterableNodeTable;
import jag.commons.collection.Node;
import jag.commons.collection.ReferenceCache;
import jag.game.InterfaceComponent;
import jag.game.client;
import jag.game.menu.ContextMenuBuilder;
import jag.game.scene.entity.Model;
import jag.game.scene.entity.UnlitModel;
import jag.graphics.Font;
import jag.graphics.JagGraphics;
import jag.graphics.JagGraphics3D;
import jag.graphics.Sprite;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;
import jag.statics.Statics5;

public class ItemDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<Sprite> sprites = new ReferenceCache<>(200);
    public static final ReferenceCache<ItemDefinition> cache = new ReferenceCache<>(64);
    public static final ReferenceCache<Model> models = new ReferenceCache<>(50);

    public static ReferenceTable aReferenceTable722;
    public static ReferenceTable aReferenceTable721;

    public static boolean loadMembersItemDefinitions;

    public String[] groundActions;
    public String[] actions;
    public String name;

    public IterableNodeTable<? super Node> parameters;

    public boolean members;
    public boolean stockMarketable;

    public int[] variantIds;
    public int[] variantStackSizes;

    public short[] aShortArray723;
    public short[] aShortArray724;
    public short[] aShortArray718;
    public short[] aShortArray719;

    public int spriteScale;
    public int id;
    public int spritePitch;
    public int spriteRoll;
    public int spriteTranslateX;
    public int stackable;
    public int spriteTranslateY;
    public int spriteYaw;
    public int value;
    public int noteTemplateId;
    public int noteId;
    public int anInt712;
    public int ambient;
    public int anInt715;
    public int contrast;
    public int team;
    public int maleHeadModel;
    public int anInt579;
    public int modelId;
    public int shiftClickActionIndex;
    public int maleHeadModel2;
    public int maleModel1;
    public int maleModel2;
    public int femaleHeadModel;
    public int femaleHeadModel2;
    public int anInt709;
    public int femaleModel1;
    public int femaleModel2;
    public int resizeX;
    public int resizeY;
    public int resizeZ;
    public int anInt711;
    public int anInt714;
    public int anInt710;
    public int anInt713;

    public ItemDefinition() {
        name = "null";
        spriteScale = 2000;
        spritePitch = 0;
        spriteRoll = 0;
        spriteYaw = 0;
        spriteTranslateX = 0;
        spriteTranslateY = 0;
        stackable = 0;
        value = 1;
        members = false;
        groundActions = new String[]{null, null, "Take", null, null};
        actions = new String[]{null, null, null, null, "Drop"};
        shiftClickActionIndex = -2;
        anInt579 = -1;
        maleModel1 = -1;
        anInt711 = 0;
        anInt709 = -1;
        femaleModel1 = -1;
        anInt710 = 0;
        maleModel2 = -1;
        femaleModel2 = -1;
        maleHeadModel = -1;
        maleHeadModel2 = -1;
        femaleHeadModel = -1;
        femaleHeadModel2 = -1;
        noteId = -1;
        noteTemplateId = -1;
        resizeX = 128;
        resizeY = 128;
        resizeZ = 128;
        ambient = 0;
        contrast = 0;
        team = 0;
        stockMarketable = false;
        anInt713 = -1;
        anInt714 = -1;
        anInt715 = -1;
        anInt712 = -1;
    }

    public static ItemDefinition get(int id) {
        ItemDefinition def = cache.get(id);
        if (def != null) {
            return def;
        }
        byte[] var3 = aReferenceTable721.unpack(10, id);
        def = new ItemDefinition();
        def.id = id;
        if (var3 != null) {
            def.decode(new Buffer(var3));
        }

        def.method23();
        if (def.noteTemplateId != -1) {
            def.method524(get(def.noteTemplateId), get(def.noteId));
        }

        if (def.anInt714 != -1) {
            def.copy(get(def.anInt714), get(def.anInt713));
        }

        if (def.anInt712 != -1) {
            def.method523(get(def.anInt712), get(def.anInt715));
        }

        if (!loadMembersItemDefinitions && def.members) {
            def.name = "Members object";
            def.stockMarketable = false;
            def.groundActions = null;
            def.actions = null;
            def.shiftClickActionIndex = -1;
            def.team = 0;
            if (def.parameters != null) {
                boolean var4 = false;

                for (Node param = def.parameters.first(); param != null; param = def.parameters.next()) {
                    ParameterDefinition var6 = ParameterDefinition.get((int) param.key);
                    if (var6.disableOnUse) {
                        param.unlink();
                    } else {
                        var4 = true;
                    }
                }

                if (!var4) {
                    def.parameters = null;
                }
            }
        }

        cache.put(def, id);
        return def;
    }

    public static void clear() {
        cache.clear();
        models.clear();
        sprites.clear();
    }

    public static void method240(ReferenceTable var0, ReferenceTable var1, boolean var2, Font var3) {
        aReferenceTable721 = var0;
        aReferenceTable722 = var1;
        loadMembersItemDefinitions = var2;
        Statics5.anInt838 = aReferenceTable721.getFileCount(10);
        Font.p11_2 = var3;
    }

    public static String quantityToString(int amount) {
        if (amount < 100000) {
            return "<col=ffff00>" + amount + "</col>";
        }
        return amount < 10000000 ? "<col=ffffff>" + amount / 1000 + "K" + "</col>" : "<col=00ff80>" + amount / 1000000 + "M" + "</col>";
    }

    public static void processOpcode(InterfaceComponent component, ItemDefinition definition, int index, int i, boolean idk) {
        String[] var5 = definition.actions;
        byte var6 = -1;
        String var7 = null;
        if (var5 != null && var5[i] != null) {
            if (i == 0) {
                var6 = 33;
            } else if (i == 1) {
                var6 = 34;
            } else if (i == 2) {
                var6 = 35;
            } else if (i == 3) {
                var6 = 36;
            } else {
                var6 = 37;
            }

            var7 = var5[i];
        } else if (i == 4) {
            var6 = 37;
            var7 = "Drop";
        }

        if (var6 != -1 && var7 != null) {
            ContextMenuBuilder.insertRow(var7, client.getColorTags(16748608) + definition.name, var6, definition.id, index, component.uid, idk);
        }

    }

    public static Sprite getSprite(int itemId, int stackSize, int borderThickness, int shadowColor, int stackSizeMode, boolean noted) {
        if (stackSize == -1) {
            stackSizeMode = 0;
        } else if (stackSizeMode == 2 && stackSize != 1) {
            stackSizeMode = 1;
        }

        long spriteUID = ((long) shadowColor << 42) + ((long) stackSizeMode << 40) + ((long) borderThickness << 38) + (long) itemId + ((long) stackSize << 16);

        Sprite sprite;
        if (!noted) {
            sprite = sprites.get(spriteUID);
            if (sprite != null) {
                return sprite;
            }
        }

        ItemDefinition definition = get(itemId);
        if (stackSize > 1 && definition.variantIds != null) {
            int variant = -1;
            for (int i = 0; i < 10; ++i) {
                if (stackSize >= definition.variantStackSizes[i] && definition.variantStackSizes[i] != 0) {
                    variant = definition.variantIds[i];
                }
            }

            if (variant != -1) {
                definition = get(variant);
            }
        }

        Model model = definition.getModel(1);
        if (model == null) {
            return null;
        }

        Sprite base = null;
        if (definition.noteTemplateId != -1) {
            base = getSprite(definition.noteId, 10, 1, 0, 0, true);
            if (base == null) {
                return null;
            }
        } else if (definition.anInt714 != -1) {
            base = getSprite(definition.anInt713, stackSize, borderThickness, shadowColor, 0, false);
            if (base == null) {
                return null;
            }
        } else if (definition.anInt712 != -1) {
            base = getSprite(definition.anInt715, stackSize, 0, 0, 0, false);
            if (base == null) {
                return null;
            }
        }

        int[] pixels = JagGraphics.drawingAreaPixels;
        int areaWidth = JagGraphics.drawingAreaWidth;
        int areaHeight = JagGraphics.drawingAreaHeight;

        int[] area = new int[4];
        JagGraphics.method1366(area);
        sprite = new Sprite(36, 32);
        JagGraphics.setTarget(sprite.pixels, 36, 32);
        JagGraphics.clear();
        JagGraphics3D.method499();
        JagGraphics3D.method637(16, 16);
        JagGraphics3D.aBoolean789 = false;
        if (definition.anInt712 != -1) {
            base.renderAlphaAt(0, 0);
        }

        int var19 = definition.spriteScale;
        if (noted) {
            var19 = (int) (1.5D * (double) var19);
        } else if (borderThickness == 2) {
            var19 = (int) (1.04D * (double) var19);
        }

        int ps = var19 * JagGraphics3D.SIN_TABLE[definition.spritePitch] >> 16;
        int pc = var19 * JagGraphics3D.COS_TABLE[definition.spritePitch] >> 16;
        model.computeBounds();
        model.method1289(0, definition.spriteRoll, definition.spriteYaw, definition.spritePitch, definition.spriteTranslateX, model.height / 2 + ps + definition.spriteTranslateY, pc + definition.spriteTranslateY);
        if (definition.anInt714 != -1) {
            base.renderAlphaAt(0, 0);
        }

        if (borderThickness >= 1) {
            sprite.method822(1);
        }

        if (borderThickness >= 2) {
            sprite.method822(16777215);
        }

        if (shadowColor != 0) {
            sprite.method835(shadowColor);
        }

        JagGraphics.setTarget(sprite.pixels, 36, 32);
        if (definition.noteTemplateId != -1) {
            base.renderAlphaAt(0, 0);
        }

        if (stackSizeMode == 1 || stackSizeMode == 2 && definition.stackable == 1) {
            Font.p11_2.drawString(quantityToString(stackSize), 0, 9, 16776960, 1);
        }

        if (!noted) {
            sprites.put(sprite, spriteUID);
        }

        JagGraphics.setTarget(pixels, areaWidth, areaHeight);
        JagGraphics.method1373(area);
        JagGraphics3D.method499();
        JagGraphics3D.aBoolean789 = true;
        return sprite;
    }

    public static void setLoadMembers(boolean var0) {
        if (var0 != loadMembersItemDefinitions) {
            clear();
            loadMembersItemDefinitions = var0;
        }

    }

    public void decode(Buffer var1, int var2) {
        if (var2 == 1) {
            modelId = var1.g2();
        } else if (var2 == 2) {
            name = var1.gstr();
        } else if (var2 == 4) {
            spriteScale = var1.g2();
        } else if (var2 == 5) {
            spritePitch = var1.g2();
        } else if (var2 == 6) {
            spriteRoll = var1.g2();
        } else if (var2 == 7) {
            spriteTranslateX = var1.g2();
            if (spriteTranslateX > 32767) {
                spriteTranslateX -= 65536;
            }
        } else if (var2 == 8) {
            spriteTranslateY = var1.g2();
            if (spriteTranslateY > 32767) {
                spriteTranslateY -= 65536;
            }
        } else if (var2 == 11) {
            stackable = 1;
        } else if (var2 == 12) {
            value = var1.g4();
        } else if (var2 == 16) {
            members = true;
        } else if (var2 == 23) {
            anInt579 = var1.g2();
            anInt711 = var1.g1();
        } else if (var2 == 24) {
            maleModel1 = var1.g2();
        } else if (var2 == 25) {
            anInt709 = var1.g2();
            anInt710 = var1.g1();
        } else if (var2 == 26) {
            femaleModel1 = var1.g2();
        } else if (var2 >= 30 && var2 < 35) {
            groundActions[var2 - 30] = var1.gstr();
            if (groundActions[var2 - 30].equalsIgnoreCase("Hidden")) {
                groundActions[var2 - 30] = null;
            }
        } else if (var2 >= 35 && var2 < 40) {
            actions[var2 - 35] = var1.gstr();
        } else {
            int var3;
            int var4;
            if (var2 == 40) {
                var3 = var1.g1();
                aShortArray723 = new short[var3];
                aShortArray718 = new short[var3];

                for (var4 = 0; var4 < var3; ++var4) {
                    aShortArray723[var4] = (short) var1.g2();
                    aShortArray718[var4] = (short) var1.g2();
                }
            } else if (var2 == 41) {
                var3 = var1.g1();
                aShortArray724 = new short[var3];
                aShortArray719 = new short[var3];

                for (var4 = 0; var4 < var3; ++var4) {
                    aShortArray724[var4] = (short) var1.g2();
                    aShortArray719[var4] = (short) var1.g2();
                }
            } else if (var2 == 42) {
                shiftClickActionIndex = var1.g1b();
            } else if (var2 == 65) {
                stockMarketable = true;
            } else if (var2 == 78) {
                maleModel2 = var1.g2();
            } else if (var2 == 79) {
                femaleModel2 = var1.g2();
            } else if (var2 == 90) {
                maleHeadModel = var1.g2();
            } else if (var2 == 91) {
                femaleHeadModel = var1.g2();
            } else if (var2 == 92) {
                maleHeadModel2 = var1.g2();
            } else if (var2 == 93) {
                femaleHeadModel2 = var1.g2();
            } else if (var2 == 95) {
                spriteYaw = var1.g2();
            } else if (var2 == 97) {
                noteId = var1.g2();
            } else if (var2 == 98) {
                noteTemplateId = var1.g2();
            } else if (var2 >= 100 && var2 < 110) {
                if (variantIds == null) {
                    variantIds = new int[10];
                    variantStackSizes = new int[10];
                }

                variantIds[var2 - 100] = var1.g2();
                variantStackSizes[var2 - 100] = var1.g2();
            } else if (var2 == 110) {
                resizeX = var1.g2();
            } else if (var2 == 111) {
                resizeY = var1.g2();
            } else if (var2 == 112) {
                resizeZ = var1.g2();
            } else if (var2 == 113) {
                ambient = var1.g1b();
            } else if (var2 == 114) {
                contrast = var1.g1b() * 5;
            } else if (var2 == 115) {
                team = var1.g1();
            } else if (var2 == 139) {
                anInt713 = var1.g2();
            } else if (var2 == 140) {
                anInt714 = var1.g2();
            } else if (var2 == 148) {
                anInt715 = var1.g2();
            } else if (var2 == 149) {
                anInt712 = var1.g2();
            } else if (var2 == 249) {
                parameters = IterableNodeTable.decode(var1, parameters);
            }
        }

    }

    public final Model getModel(int var1) {
        if (variantIds != null && var1 > 1) {
            int var3 = -1;

            for (int var4 = 0; var4 < 10; ++var4) {
                if (var1 >= variantStackSizes[var4] && variantStackSizes[var4] != 0) {
                    var3 = variantIds[var4];
                }
            }

            if (var3 != -1) {
                return get(var3).getModel(1);
            }
        }

        Model var5 = models.get(id);
        if (var5 != null) {
            return var5;
        }
        UnlitModel var6 = UnlitModel.method982(aReferenceTable722, modelId, 0);
        if (var6 == null) {
            return null;
        }
        if (resizeX != 128 || resizeY != 128 || resizeZ != 128) {
            var6.method764(resizeX, resizeY, resizeZ);
        }

        int i;
        if (aShortArray723 != null) {
            for (i = 0; i < aShortArray723.length; ++i) {
                var6.texturize(aShortArray723[i], aShortArray718[i]);
            }
        }

        if (aShortArray724 != null) {
            for (i = 0; i < aShortArray724.length; ++i) {
                var6.colorize(aShortArray724[i], aShortArray719[i]);
            }
        }

        var5 = var6.light(ambient + 64, contrast + 768, -50, -10, -50);
        var5.fitsSingleTile = true;
        models.put(var5, id);
        return var5;
    }

    public final UnlitModel method531(int var1) {
        int var3;
        if (variantIds != null && var1 > 1) {
            int var2 = -1;

            for (var3 = 0; var3 < 10; ++var3) {
                if (var1 >= variantStackSizes[var3] && variantStackSizes[var3] != 0) {
                    var2 = variantIds[var3];
                }
            }

            if (var2 != -1) {
                return get(var2).method531(1);
            }
        }

        UnlitModel var4 = UnlitModel.method982(aReferenceTable722, modelId, 0);
        if (var4 == null) {
            return null;
        }
        if (resizeX != 128 || resizeY != 128 || resizeZ != 128) {
            var4.method764(resizeX, resizeY, resizeZ);
        }

        if (aShortArray723 != null) {
            for (var3 = 0; var3 < aShortArray723.length; ++var3) {
                var4.texturize(aShortArray723[var3], aShortArray718[var3]);
            }
        }

        if (aShortArray724 != null) {
            for (var3 = 0; var3 < aShortArray724.length; ++var3) {
                var4.colorize(aShortArray724[var3], aShortArray719[var3]);
            }
        }

        return var4;
    }

    public void method23() {
    }

    public final boolean method530(boolean var1) {
        int var2 = maleHeadModel;
        int var3 = maleHeadModel2;
        if (var1) {
            var2 = femaleHeadModel;
            var3 = femaleHeadModel2;
        }

        if (var2 == -1) {
            return true;
        }
        boolean var4 = aReferenceTable722.load(var2, 0);

        if (var3 != -1 && !aReferenceTable722.load(var3, 0)) {
            var4 = false;
        }

        return var4;
    }

    public void decode(Buffer var1) {
        while (true) {
            int var2 = var1.g1();
            if (var2 == 0) {
                return;
            }

            decode(var1, var2);
        }
    }

    public final UnlitModel method521(boolean var1) {
        int var2 = maleHeadModel;
        int var3 = maleHeadModel2;
        if (var1) {
            var2 = femaleHeadModel;
            var3 = femaleHeadModel2;
        }

        if (var2 == -1) {
            return null;
        }
        UnlitModel var4 = UnlitModel.method982(aReferenceTable722, var2, 0);
        if (var3 != -1) {
            UnlitModel var5 = UnlitModel.method982(aReferenceTable722, var3, 0);
            UnlitModel[] var6 = new UnlitModel[]{var4, var5};
            var4 = new UnlitModel(var6, 2);
        }

        int var7;
        if (aShortArray723 != null) {
            for (var7 = 0; var7 < aShortArray723.length; ++var7) {
                var4.texturize(aShortArray723[var7], aShortArray718[var7]);
            }
        }

        if (aShortArray724 != null) {
            for (var7 = 0; var7 < aShortArray724.length; ++var7) {
                var4.colorize(aShortArray724[var7], aShortArray719[var7]);
            }
        }

        return var4;
    }

    public final boolean method518(boolean var1) {
        int var2 = anInt579;
        int var3 = maleModel1;
        int var4 = maleModel2;
        if (var1) {
            var2 = anInt709;
            var3 = femaleModel1;
            var4 = femaleModel2;
        }

        if (var2 == -1) {
            return true;
        }
        boolean var5 = aReferenceTable722.load(var2, 0);

        if (var3 != -1 && !aReferenceTable722.load(var3, 0)) {
            var5 = false;
        }

        if (var4 != -1 && !aReferenceTable722.load(var4, 0)) {
            var5 = false;
        }

        return var5;
    }

    public void method524(ItemDefinition var1, ItemDefinition var2) {
        modelId = var1.modelId;
        spriteScale = var1.spriteScale;
        spritePitch = var1.spritePitch;
        spriteRoll = var1.spriteRoll;
        spriteYaw = var1.spriteYaw;
        spriteTranslateX = var1.spriteTranslateX;
        spriteTranslateY = var1.spriteTranslateY;
        aShortArray723 = var1.aShortArray723;
        aShortArray718 = var1.aShortArray718;
        aShortArray724 = var1.aShortArray724;
        aShortArray719 = var1.aShortArray719;
        name = var2.name;
        members = var2.members;
        value = var2.value;
        stackable = 1;
    }

    public final UnlitModel getEquipmentModel(boolean var1) {
        int var3 = anInt579;
        int var4 = maleModel1;
        int var5 = maleModel2;
        if (var1) {
            var3 = anInt709;
            var4 = femaleModel1;
            var5 = femaleModel2;
        }

        if (var3 == -1) {
            return null;
        }
        UnlitModel var6 = UnlitModel.method982(aReferenceTable722, var3, 0);
        if (var4 != -1) {
            UnlitModel var7 = UnlitModel.method982(aReferenceTable722, var4, 0);
            if (var5 != -1) {
                UnlitModel var8 = UnlitModel.method982(aReferenceTable722, var5, 0);
                UnlitModel[] var9 = new UnlitModel[]{var6, var7, var8};
                var6 = new UnlitModel(var9, 3);
            } else {
                UnlitModel[] var11 = new UnlitModel[]{var6, var7};
                var6 = new UnlitModel(var11, 2);
            }
        }

        if (!var1 && anInt711 != 0) {
            var6.method976(0, anInt711, 0);
        }

        if (var1 && anInt710 != 0) {
            var6.method976(0, anInt710, 0);
        }

        int var10;
        if (aShortArray723 != null) {
            for (var10 = 0; var10 < aShortArray723.length; ++var10) {
                var6.texturize(aShortArray723[var10], aShortArray718[var10]);
            }
        }

        if (aShortArray724 != null) {
            for (var10 = 0; var10 < aShortArray724.length; ++var10) {
                var6.colorize(aShortArray724[var10], aShortArray719[var10]);
            }
        }

        return var6;
    }

    public void copy(ItemDefinition var1, ItemDefinition var2) {
        modelId = var1.modelId;
        spriteScale = var1.spriteScale;
        spritePitch = var1.spritePitch;
        spriteRoll = var1.spriteRoll;
        spriteYaw = var1.spriteYaw;
        spriteTranslateX = var1.spriteTranslateX;
        spriteTranslateY = var1.spriteTranslateY;
        aShortArray723 = var2.aShortArray723;
        aShortArray718 = var2.aShortArray718;
        aShortArray724 = var2.aShortArray724;
        aShortArray719 = var2.aShortArray719;
        name = var2.name;
        members = var2.members;
        stackable = var2.stackable;
        anInt579 = var2.anInt579;
        maleModel1 = var2.maleModel1;
        maleModel2 = var2.maleModel2;
        anInt709 = var2.anInt709;
        femaleModel1 = var2.femaleModel1;
        femaleModel2 = var2.femaleModel2;
        maleHeadModel = var2.maleHeadModel;
        maleHeadModel2 = var2.maleHeadModel2;
        femaleHeadModel = var2.femaleHeadModel;
        femaleHeadModel2 = var2.femaleHeadModel2;
        team = var2.team;
        groundActions = var2.groundActions;
        actions = new String[5];
        if (var2.actions != null) {
            System.arraycopy(var2.actions, 0, actions, 0, 4);
        }

        actions[4] = "Discard";
        value = 0;
    }

    public void method523(ItemDefinition var1, ItemDefinition var2) {
        modelId = var1.modelId;
        spriteScale = var1.spriteScale;
        spritePitch = var1.spritePitch;
        spriteRoll = var1.spriteRoll;
        spriteYaw = var1.spriteYaw;
        spriteTranslateX = var1.spriteTranslateX;
        spriteTranslateY = var1.spriteTranslateY;
        aShortArray723 = var1.aShortArray723;
        aShortArray718 = var1.aShortArray718;
        aShortArray724 = var1.aShortArray724;
        aShortArray719 = var1.aShortArray719;
        stackable = var1.stackable;
        name = var2.name;
        value = 0;
        members = false;
        stockMarketable = false;
    }

    public int getShiftOptionIndex() {
        if (shiftClickActionIndex != -1 && actions != null) {
            if (shiftClickActionIndex >= 0) {
                return actions[shiftClickActionIndex] != null ? shiftClickActionIndex : -1;
            }
            return "Drop".equalsIgnoreCase(actions[4]) ? 4 : -1;
        }
        return -1;
    }

    public ItemDefinition method519(int var1) {
        if (variantIds != null && var1 > 1) {
            int var2 = -1;

            for (int var3 = 0; var3 < 10; ++var3) {
                if (var1 >= variantStackSizes[var3] && variantStackSizes[var3] != 0) {
                    var2 = variantIds[var3];
                }
            }

            if (var2 != -1) {
                return get(var2);
            }
        }

        return this;
    }

    public int method527(int var1, int var2) {
        return IterableNodeTable.getIntParameter(parameters, var1, var2);
    }

    public String method520(int var1, String var2) {
        return IterableNodeTable.getStringParameter(parameters, var1, var2);
    }
}
