package jagex.oldscape.client.option;

import jagex.oldscape.client.chat.ChatHistory;
import jagex.oldscape.client.client;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;
import jagex.oldscape.stockmarket.StockmarketListingWorldComparator;
import jagex.jagex3.js5.DiskFile;
import jagex.messaging.AsyncConnection;
import jagex.messaging.Buffer;
import jagex.statics.Statics5;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClientPreferences {

  public static final int CAPACITY = 6;

  public int resizable;
  public String rememberedUsername;
  public boolean roofsHidden;
  public boolean rememberMe;
  public LinkedHashMap<Integer, Integer> properties;
  public boolean loginScreenAudioDisabled;

  public ClientPreferences() {
    resizable = 1;
    rememberedUsername = null;
    rememberMe = false;
    properties = new LinkedHashMap<>();
  }

  public ClientPreferences(Buffer buffer) {
    resizable = 1;
    rememberedUsername = null;
    rememberMe = false;
    properties = new LinkedHashMap<>();

    if (buffer != null && buffer.payload != null) {
      int size = buffer.g1();
      if (size >= 0 && size <= ClientPreferences.CAPACITY) {
        if (buffer.g1() == 1) {
          roofsHidden = true;
        }

        if (size > 1) {
          loginScreenAudioDisabled = buffer.g1() == 1;
        }

        if (size > 3) {
          resizable = buffer.g1();
        }

        if (size > 2) {
          int pcount = buffer.g1();

          for (int i = 0; i < pcount; ++i) {
            int k = buffer.g4();
            int v = buffer.g4();
            properties.put(k, v);
          }
        }

        if (size > 4) {
          rememberedUsername = buffer.fastgstr();
        }

        if (size > 5) {
          rememberMe = buffer.gBit();
        }
      }
    }
  }

  public static DiskFile getFile(String var0, String var1, boolean var2) {
    File var3 = new File(StockmarketListingWorldComparator.cachePathFile, "preferences" + var0 + ".dat");
    if (var3.exists()) {
      try {
        return new DiskFile(var3, "rw", 10000L);
      } catch (IOException ignored) {
      }
    }

    String var4 = "";
    if (AsyncConnection.anInt1210 == 33) {
      var4 = "_rc";
    } else if (AsyncConnection.anInt1210 == 34) {
      var4 = "_wip";
    }

    File var5 = new File(Statics5.userhome, "jagex_" + var1 + "_preferences" + var0 + var4 + ".dat");
    DiskFile var6;
    if (!var2 && var5.exists()) {
      try {
        var6 = new DiskFile(var5, "rw", 10000L);
        return var6;
      } catch (IOException ignored) {
      }
    }

    try {
      var6 = new DiskFile(var3, "rw", 10000L);
      return var6;
    } catch (IOException var7) {
      throw new RuntimeException();
    }
  }

  public static void method854() {
    DiskFile var0 = null;

    try {
      var0 = getFile("", client.gameType.name, true);
      Buffer var1 = client.preferences.createOutputBuffer();
      var0.write(var1.payload, 0, var1.pos);
    } catch (Exception ignored) {
    }

    try {
      if (var0 != null) {
        var0.close(true);
      }
    } catch (Exception ignored) {
    }

  }

  public static ClientPreferences create() {
    DiskFile var0 = null;
    ClientPreferences var1 = new ClientPreferences();

    try {
      var0 = getFile("", client.gameType.name, false);
      byte[] var2 = new byte[(int) var0.length()];

      int var4;
      for (int var3 = 0; var3 < var2.length; var3 += var4) {
        var4 = var0.read(var2, var3, var2.length - var3);
        if (var4 == -1) {
          throw new IOException();
        }
      }

      var1 = new ClientPreferences(new Buffer(var2));
    } catch (Exception ignored) {
    }

    try {
      if (var0 != null) {
        var0.close();
      }
    } catch (Exception ignored) {
    }

    return var1;
  }

  public static void method368(String var0) {
    if (var0.equalsIgnoreCase("toggleroof")) {
      client.preferences.roofsHidden = !client.preferences.roofsHidden;
      method854();
      if (client.preferences.roofsHidden) {
        ChatHistory.messageReceived(99, "", "Roofs are now all hidden");
      } else {
        ChatHistory.messageReceived(99, "", "Roofs will only be removed selectively");
      }
    }

    if (var0.equalsIgnoreCase("displayfps")) {
      client.displayFps = !client.displayFps;
    }

    if (var0.equalsIgnoreCase("renderself")) {
      client.displaySelf = !client.displaySelf;
    }

    if (var0.equalsIgnoreCase("mouseovertext")) {
      client.displayMouseOverText = !client.displayMouseOverText;
    }

    if (client.rights >= 2) {
      if (var0.equalsIgnoreCase("errortest")) {
        throw new RuntimeException();
      }

      if (var0.equalsIgnoreCase("showcoord")) {
        client.worldMap.drawMouseOverPosition = !client.worldMap.drawMouseOverPosition;
      }

      if (var0.equalsIgnoreCase("fpson")) {
        client.displayFps = true;
      }

      if (var0.equalsIgnoreCase("fpsoff")) {
        client.displayFps = false;
      }

      if (var0.equalsIgnoreCase("gc")) {
        System.gc();
      }

      if (var0.equalsIgnoreCase("clientdrop")) {
        client.dropConnection();
      }
    }

    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PROCESS_COMMAND, client.stream.encryptor);
    packet.buffer.p1(var0.length() + 1);
    packet.buffer.pcstr(var0);
    client.stream.writeLater(packet);
  }

  public Buffer createOutputBuffer() {
    Buffer buffer = new Buffer(100);
    buffer.p1(CAPACITY);
    buffer.p1(roofsHidden ? 1 : 0);
    buffer.p1(loginScreenAudioDisabled ? 1 : 0);
    buffer.p1(resizable);
    buffer.p1(properties.size());

    for (Map.Entry<Integer, Integer> entry : properties.entrySet()) {
      buffer.p4(entry.getKey());
      buffer.p4(entry.getValue());
    }

    buffer.pcstr(rememberedUsername != null ? rememberedUsername : "");
    buffer.pBit(rememberMe);
    return buffer;
  }
}
