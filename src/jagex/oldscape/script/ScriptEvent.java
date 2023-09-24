package jagex.oldscape.script;

import jagex.jagex3.sound.AudioOverrideEffect;
import jagex.jagex3.sound.DefaultAudioSystemProvider;
import jagex.core.stringtools.Strings;
import jagex.datastructure.Node;
import jagex.jagex3.util.Browser;
import jagex.jagex3.util.Skills;
import jagex.core.time.Clock;
import jagex.datastructure.instrusive.hashtable.IntegerNode;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;
import jagex.oldscape.*;
import jagex.oldscape.client.*;
import jagex.oldscape.client.chat.*;
import jagex.oldscape.client.fonts.BaseFont;
import jagex.oldscape.client.fonts.Font;
import jagex.oldscape.client.minimenu.ContextMenu;
import jagex.oldscape.client.option.ClientPreferences;
import jagex.oldscape.client.social.*;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.client.scene.entity.PlayerEntity;
import jagex.oldscape.stockmarket.*;
import jagex.oldscape.client.type.*;
import jagex.jagex3.graphics.*;
import jagex.jagex3.js5.*;
import jagex.messaging.*;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;
import jagex.statics.*;
import jagex.oldscape.client.worldmap.*;

import java.util.Date;

public class ScriptEvent extends Node {
  public static int anInt1806;
  public static int[] intStack;
  public static String[] stringStack;
  public static int anInt748;

  public int type;
  public Object[] args;
  public InterfaceComponent component;
  public int actionIndex;
  public String opbase;
  public boolean mouseInputDerived;
  public int mouseX;
  public int mouseY;
  public InterfaceComponent dragTarget;
  public int anInt368;
  public int anInt372;
  public int anInt379;

  public ScriptEvent() {
    this.type = 76;
  }

  public static String format(CharSequence var0, ClientParameter nameLengthParameter) {
    if (var0 == null) {
      return null;
    }
    int var2 = 0;

    int var3;
    for (var3 = var0.length(); var2 < var3 && method1377(var0.charAt(var2)); ++var2) {
    }

    while (var3 > var2 && method1377(var0.charAt(var3 - 1))) {
      --var3;
    }

    int var4 = var3 - var2;
    if (var4 >= 1 && var4 <= AsyncOutputStream.method15(nameLengthParameter)) {
      StringBuilder var5 = new StringBuilder(var4);

      for (int var6 = var2; var6 < var3; ++var6) {
        char var7 = var0.charAt(var6);
        boolean var8;
        if (Character.isISOControl(var7)) {
          var8 = false;
        } else if (isAlphaNumeric(var7)) {
          var8 = true;
        } else {
          char[] var10 = Statics42.aCharArray1910;
          int var11 = 0;

          label73:
          while (true) {
            char var12;
            if (var11 >= var10.length) {
              var10 = Statics42.aCharArray1909;

              for (var11 = 0; var11 < var10.length; ++var11) {
                var12 = var10[var11];
                if (var7 == var12) {
                  var8 = true;
                  break label73;
                }
              }

              var8 = false;
              break;
            }

            var12 = var10[var11];
            if (var12 == var7) {
              var8 = true;
              break;
            }

            ++var11;
          }
        }

        if (var8) {
          char var9 = Statics49.convertDiacritic(var7);
          if (var9 != 0) {
            var5.append(var9);
          }
        }
      }

      if (var5.length() == 0) {
        return null;
      }
      return var5.toString();
    }
    return null;
  }

  public static void method1306(boolean var0) {
    client.aBoolean1037 = var0;
  }

  public static int method875(int var0) {
    if (var0 == 3600) {
      if (client.relationshipManager.mainState == 0) {
        intStack[++Statics46.anInt442 - 1] = -2;
      } else if (client.relationshipManager.mainState == 1) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = client.relationshipManager.friendListContext.getMemberCount();
      }

      return 1;
    }
    int var3;
    if (var0 == 3601) {
      var3 = intStack[--Statics46.anInt442];
      if (client.relationshipManager.isReady() && var3 >= 0 && var3 < client.relationshipManager.friendListContext.getMemberCount()) {
        BefriendedPlayer var8 = client.relationshipManager.friendListContext.getChatter(var3);
        stringStack[++Statics46.anInt441 - 1] = var8.getRawDisplayName();
        stringStack[++Statics46.anInt441 - 1] = var8.getRawPreviousName();
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 3602) {
      var3 = intStack[--Statics46.anInt442];
      if (client.relationshipManager.isReady() && var3 >= 0 && var3 < client.relationshipManager.friendListContext.getMemberCount()) {
        intStack[++Statics46.anInt442 - 1] = client.relationshipManager.friendListContext.getChatter(var3).world;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3603) {
      var3 = intStack[--Statics46.anInt442];
      if (client.relationshipManager.isReady() && var3 >= 0 && var3 < client.relationshipManager.friendListContext.getMemberCount()) {
        intStack[++Statics46.anInt442 - 1] = client.relationshipManager.friendListContext.getChatter(var3).rank;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    String var5;
    if (var0 == 3604) {
      var5 = stringStack[--Statics46.anInt441];
      int var6 = intStack[--Statics46.anInt442];
      method681(var5, var6);
      return 1;
    }
    if (var0 == 3605) {
      var5 = stringStack[--Statics46.anInt441];
      client.relationshipManager.befriend(var5);
      return 1;
    }
    if (var0 == 3606) {
      var5 = stringStack[--Statics46.anInt441];
      client.relationshipManager.unbefriend(var5);
      return 1;
    }
    if (var0 == 3607) {
      var5 = stringStack[--Statics46.anInt441];
      client.relationshipManager.ignore(var5);
      return 1;
    }
    if (var0 == 3608) {
      var5 = stringStack[--Statics46.anInt441];
      client.relationshipManager.unignore(var5);
      return 1;
    }
    if (var0 == 3609) {
      var5 = stringStack[--Statics46.anInt441];
      var5 = PlayerAccountType.getNameExcludingTags(var5);
      intStack[++Statics46.anInt442 - 1] = client.relationshipManager.isFriend(new NamePair(var5, PreciseWorldMapAreaChunk.loginTypeParameter)) ? 1 : 0;
      return 1;
    }
    if (var0 == 3611) {
      if (client.friendChat != null) {
        stringStack[++Statics46.anInt441 - 1] = client.friendChat.channelName;
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 3612) {
      if (client.friendChat != null) {
        intStack[++Statics46.anInt442 - 1] = client.friendChat.getMemberCount();
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3613) {
      var3 = intStack[--Statics46.anInt442];
      if (client.friendChat != null && var3 < client.friendChat.getMemberCount()) {
        stringStack[++Statics46.anInt441 - 1] = client.friendChat.getChatter(var3).getDisplayName().getRaw();
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 3614) {
      var3 = intStack[--Statics46.anInt442];
      if (client.friendChat != null && var3 < client.friendChat.getMemberCount()) {
        intStack[++Statics46.anInt442 - 1] = client.friendChat.getChatter(var3).getWorld();
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3615) {
      var3 = intStack[--Statics46.anInt442];
      if (client.friendChat != null && var3 < client.friendChat.getMemberCount()) {
        intStack[++Statics46.anInt442 - 1] = client.friendChat.getChatter(var3).rank;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3616) {
      intStack[++Statics46.anInt442 - 1] = client.friendChat != null ? client.friendChat.channelRank : 0;
      return 1;
    }
    if (var0 == 3617) {
      var5 = stringStack[--Statics46.anInt441];
      Js5Worker.method1093(var5);
      return 1;
    }
    if (var0 == 3618) {
      intStack[++Statics46.anInt442 - 1] = client.friendChat != null ? client.friendChat.localPlayerRank : 0;
      return 1;
    }
    if (var0 == 3619) {
      var5 = stringStack[--Statics46.anInt441];
      Server.method1343(var5);
      return 1;
    }
    if (var0 == 3620) {
      WorldMapLabel.method232();
      return 1;
    }
    if (var0 == 3621) {
      if (!client.relationshipManager.isReady()) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = client.relationshipManager.ignoreListContext.getMemberCount();
      }

      return 1;
    }
    if (var0 == 3622) {
      var3 = intStack[--Statics46.anInt442];
      if (client.relationshipManager.isReady() && var3 >= 0 && var3 < client.relationshipManager.ignoreListContext.getMemberCount()) {
        IgnoredPlayer var4 = client.relationshipManager.ignoreListContext.getChatter(var3);
        stringStack[++Statics46.anInt441 - 1] = var4.getRawDisplayName();
        stringStack[++Statics46.anInt441 - 1] = var4.getRawPreviousName();
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 3623) {
      var5 = stringStack[--Statics46.anInt441];
      var5 = PlayerAccountType.getNameExcludingTags(var5);
      intStack[++Statics46.anInt442 - 1] = client.relationshipManager.isIgnored(new NamePair(var5, PreciseWorldMapAreaChunk.loginTypeParameter)) ? 1 : 0;
      return 1;
    }
    if (var0 == 3624) {
      var3 = intStack[--Statics46.anInt442];
      if (client.friendChat != null && var3 < client.friendChat.getMemberCount() && client.friendChat.getChatter(var3).getDisplayName().equals(PlayerEntity.local.namePair)) {
        intStack[++Statics46.anInt442 - 1] = 1;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3625) {
      if (client.friendChat != null && client.friendChat.channelOwner != null) {
        stringStack[++Statics46.anInt441 - 1] = client.friendChat.channelOwner;
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 3626) {
      var3 = intStack[--Statics46.anInt442];
      if (client.friendChat != null && var3 < client.friendChat.getMemberCount() && client.friendChat.getChatter(var3).method711()) {
        intStack[++Statics46.anInt442 - 1] = 1;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 != 3627) {
      if (var0 == 3628) {
        client.relationshipManager.friendListContext.unsetComparator();
        return 1;
      }
      boolean var7;
      if (var0 == 3629) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new Class223(var7));
        return 1;
      }
      if (var0 == 3630) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new Class211(var7));
        return 1;
      }
      if (var0 == 3631) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparatorByWorld(var7));
        return 1;
      }
      if (var0 == 3632) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new Class114(var7));
        return 1;
      }
      if (var0 == 3633) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparator_Sub5(var7));
        return 1;
      }
      if (var0 == 3634) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparatorByName(var7));
        return 1;
      }
      if (var0 == 3635) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparator_Sub2(var7));
        return 1;
      }
      if (var0 == 3636) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparatorByMyWorld(var7));
        return 1;
      }
      if (var0 == 3637) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparatorByWorldAndName(var7));
        return 1;
      }
      if (var0 == 3638) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparator_Sub4(var7));
        return 1;
      }
      if (var0 == 3639) {
        client.relationshipManager.friendListContext.sort();
        return 1;
      }
      if (var0 == 3640) {
        client.relationshipManager.ignoreListContext.unsetComparator();
        return 1;
      }
      if (var0 == 3641) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.ignoreListContext.setComparator(new Class223(var7));
        return 1;
      }
      if (var0 == 3642) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.ignoreListContext.setComparator(new Class211(var7));
        return 1;
      }
      if (var0 == 3643) {
        client.relationshipManager.ignoreListContext.sort();
        return 1;
      }
      if (var0 == 3644) {
        if (client.friendChat != null) {
          client.friendChat.unsetComparator();
        }

        return 1;
      }
      if (var0 == 3645) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new Class223(var7));
        }

        return 1;
      }
      if (var0 == 3646) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new Class211(var7));
        }

        return 1;
      }
      if (var0 == 3647) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparatorByWorld(var7));
        }

        return 1;
      }
      if (var0 == 3648) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new Class114(var7));
        }

        return 1;
      }
      if (var0 == 3649) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparator_Sub5(var7));
        }

        return 1;
      }
      if (var0 == 3650) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparatorByName(var7));
        }

        return 1;
      }
      if (var0 == 3651) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparator_Sub2(var7));
        }

        return 1;
      }
      if (var0 == 3652) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparatorByMyWorld(var7));
        }

        return 1;
      }
      if (var0 == 3653) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparatorByWorldAndName(var7));
        }

        return 1;
      }
      if (var0 == 3654) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparator_Sub4(var7));
        }

        return 1;
      }
      if (var0 == 3655) {
        if (client.friendChat != null) {
          client.friendChat.sort();
        }

        return 1;
      }
      if (var0 == 3656) {
        var7 = intStack[--Statics46.anInt442] == 1;
        client.relationshipManager.friendListContext.setComparator(new AssociateComparatorByRank(var7));
        return 1;
      }
      if (var0 == 3657) {
        var7 = intStack[--Statics46.anInt442] == 1;
        if (client.friendChat != null) {
          client.friendChat.setComparator(new AssociateComparatorByRank(var7));
        }

        return 1;
      }
      return 2;
    }
    var3 = intStack[--Statics46.anInt442];
    if (client.friendChat != null && var3 < client.friendChat.getMemberCount() && client.friendChat.getChatter(var3).method706()) {
      intStack[++Statics46.anInt442 - 1] = 1;
    } else {
      intStack[++Statics46.anInt442 - 1] = 0;
    }

    return 1;
  }

  public static void process(ScriptEvent event, int size) {
    Object[] args = event.args;
    ClientScript script;
    int id;
    if (Statics24.method966(event.type)) {
      EnumDefinition.aWorldMapScriptEvent_1443 = (WorldMapScriptEvent) args[0];
      WorldMapFunction var3 = WorldMapFunction.get(EnumDefinition.aWorldMapScriptEvent_1443.anInt306);
      script = WorldMapAreaChunk_Sub2.method151(event.type, var3.objectId, var3.category);
    } else {
      id = (Integer) args[0];
      script = ClientScript.decode(id);
    }

    if (script != null) {
      Statics46.anInt442 = 0;
      Statics46.anInt441 = 0;
      id = -1;
      int[] var6 = script.opcodes;
      int[] var7 = script.intOperands;
      byte var8 = -1;
      Statics46.anInt436 = 0;
      Statics46.aBoolean439 = false;

      try {
        try {
          Statics46.anIntArray447 = new int[script.intLocals];
          int var9 = 0;
          URLRequestProcessor.aStringArray794 = new String[script.stringLocals];
          int var10 = 0;

          String var13;
          for (int i = 1; i < args.length; ++i) {
            if (args[i] instanceof Integer) {
              int value = (Integer) args[i];
              if (value == -2147483647) {
                value = event.mouseX;
              }

              if (value == -2147483646) {
                value = event.mouseY;
              }

              if (value == -2147483645) {
                value = event.component != null ? event.component.uid : -1;
              }

              if (value == -2147483644) {
                value = event.actionIndex;
              }

              if (value == -2147483643) {
                value = event.component != null ? event.component.subComponentIndex : -1;
              }

              if (value == -2147483642) {
                value = event.dragTarget != null ? event.dragTarget.uid : -1;
              }

              if (value == -2147483641) {
                value = event.dragTarget != null ? event.dragTarget.subComponentIndex : -1;
              }

              if (value == -2147483640) {
                value = event.anInt372;
              }

              if (value == -2147483639) {
                value = event.anInt379;
              }

              Statics46.anIntArray447[var9++] = value;
            } else if (args[i] instanceof String) {
              var13 = (String) args[i];
              if (var13.equals("event_opbase")) {
                var13 = event.opbase;
              }

              URLRequestProcessor.aStringArray794[var10++] = var13;
            }
          }

          int var11 = 0;
          int var12;
          Statics46.anInt440 = event.anInt368;

          while (true) {
            ++var11;
            if (var11 > size) {
              throw new RuntimeException();
            }

            ++id;
            int var29 = var6[id];
            int var19;
            if (var29 >= 100) {
              boolean var34 = script.intOperands[id] == 1;

              var19 = process(var29, var34);
              switch (var19) {
                case 0:
                  return;
                case 1:
                default:
                  break;
                case 2:
                  throw new IllegalStateException();
              }
            } else if (var29 == 0) {
              intStack[++Statics46.anInt442 - 1] = var7[id];
            } else if (var29 == 1) {
              var12 = var7[id];
              intStack[++Statics46.anInt442 - 1] = Vars.values[var12];
            } else if (var29 == 2) {
              var12 = var7[id];
              Vars.values[var12] = intStack[--Statics46.anInt442];
              OldConnection.processOptionVarps(var12);
            } else if (var29 == 3) {
              stringStack[++Statics46.anInt441 - 1] = script.stringOperands[id];
            } else if (var29 == 6) {
              id += var7[id];
            } else if (var29 == 7) {
              Statics46.anInt442 -= 2;
              if (intStack[Statics46.anInt442] != intStack[Statics46.anInt442 + 1]) {
                id += var7[id];
              }
            } else if (var29 == 8) {
              Statics46.anInt442 -= 2;
              if (intStack[Statics46.anInt442] == intStack[Statics46.anInt442 + 1]) {
                id += var7[id];
              }
            } else if (var29 == 9) {
              Statics46.anInt442 -= 2;
              if (intStack[Statics46.anInt442] < intStack[Statics46.anInt442 + 1]) {
                id += var7[id];
              }
            } else if (var29 == 10) {
              Statics46.anInt442 -= 2;
              if (intStack[Statics46.anInt442] > intStack[Statics46.anInt442 + 1]) {
                id += var7[id];
              }
            } else if (var29 == 21) {
              if (Statics46.anInt436 == 0) {
                return;
              }

              ClientScriptFrame var33 = Statics46.A_RUNE_SCRIPT_FRAME_ARRAY_435[--Statics46.anInt436];
              script = var33.target;
              var6 = script.opcodes;
              var7 = script.intOperands;
              id = var33.instructionIndex;
              Statics46.anIntArray447 = var33.intLocals;
              URLRequestProcessor.aStringArray794 = var33.stringLocals;
            } else if (var29 == 25) {
              var12 = var7[id];
              intStack[++Statics46.anInt442 - 1] = Varbit.getValue(var12);
            } else if (var29 == 27) {
              var12 = var7[id];
              Varbit.setValue(var12, intStack[--Statics46.anInt442]);
            } else if (var29 == 31) {
              Statics46.anInt442 -= 2;
              if (intStack[Statics46.anInt442] <= intStack[Statics46.anInt442 + 1]) {
                id += var7[id];
              }
            } else if (var29 == 32) {
              Statics46.anInt442 -= 2;
              if (intStack[Statics46.anInt442] >= intStack[Statics46.anInt442 + 1]) {
                id += var7[id];
              }
            } else if (var29 == 33) {
              intStack[++Statics46.anInt442 - 1] = Statics46.anIntArray447[var7[id]];
            } else if (var29 == 34) {
              Statics46.anIntArray447[var7[id]] = intStack[--Statics46.anInt442];
            } else if (var29 == 35) {
              stringStack[++Statics46.anInt441 - 1] = URLRequestProcessor.aStringArray794[var7[id]];
            } else if (var29 == 36) {
              URLRequestProcessor.aStringArray794[var7[id]] = stringStack[--Statics46.anInt441];
            } else if (var29 == 37) {
              var12 = var7[id];
              Statics46.anInt441 -= var12;
              String var31 = TileOverlay.method1009(stringStack, Statics46.anInt441, var12);
              stringStack[++Statics46.anInt441 - 1] = var31;
            } else if (var29 == 38) {
              --Statics46.anInt442;
            } else if (var29 == 39) {
              --Statics46.anInt441;
            } else {
              int var17;
              if (var29 != 40) {
                if (var29 == 42) {
                  intStack[++Statics46.anInt442 - 1] = client.varcs.getInteger(var7[id]);
                } else if (var29 == 43) {
                  client.varcs.put(var7[id], intStack[--Statics46.anInt442]);
                } else if (var29 == 44) {
                  var12 = var7[id] >> 16;
                  var19 = var7[id] & 65535;
                  int var20 = intStack[--Statics46.anInt442];
                  if (var20 < 0 || var20 > 5000) {
                    throw new RuntimeException();
                  }

                  Statics46.anIntArray445[var12] = var20;
                  byte var21 = -1;
                  if (var19 == 105) {
                    var21 = 0;
                  }

                  for (var17 = 0; var17 < var20; ++var17) {
                    Statics46.anIntArrayArray443[var12][var17] = var21;
                  }
                } else if (var29 == 45) {
                  var12 = var7[id];
                  var19 = intStack[--Statics46.anInt442];
                  if (var19 < 0 || var19 >= Statics46.anIntArray445[var12]) {
                    throw new RuntimeException();
                  }

                  intStack[++Statics46.anInt442 - 1] = Statics46.anIntArrayArray443[var12][var19];
                } else if (var29 == 46) {
                  var12 = var7[id];
                  Statics46.anInt442 -= 2;
                  var19 = intStack[Statics46.anInt442];
                  if (var19 < 0 || var19 >= Statics46.anIntArray445[var12]) {
                    throw new RuntimeException();
                  }

                  Statics46.anIntArrayArray443[var12][var19] = intStack[Statics46.anInt442 + 1];
                } else if (var29 == 47) {
                  var13 = client.varcs.getRawString(var7[id]);
                  if (var13 == null) {
                    var13 = "null";
                  }

                  stringStack[++Statics46.anInt441 - 1] = var13;
                } else if (var29 == 48) {
                  client.varcs.putRawString(var7[id], stringStack[--Statics46.anInt441]);
                } else if (var29 == 49) {
                  var13 = client.varcs.getString(var7[id]);
                  stringStack[++Statics46.anInt441 - 1] = var13;
                } else if (var29 == 50) {
                  client.varcs.put(var7[id], stringStack[--Statics46.anInt441]);
                } else {
                  if (var29 != 60) {
                    throw new IllegalStateException();
                  }

                  IterableNodeTable<? super Node> var32 = script.tables[var7[id]];
                  IntegerNode var30 = (IntegerNode) var32.lookup(intStack[--Statics46.anInt442]);
                  if (var30 != null) {
                    id += var30.value;
                  }
                }
              } else {
                var12 = var7[id];
                ClientScript var14 = ClientScript.decode(var12);
                int[] var15 = new int[var14.intLocals];
                String[] var16 = new String[var14.stringLocals];

                for (var17 = 0; var17 < var14.intArgs; ++var17) {
                  var15[var17] = intStack[var17 + (Statics46.anInt442 - var14.intArgs)];
                }

                for (var17 = 0; var17 < var14.stringArgs; ++var17) {
                  var16[var17] = stringStack[var17 + (Statics46.anInt441 - var14.stringArgs)];
                }

                Statics46.anInt442 -= var14.intArgs;
                Statics46.anInt441 -= var14.stringArgs;
                ClientScriptFrame var18 = new ClientScriptFrame();
                var18.target = script;
                var18.instructionIndex = id;
                var18.intLocals = Statics46.anIntArray447;
                var18.stringLocals = URLRequestProcessor.aStringArray794;
                Statics46.A_RUNE_SCRIPT_FRAME_ARRAY_435[++Statics46.anInt436 - 1] = var18;
                script = var14;
                var6 = var14.opcodes;
                var7 = var14.intOperands;
                id = -1;
                Statics46.anIntArray447 = var15;
                URLRequestProcessor.aStringArray794 = var16;
              }
            }
          }
        } catch (Exception var27) {
          StringBuilder var23 = new StringBuilder(30);
          var23.append(script.key).append(" ");

          for (int i = Statics46.anInt436 - 1; i >= 0; --i) {
            var23.append(Statics46.A_RUNE_SCRIPT_FRAME_ARRAY_435[i].target.key).append(" ");
          }

          var23.append(var8);
          client.sendError(var23.toString(), var27);
        }
      } finally {
        if (Statics46.aBoolean439) {
          Statics46.aBoolean449 = true;
          InterfaceComponent.closeInterface();
          Statics46.aBoolean449 = false;
          Statics46.aBoolean439 = false;
        }

      }
    }
  }

  static int method384(int var0, boolean var2) {
    int var3;
    int var4;
    if (var0 == 100) {
      Statics46.anInt442 -= 3;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      int var5 = intStack[Statics46.anInt442 + 2];
      if (var4 == 0) {
        throw new RuntimeException();
      }
      InterfaceComponent var8 = InterfaceComponent.lookup(var3);
      if (var8.subcomponents == null) {
        var8.subcomponents = new InterfaceComponent[var5 + 1];
      }

      if (var8.subcomponents.length <= var5) {
        InterfaceComponent[] var9 = new InterfaceComponent[var5 + 1];

        System.arraycopy(var8.subcomponents, 0, var9, 0, var8.subcomponents.length);

        var8.subcomponents = var9;
      }

      if (var5 > 0 && var8.subcomponents[var5 - 1] == null) {
        throw new RuntimeException("" + (var5 - 1));
      }
      InterfaceComponent var12 = new InterfaceComponent();
      var12.type = var4;
      var12.parentUid = var12.uid = var8.uid;
      var12.subComponentIndex = var5;
      var12.if3 = true;
      var8.subcomponents[var5] = var12;
      if (var2) {
        StockmarketListingLifetimeComparator.anInterfaceComponent585 = var12;
      } else {
        IdentikitDefinition.anInterfaceComponent1518 = var12;
      }

      InterfaceComponent.invalidate(var8);
      return 1;
    }
    InterfaceComponent var6;
    if (var0 == 101) {
      var6 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
      InterfaceComponent var7 = InterfaceComponent.lookup(var6.uid);
      var7.subcomponents[var6.subComponentIndex] = null;
      InterfaceComponent.invalidate(var7);
      return 1;
    }
    if (var0 == 102) {
      var6 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
      var6.subcomponents = null;
      InterfaceComponent.invalidate(var6);
      return 1;
    }
    if (var0 != 200) {
      if (var0 == 201) {
        var6 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
        if (var6 != null) {
          intStack[++Statics46.anInt442 - 1] = 1;
          if (var2) {
            StockmarketListingLifetimeComparator.anInterfaceComponent585 = var6;
          } else {
            IdentikitDefinition.anInterfaceComponent1518 = var6;
          }
        } else {
          intStack[++Statics46.anInt442 - 1] = 0;
        }

        return 1;
      }
      return 2;
    }
    Statics46.anInt442 -= 2;
    var3 = intStack[Statics46.anInt442];
    var4 = intStack[Statics46.anInt442 + 1];
    InterfaceComponent var11 = InterfaceComponent.lookup(var3, var4);
    if (var11 != null && var4 != -1) {
      intStack[++Statics46.anInt442 - 1] = 1;
      if (var2) {
        StockmarketListingLifetimeComparator.anInterfaceComponent585 = var11;
      } else {
        IdentikitDefinition.anInterfaceComponent1518 = var11;
      }
    } else {
      intStack[++Statics46.anInt442 - 1] = 0;
    }

    return 1;
  }

  static int method889(int var0) {
    if (var0 == 6500) {
      intStack[++Statics46.anInt442 - 1] = Server.load() ? 1 : 0;
      return 1;
    }
    Server var3;
    if (var0 == 6501) {
      var3 = Server.first();
      if (var3 != null) {
        intStack[++Statics46.anInt442 - 1] = var3.id;
        intStack[++Statics46.anInt442 - 1] = var3.mask;
        stringStack[++Statics46.anInt441 - 1] = var3.activity;
        intStack[++Statics46.anInt442 - 1] = var3.location;
        intStack[++Statics46.anInt442 - 1] = var3.population;
        stringStack[++Statics46.anInt441 - 1] = var3.domain;
      } else {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 6502) {
      var3 = Server.next();
      if (var3 != null) {
        intStack[++Statics46.anInt442 - 1] = var3.id;
        intStack[++Statics46.anInt442 - 1] = var3.mask;
        stringStack[++Statics46.anInt441 - 1] = var3.activity;
        intStack[++Statics46.anInt442 - 1] = var3.location;
        intStack[++Statics46.anInt442 - 1] = var3.population;
        stringStack[++Statics46.anInt441 - 1] = var3.domain;
      } else {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    int var4;
    Server var5;
    int var6;
    if (var0 == 6506) {
      var4 = intStack[--Statics46.anInt442];
      var5 = null;

      for (var6 = 0; var6 < Server.count; ++var6) {
        if (var4 == Server.servers[var6].id) {
          var5 = Server.servers[var6];
          break;
        }
      }

      if (var5 != null) {
        intStack[++Statics46.anInt442 - 1] = var5.id;
        intStack[++Statics46.anInt442 - 1] = var5.mask;
        stringStack[++Statics46.anInt441 - 1] = var5.activity;
        intStack[++Statics46.anInt442 - 1] = var5.location;
        intStack[++Statics46.anInt442 - 1] = var5.population;
        stringStack[++Statics46.anInt441 - 1] = var5.domain;
      } else {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 6507) {
      Statics46.anInt442 -= 4;
      var4 = intStack[Statics46.anInt442];
      boolean var10 = intStack[Statics46.anInt442 + 1] == 1;
      var6 = intStack[Statics46.anInt442 + 2];
      boolean var8 = intStack[Statics46.anInt442 + 3] == 1;
      Statics50.method220(var4, var10, var6, var8);
      return 1;
    }
    if (var0 != 6511) {
      if (var0 == 6512) {
        client.aBoolean1042 = intStack[--Statics46.anInt442] == 1;
        return 1;
      }
      int var7;
      ParameterDefinition var9;
      if (var0 == 6513) {
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var7 = intStack[Statics46.anInt442 + 1];
        var9 = ParameterDefinition.get(var7);
        if (var9.isString()) {
          stringStack[++Statics46.anInt441 - 1] = NpcDefinition.get(var4).method504(var7, var9.defaultString);
        } else {
          intStack[++Statics46.anInt442 - 1] = NpcDefinition.get(var4).method511(var7, var9.defaultInteger);
        }

        return 1;
      }
      if (var0 == 6514) {
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var7 = intStack[Statics46.anInt442 + 1];
        var9 = ParameterDefinition.get(var7);
        if (var9.isString()) {
          stringStack[++Statics46.anInt441 - 1] = ObjectDefinition.get(var4).method1104(var7, var9.defaultString);
        } else {
          intStack[++Statics46.anInt442 - 1] = ObjectDefinition.get(var4).method1100(var7, var9.defaultInteger);
        }

        return 1;
      }
      if (var0 == 6515) {
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var7 = intStack[Statics46.anInt442 + 1];
        var9 = ParameterDefinition.get(var7);
        if (var9.isString()) {
          stringStack[++Statics46.anInt441 - 1] = ItemDefinition.get(var4).method520(var7, var9.defaultString);
        } else {
          intStack[++Statics46.anInt442 - 1] = ItemDefinition.get(var4).method527(var7, var9.defaultInteger);
        }

        return 1;
      }
      if (var0 == 6516) {
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var7 = intStack[Statics46.anInt442 + 1];
        var9 = ParameterDefinition.get(var7);
        if (var9.isString()) {
          stringStack[++Statics46.anInt441 - 1] = StructDefinition.get(var4).getString(var7, var9.defaultString);
        } else {
          intStack[++Statics46.anInt442 - 1] = StructDefinition.get(var4).getInteger(var7, var9.defaultInteger);
        }

        return 1;
      }
      if (var0 == 6518) {
        intStack[++Statics46.anInt442 - 1] = client.mobile ? 1 : 0;
        return 1;
      }
      if (var0 == 6519) {
        intStack[++Statics46.anInt442 - 1] = client.anInt923 & 3;
        return 1;
      }
      if (var0 == 6520) {
        return 1;
      }
      if (var0 == 6521) {
        return 1;
      }
      if (var0 == 6522) {
        --Statics46.anInt441;
        --Statics46.anInt442;
        return 1;
      }
      if (var0 == 6523) {
        --Statics46.anInt441;
        --Statics46.anInt442;
        return 1;
      }
      if (var0 == 6524) {
        intStack[++Statics46.anInt442 - 1] = -1;
        return 1;
      }
      if (var0 == 6525) {
        intStack[++Statics46.anInt442 - 1] = 1;
        return 1;
      }
      if (var0 == 6526) {
        intStack[++Statics46.anInt442 - 1] = 1;
        return 1;
      }
      return 2;
    }
    var4 = intStack[--Statics46.anInt442];
    if (var4 >= 0 && var4 < Server.count) {
      var5 = Server.servers[var4];
      intStack[++Statics46.anInt442 - 1] = var5.id;
      intStack[++Statics46.anInt442 - 1] = var5.mask;
      stringStack[++Statics46.anInt441 - 1] = var5.activity;
      intStack[++Statics46.anInt442 - 1] = var5.location;
      intStack[++Statics46.anInt442 - 1] = var5.population;
      stringStack[++Statics46.anInt441 - 1] = var5.domain;
    } else {
      intStack[++Statics46.anInt442 - 1] = -1;
      intStack[++Statics46.anInt442 - 1] = 0;
      stringStack[++Statics46.anInt441 - 1] = "";
      intStack[++Statics46.anInt442 - 1] = 0;
      intStack[++Statics46.anInt442 - 1] = 0;
      stringStack[++Statics46.anInt441 - 1] = "";
    }

    return 1;
  }

  static int method7(int var0) {
    if (var0 == 6200) {
      Statics46.anInt442 -= 2;
      client.aShort922 = (short) PlayerModel.method1426(intStack[Statics46.anInt442]);
      if (client.aShort922 <= 0) {
        client.aShort922 = 256;
      }

      client.aShort912 = (short) PlayerModel.method1426(intStack[Statics46.anInt442 + 1]);
      if (client.aShort912 <= 0) {
        client.aShort912 = 256;
      }

      return 1;
    }
    if (var0 == 6201) {
      Statics46.anInt442 -= 2;
      client.aShort913 = (short) intStack[Statics46.anInt442];
      if (client.aShort913 <= 0) {
        client.aShort913 = 256;
      }

      client.aShort920 = (short) intStack[Statics46.anInt442 + 1];
      if (client.aShort920 <= 0) {
        client.aShort920 = 320;
      }

      return 1;
    }
    if (var0 == 6202) {
      Statics46.anInt442 -= 4;
      client.aShort915 = (short) intStack[Statics46.anInt442];
      if (client.aShort915 <= 0) {
        client.aShort915 = 1;
      }

      client.aShort907 = (short) intStack[Statics46.anInt442 + 1];
      if (client.aShort907 <= 0) {
        client.aShort907 = 32767;
      } else if (client.aShort907 < client.aShort915) {
        client.aShort907 = client.aShort915;
      }

      client.aShort910 = (short) intStack[Statics46.anInt442 + 2];
      if (client.aShort910 <= 0) {
        client.aShort910 = 1;
      }

      client.aShort921 = (short) intStack[Statics46.anInt442 + 3];
      if (client.aShort921 <= 0) {
        client.aShort921 = 32767;
      } else if (client.aShort921 < client.aShort910) {
        client.aShort921 = client.aShort910;
      }

      return 1;
    }
    if (var0 == 6203) {
      if (client.minimapComponent != null) {
        method867(0, 0, client.minimapComponent.width, client.minimapComponent.height, false);
        intStack[++Statics46.anInt442 - 1] = client.viewportWidth;
        intStack[++Statics46.anInt442 - 1] = client.viewportHeight;
      } else {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
      }

      return 1;
    }
    if (var0 == 6204) {
      intStack[++Statics46.anInt442 - 1] = client.aShort913;
      intStack[++Statics46.anInt442 - 1] = client.aShort920;
      return 1;
    }
    if (var0 == 6205) {
      intStack[++Statics46.anInt442 - 1] = client.method888(client.aShort922);
      intStack[++Statics46.anInt442 - 1] = client.method888(client.aShort912);
      return 1;
    }
    if (var0 == 6220) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 6221) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 6222) {
      intStack[++Statics46.anInt442 - 1] = client.canvasWidth;
      return 1;
    }
    if (var0 == 6223) {
      intStack[++Statics46.anInt442 - 1] = client.canvasHeight;
      return 1;
    }
    return 2;
  }

  static int method954(int var0) {
    if (var0 == 5630) {
      client.logoutTimer = 250;
      return 1;
    }
    return 2;
  }

  static int method105(int var0) {
    int var3;
    if (var0 == 5504) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      int var4 = intStack[Statics46.anInt442 + 1];
      if (!client.cameraLocked) {
        Camera.minimumPitch = var3;
        Camera.yOffset = var4;
      }

      return 1;
    }
    if (var0 == 5505) {
      intStack[++Statics46.anInt442 - 1] = Camera.minimumPitch;
      return 1;
    }
    if (var0 == 5506) {
      intStack[++Statics46.anInt442 - 1] = Camera.yOffset;
      return 1;
    }
    if (var0 == 5530) {
      var3 = intStack[--Statics46.anInt442];
      if (var3 < 0) {
        var3 = 0;
      }

      Camera.followHeight = var3;
      return 1;
    }
    if (var0 == 5531) {
      intStack[++Statics46.anInt442 - 1] = Camera.followHeight;
      return 1;
    }
    return 2;
  }

  static int method641(int var0) {
    if (var0 == 5306) {
      intStack[++Statics46.anInt442 - 1] = client.isResizable();
      return 1;
    }
    int var3;
    if (var0 == 5307) {
      var3 = intStack[--Statics46.anInt442];
      if (var3 == 1 || var3 == 2) {
        AssociateComparatorByMyWorld.method603(var3);
      }

      return 1;
    }
    if (var0 == 5308) {
      intStack[++Statics46.anInt442 - 1] = client.preferences.resizable;
      return 1;
    }
    if (var0 != 5309) {
      if (var0 == 5310) {
        --Statics46.anInt442;
        return 1;
      }
      return 2;
    }
    var3 = intStack[--Statics46.anInt442];
    if (var3 == 1 || var3 == 2) {
      client.preferences.resizable = var3;
      ClientPreferences.method854();
    }

    return 1;
  }

  static int method268(int var0) {
    if (var0 == 5000) {
      intStack[++Statics46.anInt442 - 1] = client.publicChatMode;
      return 1;
    }
    if (var0 == 5001) {
      Statics46.anInt442 -= 3;
      client.publicChatMode = intStack[Statics46.anInt442];
      client.publicChatPrivacyMode = ChatModePrivacyType.valueOf(intStack[Statics46.anInt442 + 1]);
      if (client.publicChatPrivacyMode == null) {
        client.publicChatPrivacyMode = ChatModePrivacyType.A_CHAT_PRIVACY_TYPE___1569;
      }

      client.tradeChatMode = intStack[Statics46.anInt442 + 2];
      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PROCESS_CHAT_MODE, client.stream.encryptor);
      packet.buffer.p1(client.publicChatMode);
      packet.buffer.p1(client.publicChatPrivacyMode.index);
      packet.buffer.p1(client.tradeChatMode);
      client.stream.writeLater(packet);
      return 1;
    }
    String var3;
    int var4;
    if (var0 == 5002) {
      var3 = stringStack[--Statics46.anInt441];
      Statics46.anInt442 -= 2;
      var4 = intStack[Statics46.anInt442];
      int var5 = intStack[Statics46.anInt442 + 1];
      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.REPORT_ABUSE, client.stream.encryptor);
      packet.buffer.p1(Buffer.stringLengthPlusOne(var3) + 2);
      packet.buffer.pcstr(var3);
      packet.buffer.p1(var4 - 1);
      packet.buffer.p1(var5);
      client.stream.writeLater(packet);
      return 1;
    }
    int var7;
    if (var0 == 5003) {
      Statics46.anInt442 -= 2;
      var7 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      ChatLine var15 = ChatHistory.getLine(var7, var4);
      if (var15 != null) {
        intStack[++Statics46.anInt442 - 1] = var15.index;
        intStack[++Statics46.anInt442 - 1] = var15.cycle;
        stringStack[++Statics46.anInt441 - 1] = var15.channel != null ? var15.channel : "";
        stringStack[++Statics46.anInt441 - 1] = var15.source != null ? var15.source : "";
        stringStack[++Statics46.anInt441 - 1] = var15.message != null ? var15.message : "";
        intStack[++Statics46.anInt442 - 1] = var15.method693() ? 1 : (var15.method882() ? 2 : 0);
      } else {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
        stringStack[++Statics46.anInt441 - 1] = "";
        stringStack[++Statics46.anInt441 - 1] = "";
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 5004) {
      var7 = intStack[--Statics46.anInt442];
      ChatLine var16 = StockmarketListingWorldComparator.method246(var7);
      if (var16 != null) {
        intStack[++Statics46.anInt442 - 1] = var16.type;
        intStack[++Statics46.anInt442 - 1] = var16.cycle;
        stringStack[++Statics46.anInt441 - 1] = var16.channel != null ? var16.channel : "";
        stringStack[++Statics46.anInt441 - 1] = var16.source != null ? var16.source : "";
        stringStack[++Statics46.anInt441 - 1] = var16.message != null ? var16.message : "";
        intStack[++Statics46.anInt442 - 1] = var16.method693() ? 1 : (var16.method882() ? 2 : 0);
      } else {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = 0;
        stringStack[++Statics46.anInt441 - 1] = "";
        stringStack[++Statics46.anInt441 - 1] = "";
        stringStack[++Statics46.anInt441 - 1] = "";
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 5005) {
      if (client.publicChatPrivacyMode == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = client.publicChatPrivacyMode.index;
      }

      return 1;
    }
    if (var0 == 5008) {
      var3 = stringStack[--Statics46.anInt441];
      var4 = intStack[--Statics46.anInt442];
      String var14 = var3.toLowerCase();
      byte var17 = 0;
      if (var14.startsWith("yellow:")) {
        var17 = 0;
        var3 = var3.substring("yellow:".length());
      } else if (var14.startsWith("red:")) {
        var17 = 1;
        var3 = var3.substring("red:".length());
      } else if (var14.startsWith("green:")) {
        var17 = 2;
        var3 = var3.substring("green:".length());
      } else if (var14.startsWith("cyan:")) {
        var17 = 3;
        var3 = var3.substring("cyan:".length());
      } else if (var14.startsWith("purple:")) {
        var17 = 4;
        var3 = var3.substring("purple:".length());
      } else if (var14.startsWith("white:")) {
        var17 = 5;
        var3 = var3.substring("white:".length());
      } else if (var14.startsWith("flash1:")) {
        var17 = 6;
        var3 = var3.substring("flash1:".length());
      } else if (var14.startsWith("flash2:")) {
        var17 = 7;
        var3 = var3.substring("flash2:".length());
      } else if (var14.startsWith("flash3:")) {
        var17 = 8;
        var3 = var3.substring("flash3:".length());
      } else if (var14.startsWith("glow1:")) {
        var17 = 9;
        var3 = var3.substring("glow1:".length());
      } else if (var14.startsWith("glow2:")) {
        var17 = 10;
        var3 = var3.substring("glow2:".length());
      } else if (var14.startsWith("glow3:")) {
        var17 = 11;
        var3 = var3.substring("glow3:".length());
      } else if (WorldMapLabelSize.aClientLocale_525 != ClientLocale.GB) {
        if (var14.startsWith("yellow:")) {
          var17 = 0;
          var3 = var3.substring("yellow:".length());
        } else if (var14.startsWith("red:")) {
          var17 = 1;
          var3 = var3.substring("red:".length());
        } else if (var14.startsWith("green:")) {
          var17 = 2;
          var3 = var3.substring("green:".length());
        } else if (var14.startsWith("cyan:")) {
          var17 = 3;
          var3 = var3.substring("cyan:".length());
        } else if (var14.startsWith("purple:")) {
          var17 = 4;
          var3 = var3.substring("purple:".length());
        } else if (var14.startsWith("white:")) {
          var17 = 5;
          var3 = var3.substring("white:".length());
        } else if (var14.startsWith("flash1:")) {
          var17 = 6;
          var3 = var3.substring("flash1:".length());
        } else if (var14.startsWith("flash2:")) {
          var17 = 7;
          var3 = var3.substring("flash2:".length());
        } else if (var14.startsWith("flash3:")) {
          var17 = 8;
          var3 = var3.substring("flash3:".length());
        } else if (var14.startsWith("glow1:")) {
          var17 = 9;
          var3 = var3.substring("glow1:".length());
        } else if (var14.startsWith("glow2:")) {
          var17 = 10;
          var3 = var3.substring("glow2:".length());
        } else if (var14.startsWith("glow3:")) {
          var17 = 11;
          var3 = var3.substring("glow3:".length());
        }
      }

      var14 = var3.toLowerCase();
      byte var11 = 0;
      if (var14.startsWith("wave:")) {
        var11 = 1;
        var3 = var3.substring("wave:".length());
      } else if (var14.startsWith("wave2:")) {
        var11 = 2;
        var3 = var3.substring("wave2:".length());
      } else if (var14.startsWith("shake:")) {
        var11 = 3;
        var3 = var3.substring("shake:".length());
      } else if (var14.startsWith("scroll:")) {
        var11 = 4;
        var3 = var3.substring("scroll:".length());
      } else if (var14.startsWith("slide:")) {
        var11 = 5;
        var3 = var3.substring("slide:".length());
      } else if (ClientLocale.GB != WorldMapLabelSize.aClientLocale_525) {
        if (var14.startsWith("wave:")) {
          var11 = 1;
          var3 = var3.substring("wave:".length());
        } else if (var14.startsWith("wave2:")) {
          var11 = 2;
          var3 = var3.substring("wave2:".length());
        } else if (var14.startsWith("shake:")) {
          var11 = 3;
          var3 = var3.substring("shake:".length());
        } else if (var14.startsWith("scroll:")) {
          var11 = 4;
          var3 = var3.substring("scroll:".length());
        } else if (var14.startsWith("slide:")) {
          var11 = 5;
          var3 = var3.substring("slide:".length());
        }
      }

      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.SEND_PUBLIC_CHAT, client.stream.encryptor);
      packet.buffer.p1(0);
      int var13 = packet.buffer.pos;
      packet.buffer.p1(var4);
      packet.buffer.p1(var17);
      packet.buffer.p1(var11);
      ResourceCache.method1491(packet.buffer, var3);
      packet.buffer.psize1(packet.buffer.pos - var13);
      client.stream.writeLater(packet);
      return 1;
    }
    if (var0 == 5009) {
      Statics46.anInt441 -= 2;
      var3 = stringStack[Statics46.anInt441];
      String var9 = stringStack[Statics46.anInt441 + 1];
      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.SEND_PRIVATE_CHAT, client.stream.encryptor);
      packet.buffer.p2(0);
      int var10 = packet.buffer.pos;
      packet.buffer.pcstr(var3);
      ResourceCache.method1491(packet.buffer, var9);
      packet.buffer.pSize2(packet.buffer.pos - var10);
      client.stream.writeLater(packet);
      return 1;
    }
    if (var0 != 5015) {
      if (var0 == 5016) {
        intStack[++Statics46.anInt442 - 1] = client.tradeChatMode;
        return 1;
      }
      if (var0 == 5017) {
        var7 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = ChatHistory.getMessageCount(var7);
        return 1;
      }
      if (var0 == 5018) {
        var7 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = Statics47.method321(var7);
        return 1;
      }
      if (var0 == 5019) {
        var7 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = Clock.method723(var7);
        return 1;
      }
      if (var0 == 5020) {
        var3 = stringStack[--Statics46.anInt441];
        WorldMapAreaChunk_Sub3.method368(var3);
        return 1;
      }
      if (var0 == 5021) {
        client.aString1091 = stringStack[--Statics46.anInt441].toLowerCase().trim();
        return 1;
      }
      if (var0 == 5022) {
        stringStack[++Statics46.anInt441 - 1] = client.aString1091;
        return 1;
      }
      if (var0 == 5023) {
        var3 = stringStack[--Statics46.anInt441];
        System.out.println(var3);
        return 1;
      }
      return 2;
    }
    if (PlayerEntity.local != null && PlayerEntity.local.namePair != null) {
      var3 = PlayerEntity.local.namePair.getRaw();
    } else {
      var3 = "";
    }

    stringStack[++Statics46.anInt441 - 1] = var3;
    return 1;
  }

  static int method953(int var0) {
    int var3;
    if (var0 == 4200) {
      var3 = intStack[--Statics46.anInt442];
      stringStack[++Statics46.anInt441 - 1] = ItemDefinition.get(var3).name;
      return 1;
    }
    int var4;
    ItemDefinition var5;
    if (var0 == 4201) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      var5 = ItemDefinition.get(var3);
      if (var4 >= 1 && var4 <= 5 && var5.groundActions[var4 - 1] != null) {
        stringStack[++Statics46.anInt441 - 1] = var5.groundActions[var4 - 1];
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 4202) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      var5 = ItemDefinition.get(var3);
      if (var4 >= 1 && var4 <= 5 && var5.actions[var4 - 1] != null) {
        stringStack[++Statics46.anInt441 - 1] = var5.actions[var4 - 1];
      } else {
        stringStack[++Statics46.anInt441 - 1] = "";
      }

      return 1;
    }
    if (var0 == 4203) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = ItemDefinition.get(var3).value;
      return 1;
    }
    if (var0 == 4204) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = ItemDefinition.get(var3).stackable == 1 ? 1 : 0;
      return 1;
    }
    ItemDefinition var6;
    if (var0 == 4205) {
      var3 = intStack[--Statics46.anInt442];
      var6 = ItemDefinition.get(var3);
      if (var6.noteTemplateId == -1 && var6.noteId >= 0) {
        intStack[++Statics46.anInt442 - 1] = var6.noteId;
      } else {
        intStack[++Statics46.anInt442 - 1] = var3;
      }

      return 1;
    }
    if (var0 == 4206) {
      var3 = intStack[--Statics46.anInt442];
      var6 = ItemDefinition.get(var3);
      if (var6.noteTemplateId >= 0 && var6.noteId >= 0) {
        intStack[++Statics46.anInt442 - 1] = var6.noteId;
      } else {
        intStack[++Statics46.anInt442 - 1] = var3;
      }

      return 1;
    }
    if (var0 == 4207) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = ItemDefinition.get(var3).members ? 1 : 0;
      return 1;
    }
    if (var0 == 4208) {
      var3 = intStack[--Statics46.anInt442];
      var6 = ItemDefinition.get(var3);
      if (var6.anInt712 == -1 && var6.anInt715 >= 0) {
        intStack[++Statics46.anInt442 - 1] = var6.anInt715;
      } else {
        intStack[++Statics46.anInt442 - 1] = var3;
      }

      return 1;
    }
    if (var0 == 4209) {
      var3 = intStack[--Statics46.anInt442];
      var6 = ItemDefinition.get(var3);
      if (var6.anInt712 >= 0 && var6.anInt715 >= 0) {
        intStack[++Statics46.anInt442 - 1] = var6.anInt715;
      } else {
        intStack[++Statics46.anInt442 - 1] = var3;
      }

      return 1;
    }
    if (var0 == 4210) {
      String var7 = stringStack[--Statics46.anInt441];
      var4 = intStack[--Statics46.anInt442];
      Canvas.method640(var7, var4 == 1);
      intStack[++Statics46.anInt442 - 1] = DefaultAudioSystemProvider.anInt142;
      return 1;
    }
    if (var0 != 4211) {
      if (var0 == 4212) {
        anInt748 = 0;
        return 1;
      }
      return 2;
    }
    if (WorldMapLabelIcon.grandExchangeSearchResults != null && anInt748 < DefaultAudioSystemProvider.anInt142) {
      intStack[++Statics46.anInt442 - 1] = WorldMapLabelIcon.grandExchangeSearchResults[++anInt748 - 1] & '\uffff';
    } else {
      intStack[++Statics46.anInt442 - 1] = -1;
    }

    return 1;
  }

  static int method472(int var0) {
    String var3;
    int var4;
    if (var0 == 4100) {
      var3 = stringStack[--Statics46.anInt441];
      var4 = intStack[--Statics46.anInt442];
      stringStack[++Statics46.anInt441 - 1] = var3 + var4;
      return 1;
    }
    String var5;
    if (var0 == 4101) {
      Statics46.anInt441 -= 2;
      var3 = stringStack[Statics46.anInt441];
      var5 = stringStack[Statics46.anInt441 + 1];
      stringStack[++Statics46.anInt441 - 1] = var3 + var5;
      return 1;
    }
    if (var0 == 4102) {
      var3 = stringStack[--Statics46.anInt441];
      var4 = intStack[--Statics46.anInt442];
      stringStack[++Statics46.anInt441 - 1] = var3 + HealthBar.toString(var4, true);
      return 1;
    }
    if (var0 == 4103) {
      var3 = stringStack[--Statics46.anInt441];
      stringStack[++Statics46.anInt441 - 1] = var3.toLowerCase();
      return 1;
    }
    int var6;
    int var9;
    if (var0 == 4104) {
      var6 = intStack[--Statics46.anInt442];
      long var7 = 86400000L * (11745L + (long) var6);
      Statics46.aCalendar438.setTime(new Date(var7));
      var9 = Statics46.aCalendar438.get(5);
      int var16 = Statics46.aCalendar438.get(2);
      int var11 = Statics46.aCalendar438.get(1);
      stringStack[++Statics46.anInt441 - 1] = var9 + "-" + Statics46.aStringArray448[var16] + "-" + var11;
      return 1;
    }
    if (var0 != 4105) {
      if (var0 == 4106) {
        var6 = intStack[--Statics46.anInt442];
        stringStack[++Statics46.anInt441 - 1] = Integer.toString(var6);
        return 1;
      }
      if (var0 == 4107) {
        Statics46.anInt441 -= 2;
        intStack[++Statics46.anInt442 - 1] = method799(Statics46.method298(stringStack[Statics46.anInt441], stringStack[Statics46.anInt441 + 1], WorldMapLabelSize.aClientLocale_525));
        return 1;
      }
      int var12;
      byte[] var13;
      Font var14;
      if (var0 == 4108) {
        var3 = stringStack[--Statics46.anInt441];
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var12 = intStack[Statics46.anInt442 + 1];
        var13 = Archive.fonts.unpack(var12, 0);
        var14 = new Font(var13);
        intStack[++Statics46.anInt442 - 1] = var14.method1150(var3, var4);
        return 1;
      }
      if (var0 == 4109) {
        var3 = stringStack[--Statics46.anInt441];
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var12 = intStack[Statics46.anInt442 + 1];
        var13 = Archive.fonts.unpack(var12, 0);
        var14 = new Font(var13);
        intStack[++Statics46.anInt442 - 1] = var14.method1144(var3, var4);
        return 1;
      }
      if (var0 == 4110) {
        Statics46.anInt441 -= 2;
        var3 = stringStack[Statics46.anInt441];
        var5 = stringStack[Statics46.anInt441 + 1];
        if (intStack[--Statics46.anInt442] == 1) {
          stringStack[++Statics46.anInt441 - 1] = var3;
        } else {
          stringStack[++Statics46.anInt441 - 1] = var5;
        }

        return 1;
      }
      if (var0 == 4111) {
        var3 = stringStack[--Statics46.anInt441];
        stringStack[++Statics46.anInt441 - 1] = BaseFont.processGtLt(var3);
        return 1;
      }
      if (var0 == 4112) {
        var3 = stringStack[--Statics46.anInt441];
        var4 = intStack[--Statics46.anInt442];
        stringStack[++Statics46.anInt441 - 1] = var3 + (char) var4;
        return 1;
      }
      if (var0 == 4113) {
        var6 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = method725((char) var6) ? 1 : 0;
        return 1;
      }
      if (var0 == 4114) {
        var6 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = isAlphaNumeric((char) var6) ? 1 : 0;
        return 1;
      }
      if (var0 == 4115) {
        var6 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = AssociateComparator_Sub2.method607((char) var6) ? 1 : 0;
        return 1;
      }
      if (var0 == 4116) {
        var6 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = WorldMapIcon.method196((char) var6) ? 1 : 0;
        return 1;
      }
      if (var0 == 4117) {
        var3 = stringStack[--Statics46.anInt441];
        if (var3 != null) {
          intStack[++Statics46.anInt442 - 1] = var3.length();
        } else {
          intStack[++Statics46.anInt442 - 1] = 0;
        }

        return 1;
      }
      if (var0 == 4118) {
        var3 = stringStack[--Statics46.anInt441];
        Statics46.anInt442 -= 2;
        var4 = intStack[Statics46.anInt442];
        var12 = intStack[Statics46.anInt442 + 1];
        stringStack[++Statics46.anInt441 - 1] = var3.substring(var4, var12);
        return 1;
      }
      if (var0 == 4119) {
        var3 = stringStack[--Statics46.anInt441];
        StringBuilder var15 = new StringBuilder(var3.length());
        boolean var17 = false;

        for (var9 = 0; var9 < var3.length(); ++var9) {
          char var10 = var3.charAt(var9);
          if (var10 == '<') {
            var17 = true;
          } else if (var10 == '>') {
            var17 = false;
          } else if (!var17) {
            var15.append(var10);
          }
        }

        stringStack[++Statics46.anInt441 - 1] = var15.toString();
        return 1;
      }
      if (var0 == 4120) {
        var3 = stringStack[--Statics46.anInt441];
        var4 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = var3.indexOf(var4);
        return 1;
      }
      if (var0 == 4121) {
        Statics46.anInt441 -= 2;
        var3 = stringStack[Statics46.anInt441];
        var5 = stringStack[Statics46.anInt441 + 1];
        var12 = intStack[--Statics46.anInt442];
        intStack[++Statics46.anInt442 - 1] = var3.indexOf(var5, var12);
        return 1;
      }
      if (var0 == 4122) {
        var3 = stringStack[--Statics46.anInt441];
        stringStack[++Statics46.anInt441 - 1] = var3.toUpperCase();
        return 1;
      }
      return 2;
    }
    Statics46.anInt441 -= 2;
    var3 = stringStack[Statics46.anInt441];
    var5 = stringStack[Statics46.anInt441 + 1];
    if (PlayerEntity.local.model != null && PlayerEntity.local.model.female) {
      stringStack[++Statics46.anInt441 - 1] = var5;
    } else {
      stringStack[++Statics46.anInt441 - 1] = var3;
    }

    return 1;
  }

  static int method323(int var0, boolean var2) {
    int var3 = -1;
    InterfaceComponent var4;
    if (var0 >= 2000) {
      var0 -= 1000;
      var3 = intStack[--Statics46.anInt442];
      var4 = InterfaceComponent.lookup(var3);
    } else {
      var4 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    }

    if (var0 == 1000) {
      Statics46.anInt442 -= 4;
      var4.xMargin = intStack[Statics46.anInt442];
      var4.yMargin = intStack[Statics46.anInt442 + 1];
      var4.xLayout = intStack[Statics46.anInt442 + 2];
      var4.yLayout = intStack[Statics46.anInt442 + 3];
      InterfaceComponent.invalidate(var4);
      client.instance.updateComponentMargin(var4);
      if (var3 != -1 && var4.type == 0) {
        InterfaceComponent.revalidateScroll(client.interfaces[var3 >> 16], var4, false);
      }

      return 1;
    }
    if (var0 == 1001) {
      Statics46.anInt442 -= 4;
      var4.baseWidth = intStack[Statics46.anInt442];
      var4.baseHeight = intStack[Statics46.anInt442 + 1];
      var4.xAlignment = intStack[Statics46.anInt442 + 2];
      var4.yAlignment = intStack[Statics46.anInt442 + 3];
      InterfaceComponent.invalidate(var4);
      client.instance.updateComponentMargin(var4);
      if (var3 != -1 && var4.type == 0) {
        InterfaceComponent.revalidateScroll(client.interfaces[var3 >> 16], var4, false);
      }

      return 1;
    }
    if (var0 == 1003) {
      boolean var5 = intStack[--Statics46.anInt442] == 1;
      if (var5 != var4.explicitlyHidden) {
        var4.explicitlyHidden = var5;
        InterfaceComponent.invalidate(var4);
      }

      return 1;
    }
    if (var0 == 1005) {
      var4.noClickThrough = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 1006) {
      var4.noScrollThrough = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    return 2;
  }

  static int method174(int var0) {
    int var3;
    int var4;
    if (var0 == 4000) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var4 + var3;
      return 1;
    }
    if (var0 == 4001) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 - var4;
      return 1;
    }
    if (var0 == 4002) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var4 * var3;
      return 1;
    }
    if (var0 == 4003) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 / var4;
      return 1;
    }
    if (var0 == 4004) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = (int) (Math.random() * (double) var3);
      return 1;
    }
    if (var0 == 4005) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = (int) (Math.random() * (double) (var3 + 1));
      return 1;
    }
    if (var0 == 4006) {
      Statics46.anInt442 -= 5;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      int var5 = intStack[Statics46.anInt442 + 2];
      int var6 = intStack[Statics46.anInt442 + 3];
      int var7 = intStack[Statics46.anInt442 + 4];
      intStack[++Statics46.anInt442 - 1] = var3 + (var4 - var3) * (var7 - var5) / (var6 - var5);
      return 1;
    }
    if (var0 == 4007) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 + var4 * var3 / 100;
      return 1;
    }
    if (var0 == 4008) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 | 1 << var4;
      return 1;
    }
    if (var0 == 4009) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 & -1 - (1 << var4);
      return 1;
    }
    if (var0 == 4010) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = (var3 & 1 << var4) != 0 ? 1 : 0;
      return 1;
    }
    if (var0 == 4011) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 % var4;
      return 1;
    }
    if (var0 == 4012) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      if (var3 == 0) {
        intStack[++Statics46.anInt442 - 1] = 0;
      } else {
        intStack[++Statics46.anInt442 - 1] = (int) Math.pow(var3, var4);
      }

      return 1;
    }
    if (var0 == 4013) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      if (var3 == 0) {
        intStack[++Statics46.anInt442 - 1] = 0;
        return 1;
      }
      switch (var4) {
        case 0:
          intStack[++Statics46.anInt442 - 1] = Integer.MAX_VALUE;
          break;
        case 1:
          intStack[++Statics46.anInt442 - 1] = var3;
          break;
        case 2:
          intStack[++Statics46.anInt442 - 1] = (int) Math.sqrt(var3);
          break;
        case 3:
          intStack[++Statics46.anInt442 - 1] = (int) Math.cbrt(var3);
          break;
        case 4:
          intStack[++Statics46.anInt442 - 1] = (int) Math.sqrt(Math.sqrt(var3));
          break;
        default:
          intStack[++Statics46.anInt442 - 1] = (int) Math.pow(var3, 1.0D / (double) var4);
      }

      return 1;
    }
    if (var0 == 4014) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 & var4;
      return 1;
    }
    if (var0 == 4015) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = var3 | var4;
      return 1;
    }
    if (var0 == 4018) {
      Statics46.anInt442 -= 3;
      long var8 = intStack[Statics46.anInt442];
      long var10 = intStack[Statics46.anInt442 + 1];
      long var12 = intStack[Statics46.anInt442 + 2];
      intStack[++Statics46.anInt442 - 1] = (int) (var8 * var12 / var10);
      return 1;
    }
    return 2;
  }

  static int method356(int var0) {
    int var3;
    if (var0 == 3903) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.stockMarketOffers[var3].getType();
      return 1;
    }
    if (var0 == 3904) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.stockMarketOffers[var3].itemId;
      return 1;
    }
    if (var0 == 3905) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.stockMarketOffers[var3].unitPrice;
      return 1;
    }
    if (var0 == 3906) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.stockMarketOffers[var3].quantity;
      return 1;
    }
    if (var0 == 3907) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.stockMarketOffers[var3].transferred;
      return 1;
    }
    if (var0 == 3908) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.stockMarketOffers[var3].spent;
      return 1;
    }
    int var12;
    if (var0 == 3910) {
      var3 = intStack[--Statics46.anInt442];
      var12 = client.stockMarketOffers[var3].getState();
      intStack[++Statics46.anInt442 - 1] = var12 == 0 ? 1 : 0;
      return 1;
    }
    if (var0 == 3911) {
      var3 = intStack[--Statics46.anInt442];
      var12 = client.stockMarketOffers[var3].getState();
      intStack[++Statics46.anInt442 - 1] = var12 == 2 ? 1 : 0;
      return 1;
    }
    if (var0 == 3912) {
      var3 = intStack[--Statics46.anInt442];
      var12 = client.stockMarketOffers[var3].getState();
      intStack[++Statics46.anInt442 - 1] = var12 == 5 ? 1 : 0;
      return 1;
    }
    if (var0 == 3913) {
      var3 = intStack[--Statics46.anInt442];
      var12 = client.stockMarketOffers[var3].getState();
      intStack[++Statics46.anInt442 - 1] = var12 == 1 ? 1 : 0;
      return 1;
    }
    boolean var13;
    if (var0 == 3914) {
      var13 = intStack[--Statics46.anInt442] == 1;
      if (StockmarketListing.manager != null) {
        StockmarketListing.manager.sort(StockmarketManager.nameComparator, var13);
      }

      return 1;
    }
    if (var0 == 3915) {
      var13 = intStack[--Statics46.anInt442] == 1;
      if (StockmarketListing.manager != null) {
        StockmarketListing.manager.sort(StockmarketManager.priceComparator, var13);
      }

      return 1;
    }
    if (var0 == 3916) {
      Statics46.anInt442 -= 2;
      var13 = intStack[Statics46.anInt442] == 1;
      boolean var4 = intStack[Statics46.anInt442 + 1] == 1;
      if (StockmarketListing.manager != null) {
        client.stockmarketListingWorldComparator.aBoolean349 = var4;
        StockmarketListing.manager.sort(client.stockmarketListingWorldComparator, var13);
      }

      return 1;
    }
    if (var0 == 3917) {
      var13 = intStack[--Statics46.anInt442] == 1;
      if (StockmarketListing.manager != null) {
        StockmarketListing.manager.sort(StockmarketManager.lifetimeComparator, var13);
      }

      return 1;
    }
    if (var0 == 3918) {
      var13 = intStack[--Statics46.anInt442] == 1;
      if (StockmarketListing.manager != null) {
        StockmarketListing.manager.sort(StockmarketManager.quantityComparator, var13);
      }

      return 1;
    }
    if (var0 == 3919) {
      intStack[++Statics46.anInt442 - 1] = StockmarketListing.manager == null ? 0 : StockmarketListing.manager.events.size();
      return 1;
    }
    StockmarketEvent var5;
    if (var0 == 3920) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      intStack[++Statics46.anInt442 - 1] = var5.world;
      return 1;
    }
    if (var0 == 3921) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      stringStack[++Statics46.anInt441 - 1] = var5.method390();
      return 1;
    }
    if (var0 == 3922) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      stringStack[++Statics46.anInt441 - 1] = var5.method392();
      return 1;
    }
    if (var0 == 3923) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      long var6 = Clock.now() - StockmarketListing.ageAdjustment - var5.age;
      int var8 = (int) (var6 / 3600000L);
      int var9 = (int) ((var6 - (long) (var8 * 3600000L)) / 60000L);
      int var10 = (int) ((var6 - (long) (var8 * 3600000L) - (long) (var9 * 60000L)) / 1000L);
      String var11 = var8 + ":" + var9 / 10 + var9 % 10 + ":" + var10 / 10 + var10 % 10;
      stringStack[++Statics46.anInt441 - 1] = var11;
      return 1;
    }
    if (var0 == 3924) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      intStack[++Statics46.anInt442 - 1] = var5.aStockmarketListing551.quantity;
      return 1;
    }
    if (var0 == 3925) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      intStack[++Statics46.anInt442 - 1] = var5.aStockmarketListing551.unitPrice;
      return 1;
    }
    if (var0 == 3926) {
      var3 = intStack[--Statics46.anInt442];
      var5 = StockmarketListing.manager.events.get(var3);
      intStack[++Statics46.anInt442 - 1] = var5.aStockmarketListing551.itemId;
      return 1;
    }
    return 2;
  }

  static int method1393(int var0) {
    int var3;
    int var4;
    int var6;
    if (var0 == 3400) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      EnumDefinition var5 = AssociateComparator_Sub4.method664(var3);
      if (var5.output != 's') {
      }

      for (var6 = 0; var6 < var5.outputSize; ++var6) {
        if (var4 == var5.keys[var6]) {
          stringStack[++Statics46.anInt441 - 1] = var5.stringValues[var6];
          var5 = null;
          break;
        }
      }

      if (var5 != null) {
        stringStack[++Statics46.anInt441 - 1] = var5.defaultString;
      }

      return 1;
    }
    if (var0 != 3408) {
      if (var0 == 3411) {
        var3 = intStack[--Statics46.anInt442];
        EnumDefinition var9 = AssociateComparator_Sub4.method664(var3);
        intStack[++Statics46.anInt442 - 1] = var9.size();
        return 1;
      }
      return 2;
    }
    Statics46.anInt442 -= 4;
    var3 = intStack[Statics46.anInt442];
    var4 = intStack[Statics46.anInt442 + 1];
    int var7 = intStack[Statics46.anInt442 + 2];
    var6 = intStack[Statics46.anInt442 + 3];
    EnumDefinition var8 = AssociateComparator_Sub4.method664(var7);
    if (var3 == var8.input && var4 == var8.output) {
      for (int var10 = 0; var10 < var8.outputSize; ++var10) {
        if (var6 == var8.keys[var10]) {
          if (var4 == 115) {
            stringStack[++Statics46.anInt441 - 1] = var8.stringValues[var10];
          } else {
            intStack[++Statics46.anInt442 - 1] = var8.intValues[var10];
          }

          var8 = null;
          break;
        }
      }

      if (var8 != null) {
        if (var4 == 115) {
          stringStack[++Statics46.anInt441 - 1] = var8.defaultString;
        } else {
          intStack[++Statics46.anInt442 - 1] = var8.defaultInteger;
        }
      }

      return 1;
    }
    if (var4 == 115) {
      stringStack[++Statics46.anInt441 - 1] = "null";
    } else {
      intStack[++Statics46.anInt442 - 1] = 0;
    }

    return 1;
  }

  static int method132(int var0) {
    if (var0 == 3300) {
      intStack[++Statics46.anInt442 - 1] = client.ticks;
      return 1;
    }
    int var3;
    int var4;
    if (var0 == 3301) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = Inventory.getItemIdAt(var3, var4);
      return 1;
    }
    if (var0 == 3302) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = Inventory.getItemStackSizeAt(var3, var4);
      return 1;
    }
    if (var0 == 3303) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = Inventory.getCountIncludingStacks(var3, var4);
      return 1;
    }
    if (var0 == 3304) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = InventoryDefinition.lookup(var3).capacity;
      return 1;
    }
    if (var0 == 3305) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = Skills.currentLevels[var3];
      return 1;
    }
    if (var0 == 3306) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = Skills.levels[var3];
      return 1;
    }
    if (var0 == 3307) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = Skills.experiences[var3];
      return 1;
    }
    int var5;
    if (var0 == 3308) {
      var3 = SceneGraph.floorLevel;
      var4 = client.baseX + (PlayerEntity.local.absoluteX >> 7);
      var5 = client.baseY + (PlayerEntity.local.absoluteY >> 7);
      intStack[++Statics46.anInt442 - 1] = (var4 << 14) + var5 + (var3 << 28);
      return 1;
    }
    if (var0 == 3309) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = var3 >> 14 & 16383;
      return 1;
    }
    if (var0 == 3310) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = var3 >> 28;
      return 1;
    }
    if (var0 == 3311) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = var3 & 16383;
      return 1;
    }
    if (var0 == 3312) {
      intStack[++Statics46.anInt442 - 1] = client.membersWorld ? 1 : 0;
      return 1;
    }
    if (var0 == 3313) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442] + 32768;
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = Inventory.getItemIdAt(var3, var4);
      return 1;
    }
    if (var0 == 3314) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442] + 32768;
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = Inventory.getItemStackSizeAt(var3, var4);
      return 1;
    }
    if (var0 == 3315) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442] + 32768;
      var4 = intStack[Statics46.anInt442 + 1];
      intStack[++Statics46.anInt442 - 1] = Inventory.getCountIncludingStacks(var3, var4);
      return 1;
    }
    if (var0 == 3316) {
      if (client.rights >= 2) {
        intStack[++Statics46.anInt442 - 1] = client.rights;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3317) {
      intStack[++Statics46.anInt442 - 1] = client.updateTimer;
      return 1;
    }
    if (var0 == 3318) {
      intStack[++Statics46.anInt442 - 1] = client.currentWorld;
      return 1;
    }
    if (var0 == 3321) {
      intStack[++Statics46.anInt442 - 1] = client.energy;
      return 1;
    }
    if (var0 == 3322) {
      intStack[++Statics46.anInt442 - 1] = client.weight;
      return 1;
    }
    if (var0 == 3323) {
      if (client.aBoolean1056) {
        intStack[++Statics46.anInt442 - 1] = 1;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 3324) {
      intStack[++Statics46.anInt442 - 1] = client.currentWorldMask;
      return 1;
    }
    if (var0 == 3325) {
      Statics46.anInt442 -= 4;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1];
      var5 = intStack[Statics46.anInt442 + 2];
      int var6 = intStack[Statics46.anInt442 + 3];
      var3 += var4 << 14;
      var3 += var5 << 28;
      var3 += var6;
      intStack[++Statics46.anInt442 - 1] = var3;
      return 1;
    }
    return 2;
  }

  static int method556(int var0) {
    if (var0 == 3200) {
      Statics46.anInt442 -= 3;
      client.method884(intStack[Statics46.anInt442], intStack[Statics46.anInt442 + 1], intStack[Statics46.anInt442 + 2]);
      return 1;
    }
    if (var0 == 3201) {
      AudioOverrideEffect.method795(intStack[--Statics46.anInt442]);
      return 1;
    }
    if (var0 == 3202) {
      Statics46.anInt442 -= 2;
      ClientProt.method5(intStack[Statics46.anInt442]);
      return 1;
    }
    return 2;
  }

  static int method361(int var0, boolean var2) {
    String var3;
    if (var0 == 3100) {
      var3 = stringStack[--Statics46.anInt441];
      ChatHistory.messageReceived(0, "", var3);
      return 1;
    }
    if (var0 == 3101) {
      Statics46.anInt442 -= 2;
      PlayerEntity.animate(PlayerEntity.local, intStack[Statics46.anInt442], intStack[Statics46.anInt442 + 1]);
      return 1;
    }
    if (var0 == 3103) {
      if (!Statics46.aBoolean449) {
        Statics46.aBoolean439 = true;
      }

      return 1;
    }
    int var10;
    if (var0 == 3104) {
      var3 = stringStack[--Statics46.anInt441];
      var10 = 0;
      if (Strings.parseInt(var3)) {
        var10 = BaseFont.method1501(var3);
      }

      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PROCESS_NUMERIC_INPUT, client.stream.encryptor);
      packet.buffer.p4(var10);
      client.stream.writeLater(packet);
      return 1;
    }

    OutgoingPacket packet;
    if (var0 == 3105) {
      var3 = stringStack[--Statics46.anInt441];
      packet = OutgoingPacket.prepare(ClientProt.PROCESS_NAME_INPUT, client.stream.encryptor);
      packet.buffer.p1(var3.length() + 1);
      packet.buffer.pcstr(var3);
      client.stream.writeLater(packet);
      return 1;
    }

    if (var0 == 3106) {
      var3 = stringStack[--Statics46.anInt441];
      packet = OutgoingPacket.prepare(ClientProt.PROCESS_ALPHABETICAL_INPUT, client.stream.encryptor);
      packet.buffer.p1(var3.length() + 1);
      packet.buffer.pcstr(var3);
      client.stream.writeLater(packet);
      return 1;
    }
    String var6;
    int var14;
    if (var0 == 3107) {
      var14 = intStack[--Statics46.anInt442];
      var6 = stringStack[--Statics46.anInt441];
      WorldMapLabelSize.method365(var14, var6);
      return 1;
    }
    if (var0 == 3108) {
      Statics46.anInt442 -= 3;
      var14 = intStack[Statics46.anInt442];
      var10 = intStack[Statics46.anInt442 + 1];
      int var8 = intStack[Statics46.anInt442 + 2];
      InterfaceComponent var15 = InterfaceComponent.lookup(var8);
      InterfaceComponent.drag(var15, var14, var10);
      return 1;
    }
    if (var0 == 3109) {
      Statics46.anInt442 -= 2;
      var14 = intStack[Statics46.anInt442];
      var10 = intStack[Statics46.anInt442 + 1];
      InterfaceComponent var11 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
      InterfaceComponent.drag(var11, var14, var10);
      return 1;
    }
    if (var0 == 3110) {
      WorldMapObjectIcon.mouseCameraEnabled = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 3111) {
      intStack[++Statics46.anInt442 - 1] = client.preferences.roofsHidden ? 1 : 0;
      return 1;
    }
    if (var0 == 3112) {
      client.preferences.roofsHidden = intStack[--Statics46.anInt442] == 1;
      ClientPreferences.method854();
      return 1;
    }
    if (var0 == 3113) {
      var3 = stringStack[--Statics46.anInt441];
      boolean var4 = intStack[--Statics46.anInt442] == 1;
      Browser.openURL0(var3, var4, false);
      return 1;
    }
    if (var0 == 3115) {
      var14 = intStack[--Statics46.anInt442];
      packet = OutgoingPacket.prepare(ClientProt.PROCESS_OBJ_INPUT, client.stream.encryptor);
      packet.buffer.p2(var14);
      client.stream.writeLater(packet);
      return 1;
    }
    if (var0 == 3116) {
      var14 = intStack[--Statics46.anInt442];
      Statics46.anInt441 -= 2;
      var6 = stringStack[Statics46.anInt441];
      String var5 = stringStack[Statics46.anInt441 + 1];
      if (var6.length() > 500) {
        return 1;
      }
      if (var5.length() > 500) {
        return 1;
      }
      OutgoingPacket var9 = OutgoingPacket.prepare(ClientProt.REPORT_BUG, client.stream.encryptor);
      var9.buffer.p2(1 + Buffer.stringLengthPlusOne(var6) + Buffer.stringLengthPlusOne(var5));
      var9.buffer.pcstr(var6);
      var9.buffer.pcstr(var5);
      var9.buffer.p1n(var14);
      client.stream.writeLater(var9);
      return 1;
    }
    if (var0 == 3117) {
      client.aBoolean1044 = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 3118) {
      client.displayMouseOverText = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 3119) {
      client.displaySelf = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 3120) {
      if (intStack[--Statics46.anInt442] == 1) {
        client.displayPlayerNames |= 1;
      } else {
        client.displayPlayerNames &= -2;
      }

      return 1;
    }
    if (var0 == 3121) {
      if (intStack[--Statics46.anInt442] == 1) {
        client.displayPlayerNames |= 2;
      } else {
        client.displayPlayerNames &= -3;
      }

      return 1;
    }
    if (var0 == 3122) {
      if (intStack[--Statics46.anInt442] == 1) {
        client.displayPlayerNames |= 4;
      } else {
        client.displayPlayerNames &= -5;
      }

      return 1;
    }
    if (var0 == 3123) {
      if (intStack[--Statics46.anInt442] == 1) {
        client.displayPlayerNames |= 8;
      } else {
        client.displayPlayerNames &= -9;
      }

      return 1;
    }
    if (var0 == 3124) {
      client.displayPlayerNames = 0;
      return 1;
    }
    if (var0 == 3125) {
      ContextMenu.Crosshair.display = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 3126) {
      client.displayLoadingMessages = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 3127) {
      method1306(intStack[--Statics46.anInt442] == 1);
      return 1;
    }
    if (var0 == 3128) {
      intStack[++Statics46.anInt442 - 1] = WorldMapElement.method243() ? 1 : 0;
      return 1;
    }
    if (var0 == 3129) {
      Statics46.anInt442 -= 2;
      Camera.oculusOrbSpeed = intStack[Statics46.anInt442];
      Camera.oculusOrbSlowSpeed = intStack[Statics46.anInt442 + 1];
      return 1;
    }
    if (var0 == 3130) {
      Statics46.anInt442 -= 2;
      return 1;
    }
    if (var0 == 3131) {
      --Statics46.anInt442;
      return 1;
    }
    if (var0 == 3132) {
      intStack[++Statics46.anInt442 - 1] = client.canvasWidth;
      intStack[++Statics46.anInt442 - 1] = client.canvasHeight;
      return 1;
    }
    if (var0 == 3133) {
      --Statics46.anInt442;
      return 1;
    }
    if (var0 == 3134) {
      return 1;
    }
    if (var0 == 3135) {
      Statics46.anInt442 -= 2;
      return 1;
    }
    if (var0 == 3136) {
      client.anInt1047 = 3;
      client.anInt1059 = intStack[--Statics46.anInt442];
      return 1;
    }
    if (var0 == 3137) {
      client.anInt1047 = 2;
      client.anInt1059 = intStack[--Statics46.anInt442];
      return 1;
    }
    if (var0 == 3138) {
      client.anInt1047 = 0;
      return 1;
    }
    if (var0 == 3139) {
      client.anInt1047 = 1;
      return 1;
    }
    if (var0 == 3140) {
      client.anInt1047 = 3;
      client.anInt1059 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585.uid : IdentikitDefinition.anInterfaceComponent1518.uid;
      return 1;
    }
    boolean var7;
    if (var0 == 3141) {
      var7 = intStack[--Statics46.anInt442] == 1;
      client.preferences.rememberMe = var7;
      ClientPreferences.method854();
      return 1;
    }
    if (var0 == 3142) {
      intStack[++Statics46.anInt442 - 1] = client.preferences.rememberMe ? 1 : 0;
      return 1;
    }
    if (var0 == 3143) {
      var7 = intStack[--Statics46.anInt442] == 1;
      client.rememberUsername = var7;
      if (!var7) {
        client.preferences.rememberedUsername = "";
        ClientPreferences.method854();
      }

      return 1;
    }
    if (var0 == 3144) {
      intStack[++Statics46.anInt442 - 1] = client.rememberUsername ? 1 : 0;
      return 1;
    }
    if (var0 == 3145) {
      return 1;
    }
    if (var0 == 3146) {
      var7 = intStack[--Statics46.anInt442] == 1;
      if (var7 == client.preferences.loginScreenAudioDisabled) {
        client.preferences.loginScreenAudioDisabled = !var7;
        ClientPreferences.method854();
      }

      return 1;
    }
    if (var0 == 3147) {
      intStack[++Statics46.anInt442 - 1] = client.preferences.loginScreenAudioDisabled ? 0 : 1;
      return 1;
    }
    if (var0 == 3148) {
      return 1;
    }
    if (var0 == 3149) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3150) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3151) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3152) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3153) {
      intStack[++Statics46.anInt442 - 1] = Login.anInt473;
      return 1;
    }
    if (var0 == 3154) {
      intStack[++Statics46.anInt442 - 1] = StockmarketListingQuantityComparator.method476();
      return 1;
    }
    if (var0 == 3155) {
      --Statics46.anInt441;
      return 1;
    }
    if (var0 == 3156) {
      return 1;
    }
    if (var0 == 3157) {
      Statics46.anInt442 -= 2;
      return 1;
    }
    if (var0 == 3158) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3159) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3160) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3161) {
      --Statics46.anInt442;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3162) {
      --Statics46.anInt442;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3163) {
      --Statics46.anInt441;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3164) {
      --Statics46.anInt442;
      stringStack[++Statics46.anInt441 - 1] = "";
      return 1;
    }
    if (var0 == 3165) {
      --Statics46.anInt442;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3166) {
      Statics46.anInt442 -= 2;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3167) {
      Statics46.anInt442 -= 2;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3168) {
      Statics46.anInt442 -= 2;
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      stringStack[++Statics46.anInt441 - 1] = "";
      return 1;
    }
    if (var0 == 3169) {
      return 1;
    }
    if (var0 == 3170) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3171) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3172) {
      --Statics46.anInt442;
      return 1;
    }
    if (var0 == 3173) {
      --Statics46.anInt442;
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    if (var0 == 3174) {
      --Statics46.anInt442;
      return 1;
    }
    if (var0 == 3175) {
      intStack[++Statics46.anInt442 - 1] = 0;
      return 1;
    }
    return var0 == 3176 ? 1 : 2;
  }

  static int method3(int var0, boolean var2) {
    InterfaceComponent var3;
    if (var0 >= 2000) {
      var0 -= 1000;
      var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
    } else {
      var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    }

    if (var0 == 1927) {
      if (Statics46.anInt440 >= 10) {
        throw new RuntimeException();
      }
      if (var3.anObjectArray1400 == null) {
        return 0;
      }
      ScriptEvent var4 = new ScriptEvent();
      var4.component = var3;
      var4.args = var3.anObjectArray1400;
      var4.anInt368 = Statics46.anInt440 + 1;
      client.inputOccuringEventScripts.add(var4);
      return 1;
    }
    return 2;
  }

  static int method720(int var0) {
    InterfaceComponent var3;
    if (var0 == 2700) {
      var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
      intStack[++Statics46.anInt442 - 1] = var3.itemId;
      return 1;
    }
    if (var0 == 2701) {
      var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
      if (var3.itemId != -1) {
        intStack[++Statics46.anInt442 - 1] = var3.itemStackSize;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 2702) {
      int var4 = intStack[--Statics46.anInt442];
      SubInterface var5 = client.subInterfaces.lookup(var4);
      if (var5 != null) {
        intStack[++Statics46.anInt442 - 1] = 1;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 2706) {
      intStack[++Statics46.anInt442 - 1] = client.rootInterfaceIndex;
      return 1;
    }
    return 2;
  }

  static int method32(int var0) {
    InterfaceComponent var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
    if (var0 == 2800) {
      intStack[++Statics46.anInt442 - 1] = InterfaceComponent.getSpellTargets(InterfaceComponent.getConfig(var3));
      return 1;
    }
    if (var0 != 2801) {
      if (var0 == 2802) {
        if (var3.name == null) {
          stringStack[++Statics46.anInt441 - 1] = "";
        } else {
          stringStack[++Statics46.anInt441 - 1] = var3.name;
        }

        return 1;
      }
      return 2;
    }
    int var4 = intStack[--Statics46.anInt442];
    --var4;
    if (var3.actions != null && var4 < var3.actions.length && var3.actions[var4] != null) {
      stringStack[++Statics46.anInt441 - 1] = var3.actions[var4];
    } else {
      stringStack[++Statics46.anInt441 - 1] = "";
    }

    return 1;
  }

  static int method739(int var0) {
    InterfaceComponent var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
    if (var0 == 2600) {
      intStack[++Statics46.anInt442 - 1] = var3.insetX;
      return 1;
    }
    if (var0 == 2601) {
      intStack[++Statics46.anInt442 - 1] = var3.insetY;
      return 1;
    }
    if (var0 == 2602) {
      stringStack[++Statics46.anInt441 - 1] = var3.text;
      return 1;
    }
    if (var0 == 2603) {
      intStack[++Statics46.anInt442 - 1] = var3.viewportWidth;
      return 1;
    }
    if (var0 == 2604) {
      intStack[++Statics46.anInt442 - 1] = var3.viewportHeight;
      return 1;
    }
    if (var0 == 2605) {
      intStack[++Statics46.anInt442 - 1] = var3.modelZoom;
      return 1;
    }
    if (var0 == 2606) {
      intStack[++Statics46.anInt442 - 1] = var3.xRotation;
      return 1;
    }
    if (var0 == 2607) {
      intStack[++Statics46.anInt442 - 1] = var3.yRotation;
      return 1;
    }
    if (var0 == 2608) {
      intStack[++Statics46.anInt442 - 1] = var3.zRotation;
      return 1;
    }
    if (var0 == 2609) {
      intStack[++Statics46.anInt442 - 1] = var3.alpha;
      return 1;
    }
    if (var0 == 2610) {
      intStack[++Statics46.anInt442 - 1] = var3.enabledAlpha;
      return 1;
    }
    if (var0 == 2611) {
      intStack[++Statics46.anInt442 - 1] = var3.foreground;
      return 1;
    }
    if (var0 == 2612) {
      intStack[++Statics46.anInt442 - 1] = var3.enabledForeground;
      return 1;
    }
    if (var0 == 2613) {
      intStack[++Statics46.anInt442 - 1] = var3.fillType.getOrdinal();
      return 1;
    }
    if (var0 == 2614) {
      intStack[++Statics46.anInt442 - 1] = var3.transparent ? 1 : 0;
      return 1;
    }
    return 2;
  }

  static int method218(int var0) {
    InterfaceComponent var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
    if (var0 == 2500) {
      intStack[++Statics46.anInt442 - 1] = var3.relativeX;
      return 1;
    }
    if (var0 == 2501) {
      intStack[++Statics46.anInt442 - 1] = var3.relativeY;
      return 1;
    }
    if (var0 == 2502) {
      intStack[++Statics46.anInt442 - 1] = var3.width;
      return 1;
    }
    if (var0 == 2503) {
      intStack[++Statics46.anInt442 - 1] = var3.height;
      return 1;
    }
    if (var0 == 2504) {
      intStack[++Statics46.anInt442 - 1] = var3.explicitlyHidden ? 1 : 0;
      return 1;
    }
    if (var0 == 2505) {
      intStack[++Statics46.anInt442 - 1] = var3.parentUid;
      return 1;
    }
    return 2;
  }

  static int method266(int var0, boolean var2) {
    InterfaceComponent var3;
    if (var0 >= 2000) {
      var0 -= 1000;
      var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
    } else {
      var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    }

    String var4 = stringStack[--Statics46.anInt441];
    int[] var5 = null;
    if (var4.length() > 0 && var4.charAt(var4.length() - 1) == 'Y') {
      int var6 = intStack[--Statics46.anInt442];
      if (var6 > 0) {
        for (var5 = new int[var6]; var6-- > 0; var5[var6] = intStack[--Statics46.anInt442]) {
        }
      }

      var4 = var4.substring(0, var4.length() - 1);
    }

    Object[] var7 = new Object[var4.length() + 1];

    int var8;
    for (var8 = var7.length - 1; var8 >= 1; --var8) {
      if (var4.charAt(var8 - 1) == 's') {
        var7[var8] = stringStack[--Statics46.anInt441];
      } else {
        var7[var8] = intStack[--Statics46.anInt442];
      }
    }

    var8 = intStack[--Statics46.anInt442];
    if (var8 != -1) {
      var7[0] = var8;
    } else {
      var7 = null;
    }

    if (var0 == 1400) {
      var3.pressListeners = var7;
    } else if (var0 == 1401) {
      var3.holdListeners = var7;
    } else if (var0 == 1402) {
      var3.releaseListeners = var7;
    } else if (var0 == 1403) {
      var3.mouseEnterListeners = var7;
    } else if (var0 == 1404) {
      var3.mouseExitListeners = var7;
    } else if (var0 == 1405) {
      var3.dragListeners = var7;
    } else if (var0 == 1406) {
      var3.targetExitListeners = var7;
    } else if (var0 == 1407) {
      var3.varTransmit = var7;
      var3.varTriggers = var5;
    } else if (var0 == 1408) {
      var3.renderListeners = var7;
    } else if (var0 == 1409) {
      var3.cs2Listeners = var7;
    } else if (var0 == 1410) {
      var3.dragReleaseListeners = var7;
    } else if (var0 == 1411) {
      var3.clickListeners = var7;
    } else if (var0 == 1412) {
      var3.hoverListeners = var7;
    } else if (var0 == 1414) {
      var3.itemTransmit = var7;
      var3.itemTriggers = var5;
    } else if (var0 == 1415) {
      var3.skillTransmit = var7;
      var3.skillTriggers = var5;
    } else if (var0 == 1416) {
      var3.targetEnterListeners = var7;
    } else if (var0 == 1417) {
      var3.scrollListeners = var7;
    } else if (var0 == 1418) {
      var3.anObjectArray1403 = var7;
    } else if (var0 == 1419) {
      var3.anObjectArray1398 = var7;
    } else if (var0 == 1420) {
      var3.anObjectArray1399 = var7;
    } else if (var0 == 1421) {
      var3.anObjectArray1396 = var7;
    } else if (var0 == 1422) {
      var3.anObjectArray1390 = var7;
    } else if (var0 == 1423) {
      var3.anObjectArray1397 = var7;
    } else if (var0 == 1424) {
      var3.anObjectArray1393 = var7;
    } else if (var0 == 1425) {
      var3.anObjectArray1391 = var7;
    } else if (var0 == 1426) {
      var3.anObjectArray1394 = var7;
    } else {
      if (var0 != 1427) {
        return 2;
      }

      var3.anObjectArray1400 = var7;
    }

    var3.decodedObjects = true;
    return 1;
  }

  static int method120(int var0, boolean var2) {
    boolean var3 = true;
    InterfaceComponent var4;
    if (var0 >= 2000) {
      var0 -= 1000;
      var4 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
      var3 = false;
    } else {
      var4 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    }

    int var11;
    if (var0 == 1300) {
      var11 = intStack[--Statics46.anInt442] - 1;
      if (var11 >= 0 && var11 <= 9) {
        var4.method964(var11, stringStack[--Statics46.anInt441]);
        return 1;
      }
      --Statics46.anInt441;
      return 1;
    }
    int var6;
    if (var0 == 1301) {
      Statics46.anInt442 -= 2;
      var11 = intStack[Statics46.anInt442];
      var6 = intStack[Statics46.anInt442 + 1];
      var4.parent = InterfaceComponent.lookup(var11, var6);
      return 1;
    }
    if (var0 == 1302) {
      var4.scrollBar = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    if (var0 == 1303) {
      var4.dragArea = intStack[--Statics46.anInt442];
      return 1;
    }
    if (var0 == 1304) {
      var4.dragAreaThreshold = intStack[--Statics46.anInt442];
      return 1;
    }
    if (var0 == 1305) {
      var4.name = stringStack[--Statics46.anInt441];
      return 1;
    }
    if (var0 == 1306) {
      var4.selectedAction = stringStack[--Statics46.anInt441];
      return 1;
    }
    if (var0 == 1307) {
      var4.actions = null;
      return 1;
    }
    if (var0 == 1308) {
      var4.prioritizeMenuOptions = intStack[--Statics46.anInt442] == 1;
      return 1;
    }
    byte[] var8;
    int var9;
    if (var0 != 1350) {
      byte var5;
      if (var0 == 1351) {
        Statics46.anInt442 -= 2;
        var5 = 10;
        var8 = new byte[]{(byte) intStack[Statics46.anInt442]};
        byte[] var10 = new byte[]{(byte) intStack[Statics46.anInt442 + 1]};
        method868(var4, var5, var8, var10);
        return 1;
      }
      if (var0 == 1352) {
        Statics46.anInt442 -= 3;
        var11 = intStack[Statics46.anInt442] - 1;
        var6 = intStack[Statics46.anInt442 + 1];
        var9 = intStack[Statics46.anInt442 + 2];
        if (var11 >= 0 && var11 <= 9) {
          InterfaceComponent.setKeyRate(var4, var11, var6, var9);
          return 1;
        }
        throw new RuntimeException();
      }
      if (var0 == 1353) {
        var5 = 10;
        var6 = intStack[--Statics46.anInt442];
        var9 = intStack[--Statics46.anInt442];
        InterfaceComponent.setKeyRate(var4, var5, var6, var9);
        return 1;
      }
      if (var0 == 1354) {
        --Statics46.anInt442;
        var11 = intStack[Statics46.anInt442] - 1;
        if (var11 >= 0 && var11 <= 9) {
          Statics47.method320(var4, var11);
          return 1;
        }
        throw new RuntimeException();
      }
      if (var0 == 1355) {
        var5 = 10;
        Statics47.method320(var4, var5);
        return 1;
      }
      return 2;
    }
    byte[] var7 = null;
    var8 = null;
    if (var3) {
      Statics46.anInt442 -= 10;

      for (var9 = 0; var9 < 10 && intStack[var9 + Statics46.anInt442] >= 0; var9 += 2) {
      }

      if (var9 > 0) {
        var7 = new byte[var9 / 2];
        var8 = new byte[var9 / 2];

        for (var9 -= 2; var9 >= 0; var9 -= 2) {
          var7[var9 / 2] = (byte) intStack[var9 + Statics46.anInt442];
          var8[var9 / 2] = (byte) intStack[var9 + Statics46.anInt442 + 1];
        }
      }
    } else {
      Statics46.anInt442 -= 2;
      var7 = new byte[]{(byte) intStack[Statics46.anInt442]};
      var8 = new byte[]{(byte) intStack[Statics46.anInt442 + 1]};
    }

    var9 = intStack[--Statics46.anInt442] - 1;
    if (var9 >= 0 && var9 <= 9) {
      method868(var4, var9, var7, var8);
      return 1;
    }
    throw new RuntimeException();
  }

  static int method484(int var0, boolean var2) {
    InterfaceComponent var3;
    if (var0 >= 2000) {
      var0 -= 1000;
      var3 = InterfaceComponent.lookup(intStack[--Statics46.anInt442]);
    } else {
      var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    }

    InterfaceComponent.invalidate(var3);
    if (var0 != 1200 && var0 != 1205 && var0 != 1212) {
      if (var0 == 1201) {
        var3.modelType = 2;
        var3.modelId = intStack[--Statics46.anInt442];
        return 1;
      }
      if (var0 == 1202) {
        var3.modelType = 3;
        var3.modelId = PlayerEntity.local.model.hash();
        return 1;
      }
      return 2;
    }
    Statics46.anInt442 -= 2;
    int var4 = intStack[Statics46.anInt442];
    int var5 = intStack[Statics46.anInt442 + 1];
    var3.itemId = var4;
    var3.itemStackSize = var5;
    ItemDefinition var6 = ItemDefinition.get(var4);
    var3.xRotation = var6.spritePitch;
    var3.zRotation = var6.spriteRoll;
    var3.yRotation = var6.spriteYaw;
    var3.modelOffsetX = var6.spriteTranslateX;
    var3.modelOffsetY = var6.spriteTranslateY;
    var3.modelZoom = var6.spriteScale;
    if (var0 == 1205) {
      var3.itemStackSizeMode = 0;
    } else if (var0 == 1212 | var6.stackable == 1) {
      var3.itemStackSizeMode = 1;
    } else {
      var3.itemStackSizeMode = 2;
    }

    if (var3.scaleZ > 0) {
      var3.modelZoom = var3.modelZoom * 32 / var3.scaleZ;
    } else if (var3.baseWidth > 0) {
      var3.modelZoom = var3.modelZoom * 32 / var3.baseWidth;
    }

    return 1;
  }

  static int method386(int var0, boolean var2) {
    int var3 = -1;
    InterfaceComponent var4;
    if (var0 >= 2000) {
      var0 -= 1000;
      var3 = intStack[--Statics46.anInt442];
      var4 = InterfaceComponent.lookup(var3);
    } else {
      var4 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    }

    if (var0 == 1100) {
      Statics46.anInt442 -= 2;
      var4.insetX = intStack[Statics46.anInt442];
      if (var4.insetX > var4.viewportWidth - var4.width) {
        var4.insetX = var4.viewportWidth - var4.width;
      }

      if (var4.insetX < 0) {
        var4.insetX = 0;
      }

      var4.insetY = intStack[Statics46.anInt442 + 1];
      if (var4.insetY > var4.viewportHeight - var4.height) {
        var4.insetY = var4.viewportHeight - var4.height;
      }

      if (var4.insetY < 0) {
        var4.insetY = 0;
      }

      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1101) {
      var4.foreground = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1102) {
      var4.filled = intStack[--Statics46.anInt442] == 1;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1103) {
      var4.alpha = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1104) {
      var4.lineWidth = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1105) {
      var4.materialId = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1106) {
      var4.spriteId = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1107) {
      var4.tileSprites = intStack[--Statics46.anInt442] == 1;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1108) {
      var4.modelType = 1;
      var4.modelId = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1109) {
      Statics46.anInt442 -= 6;
      var4.modelOffsetX = intStack[Statics46.anInt442];
      var4.modelOffsetY = intStack[Statics46.anInt442 + 1];
      var4.xRotation = intStack[Statics46.anInt442 + 2];
      var4.zRotation = intStack[Statics46.anInt442 + 3];
      var4.yRotation = intStack[Statics46.anInt442 + 4];
      var4.modelZoom = intStack[Statics46.anInt442 + 5];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    int var8;
    if (var0 == 1110) {
      var8 = intStack[--Statics46.anInt442];
      if (var8 != var4.animation) {
        var4.animation = var8;
        var4.animationFrame = 0;
        var4.animationFrameCycle = 0;
        InterfaceComponent.invalidate(var4);
      }

      return 1;
    }
    if (var0 == 1111) {
      var4.perpendicular = intStack[--Statics46.anInt442] == 1;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1112) {
      String var6 = stringStack[--Statics46.anInt441];
      if (!var6.equals(var4.text)) {
        var4.text = var6;
        InterfaceComponent.invalidate(var4);
      }

      return 1;
    }
    if (var0 == 1113) {
      var4.fontId = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1114) {
      Statics46.anInt442 -= 3;
      var4.horizontalMargin = intStack[Statics46.anInt442];
      var4.verticalMargin = intStack[Statics46.anInt442 + 1];
      var4.textSpacing = intStack[Statics46.anInt442 + 2];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1115) {
      var4.textShadowed = intStack[--Statics46.anInt442] == 1;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1116) {
      var4.borderThickness = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1117) {
      var4.shadowColor = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1118) {
      var4.flippedVertically = intStack[--Statics46.anInt442] == 1;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1119) {
      var4.flippedHorizontally = intStack[--Statics46.anInt442] == 1;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1120) {
      Statics46.anInt442 -= 2;
      var4.viewportWidth = intStack[Statics46.anInt442];
      var4.viewportHeight = intStack[Statics46.anInt442 + 1];
      InterfaceComponent.invalidate(var4);
      if (var3 != -1 && var4.type == 0) {
        InterfaceComponent.revalidateScroll(client.interfaces[var3 >> 16], var4, false);
      }

      return 1;
    }
    if (var0 == 1121) {
      Clock.processDialogActionPacket(var4.uid, var4.subComponentIndex);
      client.pleaseWaitComponent = var4;
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1122) {
      var4.enabledMaterialId = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1123) {
      var4.enabledForeground = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1124) {
      var4.enabledAlpha = intStack[--Statics46.anInt442];
      InterfaceComponent.invalidate(var4);
      return 1;
    }
    if (var0 == 1125) {
      var8 = intStack[--Statics46.anInt442];
      ComponentFillType var7 = (ComponentFillType) EnumType.getByOrdinal(ComponentFillType.getValues(), var8);
      if (var7 != null) {
        var4.fillType = var7;
        InterfaceComponent.invalidate(var4);
      }

      return 1;
    }
    boolean var5;
    if (var0 == 1126) {
      var5 = intStack[--Statics46.anInt442] == 1;
      var4.invertDivider = var5;
      return 1;
    }
    if (var0 == 1127) {
      var5 = intStack[--Statics46.anInt442] == 1;
      var4.transparent = var5;
      return 1;
    }
    return 2;
  }

  static int method1352(int var0, boolean var2) {
    InterfaceComponent var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    if (var0 == 1800) {
      intStack[++Statics46.anInt442 - 1] = InterfaceComponent.getSpellTargets(InterfaceComponent.getConfig(var3));
      return 1;
    }
    if (var0 != 1801) {
      if (var0 == 1802) {
        if (var3.name == null) {
          stringStack[++Statics46.anInt441 - 1] = "";
        } else {
          stringStack[++Statics46.anInt441 - 1] = var3.name;
        }

        return 1;
      }
      return 2;
    }
    int var4 = intStack[--Statics46.anInt442];
    --var4;
    if (var3.actions != null && var4 < var3.actions.length && var3.actions[var4] != null) {
      stringStack[++Statics46.anInt441 - 1] = var3.actions[var4];
    } else {
      stringStack[++Statics46.anInt441 - 1] = "";
    }

    return 1;
  }

  static int method1095(int var0, boolean var2) {
    InterfaceComponent var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    if (var0 == 1700) {
      intStack[++Statics46.anInt442 - 1] = var3.itemId;
      return 1;
    }
    if (var0 == 1701) {
      if (var3.itemId != -1) {
        intStack[++Statics46.anInt442 - 1] = var3.itemStackSize;
      } else {
        intStack[++Statics46.anInt442 - 1] = 0;
      }

      return 1;
    }
    if (var0 == 1702) {
      intStack[++Statics46.anInt442 - 1] = var3.subComponentIndex;
      return 1;
    }
    return 2;
  }

  static int method717(int var0, boolean var2) {
    InterfaceComponent var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    if (var0 == 1600) {
      intStack[++Statics46.anInt442 - 1] = var3.insetX;
      return 1;
    }
    if (var0 == 1601) {
      intStack[++Statics46.anInt442 - 1] = var3.insetY;
      return 1;
    }
    if (var0 == 1602) {
      stringStack[++Statics46.anInt441 - 1] = var3.text;
      return 1;
    }
    if (var0 == 1603) {
      intStack[++Statics46.anInt442 - 1] = var3.viewportWidth;
      return 1;
    }
    if (var0 == 1604) {
      intStack[++Statics46.anInt442 - 1] = var3.viewportHeight;
      return 1;
    }
    if (var0 == 1605) {
      intStack[++Statics46.anInt442 - 1] = var3.modelZoom;
      return 1;
    }
    if (var0 == 1606) {
      intStack[++Statics46.anInt442 - 1] = var3.xRotation;
      return 1;
    }
    if (var0 == 1607) {
      intStack[++Statics46.anInt442 - 1] = var3.yRotation;
      return 1;
    }
    if (var0 == 1608) {
      intStack[++Statics46.anInt442 - 1] = var3.zRotation;
      return 1;
    }
    if (var0 == 1609) {
      intStack[++Statics46.anInt442 - 1] = var3.alpha;
      return 1;
    }
    if (var0 == 1610) {
      intStack[++Statics46.anInt442 - 1] = var3.enabledAlpha;
      return 1;
    }
    if (var0 == 1611) {
      intStack[++Statics46.anInt442 - 1] = var3.foreground;
      return 1;
    }
    if (var0 == 1612) {
      intStack[++Statics46.anInt442 - 1] = var3.enabledForeground;
      return 1;
    }
    if (var0 == 1613) {
      intStack[++Statics46.anInt442 - 1] = var3.fillType.getOrdinal();
      return 1;
    }
    if (var0 == 1614) {
      intStack[++Statics46.anInt442 - 1] = var3.transparent ? 1 : 0;
      return 1;
    }
    return 2;
  }

  static int method1184(int var0, boolean var2) {
    InterfaceComponent var3 = var2 ? StockmarketListingLifetimeComparator.anInterfaceComponent585 : IdentikitDefinition.anInterfaceComponent1518;
    if (var0 == 1500) {
      intStack[++Statics46.anInt442 - 1] = var3.relativeX;
      return 1;
    }
    if (var0 == 1501) {
      intStack[++Statics46.anInt442 - 1] = var3.relativeY;
      return 1;
    }
    if (var0 == 1502) {
      intStack[++Statics46.anInt442 - 1] = var3.width;
      return 1;
    }
    if (var0 == 1503) {
      intStack[++Statics46.anInt442 - 1] = var3.height;
      return 1;
    }
    if (var0 == 1504) {
      intStack[++Statics46.anInt442 - 1] = var3.explicitlyHidden ? 1 : 0;
      return 1;
    }
    if (var0 == 1505) {
      intStack[++Statics46.anInt442 - 1] = var3.parentUid;
      return 1;
    }
    return 2;
  }

  static int process(int opcode, boolean var2) {
    if (opcode < 1000) {
      return method384(opcode, var2);
    }
    if (opcode < 1100) {
      return method323(opcode, var2);
    }
    if (opcode < 1200) {
      return method386(opcode, var2);
    }
    if (opcode < 1300) {
      return method484(opcode, var2);
    }
    if (opcode < 1400) {
      return method120(opcode, var2);
    }
    if (opcode < 1500) {
      return method266(opcode, var2);
    }
    if (opcode < 1600) {
      return method1184(opcode, var2);
    }
    if (opcode < 1700) {
      return method717(opcode, var2);
    }
    if (opcode < 1800) {
      return method1095(opcode, var2);
    }
    if (opcode < 1900) {
      return method1352(opcode, var2);
    }
    if (opcode < 2000) {
      return method3(opcode, var2);
    }
    if (opcode < 2100) {
      return method323(opcode, var2);
    }
    if (opcode < 2200) {
      return method386(opcode, var2);
    }
    if (opcode < 2300) {
      return method484(opcode, var2);
    }
    if (opcode < 2400) {
      return method120(opcode, var2);
    }
    if (opcode < 2500) {
      return method266(opcode, var2);
    }
    if (opcode < 2600) {
      return method218(opcode);
    }
    if (opcode < 2700) {
      return method739(opcode);
    }
    if (opcode < 2800) {
      return method720(opcode);
    }
    if (opcode < 2900) {
      return method32(opcode);
    }
    if (opcode < 3000) {
      return method3(opcode, var2);
    }
    if (opcode < 3200) {
      return method361(opcode, var2);
    }
    if (opcode < 3300) {
      return method556(opcode);
    }
    if (opcode < 3400) {
      return method132(opcode);
    }
    if (opcode < 3500) {
      return method1393(opcode);
    }
    if (opcode < 3700) {
      return method875(opcode);
    }
    if (opcode < 4000) {
      return method356(opcode);
    }
    if (opcode < 4100) {
      return method174(opcode);
    }
    if (opcode < 4200) {
      return method472(opcode);
    }
    if (opcode < 4300) {
      return method953(opcode);
    }
    if (opcode < 5100) {
      return method268(opcode);
    }
    if (opcode < 5400) {
      return method641(opcode);
    }
    if (opcode < 5600) {
      return method105(opcode);
    }
    if (opcode < 5700) {
      return method954(opcode);
    }
    if (opcode < 6300) {
      return method7(opcode);
    }
    if (opcode < 6600) {
      return method889(opcode);
    }
    return opcode < 6700 ? method718(opcode) : 2;
  }

  public static int method718(int var0) {
    int var3;
    if (var0 == 6600) {
      var3 = SceneGraph.floorLevel;
      int var9 = client.baseX + (PlayerEntity.local.absoluteX >> 7);
      int var5 = client.baseY + (PlayerEntity.local.absoluteY >> 7);
      client.getWorldMap().method1261(var3, var9, var5, true);
      return 1;
    }
    WorldMapCacheArea var11;
    if (var0 == 6601) {
      var3 = intStack[--Statics46.anInt442];
      String var16 = "";
      var11 = client.getWorldMap().getArea(var3);
      if (var11 != null) {
        var16 = var11.getName();
      }

      stringStack[++Statics46.anInt441 - 1] = var16;
      return 1;
    }
    if (var0 == 6602) {
      var3 = intStack[--Statics46.anInt442];
      client.getWorldMap().setArea(var3);
      return 1;
    }
    if (var0 == 6603) {
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().getDesiredZoom();
      return 1;
    }
    if (var0 == 6604) {
      var3 = intStack[--Statics46.anInt442];
      client.getWorldMap().method1226(var3);
      return 1;
    }
    if (var0 == 6605) {
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().method1246() ? 1 : 0;
      return 1;
    }
    WorldMapPosition var15;
    if (var0 == 6606) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      client.getWorldMap().setDestination(var15.x, var15.y);
      return 1;
    }
    if (var0 == 6607) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      client.getWorldMap().setPositionRegional(var15.x, var15.y);
      return 1;
    }
    if (var0 == 6608) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      client.getWorldMap().method1227(var15.floorLevel, var15.x, var15.y);
      return 1;
    }
    if (var0 == 6609) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      client.getWorldMap().method1237(var15.floorLevel, var15.x, var15.y);
      return 1;
    }
    if (var0 == 6610) {
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().getPanelX();
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().getPanelY();
      return 1;
    }
    WorldMapCacheArea var13;
    if (var0 == 6611) {
      var3 = intStack[--Statics46.anInt442];
      var13 = client.getWorldMap().getArea(var3);
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = 0;
      } else {
        intStack[++Statics46.anInt442 - 1] = var13.method69().getHash();
      }

      return 1;
    }
    if (var0 == 6612) {
      var3 = intStack[--Statics46.anInt442];
      var13 = client.getWorldMap().getArea(var3);
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
      } else {
        intStack[++Statics46.anInt442 - 1] = (var13.method70() - var13.getMinRegionX() + 1) * 64;
        intStack[++Statics46.anInt442 - 1] = (var13.method72() - var13.getMinRegionY() + 1) * 64;
      }

      return 1;
    }
    if (var0 == 6613) {
      var3 = intStack[--Statics46.anInt442];
      var13 = client.getWorldMap().getArea(var3);
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
        intStack[++Statics46.anInt442 - 1] = 0;
      } else {
        intStack[++Statics46.anInt442 - 1] = var13.getMinRegionX() * 64;
        intStack[++Statics46.anInt442 - 1] = var13.getMinRegionY() * 64;
        intStack[++Statics46.anInt442 - 1] = var13.method70() * 64 + 64 - 1;
        intStack[++Statics46.anInt442 - 1] = var13.method72() * 64 + 64 - 1;
      }

      return 1;
    }
    if (var0 == 6614) {
      var3 = intStack[--Statics46.anInt442];
      var13 = client.getWorldMap().getArea(var3);
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var13.getZoomPercent();
      }

      return 1;
    }
    if (var0 == 6615) {
      var15 = client.getWorldMap().method1240();
      if (var15 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var15.x;
        intStack[++Statics46.anInt442 - 1] = var15.y;
      }

      return 1;
    }
    if (var0 == 6616) {
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().method1260();
      return 1;
    }
    if (var0 == 6617) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      var13 = client.getWorldMap().getActiveArea();
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
        return 1;
      }
      int[] var14 = var13.toScreen(var15.floorLevel, var15.x, var15.y);
      if (var14 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var14[0];
        intStack[++Statics46.anInt442 - 1] = var14[1];
      }

      return 1;
    }
    WorldMapPosition var7;
    if (var0 == 6618) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      var13 = client.getWorldMap().getActiveArea();
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
        return 1;
      }
      var7 = var13.getPosition(var15.x, var15.y);
      if (var7 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var7.getHash();
      }

      return 1;
    }
    WorldMapPosition var12;
    if (var0 == 6619) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var12 = new WorldMapPosition(intStack[Statics46.anInt442 + 1]);
      WorldMapLabel.method233(var3, var12, false);
      return 1;
    }
    if (var0 == 6620) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var12 = new WorldMapPosition(intStack[Statics46.anInt442 + 1]);
      WorldMapLabel.method233(var3, var12, true);
      return 1;
    }
    if (var0 == 6621) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var12 = new WorldMapPosition(intStack[Statics46.anInt442 + 1]);
      var11 = client.getWorldMap().getArea(var3);
      if (var11 == null) {
        intStack[++Statics46.anInt442 - 1] = 0;
        return 1;
      }
      intStack[++Statics46.anInt442 - 1] = var11.contains(var12.floorLevel, var12.x, var12.y) ? 1 : 0;
      return 1;
    }
    if (var0 == 6622) {
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().getPanelWidth();
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().getPanelHeight();
      return 1;
    }
    if (var0 == 6623) {
      var15 = new WorldMapPosition(intStack[--Statics46.anInt442]);
      var13 = client.getWorldMap().getAreaAt(var15.floorLevel, var15.x, var15.y);
      if (var13 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var13.getId();
      }

      return 1;
    }
    if (var0 == 6624) {
      client.getWorldMap().method1245(intStack[--Statics46.anInt442]);
      return 1;
    }
    if (var0 == 6625) {
      client.getWorldMap().method1231();
      return 1;
    }
    if (var0 == 6626) {
      client.getWorldMap().method1230(intStack[--Statics46.anInt442]);
      return 1;
    }
    if (var0 == 6627) {
      client.getWorldMap().method1233();
      return 1;
    }
    boolean var10;
    if (var0 == 6628) {
      var10 = intStack[--Statics46.anInt442] == 1;
      client.getWorldMap().setForceRefresh(var10);
      return 1;
    }
    if (var0 == 6629) {
      var3 = intStack[--Statics46.anInt442];
      client.getWorldMap().method1244(var3);
      return 1;
    }
    if (var0 == 6630) {
      var3 = intStack[--Statics46.anInt442];
      client.getWorldMap().setCategoryIndicative(var3);
      return 1;
    }
    if (var0 == 6631) {
      client.getWorldMap().method1243();
      return 1;
    }
    if (var0 == 6632) {
      var10 = intStack[--Statics46.anInt442] == 1;
      client.getWorldMap().method1258(var10);
      return 1;
    }
    boolean var4;
    if (var0 == 6633) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1] == 1;
      client.getWorldMap().method1251(var3, var4);
      return 1;
    }
    if (var0 == 6634) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var4 = intStack[Statics46.anInt442 + 1] == 1;
      client.getWorldMap().method1250(var3, var4);
      return 1;
    }
    if (var0 == 6635) {
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().method1254() ? 1 : 0;
      return 1;
    }
    if (var0 == 6636) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().isFunctionDisabled(var3) ? 1 : 0;
      return 1;
    }
    if (var0 == 6637) {
      var3 = intStack[--Statics46.anInt442];
      intStack[++Statics46.anInt442 - 1] = client.getWorldMap().isCategoryDisabled(var3) ? 1 : 0;
      return 1;
    }
    if (var0 == 6638) {
      Statics46.anInt442 -= 2;
      var3 = intStack[Statics46.anInt442];
      var12 = new WorldMapPosition(intStack[Statics46.anInt442 + 1]);
      var7 = client.getWorldMap().method1249(var3, var12);
      if (var7 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var7.getHash();
      }

      return 1;
    }
    WorldMapIcon var8;
    if (var0 == 6639) {
      var8 = client.getWorldMap().getFirstIcon();
      if (var8 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var8.getMapFunction();
        intStack[++Statics46.anInt442 - 1] = var8.max.getHash();
      }

      return 1;
    }
    if (var0 == 6640) {
      var8 = client.getWorldMap().getNextIcon();
      if (var8 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var8.getMapFunction();
        intStack[++Statics46.anInt442 - 1] = var8.max.getHash();
      }

      return 1;
    }
    WorldMapFunction var6;
    if (var0 == 6693) {
      var3 = intStack[--Statics46.anInt442];
      var6 = WorldMapFunction.get(var3);
      if (var6.aString1474 == null) {
        stringStack[++Statics46.anInt441 - 1] = "";
      } else {
        stringStack[++Statics46.anInt441 - 1] = var6.aString1474;
      }

      return 1;
    }
    if (var0 == 6694) {
      var3 = intStack[--Statics46.anInt442];
      var6 = WorldMapFunction.get(var3);
      intStack[++Statics46.anInt442 - 1] = var6.fontSize;
      return 1;
    }
    if (var0 == 6695) {
      var3 = intStack[--Statics46.anInt442];
      var6 = WorldMapFunction.get(var3);
      if (var6 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var6.category;
      }

      return 1;
    }
    if (var0 == 6696) {
      var3 = intStack[--Statics46.anInt442];
      var6 = WorldMapFunction.get(var3);
      if (var6 == null) {
        intStack[++Statics46.anInt442 - 1] = -1;
      } else {
        intStack[++Statics46.anInt442 - 1] = var6.spriteId;
      }

      return 1;
    }
    if (var0 == 6697) {
      intStack[++Statics46.anInt442 - 1] = EnumDefinition.aWorldMapScriptEvent_1443.anInt306;
      return 1;
    }
    if (var0 == 6698) {
      intStack[++Statics46.anInt442 - 1] = EnumDefinition.aWorldMapScriptEvent_1443.aWorldMapPosition307.getHash();
      return 1;
    }
    if (var0 == 6699) {
      intStack[++Statics46.anInt442 - 1] = EnumDefinition.aWorldMapScriptEvent_1443.aWorldMapPosition305.getHash();
      return 1;
    }
    return 2;
  }

  public static void fire(ScriptEvent var0) {
    process(var0, 500000);
  }

  public static boolean method1377(char var0) {
    return var0 == 160 || var0 == ' ' || var0 == '_' || var0 == '-';
  }

  public static void method681(String var0, int var1) {
    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.SET_CLANCHANNEL_RANK, client.stream.encryptor);
    packet.buffer.p1(Buffer.stringLengthPlusOne(var0) + 1);
    packet.buffer.p1(var1);
    packet.buffer.pcstr(var0);
    client.stream.writeLater(packet);
  }

  public static boolean method725(char var0) {
    if (var0 >= ' ' && var0 <= '~') {
      return true;
    }
    if (var0 >= 160 && var0 <= 255) {
      return true;
    }
    return var0 == 8364 || var0 == 338 || var0 == 8212 || var0 == 339 || var0 == 376;
  }

  public static int method799(int var0) {
    if (var0 > 0) {
      return 1;
    }
    return var0 < 0 ? -1 : 0;
  }

  public static boolean isAlphaNumeric(char var0) {
    return var0 >= '0' && var0 <= '9' || var0 >= 'A' && var0 <= 'Z' || var0 >= 'a' && var0 <= 'z';
  }

  public static void method868(InterfaceComponent var0, int var1, byte[] var2, byte[] var3) {
    if (var0.aByteArrayArray1365 == null) {
      if (var2 == null) {
        return;
      }

      var0.aByteArrayArray1365 = new byte[11][];
      var0.aByteArrayArray1363 = new byte[11][];
      var0.keyRate1 = new int[11];
      var0.keyRate2 = new int[11];
    }

    var0.aByteArrayArray1365[var1] = var2;
    if (var2 != null) {
      var0.aBoolean1372 = true;
    } else {
      var0.aBoolean1372 = false;

      for (int var4 = 0; var4 < var0.aByteArrayArray1365.length; ++var4) {
        if (var0.aByteArrayArray1365[var4] != null) {
          var0.aBoolean1372 = true;
          break;
        }
      }
    }

    var0.aByteArrayArray1363[var1] = var3;
  }

  public static void method867(int var0, int var1, int var2, int var3, boolean var4) {
    if (var2 < 1) {
      var2 = 1;
    }

    if (var3 < 1) {
      var3 = 1;
    }

    int var5 = var3 - 334;
    int var6;
    if (var5 < 0) {
      var6 = client.aShort922;
    } else if (var5 >= 100) {
      var6 = client.aShort912;
    } else {
      var6 = (client.aShort912 - client.aShort922) * var5 / 100 + client.aShort922;
    }

    int var7 = var3 * var6 * 512 / (var2 * 334);
    int var8;
    int var9;
    short var10;
    if (var7 < client.aShort910) {
      var10 = client.aShort910;
      var6 = var10 * var2 * 334 / (var3 * 512);
      if (var6 > client.aShort907) {
        var6 = client.aShort907;
        var8 = var3 * var6 * 512 / (var10 * 334);
        var9 = (var2 - var8) / 2;
        if (var4) {
          JagGraphics.resetDrawingArea();
          JagGraphics.fillRect(var0, var1, var9, var3, -16777216);
          JagGraphics.fillRect(var0 + var2 - var9, var1, var9, var3, -16777216);
        }

        var0 += var9;
        var2 -= var9 * 2;
      }
    } else if (var7 > client.aShort921) {
      var10 = client.aShort921;
      var6 = var10 * var2 * 334 / (var3 * 512);
      if (var6 < client.aShort915) {
        var6 = client.aShort915;
        var8 = var10 * var2 * 334 / (var6 * 512);
        var9 = (var3 - var8) / 2;
        if (var4) {
          JagGraphics.resetDrawingArea();
          JagGraphics.fillRect(var0, var1, var2, var9, -16777216);
          JagGraphics.fillRect(var0, var3 + var1 - var9, var2, var9, -16777216);
        }

        var1 += var9;
        var3 -= var9 * 2;
      }
    }

    client.viewportScale = var3 * var6 / 334;
    if (var2 != client.viewportWidth || var3 != client.viewportHeight) {
      StockmarketListingQuantityComparator.method480(var2, var3);
    }

    client.anInt909 = var0;
    client.anInt908 = var1;
    client.viewportWidth = var2;
    client.viewportHeight = var3;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  public void setType(int type) {
    this.type = type;
  }
}
