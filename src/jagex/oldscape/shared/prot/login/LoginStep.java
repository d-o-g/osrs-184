package jagex.oldscape.shared.prot.login;

import jagex.oldscape.EnumType;

public enum LoginStep implements EnumType {

  anEnum_Sub3_827(3, 0),
  anEnum_Sub3_828(1, 1),
  anEnum_Sub3_826(2, 2),
  anEnum_Sub3_825(0, 3);

  public final int anInt619;
  final int anInt824;

  LoginStep(int var3, int var4) {
    this.anInt619 = var3;
    this.anInt824 = var4;
  }

  public int getOrdinal() {
    return this.anInt824;
  }
}
