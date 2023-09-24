package jagex.oldscape.client.social;

import jagex.oldscape.client.client;
import jagex.oldscape.client.fonts.Font;
import jagex.jagex3.graphics.SpriteProvider;
import jagex.jagex3.js5.ReferenceTable;
import jagex.statics.Statics50;

public class FriendChatUser extends Associate<FriendChatUser> {

  public AssociateStatus aAssociateStatus_869;
  public AssociateStatus aAssociateStatus_868;

  public FriendChatUser() {
    this.aAssociateStatus_868 = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
    this.aAssociateStatus_869 = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
  }

  public static Font method708(ReferenceTable var0, ReferenceTable var1, int var2, int var3) {
    return !SpriteProvider.loadSprites(var0, var2, var3) ? null : Statics50.method221(var1.unpack(var2, var3));
  }

  public void method709() {
    this.aAssociateStatus_868 = client.relationshipManager.friendListContext.isCached(super.displayName) ? AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555 : AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1553;
  }

  public void method705() {
    this.aAssociateStatus_869 = client.relationshipManager.ignoreListContext.isCached(super.displayName) ? AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555 : AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1553;
  }

  public void method710() {
    this.aAssociateStatus_868 = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
  }

  public void method707() {
    this.aAssociateStatus_869 = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
  }

  public final boolean method711() {
    if (this.aAssociateStatus_868 == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554) {
      this.method709();
    }

    return this.aAssociateStatus_868 == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555;
  }

  public final boolean method706() {
    if (this.aAssociateStatus_869 == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554) {
      this.method705();
    }

    return this.aAssociateStatus_869 == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555;
  }
}
