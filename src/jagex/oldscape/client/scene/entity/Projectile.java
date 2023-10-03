package jagex.oldscape.client.scene.entity;

import jagex.oldscape.client.InterfaceComponent;
import jagex.oldscape.client.type.AnimationSequence;
import jagex.oldscape.client.type.EffectAnimation;

public final class Projectile extends Entity {

  public final AnimationSequence sequence;

  public final int id;
  public final int startX;
  public final int endCycle;
  public final int startY;
  public final int targetDistance;
  public final int floorLevel;
  public final int height;
  public final int startCycle;
  public final int slope;
  public final int targetIndex;
  public final int targetHeight;

  public boolean inMotion;

  public double absoluteX;
  public double speedX;
  public double absoluteY;
  public double speedY;
  public double aDouble1660;
  public double heightOffset;
  public double speedZ;
  public double speed;

  public int anInt1659;
  public int anInt1661;
  public int yRotation;
  public int xRotation;

  public Projectile(int id, int floorLevel, int startX, int startY, int height, int startCycle, int endCycle, int slope, int targetDistance, int targetIndex, int targetHeight) {
    anInt1659 = 0;
    anInt1661 = 0;
    this.id = id;
    this.floorLevel = floorLevel;
    this.startX = startX;
    this.startY = startY;
    this.height = height;
    this.startCycle = startCycle;
    this.endCycle = endCycle;
    this.slope = slope;
    this.targetDistance = targetDistance;
    this.targetIndex = targetIndex;
    this.targetHeight = targetHeight;
    inMotion = false;

    int animation = EffectAnimation.get(this.id).animation;
    sequence = animation != -1 ? AnimationSequence.get(animation) : null;

  }

  public static boolean method1192(InterfaceComponent var0) {
    if (var0.cs1Types == null) {
      return false;
    }
    for (int var1 = 0; var1 < var0.cs1Types.length; ++var1) {
      int var2 = InterfaceComponent.processCs1(var0, var1);
      int var3 = var0.cs1Values[var1];
      if (var0.cs1Types[var1] == 2) {
        if (var2 >= var3) {
          return false;
        }
      } else if (var0.cs1Types[var1] == 3) {
        if (var2 <= var3) {
          return false;
        }
      } else if (var0.cs1Types[var1] == 4) {
        if (var2 == var3) {
          return false;
        }
      } else if (var3 != var2) {
        return false;
      }
    }

    return true;
  }

  protected Model getModel() {
    EffectAnimation var1 = EffectAnimation.get(id);
    Model var2 = var1.getModel(anInt1659);
    if (var2 == null) {
      return null;
    }
    var2.method582(yRotation);
    return var2;
  }

  public void method1193(int var1) {
    inMotion = true;
    absoluteX += speedX * (double) var1;
    absoluteY += speedY * (double) var1;
    aDouble1660 += (double) var1 * (double) var1 * 0.5D * heightOffset + (double) var1 * speedZ;
    speedZ += heightOffset * (double) var1;
    xRotation = (int) (Math.atan2(speedX, speedY) * 325.949D) + 1024 & 2047;
    yRotation = (int) (Math.atan2(speedZ, speed) * 325.949D) & 2047;
    if (sequence != null) {
      anInt1661 += var1;

      while (true) {
        do {
          do {
            if (anInt1661 <= sequence.frameLengths[anInt1659]) {
              return;
            }

            anInt1661 -= sequence.frameLengths[anInt1659];
            ++anInt1659;
          } while (anInt1659 < sequence.frameIds.length);

          anInt1659 -= sequence.loopOffset;
        } while (anInt1659 >= 0 && anInt1659 < sequence.frameIds.length);

        anInt1659 = 0;
      }
    }
  }

  public void target(int x, int y, int height, int cycle) {
    double var5;
    if (!inMotion) {
      var5 = x - startX;
      double var7 = y - startY;
      double var9 = Math.sqrt(var7 * var7 + var5 * var5);
      absoluteX = (double) targetDistance * var5 / var9 + (double) startX;
      absoluteY = (double) targetDistance * var7 / var9 + (double) startY;
      aDouble1660 = this.height;
    }

    var5 = endCycle + 1 - cycle;
    speedX = ((double) x - absoluteX) / var5;
    speedY = ((double) y - absoluteY) / var5;
    speed = Math.sqrt(speedX * speedX + speedY * speedY);
    if (!inMotion) {
      speedZ = -speed * Math.tan(0.02454369D * (double) slope);
    }

    heightOffset = 2.0D * ((double) height - aDouble1660 - var5 * speedZ) / (var5 * var5);
  }
}
