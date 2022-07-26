package jag.game.menu;

import jag.SerializableLong;
import jag.commons.input.Keyboard;
import jag.commons.input.Mouse;
import jag.commons.time.Clock;
import jag.game.InterfaceComponent;
import jag.game.Vars;
import jag.game.client;
import jag.game.relationship.ChatHistory;
import jag.game.scene.HintArrow;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.NpcEntity;
import jag.game.scene.entity.PlayerEntity;
import jag.game.stockmarket.StockMarketOfferWorldComparator;
import jag.game.type.ItemDefinition;
import jag.game.type.NpcDefinition;
import jag.graphics.Font;
import jag.opcode.OldConnection;
import jag.opcode.OutgoingPacket;
import jag.opcode.ClientProt;
import jag.statics.Statics48;
import jag.worldmap.WorldMapPosition;

public class ContextMenu {

    public static final String[] actions = new String[500];
    public static final String[] targets = new String[500];

    public static final int[] opcodes = new int[500];

    public static final int[] primaryArgs = new int[500];
    public static final int[] secondaryArgs = new int[500];
    public static final int[] tertiaryArgs = new int[500];

    public static final boolean[] prioritizedActions = new boolean[500];

    public static boolean open = false;

    public static int rowCount = 0;

    public static int x;
    public static int y;

    public static int width;
    public static int height;

    public static int getLastRowIndex() {
        return rowCount - 1;
    }

    public static boolean method1169(int var0) {
        return var0 == 57 || var0 == 58 || var0 == 1007 || var0 == 25 || var0 == 30;
    }

    public static void method317() {
        for (int i = 0; i < rowCount; ++i) {
            if (method1169(opcodes[i])) {
                if (i < rowCount - 1) {
                    for (int j = i; j < rowCount - 1; ++j) {
                        actions[j] = actions[j + 1];
                        targets[j] = targets[j + 1];
                        opcodes[j] = opcodes[j + 1];
                        primaryArgs[j] = primaryArgs[j + 1];
                        secondaryArgs[j] = secondaryArgs[j + 1];
                        tertiaryArgs[j] = tertiaryArgs[j + 1];
                        prioritizedActions[j] = prioritizedActions[j + 1];
                    }
                }

                --i;
                --rowCount;
            }
        }

        open(width / 2 + x, y);
    }

    public static String addImgTags(int var0) {
        return "<img=" + var0 + ">";
    }

    public static class Crosshair {

        public static int x = 0;
        public static int y = 0;
        public static int state = 0;

        public static boolean display = true;

    }

    public static void open(int x, int y) {
        int menuWidth = Font.b12full.textWidth("Choose Option");

        int menuX;
        for (int i = 0; i < rowCount; ++i) {
            menuX = Font.b12full.textWidth(getRowText(i));
            if (menuX > menuWidth) {
                menuWidth = menuX;
            }
        }

        menuWidth += 8;
        int menuHeight = rowCount * 15 + 22;
        menuX = x - menuWidth / 2;
        if (menuX + menuWidth > client.canvasWidth) {
            menuX = client.canvasWidth - menuWidth;
        }

        if (menuX < 0) {
            menuX = 0;
        }

        int menuY = y;
        if (y + menuHeight > client.canvasHeight) {
            menuY = client.canvasHeight - menuHeight;
        }

        if (menuY < 0) {
            menuY = 0;
        }

        ContextMenu.x = menuX;
        ContextMenu.y = menuY;
        ContextMenu.width = menuWidth;
        ContextMenu.height = menuHeight;
    }

    public static String getRowText(int row) {
        return row < 0 ? "" : targets[row].length() > 0 ? actions[row] + " " + targets[row] : actions[row];
    }

    public static void close() {
        rowCount = 0;
        open = false;
    }

    public static void processActionAt(int index) {
        if (index >= 0) {
            int secondaryArg = secondaryArgs[index];
            int tertiaryArg = tertiaryArgs[index];
            int opcode = opcodes[index];
            int primaryArg = primaryArgs[index];
            String target = targets[index];
            doAction(secondaryArg, tertiaryArg, opcode, primaryArg, target, Mouse.clickX, Mouse.clickY);
        }
    }

    //Object Actions

    private static void doItemOnObject(int id, int sceneX, int sceneY) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_ITEM_ON_OBJECT, client.netWriter.encryptor);
        packet.buffer.ip2(client.baseX + sceneX);
        packet.buffer.p2(client.baseY + sceneY);
        packet.buffer.p2a(ItemSelection.index);
        packet.buffer.pirf4(ItemSelection.uid);
        packet.buffer.p2(ItemSelection.id);
        packet.buffer.ip2a(id);
        packet.buffer.p1n(Keyboard.heldKeys[82] ? 1 : 0);
        client.netWriter.writeLater(packet);
    }

    private static void doSpellOnObject(int id, int sceneX, int sceneY) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_SPELL_ON_OBJECT, client.netWriter.encryptor);
        packet.buffer.p4(ComponentSelection.uid);
        packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.p2(client.baseY + sceneY);
        packet.buffer.ip2a(id);
        packet.buffer.p2(client.baseX + sceneX);
        packet.buffer.p2a(ComponentSelection.index);
        client.netWriter.writeLater(packet);
    }

    private static void doObjectAction0(int id, int sceneX, int sceneY) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.OBJECT_ACTION_0, client.netWriter.encryptor);
        packet.buffer.p1n(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.p2(id);
        packet.buffer.p2a(client.baseY + sceneY);
        packet.buffer.ip2a(client.baseX + sceneX);
        client.netWriter.writeLater(packet);
    }

    private static void doObjectAction1(int id, int sceneX, int sceneY) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.OBJECT_ACTION_1, client.netWriter.encryptor);
        packet.buffer.ip2a(id);
        packet.buffer.p2(client.baseX + sceneX);
        packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.p2a(client.baseY + sceneY);
        client.netWriter.writeLater(packet);
    }

    private static void doObjectAction2(int id, int sceneX, int sceneY) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.OBJECT_ACTION_2, client.netWriter.encryptor);
        packet.buffer.p2(id);
        packet.buffer.p2a(client.baseX + sceneX);
        packet.buffer.ip2a(client.baseY + sceneY);
        packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
        client.netWriter.writeLater(packet);
    }

    private static void doObjectAction3(int id, int sceneX, int sceneY) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.OBJECT_ACTION_3, client.netWriter.encryptor);
        packet.buffer.p2(client.baseX + sceneX);
        packet.buffer.p1n(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.p2a(id);
        packet.buffer.p2a(client.baseY + sceneY);
        client.netWriter.writeLater(packet);
    }

    //Npc Actions

    private static void processItemOnNpc(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_ITEM_ON_NPC, client.netWriter.encryptor);
        packet.buffer.ip4(ItemSelection.uid);
        packet.buffer.ip2a(ItemSelection.index);
        packet.buffer.p2a(idx);
        packet.buffer.ip2a(ItemSelection.id);
        packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
        client.netWriter.writeLater(packet);
    }

    private static void processSpellOnNpc(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_SPELL_ON_NPC, client.netWriter.encryptor);
        packet.buffer.ip2(ComponentSelection.index);
        packet.buffer.ip2(idx);
        packet.buffer.ip4(ComponentSelection.uid);
        packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
        client.netWriter.writeLater(packet);
    }

    private static void doNpcAction0(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.NPC_ACTION_0, client.netWriter.encryptor);
        packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.ip2(idx);
        client.netWriter.writeLater(packet);
    }

    private static void doNpcAction1(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.NPC_ACTION_1, client.netWriter.encryptor);
        packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.p2a(idx);
        client.netWriter.writeLater(packet);
    }

    private static void doNpcAction2(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.NPC_ACTION_2, client.netWriter.encryptor);
        packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.ip2(idx);
        client.netWriter.writeLater(packet);
    }

    private static void doNpcAction3(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.NPC_ACTION_3, client.netWriter.encryptor);
        packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.ip2(idx);
        client.netWriter.writeLater(packet);
    }

    private static void doNpcAction4(int idx) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.NPC_ACTION_4, client.netWriter.encryptor);
        packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
        packet.buffer.ip2(idx);
        client.netWriter.writeLater(packet);
    }

    //Player Actions



    public static void doAction(int secondary, int tertiary, int opcode, int primary, String action, int crossX, int crossY) {
        if (opcode >= 2000) {
            opcode -= 2000;
        }

        if (opcode == 1) {
            Crosshair.x = crossX;
            Crosshair.y = crossY;
            Crosshair.state = 2;
            HintArrow.state = 0;
            client.destinationX = secondary;
            client.destinationY = tertiary;
            doItemOnObject(primary, secondary, tertiary);
        } else if (opcode == 2) {
            Crosshair.x = crossX;
            Crosshair.y = crossY;
            Crosshair.state = 2;
            HintArrow.state = 0;
            client.destinationX = secondary;
            client.destinationY = tertiary;
            doSpellOnObject(primary, secondary, tertiary);
        } else if (opcode == 3) {
            Crosshair.x = crossX;
            Crosshair.y = crossY;
            Crosshair.state = 2;
            HintArrow.state = 0;
            client.destinationX = secondary;
            client.destinationY = tertiary;
            doObjectAction0(primary, secondary, tertiary);
        } else if (opcode == 4) {
            Crosshair.x = crossX;
            Crosshair.y = crossY;
            Crosshair.state = 2;
            HintArrow.state = 0;
            client.destinationX = secondary;
            client.destinationY = tertiary;
            doObjectAction1(primary, secondary, tertiary);
        } else if (opcode == 5) {
            Crosshair.x = crossX;
            Crosshair.y = crossY;
            Crosshair.state = 2;
            HintArrow.state = 0;
            client.destinationX = secondary;
            client.destinationY = tertiary;
            doObjectAction2(primary, secondary, tertiary);
        } else if (opcode == 6) {
            Crosshair.x = crossX;
            Crosshair.y = crossY;
            Crosshair.state = 2;
            HintArrow.state = 0;
            client.destinationX = secondary;
            client.destinationY = tertiary;
            doObjectAction3(primary, secondary, tertiary);
        } else if (opcode == 7) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                processItemOnNpc(primary);
            }
        } else if (opcode == 8) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                processSpellOnNpc(primary);
            }
        } else if (opcode == 9) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                doNpcAction0(primary);
            }
        } else if (opcode == 10) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                doNpcAction1(primary);
            }
        } else if (opcode == 11) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                doNpcAction2(primary);
            }
        } else if (opcode == 12) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                doNpcAction3(primary);
            }
        } else if (opcode == 13) {
            NpcEntity npc = client.npcs[primary];
            if (npc != null) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                doNpcAction4(primary);
            }
        } else {
            if (opcode == 14) {
                PlayerEntity player = client.players[primary];
                if (player != null) {
                    Crosshair.x = crossX;
                    Crosshair.y = crossY;
                    Crosshair.state = 2;
                    HintArrow.state = 0;
                    client.destinationX = secondary;
                    client.destinationY = tertiary;
                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_ITEM_ON_PLAYER, client.netWriter.encryptor);
                    packet.buffer.ip2a(primary);
                    packet.buffer.pirf4(ItemSelection.uid);
                    packet.buffer.p2(ItemSelection.index);
                    packet.buffer.ip2(ItemSelection.id);
                    packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
                    client.netWriter.writeLater(packet);
                }
            } else if (opcode == 15) {
                PlayerEntity player = client.players[primary];
                if (player != null) {
                    Crosshair.x = crossX;
                    Crosshair.y = crossY;
                    Crosshair.state = 2;
                    HintArrow.state = 0;
                    client.destinationX = secondary;
                    client.destinationY = tertiary;
                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_SPELL_ON_PLAYER, client.netWriter.encryptor);
                    packet.buffer.ip2(primary);
                    packet.buffer.p2a(ComponentSelection.index);
                    packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
                    packet.buffer.pif4(ComponentSelection.uid);
                    client.netWriter.writeLater(packet);
                }
            } else if (opcode == 16) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_ITEM_ON_PICKABLE, client.netWriter.encryptor);
                packet.buffer.ip2a(primary);
                packet.buffer.p4(ItemSelection.uid);
                packet.buffer.p2a(ItemSelection.id);
                packet.buffer.ip2a(client.baseY + tertiary);
                packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
                packet.buffer.p2a(client.baseX + secondary);
                packet.buffer.p2a(ItemSelection.index);
                client.netWriter.writeLater(packet);
            } else if (opcode == 17) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_SPELL_ON_PICKABLE, client.netWriter.encryptor);
                packet.buffer.p2a(client.baseY + tertiary);
                packet.buffer.ip4(ComponentSelection.uid);
                packet.buffer.ip2a(primary);
                packet.buffer.p2a(ComponentSelection.index);
                packet.buffer.ip2(client.baseX + secondary);
                packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                client.netWriter.writeLater(packet);
            } else if (opcode == 18) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PICKABLE_ACTION_0, client.netWriter.encryptor);
                packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
                packet.buffer.ip2(client.baseY + tertiary);
                packet.buffer.p2a(client.baseX + secondary);
                packet.buffer.ip2a(primary);
                client.netWriter.writeLater(packet);
            } else if (opcode == 19) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PICKABLE_ACTION_1, client.netWriter.encryptor);
                packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                packet.buffer.p2a(client.baseX + secondary);
                packet.buffer.p2a(primary);
                packet.buffer.p2a(client.baseY + tertiary);
                client.netWriter.writeLater(packet);
            } else if (opcode == 20) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PICKABLE_ACTION_2, client.netWriter.encryptor);
                packet.buffer.p1n(Keyboard.heldKeys[82] ? 1 : 0);
                packet.buffer.ip2a(client.baseX + secondary);
                packet.buffer.p2a(client.baseY + tertiary);
                packet.buffer.ip2(primary);
                client.netWriter.writeLater(packet);
            } else if (opcode == 21) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PICKABLE_ACTION_3, client.netWriter.encryptor);
                packet.buffer.ip2a(client.baseX + secondary);
                packet.buffer.p2a(client.baseY + tertiary);
                packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
                packet.buffer.p2(primary);
                client.netWriter.writeLater(packet);
            } else if (opcode == 22) {
                Crosshair.x = crossX;
                Crosshair.y = crossY;
                Crosshair.state = 2;
                HintArrow.state = 0;
                client.destinationX = secondary;
                client.destinationY = tertiary;
                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PICKABLE_ACTION_4, client.netWriter.encryptor);
                packet.buffer.ip2(client.baseX + secondary);
                packet.buffer.ip2(client.baseY + tertiary);
                packet.buffer.ip2a(primary);
                packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
                client.netWriter.writeLater(packet);
            } else if (opcode == 23) {
                if (open) {
                    client.sceneGraph.method1443();
                } else {
                    client.sceneGraph.method1451(SceneGraph.floorLevel, secondary, tertiary, true);
                }
            } else {
                if (opcode == 24) {
                    InterfaceComponent component = InterfaceComponent.lookup(tertiary);
                    boolean appearance = true;
                    if (component.contentType > 0) {
                        appearance = Statics48.isAppearanceCode(component);
                    }

                    if (appearance) {
                        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.BUTTON_ACTION, client.netWriter.encryptor);
                        packet.buffer.p4(tertiary);
                        client.netWriter.writeLater(packet);
                    }
                } else {
                    if (opcode == 25) {
                        InterfaceComponent component = InterfaceComponent.lookup(tertiary, secondary);
                        if (component != null) {
                            ComponentSelection.process();
                            ComponentSelection.select(tertiary, secondary, SerializableLong.getComponentSpellTargets(InterfaceComponent.getConfig(component)), component.itemId);
                            ItemSelection.state = 0;
                            ComponentSelection.action = InterfaceComponent.getSelectedAction(component);
                            if (ComponentSelection.action == null) {
                                ComponentSelection.action = "null";
                            }

                            if (component.format) {
                                ComponentSelection.name = component.name + client.getColorTags(16777215);
                            } else {
                                ComponentSelection.name = client.getColorTags(65280) + component.spellName + client.getColorTags(16777215);
                            }
                        }

                        return;
                    }

                    if (opcode == 26) {
                        InterfaceComponent.closeInterface();
                    } else {
                        if (opcode == 28) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.BUTTON_ACTION, client.netWriter.encryptor);
                            packet.buffer.p4(tertiary);
                            client.netWriter.writeLater(packet);
                            InterfaceComponent component = InterfaceComponent.lookup(tertiary);
                            if (component.cs1Opcodes != null && component.cs1Opcodes[0][0] == 5) {
                                int index = component.cs1Opcodes[0][1];
                                Vars.values[index] = 1 - Vars.values[index];
                                OldConnection.method712(index);
                            }
                        } else if (opcode == 29) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.BUTTON_ACTION, client.netWriter.encryptor);
                            packet.buffer.p4(tertiary);
                            client.netWriter.writeLater(packet);
                            InterfaceComponent component = InterfaceComponent.lookup(tertiary);
                            if (component.cs1Opcodes != null && component.cs1Opcodes[0][0] == 5) {
                                int index = component.cs1Opcodes[0][1];
                                if (Vars.values[index] != component.cs1Values[0]) {
                                    Vars.values[index] = component.cs1Values[0];
                                    OldConnection.method712(index);
                                }
                            }
                        } else if (opcode == 30) {
                            if (client.pleaseWaitComponent == null) {
                                Clock.processDialogActionPacket(tertiary, secondary);
                                client.pleaseWaitComponent = InterfaceComponent.lookup(tertiary, secondary);
                                InterfaceComponent.invalidate(client.pleaseWaitComponent);
                            }
                        } else if (opcode == 31) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_ITEM_ON_ITEM, client.netWriter.encryptor);
                            packet.buffer.pirf4(tertiary);
                            packet.buffer.ip2a(ItemSelection.id);
                            packet.buffer.p4(ItemSelection.uid);
                            packet.buffer.p2(ItemSelection.index);
                            packet.buffer.p2(secondary);
                            packet.buffer.p2(primary);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else if (opcode == 32) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_SPELL_ON_ITEM, client.netWriter.encryptor);
                            packet.buffer.ip2a(ComponentSelection.index);
                            packet.buffer.ip2(secondary);
                            packet.buffer.ip2(primary);
                            packet.buffer.pirf4(tertiary);
                            packet.buffer.pif4(ComponentSelection.uid);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else if (opcode == 33) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ITEM_ACTION_0, client.netWriter.encryptor);
                            packet.buffer.p4(tertiary);
                            packet.buffer.ip2(secondary);
                            packet.buffer.ip2(primary);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else if (opcode == 34) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ITEM_ACTION_1, client.netWriter.encryptor);
                            packet.buffer.p4(tertiary);
                            packet.buffer.ip2(secondary);
                            packet.buffer.p2a(primary);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else if (opcode == 35) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ITEM_ACTION_2, client.netWriter.encryptor);
                            packet.buffer.ip2(primary);
                            packet.buffer.ip4(tertiary);
                            packet.buffer.p2(secondary);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else if (opcode == 36) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ITEM_ACTION_3, client.netWriter.encryptor);
                            packet.buffer.ip2a(primary);
                            packet.buffer.ip2(secondary);
                            packet.buffer.pirf4(tertiary);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else if (opcode == 37) {
                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ITEM_ACTION_4, client.netWriter.encryptor);
                            packet.buffer.p2a(secondary);
                            packet.buffer.pirf4(tertiary);
                            packet.buffer.p2a(primary);
                            client.netWriter.writeLater(packet);
                            client.anInt1018 = 0;
                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                            client.anInt1015 = secondary;
                        } else {
                            if (opcode == 38) {
                                ComponentSelection.process();
                                InterfaceComponent component = InterfaceComponent.lookup(tertiary);
                                ItemSelection.state = 1;
                                ItemSelection.id = secondary;
                                ItemSelection.uid = tertiary;
                                ItemSelection.index = primary;
                                InterfaceComponent.invalidate(component);
                                ItemSelection.name = client.getColorTags(16748608) + ItemDefinition.get(primary).name + client.getColorTags(16777215);
                                if (ItemSelection.name == null) {
                                    ItemSelection.name = "null";
                                }

                                return;
                            }

                            if (opcode == 39) {
                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.TABLE_ACTION_0, client.netWriter.encryptor);
                                packet.buffer.p2a(secondary);
                                packet.buffer.p2(primary);
                                packet.buffer.pif4(tertiary);
                                client.netWriter.writeLater(packet);
                                client.anInt1018 = 0;
                                StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                                client.anInt1015 = secondary;
                            } else if (opcode == 40) {
                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.TABLE_ACTION_1, client.netWriter.encryptor);
                                packet.buffer.pirf4(tertiary);
                                packet.buffer.p2a(secondary);
                                packet.buffer.ip2a(primary);
                                client.netWriter.writeLater(packet);
                                client.anInt1018 = 0;
                                StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                                client.anInt1015 = secondary;
                            } else if (opcode == 41) {
                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.TABLE_ACTION_2, client.netWriter.encryptor);
                                packet.buffer.ip2(secondary);
                                packet.buffer.pif4(tertiary);
                                packet.buffer.ip2(primary);
                                client.netWriter.writeLater(packet);
                                client.anInt1018 = 0;
                                StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                                client.anInt1015 = secondary;
                            } else if (opcode == 42) {
                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.TABLE_ACTION_3, client.netWriter.encryptor);
                                packet.buffer.p2(primary);
                                packet.buffer.p2(secondary);
                                packet.buffer.pirf4(tertiary);
                                client.netWriter.writeLater(packet);
                                client.anInt1018 = 0;
                                StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                                client.anInt1015 = secondary;
                            } else if (opcode == 43) {
                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.TABLE_ACTION_4, client.netWriter.encryptor);
                                packet.buffer.p2(primary);
                                packet.buffer.pif4(tertiary);
                                packet.buffer.p2(secondary);
                                client.netWriter.writeLater(packet);
                                client.anInt1018 = 0;
                                StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                                client.anInt1015 = secondary;
                            } else if (opcode == 44) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_0, client.netWriter.encryptor);
                                    packet.buffer.p2a(primary);
                                    packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 45) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_1, client.netWriter.encryptor);
                                    packet.buffer.ip2a(primary);
                                    packet.buffer.p1(Keyboard.heldKeys[82] ? 1 : 0);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 46) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_2, client.netWriter.encryptor);
                                    packet.buffer.writeByteS(Keyboard.heldKeys[82] ? 1 : 0);
                                    packet.buffer.ip2(primary);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 47) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_3, client.netWriter.encryptor);
                                    packet.buffer.ip2a(primary);
                                    packet.buffer.p1n(Keyboard.heldKeys[82] ? 1 : 0);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 48) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_4, client.netWriter.encryptor);
                                    packet.buffer.p1n(Keyboard.heldKeys[82] ? 1 : 0);
                                    packet.buffer.ip2(primary);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 49) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_5, client.netWriter.encryptor);
                                    packet.buffer.p2(primary);
                                    packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 50) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_6, client.netWriter.encryptor);
                                    packet.buffer.ip2(primary);
                                    packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                                    client.netWriter.writeLater(packet);
                                }
                            } else if (opcode == 51) {
                                PlayerEntity player = client.players[primary];
                                if (player != null) {
                                    Crosshair.x = crossX;
                                    Crosshair.y = crossY;
                                    Crosshair.state = 2;
                                    HintArrow.state = 0;
                                    client.destinationX = secondary;
                                    client.destinationY = tertiary;
                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_7, client.netWriter.encryptor);
                                    packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                                    packet.buffer.p2a(primary);
                                    client.netWriter.writeLater(packet);
                                }
                            } else {
                                label960:
                                {
                                    if (opcode != 57) {
                                        if (opcode == 58) {
                                            InterfaceComponent component = InterfaceComponent.lookup(tertiary, secondary);
                                            if (component != null) {
                                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.USE_SPELL_ON_COMPONENT, client.netWriter.encryptor);
                                                packet.buffer.pirf4(tertiary);
                                                packet.buffer.p2(component.itemId);
                                                packet.buffer.ip4(ComponentSelection.uid);
                                                packet.buffer.ip2a(secondary);
                                                packet.buffer.p2a(ComponentSelection.index);
                                                packet.buffer.p2a(ComponentSelection.target);
                                                client.netWriter.writeLater(packet);
                                            }
                                            break label960;
                                        }

                                        if (opcode == 1001) {
                                            Crosshair.x = crossX;
                                            Crosshair.y = crossY;
                                            Crosshair.state = 2;
                                            HintArrow.state = 0;
                                            client.destinationX = secondary;
                                            client.destinationY = tertiary;
                                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.OBJECT_ACTION_4, client.netWriter.encryptor);
                                            packet.buffer.p2(client.baseY + tertiary);
                                            packet.buffer.p2(client.baseX + secondary);
                                            packet.buffer.p2(primary);
                                            packet.buffer.p1a(Keyboard.heldKeys[82] ? 1 : 0);
                                            client.netWriter.writeLater(packet);
                                            break label960;
                                        }

                                        if (opcode == 1002) {
                                            Crosshair.x = crossX;
                                            Crosshair.y = crossY;
                                            Crosshair.state = 2;
                                            HintArrow.state = 0;
                                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.EXAMINE_OBJECT, client.netWriter.encryptor);
                                            packet.buffer.ip2(primary);
                                            client.netWriter.writeLater(packet);
                                            break label960;
                                        }

                                        if (opcode == 1003) {
                                            Crosshair.x = crossX;
                                            Crosshair.y = crossY;
                                            Crosshair.state = 2;
                                            HintArrow.state = 0;
                                            NpcEntity npc = client.npcs[primary];
                                            if (npc != null) {
                                                NpcDefinition definition = npc.definition;
                                                if (definition.transformIds != null) {
                                                    definition = definition.transform();
                                                }

                                                if (definition != null) {
                                                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.EXAMINE_NPC, client.netWriter.encryptor);
                                                    packet.buffer.p2a(definition.id);
                                                    client.netWriter.writeLater(packet);
                                                }
                                            }
                                            break label960;
                                        }

                                        if (opcode == 1004) {
                                            Crosshair.x = crossX;
                                            Crosshair.y = crossY;
                                            Crosshair.state = 2;
                                            HintArrow.state = 0;
                                            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.EXAMINE_ITEM, client.netWriter.encryptor);
                                            packet.buffer.p2(primary);
                                            client.netWriter.writeLater(packet);
                                            break label960;
                                        }

                                        if (opcode == 1005) {
                                            InterfaceComponent component = InterfaceComponent.lookup(tertiary);
                                            if (component != null && component.itemStackSizes[secondary] >= 100000) {
                                                ChatHistory.messageReceived(27, "", component.itemStackSizes[secondary] + " x " + ItemDefinition.get(primary).name);
                                            } else {
                                                OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.EXAMINE_ITEM, client.netWriter.encryptor);
                                                packet.buffer.p2(primary);
                                                client.netWriter.writeLater(packet);
                                            }

                                            client.anInt1018 = 0;
                                            StockMarketOfferWorldComparator.anInterfaceComponent351 = InterfaceComponent.lookup(tertiary);
                                            client.anInt1015 = secondary;
                                            break label960;
                                        }

                                        if (opcode != 1007) {
                                            //world map sht
                                            if (opcode == 1008 || opcode == 1011 || opcode == 1009 || opcode == 1010 || opcode == 1012) {
                                                client.worldMap.processAction(opcode, primary, new WorldMapPosition(secondary), new WorldMapPosition(tertiary));
                                            }
                                            break label960;
                                        }
                                    }

                                    InterfaceComponent component = InterfaceComponent.lookup(tertiary, secondary);
                                    if (component != null) {
                                        InterfaceComponent.processAction(primary, tertiary, secondary, component.itemId, action);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (ItemSelection.state != 0) {
            ItemSelection.state = 0;
            InterfaceComponent.invalidate(InterfaceComponent.lookup(ItemSelection.uid));
        }

        if (ComponentSelection.state) {
            ComponentSelection.process();
        }

        //maybe drag related
        if (StockMarketOfferWorldComparator.anInterfaceComponent351 != null && client.anInt1018 == 0) {
            InterfaceComponent.invalidate(StockMarketOfferWorldComparator.anInterfaceComponent351);
        }

    }

    public static boolean isComponentAction2(int opcode) {
        if (opcode < 0) {
            return false;
        }
        int var1 = opcodes[opcode];
        if (var1 >= 2000) {
            var1 -= 2000;
        }

        return var1 == 1007;
    }

}
