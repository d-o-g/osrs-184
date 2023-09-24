package jagex.oldscape.client.type;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.oldscape.client.chat.ChatHistory;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;
import jagex.oldscape.client.worldmap.WorldMapScriptEvent;

public class EnumDefinition extends DoublyLinkedNode {

  public static final ReferenceCache<EnumDefinition> cache = new ReferenceCache<>(64);
  public static ReferenceTable table;
  public static WorldMapScriptEvent aWorldMapScriptEvent_1443;

  public int outputSize;
  public String defaultString;
  public char input;
  public char output;
  public int defaultInteger;
  public int[] keys;
  public String[] stringValues;
  public int[] intValues;

  public EnumDefinition() {
    defaultString = "null";
    outputSize = 0;
  }

  public static void sendFullIgnoreListMessage() {
    ChatHistory.messageReceived(30, "", "Your ignore list is full. Max of 100 for free users, and 400 for members");
  }

  void decode(Buffer buffer, int opcode) {
    if (opcode == 1) {
      input = (char) buffer.g1();
    } else if (opcode == 2) {
      output = (char) buffer.g1();
    } else if (opcode == 3) {
      defaultString = buffer.gstr();
    } else if (opcode == 4) {
      defaultInteger = buffer.g4();
    } else {
      if (opcode == 5) {
        outputSize = buffer.g2();
        keys = new int[outputSize];
        stringValues = new String[outputSize];
        for (int i = 0; i < outputSize; ++i) {
          keys[i] = buffer.g4();
          stringValues[i] = buffer.gstr();
        }
      } else if (opcode == 6) {
        outputSize = buffer.g2();
        keys = new int[outputSize];
        intValues = new int[outputSize];
        for (int i = 0; i < outputSize; ++i) {
          keys[i] = buffer.g4();
          intValues[i] = buffer.g4();
        }
      }
    }
  }

  public int size() {
    return outputSize;
  }

  public void decode(Buffer buffer) {
    while (true) {
      int opcode = buffer.g1();
      if (opcode == 0) {
        return;
      }
      decode(buffer, opcode);
    }
  }
}
