package jagex.statics;

import jagex.datastructure.instrusive.linklist.IterableDoublyLinkedNodeQueue;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;
import jagex.oldscape.client.chat.ChatLine;

public class Statics53 {

  public static final IterableNodeTable<ChatLine> CHAT_LINE_TABLE = new IterableNodeTable<>(1024);
  public static final IterableDoublyLinkedNodeQueue<ChatLine> CHAT_LINE_QUEUE = new IterableDoublyLinkedNodeQueue<>();
  public static int chatLineCount = 0;
  public static int anInt520;

}
