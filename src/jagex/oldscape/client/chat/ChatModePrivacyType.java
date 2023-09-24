package jagex.oldscape.client.chat;

public class ChatModePrivacyType {

  public static final ChatModePrivacyType A_CHAT_PRIVACY_TYPE___1569 = new ChatModePrivacyType(1);
  public static final ChatModePrivacyType A_CHAT_PRIVACY_TYPE___1568 = new ChatModePrivacyType(0);
  public static final ChatModePrivacyType A_CHAT_PRIVACY_TYPE___1567 = new ChatModePrivacyType(2);

  public final int index;

  ChatModePrivacyType(int var1) {
    this.index = var1;
  }

  public static ChatModePrivacyType valueOf(int index) {
    ChatModePrivacyType[] values = values();
    for (ChatModePrivacyType type : values) {
      if (index == type.index) {
        return type;
      }
    }
    return null;
  }

  public static ChatModePrivacyType[] values() {
    return new ChatModePrivacyType[]{A_CHAT_PRIVACY_TYPE___1567, A_CHAT_PRIVACY_TYPE___1568, A_CHAT_PRIVACY_TYPE___1569};
  }
}
