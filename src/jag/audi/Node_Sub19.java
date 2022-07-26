package jag.audi;

import jag.ClientLocale;
import jag.SerializableLong;
import jag.audi.vorbis.RawAudioOverride;
import jag.commons.collection.Node;
import jag.commons.input.Keyboard;
import jag.commons.input.Mouse;
import jag.game.menu.ComponentSelection;
import jag.game.menu.ContextMenu;
import jag.game.InterfaceComponent;
import jag.game.SubInterface;
import jag.game.client;
import jag.game.menu.ContextMenuBuilder;
import jag.game.stockmarket.StockMarketOfferQuantityComparator;
import jag.game.type.AnimationFrameGroup;
import jag.opcode.AsyncOutputStream;
import jag.opcode.OldConnection;
import jag.script.ScriptEvent;
import jag.statics.*;
import jag.worldmap.WorldMapObjectIcon;
import jag.worldmap.WorldMapTileDecor;

public class Node_Sub19 extends Node {
    public static int anInt1191;
    public int anInt564;
    public AudioOverrideEffect aAudioOverrideEffect_1193;
    public AudioOverride aAudioOverride_1192;
    public RawAudioOverride aClass5_Sub10_Sub1_1194;
    public PcmStream_Sub2 aClass5_Sub6_Sub2_1195;
    public int anInt368;
    public int anInt380;
    public int anInt379;
    public int anInt696;
    public int anInt563;
    public int anInt372;
    public int anInt574;
    public int anInt375;
    public int anInt1149;
    public int anInt112;
    public int anInt367;
    public int anInt386;
    public int anInt788;
    public int anInt366;
    public int anInt666;
    public int anInt702;

    public Node_Sub19() {
    }

    public static void processComponentEvents(InterfaceComponent[] group, int var1, int var2, int var3, int var4, int var5, int rootX, int rootY) {
        for (InterfaceComponent c : group) {
            if (c != null && c.parentUid == var1 && (!c.format || c.type == 0 || c.decodedObjects || InterfaceComponent.getConfig(c) != 0 || c == client.topLevelOfDraggedComponent || c.contentType == 1338)) {
                if (c.format) {
                    if (InterfaceComponent.isExplicitlyHidden(c)) {
                        continue;
                    }
                } else if (c.type == 0 && c != OldConnection.hoveredComponent && InterfaceComponent.isExplicitlyHidden(c)) {
                    continue;
                }

                int var11 = c.relativeX + rootX;
                int var12 = c.relativeY + rootY;
                int var13;
                int var14;
                int var15;
                int var16;
                int var18;
                int var20;
                if (c.type == 2) {
                    var13 = var2;
                    var14 = var3;
                    var15 = var4;
                    var16 = var5;
                } else {
                    int var17;
                    if (c.type == 9) {
                        var17 = var11;
                        var18 = var12;
                        var20 = var11 + c.width;
                        int var21 = var12 + c.height;
                        if (var20 < var11) {
                            var17 = var20;
                            var20 = var11;
                        }

                        if (var21 < var12) {
                            var18 = var21;
                            var21 = var12;
                        }

                        ++var20;
                        ++var21;
                        var13 = Math.max(var17, var2);
                        var14 = Math.max(var18, var3);
                        var15 = Math.min(var20, var4);
                        var16 = Math.min(var21, var5);
                    } else {
                        var17 = var11 + c.width;
                        var18 = var12 + c.height;
                        var13 = Math.max(var11, var2);
                        var14 = Math.max(var12, var3);
                        var15 = Math.min(var17, var4);
                        var16 = Math.min(var18, var5);
                    }
                }

                if (c == client.draggedComponent) {
                    client.processingComponentDrag = true;
                    client.anInt1068 = var11;
                    client.anInt1073 = var12;
                }

                boolean var33 = false;
                if (c.aBoolean1372) {
                    switch (client.anInt1047) {
                        case 0:
                            var33 = true;
                        case 1:
                        default:
                            break;
                        case 2:
                            if (client.anInt1059 == c.uid >>> 16) {
                                var33 = true;
                            }
                            break;
                        case 3:
                            if (c.uid == client.anInt1059) {
                                var33 = true;
                            }
                    }
                }

                if (var33 || !c.format || var13 < var15 && var14 < var16) {
                    if (c.format) {
                        ScriptEvent var19;
                        if (c.noClickThrough) {
                            if (Mouse.x >= var13 && Mouse.y >= var14 && Mouse.x < var15 && Mouse.y < var16) {
                                for (var19 = client.inputOccuringEventScripts.head(); var19 != null; var19 = client.inputOccuringEventScripts.next()) {
                                    if (var19.mouseInputDerived) {
                                        var19.unlink();
                                        var19.component.hovered = false;
                                    }
                                }

                                if (CursorEntities.anInt654 == 0) {
                                    client.draggedComponent = null;
                                    client.topLevelOfDraggedComponent = null;
                                }

                                if (!ContextMenu.open) {
                                    ContextMenuBuilder.insertCancelItem();
                                }
                            }
                        } else if (c.noScrollThrough && Mouse.x >= var13 && Mouse.y >= var14 && Mouse.x < var15 && Mouse.y < var16) {
                            for (var19 = client.inputOccuringEventScripts.head(); var19 != null; var19 = client.inputOccuringEventScripts.next()) {
                                if (var19.mouseInputDerived && var19.component.scrollListeners == var19.args) {
                                    var19.unlink();
                                }
                            }
                        }
                    }

                    var18 = Mouse.x;
                    var20 = Mouse.y;
                    if (Mouse.clickMeta != 0) {
                        var18 = Mouse.clickX;
                        var20 = Mouse.clickY;
                    }

                    boolean var34 = var18 >= var13 && var20 >= var14 && var18 < var15 && var20 < var16;
                    if (c.contentType == 1337) {
                        if (!client.loadingPleaseWait && !ContextMenu.open && var34) {
                            ContextMenuBuilder.build(var18, var20, var13, var14);
                        }
                    } else if (c.contentType == 1338) {
                        AsyncOutputStream.processMinimapClick(c, var11, var12);
                    } else {
                        if (c.contentType == 1400) {
                            client.worldMap.poll(Mouse.x, Mouse.y, var34, var11, var12, c.width, c.height);
                        }

                        if (!ContextMenu.open && var34) {
                            if (c.contentType == 1400) {
                                client.worldMap.buildMenuActions(var11, var12, c.width, c.height, var18, var20);
                            } else {
                                ContextMenuBuilder.applyComponentActions(c, var18 - var11, var20 - var12);
                            }
                        }

                        boolean var23;
                        int var25;
                        if (var33) {
                            for (int var22 = 0; var22 < c.aByteArrayArray1365.length; ++var22) {
                                var23 = false;
                                boolean var24 = false;
                                if (!var23 && c.aByteArrayArray1365[var22] != null) {
                                    for (var25 = 0; var25 < c.aByteArrayArray1365[var22].length; ++var25) {
                                        boolean var26 = false;
                                        if (c.keyCycles != null) {
                                            var26 = Keyboard.heldKeys[c.aByteArrayArray1365[var22][var25]];
                                        }

                                        if (AudioOverrideEffect.method794(c.aByteArrayArray1365[var22][var25]) || var26) {
                                            var23 = true;
                                            if (c.keyCycles != null && c.keyCycles[var22] > client.engineCycle) {
                                                break;
                                            }

                                            byte var27 = c.aByteArrayArray1363[var22][var25];
                                            if (var27 == 0 || ((var27 & 8) == 0 || !Keyboard.heldKeys[86] && !Keyboard.heldKeys[82] && !Keyboard.heldKeys[81]) && ((var27 & 2) == 0 || Keyboard.heldKeys[86]) && ((var27 & 1) == 0 || Keyboard.heldKeys[82]) && ((var27 & 4) == 0 || Keyboard.heldKeys[81])) {
                                                var24 = true;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (var24) {
                                    if (var22 < 10) {
                                        InterfaceComponent.processAction(var22 + 1, c.uid, c.subComponentIndex, c.itemId, "");
                                    } else if (var22 == 10) {
                                        ComponentSelection.process();
                                        ComponentSelection.select(c.uid, c.subComponentIndex, SerializableLong.getComponentSpellTargets(InterfaceComponent.getConfig(c)), c.itemId);
                                        ComponentSelection.action = InterfaceComponent.getSelectedAction(c);
                                        if (ComponentSelection.action == null) {
                                            ComponentSelection.action = "null";
                                        }

                                        ComponentSelection.name = c.name + client.getColorTags(16777215);
                                    }

                                    var25 = c.keyRate1[var22];
                                    if (c.keyCycles == null) {
                                        c.keyCycles = new int[c.aByteArrayArray1365.length];
                                    }

                                    if (c.keyRate2 == null) {
                                        c.keyRate2 = new int[c.aByteArrayArray1365.length];
                                    }

                                    if (var25 != 0) {
                                        if (c.keyCycles[var22] == 0) {
                                            c.keyCycles[var22] = var25 + client.engineCycle + c.keyRate2[var22];
                                        } else {
                                            c.keyCycles[var22] = var25 + client.engineCycle;
                                        }
                                    } else {
                                        c.keyCycles[var22] = Integer.MAX_VALUE;
                                    }
                                }

                                if (!var23 && c.keyCycles != null) {
                                    c.keyCycles[var22] = 0;
                                }
                            }
                        }

                        if (c.format) {
                            var34 = Mouse.x >= var13 && Mouse.y >= var14 && Mouse.x < var15 && Mouse.y < var16;

                            boolean var35 = false;
                            if ((Mouse.pressMeta == 1 || !WorldMapObjectIcon.mouseCameraEnabled && Mouse.pressMeta == 4) && var34) {
                                var35 = true;
                            }

                            var23 = (Mouse.clickMeta == 1 || !WorldMapObjectIcon.mouseCameraEnabled && Mouse.clickMeta == 4) && Mouse.clickX >= var13 && Mouse.clickY >= var14 && Mouse.clickX < var15 && Mouse.clickY < var16;

                            if (var23) {
                                InterfaceComponent.drag(c, Mouse.clickX - var11, Mouse.clickY - var12);
                            }

                            if (c.contentType == 1400) {
                                client.worldMap.method1278(var18, var20, var34 & var35, var34 & var23);
                            }

                            if (client.draggedComponent != null && c != client.draggedComponent && var34 && StockMarketOfferQuantityComparator.method477(InterfaceComponent.getConfig(c))) {
                                client.draggedSpecialComponent = c;
                            }

                            if (c == client.topLevelOfDraggedComponent) {
                                client.processingComponentDragTopLevel = true;
                                client.anInt1060 = var11;
                                client.anInt1069 = var12;
                            }

                            if (c.decodedObjects) {
                                ScriptEvent var28;
                                if (var34 && client.mouseWheelPtr != 0 && c.scrollListeners != null) {
                                    var28 = new ScriptEvent();
                                    var28.mouseInputDerived = true;
                                    var28.component = c;
                                    var28.mouseY = client.mouseWheelPtr;
                                    var28.args = c.scrollListeners;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (client.draggedComponent != null || AnimationFrameGroup.dragComponent != null || ContextMenu.open) {
                                    var23 = false;
                                    var35 = false;
                                    var34 = false;
                                }

                                if (!c.clicked && var23) {
                                    c.clicked = true;
                                    if (c.pressListeners != null) {
                                        var28 = new ScriptEvent();
                                        var28.mouseInputDerived = true;
                                        var28.component = c;
                                        var28.mouseX = Mouse.clickX - var11;
                                        var28.mouseY = Mouse.clickY - var12;
                                        var28.args = c.pressListeners;
                                        client.inputOccuringEventScripts.add(var28);
                                    }
                                }

                                if (c.clicked && var35 && c.clickListeners != null) {
                                    var28 = new ScriptEvent();
                                    var28.mouseInputDerived = true;
                                    var28.component = c;
                                    var28.mouseX = Mouse.x - var11;
                                    var28.mouseY = Mouse.y - var12;
                                    var28.args = c.clickListeners;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (c.clicked && !var35) {
                                    c.clicked = false;
                                    if (c.releaseListeners != null) {
                                        var28 = new ScriptEvent();
                                        var28.mouseInputDerived = true;
                                        var28.component = c;
                                        var28.mouseX = Mouse.x - var11;
                                        var28.mouseY = Mouse.y - var12;
                                        var28.args = c.releaseListeners;
                                        client.inputFinishedEventScripts.add(var28);
                                    }
                                }

                                if (var35 && c.holdListeners != null) {
                                    var28 = new ScriptEvent();
                                    var28.mouseInputDerived = true;
                                    var28.component = c;
                                    var28.mouseX = Mouse.x - var11;
                                    var28.mouseY = Mouse.y - var12;
                                    var28.args = c.holdListeners;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (!c.hovered && var34) {
                                    c.hovered = true;
                                    if (c.mouseEnterListeners != null) {
                                        var28 = new ScriptEvent();
                                        var28.mouseInputDerived = true;
                                        var28.component = c;
                                        var28.mouseX = Mouse.x - var11;
                                        var28.mouseY = Mouse.y - var12;
                                        var28.args = c.mouseEnterListeners;
                                        client.inputOccuringEventScripts.add(var28);
                                    }
                                }

                                if (c.hovered && var34 && c.hoverListeners != null) {
                                    var28 = new ScriptEvent();
                                    var28.mouseInputDerived = true;
                                    var28.component = c;
                                    var28.mouseX = Mouse.x - var11;
                                    var28.mouseY = Mouse.y - var12;
                                    var28.args = c.hoverListeners;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (c.hovered && !var34) {
                                    c.hovered = false;
                                    if (c.mouseExitListeners != null) {
                                        var28 = new ScriptEvent();
                                        var28.mouseInputDerived = true;
                                        var28.component = c;
                                        var28.mouseX = Mouse.x - var11;
                                        var28.mouseY = Mouse.y - var12;
                                        var28.args = c.mouseExitListeners;
                                        client.inputFinishedEventScripts.add(var28);
                                    }
                                }

                                if (c.renderListeners != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.renderListeners;
                                    client.renderEventScripts.add(var28);
                                }

                                ScriptEvent var29;
                                int var36;
                                int var37;
                                if (c.varTransmit != null && client.anInt1064 > c.anInt1413) {
                                    if (c.varTriggers != null && client.anInt1064 - c.anInt1413 <= 32) {
                                        label876:
                                        for (var36 = c.anInt1413; var36 < client.anInt1064; ++var36) {
                                            var25 = client.anIntArray1076[var36 & 31];

                                            for (var37 = 0; var37 < c.varTriggers.length; ++var37) {
                                                if (var25 == c.varTriggers[var37]) {
                                                    var29 = new ScriptEvent();
                                                    var29.component = c;
                                                    var29.args = c.varTransmit;
                                                    client.inputOccuringEventScripts.add(var29);
                                                    break label876;
                                                }
                                            }
                                        }
                                    } else {
                                        var28 = new ScriptEvent();
                                        var28.component = c;
                                        var28.args = c.varTransmit;
                                        client.inputOccuringEventScripts.add(var28);
                                    }

                                    c.anInt1413 = client.anInt1064;
                                }

                                if (c.itemTransmit != null && client.anInt1078 > c.anInt1408) {
                                    if (c.itemTriggers != null && client.anInt1078 - c.anInt1408 <= 32) {
                                        label852:
                                        for (var36 = c.anInt1408; var36 < client.anInt1078; ++var36) {
                                            var25 = client.inventories[var36 & 31];

                                            for (var37 = 0; var37 < c.itemTriggers.length; ++var37) {
                                                if (var25 == c.itemTriggers[var37]) {
                                                    var29 = new ScriptEvent();
                                                    var29.component = c;
                                                    var29.args = c.itemTransmit;
                                                    client.inputOccuringEventScripts.add(var29);
                                                    break label852;
                                                }
                                            }
                                        }
                                    } else {
                                        var28 = new ScriptEvent();
                                        var28.component = c;
                                        var28.args = c.itemTransmit;
                                        client.inputOccuringEventScripts.add(var28);
                                    }

                                    c.anInt1408 = client.anInt1078;
                                }

                                if (c.skillTransmit != null && client.anInt1063 > c.anInt1411) {
                                    if (c.skillTriggers != null && client.anInt1063 - c.anInt1411 <= 32) {
                                        label828:
                                        for (var36 = c.anInt1411; var36 < client.anInt1063; ++var36) {
                                            var25 = client.anIntArray1079[var36 & 31];

                                            for (var37 = 0; var37 < c.skillTriggers.length; ++var37) {
                                                if (var25 == c.skillTriggers[var37]) {
                                                    var29 = new ScriptEvent();
                                                    var29.component = c;
                                                    var29.args = c.skillTransmit;
                                                    client.inputOccuringEventScripts.add(var29);
                                                    break label828;
                                                }
                                            }
                                        }
                                    } else {
                                        var28 = new ScriptEvent();
                                        var28.component = c;
                                        var28.args = c.skillTransmit;
                                        client.inputOccuringEventScripts.add(var28);
                                    }

                                    c.anInt1411 = client.anInt1063;
                                }

                                if (client.anInt1066 > c.anInt1406 && c.anObjectArray1403 != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.anObjectArray1403;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (client.anInt1065 > c.anInt1406 && c.anObjectArray1399 != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.anObjectArray1399;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (client.anInt1061 > c.anInt1406 && c.anObjectArray1396 != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.anObjectArray1396;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (client.anInt1071 > c.anInt1406 && c.anObjectArray1391 != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.anObjectArray1391;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (client.stockMarketUpdateCycle > c.anInt1406 && c.anObjectArray1394 != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.anObjectArray1394;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                if (client.anInt1074 > c.anInt1406 && c.anObjectArray1390 != null) {
                                    var28 = new ScriptEvent();
                                    var28.component = c;
                                    var28.args = c.anObjectArray1390;
                                    client.inputOccuringEventScripts.add(var28);
                                }

                                c.anInt1406 = client.anInt1075;
                                if (c.anObjectArray1398 != null) {
                                    for (var36 = 0; var36 < client.anInt1092; ++var36) {
                                        ScriptEvent var30 = new ScriptEvent();
                                        var30.component = c;
                                        var30.anInt372 = client.anIntArray1096[var36];
                                        var30.anInt379 = client.anIntArray1097[var36];
                                        var30.args = c.anObjectArray1398;
                                        client.inputOccuringEventScripts.add(var30);
                                    }
                                }
                            }
                        }

                        if (!c.format) {
                            if (client.draggedComponent != null || AnimationFrameGroup.dragComponent != null || ContextMenu.open) {
                                continue;
                            }

                            if ((c.detour >= 0 || c.hoverForeground != 0) && Mouse.x >= var13 && Mouse.y >= var14 && Mouse.x < var15 && Mouse.y < var16) {
                                if (c.detour >= 0) {
                                    OldConnection.hoveredComponent = group[c.detour];
                                } else {
                                    OldConnection.hoveredComponent = c;
                                }
                            }

                            if (c.type == 8 && Mouse.x >= var13 && Mouse.y >= var14 && Mouse.x < var15 && Mouse.y < var16) {
                                Statics24.anInterfaceComponent1417 = c;
                            }

                            if (c.viewportHeight > c.height) {
                                InterfaceComponent.method177(c, var11 + c.width, var12, c.height, c.viewportHeight, Mouse.x, Mouse.y);
                            }
                        }

                        if (c.type == 0) {
                            processComponentEvents(group, c.uid, var13, var14, var15, var16, var11 - c.insetX, var12 - c.insetY);
                            if (c.subcomponents != null) {
                                processComponentEvents(c.subcomponents, c.uid, var13, var14, var15, var16, var11 - c.insetX, var12 - c.insetY);
                            }

                            SubInterface var31 = client.subInterfaces.lookup(c.uid);
                            if (var31 != null) {
                                if (var31.type == 0 && Mouse.x >= var13 && Mouse.y >= var14 && Mouse.x < var15 && Mouse.y < var16 && !ContextMenu.open) {
                                    for (ScriptEvent var32 = client.inputOccuringEventScripts.head(); var32 != null; var32 = client.inputOccuringEventScripts.next()) {
                                        if (var32.mouseInputDerived) {
                                            var32.unlink();
                                            var32.component.hovered = false;
                                        }
                                    }

                                    if (CursorEntities.anInt654 == 0) {
                                        client.draggedComponent = null;
                                        client.topLevelOfDraggedComponent = null;
                                    }

                                    if (!ContextMenu.open) {
                                        ContextMenuBuilder.insertCancelItem();
                                    }
                                }

                                WorldMapTileDecor.processComponentRendering(var31.id, var13, var14, var15, var16, var11, var12);
                            }
                        }
                    }
                }
            }
        }

    }

    public static int method858(char var0, ClientLocale var1) {
        int var2 = var0 << 4;
        if (Character.isUpperCase(var0) || Character.isTitleCase(var0)) {
            var0 = Character.toLowerCase(var0);
            var2 = (var0 << 4) + 1;
        }

        if (var0 == 241 && var1 == ClientLocale.ES) {
            var2 = 1762;
        }

        return var2;
    }

    public void method499() {
        this.aAudioOverride_1192 = null;
        this.aClass5_Sub10_Sub1_1194 = null;
        this.aAudioOverrideEffect_1193 = null;
        this.aClass5_Sub6_Sub2_1195 = null;
    }
}
