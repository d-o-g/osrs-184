package jag.game;

import jag.*;
import jag.audi.AudioRunnable;
import jag.audi.DefaultAudioSystemProvider;
import jag.commons.Skills;
import jag.commons.collection.*;
import jag.commons.input.Mouse;
import jag.game.menu.ContextMenu;
import jag.game.menu.ItemSelection;
import jag.game.relationship.FriendChatUser;
import jag.game.scene.SceneGraph;
import jag.game.scene.SceneOccluder;
import jag.game.scene.entity.*;
import jag.game.stockmarket.StockMarketOffer;
import jag.game.stockmarket.StockMarketOfferWorldComparator;
import jag.game.type.*;
import jag.graphics.*;
import jag.js5.ReferenceTable;
import jag.opcode.*;
import jag.script.ScriptEvent;
import jag.statics.*;
import jag.worldmap.WorldMapObjectIcon;

public class InterfaceComponent extends Node {

    public static final ReferenceCache<Sprite> sprites = new ReferenceCache<>(200);
    public static final ReferenceCache<Font> fonts = new ReferenceCache<>(20);
    public static final ReferenceCache<Model> models = new ReferenceCache<>(50);
    public static final ReferenceCache<ComponentSprite> specialSprites = new ReferenceCache<>(8);

    public static ReferenceTable aReferenceTable1375;
    public static ReferenceTable aReferenceTable364;

    public static boolean[] valid;

    public static boolean forceRepaint = false;

    public static int anInt1342;

    public InterfaceComponent[] subcomponents;

    public Object[] cs2Listeners;
    public Object[] anObjectArray1400;
    public Object[] scrollListeners;
    public Object[] dragListeners;
    public Object[] targetExitListeners;
    public Object[] dragReleaseListeners;
    public Object[] targetEnterListeners;
    public Object[] mouseExitListeners;
    public Object[] clickListeners;
    public Object[] varTransmit;
    public Object[] pressListeners;
    public Object[] holdListeners;
    public Object[] releaseListeners;
    public Object[] mouseEnterListeners;
    public Object[] renderListeners;
    public Object[] initializationListeners;
    public Object[] hoverListeners;
    public Object[] itemTransmit;
    public Object[] skillTransmit;
    public Object[] anObjectArray1403;
    public Object[] anObjectArray1398;
    public Object[] anObjectArray1399;
    public Object[] anObjectArray1396;
    public Object[] anObjectArray1390;
    public Object[] anObjectArray1397;
    public Object[] anObjectArray1393;
    public Object[] anObjectArray1391;
    public Object[] anObjectArray1394;

    public String[] actions;
    public String[] tableActions;

    public byte[][] aByteArrayArray1365;
    public byte[][] aByteArrayArray1363;

    public int[][] cs1Opcodes;
    public int[] cs1Types;
    public int[] cs1Values;

    public int[] xSprites;
    public int[] ySprites;
    public int[] spriteIds;

    public int[] keyRate1;
    public int[] keyRate2;
    public int[] keyCycles;

    public int[] varTriggers;
    public int[] itemTriggers;
    public int[] skillTriggers;

    public int[] itemIds;
    public int[] itemStackSizes;

    public ComponentFillType fillType;

    public InterfaceComponent parent;

    public String toolTip;
    public String selectedAction;
    public String spellName;
    public String name;
    public String text;
    public String enabledText;

    public boolean format;
    public boolean decodedObjects;
    public boolean flippedVertically;
    public boolean flippedHorizontally;
    public boolean explicitlyHidden;
    public boolean aBoolean1372;
    public boolean noClickThrough;
    public boolean noScrollThrough;
    public boolean hovered;
    public boolean clicked;
    public boolean filled;
    public boolean tileSprites;
    public boolean invertDivider;
    public boolean prioritizeMenuOptions;
    public boolean scrollBar;
    public boolean textShadowed;
    public boolean perpendicular;
    public boolean transparent;

    public int enabledMaterialId;
    public int buttonType;
    public int parentUid;
    public int renderCycle;
    public int fontId;
    public int materialId;
    public int modelType;
    public int width;
    public int uid;
    public int modelId;
    public int type;
    public int height;
    public int shadowColor;
    public int boundsIndex;
    public int subComponentIndex;
    public int xLayout;
    public int xAlignment;
    public int borderThickness;
    public int xMargin;
    public int baseWidth;
    public int relativeX;
    public int clientcode;
    public int yAlignment;
    public int yLayout;
    public int relativeY;
    public int yMargin;
    public int baseHeight;
    public int scaleX;
    public int animation;
    public int itemId;
    public int scaleY;
    public int enabledAnimation;
    public int rotationKey;
    public int config;
    public int xRotation;
    public int alpha;
    public int animationFrameCycle;
    public int zRotation;
    public int xPadding;
    public int yPadding;
    public int animationFrame;
    public int detour;
    public int insetX;
    public int insetY;
    public int viewportWidth;
    public int viewportHeight;
    public int dragArea;
    public int foreground;
    public int dragAreaThreshold;
    public int enabledForeground;
    public int hoverForeground;
    public int enabledHoverForeground;
    public int enabledAlpha;
    public int lineWidth;
    public int spriteId;
    public int horizontalMargin;
    public int verticalMargin;
    public int textSpacing;
    public int modelOffsetX;
    public int modelOffsetY;
    public int yRotation;
    public int modelZoom;
    public int scaleZ;
    public int itemStackSizeMode;
    public int itemStackSize;
    public int anInt1406;
    public int anInt1413;
    public int anInt1408;
    public int anInt1411;

    int enabledModelType;
    int enabledModelId;

    public InterfaceComponent() {
        format = false;
        uid = -1;
        subComponentIndex = -1;
        buttonType = 0;
        clientcode = 0;
        xLayout = 0;
        yLayout = 0;
        xAlignment = 0;
        yAlignment = 0;
        xMargin = 0;
        yMargin = 0;
        baseWidth = 0;
        baseHeight = 0;
        relativeX = 0;
        relativeY = 0;
        width = 0;
        height = 0;
        scaleX = 1;
        scaleY = 1;
        parentUid = -1;
        explicitlyHidden = false;
        insetX = 0;
        insetY = 0;
        viewportWidth = 0;
        viewportHeight = 0;
        foreground = 0;
        enabledForeground = 0;
        hoverForeground = 0;
        enabledHoverForeground = 0;
        filled = false;
        fillType = ComponentFillType.SOLID;
        alpha = 0;
        enabledAlpha = 0;
        lineWidth = 1;
        invertDivider = false;
        materialId = -1;
        enabledMaterialId = -1;
        spriteId = 0;
        tileSprites = false;
        borderThickness = 0;
        shadowColor = 0;
        modelType = 1;
        modelId = -1;
        enabledModelType = 1;
        enabledModelId = -1;
        animation = -1;
        enabledAnimation = -1;
        modelOffsetX = 0;
        modelOffsetY = 0;
        xRotation = 0;
        zRotation = 0;
        yRotation = 0;
        modelZoom = 100;
        scaleZ = 0;
        rotationKey = 0;
        perpendicular = false;
        transparent = false;
        itemStackSizeMode = 2;
        fontId = -1;
        text = "";
        enabledText = "";
        textSpacing = 0;
        horizontalMargin = 0;
        verticalMargin = 0;
        textShadowed = false;
        xPadding = 0;
        yPadding = 0;
        config = 0;
        aBoolean1372 = false;
        name = "";
        parent = null;
        dragArea = 0;
        dragAreaThreshold = 0;
        scrollBar = false;
        selectedAction = "";
        decodedObjects = false;
        detour = -1;
        spellName = "";
        toolTip = "Ok";
        itemId = -1;
        itemStackSize = 0;
        animationFrame = 0;
        animationFrameCycle = 0;
        hovered = false;
        clicked = false;
        anInt1406 = -1;
        anInt1413 = 0;
        anInt1408 = 0;
        anInt1411 = 0;
        boundsIndex = -1;
        renderCycle = -1;
        noClickThrough = false;
        noScrollThrough = false;
        prioritizeMenuOptions = false;
    }

    public static InterfaceComponent lookup(int uid, int subcomponent) {
        InterfaceComponent component = lookup(uid);
        if (subcomponent == -1) {
            return component;
        }
        return component != null && component.subcomponents != null && subcomponent < component.subcomponents.length ? component.subcomponents[subcomponent] : null;
    }

    public static void closeInterface() {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.BUTTON_CLOSE_ACTION, client.stream.encryptor);
        client.stream.writeLater(packet);
        for (SubInterface itf = client.subInterfaces.head(); itf != null; itf = client.subInterfaces.next()) {
            if (itf.type == 0 || itf.type == 3) {
                close(itf, true);
            }
        }

        if (client.pleaseWaitComponent != null) {
            invalidate(client.pleaseWaitComponent);
            client.pleaseWaitComponent = null;
        }
    }

    public static InterfaceComponent lookup(int uid) {
        int group = uid >> 16;
        int component = uid & 0xffff;
        if (client.interfaces[group] == null || client.interfaces[group][component] == null) {
            boolean loaded = load(group);
            if (!loaded) {
                return null;
            }
        }

        return client.interfaces[group][component];
    }

    public static InterfaceComponent getTopLevelComponent(InterfaceComponent src) {
        int depth = getScriptEventDepth(getConfig(src));
        if (depth == 0) {
            return null;
        }

        for (int i = 0; i < depth; ++i) {
            src = lookup(src.parentUid);
            if (src == null) {
                return null;
            }
        }

        return src;
    }

    public static int getScriptEventDepth(int config) {
        return config >> 17 & 7;
    }

    public static int getConfig(InterfaceComponent component) {
        IntegerNode node = client.interfaceConfigs.lookup(((long) component.uid << 32) + (long) component.subComponentIndex);
        return node != null ? node.value : component.config;
    }

    public static void renderInterface(int group,
            int minX, int minY,
            int maxX, int maxY,
            int parentX, int parentY,
            int boundsIndex) {
        if (load(group)) {
            StockMarketOffer.draggingInterface = null;
            renderComponents(client.interfaces[group], -1, minX, minY, maxX, maxY, parentX, parentY, boundsIndex);
            if (StockMarketOffer.draggingInterface != null) {
                renderComponents(StockMarketOffer.draggingInterface, -1412584499, minX, minY, maxX, maxY, anInt1342, SceneOccluder.anInt1913, boundsIndex);
                StockMarketOffer.draggingInterface = null;
            }

        } else {
            if (boundsIndex != -1) {
                client.renderedComponents[boundsIndex] = true;
            } else {
                for (int i = 0; i < 100; ++i) {
                    client.renderedComponents[i] = true;
                }
            }

        }
    }

    public static void renderComponents(
            InterfaceComponent[] components, int uid,
            int x, int y, int width, int height,
            int rootX, int rootY, int boundsIndex) {

        JagGraphics.setClip(x, y, width, height);
        JagGraphics3D.method499();

        for (InterfaceComponent component : components) {
            if (component != null && (component.parentUid == uid || uid == -1412584499 && component == client.draggedComponent)) {
                int nextBoundsIndex;
                if (boundsIndex == -1) {
                    client.interfacePositionsX[client.renderedComponentCount] = component.relativeX + rootX;
                    client.interfacePositionsY[client.renderedComponentCount] = component.relativeY + rootY;
                    client.interfaceWidths[client.renderedComponentCount] = component.width;
                    client.interfaceHeights[client.renderedComponentCount] = component.height;
                    nextBoundsIndex = ++client.renderedComponentCount - 1;
                } else {
                    nextBoundsIndex = boundsIndex;
                }

                component.boundsIndex = nextBoundsIndex;
                component.renderCycle = client.engineCycle;
                if (!component.format || !isExplicitlyHidden(component)) {
                    if (component.clientcode > 0) {
                        processAppearanceCode(component);
                    }

                    int absoluteX = component.relativeX + rootX;
                    int absoluteY = component.relativeY + rootY;
                    int alpha = component.alpha;
                    int var15;
                    int var16;
                    if (component == client.draggedComponent) {
                        if (uid != -1412584499 && !component.scrollBar) {
                            StockMarketOffer.draggingInterface = components;
                            anInt1342 = rootX;
                            SceneOccluder.anInt1913 = rootY;
                            continue;
                        }

                        if (client.aBoolean1062 && client.processingComponentDragTopLevel) {
                            var15 = Mouse.x;
                            var16 = Mouse.y;
                            var15 -= client.currentComponentDragX;
                            var16 -= client.currentComponentDragY;
                            if (var15 < client.anInt1060) {
                                var15 = client.anInt1060;
                            }

                            if (var15 + component.width > client.anInt1060 + client.topLevelOfDraggedComponent.width) {
                                var15 = client.anInt1060 + client.topLevelOfDraggedComponent.width - component.width;
                            }

                            if (var16 < client.anInt1069) {
                                var16 = client.anInt1069;
                            }

                            if (var16 + component.height > client.anInt1069 + client.topLevelOfDraggedComponent.height) {
                                var16 = client.anInt1069 + client.topLevelOfDraggedComponent.height - component.height;
                            }

                            absoluteX = var15;
                            absoluteY = var16;
                        }

                        if (!component.scrollBar) {
                            alpha = 128;
                        }
                    }

                    int var17;
                    int var18;
                    int var19;
                    int var20;
                    int var21;
                    int var22;
                    if (component.type == 2) {
                        var15 = absoluteX;
                        var16 = absoluteY;
                        var17 = width;
                        var18 = height;
                    } else if (component.type == 9) {
                        var19 = absoluteX;
                        var20 = absoluteY;
                        var21 = absoluteX + component.width;
                        var22 = absoluteY + component.height;
                        if (var21 < absoluteX) {
                            var19 = var21;
                            var21 = absoluteX;
                        }

                        if (var22 < absoluteY) {
                            var20 = var22;
                            var22 = absoluteY;
                        }

                        ++var21;
                        ++var22;
                        var15 = Math.max(var19, absoluteX);
                        var16 = Math.max(var20, absoluteY);
                        var17 = Math.min(var21, width);
                        var18 = Math.min(var22, height);
                    } else {
                        var19 = absoluteX + component.width;
                        var20 = absoluteY + component.height;
                        var15 = Math.max(absoluteX, absoluteX);
                        var16 = Math.max(absoluteY, absoluteY);
                        var17 = Math.min(var19, width);
                        var18 = Math.min(var20, height);
                    }

                    if (!component.format || var15 < var17 && var16 < var18) {
                        if (component.clientcode != 0) {
                            if (component.clientcode == 1336) {
                                if (client.displayFps) {
                                    absoluteY += 15;
                                    Font.p12full.method1151("Fps:" + client.anInt1292, absoluteX + component.width, absoluteY, 16776960, -1);
                                    absoluteY += 15;
                                    Runtime var39 = Runtime.getRuntime();
                                    var20 = (int) ((var39.totalMemory() - var39.freeMemory()) / 1024L);
                                    var21 = 16776960;
                                    if (var20 > 327680 && !client.lowMemory) {
                                        var21 = 16711680;
                                    }

                                    Font.p12full.method1151("Mem:" + var20 + "k", absoluteX + component.width, absoluteY, var21, -1);
                                }
                                continue;
                            }

                            if (component.clientcode == 1337) {
                                client.anInt1039 = absoluteX;
                                client.anInt1038 = absoluteY;
                                SceneGraph.renderEntities(absoluteX, absoluteY, component.width, component.height);
                                client.renderedComponents[component.boundsIndex] = true;
                                JagGraphics.setClip(absoluteX, absoluteY, width, height);
                                continue;
                            }

                            if (component.clientcode == 1338) {
                                SceneGraph.renderMinimap(component, absoluteX, absoluteY, nextBoundsIndex);
                                JagGraphics.setClip(absoluteX, absoluteY, width, height);
                                continue;
                            }

                            if (component.clientcode == 1339) {
                                AudioRunnable.renderCompass(component, absoluteX, absoluteY);
                                JagGraphics.setClip(absoluteX, absoluteY, width, height);
                                continue;
                            }

                            if (component.clientcode == 1400) {
                                client.worldMap.method1268(absoluteX, absoluteY, component.width, component.height, client.engineCycle);
                            }

                            if (component.clientcode == 1401) {
                                client.worldMap.method1269(absoluteX, absoluteY, component.width, component.height);
                            }

                            if (component.clientcode == 1402) {
                                AsyncOutputStream.loginScreenEffect.render(absoluteX, client.engineCycle);
                            }
                        }

                        if (component.type == 0) {
                            if (!component.format && isExplicitlyHidden(component) && component != OldConnection.hoveredComponent) {
                                continue;
                            }

                            if (!component.format) {
                                if (component.insetY > component.viewportHeight - component.height) {
                                    component.insetY = component.viewportHeight - component.height;
                                }

                                if (component.insetY < 0) {
                                    component.insetY = 0;
                                }
                            }

                            renderComponents(components, component.uid, var15, var16, var17, var18, absoluteX - component.insetX, absoluteY - component.insetY, nextBoundsIndex);
                            if (component.subcomponents != null) {
                                renderComponents(component.subcomponents, component.uid, var15, var16, var17, var18, absoluteX - component.insetX, absoluteY - component.insetY, nextBoundsIndex);
                            }

                            SubInterface var24 = client.subInterfaces.lookup(component.uid);
                            if (var24 != null) {
                                renderInterface(var24.id, var15, var16, var17, var18, absoluteX, absoluteY, nextBoundsIndex);
                            }

                            JagGraphics.setClip(absoluteX, absoluteY, width, height);
                            JagGraphics3D.method499();
                        }

                        if (client.resizableMode || client.aBooleanArray1083[nextBoundsIndex] || client.redrawMode > 1) {
                            if (component.type == 0 && !component.format && component.viewportHeight > component.height) {
                                GameShell.drawScrollbar(absoluteX + component.width, absoluteY, component.insetY, component.height, component.viewportHeight);
                            }

                            if (component.type != 1) {
                                int var23;
                                int var25;
                                int var26;
                                int var27;
                                if (component.type == 2) {
                                    var19 = 0;

                                    for (var20 = 0; var20 < component.baseHeight; ++var20) {
                                        for (var21 = 0; var21 < component.baseWidth; ++var21) {
                                            var22 = absoluteX + var21 * (component.xPadding + 32);
                                            var23 = absoluteY + var20 * (component.yPadding + 32);
                                            if (var19 < 20) {
                                                var22 += component.xSprites[var19];
                                                var23 += component.ySprites[var19];
                                            }

                                            if (component.itemIds[var19] <= 0) {
                                                if (component.spriteIds != null && var19 < 20) {
                                                    Sprite var43 = component.method961(var19);
                                                    if (var43 != null) {
                                                        var43.renderAlphaAt(var22, var23);
                                                    } else if (forceRepaint) {
                                                        invalidate(component);
                                                    }
                                                }
                                            } else {
                                                boolean var40 = false;
                                                boolean var41 = false;
                                                var27 = component.itemIds[var19] - 1;
                                                if (var22 + 32 > absoluteX && var22 < width && var23 + 32 > absoluteY && var23 < height || component == AnimationFrameGroup.dragComponent && var19 == client.draggingComponentSourceIndex) {
                                                    Sprite var28;
                                                    if (ItemSelection.state == 1 && var19 == ItemSelection.id && component.uid == ItemSelection.uid) {
                                                        var28 = ItemDefinition.getSprite(var27, component.itemStackSizes[var19], 2, 0, 2, false);
                                                    } else {
                                                        var28 = ItemDefinition.getSprite(var27, component.itemStackSizes[var19], 1, 3153952, 2, false);
                                                    }

                                                    if (var28 != null) {
                                                        if (component == AnimationFrameGroup.dragComponent && var19 == client.draggingComponentSourceIndex) {
                                                            var25 = Mouse.x - client.draggingComponentX;
                                                            var26 = Mouse.y - client.draggingComponentY;
                                                            if (var25 < 5 && var25 > -5) {
                                                                var25 = 0;
                                                            }

                                                            if (var26 < 5 && var26 > -5) {
                                                                var26 = 0;
                                                            }

                                                            if (client.componentDragCycles < 5) {
                                                                var25 = 0;
                                                                var26 = 0;
                                                            }

                                                            var28.method832(var22 + var25, var23 + var26, 128);
                                                            if (uid != -1) {
                                                                InterfaceComponent var29 = components[uid & 65535];
                                                                int var30;
                                                                if (var23 + var26 < JagGraphics.drawingAreaTop && var29.insetY > 0) {
                                                                    var30 = (JagGraphics.drawingAreaTop - var23 - var26) * client.anInt972 / 3;
                                                                    if (var30 > client.anInt972 * 10) {
                                                                        var30 = client.anInt972 * 10;
                                                                    }

                                                                    if (var30 > var29.insetY) {
                                                                        var30 = var29.insetY;
                                                                    }

                                                                    var29.insetY -= var30;
                                                                    client.draggingComponentY += var30;
                                                                    invalidate(var29);
                                                                }

                                                                if (var23 + var26 + 32 > JagGraphics.drawingAreaRight && var29.insetY < var29.viewportHeight - var29.height) {
                                                                    var30 = (var26 + var23 + 32 - JagGraphics.drawingAreaRight) * client.anInt972 / 3;
                                                                    if (var30 > client.anInt972 * 10) {
                                                                        var30 = client.anInt972 * 10;
                                                                    }

                                                                    if (var30 > var29.viewportHeight - var29.height - var29.insetY) {
                                                                        var30 = var29.viewportHeight - var29.height - var29.insetY;
                                                                    }

                                                                    var29.insetY += var30;
                                                                    client.draggingComponentY -= var30;
                                                                    invalidate(var29);
                                                                }
                                                            }
                                                        } else if (component == StockMarketOfferWorldComparator.anInterfaceComponent351 && var19 == client.anInt1015) {
                                                            var28.method832(var22, var23, 128);
                                                        } else {
                                                            var28.renderAlphaAt(var22, var23);
                                                        }
                                                    } else {
                                                        invalidate(component);
                                                    }
                                                }
                                            }

                                            ++var19;
                                        }
                                    }
                                } else if (component.type == 3) {
                                    if (Projectile.method1192(component)) {
                                        var19 = component.enabledForeground;
                                        if (component == OldConnection.hoveredComponent && component.enabledHoverForeground != 0) {
                                            var19 = component.enabledHoverForeground;
                                        }
                                    } else {
                                        var19 = component.foreground;
                                        if (component == OldConnection.hoveredComponent && component.hoverForeground != 0) {
                                            var19 = component.hoverForeground;
                                        }
                                    }

                                    if (component.filled) {
                                        switch (component.fillType.type) {
                                            case 1:
                                                JagGraphics.method1376(absoluteX, absoluteY, component.width, component.height, component.foreground, component.enabledForeground);
                                                break;
                                            case 2:
                                                JagGraphics.method1359(absoluteX, absoluteY, component.width, component.height, component.foreground, component.enabledForeground, 255 - (component.alpha & 255), 255 - (component.enabledAlpha & 255));
                                                break;
                                            default:
                                                if (alpha == 0) {
                                                    JagGraphics.fillRect(absoluteX, absoluteY, component.width, component.height, var19);
                                                } else {
                                                    JagGraphics.method1370(absoluteX, absoluteY, component.width, component.height, var19, 256 - (alpha & 255));
                                                }
                                        }
                                    } else if (alpha == 0) {
                                        JagGraphics.method1372(absoluteX, absoluteY, component.width, component.height, var19);
                                    } else {
                                        JagGraphics.drawRect(absoluteX, absoluteY, component.width, component.height, var19, 256 - (alpha & 255));
                                    }
                                } else {
                                    Font var37;
                                    if (component.type == 4) {
                                        var37 = component.method957();
                                        if (var37 == null) {
                                            if (forceRepaint) {
                                                invalidate(component);
                                            }
                                        } else {
                                            String var45 = component.text;
                                            if (Projectile.method1192(component)) {
                                                var20 = component.enabledForeground;
                                                if (component == OldConnection.hoveredComponent && component.enabledHoverForeground != 0) {
                                                    var20 = component.enabledHoverForeground;
                                                }

                                                if (component.enabledText.length() > 0) {
                                                    var45 = component.enabledText;
                                                }
                                            } else {
                                                var20 = component.foreground;
                                                if (component == OldConnection.hoveredComponent && component.hoverForeground != 0) {
                                                    var20 = component.hoverForeground;
                                                }
                                            }

                                            if (component.format && component.itemId != -1) {
                                                ItemDefinition var46 = ItemDefinition.get(component.itemId);
                                                var45 = var46.name;
                                                if (var45 == null) {
                                                    var45 = "null";
                                                }

                                                if ((var46.stackable == 1 || component.itemStackSize != 1) && component.itemStackSize != -1) {
                                                    var45 = client.getColorTags(16748608) + var45 + "</col>" + " " + 'x' + Server.method149(component.itemStackSize);
                                                }
                                            }

                                            if (component == client.pleaseWaitComponent) {
                                                var45 = "Please wait...";
                                                var20 = component.foreground;
                                            }

                                            if (!component.format) {
                                                var45 = method1005(var45, component);
                                            }

                                            var37.method1149(var45, absoluteX, absoluteY, component.width, component.height, var20, component.textShadowed ? 0 : -1, component.horizontalMargin, component.verticalMargin, component.textSpacing);
                                        }
                                    } else if (component.type == 5) {
                                        Sprite var38;
                                        if (!component.format) {
                                            var38 = component.method958(Projectile.method1192(component));
                                            if (var38 != null) {
                                                var38.renderAlphaAt(absoluteX, absoluteY);
                                            } else if (forceRepaint) {
                                                invalidate(component);
                                            }
                                        } else {
                                            if (component.itemId != -1) {
                                                var38 = ItemDefinition.getSprite(component.itemId, component.itemStackSize, component.borderThickness, component.shadowColor, component.itemStackSizeMode, false);
                                            } else {
                                                var38 = component.method958(false);
                                            }

                                            if (var38 == null) {
                                                if (forceRepaint) {
                                                    invalidate(component);
                                                }
                                            } else {
                                                var20 = var38.anInt112;
                                                var21 = var38.anInt375;
                                                if (!component.tileSprites) {
                                                    var22 = component.width * 4096 / var20;
                                                    if (component.spriteId != 0) {
                                                        var38.method824(component.width / 2 + absoluteX, component.height / 2 + absoluteY, component.spriteId, var22);
                                                    } else if (alpha != 0) {
                                                        var38.method818(absoluteX, absoluteY, component.width, component.height, 256 - (alpha & 255));
                                                    } else if (var20 == component.width && var21 == component.height) {
                                                        var38.renderAlphaAt(absoluteX, absoluteY);
                                                    } else {
                                                        var38.method807(absoluteX, absoluteY, component.width, component.height);
                                                    }
                                                } else {
                                                    JagGraphics.method1364(absoluteX, absoluteY, absoluteX + component.width, absoluteY + component.height);
                                                    var22 = (var20 - 1 + component.width) / var20;
                                                    var23 = (var21 - 1 + component.height) / var21;

                                                    for (var25 = 0; var25 < var22; ++var25) {
                                                        for (var26 = 0; var26 < var23; ++var26) {
                                                            if (component.spriteId != 0) {
                                                                var38.method824(var20 / 2 + absoluteX + var25 * var20, var21 / 2 + absoluteY + var26 * var21, component.spriteId, 4096);
                                                            } else if (alpha != 0) {
                                                                var38.method832(absoluteX + var25 * var20, absoluteY + var26 * var21, 256 - (alpha & 255));
                                                            } else {
                                                                var38.renderAlphaAt(absoluteX + var25 * var20, absoluteY + var26 * var21);
                                                            }
                                                        }
                                                    }

                                                    JagGraphics.setClip(absoluteX, absoluteY, width, height);
                                                }
                                            }
                                        }
                                    } else {
                                        ItemDefinition var34;
                                        if (component.type == 6) {
                                            boolean var36 = Projectile.method1192(component);
                                            if (var36) {
                                                var20 = component.enabledAnimation;
                                            } else {
                                                var20 = component.animation;
                                            }

                                            Model var42 = null;
                                            var22 = 0;
                                            if (component.itemId != -1) {
                                                var34 = ItemDefinition.get(component.itemId);
                                                if (var34 != null) {
                                                    var34 = var34.method519(component.itemStackSize);
                                                    var42 = var34.getModel(1);
                                                    if (var42 != null) {
                                                        var42.computeBounds();
                                                        var22 = var42.height / 2;
                                                    } else {
                                                        invalidate(component);
                                                    }
                                                }
                                            } else if (component.modelType == 5) {
                                                if (component.modelId == 0) {
                                                    var42 = client.renderedAppearance.getModel(null, -1, null, -1);
                                                } else {
                                                    var42 = PlayerEntity.local.getModel();
                                                }
                                            } else if (var20 == -1) {
                                                var42 = component.method956(null, -1, var36, PlayerEntity.local.model);
                                                if (var42 == null && forceRepaint) {
                                                    invalidate(component);
                                                }
                                            } else {
                                                AnimationSequence var47 = AnimationSequence.get(var20);
                                                var42 = component.method956(var47, component.animationFrame, var36, PlayerEntity.local.model);
                                                if (var42 == null && forceRepaint) {
                                                    invalidate(component);
                                                }
                                            }

                                            JagGraphics3D.method637(component.width / 2 + absoluteX, component.height / 2 + absoluteY);
                                            var23 = JagGraphics3D.SIN_TABLE[component.xRotation] * component.modelZoom >> 16;
                                            var25 = JagGraphics3D.COS_TABLE[component.xRotation] * component.modelZoom >> 16;
                                            if (var42 != null) {
                                                if (!component.format) {
                                                    var42.method1289(0, component.zRotation, 0, component.xRotation, 0, var23, var25);
                                                } else {
                                                    var42.computeBounds();
                                                    if (component.perpendicular) {
                                                        var42.method1287(0, component.zRotation, component.yRotation, component.xRotation, component.modelOffsetX, var22 + var23 + component.modelOffsetY, var25 + component.modelOffsetY, component.modelZoom);
                                                    } else {
                                                        var42.method1289(0, component.zRotation, component.yRotation, component.xRotation, component.modelOffsetX, var23 + var22 + component.modelOffsetY, var25 + component.modelOffsetY);
                                                    }
                                                }
                                            }

                                            JagGraphics3D.method23();
                                        } else {
                                            if (component.type == 7) {
                                                var37 = component.method957();
                                                if (var37 == null) {
                                                    if (forceRepaint) {
                                                        invalidate(component);
                                                    }
                                                    continue;
                                                }

                                                var20 = 0;

                                                for (var21 = 0; var21 < component.baseHeight; ++var21) {
                                                    for (var22 = 0; var22 < component.baseWidth; ++var22) {
                                                        if (component.itemIds[var20] > 0) {
                                                            var34 = ItemDefinition.get(component.itemIds[var20] - 1);
                                                            String var31;
                                                            if (var34.stackable != 1 && component.itemStackSizes[var20] == 1) {
                                                                var31 = client.getColorTags(16748608) + var34.name + "</col>";
                                                            } else {
                                                                var31 = client.getColorTags(16748608) + var34.name + "</col>" + " " + 'x' + Server.method149(component.itemStackSizes[var20]);
                                                            }

                                                            var26 = absoluteX + var22 * (component.xPadding + 115);
                                                            var27 = absoluteY + (component.yPadding + 12) * var21;
                                                            if (component.horizontalMargin == 0) {
                                                                var37.drawString(var31, var26, var27, component.foreground, component.textShadowed ? 0 : -1);
                                                            } else if (component.horizontalMargin == 1) {
                                                                var37.method1154(var31, component.width / 2 + var26, var27, component.foreground, component.textShadowed ? 0 : -1);
                                                            } else {
                                                                var37.method1151(var31, var26 + component.width - 1, var27, component.foreground, component.textShadowed ? 0 : -1);
                                                            }
                                                        }

                                                        ++var20;
                                                    }
                                                }
                                            }

                                            if (component.type == 8 && component == Statics24.anInterfaceComponent1417 && client.anInt1041 == client.anInt1036) {
                                                var19 = 0;
                                                var20 = 0;
                                                Font var32 = Font.p12full;
                                                String var33 = component.text;

                                                String var44;
                                                for (var33 = method1005(var33, component); var33.length() > 0; var20 = var20 + var32.anInt375 + 1) {
                                                    var25 = var33.indexOf("<br>");
                                                    if (var25 != -1) {
                                                        var44 = var33.substring(0, var25);
                                                        var33 = var33.substring(var25 + 4);
                                                    } else {
                                                        var44 = var33;
                                                        var33 = "";
                                                    }

                                                    var26 = var32.textWidth(var44);
                                                    if (var26 > var19) {
                                                        var19 = var26;
                                                    }
                                                }

                                                var19 += 6;
                                                var20 += 7;
                                                var25 = absoluteX + component.width - 5 - var19;
                                                var26 = absoluteY + component.height + 5;
                                                if (var25 < absoluteX + 5) {
                                                    var25 = absoluteX + 5;
                                                }

                                                if (var25 + var19 > width) {
                                                    var25 = width - var19;
                                                }

                                                if (var26 + var20 > height) {
                                                    var26 = height - var20;
                                                }

                                                JagGraphics.fillRect(var25, var26, var19, var20, 16777120);
                                                JagGraphics.method1372(var25, var26, var19, var20, 0);
                                                var33 = component.text;
                                                var27 = var26 + var32.anInt375 + 2;

                                                for (var33 = method1005(var33, component); var33.length() > 0; var27 = var27 + var32.anInt375 + 1) {
                                                    int var35 = var33.indexOf("<br>");
                                                    if (var35 != -1) {
                                                        var44 = var33.substring(0, var35);
                                                        var33 = var33.substring(var35 + 4);
                                                    } else {
                                                        var44 = var33;
                                                        var33 = "";
                                                    }

                                                    var32.drawString(var44, var25 + 3, var27, 0, -1);
                                                }
                                            }

                                            if (component.type == 9) {
                                                if (component.invertDivider) {
                                                    var19 = absoluteX;
                                                    var20 = absoluteY + component.height;
                                                    var21 = absoluteX + component.width;
                                                    var22 = absoluteY;
                                                } else {
                                                    var19 = absoluteX;
                                                    var20 = absoluteY;
                                                    var21 = absoluteX + component.width;
                                                    var22 = absoluteY + component.height;
                                                }

                                                if (component.lineWidth == 1) {
                                                    JagGraphics.drawDiagonalLine(var19, var20, var21, var22, component.foreground);
                                                } else {
                                                    DefaultAudioSystemProvider.method98(var19, var20, var21, var22, component.foreground, component.lineWidth);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static String method1005(String str, InterfaceComponent component) {
        if (str.contains("%")) {
            for (int i = 1; i <= 5; ++i) {
                while (true) {
                    int index = str.indexOf("%" + i);
                    if (index == -1) {
                        break;
                    }

                    String prefix = str.substring(0, index);
                    int value = processCs1(component, i - 1);
                    String infix;
                    if (value < 999999999) {
                        infix = Integer.toString(value);
                    } else {
                        infix = "*";
                    }

                    str = prefix + infix + str.substring(index + 2);
                }
            }
        }
        return str;
    }

    public static void method513(InterfaceComponent[] group, int parentUid) {
        for (InterfaceComponent component : group) {
            if (component != null && component.parentUid == parentUid
                    && (!component.format || !isExplicitlyHidden(component))) {
                int animationId;
                if (component.type == 0) {
                    if (!component.format && isExplicitlyHidden(component) && component != OldConnection.hoveredComponent) {
                        continue;
                    }

                    method513(group, component.uid);
                    if (component.subcomponents != null) {
                        method513(component.subcomponents, component.uid);
                    }

                    SubInterface sub = client.subInterfaces.lookup(component.uid);
                    if (sub != null) {
                        animationId = sub.id;
                        if (load(animationId)) {
                            method513(client.interfaces[animationId], -1);
                        }
                    }
                }

                if (component.type == 6) {
                    if (component.animation != -1 || component.enabledAnimation != -1) {
                        boolean var4 = Projectile.method1192(component);
                        if (var4) {
                            animationId = component.enabledAnimation;
                        } else {
                            animationId = component.animation;
                        }

                        if (animationId != -1) {
                            AnimationSequence animation = AnimationSequence.get(animationId);

                            for (component.animationFrameCycle += client.anInt972; component.animationFrameCycle > animation.frameLengths[component.animationFrame]; invalidate(component)) {
                                component.animationFrameCycle -= animation.frameLengths[component.animationFrame];
                                ++component.animationFrame;
                                if (component.animationFrame >= animation.frameIds.length) {
                                    component.animationFrame -= animation.loopOffset;
                                    if (component.animationFrame < 0 || component.animationFrame >= animation.frameIds.length) {
                                        component.animationFrame = 0;
                                    }
                                }
                            }
                        }
                    }

                    if (component.rotationKey != 0 && !component.format) {
                        int var8 = component.rotationKey >> 16;
                        animationId = component.rotationKey << 16 >> 16;
                        var8 *= client.anInt972;
                        animationId *= client.anInt972;
                        component.xRotation = var8 + component.xRotation & 2047;
                        component.zRotation = animationId + component.zRotation & 2047;
                        invalidate(component);
                    }
                }
            }
        }

    }

    public static void method830(int i) {
        if (i != -1) {
            if (valid[i]) {
                aReferenceTable1375.method914(i);
                if (client.interfaces[i] != null) {
                    boolean bool = true;

                    for (int j = 0; j < client.interfaces[i].length; ++j) {
                        if (client.interfaces[i][j] != null) {
                            if (client.interfaces[i][j].type != 2) {
                                client.interfaces[i][j] = null;
                            } else {
                                bool = false;
                            }
                        }
                    }

                    if (bool) {
                        client.interfaces[i] = null;
                    }

                    valid[i] = false;
                }
            }
        }
    }

    public static boolean load(int group) {
        if (valid[group]) {
            return true;
        }
        if (!aReferenceTable1375.method911(group)) {
            return false;
        }
        int componentCount = aReferenceTable1375.getFileCount(group);
        if (componentCount == 0) {
            valid[group] = true;
            return true;
        }
        if (client.interfaces[group] == null) {
            client.interfaces[group] = new InterfaceComponent[componentCount];
        }

        for (int i = 0; i < componentCount; ++i) {
            if (client.interfaces[group][i] == null) {
                byte[] data = aReferenceTable1375.unpack(group, i);
                if (data != null) {
                    client.interfaces[group][i] = new InterfaceComponent();
                    client.interfaces[group][i].uid = i + (group << 16);
                    if (data[0] == -1) {
                        client.interfaces[group][i].decode(new Buffer(data));
                    } else {
                        client.interfaces[group][i].decodeOldFormat(new Buffer(data));
                    }
                }
            }
        }

        valid[group] = true;
        return true;
    }

    public static void loadAnimable(int group) {
        if (load(group)) {
            InterfaceComponent[] components = client.interfaces[group];
            for (InterfaceComponent component : components) {
                if (component != null) {
                    component.animationFrame = 0;
                    component.animationFrameCycle = 0;
                }
            }
        }
    }

    public static void invalidate(InterfaceComponent component) {
        if (component.renderCycle == client.anInt1084) {
            client.renderedComponents[component.boundsIndex] = true;
        }
    }

    public static void executeCloseListeners(int group, int var1) {
        if (load(group)) {
            MouseRecorder.method265(client.interfaces[group], var1);
        }
    }

    public static void processAction(int actionIndex, int uid, int subcomponent, int itemId, String actionText) {
        InterfaceComponent component = lookup(uid, subcomponent);
        if (component != null) {
            if (component.cs2Listeners != null) {
                ScriptEvent event = new ScriptEvent();
                event.component = component;
                event.actionIndex = actionIndex;
                event.opbase = actionText;
                event.args = component.cs2Listeners;
                ScriptEvent.fire(event);
            }

            boolean var7 = true;
            if (component.clientcode > 0) {
                var7 = isAppearanceCode(component);
            }

            if (var7) {
                int config = getConfig(component);
                int action = actionIndex - 1;
                boolean actionEnabled = (config >> action + 1 & 1) != 0;
                if (actionEnabled) {
                    OutgoingPacket packet;
                    if (actionIndex == 1) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_0, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 2) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_1, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 3) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_2, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 4) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_3, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 5) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_4, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 6) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_5, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 7) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_6, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 8) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_7, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 9) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_8, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }

                    if (actionIndex == 10) {
                        packet = OutgoingPacket.prepare(ClientProt.INTERFACE_ACTION_9, client.stream.encryptor);
                        packet.buffer.p4(uid);
                        packet.buffer.p2(subcomponent);
                        packet.buffer.p2(itemId);
                        client.stream.writeLater(packet);
                    }
                }
            }
        }
    }

    public static void method177(InterfaceComponent var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        if (client.useDefaultScrollbar) {
            client.scrollbarWidth = 32;
        } else {
            client.scrollbarWidth = 0;
        }

        client.useDefaultScrollbar = false;
        int var7;
        if (Mouse.pressMeta == 1 || !WorldMapObjectIcon.mouseCameraEnabled && Mouse.pressMeta == 4) {
            if (var5 >= var1 && var5 < var1 + 16 && var6 >= var2 && var6 < var2 + 16) {
                var0.insetY -= 4;
                invalidate(var0);
            } else if (var5 >= var1 && var5 < var1 + 16 && var6 >= var3 + var2 - 16 && var6 < var3 + var2) {
                var0.insetY += 4;
                invalidate(var0);
            } else if (var5 >= var1 - client.scrollbarWidth && var5 < client.scrollbarWidth + var1 + 16 && var6 >= var2 + 16 && var6 < var3 + var2 - 16) {
                var7 = var3 * (var3 - 32) / var4;
                if (var7 < 8) {
                    var7 = 8;
                }

                int var8 = var6 - var2 - 16 - var7 / 2;
                int var9 = var3 - 32 - var7;
                var0.insetY = var8 * (var4 - var3) / var9;
                invalidate(var0);
                client.useDefaultScrollbar = true;
            }
        }

        if (client.mouseWheelPtr != 0) {
            var7 = var0.width;
            if (var5 >= var1 - var7 && var6 >= var2 && var5 < var1 + 16 && var6 <= var3 + var2) {
                var0.insetY += client.mouseWheelPtr * 45;
                invalidate(var0);
            }
        }

    }

    public static void updateSize(InterfaceComponent component, int parentWidth, int parentHeight, boolean fireInputScripts) {
        int width = component.width;
        int height = component.height;
        if (component.xAlignment == 0) {
            component.width = component.baseWidth;
        } else if (component.xAlignment == 1) {
            component.width = parentWidth - component.baseWidth;
        } else if (component.xAlignment == 2) {
            component.width = component.baseWidth * parentWidth >> 14;
        }

        if (component.yAlignment == 0) {
            component.height = component.baseHeight;
        } else if (component.yAlignment == 1) {
            component.height = parentHeight - component.baseHeight;
        } else if (component.yAlignment == 2) {
            component.height = parentHeight * component.baseHeight >> 14;
        }

        if (component.xAlignment == 4) {
            component.width = component.scaleX * component.height / component.scaleY;
        }

        if (component.yAlignment == 4) {
            component.height = component.scaleY * component.width / component.scaleX;
        }

        if (component.clientcode == 1337) {
            client.minimapComponent = component;
        }

        if (fireInputScripts && component.anObjectArray1400 != null && (width != component.width || height != component.height)) {
            ScriptEvent evt = new ScriptEvent();
            evt.component = component;
            evt.args = component.anObjectArray1400;
            client.inputOccuringEventScripts.add(evt);
        }
    }

    public static boolean isExplicitlyHidden(InterfaceComponent component) {
        return component.explicitlyHidden;
    }

    public static void updatePosition(InterfaceComponent component, int parentWidth, int parentHeight) {
        if (component.xLayout == 0) {
            component.relativeX = component.xMargin;
        } else if (component.xLayout == 1) {
            component.relativeX = component.xMargin + (parentWidth - component.width) / 2;
        } else if (component.xLayout == 2) {
            component.relativeX = parentWidth - component.width - component.xMargin;
        } else if (component.xLayout == 3) {
            component.relativeX = component.xMargin * parentWidth >> 14;
        } else if (component.xLayout == 4) {
            component.relativeX = (parentWidth - component.width) / 2 + (component.xMargin * parentWidth >> 14);
        } else {
            component.relativeX = parentWidth - component.width - (component.xMargin * parentWidth >> 14);
        }

        if (component.yLayout == 0) {
            component.relativeY = component.yMargin;
        } else if (component.yLayout == 1) {
            component.relativeY = (parentHeight - component.height) / 2 + component.yMargin;
        } else if (component.yLayout == 2) {
            component.relativeY = parentHeight - component.height - component.yMargin;
        } else if (component.yLayout == 3) {
            component.relativeY = parentHeight * component.yMargin >> 14;
        } else if (component.yLayout == 4) {
            component.relativeY = (parentHeight * component.yMargin >> 14) + (parentHeight - component.height) / 2;
        } else {
            component.relativeY = parentHeight - component.height - (parentHeight * component.yMargin >> 14);
        }
    }

    public static void processAppearanceCode(InterfaceComponent var0) {
        int var1 = var0.clientcode;
        if (var1 == 324) {
            if (client.anInt930 == -1) {
                client.anInt930 = var0.materialId;
                client.anInt931 = var0.enabledMaterialId;
            }

            if (client.renderedAppearance.female) {
                var0.materialId = client.anInt930;
            } else {
                var0.materialId = client.anInt931;
            }

        } else if (var1 == 325) {
            if (client.anInt930 == -1) {
                client.anInt930 = var0.materialId;
                client.anInt931 = var0.enabledMaterialId;
            }

            if (client.renderedAppearance.female) {
                var0.materialId = client.anInt931;
            } else {
                var0.materialId = client.anInt930;
            }

        } else if (var1 == 327) {
            var0.xRotation = 150;
            var0.zRotation = (int) (Math.sin((double) client.engineCycle / 40.0D) * 256.0D) & 2047;
            var0.modelType = 5;
            var0.modelId = 0;
        } else if (var1 == 328) {
            var0.xRotation = 150;
            var0.zRotation = (int) (Math.sin((double) client.engineCycle / 40.0D) * 256.0D) & 2047;
            var0.modelType = 5;
            var0.modelId = 1;
        }
    }

    public static void resizeGroup(InterfaceComponent[] group, int parentUid, int parentWidth, int parentHeight, boolean fireInputScripts) {
        for (InterfaceComponent component : group) {
            if (component == null || component.parentUid != parentUid) {
                continue;
            }

            updateSize(component, parentWidth, parentHeight, fireInputScripts);
            updatePosition(component, parentWidth, parentHeight);

            if (component.insetX > component.viewportWidth - component.width) {
                component.insetX = component.viewportWidth - component.width;
            }

            if (component.insetX < 0) {
                component.insetX = 0;
            }

            if (component.insetY > component.viewportHeight - component.height) {
                component.insetY = component.viewportHeight - component.height;
            }

            if (component.insetY < 0) {
                component.insetY = 0;
            }

            if (component.type == 0) {
                revalidateScroll(group, component, fireInputScripts);
            }
        }
    }

    public static String getAction(InterfaceComponent var0, int var1) {
        int var2 = getConfig(var0);
        boolean var3 = (var2 >> var1 + 1 & 1) != 0;
        if (!var3 && var0.cs2Listeners == null) {
            return null;
        }
        return var0.actions != null && var0.actions.length > var1 && var0.actions[var1] != null && var0.actions[var1].trim().length() != 0 ? var0.actions[var1] : null;
    }

    public static String getSelectedAction(InterfaceComponent var0) {
        if (getSpellTargets(getConfig(var0)) == 0) {
            return null;
        }
        return var0.selectedAction != null && var0.selectedAction.trim().length() != 0 ? var0.selectedAction : null;
    }

    public static void close(SubInterface itf, boolean updated) {
        int id = itf.id;
        int key = (int) itf.key;
        itf.unlink();
        if (updated) {
            method830(id);
        }

        for (IntegerNode cfg = client.interfaceConfigs.head(); cfg != null; cfg = client.interfaceConfigs.next()) {
            if ((long) id == (cfg.key >> 48 & 0xffffL)) {
                cfg.unlink();
            }
        }

        InterfaceComponent cmp = lookup(key);
        if (cmp != null) {
            invalidate(cmp);
        }

        ContextMenu.method317();
        if (client.rootInterfaceIndex != -1) {
            executeCloseListeners(client.rootInterfaceIndex, 1);
        }

    }

    public static void drag(InterfaceComponent component, int var1, int var2) {
        if (client.draggedComponent == null && !ContextMenu.open) {
            if (component != null) {
                InterfaceComponent parent = getTopLevelComponent(component);
                if (parent == null) {
                    parent = component.parent;
                }

                if (parent != null) {
                    client.draggedComponent = component;
                    parent = getTopLevelComponent(component);
                    if (parent == null) {
                        parent = component.parent;
                    }

                    client.topLevelOfDraggedComponent = parent;
                    client.currentComponentDragX = var1;
                    client.currentComponentDragY = var2;
                    CursorEntities.anInt654 = 0;
                    client.aBoolean1062 = false;
                    int var5 = ContextMenu.getLastRowIndex();
                    if (var5 != -1) {
                        ParameterDefinition.method516(var5);
                    }

                }
            }

        }
    }

    public static void loadAndInitialize(int var0) {
        if (var0 != -1) {
            if (load(var0)) {
                InterfaceComponent[] var1 = client.interfaces[var0];

                for (InterfaceComponent var3 : var1) {
                    if (var3.initializationListeners != null) {
                        ScriptEvent var4 = new ScriptEvent();
                        var4.component = var3;
                        var4.args = var3.initializationListeners;
                        ScriptEvent.process(var4, 5000000);
                    }
                }

            }
        }
    }

    public static int processCs1(InterfaceComponent component, int cs1OpcodeIndex) {
        if (component.cs1Opcodes != null && cs1OpcodeIndex < component.cs1Opcodes.length) {
            try {
                int[] opcodes = component.cs1Opcodes[cs1OpcodeIndex];
                int accumulated = 0;
                int ptr = 0;
                byte operation = 0;

                while (true) {
                    int opcode = opcodes[ptr++];
                    int value = 0;
                    byte nextOperation = 0;
                    if (opcode == 0) {
                        return accumulated;
                    }

                    if (opcode == 1) {
                        value = client.currentLevels[opcodes[ptr++]];
                    }

                    if (opcode == 2) {
                        value = client.levels[opcodes[ptr++]];
                    }

                    if (opcode == 3) {
                        value = client.experiences[opcodes[ptr++]];
                    }

                    if (opcode == 4) {
                        int componentUid = opcodes[ptr++] << 16;
                        componentUid += opcodes[ptr++];
                        InterfaceComponent targetComponent = lookup(componentUid);
                        int itemId = opcodes[ptr++];
                        if (itemId != -1 && (!ItemDefinition.get(itemId).members || client.membersWorld)) {
                            for (int i = 0; i < targetComponent.itemIds.length; ++i) {
                                if (itemId + 1 == targetComponent.itemIds[i]) {
                                    value += targetComponent.itemStackSizes[i];
                                }
                            }
                        }
                    }

                    if (opcode == 5) {
                        value = Vars.values[opcodes[ptr++]];
                    }

                    if (opcode == 6) {
                        value = Skills.EXP_TABLE[client.levels[opcodes[ptr++]] - 1];
                    }

                    if (opcode == 7) {
                        value = Vars.values[opcodes[ptr++]] * 100 / 46875;
                    }

                    if (opcode == 8) {
                        value = PlayerEntity.local.combatLevel;
                    }

                    if (opcode == 9) {
                        for (int i = 0; i < 25; ++i) {
                            if (Skills.ENABLED[i]) {
                                value += client.levels[i];
                            }
                        }
                    }

                    if (opcode == 10) {
                        int componentUid = opcodes[ptr++] << 16;
                        componentUid += opcodes[ptr++];
                        InterfaceComponent targetComponent = lookup(componentUid);
                        int itemId = opcodes[ptr++];
                        if (itemId != -1 && (!ItemDefinition.get(itemId).members || client.membersWorld)) {
                            for (int i = 0; i < targetComponent.itemIds.length; ++i) {
                                if (itemId + 1 == targetComponent.itemIds[i]) {
                                    value = 999999999;
                                    break;
                                }
                            }
                        }
                    }

                    if (opcode == 11) {
                        value = client.energy;
                    }

                    if (opcode == 12) {
                        value = client.weight;
                    }

                    if (opcode == 13) {
                        int varp = Vars.values[opcodes[ptr++]];
                        int shift = opcodes[ptr++];
                        value = (varp & 1 << shift) != 0 ? 1 : 0;
                    }

                    if (opcode == 14) {
                        int varbitIndex = opcodes[ptr++];
                        value = Varbit.get(varbitIndex);
                    }

                    if (opcode == 15) {
                        nextOperation = 1;
                    }

                    if (opcode == 16) {
                        nextOperation = 2;
                    }

                    if (opcode == 17) {
                        nextOperation = 3;
                    }

                    if (opcode == 18) {
                        value = client.baseX + (PlayerEntity.local.absoluteX >> 7);
                    }

                    if (opcode == 19) {
                        value = client.baseY + (PlayerEntity.local.absoluteY >> 7);
                    }

                    if (opcode == 20) {
                        value = opcodes[ptr++];
                    }

                    if (nextOperation == 0) {
                        if (operation == 0) {
                            accumulated += value;
                        }

                        if (operation == 1) {
                            accumulated -= value;
                        }

                        if (operation == 2 && value != 0) {
                            accumulated /= value;
                        }

                        if (operation == 3) {
                            accumulated *= value;
                        }

                        operation = 0;
                    } else {
                        operation = nextOperation;
                    }
                }
            } catch (Exception e) {
                return -1;
            }
        }
        return -2;
    }

    public static void setKeyRate(InterfaceComponent component, int index, int keyRate1, int keyRate2) {
        if (component.keyRate1 == null) {
            throw new RuntimeException();
        }

        component.keyRate1[index] = keyRate1;
        component.keyRate2[index] = keyRate2;
    }

    public static boolean method650(int var0) {
        return (var0 >> 28 & 1) != 0;
    }

    public static void resizeGroup(int group, int var1, int var2, boolean var3) {
        if (load(group)) {
            resizeGroup(client.interfaces[group], -1, var1, var2, var3);
        }
    }

    public static void revalidateScroll(InterfaceComponent[] var0, InterfaceComponent var1, boolean var2) {
        int var3 = var1.viewportWidth != 0 ? var1.viewportWidth : var1.width;
        int var4 = var1.viewportHeight != 0 ? var1.viewportHeight : var1.height;
        resizeGroup(var0, var1.uid, var3, var4, var2);
        if (var1.subcomponents != null) {
            resizeGroup(var1.subcomponents, var1.uid, var3, var4, var2);
        }

        SubInterface var5 = client.subInterfaces.lookup(var1.uid);
        if (var5 != null) {
            resizeGroup(var5.id, var3, var4, var2);
        }

        if (var1.clientcode == 1337) {

        }
    }

    public static boolean canDrag(int var0) {
        return (var0 >> 29 & 1) != 0;
    }

    public static int getSpellTargets(int value) {
        return value >> 11 & 63;
    }

    public static boolean isAppearanceCode(InterfaceComponent component) {
        int code = component.clientcode;
        if (code == 205) {
            client.logoutTimer = 250;
            return true;
        }

        if (code >= 300 && code <= 313) {
            int equipIndex = (code - 300) / 2;
            int type = code & 1;
            client.renderedAppearance.method1428(equipIndex, type == 1);
        }

        if (code >= 314 && code <= 323) {
            int appearanceIndex = (code - 314) / 2;
            int type = code & 1;
            client.renderedAppearance.method1432(appearanceIndex, type == 1);
        }

        if (code == 324) {
            client.renderedAppearance.setGender(false);
        }

        if (code == 325) {
            client.renderedAppearance.setGender(true);
        }

        if (code == 326) {
            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PLAYER_APPEARANCE_BUTTON, client.stream.encryptor);
            client.renderedAppearance.writeAppearanceTo(packet.buffer);
            client.stream.writeLater(packet);
            return true;
        }

        return false;
    }

    public Sprite method958(boolean enabled) {
        forceRepaint = false;

        int material = enabled ? enabledMaterialId : materialId;
        if (material == -1) {
            return null;
        }

        long key = ((long) shadowColor << 40) + ((flippedHorizontally ? 1L : 0L) << 39) + (long) material + ((long) borderThickness << 36) + ((flippedVertically ? 1L : 0L) << 38);
        Sprite sprite = sprites.get(key);
        if (sprite != null) {
            return sprite;
        }

        sprite = Sprite.get(aReferenceTable364, material, 0);
        if (sprite == null) {
            forceRepaint = true;
            return null;
        }
        if (flippedVertically) {
            sprite.method828();
        }

        if (flippedHorizontally) {
            sprite.method827();
        }

        if (borderThickness > 0) {
            sprite.method830(borderThickness);
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

        sprites.put(sprite, key);
        return sprite;
    }

    public void decodeOldFormat(Buffer buffer) {
        format = false;
        type = buffer.g1();
        buttonType = buffer.g1();
        clientcode = buffer.g2();
        xMargin = buffer.g2b();
        yMargin = buffer.g2b();
        baseWidth = buffer.g2();
        baseHeight = buffer.g2();
        alpha = buffer.g1();
        parentUid = buffer.g2();
        if (parentUid == 0xffff) {
            parentUid = -1;
        } else {
            parentUid += uid & 0xffff0000;
        }

        detour = buffer.g2();
        if (detour == 0xffff) {
            detour = -1;
        }

        int var2 = buffer.g1();
        if (var2 > 0) {
            cs1Types = new int[var2];
            cs1Values = new int[var2];

            for (int var3 = 0; var3 < var2; ++var3) {
                cs1Types[var3] = buffer.g1();
                cs1Values[var3] = buffer.g2();
            }
        }

        int size = buffer.g1();
        if (size > 0) {
            cs1Opcodes = new int[size][];
            for (int i = 0; i < size; ++i) {
                int group = buffer.g2();
                cs1Opcodes[i] = new int[group];
                for (int j = 0; j < group; ++j) {
                    cs1Opcodes[i][j] = buffer.g2();
                    if (cs1Opcodes[i][j] == 0xffff) {
                        cs1Opcodes[i][j] = -1;
                    }
                }
            }
        }

        if (type == 0) {
            viewportHeight = buffer.g2();
            explicitlyHidden = buffer.g1() == 1;
        }

        if (type == 1) {
            buffer.g2();
            buffer.g1();
        }

        if (type == 2) {
            itemIds = new int[baseWidth * baseHeight];
            itemStackSizes = new int[baseHeight * baseWidth];
            int var4 = buffer.g1();
            if (var4 == 1) {
                config |= 268435456;
            }

            int var5 = buffer.g1();
            if (var5 == 1) {
                config |= 0x40000000;
            }

            int var6 = buffer.g1();
            if (var6 == 1) {
                config |= Integer.MIN_VALUE;
            }

            int var7 = buffer.g1();
            if (var7 == 1) {
                config |= 0x20000000;
            }

            xPadding = buffer.g1();
            yPadding = buffer.g1();
            xSprites = new int[20];
            ySprites = new int[20];
            spriteIds = new int[20];

            for (int i = 0; i < 20; ++i) {
                int var9 = buffer.g1();
                if (var9 == 1) {
                    xSprites[i] = buffer.g2b();
                    ySprites[i] = buffer.g2b();
                    spriteIds[i] = buffer.g4();
                } else {
                    spriteIds[i] = -1;
                }
            }

            tableActions = new String[5];
            for (int i = 0; i < 5; ++i) {
                String action = buffer.gstr();
                if (action.length() > 0) {
                    tableActions[i] = action;
                    config |= 1 << i + 23;
                }
            }
        }

        if (type == 3) {
            filled = buffer.g1() == 1;
        }

        if (type == 4 || type == 1) {
            horizontalMargin = buffer.g1();
            verticalMargin = buffer.g1();
            textSpacing = buffer.g1();
            fontId = buffer.g2();
            if (fontId == 0xffff) {
                fontId = -1;
            }

            textShadowed = buffer.g1() == 1;
        }

        if (type == 4) {
            text = buffer.gstr();
            enabledText = buffer.gstr();
        }

        if (type == 1 || type == 3 || type == 4) {
            foreground = buffer.g4();
        }

        if (type == 3 || type == 4) {
            enabledForeground = buffer.g4();
            hoverForeground = buffer.g4();
            enabledHoverForeground = buffer.g4();
        }

        if (type == 5) {
            materialId = buffer.g4();
            enabledMaterialId = buffer.g4();
        }

        if (type == 6) {
            modelType = 1;
            modelId = buffer.g2();
            if (modelId == 0xffff) {
                modelId = -1;
            }

            enabledModelType = 1;
            enabledModelId = buffer.g2();
            if (enabledModelId == 0xffff) {
                enabledModelId = -1;
            }

            animation = buffer.g2();
            if (animation == 0xffff) {
                animation = -1;
            }

            enabledAnimation = buffer.g2();
            if (enabledAnimation == 0xffff) {
                enabledAnimation = -1;
            }

            modelZoom = buffer.g2();
            xRotation = buffer.g2();
            zRotation = buffer.g2();
        }

        if (type == 7) {
            itemIds = new int[baseHeight * baseWidth];
            itemStackSizes = new int[baseWidth * baseHeight];
            horizontalMargin = buffer.g1();
            fontId = buffer.g2();
            if (fontId == 0xffff) {
                fontId = -1;
            }

            textShadowed = buffer.g1() == 1;
            foreground = buffer.g4();
            xPadding = buffer.g2b();
            yPadding = buffer.g2b();

            int var4 = buffer.g1();
            if (var4 == 1) {
                config |= 0x40000000;
            }

            tableActions = new String[5];

            for (int i = 0; i < 5; ++i) {
                String action = buffer.gstr();
                if (action.length() > 0) {
                    tableActions[i] = action;
                    config |= 1 << i + 23;
                }
            }
        }

        if (type == 8) {
            text = buffer.gstr();
        }

        if (buttonType == 2 || type == 2) {
            selectedAction = buffer.gstr();
            spellName = buffer.gstr();
            int var4 = buffer.g2() & 0x3f;
            config |= var4 << 11;
        }

        if (buttonType == 1 || buttonType == 4 || buttonType == 5 || buttonType == 6) {
            toolTip = buffer.gstr();
            if (toolTip.length() == 0) {
                if (buttonType == 1) {
                    toolTip = "Ok";
                }

                if (buttonType == 4) {
                    toolTip = "Select";
                }

                if (buttonType == 5) {
                    toolTip = "Select";
                }

                if (buttonType == 6) {
                    toolTip = "Continue";
                }
            }
        }

        if (buttonType == 1 || buttonType == 4 || buttonType == 5) {
            config |= 0x400000;
        }

        if (buttonType == 6) {
            config |= 1;
        }
    }

    public void decode(Buffer buffer) {
        buffer.g1();
        format = true;
        type = buffer.g1();
        clientcode = buffer.g2();
        xMargin = buffer.g2b();
        yMargin = buffer.g2b();
        baseWidth = buffer.g2();
        if (type == 9) {
            baseHeight = buffer.g2b();
        } else {
            baseHeight = buffer.g2();
        }

        xAlignment = buffer.g1b();
        yAlignment = buffer.g1b();
        xLayout = buffer.g1b();
        yLayout = buffer.g1b();
        parentUid = buffer.g2();
        if (parentUid == 0xffff) {
            parentUid = -1;
        } else {
            parentUid += uid & 0xffff0000;
        }

        explicitlyHidden = buffer.g1() == 1;
        if (type == 0) {
            viewportWidth = buffer.g2();
            viewportHeight = buffer.g2();
            noClickThrough = buffer.g1() == 1;
        }

        if (type == 5) {
            materialId = buffer.g4();
            spriteId = buffer.g2();
            tileSprites = buffer.g1() == 1;
            alpha = buffer.g1();
            borderThickness = buffer.g1();
            shadowColor = buffer.g4();
            flippedVertically = buffer.g1() == 1;
            flippedHorizontally = buffer.g1() == 1;
        }

        if (type == 6) {
            modelType = 1;
            modelId = buffer.g2();
            if (modelId == 0xffff) {
                modelId = -1;
            }

            modelOffsetX = buffer.g2b();
            modelOffsetY = buffer.g2b();
            xRotation = buffer.g2();
            zRotation = buffer.g2();
            yRotation = buffer.g2();
            modelZoom = buffer.g2();
            animation = buffer.g2();
            if (animation == 0xffff) {
                animation = -1;
            }

            perpendicular = buffer.g1() == 1;
            buffer.g2();
            if (xAlignment != 0) {
                scaleZ = buffer.g2();
            }

            if (yAlignment != 0) {
                buffer.g2();
            }
        }

        if (type == 4) {
            fontId = buffer.g2();
            if (fontId == 0xffff) {
                fontId = -1;
            }

            text = buffer.gstr();
            textSpacing = buffer.g1();
            horizontalMargin = buffer.g1();
            verticalMargin = buffer.g1();
            textShadowed = buffer.g1() == 1;
            foreground = buffer.g4();
        }

        if (type == 3) {
            foreground = buffer.g4();
            filled = buffer.g1() == 1;
            alpha = buffer.g1();
        }

        if (type == 9) {
            lineWidth = buffer.g1();
            foreground = buffer.g4();
            invertDivider = buffer.g1() == 1;
        }

        config = buffer.g3();
        name = buffer.gstr();

        int actionCount = buffer.g1();
        if (actionCount > 0) {
            actions = new String[actionCount];
            for (int i = 0; i < actionCount; ++i) {
                actions[i] = buffer.gstr();
            }
        }

        dragArea = buffer.g1();
        dragAreaThreshold = buffer.g1();
        scrollBar = buffer.g1() == 1;
        selectedAction = buffer.gstr();
        initializationListeners = decodeObjects(buffer);
        mouseEnterListeners = decodeObjects(buffer);
        mouseExitListeners = decodeObjects(buffer);
        targetExitListeners = decodeObjects(buffer);
        targetEnterListeners = decodeObjects(buffer);
        varTransmit = decodeObjects(buffer);
        itemTransmit = decodeObjects(buffer);
        skillTransmit = decodeObjects(buffer);
        renderListeners = decodeObjects(buffer);
        cs2Listeners = decodeObjects(buffer);
        hoverListeners = decodeObjects(buffer);
        pressListeners = decodeObjects(buffer);
        clickListeners = decodeObjects(buffer);
        releaseListeners = decodeObjects(buffer);
        holdListeners = decodeObjects(buffer);
        dragListeners = decodeObjects(buffer);
        dragReleaseListeners = decodeObjects(buffer);
        scrollListeners = decodeObjects(buffer);
        varTriggers = decodeArray(buffer);
        itemTriggers = decodeArray(buffer);
        skillTriggers = decodeArray(buffer);
    }

    public ComponentSprite getSprite(boolean enabled) {
        if (enabledMaterialId == -1) {
            enabled = false;
        }

        int material = enabled ? this.enabledMaterialId : this.materialId;
        if (material == -1) {
            return null;
        }

        long key = ((this.flippedVertically ? 1L : 0L) << 38) + (long) material + ((long) this.borderThickness << 36) + ((this.flippedHorizontally ? 1L : 0L) << 39) + ((long) this.shadowColor << 40);
        ComponentSprite sprite = specialSprites.get(key);
        if (sprite != null) {
            return sprite;
        }

        Sprite var6 = method958(enabled);
        if (var6 == null) {
            return null;
        }
        Sprite var7 = var6.method823();
        int[] var8 = new int[var7.height];
        int[] var9 = new int[var7.height];

        for (int var10 = 0; var10 < var7.height; ++var10) {
            int var11 = 0;
            int var12 = var7.width;

            int var13;
            for (var13 = 0; var13 < var7.width; ++var13) {
                if (var7.pixels[var13 + var10 * var7.width] == 0) {
                    var11 = var13;
                    break;
                }
            }

            for (var13 = var7.width - 1; var13 >= var11; --var13) {
                if (var7.pixels[var13 + var10 * var7.width] == 0) {
                    var12 = var13 + 1;
                    break;
                }
            }

            var8[var10] = var11;
            var9[var10] = var12 - var11;
        }

        sprite = new ComponentSprite(var7.width, var7.height, var9, var8);
        specialSprites.put(sprite, key);
        return sprite;
    }

    public Font method957() {
        forceRepaint = false;
        if (fontId == -1) {
            return null;
        }
        Font var1 = fonts.get(fontId);
        if (var1 != null) {
            return var1;
        }
        var1 = FriendChatUser.method708(aReferenceTable364, SerializableLong.aReferenceTable645, fontId, 0);
        if (var1 != null) {
            fonts.put(var1, fontId);
        } else {
            forceRepaint = true;
        }

        return var1;
    }

    public Model method956(AnimationSequence var1, int var2, boolean var3, PlayerModel var4) {
        forceRepaint = false;
        int var5;
        int var6;
        if (var3) {
            var5 = enabledModelType;
            var6 = enabledModelId;
        } else {
            var5 = modelType;
            var6 = modelId;
        }

        if (var5 == 0) {
            return null;
        }
        if (var5 == 1 && var6 == -1) {
            return null;
        }
        Model var7 = models.get(var6 + (var5 << 16));
        if (var7 == null) {
            UnlitModel var8;
            if (var5 == 1) {
                var8 = UnlitModel.method982(OldConnectionTaskProcessor.aReferenceTable854, var6, 0);
                if (var8 == null) {
                    forceRepaint = true;
                    return null;
                }

                var7 = var8.light(64, 768, -50, -10, -50);
            }

            if (var5 == 2) {
                var8 = NpcDefinition.get(var6).getBaseModel();
                if (var8 == null) {
                    forceRepaint = true;
                    return null;
                }

                var7 = var8.light(64, 768, -50, -10, -50);
            }

            if (var5 == 3) {
                if (var4 == null) {
                    return null;
                }

                var8 = var4.method1427();
                if (var8 == null) {
                    forceRepaint = true;
                    return null;
                }

                var7 = var8.light(64, 768, -50, -10, -50);
            }

            if (var5 == 4) {
                ItemDefinition var9 = ItemDefinition.get(var6);
                var8 = var9.method531(10);
                if (var8 == null) {
                    forceRepaint = true;
                    return null;
                }

                var7 = var8.light(var9.ambient + 64, var9.contrast + 768, -50, -10, -50);
            }

            models.put(var7, var6 + (var5 << 16));
        }

        if (var1 != null) {
            var7 = var1.method879(var7, var2);
        }

        return var7;
    }

    public Sprite method961(int var1) {
        forceRepaint = false;
        if (var1 >= 0 && var1 < spriteIds.length) {
            int var2 = spriteIds[var1];
            if (var2 == -1) {
                return null;
            }
            Sprite var3 = sprites.get(var2);
            if (var3 != null) {
                return var3;
            }
            var3 = Sprite.get(aReferenceTable364, var2, 0);
            if (var3 != null) {
                sprites.put(var3, var2);
            } else {
                forceRepaint = true;
            }

            return var3;
        }
        return null;
    }

    public void method964(int var1, String var2) {
        if (actions == null || actions.length <= var1) {
            String[] var3 = new String[var1 + 1];
            if (actions != null) {
                System.arraycopy(actions, 0, var3, 0, actions.length);
            }

            actions = var3;
        }

        actions[var1] = var2;
    }

    Object[] decodeObjects(Buffer var1) {
        int len = var1.g1();
        if (len == 0) {
            return null;
        }

        Object[] arr = new Object[len];

        for (int i = 0; i < len; ++i) {
            int type = var1.g1();
            if (type == 0) {
                arr[i] = var1.g4();
            } else if (type == 1) {
                arr[i] = var1.gstr();
            }
        }

        decodedObjects = true;
        return arr;
    }

    int[] decodeArray(Buffer var1) {
        int var2 = var1.g1();
        if (var2 == 0) {
            return null;
        }
        int[] var3 = new int[var2];

        for (int var4 = 0; var4 < var2; ++var4) {
            var3[var4] = var1.g4();
        }

        return var3;
    }

    public void swapItem(int src, int dst) {
        int srcvalue = itemIds[dst];
        itemIds[dst] = itemIds[src];
        itemIds[src] = srcvalue;
        srcvalue = itemStackSizes[dst];
        itemStackSizes[dst] = itemStackSizes[src];
        itemStackSizes[src] = srcvalue;
    }
}




