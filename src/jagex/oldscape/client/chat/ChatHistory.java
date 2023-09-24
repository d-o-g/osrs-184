package jagex.oldscape.client.chat;

import jagex.oldscape.client.client;
import jagex.statics.Statics53;

public class ChatHistory {

  public final ChatLine[] buffer;
  public int caret;

  public ChatHistory() {
    buffer = new ChatLine[100];
  }

  public static ChatLine getLine(int type, int index) {
    ChatHistory history = client.chatHistory.get(type);
    return history.getLine(index);
  }

  public static int getMessageCount(int type) {
    ChatHistory history = client.chatHistory.get(type);
    return history == null ? 0 : history.getCaret();
  }

  public static void messageReceived(int type, String channel, String source, String message) {
    ChatHistory history = client.chatHistory.get(type);
    if (history == null) {
      history = new ChatHistory();
      client.chatHistory.put(type, history);
    }

    ChatLine line = history.insert(type, channel, source, message);
    Statics53.CHAT_LINE_TABLE.put(line, line.index);
    Statics53.CHAT_LINE_QUEUE.insert(line);
    client.anInt1066 = client.anInt1075;
  }

  public static void clear() {
    client.chatHistory.clear();
    Statics53.CHAT_LINE_TABLE.clear();
    Statics53.CHAT_LINE_QUEUE.clear();
    Statics53.chatLineCount = 0;
  }

  public static void messageReceived(int type, String channel, String source) {
    messageReceived(type, channel, source, null);
  }

  public ChatLine insert(int type, String channel, String source, String message) {
    ChatLine line = buffer[99];

    for (int i = caret; i > 0; --i) {
      if (i != 100) {
        buffer[i] = buffer[i - 1];
      }
    }

    if (line == null) {
      line = new ChatLine(type, channel, message, source);
    } else {
      line.unlink();
      line.unlinkDoubly();
      line.set(type, channel, message, source);
    }

    buffer[0] = line;
    if (caret < 100) {
      ++caret;
    }

    return line;
  }

  public ChatLine getLine(int index) {
    return index >= 0 && index < caret ? buffer[index] : null;
  }

  public int getCaret() {
    return caret;
  }
}
