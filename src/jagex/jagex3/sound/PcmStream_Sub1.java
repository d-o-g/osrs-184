package jagex.jagex3.sound;

import jagex.datastructure.Node;
import jagex.datastructure.instrusive.linklist.NodeDeque;

public class PcmStream_Sub1 extends PcmStream {

  final NodeDeque<PcmStream> aNodeDeque454;
  final NodeDeque<PcmStream_Sub1_Listener> aNodeDeque373;
  int anInt377;
  int anInt378;

  public PcmStream_Sub1() {
    aNodeDeque454 = new NodeDeque<>();
    aNodeDeque373 = new NodeDeque<>();
    anInt378 = 0;
    anInt377 = -1;
  }

  void method304(int[] var1, int var2, int var3) {
    for (PcmStream var4 = aNodeDeque454.head(); var4 != null; var4 = aNodeDeque454.next()) {
      var4.method482(var1, var2, var3);
    }

  }

  void method302(int var1) {
    for (PcmStream var2 = aNodeDeque454.head(); var2 != null; var2 = aNodeDeque454.next()) {
      var2.method303(var1);
    }

  }

  public final synchronized void method311(int[] var1, int var2, int var3) {
    do {
      if (this.anInt377 < 0) {
        this.method304(var1, var2, var3);
        return;
      }

      if (var3 + this.anInt378 < this.anInt377) {
        this.anInt378 += var3;
        this.method304(var1, var2, var3);
        return;
      }

      int var4 = this.anInt377 - this.anInt378;
      this.method304(var1, var2, var4);
      var2 += var4;
      var3 -= var4;
      this.anInt378 += var4;
      this.method23();
      PcmStream_Sub1_Listener var5 = this.aNodeDeque373.head();
      synchronized (var5) {
        int var7 = var5.method500(this);
        if (var7 < 0) {
          var5.anInt380 = 0;
          this.method309(var5);
        } else {
          var5.anInt380 = var7;
          this.method310(var5.next, var5);
        }
      }
    } while (var3 != 0);

  }

  public final synchronized void method303(int var1) {
    do {
      if (this.anInt377 < 0) {
        this.method302(var1);
        return;
      }

      if (this.anInt378 + var1 < this.anInt377) {
        this.anInt378 += var1;
        this.method302(var1);
        return;
      }

      int var2 = this.anInt377 - this.anInt378;
      this.method302(var2);
      var1 -= var2;
      this.anInt378 += var2;
      this.method23();
      PcmStream_Sub1_Listener var3 = this.aNodeDeque373.head();
      synchronized (var3) {
        int var5 = var3.method500(this);
        if (var5 < 0) {
          var3.anInt380 = 0;
          this.method309(var3);
        } else {
          var3.anInt380 = var5;
          this.method310(var3.next, var3);
        }
      }
    } while (var1 != 0);

  }

  public PcmStream method308() {
    return this.aNodeDeque454.next();
  }

  void method23() {
    if (this.anInt378 > 0) {
      for (PcmStream_Sub1_Listener var1 = this.aNodeDeque373.head(); var1 != null; var1 = this.aNodeDeque373.next()) {
        var1.anInt380 -= this.anInt378;
      }

      this.anInt377 -= this.anInt378;
      this.anInt378 = 0;
    }

  }

  public final synchronized void method312(PcmStream var1) {
    this.aNodeDeque454.insert(var1);
  }

  public PcmStream method307() {
    return this.aNodeDeque454.head();
  }

  public final synchronized void removeDelegate(PcmStream var1) {
    var1.unlink();
  }

  void method309(PcmStream_Sub1_Listener var1) {
    var1.unlink();
    var1.method499();
    Node var2 = this.aNodeDeque373.sentinel.next;
    if (var2 == this.aNodeDeque373.sentinel) {
      this.anInt377 = -1;
    } else {
      this.anInt377 = ((PcmStream_Sub1_Listener) var2).anInt380;
    }

  }

  void method310(Node var1, PcmStream_Sub1_Listener var2) {
    while (this.aNodeDeque373.sentinel != var1 && ((PcmStream_Sub1_Listener) var1).anInt380 <= var2.anInt380) {
      var1 = var1.next;
    }

    NodeDeque.replace(var2, var1);
    this.anInt377 = ((PcmStream_Sub1_Listener) this.aNodeDeque373.sentinel.next).anInt380;
  }

  public int method305() {
    return 0;
  }
}
