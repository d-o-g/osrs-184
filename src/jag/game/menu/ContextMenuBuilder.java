package jag.game.menu;

import jag.audi.DefaultAudioSystemProvider;
import jag.commons.collection.NodeDeque;
import jag.game.InterfaceComponent;
import jag.game.client;
import jag.game.option.AttackOptionPriority;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.EntityUID;
import jag.game.scene.entity.NpcEntity;
import jag.game.scene.entity.Pickable;
import jag.game.scene.entity.PlayerEntity;
import jag.game.type.ItemDefinition;
import jag.game.type.NpcDefinition;
import jag.game.type.ObjectDefinition;
import jag.opcode.GPI;
import jag.statics.CursorEntities;
import jag.statics.Statics52;

public class ContextMenuBuilder {

    public static void insertRow(String action, String target, int opcode, int primary, int secondary, int tertiary) {
        insertRow(action, target, opcode, primary, secondary, tertiary, false);
    }

    public static void insertRow(String action, String target, int opcode, int primary, int secondary, int tertiary, boolean prioritize) {
        if (!ContextMenu.open) {
            if (ContextMenu.rowCount < 500) {
                ContextMenu.actions[ContextMenu.rowCount] = action;
                ContextMenu.targets[ContextMenu.rowCount] = target;
                ContextMenu.opcodes[ContextMenu.rowCount] = opcode;
                ContextMenu.primaryArgs[ContextMenu.rowCount] = primary;
                ContextMenu.secondaryArgs[ContextMenu.rowCount] = secondary;
                ContextMenu.tertiaryArgs[ContextMenu.rowCount] = tertiary;
                ContextMenu.prioritizedActions[ContextMenu.rowCount] = prioritize;
                ++ContextMenu.rowCount;
            }

        }
    }

    public static void applyComponentActions(InterfaceComponent component, int rootX, int rootY) {

        if (component.buttonType == 1) {
            insertRow(component.toolTip, "", 24, 0, 0, component.uid);
        }

        if (component.buttonType == 2 && !ComponentSelection.state) {
            String action = InterfaceComponent.getSelectedAction(component);
            if (action != null) {
                insertRow(action, client.getColorTags(65280) + component.spellName, 25, 0, -1, component.uid);
            }
        }

        if (component.buttonType == 3) {
            insertRow("Close", "", 26, 0, 0, component.uid);
        }

        if (component.buttonType == 4) {
            insertRow(component.toolTip, "", 28, 0, 0, component.uid);
        }

        if (component.buttonType == 5) {
            insertRow(component.toolTip, "", 29, 0, 0, component.uid);
        }

        if (component.buttonType == 6 && client.pleaseWaitComponent == null) {
            insertRow(component.toolTip, "", 30, 0, -1, component.uid);
        }

        if (component.type == 2) {
            int index = 0;
            for (int baseX = 0; baseX < component.height; ++baseX) {
                for (int baseY = 0; baseY < component.width; ++baseY) {
                    int x = (component.xPadding + 32) * baseY;
                    int y = (component.yPadding + 32) * baseX;
                    if (index < 20) {
                        x += component.xSprites[index];
                        y += component.ySprites[index];
                    }

                    if (rootX >= x && rootY >= y && rootX < x + 32 && rootY < y + 32) {
                        client.draggingComponentIndex = index;
                        DefaultAudioSystemProvider.processingItemComponent = component;
                        if (component.itemIds[index] > 0) {
                            label331:
                            {
                                ItemDefinition def = ItemDefinition.get(component.itemIds[index] - 1);
                                if (ItemSelection.state == 1) {
                                    int cfg = InterfaceComponent.getConfig(component);
                                    boolean allowUsability = (cfg >> 30 & 1) != 0;
                                    if (allowUsability) {
                                        if (component.uid != ItemSelection.uid || index != ItemSelection.id) {
                                            insertRow("Use", ItemSelection.name + " " + "->" + " " + client.getColorTags(16748608) + def.name, 31, def.id, index, component.uid);
                                        }
                                        break label331;
                                    }
                                }

                                if (ComponentSelection.state) {
                                    int cfg = InterfaceComponent.getConfig(component);
                                    boolean allowUsability = (cfg >> 30 & 1) != 0;
                                    if (allowUsability) {
                                        if ((ComponentSelection.targetFlags & 16) == 16) {
                                            insertRow(ComponentSelection.action, ComponentSelection.name + " " + "->" + " " + client.getColorTags(16748608) + def.name, 32, def.id, index, component.uid);
                                        }
                                        break label331;
                                    }
                                }

                                int shiftIndex = -1;
                                if (client.aBoolean1044 && Statics52.method345()) {
                                    shiftIndex = def.getShiftOptionIndex();
                                }

                                int cfg = InterfaceComponent.getConfig(component);
                                boolean var15 = (cfg >> 30 & 1) != 0;
                                if (var15) {
                                    for (int var16 = 4; var16 >= 3; --var16) {
                                        if (var16 != shiftIndex) {
                                            ItemDefinition.processOpcode(component, def, index, var16, false);
                                        }
                                    }
                                }

                                int var17 = InterfaceComponent.getConfig(component);
                                boolean var26 = (var17 >> 31 & 1) != 0;
                                if (var26) {
                                    insertRow("Use", client.getColorTags(16748608) + def.name, 38, def.id, index, component.uid);
                                }

                                int var18 = InterfaceComponent.getConfig(component);
                                boolean allowUsability = (var18 >> 30 & 1) != 0;
                                if (allowUsability) {
                                    for (int i = 2; i >= 0; --i) {
                                        if (shiftIndex != i) {
                                            ItemDefinition.processOpcode(component, def, index, i, false);
                                        }
                                    }

                                    if (shiftIndex >= 0) {
                                        ItemDefinition.processOpcode(component, def, index, shiftIndex, true);
                                    }
                                }

                                String[] tableActions = component.tableActions;
                                if (tableActions != null) {
                                    for (int i = 4; i >= 0; --i) {
                                        if (tableActions[i] != null) {
                                            byte opcode = 0;
                                            if (i == 0) {
                                                opcode = 39;
                                            }

                                            if (i == 1) {
                                                opcode = 40;
                                            }

                                            if (i == 2) {
                                                opcode = 41;
                                            }

                                            if (i == 3) {
                                                opcode = 42;
                                            }

                                            if (i == 4) {
                                                opcode = 43;
                                            }

                                            insertRow(tableActions[i], client.getColorTags(16748608) + def.name, opcode, def.id, index, component.uid);
                                        }
                                    }
                                }

                                insertRow("Examine", client.getColorTags(16748608) + def.name, 1005, def.id, index, component.uid);
                            }
                        }
                    }

                    ++index;
                }
            }
        }

        if (component.if3) {
            if (ComponentSelection.state) {
                int cfg = InterfaceComponent.getConfig(component);
                boolean var24 = (cfg >> 21 & 1) != 0;
                if (var24 && (ComponentSelection.targetFlags & 32) == 32) {
                    insertRow(ComponentSelection.action, ComponentSelection.name + " " + "->" + " " + component.name, 58, 0, component.subComponentIndex, component.uid);
                }
            } else {
                for (int i = 9; i >= 5; --i) {
                    String action = InterfaceComponent.getAction(component, i);
                    if (action != null) {
                        insertRow(action, component.name, 1007, i + 1, component.subComponentIndex, component.uid);
                    }
                }

                String selected = InterfaceComponent.getSelectedAction(component);
                if (selected != null) {
                    insertRow(selected, component.name, 25, 0, component.subComponentIndex, component.uid);
                }

                for (int i = 4; i >= 0; --i) {
                    String action = InterfaceComponent.getAction(component, i);
                    if (action != null) {
                        insertRow(action, component.name, 57, i + 1, component.subComponentIndex, component.uid, component.prioritizeMenuOptions);
                    }
                }

                int cfg = InterfaceComponent.getConfig(component);
                boolean var25 = (cfg & 1) != 0;
                if (var25) {
                    insertRow("Continue", "", 30, 0, component.subComponentIndex, component.uid);
                }
            }
        }
    }

    public static void applyPlayerActions(PlayerEntity var0, int var1, int var2, int var3) {
        if (PlayerEntity.local != var0) {
            if (ContextMenu.rowCount < 400) {
                int var5;
                String var9;
                if (var0.skillLevel == 0) {
                    String var4 = var0.actions[0] + var0.namePair + var0.actions[1];
                    var5 = var0.combatLevel;
                    int var6 = PlayerEntity.local.combatLevel;
                    int var7 = var6 - var5;
                    String var8;
                    if (var7 < -9) {
                        var8 = client.getColorTags(16711680);
                    } else if (var7 < -6) {
                        var8 = client.getColorTags(16723968);
                    } else if (var7 < -3) {
                        var8 = client.getColorTags(16740352);
                    } else if (var7 < 0) {
                        var8 = client.getColorTags(16756736);
                    } else if (var7 > 9) {
                        var8 = client.getColorTags(65280);
                    } else if (var7 > 6) {
                        var8 = client.getColorTags(4259584);
                    } else if (var7 > 3) {
                        var8 = client.getColorTags(8453888);
                    } else if (var7 > 0) {
                        var8 = client.getColorTags(12648192);
                    } else {
                        var8 = client.getColorTags(16776960);
                    }

                    var9 = var4 + var8 + " " + " (" + "level-" + var0.combatLevel + ")" + var0.actions[2];
                } else {
                    var9 = var0.actions[0] + var0.namePair + var0.actions[1] + " " + " (" + "skill-" + var0.skillLevel + ")" + var0.actions[2];
                }

                int var10;
                if (ItemSelection.state == 1) {
                    insertRow("Use", ItemSelection.name + " " + "->" + " " + client.getColorTags(16777215) + var9, 14, var1, var2, var3);
                } else if (ComponentSelection.state) {
                    if ((ComponentSelection.targetFlags & 8) == 8) {
                        insertRow(ComponentSelection.action, ComponentSelection.name + " " + "->" + " " + client.getColorTags(16777215) + var9, 15, var1, var2, var3);
                    }
                } else {
                    for (var10 = 7; var10 >= 0; --var10) {
                        if (client.playerActions[var10] != null) {
                            short var11 = 0;
                            if (client.playerActions[var10].equalsIgnoreCase("Attack")) {
                                if (AttackOptionPriority.HIDDEN == client.playerAttackOptionPriority) {
                                    continue;
                                }

                                if (AttackOptionPriority.RIGHT == client.playerAttackOptionPriority || client.playerAttackOptionPriority == AttackOptionPriority.DEPENDS && var0.combatLevel > PlayerEntity.local.combatLevel) {
                                    var11 = 2000;
                                }

                                if (PlayerEntity.local.team != 0 && var0.team != 0) {
                                    if (var0.team == PlayerEntity.local.team) {
                                        var11 = 2000;
                                    } else {
                                        var11 = 0;
                                    }
                                }
                            } else if (client.playerActionPriorities[var10]) {
                                var11 = 2000;
                            }

                            boolean var12 = false;
                            var5 = client.PLAYER_ACTION_OPCODES[var10] + var11;
                            insertRow(client.playerActions[var10], client.getColorTags(16777215) + var9, var5, var1, var2, var3);
                        }
                    }
                }

                for (var10 = 0; var10 < ContextMenu.rowCount; ++var10) {
                    if (ContextMenu.opcodes[var10] == 23) {
                        ContextMenu.targets[var10] = client.getColorTags(16777215) + var9;
                        break;
                    }
                }

            }
        }
    }

    public static void applyNpcActions(NpcDefinition def, int index, int sceneX, int sceneY) {
        if (ContextMenu.rowCount < 400) {
            if (def.transformIds != null) {
                def = def.transform();
            }

            if (def != null && def.interactable && (!def.follower || client.anInt1053 == index)) {
                String menuDefinition = def.name;
                int var6;
                int opcode;
                if (def.combatLevel != 0) {
                    var6 = def.combatLevel;
                    opcode = PlayerEntity.local.combatLevel;
                    int var8 = opcode - var6;
                    String color;
                    if (var8 < -9) {
                        color = client.getColorTags(16711680);
                    } else if (var8 < -6) {
                        color = client.getColorTags(16723968);
                    } else if (var8 < -3) {
                        color = client.getColorTags(16740352);
                    } else if (var8 < 0) {
                        color = client.getColorTags(16756736);
                    } else if (var8 > 9) {
                        color = client.getColorTags(65280);
                    } else if (var8 > 6) {
                        color = client.getColorTags(4259584);
                    } else if (var8 > 3) {
                        color = client.getColorTags(8453888);
                    } else if (var8 > 0) {
                        color = client.getColorTags(12648192);
                    } else {
                        color = client.getColorTags(16776960);
                    }

                    menuDefinition = menuDefinition + color + " " + " (" + "level-" + def.combatLevel + ")";
                }

                if (def.follower && client.aBoolean1042) {
                    insertRow("Examine", client.getColorTags(16776960) + menuDefinition, 1003, index, sceneX, sceneY);
                }

                if (ItemSelection.state == 1) {
                    insertRow("Use", ItemSelection.name + " " + "->" + " " + client.getColorTags(16776960) + menuDefinition, 7, index, sceneX, sceneY);
                } else if (ComponentSelection.state) {
                    if ((ComponentSelection.targetFlags & 2) == 2) {
                        insertRow(ComponentSelection.action, ComponentSelection.name + " " + "->" + " " + client.getColorTags(16776960) + menuDefinition, 8, index, sceneX, sceneY);
                    }
                } else {
                    int var10 = def.follower && client.aBoolean1042 ? 2000 : 0;
                    String[] var12 = def.actions;
                    if (var12 != null) {
                        for (var6 = 4; var6 >= 0; --var6) {
                            if (var12[var6] != null && !var12[var6].equalsIgnoreCase("Attack")) {
                                opcode = 0;
                                if (var6 == 0) {
                                    opcode = var10 + 9;
                                }

                                if (var6 == 1) {
                                    opcode = var10 + 10;
                                }

                                if (var6 == 2) {
                                    opcode = var10 + 11;
                                }

                                if (var6 == 3) {
                                    opcode = var10 + 12;
                                }

                                if (var6 == 4) {
                                    opcode = var10 + 13;
                                }

                                insertRow(var12[var6], client.getColorTags(16776960) + menuDefinition, opcode, index, sceneX, sceneY);
                            }
                        }
                    }

                    if (var12 != null) {
                        for (var6 = 4; var6 >= 0; --var6) {
                            if (var12[var6] != null && var12[var6].equalsIgnoreCase("Attack")) {
                                short var11 = 0;
                                if (client.npcAttackOptionPriority != AttackOptionPriority.HIDDEN) {
                                    if (AttackOptionPriority.RIGHT == client.npcAttackOptionPriority || AttackOptionPriority.DEPENDS == client.npcAttackOptionPriority && def.combatLevel > PlayerEntity.local.combatLevel) {
                                        var11 = 2000;
                                    }

                                    opcode = 0;
                                    if (var6 == 0) {
                                        opcode = var11 + 9;
                                    }

                                    if (var6 == 1) {
                                        opcode = var11 + 10;
                                    }

                                    if (var6 == 2) {
                                        opcode = var11 + 11;
                                    }

                                    if (var6 == 3) {
                                        opcode = var11 + 12;
                                    }

                                    if (var6 == 4) {
                                        opcode = var11 + 13;
                                    }

                                    insertRow(var12[var6], client.getColorTags(16776960) + menuDefinition, opcode, index, sceneX, sceneY);
                                }
                            }
                        }
                    }

                    if (!def.follower || !client.aBoolean1042) {
                        insertRow("Examine", client.getColorTags(16776960) + menuDefinition, 1003, index, sceneX, sceneY);
                    }
                }

            }
        }
    }

    public static void build(int var0, int var1, int var2, int var3) {
        if (ItemSelection.state == 0 && !ComponentSelection.state) {
            insertRow("Walk here", "", 23, 0, var0 - var2, var1 - var3);
        }

        long playeruid = -1L;
        long last = -1L;
        int ptr = 0;

        while (true) {
            if (ptr >= CursorEntities.count) {
                if (playeruid != -1L) {
                    int sceneX = EntityUID.getObjectSceneX(playeruid);
                    int sceneY = EntityUID.getObjectSceneY(playeruid);
                    PlayerEntity var12 = client.players[client.varpControlledInt2];
                    applyPlayerActions(var12, client.varpControlledInt2, sceneX, sceneY);
                }

                return;
            }

            long uid = CursorEntities.uids[ptr];
            if (uid != last) {
                label336:
                {
                    last = uid;
                    int x = EntityUID.getObjectSceneX(CursorEntities.uids[ptr]);
                    int y = EntityUID.getHoveredObjectSceneY(ptr);
                    int type = EntityUID.getHoveredObjectType(ptr);
                    int id = EntityUID.getObjectId(CursorEntities.uids[ptr]);
                    if (type == 2 && client.sceneGraph.getConfigAt(SceneGraph.floorLevel, x, y, uid) >= 0) {
                        ObjectDefinition def = ObjectDefinition.get(id);
                        if (def.transformIds != null) {
                            def = def.transform();
                        }

                        if (def == null) {
                            break label336;
                        }

                        if (ItemSelection.state == 1) {
                            insertRow("Use", ItemSelection.name + " " + "->" + " " + client.getColorTags(65535) + def.name, 1, id, x, y);
                        } else if (ComponentSelection.state) {
                            if ((ComponentSelection.targetFlags & 4) == 4) {
                                insertRow(ComponentSelection.action, ComponentSelection.name + " " + "->" + " " + client.getColorTags(65535) + def.name, 2, id, x, y);
                            }
                        } else {
                            String[] actions = def.actions;
                            if (actions != null) {
                                for (int i = 4; i >= 0; --i) {
                                    if (actions[i] != null) {
                                        short opcode = 0;
                                        if (i == 0) {
                                            opcode = 3;
                                        }

                                        if (i == 1) {
                                            opcode = 4;
                                        }

                                        if (i == 2) {
                                            opcode = 5;
                                        }

                                        if (i == 3) {
                                            opcode = 6;
                                        }

                                        if (i == 4) {
                                            opcode = 1001;
                                        }

                                        insertRow(actions[i], client.getColorTags(65535) + def.name, opcode, id, x, y);
                                    }
                                }
                            }

                            insertRow("Examine", client.getColorTags(65535) + def.name, 1002, def.id, x, y);
                        }
                    }

                    if (type == 1) {
                        NpcEntity npc = client.npcs[id];
                        if (npc == null) {
                            break label336;
                        }

                        if (npc.definition.size == 1 && (npc.absoluteX & 127) == 64 && (npc.absoluteY & 127) == 64) {
                            for (int i = 0; i < client.npcCount; ++i) {
                                NpcEntity npc2 = client.npcs[client.npcIndices[i]];
                                if (npc2 != null && npc2 != npc && npc2.definition.size == 1 && npc2.absoluteX == npc.absoluteX && npc2.absoluteY == npc.absoluteY) {
                                    applyNpcActions(npc.definition, client.npcIndices[i], x, y);
                                }
                            }

                            int count = GPI.playerCount;
                            int[] indices = GPI.playerIndices;

                            for (int i = 0; i < count; ++i) {
                                PlayerEntity player = client.players[indices[i]];
                                if (player != null && npc.absoluteX == player.absoluteX && npc.absoluteY == player.absoluteY) {
                                    applyPlayerActions(player, indices[i], x, y);
                                }
                            }
                        }

                        applyNpcActions(npc.definition, id, x, y);
                    }

                    if (type == 0) {
                        PlayerEntity player = client.players[id];
                        if (player == null) {
                            break label336;
                        }

                        if ((player.absoluteX & 127) == 64 && (player.absoluteY & 127) == 64) {
                            for (int i = 0; i < client.npcCount; ++i) {
                                NpcEntity npc = client.npcs[client.npcIndices[i]];
                                if (npc != null && npc.definition.size == 1 && npc.absoluteX == player.absoluteX && player.absoluteY == npc.absoluteY) {
                                    applyNpcActions(npc.definition, client.npcIndices[i], x, y);
                                }
                            }

                            int count = GPI.playerCount;
                            int[] indices = GPI.playerIndices;

                            for (int i = 0; i < count; ++i) {
                                PlayerEntity player2 = client.players[indices[i]];
                                if (player2 != null && player2 != player && player2.absoluteX == player.absoluteX && player2.absoluteY == player.absoluteY) {
                                    applyPlayerActions(player, indices[i], x, y);
                                }
                            }
                        }

                        if (id != client.varpControlledInt2) {
                            applyPlayerActions(player, id, x, y);
                        } else {
                            playeruid = uid;
                        }
                    }

                    if (type == 3) {
                        NodeDeque<Pickable> deque = client.pickables[SceneGraph.floorLevel][x][y];
                        if (deque != null) {
                            for (Pickable pickable = deque.tail(); pickable != null; pickable = deque.previous()) {
                                ItemDefinition def = ItemDefinition.get(pickable.id);
                                if (ItemSelection.state == 1) {
                                    insertRow("Use", ItemSelection.name + " " + "->" + " " + client.getColorTags(16748608) + def.name, 16, pickable.id, x, y);
                                } else if (ComponentSelection.state) {
                                    if ((ComponentSelection.targetFlags & 1) == 1) {
                                        insertRow(ComponentSelection.action, ComponentSelection.name + " " + "->" + " " + client.getColorTags(16748608) + def.name, 17, pickable.id, x, y);
                                    }
                                } else {
                                    String[] actions = def.groundActions;
                                    for (int i = 4; i >= 0; --i) {
                                        if (actions != null && actions[i] != null) {
                                            byte opcode = 0;
                                            if (i == 0) {
                                                opcode = 18;
                                            }

                                            if (i == 1) {
                                                opcode = 19;
                                            }

                                            if (i == 2) {
                                                opcode = 20;
                                            }

                                            if (i == 3) {
                                                opcode = 21;
                                            }

                                            if (i == 4) {
                                                opcode = 22;
                                            }

                                            insertRow(actions[i], client.getColorTags(16748608) + def.name, opcode, pickable.id, x, y);
                                        } else if (i == 2) {
                                            insertRow("Take", client.getColorTags(0xff9040) + def.name, 20, pickable.id, x, y);
                                        }
                                    }

                                    insertRow("Examine", client.getColorTags(16748608) + def.name, 1004, pickable.id, x, y);
                                }
                            }
                        }
                    }
                }
            }
            ++ptr;
        }
    }

    public static void insertCancelItem() {
        ContextMenu.close();
        ContextMenu.actions[0] = "Cancel";
        ContextMenu.targets[0] = "";
        ContextMenu.opcodes[0] = 1006;
        ContextMenu.prioritizedActions[0] = false;
        ContextMenu.rowCount = 1;
    }
}
