package jagex.oldscape.client.chat;

import jagex.oldscape.ClientParameter;
import jagex.oldscape.PlayerAccountType;
import jagex.datastructure.DoublyLinkedNode;
import jagex.oldscape.client.client;
import jagex.oldscape.client.social.AssociateStatus;
import jagex.oldscape.client.social.NamePair;
import jagex.statics.Statics53;

public class ChatLine extends DoublyLinkedNode {

  public String channel;
  public AssociateStatus ignore;
  public AssociateStatus friend;
  public NamePair channelPair;
  public int index;
  public int cycle;
  public int type;
  public String source;
  public String message;

  public ChatLine(int type, String channel, String source, String message) {
    this.friend = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
    this.ignore = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
    this.set(type, channel, source, message);
  }

  public static int method723(int key) {
    ChatLine line = Statics53.CHAT_LINE_TABLE.lookup(key);
    if (line == null) {
      return -1;
    }
    return line.previousDoubly == Statics53.CHAT_LINE_QUEUE.sentinel ? -1 : ((ChatLine) line.previousDoubly).index;
  }

  public void method775() {
    this.friend = client.relationshipManager.friendListContext.isCached(this.channelPair) ? AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555 : AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1553;
  }

  public void method828() {
    this.ignore = client.relationshipManager.ignoreListContext.isCached(this.channelPair) ? AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555 : AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1553;
  }

  public void set(int type, String channel, String source, String message) {
    this.index = ++Statics53.chatLineCount - 1;
    this.cycle = client.ticks;
    this.type = type;
    this.channel = channel;
    this.method886();
    this.source = source;
    this.message = message;
    this.method254();
    this.method592();
  }

  public final void method886() {
    if (this.channel != null) {
      this.channelPair = new NamePair(PlayerAccountType.getNameExcludingTags(this.channel), ClientParameter.loginTypeParameter);
    } else {
      this.channelPair = null;
    }

  }

  public void method592() {
    this.ignore = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
  }

  public void method254() {
    this.friend = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
  }

  public final boolean method693() {
    if (this.friend == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554) {
      this.method775();
    }

    return this.friend == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555;
  }

  public final boolean method882() {
    if (this.ignore == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554) {
      this.method828();
    }

    return this.ignore == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555;
  }
}
