package jagex.oldscape.client.option;

import jagex.oldscape.EnumType;
import jagex.jagex3.graphics.Sprite;

public enum AttackOptionPriority implements EnumType {

  DEPENDS(0),
  RIGHT(1),
  LEFT(2),
  HIDDEN(3);

  public static Sprite compass;

  public final int index;

  AttackOptionPriority(int index) {
    this.index = index;
  }

  public static AttackOptionPriority[] getValues() {
    return new AttackOptionPriority[]{HIDDEN, LEFT, RIGHT, DEPENDS};
  }

  public int getOrdinal() {
    return this.index;
  }
}
