package jag.game;

import jag.*;
import jag.audi.*;
import jag.audi.vorbis.RawAudioOverride;
import jag.commons.*;
import jag.commons.collection.IntegerNode;
import jag.commons.collection.LinkedList;
import jag.commons.collection.NodeDeque;
import jag.commons.collection.NodeTable;
import jag.commons.crypt.Base37;
import jag.commons.crypt.Djb2;
import jag.commons.input.Keyboard;
import jag.commons.input.Mouse;
import jag.commons.input.MouseWheel;
import jag.commons.time.Clock;
import jag.game.menu.*;
import jag.game.option.AttackOptionPriority;
import jag.game.option.ClientPreferences;
import jag.game.relationship.*;
import jag.game.scene.CollisionMap;
import jag.game.scene.HintArrow;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.*;
import jag.game.stockmarket.*;
import jag.game.type.*;
import jag.graphics.*;
import jag.js5.*;
import jag.opcode.*;
import jag.opcode.handler.*;
import jag.opcode.login.LoginHeaderType;
import jag.opcode.login.LoginProt;
import jag.opcode.login.LoginStep;
import jag.script.ClientScript;
import jag.script.ScriptEvent;
import jag.statics.*;
import jag.worldmap.*;
import netscape.javascript.JSObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class client extends GameShell implements LocalPlayerNameProvider {

    public static final boolean[] renderedComponents = new boolean[100];
    public static final boolean[] aBooleanArray1083 = new boolean[100];
    public static final boolean[] aBooleanArray1087 = new boolean[100];
    public static final boolean[] playerActionPriorities = new boolean[8];
    public static final boolean[] aBooleanArray919 = new boolean[5];

    public static final boolean mobile = false;
    public static final boolean lockMouseRecorder = true;

    public static final int[][][] dynamicSceneData = new int[4][13][13];

    public static final int[][] pathingEntityRenderPositions = new int[104][104];

    public static final int[] OBJECT_TYPE_TO_STUB_TYPE = new int[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
    public static final int[] PLAYER_ACTION_OPCODES = new int[]{44, 45, 46, 47, 48, 49, 50, 51};
    public static final int[] npcIndices2 = new int[1000];
    public static final int[] npcIndices = new int[32768];
    public static final int[] processedNpcIndices = new int[250];
    public static final int[] interfacePositionsX = new int[100];
    public static final int[] interfaceWidths = new int[100];
    public static final int[] currentLevels = new int[25];
    public static final int[] interfaceHeights = new int[100];
    public static final int[] levels = new int[25];
    public static final int[] interfacePositionsY = new int[100];
    public static final int[] experiences = new int[25];
    public static final int[] pathingEntityOrientations = new int[]{768, 1024, 1280, 512, 1536, 256, 0, 1792};
    public static final int[] anIntArray945 = new int[50];
    public static final int[] anIntArray942 = new int[50];
    public static final int[] inventories = new int[32];
    public static final int[] anIntArray1096 = new int[128];
    public static final int[] anIntArray899 = new int[50];
    public static final int[] anIntArray905 = new int[50];
    public static final int[] anIntArray906 = new int[50];
    public static final int[] anIntArray902 = new int[50];
    public static final int[] anIntArray917 = new int[5];
    public static final int[] anIntArray918 = new int[5];
    public static final int[] anIntArray1076 = new int[32];
    public static final int[] anIntArray914 = new int[5];
    public static final int[] anIntArray916 = new int[5];
    public static final int[] anIntArray892 = new int[1000];
    public static final int[] anIntArray894 = new int[1000];
    public static final int[] overheadMessageXPositions;
    public static final int[] overheadMessageYPositions;
    public static final int[] overheadMessageYShifts;
    public static final int[] overheadMessageXShifts;
    public static final int[] overheadMessageColors;
    public static final int[] overheadMessageEffects;
    public static final int[] overheadMessageCyclesRemaining;
    public static final int[] anIntArray1097 = new int[128];
    public static final int[] anIntArray1094 = new int[]{16776960, 16711680, 65280, 65535, 16711935, 16777215};
    public static final int[] anIntArray1079 = new int[32];

    public static final int redrawMode = 0;
    public static final int anInt1036 = 50;
    public static final int anInt980 = 2301979;
    public static final int anInt987 = 5063219;
    public static final int anInt983 = 3353893;
    public static final int anInt990 = 7759444;
    public static final int overheadMessageCapacity;

    public static final long[] aLongArray896 = new long[100];

    public static final String[] playerActions = new String[8];
    public static final String[] overheadMessages;

    public static final NodeDeque<Pickable>[][][] pickables = new NodeDeque[4][104][104];

    public static final CollisionMap[] collisionMaps = new CollisionMap[4];
    public static final StockMarketOffer[] stockMarketOffers = new StockMarketOffer[8];
    public static final AudioEffect[] audioEffects = new AudioEffect[50];
    public static final Sprite[] minimapFunctions = new Sprite[1000];
    public static final PlayerEntity[] players = new PlayerEntity[2048];
    public static final NpcEntity[] npcs = new NpcEntity[2048];

    public static final NodeDeque<ScriptEvent> inputFinishedEventScripts = new NodeDeque<>();
    public static final NodeDeque<ScriptEvent> renderEventScripts = new NodeDeque<>();
    public static final NodeDeque<ScriptEvent> inputOccuringEventScripts = new NodeDeque<>();
    public static final NodeDeque<EffectObject> effectObjects = new NodeDeque<>();
    public static final NodeDeque<Projectile> projectiles = new NodeDeque<>();

    public static final Map<Integer, ChatHistory> chatHistory = new HashMap<>();
    public static final ArrayList<LoadedArchive> archives = new ArrayList<>(10);;
    public static final PlayerModel renderedAppearance = new PlayerModel();
    public static final NetWriter netWriter = new NetWriter();
    public static final OperatingSystemProvider operatingSystemProvider = new DefaultOperatingSystemProvider();
    public static final DefaultRouteStrategy routeStrategy = new DefaultRouteStrategy();
    public static final GameStateEvent gameStateEvent = new GameStateEvent();
    public static final StockMarketOfferWorldComparator stockMarketComparator = new StockMarketOfferWorldComparator();

    public static byte[] random = null;

    public static boolean membersWorld = false;
    public static boolean dynamicScene = false;
    public static boolean lowMemory = false;
    public static boolean processingComponentDrag = false;
    public static boolean pendingDisconnect = false;
    public static boolean processingComponentDragTopLevel = false;
    public static boolean usingBufferedConnection = true;
    public static boolean resizableMode = true;
    public static boolean aBoolean904 = false;
    public static boolean loadingPleaseWait = true;
    public static boolean previousFocusState = true;
    public static boolean displayFps = false;
    public static boolean draggingComponent = false;
    public static boolean displayLoadingMessages = true;
    public static boolean aBoolean1043 = true;
    public static boolean aBoolean1062 = false;
    public static boolean cameraLocked = false;
    public static boolean aBoolean1042 = false;
    public static boolean aBoolean1044 = false;
    public static boolean rememberUsername = false;
    public static boolean aBoolean1037 = false;
    public static boolean useDefaultScrollbar = false;
    public static boolean displaySelf = true;
    public static boolean aBoolean1056 = false;
    public static boolean hasFocus;

    public static int clientParameter4;
    public static int anInt1232;
    public static int baseX;
    public static int baseY;
    public static int currentWorld = 1;
    public static int anInt929 = -1;
    public static int rights = 0;
    public static int build;
    public static int canvasWidth;
    public static int canvasHeight;
    public static int engineCycle = 0;
    public static int npcCount2 = 0;
    public static int rebootTimer = 0;
    public static int rootInterfaceIndex = -1;
    public static int gameState = 0;
    public static int playerIndex = -1;
    public static int viewportRenderX = -1;
    public static int varpControlledInt1 = 0;
    public static int npcUpdateCount = 0;
    public static int renderedComponentCount = 0;
    public static int anInt1084 = -2;
    public static int logoutTimer = 0;
    public static int viewportRenderY = -1;
    public static int js5Cycles = 0;
    public static int js5State = 0;
    public static int currentWorldMask = 0;
    public static int loginProcess = 0; //TODO name
    public static int loginStage = 0;
    public static int gameType = 0;
    public static int anInt1039 = -1;
    public static int relationshipSystemState = 0;
    public static int anInt923 = -1;
    public static int anInt1038 = -1;
    public static int npcCount = 0;
    public static int destinationX = 0;
    public static int loginStageCycles = 0;
    public static int anInt1075 = 1;
    public static int anInt1065 = 0;
    public static int js5ErrorCount = 0;
    public static int destinationY = 0;
    public static int currentComponentDragX = 0;
    public static int anInt1066 = 0;
    public static int currentComponentDragY = 0;
    public static int varpControlledInt2 = -1;
    public static int anInt1060 = -1;
    public static int anInt1068 = -1;
    public static int anInt1073 = -1;
    public static int lastMouseRecordX = -1;
    public static int lastMouseRecordY = -1;
    public static int anInt1047 = 0;
    public static int anInt1069 = -1;
    public static int anInt972 = 0;
    public static int anInt1059 = -1;
    public static int viewportWidth = 0;
    public static int loadingDrawState = 0;
    public static int viewportScale = 0;
    public static int loadingIndicator1 = 0;
    public static int componentDragCycles = 0;
    public static int loadingIndicator2 = 1;
    public static int loadingIndicator3 = 0;
    public static int viewportHeight = 0;
    public static int loadingIndicator4 = 1;
    public static int draggingComponentX = 0;
    public static int draggingComponentY = 0;
    public static int anInt1018 = 0;
    public static int anInt1053 = -1;
    public static int mapState = 0;
    public static int anInt1015 = 0;
    public static int draggingComponentSourceIndex = 0;
    public static int draggingComponentIndex = 0;
    public static int bootState = 0;
    public static int anInt1074 = 0;
    public static int stockMarketUpdateCycle = 0;
    public static int audioEffectCount = 0;
    public static int energy = 0;
    public static int anInt898 = -1;
    public static int weight = 0;
    public static int serverTransferCycles = 0;
    public static int anInt1002 = 0;
    public static int anInt897 = 127;
    public static int anInt1092 = 0;
    public static int anInt1061 = 0;
    public static int anInt1078 = 0;
    public static int minimapFloorLevel = -1;
    public static int anInt930 = -1;
    public static int mouseWheelPtr = 0;
    public static int anInt931 = -1;
    public static int anInt1071 = 0;
    public static int viewportRenderCount = 0;
    public static int anInt926 = 0;
    public static int scrollbarWidth = 0;
    public static int anInt1041 = 0;
    public static int anInt1064 = 0;
    public static int anInt1014 = 0;
    public static int anInt891 = 0;
    public static int anInt900 = 255;
    public static int anInt901 = 127;
    public static int publicChatMode = 0;
    public static int displayPlayerNames = 0;
    public static int tradeChatMode = 0;
    public static int anInt1045 = 0;
    public static int anInt1054 = 0;
    public static int archiveEntryCount = 0;
    public static int anInt895 = 0;
    public static int anInt977 = 0;
    public static int anInt986 = 0;
    public static int anInt997 = 0;
    public static int anInt1008 = 0;
    public static int anInt909 = 0;
    public static int overheadMessageCount = 0;
    public static int anInt908 = 0;
    public static int anInt1063 = 0;

    public static long aLong1081 = 0L;
    public static long timeOfPreviousClick = 1L;
    public static long lastMouseRecordTime = -1L;
    public static long timeOfPreviousKeyPress = -1L;

    public static short aShort922 = 256;
    public static short aShort912 = 205;
    public static short aShort910 = 1;
    public static short aShort921 = 32767;
    public static short aShort907 = 32767;
    public static short aShort915 = 1;
    public static short aShort913 = 256;
    public static short aShort920 = 320;

    public static String[] aStringArray1533;

    public static String aString1091 = "";

    public static InterfaceComponent[][] interfaces;

    public static NodeDeque<PendingSpawn> pendingSpawns = new NodeDeque<>();
    public static NodeTable<SubInterface> subInterfaces = new NodeTable<>(8);
    public static NodeTable<IntegerNode> interfaceConfigs = new NodeTable<>(512);

    public static InterfaceComponent draggedSpecialComponent = null;
    public static InterfaceComponent draggedComponent = null;
    public static InterfaceComponent topLevelOfDraggedComponent = null;
    public static InterfaceComponent pleaseWaitComponent = null;
    public static InterfaceComponent minimapComponent = null;

    public static AttackOptionPriority playerAttackOptionPriority = AttackOptionPriority.HIDDEN;
    public static AttackOptionPriority npcAttackOptionPriority = AttackOptionPriority.HIDDEN;

    public static LoginStep loginStep = LoginStep.anEnum_Sub3_826;

    public static SceneGraph sceneGraph;

    public static ClientPreferences preferences;

    public static MouseRecorder mouseRecorder;

    public static Varcs varcs;

    public static WorldMap worldMap;

    public static ChatModePrivacyType publicChatPrivacyMode;

    public static OperatingSystemNode operatingSystemNode;

    public static FriendsChatSystem friendsChatSystem;

    public static RelationshipSystem relationshipSystem;

    public static URLRequestProcessor urlRequestProcessor;

    public static SecureRandomService secureRandomService = new SecureRandomService();

    public static HashMap<NamedFont, BaseFont> fonts = new HashMap<>();

    public static client instance;

    public static DevelopmentBuild devbuild;

    public static GraphicsProvider graphicsProvider;

    private static final ClientProtHandler clientProtHandler = new JagClientProtHandler(netWriter);

    private static final ServerProtHandler serverProtHandler = new JagServerProtHandler(netWriter);

    static {
        overheadMessageCapacity = 50;
        overheadMessageXPositions = new int[overheadMessageCapacity];
        overheadMessageYPositions = new int[overheadMessageCapacity];
        overheadMessageYShifts = new int[overheadMessageCapacity];
        overheadMessageXShifts = new int[overheadMessageCapacity];
        overheadMessageColors = new int[overheadMessageCapacity];
        overheadMessageEffects = new int[overheadMessageCapacity];
        overheadMessageCyclesRemaining = new int[overheadMessageCapacity];
        overheadMessages = new String[overheadMessageCapacity];
    }

    public static void processAudioEffects() {
        for (int i = 0; i < audioEffectCount; ++i) {
            if (anIntArray906[i] >= -10) {
                AudioEffect effect = audioEffects[i];
                if (effect == null) {
                    effect = AudioEffect.load(Archive.audioEffects, anIntArray899[i], 0);
                    if (effect == null) {
                        continue;
                    }

                    anIntArray906[i] += effect.method1521();
                    audioEffects[i] = effect;
                }

                if (anIntArray906[i] < 0) {
                    int var9;
                    if (anIntArray902[i] != 0) {
                        int var3 = (anIntArray902[i] & 255) * 128;
                        int var4 = anIntArray902[i] >> 16 & 255;
                        int var5 = var4 * 128 + 64 - PlayerEntity.local.absoluteX;
                        if (var5 < 0) {
                            var5 = -var5;
                        }

                        int var6 = anIntArray902[i] >> 8 & 255;
                        int var7 = var6 * 128 + 64 - PlayerEntity.local.absoluteY;
                        if (var7 < 0) {
                            var7 = -var7;
                        }

                        int var8 = var5 + var7 - 128;
                        if (var8 > var3) {
                            anIntArray906[i] = -100;
                            continue;
                        }

                        if (var8 < 0) {
                            var8 = 0;
                        }

                        var9 = (var3 - var8) * anInt897 / var3;
                    } else {
                        var9 = anInt901;
                    }

                    if (var9 > 0) {
                        RawAudioOverride var10 = effect.method1523().resample(Statics46.aClass98_446);
                        PcmStream_Sub2 var11 = PcmStream_Sub2.method598(var10, 100, var9);
                        var11.method585(anIntArray905[i] - 1);
                        WorldMapLabelSize.aClass5_Sub6_Sub1_528.method312(var11);
                    }

                    anIntArray906[i] = -100;
                }
            } else {
                --audioEffectCount;

                for (int var1 = i; var1 < audioEffectCount; ++var1) {
                    anIntArray899[var1] = anIntArray899[var1 + 1];
                    audioEffects[var1] = audioEffects[var1 + 1];
                    anIntArray905[var1] = anIntArray905[var1 + 1];
                    anIntArray906[var1] = anIntArray906[var1 + 1];
                    anIntArray902[var1] = anIntArray902[var1 + 1];
                }

                --i;
            }
        }

        if (aBoolean904) {
            boolean var12;
            if (AudioSystem.state != 0) {
                var12 = true;
            } else {
                var12 = Statics50.aClass5_Sub6_Sub3_326.method684();
            }

            if (!var12) {
                if (anInt900 != 0 && anInt898 != -1) {
                    Statics51.method344(Archive.audioTracks, anInt898, 0, anInt900, false);
                }

                aBoolean904 = false;
            }
        }

    }

    public static void processObjectSpawns() {
        for (PendingSpawn spawn = pendingSpawns.head(); spawn != null; spawn = pendingSpawns.next()) {
            if (spawn.hitpoints > 0) {
                --spawn.hitpoints;
            }

            int var1;
            int var2;
            ObjectDefinition var3;
            boolean var4;
            if (spawn.hitpoints == 0) {
                if (spawn.anInt380 >= 0) {
                    var1 = spawn.anInt380;
                    var2 = spawn.anInt375;
                    var3 = ObjectDefinition.get(var1);
                    if (var2 == 11) {
                        var2 = 10;
                    }

                    if (var2 >= 5 && var2 <= 8) {
                        var2 = 4;
                    }

                    var4 = var3.method1106(var2);
                    if (!var4) {
                        continue;
                    }
                }

                SceneGraph.method404(spawn.floorLevel, spawn.type, spawn.sceneX, spawn.sceneY, spawn.anInt380, spawn.anInt112, spawn.anInt375);
                spawn.unlink();
            } else {
                if (spawn.delay > 0) {
                    --spawn.delay;
                }

                if (spawn.delay == 0 && spawn.sceneX >= 1 && spawn.sceneY >= 1 && spawn.sceneX <= 102 && spawn.sceneY <= 102) {
                    if (spawn.anInt693 >= 0) {
                        var1 = spawn.anInt693;
                        var2 = spawn.anInt564;
                        var3 = ObjectDefinition.get(var1);
                        if (var2 == 11) {
                            var2 = 10;
                        }

                        if (var2 >= 5 && var2 <= 8) {
                            var2 = 4;
                        }

                        var4 = var3.method1106(var2);
                        if (!var4) {
                            continue;
                        }
                    }

                    SceneGraph.method404(spawn.floorLevel, spawn.type, spawn.sceneX, spawn.sceneY, spawn.anInt693, spawn.orientation, spawn.anInt564);
                    spawn.delay = -1;
                    if (spawn.anInt380 == spawn.anInt693 && spawn.anInt380 == -1) {
                        spawn.unlink();
                    } else if (spawn.anInt693 == spawn.anInt380 && spawn.anInt112 == spawn.orientation && spawn.anInt375 == spawn.anInt564) {
                        spawn.unlink();
                    }
                }
            }
        }

    }

    public static void dropConnection() {
        if (logoutTimer > 0) {
            DynamicObject.gc();
        } else {
            gameStateEvent.dropConnection();
            setGameState(40);
            WorldMapGroundDecorType2.aConnection299 = netWriter.unwrap();
            netWriter.kill();
        }
    }

    public static void processPlayers() {
        int var0 = GPI.playerCount;
        int[] var1 = GPI.playerIndices;

        for (int var2 = 0; var2 < var0; ++var2) {
            PlayerEntity var3 = players[var1[var2]];
            if (var3 != null) {
                PathingEntity.update(var3);
            }
        }

    }

    public static void processNpcs() {
        for (int var0 = 0; var0 < npcCount; ++var0) {
            int var1 = npcIndices[var0];
            NpcEntity var2 = npcs[var1];
            if (var2 != null) {
                PathingEntity.update(var2);
            }
        }

    }

    public static int isResizable() {
        return resizableMode ? 2 : 1;
    }

    public static void setGameState(int var0) {
        if (var0 != gameState) {
            if (gameState == 0) {
                instance.method929();
            }

            if (var0 == 20 || var0 == 40 || var0 == 45) {
                loginStage = 0;
                loginStageCycles = 0;
                loginProcess = 0;
                gameStateEvent.updateGameState(var0);
                if (var0 != 20) {
                    PlayerAccountType.method918(false);
                }
            }

            if (var0 != 20 && var0 != 40 && WorldMapGroundDecorType2.aConnection299 != null) {
                WorldMapGroundDecorType2.aConnection299.stop();
                WorldMapGroundDecorType2.aConnection299 = null;
            }

            if (gameState == 25) {
                loadingDrawState = 0;
                loadingIndicator1 = 0;
                loadingIndicator2 = 1;
                loadingIndicator3 = 0;
                loadingIndicator4 = 1;
            }

            if (var0 != 5 && var0 != 10) {
                if (var0 == 20) {
                    Login.prepare(Archive.huffman, Archive.sprites, true, gameState == 11 ? 4 : 0);
                } else if (var0 == 11) {
                    Login.prepare(Archive.huffman, Archive.sprites, false, 4);
                } else if (Login.clear) {
                    Statics49.titleboxSprite = null;
                    GraphicsProvider.titlebuttonSprite = null;
                    Login.runeSprites = null;
                    Login.leftTitleSprite = null;
                    Login.rightTitleSprite = null;
                    Login.logoSprite = null;
                    AssociateComparator_Sub2.titleMuteSprites = null;
                    Login.aDoublyNode_Sub24_Sub4_1148 = null;
                    WorldMapAreaChunk_Sub2.aDoublyNode_Sub24_Sub4_288 = null;
                    LoadedArchive.aSpriteArray429 = null;
                    Login.worldSelectorFlags = null;
                    StockMarketOfferQuantityComparator.aDoublyNode_Sub24_Sub4Array653 = null;
                    AnimationFrameGroup.aDoublyNode_Sub24_Sub4Array801 = null;
                    Login.slbutton = null;
                    AsyncOutputStream.loginScreenEffect.destroy();
                    AudioSystem.state = 1;
                    AudioSystem.tracks = null;
                    AudioSystem.trackGroup = -1;
                    AudioSystem.trackFile = -1;
                    AudioSystem.volume = 0;
                    AudioSystem.aBoolean620 = false;
                    AudioSystem.pcmSampleLength = 2;
                    Js5Worker.kill(true);
                    Login.clear = false;
                }
            } else {
                Login.prepare(Archive.huffman, Archive.sprites, true, 0);
            }

            gameState = var0;
        }
    }

    public static void sendError(String var0, Throwable var1) {
        if (var1 != null) {
            var1.printStackTrace();
        } else {
            try {
                String var2 = "";
                if (var1 != null) {
                    var2 = WorldMapController.method143(var1);
                }

                if (var0 != null) {
                    if (var1 != null) {
                        var2 = var2 + " | ";
                    }

                    var2 = var2 + var0;
                }

                System.out.println("Error: " + var2);
                var2 = var2.replace(':', '.');
                var2 = var2.replace('@', '_');
                var2 = var2.replace('&', '_');
                var2 = var2.replace('#', '_');
                if (Jagexception.applet == null) {
                    return;
                }

                URL var3 = new URL(Jagexception.applet.getCodeBase(), "clienterror.ws?c=" + build + "&u=" + Jagexception.localPlayerName + "&v1=" + OldConnectionTaskProcessor.javaVendor + "&v2=" + OldConnectionTaskProcessor.javaVerson + "&ct=" + clientParameter4 + "&e=" + var2);
                DataInputStream var4 = new DataInputStream(var3.openStream());
                var4.read();
                var4.close();
            } catch (Exception ignored) {
            }

        }
    }

    public static WorldMap getWorldMap() {
        return worldMap;
    }

    public static int method888(int var0) {
        return (int) ((Math.log(var0) / Statics46.aDouble437 - 7.0D) * 256.0D);
    }

    public static void method884(int var0, int var1, int var2) {
        if (anInt901 != 0 && var1 != 0 && audioEffectCount < 50) {
            anIntArray899[audioEffectCount] = var0;
            anIntArray905[audioEffectCount] = var1;
            anIntArray906[audioEffectCount] = var2;
            audioEffects[audioEffectCount] = null;
            anIntArray902[audioEffectCount] = 0;
            ++audioEffectCount;
        }

    }

    public static void method181(AnimationSequence var0, int var1, int var2, int var3) {
        if (audioEffectCount < 50 && anInt897 != 0) {
            if (var0.anIntArray691 != null && var1 < var0.anIntArray691.length) {
                int var4 = var0.anIntArray691[var1];
                if (var4 != 0) {
                    int var5 = var4 >> 8;
                    int var6 = var4 >> 4 & 7;
                    int var7 = var4 & 15;
                    anIntArray899[audioEffectCount] = var5;
                    anIntArray905[audioEffectCount] = var6;
                    anIntArray906[audioEffectCount] = 0;
                    audioEffects[audioEffectCount] = null;
                    int var8 = (var2 - 64) / 128;
                    int var9 = (var3 - 64) / 128;
                    anIntArray902[audioEffectCount] = var7 + (var9 << 8) + (var8 << 16);
                    ++audioEffectCount;
                }
            }
        }
    }

    public static void gc() {
        TileOverlay.cache.clear();
        TileUnderlay.cache.clear();
        IdentikitDefinition.cache.clear();
        ObjectDefinition.cache.clear();
        ObjectDefinition.rawmodels.clear();
        ObjectDefinition.aReferenceCache1513.clear();
        ObjectDefinition.models.clear();
        NpcDefinition.cache.clear();
        NpcDefinition.models.clear();
        ItemDefinition.clear();
        AnimationSequence.cache.clear();
        AnimationSequence.frames.clear();
        EffectAnimation.clear();
        Varbit.cache.clear();
        AudioOverride.clear();
        StockMarketEvent.clear();
        HealthBarDefinition.cache.clear();
        HealthBarDefinition.sprites.clear();
        StructDefinition.cache.clear();
        ParameterDefinition.clear();
        WorldMapFunction.cache.clear();
        Class223.clear();
        InterfaceComponent.sprites.clear();
        InterfaceComponent.models.clear();
        InterfaceComponent.fonts.clear();
        InterfaceComponent.specialSprites.clear();
        ((DefaultMaterialProvider) JagGraphics3D.materialProvider).clear();
        ClientScript.cache.clear();
        Archive.skeletons.clear();
        Archive.skins.clear();
        Archive.interfaces.clear();
        Archive.audioEffects.clear();
        Archive.landscape.clear();
        Archive.audioTracks.clear();
        Archive.models.clear();
        Archive.sprites.clear();
        Archive.textures.clear();
        Archive.huffman.clear();
        Archive.audioTracks2.clear();
        Archive.cs2.clear();
    }

    public static String method9() {
        String var0 = "";

        ChatLine var2;
        for (Iterator<ChatLine> var1 = Statics53.CHAT_LINE_TABLE.iterator(); var1.hasNext(); var0 = var0 + var2.channel + ':' + var2.message + '\n') {
            var2 = var1.next();
        }

        return var0;
    }

    public static void processGameBoundsPacket() {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.GAME_BOUNDS, netWriter.encryptor);
        packet.buffer.p1(isResizable());
        packet.buffer.p2(canvasWidth);
        packet.buffer.p2(canvasHeight);
        netWriter.writeLater(packet);
    }

    public static String getColorTags(int color) {
        return "<col=" + Integer.toHexString(color) + ">";
    }

    public void method741() {
        if (gameState != 1000) {
            boolean var1 = ResourceRequest.processResources();
            if (!var1) {
                this.method738();
            }

        }
    }

    public NamePair getNamePair() {
        return PlayerEntity.local != null ? PlayerEntity.local.namePair : null;
    }

    public void method733() {
        int var1 = canvasWidth;
        int var2 = canvasHeight;
        if (this.width_ < var1) {
        }

        if (this.height_ < var2) {
        }

        if (preferences != null) {
            try {
                client var3 = instance;
                Object[] var4 = new Object[]{isResizable()};
                JSObject.getWindow(var3).call("resize", var4);
            } catch (Throwable ignored) {
            }
        }

    }

    protected void releaseThreads() {
        if (varcs.persist()) {
            varcs.update();
        }

        if (mouseRecorder != null) {
            mouseRecorder.enabled = false;
        }

        mouseRecorder = null;
        netWriter.stop();
        Keyboard.destroy();
        Mouse.destroy();
        MouseWheel.provider = null;
        if (AudioSystem.anAudioSystem1236 != null) {
            AudioSystem.anAudioSystem1236.destroy();
        }

        if (AudioSystem.anAudioSystem599 != null) {
            AudioSystem.anAudioSystem599.destroy();
        }

        Js5Worker.destroy();
        CacheRequestWorker.release();
        if (urlRequestProcessor != null) {
            urlRequestProcessor.release();
            urlRequestProcessor = null;
        }

        BufferedFile.releaseAll();
    }

    protected void method745() {
        int[] var1 = new int[]{20, 260, 10000};
        int[] var2 = new int[]{1000, 100, 500};
        if (var1 != null && var2 != null) {
            Buffer.variadicSizes = var1;
            Buffer.variadicIndices = new int[var1.length];
            Buffer.variadic = new byte[var1.length][][];

            for (int var5 = 0; var5 < Buffer.variadicSizes.length; ++var5) {
                Buffer.variadic[var5] = new byte[var2[var5]][];
            }
        } else {
            Buffer.variadicSizes = null;
            Buffer.variadicIndices = null;
            Buffer.variadic = null;
        }

        HitsplatDefinition.anInt1929 = gameType == 0 ? 43594 : currentWorld + 40000;
        Bzip2Entry.anInt1579 = gameType == 0 ? 443 : currentWorld + 50000;
        NpcEntity.port = HitsplatDefinition.anInt1929;
        HAlign.aShortArray1482 = PlayerModel.Colors.aShortArray1225;
        PlayerModel.colors = PlayerModel.Colors.aShortArrayArray1226;
        PlayerModel.colors2 = PlayerModel.Colors.aShortArray1224;
        DefaultAudioSystemProvider.aShortArrayArray145 = PlayerModel.Colors.aShortArrayArray1223;
        urlRequestProcessor = new URLRequestProcessor();
        Keyboard.method921();

        java.awt.Canvas canvas = super.canvas;
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.addKeyListener(Keyboard.instance);
        canvas.addFocusListener(Keyboard.instance);
        Statics48.method95(canvas);

        MouseWheel.provider = this.method943();
        WorldMapTileDecor_Sub2.aResourceCache649 = new ResourceCache(255, BufferedFile.dataFile, BufferedFile.indexFile, 500000);
        preferences = ItemDefinition.method529();
        this.updateClipboard();
        String var4 = WorldMapController.aString264;
        GameShell.applet = this;
        if (var4 != null) {
            WorldMapController.aString325 = var4;
        }

        if (gameType != 0) {
            displayFps = true;
        }

        AssociateComparatorByMyWorld.method603(preferences.resizable);
        relationshipSystem = new RelationshipSystem(PreciseWorldMapAreaChunk.nameLengthParameter);
    }

    public void method734(boolean var1) {
        InterfaceComponent.method1112(rootInterfaceIndex, canvasWidth, canvasHeight, var1);
    }

    public void js5error(int var1) {
        Login.js5task = null;
        Login.js5connection = null;
        js5State = 0;
        if (NpcEntity.port == HitsplatDefinition.anInt1929) {
            NpcEntity.port = Bzip2Entry.anInt1579;
        } else {
            NpcEntity.port = HitsplatDefinition.anInt1929;
        }

        ++js5ErrorCount;
        if (js5ErrorCount >= 2 && (var1 == 7 || var1 == 9)) {
            if (gameState <= 5) {
                this.error("js5connect_full");
                gameState = 1000;
            } else {
                js5Cycles = 3000;
            }
        } else if (js5ErrorCount >= 2 && var1 == 6) {
            this.error("js5connect_outofdate");
            gameState = 1000;
        } else if (js5ErrorCount >= 4) {
            if (gameState <= 5) {
                this.error("js5connect");
                gameState = 1000;
            } else {
                js5Cycles = 3000;
            }
        }

    }

    public boolean method731() {
        int last = ContextMenu.getLastRowIndex();
        return (varpControlledInt1 == 1 && ContextMenu.rowCount > 2 || ContextMenu.isComponentAction2(last)) && !ContextMenu.prioritizedActions[last];
    }

    protected void onExit() {

    }

    private boolean readIncomingPacket(Connection connection, BitBuffer incoming) throws IOException {
        if (netWriter.alive) {
            if (!connection.available(1)) {
                return false;
            }

            connection.read(netWriter.inbuffer.payload, 0, 1);
            netWriter.idleReadTicks = 0;
            netWriter.alive = false;
        }

        incoming.pos = 0;
        if (incoming.nextIsSmart()) {
            if (!connection.available(1)) {
                return false;
            }

            connection.read(netWriter.inbuffer.payload, 1, 1);
            netWriter.idleReadTicks = 0;
        }

        netWriter.alive = true;
        ServerProt[] incomingPacketTypes = ServerProt.values();
        int packetIndex = incoming.gesmart();
        if (packetIndex < 0 || packetIndex >= incomingPacketTypes.length) {
            throw new IOException(packetIndex + " " + incoming.pos);
        }

        netWriter.currentIncomingPacket = incomingPacketTypes[packetIndex];
        netWriter.incomingPacketSize = netWriter.currentIncomingPacket.size;
        return true;
    }

    public boolean processIncomingPacket() {
        Connection connection = netWriter.unwrap();
        BitBuffer incoming = netWriter.inbuffer;
        if (connection == null) {
            return false;
        }

        try {
            if (netWriter.currentIncomingPacket == null && !readIncomingPacket(connection, incoming)) {
                return false;
            }

            if (netWriter.incomingPacketSize == -1) {
                if (!connection.available(1)) {
                    return false;
                }

                netWriter.unwrap().read(incoming.payload, 0, 1);
                netWriter.incomingPacketSize = incoming.payload[0] & 0xff;
            }

            if (netWriter.incomingPacketSize == -2) {
                if (!connection.available(2)) {
                    return false;
                }

                netWriter.unwrap().read(incoming.payload, 0, 2);
                incoming.pos = 0;
                netWriter.incomingPacketSize = incoming.g2();
            }

            if (!connection.available(netWriter.incomingPacketSize)) {
                return false;
            }

            incoming.pos = 0;
            connection.read(incoming.payload, 0, netWriter.incomingPacketSize);
            netWriter.idleReadTicks = 0;
            gameStateEvent.updateTime();

            netWriter.thirdLastIncomingPacket = netWriter.secondLastIncomingPacket;
            netWriter.secondLastIncomingPacket = netWriter.lastIncomingPacket;
            netWriter.lastIncomingPacket = netWriter.currentIncomingPacket;

            if (ServerProt.MAP_PROJANIM == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.MAP_PROJANIM);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_COMPONENT_CONFIG == netWriter.currentIncomingPacket) {
                return serverProtHandler.processComponentConfigUpdate(incoming);
            }

            if (ServerProt.SET_OCULUSORB_MODE == netWriter.currentIncomingPacket) {
                return serverProtHandler.processOculusOrbModeUpdate(incoming);
            }

            if (ServerProt.AN_SERVER_PROT_175 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1070();
                int var5 = incoming.method1070();
                int var7 = incoming.method1015();
                InterfaceComponent var47 = InterfaceComponent.lookup(var7);
                if (var5 != var47.xMargin || var6 != var47.yMargin || var47.xLayout != 0 || var47.yLayout != 0) {
                    var47.xMargin = var5;
                    var47.yMargin = var6;
                    var47.xLayout = 0;
                    var47.yLayout = 0;
                    InterfaceComponent.repaint(var47);
                    this.method742(var47);
                    if (var47.type == 0) {
                        GameShell.method925(interfaces[var7 >> 16], var47, false);
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.LOC_DEL == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.LOC_DEL);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_217 == netWriter.currentIncomingPacket) {
                rebootTimer = incoming.readLEUShortA() * 30;
                anInt1074 = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_STOCKMARKET == netWriter.currentIncomingPacket) {
                boolean var42 = incoming.g1() == 1;
                if (var42) {
                    StockMarketOffer.ageAdjustment = Clock.now() - incoming.g8();
                    StockMarketOffer.mediator = new StockMarketMediator(incoming);
                } else {
                    StockMarketOffer.mediator = null;
                }

                stockMarketUpdateCycle = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_230 == netWriter.currentIncomingPacket) {
                GameShell.method1417(incoming.gstr());
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_ITEMTABLE == netWriter.currentIncomingPacket) {
                return serverProtHandler.processItemTableUpdate(incoming);
            }

            if (ServerProt.LOCK_CAMERA == netWriter.currentIncomingPacket) {
                return serverProtHandler.processLockCameraRequest(incoming);
            }

            if (ServerProt.MAP_ANIM == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.MAP_ANIM);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.GARBAGE_COLLECTOR == netWriter.currentIncomingPacket) {
                return serverProtHandler.processGcInfoRequest(incoming);
            }

            if (ServerProt.AN_SERVER_PROT_224 == netWriter.currentIncomingPacket) {
                SubInterface.process();
                weight = incoming.g2b();
                anInt1074 = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_203 == netWriter.currentIncomingPacket) {
                int var6 = incoming.g4();
                if (var6 != anInt1002) {
                    anInt1002 = var6;
                    Camera.setOculusOrbOnLocalPlayer();
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_250 == netWriter.currentIncomingPacket) {
                if (friendsChatSystem != null) {
                    friendsChatSystem.decodeUpdate(incoming);
                }

                ZoneProt.method865();
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_211 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1015();
                int var5 = incoming.g4();
                SubInterface var53 = subInterfaces.lookup(var5);
                SubInterface var13 = subInterfaces.lookup(var6);
                if (var13 != null) {
                    InterfaceComponent.close(var13, var53 == null || var13.id != var53.id);
                }

                if (var53 != null) {
                    var53.unlink();
                    subInterfaces.put(var53, var6);
                }

                InterfaceComponent component = InterfaceComponent.lookup(var5);
                if (component != null) {
                    InterfaceComponent.repaint(component);
                }

                component = InterfaceComponent.lookup(var6);
                if (component != null) {
                    InterfaceComponent.repaint(component);
                    GameShell.method925(interfaces[component.uid >>> 16], component, true);
                }

                if (rootInterfaceIndex != -1) {
                    InterfaceComponent.method118(rootInterfaceIndex, 1);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_210 == netWriter.currentIncomingPacket) {
                relationshipSystem.decodeFriends(incoming, netWriter.incomingPacketSize);
                anInt1065 = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_238 == netWriter.currentIncomingPacket) {
                String var38 = incoming.gstr();
                long var18 = incoming.g8();
                int var20 = incoming.g2();
                int var10 = incoming.g3();
                PlayerAccountType accountType = (PlayerAccountType) EnumType.getByOrdinal(PlayerAccountType.getValues(), incoming.g1());
                int var22 = var10 + (var20 << 32);
                boolean ignored = false;

                for (int i = 0; i < 100; ++i) {
                    if (aLongArray896[i] == var22) {
                        ignored = true;
                        break;
                    }
                }

                if (accountType.notJagex && relationshipSystem.isIgnoring(new NamePair(var38, PreciseWorldMapAreaChunk.nameLengthParameter))) {
                    ignored = true;
                }

                if (!ignored && anInt1014 == 0) {
                    aLongArray896[anInt891] = var22;
                    anInt891 = (anInt891 + 1) % 100;
                    String var26 = BaseFont.method1166(OldConnection.method714(DefaultRouteStrategy.method294(incoming)));
                    if (accountType.icon != -1) {
                        ChatHistory.messageReceived(9, ContextMenu.addImgTags(accountType.icon) + var38, var26, Base37.decode(var18));
                    } else {
                        ChatHistory.messageReceived(9, var38, var26, Base37.decode(var18));
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_RANDOM == netWriter.currentIncomingPacket) {
                incoming.pos += 28;
                if (incoming.matchCrcs()) {
                    ResourceCache.method1489(incoming, incoming.pos - 28);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_GRAND_EXCHANGE == netWriter.currentIncomingPacket) {
                return serverProtHandler.processStockMarketUpdate(incoming);
            }

            if (ServerProt.AN_SERVER_PROT_181 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1067();
                int var5 = incoming.method1011();
                InterfaceComponent var14 = InterfaceComponent.lookup(var5);
                if (var6 != var14.animation || var6 == -1) {
                    var14.animation = var6;
                    var14.animationFrame = 0;
                    var14.animationFrameCycle = 0;
                    InterfaceComponent.repaint(var14);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_TO_ITEM_MODEL == netWriter.currentIncomingPacket) {
                int var6 = incoming.g4();
                int var5 = incoming.method1015();
                int var7 = incoming.readLEUShortA();
                if (var7 == 65535) {
                    var7 = -1;
                }

                InterfaceComponent var47 = InterfaceComponent.lookup(var6);
                ItemDefinition var52;
                if (!var47.format) {
                    if (var7 == -1) {
                        var47.modelType = 0;
                        netWriter.currentIncomingPacket = null;
                        return true;
                    }

                    var52 = ItemDefinition.get(var7);
                    var47.modelType = 4;
                    var47.modelId = var7;
                    var47.xRotation = var52.spritePitch;
                    var47.zRotation = var52.spriteRoll;
                    var47.modelZoom = var52.spriteScale * 100 / var5;
                } else {
                    var47.itemId = var7;
                    var47.itemStackSize = var5;
                    var52 = ItemDefinition.get(var7);
                    var47.xRotation = var52.spritePitch;
                    var47.zRotation = var52.spriteRoll;
                    var47.yRotation = var52.spriteYaw;
                    var47.modelOffsetX = var52.spriteTranslateX;
                    var47.modelOffsetY = var52.spriteTranslateY;
                    var47.modelZoom = var52.spriteScale;
                    if (var52.stackable == 1) {
                        var47.itemStackSizeMode = 1;
                    } else {
                        var47.itemStackSizeMode = 2;
                    }

                    if (var47.scaleZ > 0) {
                        var47.modelZoom = var47.modelZoom * 32 / var47.scaleZ;
                    } else if (var47.baseWidth > 0) {
                        var47.modelZoom = var47.modelZoom * 32 / var47.baseWidth;
                    }

                }
                InterfaceComponent.repaint(var47);

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_MODEL_TYPE2 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1011();
                int var5 = incoming.method1055();
                InterfaceComponent var14 = InterfaceComponent.lookup(var6);
                if (var14.modelType != 2 || var5 != var14.modelId) {
                    var14.modelType = 2;
                    var14.modelId = var5;
                    InterfaceComponent.repaint(var14);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.LOC_ANIM == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.LOC_ANIM);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_180 == netWriter.currentIncomingPacket) {
                String var38 = incoming.gstr();
                long var18 = incoming.g2();
                int var20 = incoming.g3();
                PlayerAccountType var27 = (PlayerAccountType) EnumType.getByOrdinal(PlayerAccountType.getValues(), incoming.g1());
                long var28 = (var18 << 32) + var20;
                boolean var30 = false;

                for (int var31 = 0; var31 < 100; ++var31) {
                    if (aLongArray896[var31] == var28) {
                        var30 = true;
                        break;
                    }
                }

                if (relationshipSystem.isIgnoring(new NamePair(var38, PreciseWorldMapAreaChunk.nameLengthParameter))) {
                    var30 = true;
                }

                if (!var30 && anInt1014 == 0) {
                    aLongArray896[anInt891] = var28;
                    anInt891 = (anInt891 + 1) % 100;
                    String var32 = BaseFont.method1166(OldConnection.method714(DefaultRouteStrategy.method294(incoming)));
                    byte var24;
                    if (var27.staff) {
                        var24 = 7;
                    } else {
                        var24 = 3;
                    }

                    if (var27.icon != -1) {
                        ChatHistory.messageReceived(var24, ContextMenu.addImgTags(var27.icon) + var38, var32);
                    } else {
                        ChatHistory.messageReceived(var24, var38, var32);
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_258 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1015();
                InterfaceComponent component = InterfaceComponent.lookup(var6);

                for (int i = 0; i < component.itemIds.length; ++i) {
                    component.itemIds[i] = -1;
                    component.itemIds[i] = 0;
                }

                InterfaceComponent.repaint(component);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_184 == netWriter.currentIncomingPacket) {
                NpcEntity.update(true, incoming);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_VARP2 == netWriter.currentIncomingPacket) {
                int value = incoming.method1019();
                int index = incoming.readLEUShortA();
                Vars.baseValues[index] = value;
                if (Vars.values[index] != value) {
                    Vars.values[index] = value;
                }

                OldConnection.method712(index);
                anIntArray1076[++anInt1064 - 1 & 31] = index;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_REGION_CHUNK == netWriter.currentIncomingPacket) {
                SceneGraph.regionChunkX = incoming.ig1();
                SceneGraph.regionChunkY = incoming.g1();
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_192 == netWriter.currentIncomingPacket) {
                int index = incoming.g1();
                int var5 = incoming.g1();
                int var7 = incoming.g1();
                int var8 = incoming.g1();
                aBooleanArray919[index] = true;
                anIntArray917[index] = var5;
                anIntArray918[index] = var7;
                anIntArray914[index] = var8;
                anIntArray916[index] = 0;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_PLAYER_ACTION == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1074();
                StringBuilder action = new StringBuilder(incoming.gstr());
                int var7 = incoming.method1056();
                if (var7 >= 1 && var7 <= 8) {
                    if (action.toString().equalsIgnoreCase("null")) {
                        action = null;
                    }

                    if (action != null) {
                        playerActions[var7 - 1] = action.toString();
                        playerActionPriorities[var7 - 1] = var6 == 0;
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_WORLD == netWriter.currentIncomingPacket) {
                Server server = new Server();
                server.domain = incoming.gstr();
                server.id = incoming.g2();
                int mask = incoming.g4();
                server.mask = mask;
                setGameState(45);
                connection.stop();
                SerializableString.setWorld(server);
                netWriter.currentIncomingPacket = null;
                return false;
            }

            if (ServerProt.AN_SERVER_PROT_200 == netWriter.currentIncomingPacket) {
                relationshipSystem.setLoading();
                anInt1065 = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.DROP_CONNECTION == netWriter.currentIncomingPacket) {
                int reason = incoming.g1();
                AnimationSequence.method881(reason);
                netWriter.currentIncomingPacket = null;
                return false;
            }

            if (ServerProt.UNLOCK_CAMERA == netWriter.currentIncomingPacket) {
                cameraLocked = false;

                for (int i = 0; i < 5; ++i) {
                    aBooleanArray919[i] = false;
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.PICKABLE_ADD == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.PICKABLE_ADD);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_197 == netWriter.currentIncomingPacket) {
                if (netWriter.incomingPacketSize == 0) {
                    friendsChatSystem = null;
                } else {
                    if (friendsChatSystem == null) {
                        friendsChatSystem = new FriendsChatSystem(PreciseWorldMapAreaChunk.nameLengthParameter, instance);
                    }

                    friendsChatSystem.decode(incoming);
                }

                ZoneProt.method865();
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_HINT_ARROW == netWriter.currentIncomingPacket) {
                HintArrow.type = incoming.g1();
                if (HintArrow.type == 1) {
                    HintArrow.npc = incoming.g2();
                }

                if (HintArrow.type >= 2 && HintArrow.type <= 6) {
                    if (HintArrow.type == 2) {
                        HintArrow.insetX = 64;
                        HintArrow.insetY = 4096;
                    }

                    if (HintArrow.type == 3) {
                        HintArrow.insetX = 0;
                        HintArrow.insetY = 4096;
                    }

                    if (HintArrow.type == 4) {
                        HintArrow.insetX = 128;
                        HintArrow.insetY = 4096;
                    }

                    if (HintArrow.type == 5) {
                        HintArrow.insetX = 64;
                        HintArrow.insetY = 0;
                    }

                    if (HintArrow.type == 6) {
                        HintArrow.insetX = 64;
                        HintArrow.insetY = 8192;
                    }

                    HintArrow.type = 2;
                    HintArrow.x = incoming.g2();
                    HintArrow.y = incoming.g2();
                    HintArrow.z = incoming.g1() * 2;
                }

                if (HintArrow.type == 10) {
                    HintArrow.player = incoming.g2();
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_209 == netWriter.currentIncomingPacket) {
                int key = incoming.method1055();
                Inventory.delete(key);
                inventories[++anInt1078 - 1 & 31] = key & 32767;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_236 == netWriter.currentIncomingPacket) {
                int rootInterfaceIndex = incoming.method1055();
                client.rootInterfaceIndex = rootInterfaceIndex;
                this.method734(false);
                InterfaceComponent.loadAnimable(rootInterfaceIndex);
                InterfaceComponent.loadAndInitialize(client.rootInterfaceIndex);

                for (int i = 0; i < 100; ++i) {
                    renderedComponents[i] = true;
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.ADD_CHAT_MESSAGE == netWriter.currentIncomingPacket) {
                int type = incoming.gsmarts();
                boolean sentByPlayer = incoming.g1() == 1;
                String name = "";
                boolean ignored = false;
                if (sentByPlayer) {
                    name = incoming.gstr();
                    if (relationshipSystem.isIgnoring(new NamePair(name, PreciseWorldMapAreaChunk.nameLengthParameter))) {
                        ignored = true;
                    }
                }

                String channel = incoming.gstr();
                if (!ignored) {
                    ChatHistory.messageReceived(type, name, channel);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_MAP_REGION == netWriter.currentIncomingPacket) {
                PlayerAccountType.onSceneXTEAKeyChange(false, netWriter.inbuffer);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.OPEN_BROWSER_URL == netWriter.currentIncomingPacket) {
                byte[] data = new byte[netWriter.incomingPacketSize];
                incoming.ge(data, 0, data.length);
                Buffer buffer = new Buffer(data);
                String url = buffer.gstr();
                CacheRequestWorker.openURL(url, true, false);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_HIDDEN == netWriter.currentIncomingPacket) {
                boolean hidden = incoming.g1() == 1;
                int uid = incoming.method1015();
                InterfaceComponent component = InterfaceComponent.lookup(uid);
                if (hidden != component.explicitlyHidden) {
                    component.explicitlyHidden = hidden;
                    InterfaceComponent.repaint(component);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_ITEM_TABLE == netWriter.currentIncomingPacket) {
                int uid = incoming.g4();
                int key = incoming.g2();
                if (uid < -70000) {
                    key += 32768;
                }

                InterfaceComponent component = uid >= 0 ? InterfaceComponent.lookup(uid) : null;

                while (incoming.pos < netWriter.incomingPacketSize) {
                    int var8 = incoming.gsmarts();
                    int var9 = incoming.g2();
                    int var15 = 0;
                    if (var9 != 0) {
                        var15 = incoming.g1();
                        if (var15 == 255) {
                            var15 = incoming.g4();
                        }
                    }

                    if (component != null && var8 >= 0 && var8 < component.itemIds.length) {
                        component.itemIds[var8] = var9;
                        component.itemStackSizes[var8] = var15;
                    }
                    Inventory.addItem(key, var8, var9 - 1, var15);
                }

                if (component != null) {
                    InterfaceComponent.repaint(component);
                }

                SubInterface.process();
                inventories[++anInt1078 - 1 & 31] = key & 32767;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_MODEL_TYPE1 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1019();
                int var5 = incoming.method1055();
                InterfaceComponent component = InterfaceComponent.lookup(var6);
                if (component.modelType != 1 || var5 != component.modelId) {
                    component.modelType = 1;
                    component.modelId = var5;
                    InterfaceComponent.repaint(component);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_FOREGROUND == netWriter.currentIncomingPacket) {
                int var6 = incoming.g2();
                int var5 = incoming.method1015();
                int var7 = var6 >> 10 & 31;
                int var8 = var6 >> 5 & 31;
                int var9 = var6 & 31;
                int var15 = (var8 << 11) + (var7 << 19) + (var9 << 3);
                InterfaceComponent var59 = InterfaceComponent.lookup(var5);
                if (var15 != var59.foreground) {
                    var59.foreground = var15;
                    InterfaceComponent.repaint(var59);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_ZOOM == netWriter.currentIncomingPacket) {
                int var6 = incoming.g2();
                int var5 = incoming.method1055();
                int var7 = incoming.method1060();
                int var8 = incoming.g4();
                InterfaceComponent var17 = InterfaceComponent.lookup(var8);
                if (var5 != var17.xRotation || var7 != var17.zRotation || var6 != var17.modelZoom) {
                    var17.xRotation = var5;
                    var17.zRotation = var7;
                    var17.modelZoom = var6;
                    InterfaceComponent.repaint(var17);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_CHAT_MODE == netWriter.currentIncomingPacket) {
                tradeChatMode = incoming.method1074();
                publicChatMode = incoming.method1056();
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_STATS == netWriter.currentIncomingPacket) {
                SubInterface.process();
                int experience = incoming.method1019();
                int index = incoming.method1056();
                int level = incoming.method1074();
                experiences[index] = experience;
                currentLevels[index] = level;
                levels[index] = 1;

                for (int i = 0; i < 98; ++i) {
                    if (experience >= Skills.EXP_TABLE[i]) {
                        levels[index] = i + 2;
                    }
                }

                anIntArray1079[++anInt1063 - 1 & 31] = index;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_MAP_STATE == netWriter.currentIncomingPacket) {
                mapState = incoming.g1();
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_245 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1055();
                int var5 = incoming.method1011();
                int var7 = incoming.method1060();
                InterfaceComponent var47 = InterfaceComponent.lookup(var5);
                var47.rotationKey = var6 + (var7 << 16);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.FIRE_SCRIPT_EVENT == netWriter.currentIncomingPacket) {
                String str = incoming.gstr();
                Object[] args = new Object[str.length() + 1];

                for (int i = str.length() - 1; i >= 0; --i) {
                    if (str.charAt(i) == 's') {
                        args[i + 1] = incoming.gstr();
                    } else {
                        args[i + 1] = incoming.g4();
                    }
                }

                args[0] = incoming.g4(); //id
                ScriptEvent evt = new ScriptEvent();
                evt.args = args;
                ScriptEvent.fire(evt);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_219 == netWriter.currentIncomingPacket) {
                cameraLocked = true;
                GameShell.anInt1288 = incoming.g1() * 16384;
                SecureRandomService.anInt458 = incoming.g1() * 128;
                Statics52.anInt499 = incoming.g2();
                Statics54.anInt627 = incoming.g1();
                ScriptEvent.anInt1806 = incoming.g1();
                if (ScriptEvent.anInt1806 >= 100) {
                    int var6 = GameShell.anInt1288 * 128 + 64;
                    int var5 = SecureRandomService.anInt458 * 16384 + 64;
                    int var7 = SceneGraph.getTileHeight(var6, var5, SceneGraph.floorLevel) - Statics52.anInt499;
                    int var8 = var6 - Camera.x;
                    int var9 = var7 - Camera.z;
                    int var15 = var5 - Camera.y;
                    int var16 = (int) Math.sqrt(var15 * var15 + var8 * var8);
                    Camera.pitch = (int) (Math.atan2(var9, var16) * 325.949D) & 2047;
                    Camera.yaw = (int) (Math.atan2(var8, var15) * -325.949D) & 2047;
                    if (Camera.pitch < 128) {
                        Camera.pitch = 128;
                    }

                    if (Camera.pitch > 383) {
                        Camera.pitch = 383;
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SOUND_AREA == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.SOUND_AREA);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_240 == netWriter.currentIncomingPacket) {
                int var6 = incoming.g4();
                int var5 = incoming.g1();
                int var7 = incoming.g2();
                SubInterface var13 = subInterfaces.lookup(var6);
                if (var13 != null) {
                    InterfaceComponent.close(var13, var7 != var13.id);
                }

                SubInterface.create(var6, var7, var5);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.LOC_ADD_CHANGE == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.LOC_ADD_CHANGE);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_227 == netWriter.currentIncomingPacket) {
                int var6 = incoming.g4();
                SubInterface var58 = subInterfaces.lookup(var6);
                if (var58 != null) {
                    InterfaceComponent.close(var58, true);
                }

                if (pleaseWaitComponent != null) {
                    InterfaceComponent.repaint(pleaseWaitComponent);
                    pleaseWaitComponent = null;
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_VARP == netWriter.currentIncomingPacket) {
                int index = incoming.method1055();
                byte value = incoming.g1b();
                Vars.baseValues[index] = value;
                if (Vars.values[index] != value) {
                    Vars.values[index] = value;
                }

                OldConnection.method712(index);
                anIntArray1076[++anInt1064 - 1 & 31] = index;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_182 == netWriter.currentIncomingPacket) {
                SubInterface.process();
                energy = incoming.g1();
                anInt1074 = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_COMPONENT_MODEL_TYPE3 == netWriter.currentIncomingPacket) {
                int uid = incoming.method1015();
                InterfaceComponent component = InterfaceComponent.lookup(uid);
                component.modelType = 3;
                component.modelId = PlayerEntity.local.model.hash();
                InterfaceComponent.repaint(component);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.RESET_REGION_CHUNK == netWriter.currentIncomingPacket) {
                SceneGraph.regionChunkY = incoming.method1074();
                SceneGraph.regionChunkX = incoming.ig1();

                for (int x = SceneGraph.regionChunkX; x < SceneGraph.regionChunkX + 8; ++x) {
                    for (int y = SceneGraph.regionChunkY; y < SceneGraph.regionChunkY + 8; ++y) {
                        if (pickables[SceneGraph.floorLevel][x][y] != null) {
                            pickables[SceneGraph.floorLevel][x][y] = null;
                            Pickable.refresh(x, y);
                        }
                    }
                }

                for (PendingSpawn spawn = pendingSpawns.head(); spawn != null; spawn = pendingSpawns.next()) {
                    if (spawn.sceneX >= SceneGraph.regionChunkX && spawn.sceneX < SceneGraph.regionChunkX + 8 && spawn.sceneY >= SceneGraph.regionChunkY && spawn.sceneY < SceneGraph.regionChunkY + 8 && spawn.floorLevel == SceneGraph.floorLevel) {
                        spawn.hitpoints = 0;
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.PROCESS_REFLECTION_REQUEST == netWriter.currentIncomingPacket) {
                ClassStructure.decode(incoming);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_VARPS == netWriter.currentIncomingPacket) {
                for (int i = 0; i < Vars.values.length; ++i) {
                    if (Vars.baseValues[i] != Vars.values[i]) {
                        Vars.values[i] = Vars.baseValues[i];
                        OldConnection.method712(i);
                        anIntArray1076[++anInt1064 - 1 & 31] = i;
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_225 == netWriter.currentIncomingPacket) {
                relationshipSystem.ignoreListContext.decode(incoming, netWriter.incomingPacketSize);
                RelationshipSystem.method843();
                anInt1065 = anInt1075;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_NPCS == netWriter.currentIncomingPacket) {
                NpcEntity.update(false, incoming);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_INSTANCE_REGION == netWriter.currentIncomingPacket) {
                PlayerAccountType.onSceneXTEAKeyChange(true, netWriter.inbuffer);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_COMPONENT_TEXT == netWriter.currentIncomingPacket) {
                int uid = incoming.method1015();
                StringBuilder text = new StringBuilder(incoming.gstr());
                InterfaceComponent component = InterfaceComponent.lookup(uid);
                if (!text.toString().equals(component.text)) {
                    component.text = text.toString();
                    InterfaceComponent.repaint(component);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_194 == netWriter.currentIncomingPacket) {
                int var6 = incoming.g2();
                if (var6 == 65535) {
                    var6 = -1;
                }

                AudioOverrideEffect.method795(var6);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_231 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1060();
                if (var6 == 65535) {
                    var6 = -1;
                }

                int var5 = incoming.method1017();
                ClientProt.method5(var6);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.PLAYER_LOC_TRANSMOG == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.PLAYER_LOC_TRANSMOG);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_246 == netWriter.currentIncomingPacket) {
                for (int i = 0; i < VarDefinition.count; ++i) {
                    VarDefinition var56 = VarDefinition.get(i);
                    if (var56 != null) {
                        Vars.baseValues[i] = 0;
                        Vars.values[i] = 0;
                    }
                }

                SubInterface.process();
                anInt1064 += 32;
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.PICKABLE_REMOVE == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.PICKABLE_REMOVE);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_190 == netWriter.currentIncomingPacket) {
                int var6 = incoming.pos + netWriter.incomingPacketSize;
                int var5 = incoming.g2();
                int var7 = incoming.g2();
                if (var5 != rootInterfaceIndex) {
                    rootInterfaceIndex = var5;
                    this.method734(false);
                    InterfaceComponent.loadAnimable(rootInterfaceIndex);
                    InterfaceComponent.loadAndInitialize(rootInterfaceIndex);

                    for (int i = 0; i < 100; ++i) {
                        renderedComponents[i] = true;
                    }
                }

                SubInterface lookup;
                while (var7-- > 0) {
                    int key = incoming.g4();
                    int id = incoming.g2();
                    int type = incoming.g1();
                    lookup = subInterfaces.lookup(key);
                    if (lookup != null && id != lookup.id) {
                        InterfaceComponent.close(lookup, true);
                        lookup = null;
                    }

                    if (lookup == null) {
                        lookup = SubInterface.create(key, id, type);
                    }

                    lookup.aBoolean790 = true;
                }

                for (SubInterface sub = subInterfaces.head(); sub != null; sub = subInterfaces.next()) {
                    if (sub.aBoolean790) {
                        sub.aBoolean790 = false;
                    } else {
                        InterfaceComponent.close(sub, true);
                    }
                }

                interfaceConfigs = new NodeTable<>(512);

                while (incoming.pos < var6) {
                    int var8 = incoming.g4();
                    int start = incoming.g2();
                    int end = incoming.g2();
                    int key = incoming.g4();

                    for (int i = start; i <= end; ++i) {
                        long value = (long) i + ((long) var8 << 32);
                        interfaceConfigs.put(new IntegerNode(key), value);
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_247 == netWriter.currentIncomingPacket) {
                String var38 = incoming.gstr();
                StringBuilder var33 = new StringBuilder(BaseFont.method1166(OldConnection.method714(DefaultRouteStrategy.method294(incoming))));
                ChatHistory.messageReceived(6, var38, var33.toString());
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.LOGOUT == netWriter.currentIncomingPacket) {
                DynamicObject.gc();
                netWriter.currentIncomingPacket = null;
                return false;
            }

            if (ServerProt.AN_SERVER_PROT_196 == netWriter.currentIncomingPacket) {
                int var6 = incoming.g2();
                int var5 = incoming.g1();
                int var7 = incoming.g2();
                method884(var6, var5, var7);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_249 == netWriter.currentIncomingPacket) {
                PlayerEntity.update(incoming, netWriter.incomingPacketSize);
                Archive.method485();
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.PICKABLE_COUNT == netWriter.currentIncomingPacket) {
                ZoneProt.process(ZoneProt.PICKABLE_COUNT);
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_218 == netWriter.currentIncomingPacket) {
                if (rootInterfaceIndex != -1) {
                    InterfaceComponent.method118(rootInterfaceIndex, 0);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.UPDATE_REGION_CHUNK == netWriter.currentIncomingPacket) {
                SceneGraph.regionChunkX = incoming.ig1();
                SceneGraph.regionChunkY = incoming.g1();

                while (incoming.pos < netWriter.incomingPacketSize) {
                    int var6 = incoming.g1();
                    ZoneProt var55 = ZoneProt.values()[var6];
                    ZoneProt.process(var55);
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_223 == netWriter.currentIncomingPacket) {
                for (int i = 0; i < players.length; ++i) {
                    if (players[i] != null) {
                        players[i].animation = -1;
                    }
                }

                for (int i = 0; i < npcs.length; ++i) {
                    if (npcs[i] != null) {
                        npcs[i].animation = -1;
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.SET_DESTINATION == netWriter.currentIncomingPacket) {
                destinationX = incoming.g1();
                if (destinationX == 255) {
                    destinationX = 0;
                }

                destinationY = incoming.g1();
                if (destinationY == 255) {
                    destinationY = 0;
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_234 == netWriter.currentIncomingPacket) {
                publicChatPrivacyMode = ChatModePrivacyType.valueOf(incoming.g1());
                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_177 == netWriter.currentIncomingPacket) {
                boolean var42 = incoming.gbool();
                if (var42) {
                    if (WorldMap.heatmap == null) {
                        WorldMap.heatmap = new WorldMapHeatMap();
                    }
                } else {
                    WorldMap.heatmap = null;
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            if (ServerProt.AN_SERVER_PROT_241 == netWriter.currentIncomingPacket) {
                int var6 = incoming.method1015();
                int var5 = incoming.method1060();
                InterfaceComponent var14 = InterfaceComponent.lookup(var6);
                if (var14 != null && var14.type == 0) {
                    if (var5 > var14.viewportHeight - var14.height) {
                        var5 = var14.viewportHeight - var14.height;
                    }

                    if (var5 < 0) {
                        var5 = 0;
                    }

                    if (var5 != var14.insetY) {
                        var14.insetY = var5;
                        InterfaceComponent.repaint(var14);
                    }
                }

                netWriter.currentIncomingPacket = null;
                return true;
            }

            sendError("" + (netWriter.currentIncomingPacket != null ? netWriter.currentIncomingPacket.opcode : -1) + "," + (netWriter.secondLastIncomingPacket != null ? netWriter.secondLastIncomingPacket.opcode : -1) + "," + (netWriter.thirdLastIncomingPacket != null ? netWriter.thirdLastIncomingPacket.opcode : -1) + "," + netWriter.incomingPacketSize, null);
            DynamicObject.gc();
        } catch (IOException var36) {
            dropConnection();
        } catch (Exception var37) {
            StringBuilder var33 = new StringBuilder("" + (netWriter.currentIncomingPacket != null ? netWriter.currentIncomingPacket.opcode : -1) + "," + (netWriter.secondLastIncomingPacket != null ? netWriter.secondLastIncomingPacket.opcode : -1) + "," + (netWriter.thirdLastIncomingPacket != null ? netWriter.thirdLastIncomingPacket.opcode : -1) + "," + netWriter.incomingPacketSize + "," + (PlayerEntity.local.pathXQueue[0] + baseX) + "," + (PlayerEntity.local.pathYQueue[0] + baseY) + ",");

            for (int i = 0; i < netWriter.incomingPacketSize && i < 50; ++i) {
                var33.append(incoming.payload[i]).append(",");
            }

            sendError(var33.toString(), var37);
            DynamicObject.gc();
        }

        return true;
    }

    void method738() {
        if (Js5Worker.mismatchedCrcCount >= 4) {
            this.error("js5crc");
            gameState = 1000;
        } else {
            if (Js5Worker.ioExceptionCount >= 4) {
                if (gameState <= 5) {
                    this.error("js5io");
                    gameState = 1000;
                    return;
                }

                js5Cycles = 3000;
                Js5Worker.ioExceptionCount = 3;
            }

            if (--js5Cycles + 1 <= 0) {
                try {
                    if (js5State == 0) {
                        Login.js5task = taskProcessor.create(LoginStep.currentDomain, NpcEntity.port);
                        ++js5State;
                    }

                    if (js5State == 1) {
                        if (Login.js5task.state == 2) {
                            this.js5error(-1);
                            return;
                        }

                        if (Login.js5task.state == 1) {
                            ++js5State;
                        }
                    }

                    if (js5State == 2) {
                        if (usingBufferedConnection) {
                            Socket socket = (Socket) Login.js5task.result;
                            Login.js5connection = new AsyncConnection(socket, 40000, 5000);
                        } else {
                            Login.js5connection = new OldConnection((Socket) Login.js5task.result, taskProcessor, 5000);
                        }

                        Buffer var5 = new Buffer(5);
                        var5.p1(15);
                        var5.p4(184);
                        Login.js5connection.write(var5.payload, 0, 5);
                        ++js5State;
                        VAlign.aLong1478 = Clock.now();
                    }

                    if (js5State == 3) {
                        if (Login.js5connection.available() > 0 || !usingBufferedConnection && gameState <= 5) {
                            int var3 = Login.js5connection.read();
                            if (var3 != 0) {
                                this.js5error(var3);
                                return;
                            }

                            ++js5State;
                        } else if (Clock.now() - VAlign.aLong1478 > 30000L) {
                            this.js5error(-2);
                            return;
                        }
                    }

                    if (js5State == 4) {
                        Js5Worker.start(Login.js5connection, gameState > 20);
                        Login.js5task = null;
                        Login.js5connection = null;
                        js5State = 0;
                        js5ErrorCount = 0;
                    }
                } catch (IOException var4) {
                    this.js5error(-3);
                }

            }
        }
    }

    public void openMenu(int var1, int var2) {
        ContextMenu.open(var1, var2);
        sceneGraph.method1451(SceneGraph.floorLevel, var1, var2, false);
        ContextMenu.open = true;
    }

    protected void cycle() {
        ++engineCycle;
        this.method741();

        while (true) {
            CacheRequest var2;
            synchronized (CacheRequestWorker.requests) {
                var2 = CacheRequestWorker.read.popFirst();
            }

            if (var2 == null) {
                int var4;
                try {
                    if (AudioSystem.state == 1) {
                        var4 = Statics50.aClass5_Sub6_Sub3_326.method770();
                        if (var4 > 0 && Statics50.aClass5_Sub6_Sub3_326.method684()) {
                            var4 -= AudioSystem.pcmSampleLength;
                            if (var4 < 0) {
                                var4 = 0;
                            }

                            Statics50.aClass5_Sub6_Sub3_326.method703(var4);
                        } else {
                            Statics50.aClass5_Sub6_Sub3_326.method756();
                            Statics50.aClass5_Sub6_Sub3_326.method592();
                            if (AudioSystem.tracks != null) {
                                AudioSystem.state = 2;
                            } else {
                                AudioSystem.state = 0;
                            }

                            Statics57.anAudioTrack1158 = null;
                            FriendLoginUpdate.aClass97_668 = null;
                        }
                    }
                } catch (Exception var9) {
                    var9.printStackTrace();
                    Statics50.aClass5_Sub6_Sub3_326.method756();
                    AudioSystem.state = 0;
                    Statics57.anAudioTrack1158 = null;
                    FriendLoginUpdate.aClass97_668 = null;
                    AudioSystem.tracks = null;
                }

                AudioSystem.process();
                synchronized (Keyboard.instance) {
                    Keyboard.idleTime++;
                    Keyboard.anInt157 = Keyboard.anInt162;
                    Keyboard.pressedKeysCount = 0;
                    int var5;
                    if (Keyboard.meta >= 0) {
                        while (Keyboard.meta != Keyboard.pendingMeta) {
                            int index = Keyboard.anIntArray154[Keyboard.pendingMeta];
                            Keyboard.pendingMeta = Keyboard.pendingMeta + 1 & 0x7f;
                            if (index < 0) {
                                Keyboard.heldKeys[~index] = false;
                            } else {
                                if (!Keyboard.heldKeys[index] && Keyboard.pressedKeysCount < Keyboard.pressedKeyIndices.length - 1) {
                                    Keyboard.pressedKeyIndices[Keyboard.pressedKeysCount++] = index;
                                }

                                Keyboard.heldKeys[index] = true;
                            }
                        }
                    } else {
                        for (int i = 0; i < 112; i++) {
                            Keyboard.heldKeys[i] = false;
                        }

                        Keyboard.meta = Keyboard.pendingMeta;
                    }

                    if (Keyboard.pressedKeysCount > 0) {
                        Keyboard.idleTime = 0;
                    }

                    Keyboard.anInt162 = Keyboard.anInt153;
                }

                Mouse.process();
                if (MouseWheel.provider != null) {
                    var4 = MouseWheel.provider.getAndReset();
                    mouseWheelPtr = var4;
                }

                if (gameState == 0) {
                    Login.processLoadingScreen();
                    GameShell.updateClock();
                } else if (gameState == 5) {
                    Login.processLoginScreen();
                    Login.processLoadingScreen();
                    GameShell.updateClock();
                } else if (gameState != 10 && gameState != 11) {
                    if (gameState == 20) {
                        Login.processLoginScreen();
                        processConnect();
                    } else if (gameState == 25) {
                        SceneGraph.load();
                    }
                } else {
                    Login.processLoginScreen();
                }

                if (gameState == 30) {
                    processPackets();
                } else if (gameState == 40 || gameState == 45) {
                    processConnect();
                }

                return;
            }

            var2.archive.method486(var2.cache, (int) var2.key, var2.data, false);
        }
    }

    public void method729() {
        if (rootInterfaceIndex != -1) {
            int root = rootInterfaceIndex;
            if (InterfaceComponent.load(root)) {
                InterfaceComponent.method513(interfaces[root], -1);
            }
        }

        for (int i = 0; i < renderedComponentCount; ++i) {
            if (renderedComponents[i]) {
                aBooleanArray1087[i] = true;
            }

            aBooleanArray1083[i] = renderedComponents[i];
            renderedComponents[i] = false;
        }

        anInt1084 = engineCycle;
        anInt1039 = -1;
        anInt1038 = -1;
        DefaultAudioSystemProvider.processingItemComponent = null;
        if (rootInterfaceIndex != -1) {
            renderedComponentCount = 0;
            InterfaceComponent.renderInterface(rootInterfaceIndex, 0, 0, canvasWidth, canvasHeight, 0, 0, -1);
        }

        JagGraphics.resetDrawingArea();
        if (ContextMenu.Crosshair.display) {
            if (ContextMenu.Crosshair.state == 1) {
                HintArrow.sprites[HintArrow.state / 100].renderAlphaAt(ContextMenu.Crosshair.x - 8, ContextMenu.Crosshair.y - 8);
            }

            if (ContextMenu.Crosshair.state == 2) {
                HintArrow.sprites[HintArrow.state / 100 + 4].renderAlphaAt(ContextMenu.Crosshair.x - 8, ContextMenu.Crosshair.y - 8);
            }
        }

        if (!ContextMenu.open) {
            if (anInt1039 != -1) {
                int var1 = anInt1039;
                int var2 = anInt1038;
                if ((ContextMenu.rowCount >= 2 || ItemSelection.state != 0 || ComponentSelection.state) && aBoolean1043) {
                    int var3 = ContextMenu.getLastRowIndex();
                    String var4;
                    if (ItemSelection.state == 1 && ContextMenu.rowCount < 2) {
                        var4 = "Use" + " " + ItemSelection.name + " " + "->";
                    } else if (ComponentSelection.state && ContextMenu.rowCount < 2) {
                        var4 = ComponentSelection.action + " " + ComponentSelection.name + " " + "->";
                    } else {
                        var4 = ContextMenu.getRowText(var3);
                    }

                    if (ContextMenu.rowCount > 2) {
                        var4 = var4 + getColorTags(16777215) + " " + '/' + " " + (ContextMenu.rowCount - 2) + " more options";
                    }

                    Font.b12full.method1142(var4, var1 + 4, var2 + 15, 16777215, 0, engineCycle / 1000);
                }
            }
        } else {
            int x = ContextMenu.x;
            int y = ContextMenu.y;
            int w = ContextMenu.width;
            int h = ContextMenu.height;
            int col = 6116423;
            JagGraphics.fillRect(x, y, w, h, col);
            JagGraphics.fillRect(x + 1, y + 1, w - 2, 16, 0);
            JagGraphics.method1372(x + 1, y + 18, w - 2, h - 19, 0);
            Font.b12full.drawString("Choose Option", x + 3, y + 14, col, -1);
            int var7 = Mouse.x;
            int var8 = Mouse.y;

            for (int i = 0; i < ContextMenu.rowCount; ++i) {
                int endX = y + (ContextMenu.rowCount - 1 - i) * 15 + 31;
                int endY = 16777215;
                if (var7 > x && var7 < w + x && var8 > endX - 13 && var8 < endX + 3) {
                    endY = 16776960;
                }

                Font.b12full.drawString(ContextMenu.getRowText(i), x + 3, endX, endY, 0);
            }

            int var9 = ContextMenu.x;
            int var10 = ContextMenu.y;
            int var11 = ContextMenu.width;
            int var12 = ContextMenu.height;

            for (int i = 0; i < renderedComponentCount; ++i) {
                if (interfacePositionsX[i] + interfaceWidths[i] > var9 && interfacePositionsX[i] < var11 + var9 && interfaceHeights[i] + interfacePositionsY[i] > var10 && interfacePositionsY[i] < var10 + var12) {
                    aBooleanArray1087[i] = true;
                }
            }
        }

        if (redrawMode == 3) {
            for (int i = 0; i < renderedComponentCount; ++i) {
                if (aBooleanArray1083[i]) {
                    JagGraphics.method1370(interfacePositionsX[i], interfacePositionsY[i], interfaceWidths[i], interfaceHeights[i], 16711935, 128);
                } else if (aBooleanArray1087[i]) {
                    JagGraphics.method1370(interfacePositionsX[i], interfacePositionsY[i], interfaceWidths[i], interfaceHeights[i], 16711680, 128);
                }
            }
        }

        SerializableProcessor.method456(SceneGraph.floorLevel, PlayerEntity.local.absoluteX, PlayerEntity.local.absoluteY, anInt972);
        anInt972 = 0;
    }

    protected void draw(boolean drawing) {
        boolean var2;
        label168:
        {
            try {
                if (AudioSystem.state == 2) {
                    if (Statics57.anAudioTrack1158 == null) {
                        Statics57.anAudioTrack1158 = AudioTrack.load(AudioSystem.tracks, AudioSystem.trackGroup, AudioSystem.trackFile);
                        if (Statics57.anAudioTrack1158 == null) {
                            var2 = false;
                            break label168;
                        }
                    }

                    if (FriendLoginUpdate.aClass97_668 == null) {
                        FriendLoginUpdate.aClass97_668 = new Class97(Statics57.aReferenceTable1159, SecureRandomCallable.aReferenceTable382);
                    }

                    if (Statics50.aClass5_Sub6_Sub3_326.method776(Statics57.anAudioTrack1158, Statics57.aReferenceTable1160, FriendLoginUpdate.aClass97_668, 22050)) {
                        Statics50.aClass5_Sub6_Sub3_326.method775();
                        Statics50.aClass5_Sub6_Sub3_326.method703(AudioSystem.volume);
                        Statics50.aClass5_Sub6_Sub3_326.method766(Statics57.anAudioTrack1158, AudioSystem.aBoolean620);
                        AudioSystem.state = 0;
                        Statics57.anAudioTrack1158 = null;
                        FriendLoginUpdate.aClass97_668 = null;
                        AudioSystem.tracks = null;
                        var2 = true;
                        break label168;
                    }
                }
            } catch (Exception var6) {
                var6.printStackTrace();
                Statics50.aClass5_Sub6_Sub3_326.method756();
                AudioSystem.state = 0;
                Statics57.anAudioTrack1158 = null;
                FriendLoginUpdate.aClass97_668 = null;
                AudioSystem.tracks = null;
            }

            var2 = false;
        }

        if (var2 && aBoolean904 && AudioSystem.anAudioSystem1236 != null) {
            AudioSystem.anAudioSystem1236.method1333();
        }

        if ((gameState == 10 || gameState == 20 || gameState == 30) && 0L != aLong1081 && Clock.now() > aLong1081) {
            AssociateComparatorByMyWorld.method603(isResizable());
        }

        int var5;
        if (drawing) {
            for (var5 = 0; var5 < 100; ++var5) {
                renderedComponents[var5] = true;
            }
        }

        if (gameState == 0) {
            this.method931(Login.anInt473, Login.loadingStateText, drawing);
        } else if (gameState == 5) {
            Login.draw(Font.b12full, Font.p11, Font.p12full);
        } else if (gameState != 10 && gameState != 11) {
            if (gameState == 20) {
                Login.draw(Font.b12full, Font.p11, Font.p12full);
            } else if (gameState == 25) {
                if (loadingDrawState == 1) {
                    if (loadingIndicator1 > loadingIndicator2) {
                        loadingIndicator2 = loadingIndicator1;
                    }

                    var5 = (loadingIndicator2 * 50 - loadingIndicator1 * 50) / loadingIndicator2;
                    WorldMapElement.method242("Loading - please wait." + "<br>" + " (" + var5 + "%" + ")", false);
                } else if (loadingDrawState == 2) {
                    if (loadingIndicator3 > loadingIndicator4) {
                        loadingIndicator4 = loadingIndicator3;
                    }

                    var5 = (loadingIndicator4 * 50 - loadingIndicator3 * 50) / loadingIndicator4 + 50;
                    WorldMapElement.method242("Loading - please wait." + "<br>" + " (" + var5 + "%" + ")", false);
                } else {
                    WorldMapElement.method242("Loading - please wait.", false);
                }
            } else if (gameState == 30) {
                this.method729();
            } else if (gameState == 40) {
                WorldMapElement.method242("Connection lost" + "<br>" + "Please wait - attempting to reestablish", false);
            } else if (gameState == 45) {
                WorldMapElement.method242("Please wait...", false);
            }
        } else {
            Login.draw(Font.b12full, Font.p11, Font.p12full);
        }

        if (gameState == 30 && redrawMode == 0 && !drawing && !resizableMode) {
            for (var5 = 0; var5 < renderedComponentCount; ++var5) {
                if (aBooleanArray1087[var5]) {
                    graphicsProvider.drawComponent(interfacePositionsX[var5], interfacePositionsY[var5], interfaceWidths[var5], interfaceHeights[var5]);
                    aBooleanArray1087[var5] = false;
                }
            }
        } else if (gameState > 0) {
            graphicsProvider.drawGame(0, 0);

            for (var5 = 0; var5 < renderedComponentCount; ++var5) {
                aBooleanArray1087[var5] = false;
            }
        }

    }

    protected void method737() {
        aLong1081 = Clock.now() + 500L;
        this.method733();
        if (rootInterfaceIndex != -1) {
            this.method734(true);
        }

    }

    public void method742(InterfaceComponent var1) {
        InterfaceComponent var2 = var1.parentUid == -1 ? null : InterfaceComponent.lookup(var1.parentUid);
        int var3;
        int var4;
        if (var2 == null) {
            var3 = canvasWidth;
            var4 = canvasHeight;
        } else {
            var3 = var2.width;
            var4 = var2.height;
        }

        InterfaceComponent.method728(var1, var3, var4, false);
        InterfaceComponent.method185(var1, var3, var4);
    }

    public void processPackets() {
        if (rebootTimer > 1) {
            --rebootTimer;
        }

        if (logoutTimer > 0) {
            --logoutTimer;
        }

        if (pendingDisconnect) {
            pendingDisconnect = false;
            dropConnection();
        } else {
            if (!ContextMenu.open) {
                ContextMenuBuilder.insertCancelItem();
            }

            for (int i = 0; i < 100 && processIncomingPacket(); ++i) {

            }

            if (gameState == 30) {
                while (true) {
                    ClassStructure var2 = ClassStructure.list.head();
                    boolean var29;
                    var29 = var2 != null;

                    int offset;
                    if (!var29) {
                        int var5;
                        if (gameStateEvent.shouldProcess) {
                            clientProtHandler.processGameStateEvents();
                        }

                        clientProtHandler.processMouseMotionRecords();
                        clientProtHandler.processMouseActionRecords();
                        clientProtHandler.processKeyInfo();
                        clientProtHandler.processCameraInfo();
                        clientProtHandler.processFocusInfo();

                        if (worldMap != null) {
                            worldMap.method1272();
                        }

                        if (StockMarketOfferLifetimeComparator.aBoolean584) {
                            if (friendsChatSystem != null) {
                                friendsChatSystem.sort();
                            }

                            GPI.method488();
                            StockMarketOfferLifetimeComparator.aBoolean584 = false;
                        }

                        Statics44.updateMinimapFloorLevel();
                        if (gameState != 30) {
                            return;
                        }

                        processObjectSpawns();
                        processAudioEffects();
                        ++netWriter.idleReadTicks;
                        if (netWriter.idleReadTicks > 750) {
                            dropConnection();
                            return;
                        }

                        processPlayers();
                        processNpcs();

                        int[] var32 = GPI.playerIndices;
                        for (var5 = 0; var5 < GPI.playerCount; ++var5) {
                            PlayerEntity var23 = players[var32[var5]];
                            if (var23 != null && var23.overheadTextCyclesLeft > 0) {
                                --var23.overheadTextCyclesLeft;
                                if (var23.overheadTextCyclesLeft == 0) {
                                    var23.overheadText = null;
                                }
                            }
                        }

                        for (var5 = 0; var5 < npcCount; ++var5) {
                            offset = npcIndices[var5];
                            NpcEntity var24 = npcs[offset];
                            if (var24 != null && var24.overheadTextCyclesLeft > 0) {
                                --var24.overheadTextCyclesLeft;
                                if (var24.overheadTextCyclesLeft == 0) {
                                    var24.overheadText = null;
                                }
                            }
                        }

                        ++anInt972;
                        if (ContextMenu.Crosshair.state != 0) {
                            HintArrow.state = HintArrow.state + 20;
                            if (HintArrow.state >= 400) {
                                ContextMenu.Crosshair.state = 0;
                            }
                        }

                        if (StockMarketOfferWorldComparator.anInterfaceComponent351 != null) {
                            ++anInt1018;
                            if (anInt1018 >= 15) {
                                InterfaceComponent.repaint(StockMarketOfferWorldComparator.anInterfaceComponent351);
                                StockMarketOfferWorldComparator.anInterfaceComponent351 = null;
                            }
                        }

                        InterfaceComponent var34 = OldConnection.hoveredComponent;
                        InterfaceComponent var31 = Statics24.anInterfaceComponent1417;
                        OldConnection.hoveredComponent = null;
                        Statics24.anInterfaceComponent1417 = null;
                        draggedSpecialComponent = null;
                        processingComponentDrag = false;
                        processingComponentDragTopLevel = false;
                        anInt1092 = 0;

                        while (Keyboard.isKeyHeld() && anInt1092 < 128) {
                            if (rights >= 2 && Keyboard.heldKeys[82] && SecureRandomService.anInt457 == 66) {
                                String var39 = method9();
                                instance.copyToClipboard(var39);
                            } else if (Camera.oculusOrbMode != 1 || Keyboard.aChar151 <= 0) {
                                anIntArray1096[anInt1092] = SecureRandomService.anInt457;
                                anIntArray1097[anInt1092] = Keyboard.aChar151;
                                ++anInt1092;
                            }
                        }

                        boolean jmod = rights >= 2;
                        if (jmod && Keyboard.heldKeys[82] && Keyboard.heldKeys[81] && mouseWheelPtr != 0) {
                            int var6 = PlayerEntity.local.floorLevel - mouseWheelPtr;
                            if (var6 < 0) {
                                var6 = 0;
                            } else if (var6 > 3) {
                                var6 = 3;
                            }

                            if (var6 != PlayerEntity.local.floorLevel) {
                                Statics35.teleport(PlayerEntity.local.pathXQueue[0] + baseX, PlayerEntity.local.pathYQueue[0] + baseY, var6, false);
                            }

                            mouseWheelPtr = 0;
                        }

                        if (rootInterfaceIndex != -1) {
                            WorldMapTileDecor.processComponentRendering(rootInterfaceIndex, 0, 0, canvasWidth, canvasHeight, 0, 0);
                        }

                        ++anInt1075;

                        while (true) {
                            InterfaceComponent var25;
                            InterfaceComponent var38;
                            ScriptEvent var40;
                            do {
                                var40 = renderEventScripts.popFirst();
                                if (var40 == null) {
                                    while (true) {
                                        do {
                                            var40 = inputFinishedEventScripts.popFirst();
                                            if (var40 == null) {
                                                while (true) {
                                                    do {
                                                        var40 = inputOccuringEventScripts.popFirst();
                                                        if (var40 == null) {
                                                            this.method751();
                                                            if (worldMap != null) {
                                                                worldMap.method1261(SceneGraph.floorLevel, baseX + (PlayerEntity.local.absoluteX >> 7), baseY + (PlayerEntity.local.absoluteY >> 7), false);
                                                                worldMap.method1225();
                                                            }

                                                            if (draggedComponent != null) {
                                                                this.method732();
                                                            }

                                                            if (AnimationFrameGroup.dragComponent != null) {
                                                                InterfaceComponent.repaint(AnimationFrameGroup.dragComponent);
                                                                ++componentDragCycles;
                                                                if (Mouse.pressMeta == 0) {
                                                                    if (draggingComponent) {
                                                                        if (AnimationFrameGroup.dragComponent == DefaultAudioSystemProvider.processingItemComponent && draggingComponentIndex != draggingComponentSourceIndex) {
                                                                            InterfaceComponent var41 = AnimationFrameGroup.dragComponent;
                                                                            byte var35 = 0;
                                                                            if (anInt1054 == 1 && var41.contentType == 206) {
                                                                                var35 = 1;
                                                                            }

                                                                            if (var41.itemIds[draggingComponentIndex] <= 0) {
                                                                                var35 = 0;
                                                                            }

                                                                            if (WorldMapAreaChunk_Sub3.method367(InterfaceComponent.getConfig(var41))) {
                                                                                int var8 = draggingComponentSourceIndex;
                                                                                int var9 = draggingComponentIndex;
                                                                                var41.itemIds[var9] = var41.itemIds[var8];
                                                                                var41.itemStackSizes[var9] = var41.itemStackSizes[var8];
                                                                                var41.itemIds[var8] = -1;
                                                                                var41.itemStackSizes[var8] = 0;
                                                                            } else if (var35 == 1) {
                                                                                int var8 = draggingComponentSourceIndex;
                                                                                int var9 = draggingComponentIndex;

                                                                                while (var9 != var8) {
                                                                                    if (var8 > var9) {
                                                                                        var41.swapItem(var8 - 1, var8);
                                                                                        --var8;
                                                                                    } else if (var8 < var9) {
                                                                                        var41.swapItem(var8 + 1, var8);
                                                                                        ++var8;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                var41.swapItem(draggingComponentIndex, draggingComponentSourceIndex);
                                                                            }

                                                                            OutgoingPacket outgoing = OutgoingPacket.prepare(ClientProt.DRAG_ITEM, netWriter.encryptor);
                                                                            outgoing.buffer.pirf4(AnimationFrameGroup.dragComponent.uid);
                                                                            outgoing.buffer.p2(draggingComponentIndex);
                                                                            outgoing.buffer.p1(var35);
                                                                            outgoing.buffer.ip2(draggingComponentSourceIndex);
                                                                            netWriter.writeLater(outgoing);
                                                                        }
                                                                    } else if (this.method731()) {
                                                                        this.openMenu(draggingComponentX, draggingComponentY);
                                                                    } else if (ContextMenu.rowCount > 0) {
                                                                        SerializableProcessor.method459(draggingComponentX, draggingComponentY);
                                                                    }

                                                                    anInt1018 = 10;
                                                                    Mouse.clickMeta = 0;
                                                                    AnimationFrameGroup.dragComponent = null;
                                                                } else if (componentDragCycles >= 5 && (Mouse.x > draggingComponentX + 5 || Mouse.x < draggingComponentX - 5 || Mouse.y > draggingComponentY + 5 || Mouse.y < draggingComponentY - 5)) {
                                                                    draggingComponent = true;
                                                                }
                                                            }

                                                            if (SceneGraph.isMovementPending()) {
                                                                int var6 = SceneGraph.pendingDestinationX;
                                                                int var7 = SceneGraph.pendingDestinationY;
                                                                OutgoingPacket outgoing = OutgoingPacket.prepare(ClientProt.PROCESS_MOVEMENT, netWriter.encryptor);
                                                                outgoing.buffer.p1(5);
                                                                outgoing.buffer.ip2a(baseX + var6);
                                                                outgoing.buffer.ip2(baseY + var7);
                                                                outgoing.buffer.p1n(Keyboard.heldKeys[82] ? (Keyboard.heldKeys[81] ? 2 : 1) : 0);
                                                                netWriter.writeLater(outgoing);
                                                                SceneGraph.unsetPendingMovement();
                                                                ContextMenu.Crosshair.x = Mouse.clickX;
                                                                ContextMenu.Crosshair.y = Mouse.clickY;
                                                                ContextMenu.Crosshair.state = 1;
                                                                HintArrow.state = 0;
                                                                destinationX = var6;
                                                                destinationY = var7;
                                                            }

                                                            if (var34 != OldConnection.hoveredComponent) {
                                                                if (var34 != null) {
                                                                    InterfaceComponent.repaint(var34);
                                                                }

                                                                if (OldConnection.hoveredComponent != null) {
                                                                    InterfaceComponent.repaint(OldConnection.hoveredComponent);
                                                                }
                                                            }

                                                            if (var31 != Statics24.anInterfaceComponent1417 && anInt1036 == anInt1041) {
                                                                if (var31 != null) {
                                                                    InterfaceComponent.repaint(var31);
                                                                }

                                                                if (Statics24.anInterfaceComponent1417 != null) {
                                                                    InterfaceComponent.repaint(Statics24.anInterfaceComponent1417);
                                                                }
                                                            }

                                                            if (Statics24.anInterfaceComponent1417 != null) {
                                                                if (anInt1041 < anInt1036) {
                                                                    ++anInt1041;
                                                                    if (anInt1041 == anInt1036) {
                                                                        InterfaceComponent.repaint(Statics24.anInterfaceComponent1417);
                                                                    }
                                                                }
                                                            } else if (anInt1041 > 0) {
                                                                --anInt1041;
                                                            }

                                                            if (Camera.oculusOrbMode == 0) {
                                                                int var6 = PlayerEntity.local.absoluteX;
                                                                int var7 = PlayerEntity.local.absoluteY;
                                                                if (Camera.oculusOrbAbsoluteX - var6 < -500 || Camera.oculusOrbAbsoluteX - var6 > 500 || Camera.oculusOrbAbsoluteY - var7 < -500 || Camera.oculusOrbAbsoluteY - var7 > 500) {
                                                                    Camera.oculusOrbAbsoluteX = var6;
                                                                    Camera.oculusOrbAbsoluteY = var7;
                                                                }

                                                                if (var6 != Camera.oculusOrbAbsoluteX) {
                                                                    Camera.oculusOrbAbsoluteX += (var6 - Camera.oculusOrbAbsoluteX) / 16;
                                                                }

                                                                if (var7 != Camera.oculusOrbAbsoluteY) {
                                                                    Camera.oculusOrbAbsoluteY += (var7 - Camera.oculusOrbAbsoluteY) / 16;
                                                                }

                                                                int var8 = Camera.oculusOrbAbsoluteX >> 7;
                                                                int var9 = Camera.oculusOrbAbsoluteY >> 7;
                                                                int var10 = SceneGraph.getTileHeight(Camera.oculusOrbAbsoluteX, Camera.oculusOrbAbsoluteY, SceneGraph.floorLevel);
                                                                int var11 = 0;
                                                                if (var8 > 3 && var9 > 3 && var8 < 100 && var9 < 100) {
                                                                    for (int x = var8 - 4; x <= var8 + 4; ++x) {
                                                                        for (int y = var9 - 4; y <= var9 + 4; ++y) {
                                                                            int z = SceneGraph.floorLevel;
                                                                            if (z < 3 && (Statics45.sceneRenderRules[1][x][y] & 2) == 2) {
                                                                                ++z;
                                                                            }

                                                                            int var26 = var10 - Statics45.tileHeights[z][x][y];
                                                                            if (var26 > var11) {
                                                                                var11 = var26;
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                int xd = var11 * 192;
                                                                if (xd > 98048) {
                                                                    xd = 98048;
                                                                }

                                                                if (xd < 32768) {
                                                                    xd = 32768;
                                                                }

                                                                if (xd > anInt1008) {
                                                                    anInt1008 += (xd - anInt1008) / 24;
                                                                } else if (xd < anInt1008) {
                                                                    anInt1008 += (xd - anInt1008) / 80;
                                                                }

                                                                Camera.anInt802 = SceneGraph.getTileHeight(PlayerEntity.local.absoluteX, PlayerEntity.local.absoluteY, SceneGraph.floorLevel) - Camera.followHeight;
                                                            } else if (Camera.oculusOrbMode == 1) {
                                                                Camera.setOculusOrbToLocalPlayerPosition();
                                                                short var36 = -1;
                                                                if (Keyboard.heldKeys[33]) {
                                                                    var36 = 0;
                                                                } else if (Keyboard.heldKeys[49]) {
                                                                    var36 = 1024;
                                                                }

                                                                if (Keyboard.heldKeys[48]) {
                                                                    if (var36 == 0) {
                                                                        var36 = 1792;
                                                                    } else if (var36 == 1024) {
                                                                        var36 = 1280;
                                                                    } else {
                                                                        var36 = 1536;
                                                                    }
                                                                } else if (Keyboard.heldKeys[50]) {
                                                                    if (var36 == 0) {
                                                                        var36 = 256;
                                                                    } else if (var36 == 1024) {
                                                                        var36 = 768;
                                                                    } else {
                                                                        var36 = 512;
                                                                    }
                                                                }

                                                                byte var37 = 0;
                                                                if (Keyboard.heldKeys[35]) {
                                                                    var37 = -1;
                                                                } else if (Keyboard.heldKeys[51]) {
                                                                    var37 = 1;
                                                                }

                                                                int var8 = 0;
                                                                if (var36 >= 0 || var37 != 0) {
                                                                    var8 = Keyboard.heldKeys[81] ? Camera.oculusOrbSlowSpeed : Camera.oculusOrbSpeed;
                                                                    var8 *= 16;
                                                                    anInt986 = var36;
                                                                    anInt997 = var37;
                                                                }

                                                                if (anInt977 < var8) {
                                                                    anInt977 += var8 / 8;
                                                                    if (anInt977 > var8) {
                                                                        anInt977 = var8;
                                                                    }
                                                                } else if (anInt977 > var8) {
                                                                    anInt977 = anInt977 * 9 / 10;
                                                                }

                                                                if (anInt977 > 0) {
                                                                    int var9 = anInt977 / 16;
                                                                    if (anInt986 >= 0) {
                                                                        int var6 = anInt986 - Camera.yaw & 2047;
                                                                        int var10 = JagGraphics3D.SIN_TABLE[var6];
                                                                        int var11 = JagGraphics3D.COS_TABLE[var6];
                                                                        Camera.oculusOrbAbsoluteX += var10 * var9 / 65536;
                                                                        Camera.oculusOrbAbsoluteY += var9 * var11 / 65536;
                                                                    }

                                                                    if (anInt997 != 0) {
                                                                        Camera.anInt802 += var9 * anInt997;
                                                                        if (Camera.anInt802 > 0) {
                                                                            Camera.anInt802 = 0;
                                                                        }
                                                                    }
                                                                } else {
                                                                    anInt986 = -1;
                                                                    anInt997 = -1;
                                                                }

                                                                if (Keyboard.heldKeys[13]) {
                                                                    netWriter.writeLater(OutgoingPacket.prepare(ClientProt.CLOSE_OCULUS_ORB, netWriter.encryptor));
                                                                    Camera.oculusOrbMode = 0;
                                                                }
                                                            }

                                                            if (Mouse.pressMeta == 4 && WorldMapObjectIcon.mouseCameraEnabled) {
                                                                int var6 = Mouse.y - Camera.mouseDragY;
                                                                Camera.moveY = var6 * 2;
                                                                Camera.mouseDragY = var6 != -1 && var6 != 1 ? (Camera.mouseDragY + Mouse.y) / 2 : Mouse.y;
                                                                int var7 = Camera.mouseDragX - Mouse.x;
                                                                Camera.moveX = var7 * 2;
                                                                Camera.mouseDragX = var7 != -1 && var7 != 1 ? (Mouse.x + Camera.mouseDragX) / 2 : Mouse.x;
                                                            } else {
                                                                if (Keyboard.heldKeys[96]) {
                                                                    Camera.moveX += (-24 - Camera.moveX) / 2;
                                                                } else if (Keyboard.heldKeys[97]) {
                                                                    Camera.moveX += (24 - Camera.moveX) / 2;
                                                                } else {
                                                                    Camera.moveX /= 2;
                                                                }

                                                                if (Keyboard.heldKeys[98]) {
                                                                    Camera.moveY += (12 - Camera.moveY) / 2;
                                                                } else if (Keyboard.heldKeys[99]) {
                                                                    Camera.moveY += (-12 - Camera.moveY) / 2;
                                                                } else {
                                                                    Camera.moveY /= 2;
                                                                }

                                                                Camera.mouseDragY = Mouse.y;
                                                                Camera.mouseDragX = Mouse.x;
                                                            }

                                                            Camera.yOffset = Camera.moveX / 2 + Camera.yOffset & 2047;
                                                            Camera.minimumPitch += Camera.moveY / 2;
                                                            if (Camera.minimumPitch < 128) {
                                                                Camera.minimumPitch = 128;
                                                            }

                                                            if (Camera.minimumPitch > 383) {
                                                                Camera.minimumPitch = 383;
                                                            }

                                                            if (cameraLocked) {
                                                                SceneGraph.method517();
                                                            }

                                                            for (int var6 = 0; var6 < 5; ++var6) {
                                                                int var10002 = anIntArray916[var6]++;
                                                            }

                                                            varcs.updateIfRequired();

                                                            int mouseIdle = Mouse.getAndIncrementIdleTime();
                                                            int keyboardIdle = Keyboard.getIdleTime();
                                                            if (mouseIdle > 15000 && keyboardIdle > 15000) {
                                                                logoutTimer = 250;
                                                                Mouse.idleTime = 14500;
                                                                OutgoingPacket outgoing = OutgoingPacket.prepare(ClientProt.IDLE_LOGOUT, netWriter.encryptor);
                                                                netWriter.writeLater(outgoing);
                                                            }

                                                            relationshipSystem.processFriendLogins();
                                                            ++netWriter.idleWriteTicks;
                                                            if (netWriter.idleWriteTicks > 50) {
                                                                OutgoingPacket outgoing = OutgoingPacket.prepare(ClientProt.KEEP_ALIVE, netWriter.encryptor);
                                                                netWriter.writeLater(outgoing);
                                                            }

                                                            try {
                                                                netWriter.flush();
                                                            } catch (IOException e) {
                                                                dropConnection();
                                                            }

                                                            return;
                                                        }

                                                        var25 = var40.component;
                                                        if (var25.subComponentIndex < 0) {
                                                            break;
                                                        }

                                                        var38 = InterfaceComponent.lookup(var25.parentUid);
                                                    }
                                                    while (var38 == null || var38.subcomponents == null || var25.subComponentIndex >= var38.subcomponents.length || var25 != var38.subcomponents[var25.subComponentIndex]);

                                                    ScriptEvent.fire(var40);
                                                }
                                            }

                                            var25 = var40.component;
                                            if (var25.subComponentIndex < 0) {
                                                break;
                                            }

                                            var38 = InterfaceComponent.lookup(var25.parentUid);
                                        }
                                        while (var38 == null || var38.subcomponents == null || var25.subComponentIndex >= var38.subcomponents.length || var25 != var38.subcomponents[var25.subComponentIndex]);

                                        ScriptEvent.fire(var40);
                                    }
                                }

                                var25 = var40.component;
                                if (var25.subComponentIndex < 0) {
                                    break;
                                }

                                var38 = InterfaceComponent.lookup(var25.parentUid);
                            }
                            while (var38 == null || var38.subcomponents == null || var25.subComponentIndex >= var38.subcomponents.length || var25 != var38.subcomponents[var25.subComponentIndex]);

                            ScriptEvent.fire(var40);
                        }
                    }

                    clientProtHandler.processReflection();
                }
            }
        }
    }

    public void processConnect() {
        Connection connection = netWriter.unwrap();
        BitBuffer input = netWriter.inbuffer;

        try {
            if (loginStage == 0) {
                if (SecureRandomService.instance == null && (secureRandomService.isDone() || loginStageCycles > 250)) {
                    SecureRandomService.instance = secureRandomService.get();
                    secureRandomService.kill();
                    secureRandomService = null;
                }

                if (SecureRandomService.instance != null) {
                    if (connection != null) {
                        connection.stop();
                        connection = null;
                    }

                    LoginProt.task = null;
                    pendingDisconnect = false;
                    loginStageCycles = 0;
                    loginStage = 1;
                }
            }

            if (loginStage == 1) {
                if (LoginProt.task == null) {
                    LoginProt.task = taskProcessor.create(LoginStep.currentDomain, NpcEntity.port);
                }

                if (LoginProt.task.state == 2) {
                    throw new IOException();
                }

                if (LoginProt.task.state == 1) {
                    if (usingBufferedConnection) {
                        Socket socket = (Socket) LoginProt.task.result;
                        connection = new AsyncConnection(socket, 40000, 5000);
                    } else {
                        connection = new OldConnection((Socket) LoginProt.task.result, taskProcessor, 5000);
                    }

                    netWriter.setConnection(connection);
                    LoginProt.task = null;
                    loginStage = 2;
                }
            }

            if (loginStage == 2) {
                netWriter.drop();
                OutgoingPacket packet = OutgoingPacket.prepareLoginPacket();
                packet.buffer.p1(LoginProt.HANDSHAKE.value);
                netWriter.writeLater(packet);
                netWriter.flush();
                input.pos = 0;
                loginStage = 3;
            }

            if (loginStage == 3) {
                if (AudioSystem.anAudioSystem1236 != null) {
                    AudioSystem.anAudioSystem1236.method1332();
                }

                if (AudioSystem.anAudioSystem599 != null) {
                    AudioSystem.anAudioSystem599.method1332();
                }

                boolean available = !usingBufferedConnection || connection.available(1);

                if (available) {
                    int read = connection.read();
                    if (AudioSystem.anAudioSystem1236 != null) {
                        AudioSystem.anAudioSystem1236.method1332();
                    }

                    if (AudioSystem.anAudioSystem599 != null) {
                        AudioSystem.anAudioSystem599.method1332();
                    }

                    if (read != 0) {
                        Login.processResponseCode(read);
                        return;
                    }

                    input.pos = 0;
                    loginStage = 4;
                }
            }

            if (loginStage == 4) {
                if (input.pos < 8) {
                    int len = connection.available();
                    if (len > 8 - input.pos) {
                        len = 8 - input.pos;
                    }

                    if (len > 0) {
                        connection.read(input.payload, input.pos, len);
                        input.pos += len;
                    }
                }

                if (input.pos == 8) {
                    input.pos = 0;
                    Statics45.aLong403 = input.g8();
                    loginStage = 5;
                }
            }

            if (loginStage == 5) {
                netWriter.inbuffer.pos = 0;
                netWriter.drop();
                BitBuffer buffer = new BitBuffer(500);
                int[] seed = new int[]{SecureRandomService.instance.nextInt(), SecureRandomService.instance.nextInt(), SecureRandomService.instance.nextInt(), SecureRandomService.instance.nextInt()};
                buffer.pos = 0;
                buffer.p1(1);
                buffer.p4(seed[0]);
                buffer.p4(seed[1]);
                buffer.p4(seed[2]);
                buffer.p4(seed[3]);
                buffer.p8(Statics45.aLong403);
                if (gameState == 40) {
                    buffer.p4(DirectByteBufferProvider.anIntArray1136[0]);
                    buffer.p4(DirectByteBufferProvider.anIntArray1136[1]);
                    buffer.p4(DirectByteBufferProvider.anIntArray1136[2]);
                    buffer.p4(DirectByteBufferProvider.anIntArray1136[3]);
                } else {
                    buffer.p1(loginStep.getOrdinal());
                    switch (loginStep.anInt619) {
                        case 0:
                        case 1:
                            buffer.p3(ZoneProt.parsedTotp);
                            ++buffer.pos;
                            break;
                        case 2:
                            buffer.pos += 4;
                            break;
                        case 3:
                            buffer.p4(preferences.properties.get(Djb2.hash(Login.username)));
                    }

                    buffer.p1(LoginHeaderType.anEnum_Sub10_1767.getOrdinal());
                    buffer.pcstr(Login.password);
                }

                buffer.prsa(Statics51.rsaExponent, Statics51.rsaModulus);
                DirectByteBufferProvider.anIntArray1136 = seed;
                OutgoingPacket packet = OutgoingPacket.prepareLoginPacket();
                packet.buffer.pos = 0;
                if (gameState == 40) {
                    packet.buffer.p1(LoginProt.CHANGE_SERVERS.value);
                } else {
                    packet.buffer.p1(LoginProt.LOGIN.value);
                }

                packet.buffer.p2(0);
                int var8 = packet.buffer.pos;
                packet.buffer.p4(184);
                packet.buffer.p4(1);
                packet.buffer.p1(anInt923);
                packet.buffer.p(buffer.payload, 0, buffer.pos);
                int offset = packet.buffer.pos;
                packet.buffer.pcstr(Login.username);
                packet.buffer.p1((resizableMode ? 1 : 0) << 1 | (lowMemory ? 1 : 0));
                packet.buffer.p2(canvasWidth);
                packet.buffer.p2(canvasHeight);
                int var12;
                if (random != null) {
                    packet.buffer.p(random, 0, random.length);
                } else {
                    byte[] var11 = new byte[24];

                    try {
                        BufferedFile.random.seek(0L);
                        BufferedFile.random.read(var11);

                        var12 = 0;
                        while (var12 < 24 && var11[var12] == 0) {
                            ++var12;
                        }

                        if (var12 >= 24) {
                            throw new IOException();
                        }
                    } catch (Exception var20) {
                        for (int var14 = 0; var14 < 24; ++var14) {
                            var11[var14] = -1;
                        }
                    }

                    packet.buffer.p(var11, 0, var11.length);
                }

                packet.buffer.pcstr(Statics57.aString1162);
                packet.buffer.p4(WorldMapCacheArea.anInt130);
                Buffer var15 = new Buffer(operatingSystemNode.getPayloadSize());
                operatingSystemNode.writeTo(var15);
                packet.buffer.p(var15.payload, 0, var15.payload.length);
                packet.buffer.p1(anInt923);
                packet.buffer.p4(0);
                packet.buffer.p4(Archive.skeletons.hash);
                packet.buffer.p4(Archive.skins.hash);
                packet.buffer.p4(Archive.config.hash);
                packet.buffer.p4(Archive.interfaces.hash);
                packet.buffer.p4(Archive.audioEffects.hash);
                packet.buffer.p4(Archive.landscape.hash);
                packet.buffer.p4(Archive.audioTracks.hash);
                packet.buffer.p4(Archive.models.hash);
                packet.buffer.p4(Archive.sprites.hash);
                packet.buffer.p4(Archive.textures.hash);
                packet.buffer.p4(Archive.huffman.hash);
                packet.buffer.p4(Archive.audioTracks2.hash);
                packet.buffer.p4(Archive.cs2.hash);
                packet.buffer.p4(Archive.fonts.hash);
                packet.buffer.p4(Archive.audioEffects2.hash);
                packet.buffer.p4(Archive.audioEffects3.hash);
                packet.buffer.p4(0);
                packet.buffer.p4(Archive.bootSprites.hash);
                packet.buffer.p4(Archive.mapscene.hash);
                packet.buffer.p4(Archive.worldmap.hash);
                packet.buffer.p4(Archive.mapland.hash);
                packet.buffer.tinyenc2(seed, offset, packet.buffer.pos);
                packet.buffer.psize2(packet.buffer.pos - var8);
                netWriter.writeLater(packet);
                netWriter.flush();
                netWriter.encryptor = new IsaacCipher(seed);
                int[] var34 = new int[4];

                for (var12 = 0; var12 < 4; ++var12) {
                    var34[var12] = seed[var12] + 50;
                }

                input.setCipher(var34);
                loginStage = 6;
            }

            if (loginStage == 6 && connection.available() > 0) {
                int next = connection.read();
                if (next == 21 && gameState == 20) {
                    loginStage = 9;
                } else if (next == 2) {
                    loginStage = 11;
                } else if (next == 15 && gameState == 40) {
                    netWriter.incomingPacketSize = -1;
                    loginStage = 16;
                } else if (next == 64) {
                    loginStage = 7;
                } else if (next == 23 && loginProcess < 1) {
                    ++loginProcess;
                    loginStage = 0;
                } else {
                    if (next != 29) {
                        Login.processResponseCode(next);
                        return;
                    }

                    loginStage = 14;
                }
            }

            if (loginStage == 7 && connection.available() > 0) {
                anInt1232 = connection.read();
                loginStage = 8;
            }

            if (loginStage == 8 && connection.available() >= anInt1232) {
                connection.read(input.payload, 0, anInt1232);
                input.pos = 0;
                loginStage = 6;
            }

            if (loginStage == 9 && connection.available() > 0) {
                serverTransferCycles = (connection.read() + 3) * 60;
                loginStage = 10;
            }

            if (loginStage == 10) {
                loginStageCycles = 0;
                Login.setMessages("You have only just left another world.", "Your profile will be transferred in:", serverTransferCycles / 60 + " seconds.");
                if (--serverTransferCycles <= 0) {
                    loginStage = 0;
                }

            } else {
                if (loginStage == 11 && connection.available() >= 1) {
                    Server.expectedRead = connection.read();
                    loginStage = 12;
                }

                int var16;
                if (loginStage == 12 && connection.available() >= Server.expectedRead) {
                    boolean var5 = connection.read() == 1;
                    connection.read(input.payload, 0, 4);
                    input.pos = 0;
                    if (var5) {
                        int var6 = input.gheader() << 24;
                        var6 |= input.gheader() << 16;
                        var6 |= input.gheader() << 8;
                        var6 |= input.gheader();
                        var16 = Djb2.hash(Login.username);
                        if (preferences.properties.size() >= 10 && !preferences.properties.containsKey(var16)) {
                            Iterator<Map.Entry<Integer, Integer>> var17 = preferences.properties.entrySet().iterator();
                            var17.next();
                            var17.remove();
                        }

                        preferences.properties.put(var16, var6);
                    }

                    if (rememberUsername) {
                        preferences.rememberedUsername = Login.username;
                    } else {
                        preferences.rememberedUsername = null;
                    }

                    ClientPreferences.method854();
                    rights = connection.read();
                    aBoolean1056 = connection.read() == 1;
                    playerIndex = connection.read();
                    playerIndex <<= 8;
                    playerIndex += connection.read();
                    relationshipSystemState = connection.read();
                    connection.read(input.payload, 0, 1);
                    input.pos = 0;
                    ServerProt[] var31 = ServerProt.values();
                    int var8 = input.gesmart();
                    if (var8 < 0 || var8 >= var31.length) {
                        throw new IOException(var8 + " " + input.pos);
                    }

                    netWriter.currentIncomingPacket = var31[var8];
                    netWriter.incomingPacketSize = netWriter.currentIncomingPacket.size;
                    connection.read(input.payload, 0, 2);
                    input.pos = 0;
                    netWriter.incomingPacketSize = input.g2();

                    try {
                        JSObjectUtil.call(instance, "zap");
                    } catch (Throwable ignored) {
                    }

                    loginStage = 13;
                }

                if (loginStage == 13) {
                    if (connection.available() >= netWriter.incomingPacketSize) {
                        input.pos = 0;
                        connection.read(input.payload, 0, netWriter.incomingPacketSize);
                        gameStateEvent.completeLogin();
                        timeOfPreviousClick = 1L;
                        mouseRecorder.caret = 0;
                        hasFocus = true;
                        previousFocusState = true;
                        timeOfPreviousKeyPress = -1L;
                        ClassStructure.list = new LinkedList<>();
                        netWriter.drop();
                        netWriter.inbuffer.pos = 0;
                        netWriter.currentIncomingPacket = null;
                        netWriter.lastIncomingPacket = null;
                        netWriter.secondLastIncomingPacket = null;
                        netWriter.thirdLastIncomingPacket = null;
                        netWriter.incomingPacketSize = 0;
                        netWriter.idleReadTicks = 0;
                        rebootTimer = 0;
                        logoutTimer = 0;
                        HintArrow.type = 0;
                        ContextMenu.close();
                        Mouse.idleTime = 0;
                        ChatHistory.clear();
                        ItemSelection.state = 0;
                        ComponentSelection.state = false;
                        audioEffectCount = 0;
                        Camera.yOffset = 0;
                        Camera.oculusOrbMode = 0;
                        WorldMap.heatmap = null;
                        mapState = 0;
                        minimapFloorLevel = -1;
                        destinationX = 0;
                        destinationY = 0;
                        playerAttackOptionPriority = AttackOptionPriority.HIDDEN;
                        npcAttackOptionPriority = AttackOptionPriority.HIDDEN;
                        npcCount = 0;
                        GPI.method250();

                        int var29;
                        int var6;
                        for (var29 = 0; var29 < 2048; ++var29) {
                            players[var29] = null;
                        }

                        for (var29 = 0; var29 < 32768; ++var29) {
                            npcs[var29] = null;
                        }

                        varpControlledInt2 = -1;
                        projectiles.clear();
                        effectObjects.clear();

                        for (var29 = 0; var29 < 4; ++var29) {
                            for (var6 = 0; var6 < 104; ++var6) {
                                for (var16 = 0; var16 < 104; ++var16) {
                                    pickables[var29][var6][var16] = null;
                                }
                            }
                        }

                        pendingSpawns = new NodeDeque();
                        relationshipSystem.clear();

                        for (var29 = 0; var29 < VarDefinition.count; ++var29) {
                            VarDefinition var24 = VarDefinition.get(var29);
                            if (var24 != null) {
                                Vars.baseValues[var29] = 0;
                                Vars.values[var29] = 0;
                            }
                        }

                        varcs.clear();
                        anInt1053 = -1;
                        if (rootInterfaceIndex != -1) {
                            InterfaceComponent.method830(rootInterfaceIndex);
                        }

                        for (SubInterface var28 = subInterfaces.head(); var28 != null; var28 = subInterfaces.next()) {
                            InterfaceComponent.close(var28, true);
                        }

                        rootInterfaceIndex = -1;
                        subInterfaces = new NodeTable(8);
                        pleaseWaitComponent = null;
                        ContextMenu.close();
                        renderedAppearance.update(null, new int[]{0, 0, 0, 0, 0}, false, -1);

                        for (var29 = 0; var29 < 8; ++var29) {
                            playerActions[var29] = null;
                            playerActionPriorities[var29] = false;
                        }

                        NamedFont.method1181();
                        loadingPleaseWait = true;

                        for (var29 = 0; var29 < 100; ++var29) {
                            renderedComponents[var29] = true;
                        }

                        processGameBoundsPacket();
                        friendsChatSystem = null;

                        for (var29 = 0; var29 < 8; ++var29) {
                            stockMarketOffers[var29] = new StockMarketOffer();
                        }

                        StockMarketOffer.mediator = null;
                        PlayerEntity.update(input);
                        ServerProt.anInt206 = -1;
                        PlayerAccountType.onSceneXTEAKeyChange(false, input);
                        netWriter.currentIncomingPacket = null;
                    }

                } else {
                    if (loginStage == 14 && connection.available() >= 2) {
                        input.pos = 0;
                        connection.read(input.payload, 0, 2);
                        input.pos = 0;
                        WorldMapRenderRules.anInt168 = input.g2();
                        loginStage = 15;
                    }

                    if (loginStage == 15 && connection.available() >= WorldMapRenderRules.anInt168) {
                        input.pos = 0;
                        connection.read(input.payload, 0, WorldMapRenderRules.anInt168);
                        input.pos = 0;
                        String var27 = input.gstr();
                        String var23 = input.gstr();
                        String var32 = input.gstr();
                        Login.setMessages(var27, var23, var32);
                        setGameState(10);
                    }

                    if (loginStage != 16) {
                        ++loginStageCycles;
                        if (loginStageCycles > 2000) {
                            if (loginProcess < 1) {
                                if (HitsplatDefinition.anInt1929 == NpcEntity.port) {
                                    NpcEntity.port = Bzip2Entry.anInt1579;
                                } else {
                                    NpcEntity.port = HitsplatDefinition.anInt1929;
                                }

                                ++loginProcess;
                                loginStage = 0;
                            } else {
                                Login.processResponseCode(-3);
                            }
                        }
                    } else {
                        if (netWriter.incomingPacketSize == -1) {
                            if (connection.available() < 2) {
                                return;
                            }

                            connection.read(input.payload, 0, 2);
                            input.pos = 0;
                            netWriter.incomingPacketSize = input.g2();
                        }

                        if (connection.available() >= netWriter.incomingPacketSize) {
                            connection.read(input.payload, 0, netWriter.incomingPacketSize);
                            input.pos = 0;
                            int var29 = netWriter.incomingPacketSize;
                            gameStateEvent.method1116();
                            netWriter.drop();
                            netWriter.inbuffer.pos = 0;
                            netWriter.currentIncomingPacket = null;
                            netWriter.lastIncomingPacket = null;
                            netWriter.secondLastIncomingPacket = null;
                            netWriter.thirdLastIncomingPacket = null;
                            netWriter.incomingPacketSize = 0;
                            netWriter.idleReadTicks = 0;
                            rebootTimer = 0;
                            ContextMenu.close();
                            mapState = 0;
                            destinationX = 0;

                            for (int var6 = 0; var6 < 2048; ++var6) {
                                players[var6] = null;
                            }

                            PlayerEntity.local = null;

                            for (int var6 = 0; var6 < npcs.length; ++var6) {
                                NpcEntity var33 = npcs[var6];
                                if (var33 != null) {
                                    var33.targetIndex = -1;
                                    var33.aBoolean2015 = false;
                                }
                            }

                            NamedFont.method1181();
                            setGameState(30);

                            for (int var6 = 0; var6 < 100; ++var6) {
                                renderedComponents[var6] = true;
                            }

                            processGameBoundsPacket();
                            PlayerEntity.update(input);
                            if (var29 != input.pos) {
                                throw new RuntimeException();
                            }
                        }
                    }
                }
            }
        } catch (IOException var21) {
            if (loginProcess < 1) {
                if (NpcEntity.port == HitsplatDefinition.anInt1929) {
                    NpcEntity.port = Bzip2Entry.anInt1579;
                } else {
                    NpcEntity.port = HitsplatDefinition.anInt1929;
                }

                ++loginProcess;
                loginStage = 0;
            } else {
                Login.processResponseCode(-2);
            }
        }
    }

    public void method751() {
        boolean var1 = false;

        int var2;
        int var5;
        while (!var1) {
            var1 = true;

            for (var2 = 0; var2 < ContextMenu.rowCount - 1; ++var2) {
                if (ContextMenu.opcodes[var2] < 1000 && ContextMenu.opcodes[var2 + 1] > 1000) {
                    String var3 = ContextMenu.targets[var2];
                    ContextMenu.targets[var2] = ContextMenu.targets[var2 + 1];
                    ContextMenu.targets[var2 + 1] = var3;
                    String var4 = ContextMenu.actions[var2];
                    ContextMenu.actions[var2] = ContextMenu.actions[var2 + 1];
                    ContextMenu.actions[var2 + 1] = var4;
                    var5 = ContextMenu.opcodes[var2];
                    ContextMenu.opcodes[var2] = ContextMenu.opcodes[var2 + 1];
                    ContextMenu.opcodes[var2 + 1] = var5;
                    var5 = ContextMenu.secondaryArgs[var2];
                    ContextMenu.secondaryArgs[var2] = ContextMenu.secondaryArgs[var2 + 1];
                    ContextMenu.secondaryArgs[var2 + 1] = var5;
                    var5 = ContextMenu.tertiaryArgs[var2];
                    ContextMenu.tertiaryArgs[var2] = ContextMenu.tertiaryArgs[var2 + 1];
                    ContextMenu.tertiaryArgs[var2 + 1] = var5;
                    var5 = ContextMenu.primaryArgs[var2];
                    ContextMenu.primaryArgs[var2] = ContextMenu.primaryArgs[var2 + 1];
                    ContextMenu.primaryArgs[var2 + 1] = var5;
                    boolean var6 = ContextMenu.prioritizedActions[var2];
                    ContextMenu.prioritizedActions[var2] = ContextMenu.prioritizedActions[var2 + 1];
                    ContextMenu.prioritizedActions[var2 + 1] = var6;
                    var1 = false;
                }
            }
        }

        if (AnimationFrameGroup.dragComponent == null) {
            if (draggedComponent == null) {
                int var16 = Mouse.clickMeta;
                int var7;
                int var8;
                if (ContextMenu.open) {
                    int var9;
                    int var10;
                    int var17;
                    if (var16 != 1 && (WorldMapObjectIcon.mouseCameraEnabled || var16 != 4)) {
                        var2 = Mouse.x;
                        var7 = Mouse.y;
                        if (var2 < ContextMenu.x - 10 || var2 > ContextMenu.width + ContextMenu.x + 10 || var7 < ContextMenu.y - 10 || var7 > ContextMenu.y + ContextMenu.height + 10) {
                            ContextMenu.open = false;
                            var8 = ContextMenu.x;
                            var5 = ContextMenu.y;
                            var17 = ContextMenu.width;
                            var9 = ContextMenu.height;

                            for (var10 = 0; var10 < renderedComponentCount; ++var10) {
                                if (interfaceWidths[var10] + interfacePositionsX[var10] > var8 && interfacePositionsX[var10] < var17 + var8 && interfaceHeights[var10] + interfacePositionsY[var10] > var5 && interfacePositionsY[var10] < var5 + var9) {
                                    renderedComponents[var10] = true;
                                }
                            }
                        }
                    }

                    if (var16 == 1 || !WorldMapObjectIcon.mouseCameraEnabled && var16 == 4) {
                        var2 = ContextMenu.x;
                        var7 = ContextMenu.y;
                        var8 = ContextMenu.width;
                        var5 = Mouse.clickX;
                        var17 = Mouse.clickY;
                        var9 = -1;

                        int var11;
                        for (var10 = 0; var10 < ContextMenu.rowCount; ++var10) {
                            var11 = (ContextMenu.rowCount - 1 - var10) * 15 + var7 + 31;
                            if (var5 > var2 && var5 < var8 + var2 && var17 > var11 - 13 && var17 < var11 + 3) {
                                var9 = var10;
                            }
                        }

                        if (var9 != -1) {
                            ContextMenu.processActionAt(var9);
                        }

                        ContextMenu.open = false;
                        var10 = ContextMenu.x;
                        var11 = ContextMenu.y;
                        int var12 = ContextMenu.width;
                        int var13 = ContextMenu.height;

                        for (int var14 = 0; var14 < renderedComponentCount; ++var14) {
                            if (interfacePositionsX[var14] + interfaceWidths[var14] > var10 && interfacePositionsX[var14] < var10 + var12 && interfaceHeights[var14] + interfacePositionsY[var14] > var11 && interfacePositionsY[var14] < var13 + var11) {
                                renderedComponents[var14] = true;
                            }
                        }
                    }
                } else {
                    var2 = ContextMenu.getLastRowIndex();
                    if ((var16 == 1 || !WorldMapObjectIcon.mouseCameraEnabled && var16 == 4) && var2 >= 0) {
                        var7 = ContextMenu.opcodes[var2];
                        if (var7 == 39 || var7 == 40 || var7 == 41 || var7 == 42 || var7 == 43 || var7 == 33 || var7 == 34 || var7 == 35 || var7 == 36 || var7 == 37 || var7 == 38 || var7 == 1005) {
                            var8 = ContextMenu.secondaryArgs[var2];
                            var5 = ContextMenu.tertiaryArgs[var2];
                            InterfaceComponent var15 = InterfaceComponent.lookup(var5);
                            if (InterfaceComponent.method650(InterfaceComponent.getConfig(var15)) || WorldMapAreaChunk_Sub3.method367(InterfaceComponent.getConfig(var15))) {
                                if (AnimationFrameGroup.dragComponent != null && !draggingComponent && ContextMenu.rowCount > 0 && !this.method731()) {
                                    SerializableProcessor.method459(draggingComponentX, draggingComponentY);
                                }

                                draggingComponent = false;
                                componentDragCycles = 0;
                                if (AnimationFrameGroup.dragComponent != null) {
                                    InterfaceComponent.repaint(AnimationFrameGroup.dragComponent);
                                }

                                AnimationFrameGroup.dragComponent = InterfaceComponent.lookup(var5);
                                draggingComponentSourceIndex = var8;
                                draggingComponentX = Mouse.clickX;
                                draggingComponentY = Mouse.clickY;
                                if (var2 >= 0) {
                                    ParameterDefinition.method516(var2);
                                }

                                InterfaceComponent.repaint(AnimationFrameGroup.dragComponent);
                                return;
                            }
                        }
                    }

                    if ((var16 == 1 || !WorldMapObjectIcon.mouseCameraEnabled && var16 == 4) && this.method731()) {
                        var16 = 2;
                    }

                    if ((var16 == 1 || !WorldMapObjectIcon.mouseCameraEnabled && var16 == 4) && ContextMenu.rowCount > 0) {
                        ContextMenu.processActionAt(var2);
                    }

                    if (var16 == 2 && ContextMenu.rowCount > 0) {
                        this.openMenu(Mouse.clickX, Mouse.clickY);
                    }
                }

            }
        }
    }

    public void method732() {
        InterfaceComponent.repaint(draggedComponent);
        ++CursorEntities.anInt654;
        if (processingComponentDrag && processingComponentDragTopLevel) {
            int var1 = Mouse.x;
            int var2 = Mouse.y;
            var1 -= currentComponentDragX;
            var2 -= currentComponentDragY;
            if (var1 < anInt1060) {
                var1 = anInt1060;
            }

            if (var1 + draggedComponent.width > anInt1060 + topLevelOfDraggedComponent.width) {
                var1 = anInt1060 + topLevelOfDraggedComponent.width - draggedComponent.width;
            }

            if (var2 < anInt1069) {
                var2 = anInt1069;
            }

            if (var2 + draggedComponent.height > anInt1069 + topLevelOfDraggedComponent.height) {
                var2 = anInt1069 + topLevelOfDraggedComponent.height - draggedComponent.height;
            }

            int var3 = var1 - anInt1068;
            int var4 = var2 - anInt1073;
            int var5 = draggedComponent.dragArea;
            if (CursorEntities.anInt654 > draggedComponent.dragAreaThreshold && (var3 > var5 || var3 < -var5 || var4 > var5 || var4 < -var5)) {
                aBoolean1062 = true;
            }

            int var6 = var1 - anInt1060 + topLevelOfDraggedComponent.insetX;
            int var7 = var2 - anInt1069 + topLevelOfDraggedComponent.insetY;
            ScriptEvent var8;
            if (draggedComponent.dragListeners != null && aBoolean1062) {
                var8 = new ScriptEvent();
                var8.component = draggedComponent;
                var8.mouseX = var6;
                var8.mouseY = var7;
                var8.args = draggedComponent.dragListeners;
                ScriptEvent.fire(var8);
            }

            if (Mouse.pressMeta == 0) {
                if (aBoolean1062) {
                    if (draggedComponent.dragReleaseListeners != null) {
                        var8 = new ScriptEvent();
                        var8.component = draggedComponent;
                        var8.mouseX = var6;
                        var8.mouseY = var7;
                        var8.dragTarget = draggedSpecialComponent;
                        var8.args = draggedComponent.dragReleaseListeners;
                        ScriptEvent.fire(var8);
                    }

                    if (draggedSpecialComponent != null && InterfaceComponent.getTopLevelComponent(draggedComponent) != null) {
                        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.DRAGGED_COMPONENT, netWriter.encryptor);
                        packet.buffer.ip2a(draggedSpecialComponent.subComponentIndex);
                        packet.buffer.ip2a(draggedSpecialComponent.itemId);
                        packet.buffer.ip2(draggedComponent.subComponentIndex);
                        packet.buffer.pirf4(draggedSpecialComponent.uid);
                        packet.buffer.p2a(draggedComponent.itemId);
                        packet.buffer.p4(draggedComponent.uid);
                        netWriter.writeLater(packet);
                    }
                } else if (this.method731()) {
                    this.openMenu(anInt1068 + currentComponentDragX, currentComponentDragY + anInt1073);
                } else if (ContextMenu.rowCount > 0) {
                    SerializableProcessor.method459(anInt1068 + currentComponentDragX, anInt1073 + currentComponentDragY);
                }

                draggedComponent = null;
            }

        } else {
            if (CursorEntities.anInt654 > 1) {
                draggedComponent = null;
            }

        }
    }

    public void init() {
        try {
            if (this.method937()) {
                for (int var1 = 0; var1 <= 19; ++var1) {
                    String var2 = this.getParameter(Integer.toString(var1));
                    if (var2 != null) {
                        switch (var1) {
                            case 1:
                                usingBufferedConnection = Integer.parseInt(var2) != 0;
                            case 2:
                            case 11:
                            case 13:
                            case 16:
                            default:
                                break;
                            case 3:
                                membersWorld = var2.equalsIgnoreCase("true");
                                break;
                            case 4:
                                if (anInt923 == -1) {
                                    anInt923 = Integer.parseInt(var2);
                                }
                                break;
                            case 5:
                                currentWorldMask = Integer.parseInt(var2);
                                break;
                            case 6:
                                WorldMapLabelSize.aClientLocale_525 = ClientLocale.valueOf(Integer.parseInt(var2));
                                break;
                            case 7:
                                devbuild = DevelopmentBuild.valueOf(Integer.parseInt(var2));
                                break;
                            case 8:
                                if (var2.equalsIgnoreCase("true")) {
                                }
                                break;
                            case 9:
                                Statics57.aString1162 = var2;
                                break;
                            case 10:
                                GameType[] var3 = new GameType[]{GameType.GAME3, GameType.RUNESCAPE, GameType.STELLARDAWN, GameType.OSRS, GameType.GAME4, GameType.GAME5};
                                Statics55.gameType = (GameType) EnumType.getByOrdinal(var3, Integer.parseInt(var2));
                                if (Statics55.gameType == GameType.OSRS) {
                                    PreciseWorldMapAreaChunk.nameLengthParameter = ClientParameter.A_CLIENT_PARAMETER___1801;
                                } else {
                                    PreciseWorldMapAreaChunk.nameLengthParameter = ClientParameter.A_CLIENT_PARAMETER___1799;
                                }
                                break;
                            case 12:
                                currentWorld = Integer.parseInt(var2);
                                break;
                            case 14:
                                WorldMapCacheArea.anInt130 = Integer.parseInt(var2);
                                break;
                            case 15:
                                gameType = Integer.parseInt(var2);
                                break;
                            case 17:
                                Server.slu = var2;
                        }
                    }
                }

                StockMarketOfferPriceComparator.method330();
                LoginStep.currentDomain = this.getCodeBase().getHost();
                String var4 = devbuild.name;
                byte var5 = 0;

                try {
                    BufferedFile.indexCount = 21;
                    AsyncConnection.anInt1210 = var5;

                    try {
                        DefaultOperatingSystemProvider.aString407 = System.getProperty("os.name");
                    } catch (Exception var19) {
                        DefaultOperatingSystemProvider.aString407 = "Unknown";
                    }

                    DefaultOperatingSystemProvider.osName = DefaultOperatingSystemProvider.aString407.toLowerCase();

                    try {
                        Statics5.userhome = System.getProperty("user.home");
                        if (Statics5.userhome != null) {
                            Statics5.userhome = Statics5.userhome + "/";
                        }
                    } catch (Exception ignored) {
                    }

                    try {
                        if (DefaultOperatingSystemProvider.osName.startsWith("win")) {
                            if (Statics5.userhome == null) {
                                Statics5.userhome = System.getenv("USERPROFILE");
                            }
                        } else if (Statics5.userhome == null) {
                            Statics5.userhome = System.getenv("HOME");
                        }

                        if (Statics5.userhome != null) {
                            Statics5.userhome = Statics5.userhome + "/";
                        }
                    } catch (Exception ignored) {
                    }

                    if (Statics5.userhome == null) {
                        Statics5.userhome = "~/";
                    }

                    Class114.cacheDirectories = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", Statics5.userhome, "/tmp/", ""};
                    aStringArray1533 = new String[]{".jagex_cache_" + AsyncConnection.anInt1210, ".file_store_" + AsyncConnection.anInt1210};

                    int var9;
                    File var10;
                    label176:
                    for (int var6 = 0; var6 < 4; ++var6) {
                        StockMarketOfferWorldComparator.cachePathFile = Statics5.method60("oldschool", var4, var6);
                        if (!StockMarketOfferWorldComparator.cachePathFile.exists()) {
                            StockMarketOfferWorldComparator.cachePathFile.mkdirs();
                        }

                        File[] var7 = StockMarketOfferWorldComparator.cachePathFile.listFiles();
                        if (var7 == null) {
                            break;
                        }

                        var9 = 0;

                        while (true) {
                            if (var9 >= var7.length) {
                                break label176;
                            }

                            var10 = var7[var9];

                            boolean var13;
                            try {
                                RandomAccessFile var11 = new RandomAccessFile(var10, "rw");
                                int var12 = var11.read();
                                var11.seek(0L);
                                var11.write(var12);
                                var11.seek(0L);
                                var11.close();
                                var13 = true;
                            } catch (Exception var16) {
                                var13 = false;
                            }

                            if (!var13) {
                                break;
                            }

                            ++var9;
                        }
                    }

                    BufferedFile.cachePathFile = StockMarketOfferWorldComparator.cachePathFile;
                    if (!BufferedFile.cachePathFile.exists()) {
                        throw new RuntimeException("");
                    }

                    BufferedFile.aBoolean830 = true;

                    try {
                        File var24 = new File(Statics5.userhome, "random.dat");
                        if (var24.exists()) {
                            BufferedFile.random = new BufferedFile(new DiskFile(var24, "rw", 25L), 24, 0);
                        } else {
                            label153:
                            for (int var14 = 0; var14 < aStringArray1533.length; ++var14) {
                                for (var9 = 0; var9 < Class114.cacheDirectories.length; ++var9) {
                                    var10 = new File(Class114.cacheDirectories[var9] + aStringArray1533[var14] + File.separatorChar + "random.dat");
                                    if (var10.exists()) {
                                        BufferedFile.random = new BufferedFile(new DiskFile(var10, "rw", 25L), 24, 0);
                                        break label153;
                                    }
                                }
                            }
                        }

                        if (BufferedFile.random == null) {
                            RandomAccessFile var25 = new RandomAccessFile(var24, "rw");
                            var9 = var25.read();
                            var25.seek(0L);
                            var25.write(var9);
                            var25.seek(0L);
                            var25.close();
                            BufferedFile.random = new BufferedFile(new DiskFile(var24, "rw", 25L), 24, 0);
                        }
                    } catch (IOException ignored) {
                    }

                    BufferedFile.dataFile = new BufferedFile(new DiskFile(BufferedFile.openRw("main_file_cache.dat2"), "rw", 1048576000L), 5200, 0);
                    BufferedFile.indexFile = new BufferedFile(new DiskFile(BufferedFile.openRw("main_file_cache.idx255"), "rw", 1048576L), 6000, 0);
                    BufferedFile.indexes = new BufferedFile[BufferedFile.indexCount];

                    for (int i = 0; i < BufferedFile.indexCount; ++i) {
                        BufferedFile.indexes[i] = new BufferedFile(new DiskFile(BufferedFile.openRw("main_file_cache.idx" + i), "rw", 1048576L), 6000, 0);
                    }
                } catch (Exception var21) {
                    sendError(null, var21);
                }

                instance = this;
                clientParameter4 = anInt923;
                this.method935();
            }
        } catch (RuntimeException var22) {
            throw Statics52.method348(var22, "init(" + ')');
        }
    }
}
