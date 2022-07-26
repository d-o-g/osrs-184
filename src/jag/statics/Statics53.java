package jag.statics;

import jag.commons.collection.IterableDoublyLinkedNodeQueue;
import jag.commons.collection.IterableNodeTable;
import jag.game.relationship.ChatLine;

public class Statics53 {

    public static final IterableNodeTable<ChatLine> CHAT_LINE_TABLE = new IterableNodeTable<>(1024);
    public static final IterableDoublyLinkedNodeQueue<ChatLine> CHAT_LINE_QUEUE = new IterableDoublyLinkedNodeQueue<>();
    public static int chatLineCount = 0;
    public static int anInt520;

}
