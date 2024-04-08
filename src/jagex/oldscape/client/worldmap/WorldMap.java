package jagex.oldscape.client.worldmap;

import jagex.jagex3.client.input.keyboard.Keyboard;
import jagex.core.time.Clock;
import jagex.oldscape.client.client;
import jagex.oldscape.client.fonts.*;
import jagex.oldscape.client.minimenu.ContextMenuBuilder;
import jagex.jagex3.graphics.*;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.*;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;
import jagex.oldscape.script.ScriptEvent;

import java.util.*;

public class WorldMap {

  static final NamedFont VERDANA11 = NamedFont.VERDANA11;
  static final NamedFont VERDANA13 = NamedFont.VERDANA13;
  static final NamedFont VERDANA15 = NamedFont.VERDANA15;
  public static WorldMapHeatMap heatmap;

  final int[] actionOpcodes;
  public boolean drawMouseOverPosition;
  IndexedSprite[] mapSceneSprites;
  boolean forceRefresh;
  HashSet<Integer> toggledCategories;
  int refreshLimit;
  int anInt1678;
  int panelHeight;
  HashSet<Integer> toggledFunctions;
  int panelWidth;
  boolean disableFunctions;
  int refreshRate;
  HashMap<String, WorldMapCacheArea> areas;
  HashSet<Integer> toggledFeatures;
  HashSet<Integer> indicativeFunctions;
  WorldMapDefinitionLoader definitionLoader;
  Iterator<WorldMapIcon> iconIterator;
  int destinationX;
  float desiredZoom;
  Sprite aSprite1695;
  int centerX;
  WorldMapCacheArea activeArea;
  int anInt1676;
  ReferenceTable table;
  int destinationY;
  WorldMapPosition mouseOver;
  int centerY;
  float zoom;
  WorldMapController controller;
  HashMap<WorldMapLabelSize, BaseFont> sizeToFont;
  int panelX;
  int indicationLimit;
  WorldMapCacheArea aWorldMapCacheArea_1724;
  ReferenceTable scenery;
  WorldMapCacheArea mainland;
  int anInt1681;
  ReferenceTable ground;
  int indicationRate;
  HashSet<Integer> toggledCategoryObjects;
  int anInt1673;
  Font font;
  int anInt1699;
  int panelY;
  List<WorldMapIcon> icons;
  int anInt1697;
  int minPositionX;
  int minPositionY;
  boolean aBoolean1689;
  HashSet<WorldMapIcon> aHashSet1698;
  int anInt1680;
  int anInt1674;
  int anInt1704;
  long aLong1684;

  public WorldMap() {
    destinationX = -1;
    destinationY = -1;
    panelWidth = -1;
    panelHeight = -1;
    panelX = -1;
    panelY = -1;
    refreshLimit = 3;
    refreshRate = 50;
    forceRefresh = false;
    indicativeFunctions = null;
    indicationLimit = -1;
    indicationRate = -1;
    anInt1673 = -1;
    anInt1681 = -1;
    anInt1676 = -1;
    anInt1678 = -1;
    aBoolean1689 = true;
    toggledFunctions = new HashSet<>();
    toggledCategories = new HashSet<>();
    toggledCategoryObjects = new HashSet<>();
    toggledFeatures = new HashSet<>();
    disableFunctions = false;
    anInt1704 = 0;
    actionOpcodes = new int[]{1008, 1009, 1010, 1011, 1012};
    aHashSet1698 = new HashSet<>();
    mouseOver = null;
    drawMouseOverPosition = false;
    minPositionX = -1;
    minPositionY = -1;
    anInt1697 = -1;
  }

  boolean isDestinationPresent() {
    return destinationX != -1 && destinationY != -1;
  }

  public WorldMapCacheArea getAreaAt(int level, int x, int y) {
    Iterator<WorldMapCacheArea> iterator = areas.values().iterator();

    WorldMapCacheArea area;
    do {
      if (!iterator.hasNext()) {
        return null;
      }

      area = iterator.next();
    } while (!area.contains(level, x, y));

    return area;
  }

  float getZoom(int percent) {
    if (percent == 25) {
      return 1.0F;
    }

    if (percent == 37) {
      return 1.5F;
    }

    if (percent == 50) {
      return 2.0F;
    }

    if (percent == 75) {
      return 3.0F;
    }

    return percent == 100 ? 4.0F : 8.0F;
  }

  public WorldMapCacheArea getArea(int id) {
    Iterator<WorldMapCacheArea> iterator = areas.values().iterator();

    WorldMapCacheArea area;
    do {
      if (!iterator.hasNext()) {
        return null;
      }

      area = iterator.next();
    } while (area.getId() != id);

    return area;
  }

  void method1266(WorldMapCacheArea activeArea) {
    this.activeArea = activeArea;
    controller = new WorldMapController(mapSceneSprites, sizeToFont, scenery, ground);
    definitionLoader.method1309(activeArea.getJagName());
  }

  void setArea(WorldMapCacheArea area) {
    if (activeArea == null || area != activeArea) {
      method1266(area);
      setArea(-1, -1, -1);
    }
  }

  public int getPanelX() {
    return activeArea == null ? -1 : centerX + activeArea.getMinRegionX() * 64;
  }

  void zoomSmoothly() {
    if (heatmap != null) {
      zoom = desiredZoom;
    } else {
      if (zoom < desiredZoom) {
        zoom = Math.min(desiredZoom, zoom + zoom / 30.0F);
      }

      if (zoom > desiredZoom) {
        zoom = Math.max(desiredZoom, zoom - zoom / 30.0F);
      }

    }
  }

  void mapToggledFeatures() {
    toggledFeatures.clear();
    toggledFeatures.addAll(toggledFunctions);
    toggledFeatures.addAll(toggledCategoryObjects);
  }

  void setArea(int floor, int x, int y) {
    if (activeArea != null) {
      int[] position = activeArea.toScreen(floor, x, y);
      if (position == null) {
        position = activeArea.toScreen(activeArea.getLevel(), activeArea.getBaseX(), activeArea.getBaseY());
      }

      setPosition(position[0] - activeArea.getMinRegionX() * 64, position[1] - activeArea.getMinRegionY() * 64, true);
      destinationX = -1;
      destinationY = -1;
      zoom = getZoom(activeArea.getZoomPercent());
      desiredZoom = zoom;
      icons = null;
      iconIterator = null;
      controller.clearIcons();
    }
  }

  public int getPanelWidth() {
    return panelWidth;
  }

  public int getPanelY() {
    return activeArea == null ? -1 : centerY + activeArea.getMinRegionY() * 64;
  }

  void panToDestination() {
    if (isDestinationPresent()) {
      int dx = destinationX - centerX;
      int dy = destinationY - centerY;
      if (dx != 0) {
        dx /= Math.min(8, Math.abs(dx));
      }

      if (dy != 0) {
        dy /= Math.min(8, Math.abs(dy));
      }

      setPosition(dx + centerX, dy + centerY, true);
      if (destinationX == centerX && centerY == destinationY) {
        destinationX = -1;
        destinationY = -1;
      }

    }
  }

  final void method1281() {
    anInt1678 = -1;
    anInt1676 = -1;
    anInt1681 = -1;
    anInt1673 = -1;
  }

  public void setPositionRegional(int x, int y) {
    if (activeArea != null) {
      setPosition(x - activeArea.getMinRegionX() * 64, y - activeArea.getMinRegionY() * 64, true);
      destinationX = -1;
      destinationY = -1;
    }
  }

  public void setDestination(int x, int y) {
    if (activeArea != null && activeArea.contains(x, y)) {
      destinationX = x - activeArea.getMinRegionX() * 64;
      destinationY = y - activeArea.getMinRegionY() * 64;
    }
  }

  void method1276(int var1, int var2, boolean var3, long var4) {
    if (activeArea == null) {
      mouseOver = null;
      return;
    }

    int var6 = (int) ((float) centerX + ((float) (var1 - panelX) - (float) getPanelWidth() * zoom / 2.0F) / zoom);
    int var7 = (int) ((float) centerY - ((float) (var2 - panelY) - (float) getPanelHeight() * zoom / 2.0F) / zoom);
    mouseOver = activeArea.getPosition(var6 + activeArea.getMinRegionX() * 64, var7 + activeArea.getMinRegionY() * 64);
    if (mouseOver != null && var3) {
      boolean jmod = client.rights >= 2;
      if (jmod && Keyboard.heldKeys[Keyboard.Code.CTRL] && Keyboard.heldKeys[Keyboard.Code.SHIFT]) {
        client.clientProtHandler.processTeleport(mouseOver.x, mouseOver.y, mouseOver.floorLevel, false);
        return;
      }

      //check double click
      boolean clicked = true;
      if (aBoolean1689) {
        int var10 = var1 - anInt1680;
        int var11 = var2 - anInt1674;
        if (var4 - aLong1684 > 500L || var10 < -25 || var10 > 25 || var11 < -25 || var11 > 25) {
          clicked = false;
        }
      }

      if (clicked) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.JMOD_WORLDMAP_SELECTION, client.stream.encryptor);
        packet.buffer.p4(mouseOver.getHash());
        client.stream.writeLater(packet);
        aLong1684 = 0L;
      }
    }
  }

  final void setPosition(int var1, int var2, boolean var3) {
    centerX = var1;
    centerY = var2;
    Clock.now();
    if (var3) {
      method1281();
    }

  }

  public int getPanelHeight() {
    return panelHeight;
  }

  public WorldMapIcon getNextIcon() {
    if (iconIterator == null) {
      return null;
    }
    WorldMapIcon icon;
    do {
      if (!iconIterator.hasNext()) {
        return null;
      }

      icon = iconIterator.next();
    } while (icon.getMapFunction() == -1);
    return icon;
  }

  boolean method1263(int var1, int var2, int var3, int var4, int var5, int var6) {
    if (aSprite1695 == null) {
      return true;
    }
    if (aSprite1695.width == var1 && aSprite1695.height == var2) {
      if (controller.tileScale != anInt1699) {
        return true;
      }
      if (anInt1697 != client.anInt929) {
        return true;
      }
      if (var3 <= 0 && var4 <= 0) {
        return var3 + var1 < var5 || var2 + var4 < var6;
      }
      return true;
    }
    return true;
  }

  void drawLoadingScreen(int var1, int var2, int var3, int var4, int var5) {
    byte var6 = 20;
    int var7 = var3 / 2 + var1;
    int var8 = var4 / 2 + var2 - 18 - var6;
    JagGraphics.fillRect(var1, var2, var3, var4, -16777216);
    JagGraphics.drawRectOutline(var7 - 152, var8, 304, 34, -65536);
    JagGraphics.fillRect(var7 - 150, var8 + 2, var5 * 3, 30, -65536);
    font.method1154("Loading...", var7, var6 + var8, -1, -1);
  }

  public void poll(int var1, int var2, boolean var3, int var4, int var5, int var6, int var7) {
    if (definitionLoader.isLoaded()) {
      zoomSmoothly();
      panToDestination();
      if (var3) {
        int var8 = (int) Math.ceil((float) var6 / zoom);
        int var9 = (int) Math.ceil((float) var7 / zoom);
        List<WorldMapIcon> icons = controller.getIcons(centerX - var8 / 2 - 1, centerY - var9 / 2 - 1, var8 / 2 + centerX + 1, var9 / 2 + centerY + 1, var4, var5, var6, var7, var1, var2);
        HashSet<WorldMapIcon> var11 = new HashSet<>();
        Iterator<WorldMapIcon> var12;
        WorldMapIcon var13;
        ScriptEvent var14;
        WorldMapScriptEvent var15;
        for (var12 = icons.iterator(); var12.hasNext(); ScriptEvent.fire(var14)) {
          var13 = var12.next();
          var11.add(var13);
          var14 = new ScriptEvent();
          var15 = new WorldMapScriptEvent(var13.getMapFunction(), var13.min, var13.max);
          var14.setArgs(new Object[]{var15, var1, var2});
          if (aHashSet1698.contains(var13)) {
            var14.setType(17);
          } else {
            var14.setType(15);
          }
        }

        var12 = aHashSet1698.iterator();

        while (var12.hasNext()) {
          var13 = var12.next();
          if (!var11.contains(var13)) {
            var14 = new ScriptEvent();
            var15 = new WorldMapScriptEvent(var13.getMapFunction(), var13.min, var13.max);
            var14.setArgs(new Object[]{var15, var1, var2});
            var14.setType(16);
            ScriptEvent.fire(var14);
          }
        }

        aHashSet1698 = var11;
      }
    }
  }

  public void processAction(int var1, int var2, WorldMapPosition var3, WorldMapPosition var4) {
    ScriptEvent var5 = new ScriptEvent();
    WorldMapScriptEvent var6 = new WorldMapScriptEvent(var2, var3, var4);
    var5.setArgs(new Object[]{var6});
    switch (var1) {
      case 1008:
        var5.setType(10);
        break;
      case 1009:
        var5.setType(11);
        break;
      case 1010:
        var5.setType(12);
        break;
      case 1011:
        var5.setType(13);
        break;
      case 1012:
        var5.setType(14);
    }

    ScriptEvent.fire(var5);
  }

  public void buildMenuActions(int var1, int var2, int var3, int var4, int var5, int var6) {
    if (definitionLoader.isLoaded()) {
      int var7 = (int) Math.ceil((float) var3 / zoom);
      int var8 = (int) Math.ceil((float) var4 / zoom);
      List<WorldMapIcon> var9 = controller.getIcons(centerX - var7 / 2 - 1, centerY - var8 / 2 - 1, var7 / 2 + centerX + 1, var8 / 2 + centerY + 1, var1, var2, var3, var4, var5, var6);
      if (!var9.isEmpty()) {
        Iterator<WorldMapIcon> var10 = var9.iterator();

        boolean var13;
        do {
          if (!var10.hasNext()) {
            return;
          }

          WorldMapIcon var11 = var10.next();
          WorldMapFunction var12 = WorldMapFunction.get(var11.getMapFunction());
          var13 = false;

          for (int var14 = actionOpcodes.length - 1; var14 >= 0; --var14) {
            if (var12.menuActions[var14] != null) {
              ContextMenuBuilder.insertRow(var12.menuActions[var14], var12.aString1476, actionOpcodes[var14], var11.getMapFunction(), var11.min.getHash(), var11.max.getHash());
              var13 = true;
            }
          }
        } while (!var13);

      }
    }
  }

  void renderHeatMap(int var1, int var2, int var3, int var4, int var5, int var6) {
    if (heatmap != null) {
      int var7 = 512 / (controller.tileScale * 2);
      int var8 = var3 + 512;
      int var9 = var4 + 512;
      float var10 = 1.0F;
      var8 = (int) ((float) var8 / var10);
      var9 = (int) ((float) var9 / var10);
      int var11 = getPanelX() - var5 / 2 - var7;
      int var12 = getPanelY() - var6 / 2 - var7;
      int var13 = var1 - (var11 + var7 - minPositionX) * controller.tileScale;
      int var14 = var2 - controller.tileScale * (var7 - (var12 - minPositionY));
      if (method1263(var8, var9, var13, var14, var3, var4)) {
        if (aSprite1695 != null && aSprite1695.width == var8 && aSprite1695.height == var9) {
          Arrays.fill(aSprite1695.pixels, 0);
        } else {
          aSprite1695 = new Sprite(var8, var9);
        }

        minPositionX = getPanelX() - var5 / 2 - var7;
        minPositionY = getPanelY() - var6 / 2 - var7;
        anInt1699 = controller.tileScale;
        heatmap.transform(minPositionX, minPositionY, aSprite1695, (float) anInt1699 / var10);
        anInt1697 = client.anInt929;
        var13 = var1 - (var7 + var11 - minPositionX) * controller.tileScale;
        var14 = var2 - controller.tileScale * (var7 - (var12 - minPositionY));
      }

      JagGraphics.drawRectWithAlpha(var1, var2, var3, var4, 0, 128);
      if (1.0F == var10) {
        aSprite1695.method821(var13, var14, 192);
      } else {
        aSprite1695.method806(var13, var14, (int) (var10 * (float) var8), (int) ((float) var9 * var10), 192);
      }
    }

  }

  public void method1278(int var1, int var2, boolean var3, boolean var4) {
    long var5 = Clock.now();
    method1276(var1, var2, var4, var5);
    if (!isDestinationPresent() && (var4 || var3)) {
      if (var4) {
        anInt1676 = var1;
        anInt1678 = var2;
        anInt1673 = centerX;
        anInt1681 = centerY;
      }

      if (anInt1673 != -1) {
        int var7 = var1 - anInt1676;
        int var8 = var2 - anInt1678;
        setPosition(anInt1673 - (int) ((float) var7 / desiredZoom), (int) ((float) var8 / desiredZoom) + anInt1681, false);
      }
    } else {
      method1281();
    }

    if (var4) {
      aLong1684 = var5;
      anInt1680 = var1;
      anInt1674 = var2;
    }

  }

  public void method1268(int var1, int var2, int var3, int var4, int var5) {
    int[] var6 = new int[4];
    JagGraphics.method1366(var6);
    JagGraphics.setClip(var1, var2, var3 + var1, var2 + var4);
    JagGraphics.fillRect(var1, var2, var3, var4, -16777216);
    int var7 = definitionLoader.method1307();
    if (var7 < 100) {
      drawLoadingScreen(var1, var2, var3, var4, var7);
    } else {
      if (!controller.isLoaded()) {
        controller.method145(table, activeArea.getJagName(), client.membersWorld);
        if (!controller.isLoaded()) {
          return;
        }
      }

      if (indicativeFunctions != null) {
        ++indicationRate;
        if (indicationRate % refreshRate == 0) {
          indicationRate = 0;
          ++indicationLimit;
        }

        if (indicationLimit >= refreshLimit && !forceRefresh) {
          indicativeFunctions = null;
        }
      }

      int var8 = (int) Math.ceil((float) var3 / zoom);
      int var9 = (int) Math.ceil((float) var4 / zoom);
      controller.method142(centerX - var8 / 2, centerY - var9 / 2, var8 / 2 + centerX, var9 / 2 + centerY, var1, var3 + var1, var2 + var4);
      boolean var10;
      if (!disableFunctions) {
        var10 = false;
        if (var5 - anInt1704 > 100) {
          anInt1704 = var5;
          var10 = true;
        }

        controller.method141(centerX - var8 / 2, centerY - var9 / 2, var8 / 2 + centerX, var9 / 2 + centerY, var1, var3 + var1, var2 + var4, toggledFeatures, indicativeFunctions, indicationRate, refreshRate, var10);
      }

      renderHeatMap(var1, var2, var3, var4, var8, var9);
      var10 = client.rights >= 2;
      if (var10 && drawMouseOverPosition && mouseOver != null) {
        font.drawString("Coord: " + mouseOver, JagGraphics.drawingAreaLeft + 10, JagGraphics.drawingAreaTop + 20, 16776960, -1);
      }

      panelWidth = var8;
      panelHeight = var9;
      panelX = var1;
      panelY = var2;
      JagGraphics.method1373(var6);
    }
  }

  public void method1269(int var1, int var2, int var3, int var4) {
    if (definitionLoader.isLoaded()) {
      if (!controller.isLoaded()) {
        controller.method145(table, activeArea.getJagName(), client.membersWorld);
        if (!controller.isLoaded()) {
          return;
        }
      }

      controller.method140(var1, var2, var3, var4, indicativeFunctions, indicationRate, refreshRate);
    }
  }

  public void method1272() {
    WorldMapChunkDefinition.A_REFERENCE_NODE_TABLE___118.relegate(5);
  }

  public void initialize(ReferenceTable table, ReferenceTable scenery, ReferenceTable ground,
      Font font, HashMap<NamedFont, BaseFont> namedFonts, IndexedSprite[] mapSceneSprites) {
    this.mapSceneSprites = mapSceneSprites;
    this.table = table;
    this.scenery = scenery;
    this.ground = ground;
    this.font = font;
    this.sizeToFont = new HashMap<>();

    this.sizeToFont.put(WorldMapLabelSize.SMALL, namedFonts.get(VERDANA11));
    this.sizeToFont.put(WorldMapLabelSize.MEDIUM, namedFonts.get(VERDANA13));
    this.sizeToFont.put(WorldMapLabelSize.LARGE, namedFonts.get(VERDANA15));
    this.definitionLoader = new WorldMapDefinitionLoader(table);

    int groupId = table.getGroup(WorldMapCacheFeature.DETAILS.name);
    int[] fileIds = table.getFileIds(groupId);

    this.areas = new HashMap<>(fileIds.length);

    for (int id : fileIds) {
      Buffer buffer = new Buffer(table.unpack(groupId, id));
      WorldMapCacheArea area = new WorldMapCacheArea();
      area.decode(buffer, id);
      areas.put(area.getJagName(), area);
      if (area.isMainland()) {
        mainland = area;
      }
    }

    setArea(mainland);
    aWorldMapCacheArea_1724 = null;
  }

  public void setArea(int id) {
    WorldMapCacheArea area = getArea(id);
    if (area != null) {
      setArea(area);
    }
  }

  public int getDesiredZoom() {
    if ((double) desiredZoom == 1.0D) {
      return 25;
    }
    if ((double) desiredZoom == 1.5D) {
      return 37;
    }
    if ((double) desiredZoom == 2.0D) {
      return 50;
    }
    if ((double) desiredZoom == 3.0D) {
      return 75;
    }
    return (double) desiredZoom == 4.0D ? 100 : 200;
  }

  public void method1261(int var1, int var2, int var3, boolean var4) {
    WorldMapCacheArea area = getAreaAt(var1, var2, var3);
    if (area == null) {
      if (!var4) {
        return;
      }

      area = mainland;
    }

    boolean var6 = false;
    if (area != aWorldMapCacheArea_1724 || var4) {
      aWorldMapCacheArea_1724 = area;
      setArea(area);
      var6 = true;
    }

    if (var6 || var4) {
      setArea(var1, var2, var3);
    }

  }

  public void method1226(int var1) {
    desiredZoom = getZoom(var1);
  }

  public boolean method1246() {
    return definitionLoader.isLoaded();
  }

  public WorldMapPosition method1240() {
    return activeArea == null ? null : activeArea.getPosition(getPanelX(), getPanelY());
  }

  public void method1227(int var1, int var2, int var3) {
    if (activeArea != null) {
      int[] screen = activeArea.toScreen(var1, var2, var3);
      if (screen != null) {
        setDestination(screen[0], screen[1]);
      }

    }
  }

  public void method1237(int var1, int var2, int var3) {
    if (activeArea != null) {
      int[] var4 = activeArea.toScreen(var1, var2, var3);
      if (var4 != null) {
        setPositionRegional(var4[0], var4[1]);
      }

    }
  }

  public int method1260() {
    return activeArea == null ? -1 : activeArea.getId();
  }

  public void method1231() {
    refreshLimit = 3;
  }

  public WorldMapCacheArea getActiveArea() {
    return activeArea;
  }

  public void method1233() {
    refreshRate = 50;
  }

  public void method1245(int var1) {
    if (var1 >= 1) {
      refreshLimit = var1;
    }

  }

  public void method1243() {
    indicativeFunctions = null;
  }

  public void method1230(int var1) {
    if (var1 >= 1) {
      refreshRate = var1;
    }

  }

  public void method1244(int var1) {
    indicativeFunctions = new HashSet<>();
    indicativeFunctions.add(var1);
    indicationLimit = 0;
    indicationRate = 0;
  }

  public void setForceRefresh(boolean forceRefresh) {
    this.forceRefresh = forceRefresh;
  }

  public void setCategoryIndicative(int category) {
    indicativeFunctions = new HashSet<>();
    indicationLimit = 0;
    indicationRate = 0;

    for (int i = 0; i < WorldMapFunction.count; ++i) {
      if (WorldMapFunction.get(i) != null && WorldMapFunction.get(i).category == category) {
        indicativeFunctions.add(WorldMapFunction.get(i).objectId);
      }
    }

  }

  public WorldMapIcon getFirstIcon() {
    if (!definitionLoader.isLoaded()) {
      return null;
    }

    if (!controller.isLoaded()) {
      return null;
    }

    HashMap<Integer, List<WorldMapIcon>> icons = controller.defineIcons();
    this.icons = new java.util.LinkedList<>();

    for (List<WorldMapIcon> o : icons.values()) {
      this.icons.addAll(o);
    }

    this.iconIterator = this.icons.iterator();
    return getNextIcon();
  }

  public void method1258(boolean var1) {
    disableFunctions = !var1;
  }

  public boolean method1254() {
    return !disableFunctions;
  }

  public void method1251(int function, boolean disable) {
    if (!disable) {
      toggledFunctions.add(function);
    } else {
      toggledFunctions.remove(function);
    }

    mapToggledFeatures();
  }

  public void method1250(int category, boolean disable) {
    if (!disable) {
      toggledCategories.add(category);
    } else {
      toggledCategories.remove(category);
    }

    for (int function = 0; function < WorldMapFunction.count; ++function) {
      if (WorldMapFunction.get(function) != null && WorldMapFunction.get(function).category == category) {
        int objectId = WorldMapFunction.get(function).objectId;
        if (!disable) {
          toggledCategoryObjects.add(objectId);
        } else {
          toggledCategoryObjects.remove(objectId);
        }
      }
    }

    mapToggledFeatures();
  }

  public boolean isFunctionDisabled(int id) {
    return !toggledFunctions.contains(id);
  }

  public void method1277(WorldMapCacheArea var1, WorldMapPosition var2, WorldMapPosition var3, boolean var4) {
    if (var1 != null) {
      if (activeArea == null || var1 != activeArea) {
        method1266(var1);
      }

      if (!var4 && activeArea.contains(var2.floorLevel, var2.x, var2.y)) {
        setArea(var2.floorLevel, var2.x, var2.y);
      } else {
        setArea(var3.floorLevel, var3.x, var3.y);
      }
    }
  }

  public boolean isCategoryDisabled(int id) {
    return !toggledCategories.contains(id);
  }

  public WorldMapPosition method1249(int var1, WorldMapPosition var2) {
    if (!definitionLoader.isLoaded()) {
      return null;
    }

    if (!controller.isLoaded()) {
      return null;
    }

    if (!activeArea.contains(var2.x, var2.y)) {
      return null;
    }

    HashMap<Integer, List<WorldMapIcon>> var3 = controller.defineIcons();
    List<WorldMapIcon> var4 = var3.get(var1);
    if (var4 != null && !var4.isEmpty()) {
      WorldMapIcon var5 = null;
      int var6 = -1;
      Iterator<WorldMapIcon> var7 = var4.iterator();

      while (true) {
        WorldMapIcon var8;
        int var11;
        do {
          if (!var7.hasNext()) {
            return var5.max;
          }

          var8 = var7.next();
          int var9 = var8.max.x - var2.x;
          int var10 = var8.max.y - var2.y;
          var11 = var10 * var10 + var9 * var9;
          if (var11 == 0) {
            return var8.max;
          }
        } while (var11 >= var6 && var5 != null);

        var5 = var8;
        var6 = var11;
      }
    }
    return null;
  }

  public void method1225() {
    definitionLoader.method1310();
  }
}
