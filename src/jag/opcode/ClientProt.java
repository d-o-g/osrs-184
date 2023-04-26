package jag.opcode;

import jag.Login;
import jag.audi.AudioSystem;
import jag.audi.ObjectSound;
import jag.game.client;
import jag.graphics.IndexedSprite;
import jag.js5.Archive;
import jag.worldmap.WorldMapAreaChunk_Sub2;
import jag.worldmap.WorldMapElement;
import jag.worldmap.WorldMapLabelSize;

public class ClientProt implements OutgoingPacketProtocol {
    public static final ClientProt USING_AWT_FRAME = new ClientProt(0, 4);
    public static final ClientProt ITEM_ACTION_2 = new ClientProt(5, 8);
    public static final ClientProt DIALOG_ACTION = new ClientProt(1, 6);
    public static final ClientProt BUTTON_CLOSE_ACTION = new ClientProt(57, 0);
    public static final ClientProt TABLE_ACTION_4 = new ClientProt(47, 8);
    public static final ClientProt INTERFACE_ACTION_9 = new ClientProt(2, 8);
    public static final ClientProt REMOVE_FRIEND = new ClientProt(48, -1);
    public static final ClientProt REMOVE_FROM_IGNORE_LIST = new ClientProt(56, -1);
    public static final ClientProt OBJECT_ACTION_4 = new ClientProt(50, 7);
    public static final ClientProt GAME_STATE_EVENT_REQUEST = new ClientProt(3, -1);
    public static final ClientProt PLAYER_ACTION_1 = new ClientProt(43, 3);
    public static final ClientProt MOUSE_MOTION_RECORD = new ClientProt(4, -1);
    public static final ClientProt PICKABLE_ACTION_4 = new ClientProt(24, 7);
    public static final ClientProt NPC_ACTION_2 = new ClientProt(35, 3);
    public static final ClientProt OBJECT_ACTION_2 = new ClientProt(42, 7);
    public static final ClientProt USE_SPELL_ON_ITEM = new ClientProt(62, 14);
    public static final ClientProt ADD_FRIEND = new ClientProt(80, -1);
    public static final ClientProt ADD_TO_IGNORE_LIST = new ClientProt(84, -1);
    public static final ClientProt TABLE_ACTION_0 = new ClientProt(33, 8);
    public static final ClientProt OBJECT_ACTION_0 = new ClientProt(51, 7);
    public static final ClientProt TABLE_ACTION_1 = new ClientProt(31, 8);
    public static final ClientProt PLAYER_ACTION_3 = new ClientProt(71, 3);
    public static final ClientProt OBJECT_ACTION_1 = new ClientProt(6, 7);
    public static final ClientProt ITEM_ACTION_0 = new ClientProt(88, 8);
    public static final ClientProt USE_ITEM_ON_PLAYER = new ClientProt(59, 11);
    public static final ClientProt BUTTON_ACTION = new ClientProt(99, 4);
    public static final ClientProt PROCESS_REFLECTION = new ClientProt(12, -1);
    public static final ClientProt NPC_ACTION_0 = new ClientProt(89, 3);
    public static final ClientProt TELEPORT = new ClientProt(7, 9);
    public static final ClientProt NPC_ACTION_1 = new ClientProt(96, 3);
    public static final ClientProt TABLE_ACTION_3 = new ClientProt(30, 8);
    public static final ClientProt PLAYER_ACTION_0 = new ClientProt(81, 3);
    public static final ClientProt PROCESS_NUMERIC_INPUT = new ClientProt(8, 4);
    public static final ClientProt EXAMINE_ITEM = new ClientProt(28, 2);
    public static final ClientProt USE_ITEM_ON_PICKABLE = new ClientProt(82, 15);
    public static final ClientProt USE_SPELL_ON_NPC = new ClientProt(77, 9);
    public static final ClientProt EXAMINE_NPC = new ClientProt(9, 2);
    public static final ClientProt INTERFACE_ACTION_3 = new ClientProt(10, 8);
    public static final ClientProt OBJECT_ACTION_3 = new ClientProt(95, 7);
    public static final ClientProt NPC_ACTION_4 = new ClientProt(37, 3);
    public static final ClientProt PLAYER_ACTION_2 = new ClientProt(61, 3);
    public static final ClientProt USE_ITEM_ON_NPC = new ClientProt(11, 11);
    public static final ClientProt ITEM_ACTION_3 = new ClientProt(14, 8);
    public static final ClientProt JMOD_WORLDMAP_SELECTION = new ClientProt(69, 4);
    public static final ClientProt NPC_ACTION_3 = new ClientProt(79, 3);
    public static final ClientProt PICKABLE_ACTION_3 = new ClientProt(63, 7);
    public static final ClientProt USE_SPELL_ON_COMPONENT = new ClientProt(64, 16);
    public static final ClientProt ITEM_ACTION_1 = new ClientProt(70, 8);
    public static final ClientProt PLAYER_APPEARANCE_BUTTON = new ClientProt(44, 13);
    public static final ClientProt SCENE_LOADED = new ClientProt(13, 0);
    public static final ClientProt INTERFACE_ACTION_0 = new ClientProt(60, 8);
    public static final ClientProt EXAMINE_OBJECT = new ClientProt(36, 2);
    public static final ClientProt GAME_BOUNDS = new ClientProt(72, 5);
    public static final ClientProt PLAYER_ACTION_6 = new ClientProt(90, 3);
    public static final ClientProt PICKABLE_ACTION_0 = new ClientProt(97, 7);
    public static final ClientProt PLAYER_ACTION_5 = new ClientProt(52, 3);
    public static final ClientProt FOCUS_INFO = new ClientProt(15, 1);
    public static final ClientProt USE_SPELL_ON_OBJECT = new ClientProt(68, 13);
    public static final ClientProt PICKABLE_ACTION_2 = new ClientProt(18, 7);
    public static final ClientProt INTERFACE_ACTION_5 = new ClientProt(16, 8);
    public static final ClientProt PLAYER_ACTION_7 = new ClientProt(78, 3);
    public static final ClientProt USE_ITEM_ON_ITEM = new ClientProt(85, 16);
    public static final ClientProt ITEM_ACTION_4 = new ClientProt(76, 8);
    public static final ClientProt PROCESS_COMMAND = new ClientProt(17, -1);
    public static final ClientProt INTERFACE_ACTION_1 = new ClientProt(65, 8);
    public static final ClientProt INTERFACE_ACTION_2 = new ClientProt(73, 8);
    public static final ClientProt TABLE_ACTION_2 = new ClientProt(66, 8);
    public static final ClientProt INTERFACE_ACTION_7 = new ClientProt(19, 8);
    public static final ClientProt USE_SPELL_ON_PLAYER = new ClientProt(55, 9);
    public static final ClientProt INTERFACE_ACTION_4 = new ClientProt(20, 8);
    public static final ClientProt PICKABLE_ACTION_1 = new ClientProt(27, 7);
    public static final ClientProt USE_ITEM_ON_OBJECT = new ClientProt(46, 15);
    public static final ClientProt PROCESS_CHAT_MODE = new ClientProt(21, 3);
    public static final ClientProt USE_SPELL_ON_PICKABLE = new ClientProt(34, 13);
    public static final ClientProt GARBAGE_COLLECTOR = new ClientProt(75, 10);
    public static final ClientProt KICK_CLANCHANNEL_USER = new ClientProt(22, -1);
    public static final ClientProt WALK_MAP = new ClientProt(91, -1);
    public static final ClientProt PLAYER_ACTION_4 = new ClientProt(58, 3);
    public static final ClientProt KEEP_ALIVE = new ClientProt(23, 0);
    public static final ClientProt CAMERA_INFO = new ClientProt(25, 4);
    public static final ClientProt MOUSE_ACTION = new ClientProt(26, 6);
    public static final ClientProt DRAGGED_COMPONENT = new ClientProt(94, 16);
    public static final ClientProt KEY_INFO = new ClientProt(40, -2);
    public static final ClientProt INTERFACE_ACTION_8 = new ClientProt(29, 8);
    public static final ClientProt SEND_PUBLIC_CHAT = new ClientProt(32, -1);
    public static final ClientProt DRAG_ITEM = new ClientProt(38, 9);
    public static final ClientProt PROCESS_ALPHABETICAL_INPUT = new ClientProt(39, -1);
    public static final ClientProt INTERFACE_ACTION_6 = new ClientProt(87, 8);
    public static final ClientProt IDLE_LOGOUT = new ClientProt(41, 0);
    public static final ClientProt PROCESS_NAME_INPUT = new ClientProt(100, -1);
    public static final ClientProt PROCESS_OBJ_INPUT = new ClientProt(54, 2);
    public static final ClientProt SET_CLANCHANNEL_RANK = new ClientProt(67, -1);
    public static final ClientProt JOIN_CLANCHANNEL = new ClientProt(53, -1);
    public static final ClientProt SEND_PRIVATE_CHAT = new ClientProt(93, -2);
    public static final ClientProt REPORT_ABUSE = new ClientProt(92, -1);
    public static final ClientProt REPORT_BUG = new ClientProt(86, -2);
    public static final ClientProt CLOSE_OCULUS_ORB = new ClientProt(49, 0);
    public static final ClientProt PROCESS_MOVEMENT = new ClientProt(98, -1);

    static final ClientProt AN_CLIENT_PROT_8 = new ClientProt(45, -1);
    static final ClientProt AN_CLIENT_PROT_32 = new ClientProt(74, 7);
    static final ClientProt AN_CLIENT_PROT_63 = new ClientProt(83, 2);

    final int opcode;
    final int size;

    ClientProt(int var1, int var2) {
        this.opcode = var1;
        this.size = var2;
    }

    public static void method4() {
        for (ObjectSound sound = ObjectSound.OBJECT_SOUNDS.head(); sound != null; sound = ObjectSound.OBJECT_SOUNDS.next()) {
            if (sound.aClass5_Sub6_Sub2_370 != null) {
                WorldMapLabelSize.aClass5_Sub6_Sub1_528.removeDelegate(sound.aClass5_Sub6_Sub2_370);
                sound.aClass5_Sub6_Sub2_370 = null;
            }

            if (sound.aClass5_Sub6_Sub2_369 != null) {
                WorldMapLabelSize.aClass5_Sub6_Sub1_528.removeDelegate(sound.aClass5_Sub6_Sub2_369);
                sound.aClass5_Sub6_Sub2_369 = null;
            }
        }

        ObjectSound.OBJECT_SOUNDS.clear();
    }

    public static IndexedSprite method6(boolean var0, boolean var1) {
        return var0 ? (var1 ? Login.aDoublyNode_Sub24_Sub4_470 : WorldMapAreaChunk_Sub2.aDoublyNode_Sub24_Sub4_288) : (var1 ? WorldMapElement.aDoublyNode_Sub24_Sub4_363 : Login.aDoublyNode_Sub24_Sub4_1148);
    }

    public static void method5(int var0) {
        if (client.anInt900 != 0 && var0 != -1) {
            AudioSystem.init(Archive.audioTracks2, var0, 0, client.anInt900, false);
            client.aBoolean904 = true;
        }

    }
}
