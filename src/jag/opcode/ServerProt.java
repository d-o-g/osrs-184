package jag.opcode;

public class ServerProt {
    public static final ServerProt LOCK_CAMERA = new ServerProt(0, 6);
    public static final ServerProt UPDATE_ITEM_TABLE = new ServerProt(1, -2);
    public static final ServerProt PICKABLE_COUNT = new ServerProt(2, 7);
    public static final ServerProt OPEN_BROWSER_URL = new ServerProt(3, -2);
    public static final ServerProt AN_SERVER_PROT_249 = new ServerProt(4, -2);
    public static final ServerProt AN_SERVER_PROT_246 = new ServerProt(5, 0);
    public static final ServerProt UPDATE_CHAT_MODE = new ServerProt(6, 2);
    public static final ServerProt SET_WORLD = new ServerProt(7, -1);
    public static final ServerProt AN_SERVER_PROT_258 = new ServerProt(8, 4);
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
    public static final ServerProt AN_SERVER_PROT_175 = new ServerProt(26, 8);
    public static final ServerProt AN_SERVER_PROT_234 = new ServerProt(17, 1);
    public static final ServerProt LOC_DEL = new ServerProt(60, 2);
    public static final ServerProt UPDATE_GRAND_EXCHANGE = new ServerProt(18, 20);
    public static final ServerProt LOC_ANIM = new ServerProt(19, 6);
    public static final ServerProt AN_SERVER_PROT_217 = new ServerProt(72, 2);
    public static final ServerProt AN_SERVER_PROT_250 = new ServerProt(20, -1);
    public static final ServerProt PLAYER_LOC_TRANSMOG = new ServerProt(21, 14);
    public static final ServerProt AN_SERVER_PROT_230 = new ServerProt(23, -1);
    public static final ServerProt AN_SERVER_PROT_238 = new ServerProt(22, -1);
    public static final ServerProt UPDATE_ITEMTABLE = new ServerProt(49, -2);
    public static final ServerProt AN_SERVER_PROT_241 = new ServerProt(24, 6);
    public static final ServerProt SET_DESTINATION = new ServerProt(25, 2);
    public static final ServerProt MAP_ANIM = new ServerProt(54, 4);
    public static final ServerProt PROCESS_REFLECTION_REQUEST = new ServerProt(28, -2);
    public static final ServerProt GARBAGE_COLLECTOR = new ServerProt(50, 8);
    public static final ServerProt SET_REGION_CHUNK = new ServerProt(29, 2);
    public static final ServerProt PICKABLE_REMOVE = new ServerProt(30, 3);
    public static final ServerProt AN_SERVER_PROT_224 = new ServerProt(69, 2);
    public static final ServerProt AN_SERVER_PROT_190 = new ServerProt(31, -2);
    public static final ServerProt AN_SERVER_PROT_203 = new ServerProt(64, 4);
    public static final ServerProt UPDATE_COMPONENT_TEXT = new ServerProt(32, -2);
    public static final ServerProt AN_SERVER_PROT_196 = new ServerProt(33, 5);
    public static final ServerProt UPDATE_VARP = new ServerProt(34, 3);
    public static final ServerProt AN_SERVER_PROT_211 = new ServerProt(73, 8);
    public static final ServerProt DROP_CONNECTION = new ServerProt(35, 1);
    public static final ServerProt AN_SERVER_PROT_210 = new ServerProt(61, -2);
    public static final ServerProt AN_SERVER_PROT_184 = new ServerProt(36, -2);
    public static final ServerProt SET_COMPONENT_MODEL_TYPE2 = new ServerProt(37, 6);
    public static final ServerProt AN_SERVER_PROT_181 = new ServerProt(39, 6);
    public static final ServerProt UPDATE_RANDOM = new ServerProt(70, 28);
    public static final ServerProt SET_COMPONENT_FOREGROUND = new ServerProt(40, 6);
    public static final ServerProt SOUND_AREA = new ServerProt(41, 5);
    public static final ServerProt UPDATE_VARP2 = new ServerProt(42, 6);
    public static final ServerProt AN_SERVER_PROT_177 = new ServerProt(43, 1);
    public static final ServerProt SET_COMPONENT_TO_ITEM_MODEL = new ServerProt(75, 10);
    public static final ServerProt AN_SERVER_PROT_194 = new ServerProt(44, 2);
    public static final ServerProt AN_SERVER_PROT_180 = new ServerProt(45, -2);
    public static final ServerProt UPDATE_HINT_ARROW = new ServerProt(46, 6);
    public static final ServerProt AN_SERVER_PROT_182 = new ServerProt(47, 1);
    public static final ServerProt AN_SERVER_PROT_197 = new ServerProt(48, -2);
    public static final ServerProt AN_SERVER_PROT_192 = new ServerProt(51, 4);
    public static final ServerProt AN_SERVER_PROT_223 = new ServerProt(52, 0);
    public static final ServerProt UPDATE_NPCS = new ServerProt(53, -2);
    public static final ServerProt SET_MAP_STATE = new ServerProt(55, 1);
    public static final ServerProt FIRE_SCRIPT_EVENT = new ServerProt(56, -2);
    public static final ServerProt AN_SERVER_PROT_209 = new ServerProt(57, 2);
    public static final ServerProt SET_PLAYER_ACTION = new ServerProt(80, -1);
    public static final ServerProt AN_SERVER_PROT_218 = new ServerProt(58, 0);
    public static final ServerProt AN_SERVER_PROT_225 = new ServerProt(59, -2);
    public static final ServerProt AN_SERVER_PROT_200 = new ServerProt(67, 0);
    public static final ServerProt ADD_CHAT_MESSAGE = new ServerProt(62, -1);
    public static final ServerProt SET_COMPONENT_MODEL_TYPE1 = new ServerProt(63, 6);
    public static final ServerProt UNLOCK_CAMERA = new ServerProt(76, 0);
    public static final ServerProt PICKABLE_ADD = new ServerProt(78, 5);
    public static final ServerProt RESET_REGION_CHUNK = new ServerProt(65, 2);
    public static final ServerProt UPDATE_MAP_REGION = new ServerProt(66, -2);
    public static final ServerProt AN_SERVER_PROT_219 = new ServerProt(68, 6);
    public static final ServerProt AN_SERVER_PROT_236 = new ServerProt(79, 2);
    public static final ServerProt UPDATE_INSTANCE_REGION = new ServerProt(71, -2);
    public static final ServerProt UPDATE_VARPS = new ServerProt(74, 0);
    public static final ServerProt LOC_ADD_CHANGE = new ServerProt(77, 4);
    public static final ServerProt SET_COMPONENT_MODEL_TYPE3 = new ServerProt(81, 4);
    public static final ServerProt AN_SERVER_PROT_247 = new ServerProt(82, -2);
    public static final ServerProt AN_SERVER_PROT_231 = new ServerProt(83, 5);
    public static final ServerProt AN_SERVER_PROT_240 = new ServerProt(84, 7);
    public static final ServerProt AN_SERVER_PROT_227 = new ServerProt(85, 4);
    public static int anInt206;

    public final int opcode;
    public final int size;

    ServerProt(int var1, int var2) {
        this.opcode = var1;
        this.size = var2;
    }

    public static ServerProt[] values() {
        return new ServerProt[]{LOCK_CAMERA, UPDATE_ITEM_TABLE, PICKABLE_COUNT, OPEN_BROWSER_URL, AN_SERVER_PROT_249, AN_SERVER_PROT_246, UPDATE_CHAT_MODE, SET_WORLD, AN_SERVER_PROT_258, UPDATE_STATS, SET_COMPONENT_HIDDEN, SET_OCULUSORB_MODE, AN_SERVER_PROT_245, SET_COMPONENT_ZOOM, UPDATE_STOCKMARKET, UPDATE_REGION_CHUNK, LOGOUT, AN_SERVER_PROT_234, UPDATE_GRAND_EXCHANGE, LOC_ANIM, AN_SERVER_PROT_250, PLAYER_LOC_TRANSMOG, AN_SERVER_PROT_238, AN_SERVER_PROT_230, AN_SERVER_PROT_241, SET_DESTINATION, AN_SERVER_PROT_175, UPDATE_COMPONENT_CONFIG, PROCESS_REFLECTION_REQUEST, SET_REGION_CHUNK, PICKABLE_REMOVE, AN_SERVER_PROT_190, UPDATE_COMPONENT_TEXT, AN_SERVER_PROT_196, UPDATE_VARP, DROP_CONNECTION, AN_SERVER_PROT_184, SET_COMPONENT_MODEL_TYPE2, MAP_PROJANIM, AN_SERVER_PROT_181, SET_COMPONENT_FOREGROUND, SOUND_AREA, UPDATE_VARP2, AN_SERVER_PROT_177, AN_SERVER_PROT_194, AN_SERVER_PROT_180, UPDATE_HINT_ARROW, AN_SERVER_PROT_182, AN_SERVER_PROT_197, UPDATE_ITEMTABLE, GARBAGE_COLLECTOR, AN_SERVER_PROT_192, AN_SERVER_PROT_223, UPDATE_NPCS, MAP_ANIM, SET_MAP_STATE, FIRE_SCRIPT_EVENT, AN_SERVER_PROT_209, AN_SERVER_PROT_218, AN_SERVER_PROT_225, LOC_DEL, AN_SERVER_PROT_210, ADD_CHAT_MESSAGE, SET_COMPONENT_MODEL_TYPE1, AN_SERVER_PROT_203, RESET_REGION_CHUNK, UPDATE_MAP_REGION, AN_SERVER_PROT_200, AN_SERVER_PROT_219, AN_SERVER_PROT_224, UPDATE_RANDOM, UPDATE_INSTANCE_REGION, AN_SERVER_PROT_217, AN_SERVER_PROT_211, UPDATE_VARPS, SET_COMPONENT_TO_ITEM_MODEL, UNLOCK_CAMERA, LOC_ADD_CHANGE, PICKABLE_ADD, AN_SERVER_PROT_236, SET_PLAYER_ACTION, SET_COMPONENT_MODEL_TYPE3, AN_SERVER_PROT_247, AN_SERVER_PROT_231, AN_SERVER_PROT_240, AN_SERVER_PROT_227};
    }
}
