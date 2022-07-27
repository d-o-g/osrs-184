package jag.opcode;

public class ServerProt {
    public static final ServerProt LOCK_CAMERA = new ServerProt(0, 6);
    public static final ServerProt UPDATE_ITEM_TABLE = new ServerProt(1, -2);
    public static final ServerProt PICKABLE_COUNT = new ServerProt(2, 7);
    public static final ServerProt OPEN_BROWSER_URL = new ServerProt(3, -2);
    public static final ServerProt UPDATE_PLAYER = new ServerProt(4, -2);
    public static final ServerProt RESET_VARPS = new ServerProt(5, 0);
    public static final ServerProt UPDATE_CHAT_MODE = new ServerProt(6, 2);
    public static final ServerProt SET_WORLD = new ServerProt(7, -1);
    public static final ServerProt CLEAR_INV = new ServerProt(8, 4);
    public static final ServerProt UPDATE_STATS = new ServerProt(9, 6);
    public static final ServerProt SET_COMPONENT_HIDDEN = new ServerProt(10, 5);
    public static final ServerProt SET_OCULUSORB_MODE = new ServerProt(11, 1);
    public static final ServerProt AN_SERVER_PROT_245 = new ServerProt(12, 8);
    public static final ServerProt MAP_PROJANIM = new ServerProt(38, 15);
    public static final ServerProt SET_COMPONENT_ZOOM = new ServerProt(13, 10);
    public static final ServerProt UPDATE_COMPONENT_CONFIG = new ServerProt(27, 12);
    public static final ServerProt UPDATE_STOCKMARKET = new ServerProt(14, -2);
    public static final ServerProt UPDATE_REGION_CHUNK = new ServerProt(15, -2);
    public static final ServerProt LOGOUT = new ServerProt(16, 0);
    public static final ServerProt IF_SETPOS = new ServerProt(26, 8);
    public static final ServerProt SET_PUBLIC_CHATMODE = new ServerProt(17, 1);
    public static final ServerProt LOC_DEL = new ServerProt(60, 2);
    public static final ServerProt UPDATE_GRAND_EXCHANGE = new ServerProt(18, 20);
    public static final ServerProt LOC_ANIM = new ServerProt(19, 6);
    public static final ServerProt SET_UPDATE_TIMER = new ServerProt(72, 2);
    public static final ServerProt UPDATE_FRIENDSCHAT = new ServerProt(20, -1);
    public static final ServerProt PLAYER_LOC_TRANSMOG = new ServerProt(21, 14);
    public static final ServerProt SET_JS_COOKIE = new ServerProt(23, -1);
    public static final ServerProt ADD_FRIENDS_CHAT_MESSAGE = new ServerProt(22, -1);
    public static final ServerProt UPDATE_ITEMTABLE = new ServerProt(49, -2);
    public static final ServerProt SET_IF_SCROLL = new ServerProt(24, 6);
    public static final ServerProt SET_DESTINATION = new ServerProt(25, 2);
    public static final ServerProt MAP_ANIM = new ServerProt(54, 4);
    public static final ServerProt PROCESS_REFLECTION_REQUEST = new ServerProt(28, -2);
    public static final ServerProt GARBAGE_COLLECTOR = new ServerProt(50, 8);
    public static final ServerProt SET_REGION_CHUNK = new ServerProt(29, 2);
    public static final ServerProt PICKABLE_REMOVE = new ServerProt(30, 3);
    public static final ServerProt SET_PLAYER_WEIGHT = new ServerProt(69, 2);
    public static final ServerProt UPDATE_IF_CONFIGS = new ServerProt(31, -2);
    public static final ServerProt SET_OCULUS_ORB_LOCAL = new ServerProt(64, 4);
    public static final ServerProt UPDATE_COMPONENT_TEXT = new ServerProt(32, -2);
    public static final ServerProt ADD_AUDIO_EFFECT = new ServerProt(33, 5);
    public static final ServerProt UPDATE_VARP = new ServerProt(34, 3);
    public static final ServerProt MOVE_SUBIF = new ServerProt(73, 8);
    public static final ServerProt DROP_CONNECTION = new ServerProt(35, 1);
    public static final ServerProt UPDATE_FRIENDLIST = new ServerProt(61, -2);
    public static final ServerProt NPC_UPDATE1 = new ServerProt(36, -2);
    public static final ServerProt SET_IF_MODEL_TYPE2 = new ServerProt(37, 6);
    public static final ServerProt SET_IF_ANIMATION = new ServerProt(39, 6);
    public static final ServerProt UPDATE_RANDOM = new ServerProt(70, 28);
    public static final ServerProt SET_COMPONENT_FOREGROUND = new ServerProt(40, 6);
    public static final ServerProt SOUND_AREA = new ServerProt(41, 5);
    public static final ServerProt UPDATE_VARP2 = new ServerProt(42, 6);
    public static final ServerProt INIT_WORLDMAP_HEATMAP = new ServerProt(43, 1);
    public static final ServerProt SET_IF_TO_ITEM_MODEL = new ServerProt(75, 10);
    public static final ServerProt UPDATE_AUDIOSYSTEM = new ServerProt(44, 2);
    public static final ServerProt ADD_PREV_CHAT_MESSAGE = new ServerProt(45, -2);
    public static final ServerProt UPDATE_HINT_ARROW = new ServerProt(46, 6);
    public static final ServerProt UPDATE_RUN_ENERGY = new ServerProt(47, 1);
    public static final ServerProt INIT_FRIENDSCHAT = new ServerProt(48, -2);
    public static final ServerProt AN_SERVER_PROT_192 = new ServerProt(51, 4);
    public static final ServerProt RESET_PATHINGENTITY_ANIMS = new ServerProt(52, 0);
    public static final ServerProt UPDATE_NPCS = new ServerProt(53, -2);
    public static final ServerProt SET_MAP_STATE = new ServerProt(55, 1);
    public static final ServerProt FIRE_SCRIPT_EVENT = new ServerProt(56, -2);
    public static final ServerProt SET_INV_STOP_TRANSIT = new ServerProt(57, 2);
    public static final ServerProt SET_PLAYER_ACTION = new ServerProt(80, -1);
    public static final ServerProt CLOSE_DIALOG = new ServerProt(58, 0);
    public static final ServerProt UPDATE_IGNORELIST = new ServerProt(59, -2);
    public static final ServerProt INIT_RELATIONSHIP_SYSYEM = new ServerProt(67, 0);
    public static final ServerProt ADD_CHAT_MESSAGE = new ServerProt(62, -1);
    public static final ServerProt SET_COMPONENT_MODEL_TYPE1 = new ServerProt(63, 6);
    public static final ServerProt UNLOCK_CAMERA = new ServerProt(76, 0);
    public static final ServerProt PICKABLE_ADD = new ServerProt(78, 5);
    public static final ServerProt RESET_REGION_CHUNK = new ServerProt(65, 2);
    public static final ServerProt UPDATE_MAP_REGION = new ServerProt(66, -2);
    public static final ServerProt UPDATE_CAMERA = new ServerProt(68, 6);
    public static final ServerProt AN_SERVER_PROT_236 = new ServerProt(79, 2);
    public static final ServerProt UPDATE_INSTANCE_REGION = new ServerProt(71, -2);
    public static final ServerProt UPDATE_VARPS = new ServerProt(74, 0);
    public static final ServerProt LOC_ADD_CHANGE = new ServerProt(77, 4);
    public static final ServerProt SET_COMPONENT_MODEL_TYPE3 = new ServerProt(81, 4);
    public static final ServerProt SEND_PRIV_MSG2 = new ServerProt(82, -2);
    public static final ServerProt UPDATE_AUDIOTRACKS = new ServerProt(83, 5);
    public static final ServerProt AN_SERVER_PROT_240 = new ServerProt(84, 7);
    public static final ServerProt CLOSE_SUBIF = new ServerProt(85, 4);
    public static int anInt206;

    public final int opcode;
    public final int size;

    ServerProt(int var1, int var2) {
        this.opcode = var1;
        this.size = var2;
    }

    public static ServerProt[] values() {
        return new ServerProt[]{LOCK_CAMERA, UPDATE_ITEM_TABLE, PICKABLE_COUNT, OPEN_BROWSER_URL, UPDATE_PLAYER, RESET_VARPS, UPDATE_CHAT_MODE, SET_WORLD, CLEAR_INV, UPDATE_STATS, SET_COMPONENT_HIDDEN, SET_OCULUSORB_MODE, AN_SERVER_PROT_245, SET_COMPONENT_ZOOM, UPDATE_STOCKMARKET, UPDATE_REGION_CHUNK, LOGOUT, SET_PUBLIC_CHATMODE, UPDATE_GRAND_EXCHANGE, LOC_ANIM, UPDATE_FRIENDSCHAT, PLAYER_LOC_TRANSMOG, ADD_FRIENDS_CHAT_MESSAGE, SET_JS_COOKIE, SET_IF_SCROLL, SET_DESTINATION, IF_SETPOS, UPDATE_COMPONENT_CONFIG, PROCESS_REFLECTION_REQUEST, SET_REGION_CHUNK, PICKABLE_REMOVE, UPDATE_IF_CONFIGS, UPDATE_COMPONENT_TEXT, ADD_AUDIO_EFFECT, UPDATE_VARP, DROP_CONNECTION, NPC_UPDATE1, SET_IF_MODEL_TYPE2, MAP_PROJANIM, SET_IF_ANIMATION, SET_COMPONENT_FOREGROUND, SOUND_AREA, UPDATE_VARP2, INIT_WORLDMAP_HEATMAP, UPDATE_AUDIOSYSTEM, ADD_PREV_CHAT_MESSAGE, UPDATE_HINT_ARROW, UPDATE_RUN_ENERGY, INIT_FRIENDSCHAT, UPDATE_ITEMTABLE, GARBAGE_COLLECTOR, AN_SERVER_PROT_192, RESET_PATHINGENTITY_ANIMS, UPDATE_NPCS, MAP_ANIM, SET_MAP_STATE, FIRE_SCRIPT_EVENT, SET_INV_STOP_TRANSIT, CLOSE_DIALOG, UPDATE_IGNORELIST, LOC_DEL, UPDATE_FRIENDLIST, ADD_CHAT_MESSAGE, SET_COMPONENT_MODEL_TYPE1, SET_OCULUS_ORB_LOCAL, RESET_REGION_CHUNK, UPDATE_MAP_REGION, INIT_RELATIONSHIP_SYSYEM, UPDATE_CAMERA, SET_PLAYER_WEIGHT, UPDATE_RANDOM, UPDATE_INSTANCE_REGION, SET_UPDATE_TIMER, MOVE_SUBIF, UPDATE_VARPS, SET_IF_TO_ITEM_MODEL, UNLOCK_CAMERA, LOC_ADD_CHANGE, PICKABLE_ADD, AN_SERVER_PROT_236, SET_PLAYER_ACTION, SET_COMPONENT_MODEL_TYPE3, SEND_PRIV_MSG2, UPDATE_AUDIOTRACKS, AN_SERVER_PROT_240, CLOSE_SUBIF};
    }
}
