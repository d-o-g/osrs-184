package jagex.jagex3.sound;

import jagex.oldscape.URLRequest;
import jagex.jagex3.sound.vorbis.RawAudioOverride;

public class PcmStream_Sub2 extends PcmStream {
  final int anInt379;
  final int anInt564;
  final boolean aBoolean763;
  int anInt377;
  int anInt378;
  int anInt574;
  int anInt372;
  int anInt367;
  int anInt568;
  int anInt380;
  int anInt112;
  int anInt375;
  int anInt386;
  int anInt366;
  int anInt696;

  PcmStream_Sub2(RawAudioOverride var1, int var2, int var3, int var4) {
    aVorbisNode_667 = var1;
    anInt379 = var1.start;
    anInt564 = var1.end;
    aBoolean763 = var1.aBoolean502;
    anInt568 = var2;
    anInt378 = var3;
    anInt377 = var4;
    anInt380 = 0;
    method592();
  }

  PcmStream_Sub2(RawAudioOverride var1, int var2, int var3) {
    aVorbisNode_667 = var1;
    anInt379 = var1.start;
    anInt564 = var1.end;
    aBoolean763 = var1.aBoolean502;
    anInt568 = var2;
    anInt378 = var3;
    anInt377 = 8192;
    anInt380 = 0;
    method592();
  }

  static int method601(int var0, int var1) {
    return var1 < 0 ? var0 : (int) ((double) var0 * Math.sqrt((double) (16384 - var1) * 1.220703125E-4D) + 0.5D);
  }

  static int method590(int var0, int var1) {
    return var1 < 0 ? -var0 : (int) ((double) var0 * Math.sqrt((double) var1 * 1.220703125E-4D) + 0.5D);
  }

  static int method573(byte[] var2, int[] var3, int var4, int var5, int var6, int var8, int var9, PcmStream_Sub2 var10, int var11, int var12) {
    int var7;
    if (var11 == 0 || (var7 = var5 + (var11 + (var9 - var4) - 257) / var11) > var8) {
      var7 = var8;
    }

    byte var13;
    int var10001;
    while (var5 < var7) {
      int var1 = var4 >> 8;
      var13 = var2[var1];
      var10001 = var5++;
      var3[var10001] += ((var13 << 8) + (var2[var1 + 1] - var13) * (var4 & 255)) * var6 >> 6;
      var4 += var11;
    }

    if (var11 == 0 || (var7 = var5 + (var11 + (var9 - var4) - 1) / var11) > var8) {
      var7 = var8;
    }

    for (int var1 = var12; var5 < var7; var4 += var11) {
      var13 = var2[var4 >> 8];
      var10001 = var5++;
      var3[var10001] += ((var13 << 8) + (var1 - var13) * (var4 & 255)) * var6 >> 6;
    }

    var10.anInt380 = var4;
    return var5;
  }

  static int method565(byte[] var2, int[] var3, int var4, int var5, int var6, int var8, int var9, PcmStream_Sub2 var10, int var11, int var12) {
    int var7;
    if (var11 == 0 || (var7 = var5 + (var11 + (var9 + 256 - var4)) / var11) > var8) {
      var7 = var8;
    }

    int var10001;
    int var1;
    while (var5 < var7) {
      var1 = var4 >> 8;
      byte var13 = var2[var1 - 1];
      var10001 = var5++;
      var3[var10001] += ((var13 << 8) + (var2[var1] - var13) * (var4 & 255)) * var6 >> 6;
      var4 += var11;
    }

    if (var11 == 0 || (var7 = var5 + (var11 + (var9 - var4)) / var11) > var8) {
      var7 = var8;
    }

    for (var1 = var11; var5 < var7; var4 += var1) {
      var10001 = var5++;
      var3[var10001] += ((var12 << 8) + (var2[var4 >> 8] - var12) * (var4 & 255)) * var6 >> 6;
    }

    var10.anInt380 = var4;
    return var5;
  }

  static int method600(byte[] var0, int[] var1, int var2, int var3, int var4, int var6, int var7, PcmStream_Sub2 var8) {
    var2 >>= 8;
    var7 >>= 8;
    var4 <<= 2;
    int var5;
    if ((var5 = var3 + var7 - var2) > var6) {
      var5 = var6;
    }

    int var10001;
    for (var5 -= 3; var3 < var5; var1[var10001] += var0[var2++] * var4) {
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
      var10001 = var3++;
    }

    for (var5 += 3; var3 < var5; var1[var10001] += var0[var2++] * var4) {
      var10001 = var3++;
    }

    var8.anInt380 = var2 << 8;
    return var3;
  }

  static int method566(byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var9, int var10, PcmStream_Sub2 var11, int var12, int var13) {
    int var8;
    if (var12 == 0 || (var8 = var5 + (var10 - var4 + var12 - 257) / var12) > var9) {
      var8 = var9;
    }

    var5 <<= 1;

    byte var14;
    int var10001;
    int var1;
    int var0;
    for (var8 <<= 1; var5 < var8; var4 += var12) {
      var1 = var4 >> 8;
      var14 = var2[var1];
      var0 = (var14 << 8) + (var4 & 255) * (var2[var1 + 1] - var14);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
    }

    if (var12 == 0 || (var8 = (var5 >> 1) + (var10 - var4 + var12 - 1) / var12) > var9) {
      var8 = var9;
    }

    var8 <<= 1;

    for (var1 = var13; var5 < var8; var4 += var12) {
      var14 = var2[var4 >> 8];
      var0 = (var14 << 8) + (var1 - var14) * (var4 & 255);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
    }

    var11.anInt380 = var4;
    return var5 >> 1;
  }

  static int method579(byte[] var0, int[] var1, int var2, int var3, int var4, int var6, int var7, PcmStream_Sub2 var8) {
    var2 >>= 8;
    var7 >>= 8;
    var4 <<= 2;
    int var5;
    if ((var5 = var3 + var2 - (var7 - 1)) > var6) {
      var5 = var6;
    }

    int var10001;
    for (var5 -= 3; var3 < var5; var1[var10001] += var0[var2--] * var4) {
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
      var10001 = var3++;
    }

    for (var5 += 3; var3 < var5; var1[var10001] += var0[var2--] * var4) {
      var10001 = var3++;
    }

    var8.anInt380 = var2 << 8;
    return var3;
  }

  static int method569(byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var9, int var10, PcmStream_Sub2 var11, int var12, int var13) {
    int var8;
    if (var12 == 0 || (var8 = var5 + (var10 + 256 - var4 + var12) / var12) > var9) {
      var8 = var9;
    }

    var5 <<= 1;

    int var10001;
    int var1;
    int var0;
    for (var8 <<= 1; var5 < var8; var4 += var12) {
      var1 = var4 >> 8;
      byte var14 = var2[var1 - 1];
      var0 = (var2[var1] - var14) * (var4 & 255) + (var14 << 8);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
    }

    if (var12 == 0 || (var8 = (var5 >> 1) + (var10 - var4 + var12) / var12) > var9) {
      var8 = var9;
    }

    var8 <<= 1;

    for (var1 = var13; var5 < var8; var4 += var12) {
      var0 = (var1 << 8) + (var4 & 255) * (var2[var4 >> 8] - var1);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
    }

    var11.anInt380 = var4;
    return var5 >> 1;
  }

  static int method595(byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var8, int var9, PcmStream_Sub2 var10) {
    var3 >>= 8;
    var9 >>= 8;
    var5 <<= 2;
    var6 <<= 2;
    int var7;
    if ((var7 = var4 + var9 - var3) > var8) {
      var7 = var8;
    }

    var4 <<= 1;
    var7 <<= 1;

    int var10001;
    byte var11;
    for (var7 -= 6; var4 < var7; var2[var10001] += var11 * var6) {
      var11 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
      var2[var10001] += var11 * var6;
      var11 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
      var2[var10001] += var11 * var6;
      var11 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
      var2[var10001] += var11 * var6;
      var11 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
    }

    for (var7 += 6; var4 < var7; var2[var10001] += var11 * var6) {
      var11 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
    }

    var10.anInt380 = var3 << 8;
    return var4 >> 1;
  }

  static int method594(byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var8, int var9, PcmStream_Sub2 var10) {
    var3 >>= 8;
    var9 >>= 8;
    var5 <<= 2;
    var6 <<= 2;
    int var7;
    if ((var7 = var3 + var4 - (var9 - 1)) > var8) {
      var7 = var8;
    }

    var4 <<= 1;
    var7 <<= 1;

    int var10001;
    byte var11;
    for (var7 -= 6; var4 < var7; var2[var10001] += var11 * var6) {
      var11 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
      var2[var10001] += var11 * var6;
      var11 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
      var2[var10001] += var11 * var6;
      var11 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
      var2[var10001] += var11 * var6;
      var11 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
    }

    for (var7 += 6; var4 < var7; var2[var10001] += var11 * var6) {
      var11 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var11 * var5;
      var10001 = var4++;
    }

    var10.anInt380 = var3 << 8;
    return var4 >> 1;
  }

  static int method564(byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var9, int var10, PcmStream_Sub2 var11, int var12, int var13) {
    var11.anInt112 -= var11.anInt366 * var5;
    var11.anInt375 -= var11.anInt696 * var5;
    int var8;
    if (var12 == 0 || (var8 = var5 + (var10 - var4 + var12 - 257) / var12) > var9) {
      var8 = var9;
    }

    byte var14;
    int var10001;
    int var1;
    while (var5 < var8) {
      var1 = var4 >> 8;
      var14 = var2[var1];
      var10001 = var5++;
      var3[var10001] += ((var14 << 8) + (var2[var1 + 1] - var14) * (var4 & 255)) * var6 >> 6;
      var6 += var7;
      var4 += var12;
    }

    if (var12 == 0 || (var8 = var5 + (var10 - var4 + var12 - 1) / var12) > var9) {
      var8 = var9;
    }

    for (var1 = var13; var5 < var8; var4 += var12) {
      var14 = var2[var4 >> 8];
      var10001 = var5++;
      var3[var10001] += ((var14 << 8) + (var1 - var14) * (var4 & 255)) * var6 >> 6;
      var6 += var7;
    }

    var11.anInt112 += var11.anInt366 * var5;
    var11.anInt375 += var11.anInt696 * var5;
    var11.anInt574 = var6;
    var11.anInt380 = var4;
    return var5;
  }

  static int method602(byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var9, int var10, PcmStream_Sub2 var11, int var12, int var13) {
    var11.anInt112 -= var11.anInt366 * var5;
    var11.anInt375 -= var11.anInt696 * var5;
    int var8;
    if (var12 == 0 || (var8 = var5 + (var10 + 256 - var4 + var12) / var12) > var9) {
      var8 = var9;
    }

    int var10001;
    int var1;
    while (var5 < var8) {
      var1 = var4 >> 8;
      byte var14 = var2[var1 - 1];
      var10001 = var5++;
      var3[var10001] += ((var14 << 8) + (var2[var1] - var14) * (var4 & 255)) * var6 >> 6;
      var6 += var7;
      var4 += var12;
    }

    if (var12 == 0 || (var8 = var5 + (var10 - var4 + var12) / var12) > var9) {
      var8 = var9;
    }

    for (var1 = var12; var5 < var8; var4 += var1) {
      var10001 = var5++;
      var3[var10001] += ((var13 << 8) + (var2[var4 >> 8] - var13) * (var4 & 255)) * var6 >> 6;
      var6 += var7;
    }

    var11.anInt112 += var11.anInt366 * var5;
    var11.anInt375 += var11.anInt696 * var5;
    var11.anInt574 = var6;
    var11.anInt380 = var4;
    return var5;
  }

  static int method572(byte[] var0, int[] var1, int var2, int var3, int var4, int var5, int var7, int var8, PcmStream_Sub2 var9) {
    var2 >>= 8;
    var8 >>= 8;
    var4 <<= 2;
    var5 <<= 2;
    int var6;
    if ((var6 = var3 + var8 - var2) > var7) {
      var6 = var7;
    }

    var9.anInt112 += var9.anInt366 * (var6 - var3);
    var9.anInt375 += var9.anInt696 * (var6 - var3);

    int var10001;
    for (var6 -= 3; var3 < var6; var4 += var5) {
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
      var4 += var5;
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
      var4 += var5;
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
      var4 += var5;
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
    }

    for (var6 += 3; var3 < var6; var4 += var5) {
      var10001 = var3++;
      var1[var10001] += var0[var2++] * var4;
    }

    var9.anInt574 = var4 >> 2;
    var9.anInt380 = var2 << 8;
    return var3;
  }

  static int method571(byte[] var0, int[] var1, int var2, int var3, int var4, int var5, int var7, int var8, PcmStream_Sub2 var9) {
    var2 >>= 8;
    var8 >>= 8;
    var4 <<= 2;
    var5 <<= 2;
    int var6;
    if ((var6 = var3 + var2 - (var8 - 1)) > var7) {
      var6 = var7;
    }

    var9.anInt112 += var9.anInt366 * (var6 - var3);
    var9.anInt375 += var9.anInt696 * (var6 - var3);

    int var10001;
    for (var6 -= 3; var3 < var6; var4 += var5) {
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
      var4 += var5;
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
      var4 += var5;
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
      var4 += var5;
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
    }

    for (var6 += 3; var3 < var6; var4 += var5) {
      var10001 = var3++;
      var1[var10001] += var0[var2--] * var4;
    }

    var9.anInt574 = var4 >> 2;
    var9.anInt380 = var2 << 8;
    return var3;
  }

  static int method568(byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var11, int var12, PcmStream_Sub2 var13, int var14, int var15) {
    var13.anInt574 -= var5 * var13.anInt386;
    int var10;
    if (var14 == 0 || (var10 = var5 + (var12 - var4 + var14 - 257) / var14) > var11) {
      var10 = var11;
    }

    var5 <<= 1;

    byte var16;
    int var10001;
    int var1;
    int var0;
    for (var10 <<= 1; var5 < var10; var4 += var14) {
      var1 = var4 >> 8;
      var16 = var2[var1];
      var0 = (var16 << 8) + (var4 & 255) * (var2[var1 + 1] - var16);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var6 += var8;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
      var7 += var9;
    }

    if (var14 == 0 || (var10 = (var5 >> 1) + (var12 - var4 + var14 - 1) / var14) > var11) {
      var10 = var11;
    }

    var10 <<= 1;

    for (var1 = var15; var5 < var10; var4 += var14) {
      var16 = var2[var4 >> 8];
      var0 = (var16 << 8) + (var1 - var16) * (var4 & 255);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var6 += var8;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
      var7 += var9;
    }

    var5 >>= 1;
    var13.anInt574 += var13.anInt386 * var5;
    var13.anInt112 = var6;
    var13.anInt375 = var7;
    var13.anInt380 = var4;
    return var5;
  }

  static int method570(byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var11, int var12, PcmStream_Sub2 var13, int var14, int var15) {
    var13.anInt574 -= var5 * var13.anInt386;
    int var10;
    if (var14 == 0 || (var10 = var5 + (var12 + 256 - var4 + var14) / var14) > var11) {
      var10 = var11;
    }

    var5 <<= 1;

    int var10001;
    int var1;
    int var0;
    for (var10 <<= 1; var5 < var10; var4 += var14) {
      var1 = var4 >> 8;
      byte var16 = var2[var1 - 1];
      var0 = (var2[var1] - var16) * (var4 & 255) + (var16 << 8);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var6 += var8;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
      var7 += var9;
    }

    if (var14 == 0 || (var10 = (var5 >> 1) + (var12 - var4 + var14) / var14) > var11) {
      var10 = var11;
    }

    var10 <<= 1;

    for (var1 = var15; var5 < var10; var4 += var14) {
      var0 = (var1 << 8) + (var4 & 255) * (var2[var4 >> 8] - var1);
      var10001 = var5++;
      var3[var10001] += var0 * var6 >> 6;
      var6 += var8;
      var10001 = var5++;
      var3[var10001] += var0 * var7 >> 6;
      var7 += var9;
    }

    var5 >>= 1;
    var13.anInt574 += var13.anInt386 * var5;
    var13.anInt112 = var6;
    var13.anInt375 = var7;
    var13.anInt380 = var4;
    return var5;
  }

  static int method567(byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var10, int var11, PcmStream_Sub2 var12) {
    var3 >>= 8;
    var11 >>= 8;
    var5 <<= 2;
    var6 <<= 2;
    var7 <<= 2;
    var8 <<= 2;
    int var9;
    if ((var9 = var11 + var4 - var3) > var10) {
      var9 = var10;
    }

    var12.anInt574 += var12.anInt386 * (var9 - var4);
    var4 <<= 1;
    var9 <<= 1;

    byte var13;
    int var10001;
    for (var9 -= 6; var4 < var9; var6 += var8) {
      var13 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
      var6 += var8;
      var13 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
      var6 += var8;
      var13 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
      var6 += var8;
      var13 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
    }

    for (var9 += 6; var4 < var9; var6 += var8) {
      var13 = var1[var3++];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
    }

    var12.anInt112 = var5 >> 2;
    var12.anInt375 = var6 >> 2;
    var12.anInt380 = var3 << 8;
    return var4 >> 1;
  }

  static int method574(byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var10, int var11, PcmStream_Sub2 var12) {
    var3 >>= 8;
    var11 >>= 8;
    var5 <<= 2;
    var6 <<= 2;
    var7 <<= 2;
    var8 <<= 2;
    int var9;
    if ((var9 = var3 + var4 - (var11 - 1)) > var10) {
      var9 = var10;
    }

    var12.anInt574 += var12.anInt386 * (var9 - var4);
    var4 <<= 1;
    var9 <<= 1;

    byte var13;
    int var10001;
    for (var9 -= 6; var4 < var9; var6 += var8) {
      var13 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
      var6 += var8;
      var13 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
      var6 += var8;
      var13 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
      var6 += var8;
      var13 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
    }

    for (var9 += 6; var4 < var9; var6 += var8) {
      var13 = var1[var3--];
      var10001 = var4++;
      var2[var10001] += var13 * var5;
      var5 += var7;
      var10001 = var4++;
      var2[var10001] += var13 * var6;
    }

    var12.anInt112 = var5 >> 2;
    var12.anInt375 = var6 >> 2;
    var12.anInt380 = var3 << 8;
    return var4 >> 1;
  }

  public static PcmStream_Sub2 method597(RawAudioOverride var0, int var1, int var2, int var3) {
    return var0.samples != null && var0.samples.length != 0 ? new PcmStream_Sub2(var0, var1, var2, var3) : null;
  }

  public static PcmStream_Sub2 method598(RawAudioOverride var0, int var1, int var2) {
    return var0.samples != null && var0.samples.length != 0 ? new PcmStream_Sub2(var0, (int) ((long) var0.sampleRate * 256L * (long) var1 / (long) (URLRequest.audioSampleRate * 100L)), var2 << 6) : null;
  }

  public synchronized int method599() {
    return anInt377 < 0 ? -1 : anInt377;
  }

  synchronized void method591() {
    method575(0, method599());
  }

  synchronized void method575(int var1, int var2) {
    anInt378 = var1;
    anInt377 = var2;
    anInt367 = 0;
    method592();
  }

  public synchronized void method576(int var1, int var2, int var3) {
    if (var1 == 0) {
      method575(var2, var3);
    } else {
      int var4 = method601(var2, var3);
      int var5 = method590(var2, var3);
      if (var4 == anInt112 && var5 == anInt375) {
        anInt367 = 0;
      } else {
        int var6 = var2 - anInt574;
        if (anInt574 - var2 > var6) {
          var6 = anInt574 - var2;
        }

        if (var4 - anInt112 > var6) {
          var6 = var4 - anInt112;
        }

        if (anInt112 - var4 > var6) {
          var6 = anInt112 - var4;
        }

        if (var5 - anInt375 > var6) {
          var6 = var5 - anInt375;
        }

        if (anInt375 - var5 > var6) {
          var6 = anInt375 - var5;
        }

        if (var1 > var6) {
          var1 = var6;
        }

        anInt367 = var1;
        anInt378 = var2;
        anInt377 = var3;
        anInt386 = (var2 - anInt574) / var1;
        anInt366 = (var4 - anInt112) / var1;
        anInt696 = (var5 - anInt375) / var1;
      }
    }
  }

  public synchronized void method311(int[] var1, int var2, int var3) {
    if (anInt378 == 0 && anInt367 == 0) {
      method303(var3);
    } else {
      RawAudioOverride var4 = (RawAudioOverride) aVorbisNode_667;
      int var5 = anInt379 << 8;
      int var6 = anInt564 << 8;
      int var7 = var4.samples.length << 8;
      int var8 = var6 - var5;
      if (var8 <= 0) {
        anInt372 = 0;
      }

      int var9 = var2;
      var3 += var2;
      if (anInt380 < 0) {
        if (anInt568 <= 0) {
          method580();
          unlink();
          return;
        }

        anInt380 = 0;
      }

      if (anInt380 >= var7) {
        if (anInt568 >= 0) {
          method580();
          unlink();
          return;
        }

        anInt380 = var7 - 1;
      }

      if (anInt372 < 0) {
        if (aBoolean763) {
          if (anInt568 < 0) {
            var9 = method581(var1, var2, var5, var3, var4.samples[anInt379]);
            if (anInt380 >= var5) {
              return;
            }

            anInt380 = var5 + var5 - 1 - anInt380;
            anInt568 = -anInt568;
          }

          while (true) {
            var9 = method596(var1, var9, var6, var3, var4.samples[anInt564 - 1]);
            if (anInt380 < var6) {
              return;
            }

            anInt380 = var6 + var6 - 1 - anInt380;
            anInt568 = -anInt568;
            var9 = method581(var1, var9, var5, var3, var4.samples[anInt379]);
            if (anInt380 >= var5) {
              return;
            }

            anInt380 = var5 + var5 - 1 - anInt380;
            anInt568 = -anInt568;
          }
        }
        if (anInt568 < 0) {
          while (true) {
            var9 = method581(var1, var9, var5, var3, var4.samples[anInt564 - 1]);
            if (anInt380 >= var5) {
              return;
            }

            anInt380 = var6 - 1 - (var6 - 1 - anInt380) % var8;
          }
        }
        while (true) {
          var9 = method596(var1, var9, var6, var3, var4.samples[anInt379]);
          if (anInt380 < var6) {
            return;
          }

          anInt380 = var5 + (anInt380 - var5) % var8;
        }
      }
      if (anInt372 > 0) {
        if (aBoolean763) {
          label129:
          {
            if (anInt568 < 0) {
              var9 = method581(var1, var2, var5, var3, var4.samples[anInt379]);
              if (anInt380 >= var5) {
                return;
              }

              anInt380 = var5 + var5 - 1 - anInt380;
              anInt568 = -anInt568;
              if (--anInt372 == 0) {
                break label129;
              }
            }

            do {
              var9 = method596(var1, var9, var6, var3, var4.samples[anInt564 - 1]);
              if (anInt380 < var6) {
                return;
              }

              anInt380 = var6 + var6 - 1 - anInt380;
              anInt568 = -anInt568;
              if (--anInt372 == 0) {
                break;
              }

              var9 = method581(var1, var9, var5, var3, var4.samples[anInt379]);
              if (anInt380 >= var5) {
                return;
              }

              anInt380 = var5 + var5 - 1 - anInt380;
              anInt568 = -anInt568;
            } while (--anInt372 != 0);
          }
        } else {
          int var10;
          if (anInt568 < 0) {
            while (true) {
              var9 = method581(var1, var9, var5, var3, var4.samples[anInt564 - 1]);
              if (anInt380 >= var5) {
                return;
              }

              var10 = (var6 - 1 - anInt380) / var8;
              if (var10 >= anInt372) {
                anInt380 += var8 * anInt372;
                anInt372 = 0;
                break;
              }

              anInt380 += var8 * var10;
              anInt372 -= var10;
            }
          } else {
            while (true) {
              var9 = method596(var1, var9, var6, var3, var4.samples[anInt379]);
              if (anInt380 < var6) {
                return;
              }

              var10 = (anInt380 - var5) / var8;
              if (var10 >= anInt372) {
                anInt380 -= var8 * anInt372;
                anInt372 = 0;
                break;
              }

              anInt380 -= var8 * var10;
              anInt372 -= var10;
            }
          }
        }
      }

      if (anInt568 < 0) {
        method581(var1, var9, 0, var3, 0);
        if (anInt380 < 0) {
          anInt380 = -1;
          method580();
          unlink();
        }
      } else {
        method596(var1, var9, var7, var3, 0);
        if (anInt380 >= var7) {
          anInt380 = var7;
          method580();
          unlink();
        }
      }

    }
  }

  public synchronized void method303(int var1) {
    if (anInt367 > 0) {
      if (var1 >= anInt367) {
        if (anInt378 == Integer.MIN_VALUE) {
          anInt378 = 0;
          anInt375 = 0;
          anInt112 = 0;
          anInt574 = 0;
          unlink();
          var1 = anInt367;
        }

        anInt367 = 0;
        method592();
      } else {
        anInt574 += anInt386 * var1;
        anInt112 += anInt366 * var1;
        anInt375 += anInt696 * var1;
        anInt367 -= var1;
      }
    }

    RawAudioOverride var2 = (RawAudioOverride) aVorbisNode_667;
    int var3 = anInt379 << 8;
    int var4 = anInt564 << 8;
    int var5 = var2.samples.length << 8;
    int var6 = var4 - var3;
    if (var6 <= 0) {
      anInt372 = 0;
    }

    if (anInt380 < 0) {
      if (anInt568 <= 0) {
        method580();
        unlink();
        return;
      }

      anInt380 = 0;
    }

    if (anInt380 >= var5) {
      if (anInt568 >= 0) {
        method580();
        unlink();
        return;
      }

      anInt380 = var5 - 1;
    }

    anInt380 += anInt568 * var1;
    if (anInt372 < 0) {
      if (!aBoolean763) {
        if (anInt568 < 0) {
          if (anInt380 >= var3) {
            return;
          }

          anInt380 = var4 - 1 - (var4 - 1 - anInt380) % var6;
        } else {
          if (anInt380 < var4) {
            return;
          }

          anInt380 = var3 + (anInt380 - var3) % var6;
        }

      } else {
        if (anInt568 < 0) {
          if (anInt380 >= var3) {
            return;
          }

          anInt380 = var3 + var3 - 1 - anInt380;
          anInt568 = -anInt568;
        }

        while (anInt380 >= var4) {
          anInt380 = var4 + var4 - 1 - anInt380;
          anInt568 = -anInt568;
          if (anInt380 >= var3) {
            return;
          }

          anInt380 = var3 + var3 - 1 - anInt380;
          anInt568 = -anInt568;
        }

      }
    } else {
      if (anInt372 > 0) {
        if (aBoolean763) {
          label126:
          {
            if (anInt568 < 0) {
              if (anInt380 >= var3) {
                return;
              }

              anInt380 = var3 + var3 - 1 - anInt380;
              anInt568 = -anInt568;
              if (--anInt372 == 0) {
                break label126;
              }
            }

            do {
              if (anInt380 < var4) {
                return;
              }

              anInt380 = var4 + var4 - 1 - anInt380;
              anInt568 = -anInt568;
              if (--anInt372 == 0) {
                break;
              }

              if (anInt380 >= var3) {
                return;
              }

              anInt380 = var3 + var3 - 1 - anInt380;
              anInt568 = -anInt568;
            } while (--anInt372 != 0);
          }
        } else {
          int var7;
          if (anInt568 < 0) {
            if (anInt380 >= var3) {
              return;
            }

            var7 = (var4 - 1 - anInt380) / var6;
            if (var7 < anInt372) {
              anInt380 += var6 * var7;
              anInt372 -= var7;
              return;
            }

            anInt380 += var6 * anInt372;
          } else {
            if (anInt380 < var4) {
              return;
            }

            var7 = (anInt380 - var3) / var6;
            if (var7 < anInt372) {
              anInt380 -= var6 * var7;
              anInt372 -= var7;
              return;
            }

            anInt380 -= var6 * anInt372;
          }
          anInt372 = 0;
        }
      }

      if (anInt568 < 0) {
        if (anInt380 < 0) {
          anInt380 = -1;
          method580();
          unlink();
        }
      } else if (anInt380 >= var5) {
        anInt380 = var5;
        method580();
        unlink();
      }

    }
  }

  void method592() {
    anInt574 = anInt378;
    anInt112 = method601(anInt378, anInt377);
    anInt375 = method590(anInt378, anInt377);
  }

  public synchronized void method582(int var1) {
    int var2 = ((RawAudioOverride) aVorbisNode_667).samples.length << 8;
    if (var1 < -1) {
      var1 = -1;
    }

    if (var1 > var2) {
      var1 = var2;
    }

    anInt380 = var1;
  }

  public PcmStream method308() {
    return null;
  }

  public boolean method586() {
    return anInt380 < 0 || anInt380 >= ((RawAudioOverride) aVorbisNode_667).samples.length << 8;
  }

  public synchronized void method588(int var1) {
    if (anInt568 < 0) {
      anInt568 = -var1;
    } else {
      anInt568 = var1;
    }

  }

  public synchronized void method589(int var1) {
    if (var1 == 0) {
      method591();
      unlink();
    } else if (anInt112 == 0 && anInt375 == 0) {
      anInt367 = 0;
      anInt378 = 0;
      anInt574 = 0;
      unlink();
    } else {
      int var2 = -anInt574;
      if (anInt574 > var2) {
        var2 = anInt574;
      }

      if (-anInt112 > var2) {
        var2 = -anInt112;
      }

      if (anInt112 > var2) {
        var2 = anInt112;
      }

      if (-anInt375 > var2) {
        var2 = -anInt375;
      }

      if (anInt375 > var2) {
        var2 = anInt375;
      }

      if (var1 > var2) {
        var1 = var2;
      }

      anInt367 = var1;
      anInt378 = Integer.MIN_VALUE;
      anInt386 = -anInt574 / var1;
      anInt366 = -anInt112 / var1;
      anInt696 = -anInt375 / var1;
    }
  }

  void method580() {
    if (anInt367 != 0) {
      if (anInt378 == Integer.MIN_VALUE) {
        anInt378 = 0;
      }

      anInt367 = 0;
      method592();
    }

  }

  public boolean method577() {
    return anInt367 != 0;
  }

  public synchronized void method593() {
    anInt568 = (anInt568 ^ anInt568 >> 31) + (anInt568 >>> 31);
    anInt568 = -anInt568;
  }

  public PcmStream method307() {
    return null;
  }

  public int method483() {
    int var1 = anInt574 * 3 >> 6;
    var1 = (var1 ^ var1 >> 31) + (var1 >>> 31);
    if (anInt372 == 0) {
      var1 -= var1 * anInt380 / (((RawAudioOverride) aVorbisNode_667).samples.length << 8);
    } else if (anInt372 >= 0) {
      var1 -= var1 * anInt379 / ((RawAudioOverride) aVorbisNode_667).samples.length;
    }

    return Math.min(var1, 255);
  }

  int method581(int[] var1, int var2, int var3, int var4, int var5) {
    while (true) {
      if (anInt367 > 0) {
        int var6 = var2 + anInt367;
        if (var6 > var4) {
          var6 = var4;
        }

        anInt367 += var2;
        if (anInt568 == -256 && (anInt380 & 255) == 0) {
          if (AudioSystem.useTwoChannels) {
            var2 = method574(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, anInt366, anInt696, var6, var3, this);
          } else {
            var2 = method571(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, anInt386, var6, var3, this);
          }
        } else if (AudioSystem.useTwoChannels) {
          var2 = method570(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, anInt366, anInt696, var6, var3, this, anInt568, var5);
        } else {
          var2 = method602(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, anInt386, var6, var3, this, anInt568, var5);
        }

        anInt367 -= var2;
        if (anInt367 != 0) {
          return var2;
        }

        if (!method584()) {
          continue;
        }

        return var4;
      }

      if (anInt568 == -256 && (anInt380 & 255) == 0) {
        if (AudioSystem.useTwoChannels) {
          return method594(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, var4, var3, this);
        }

        return method579(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, var4, var3, this);
      }

      if (AudioSystem.useTwoChannels) {
        return method569(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, var4, var3, this, anInt568, var5);
      }

      return method565(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, var4, var3, this, anInt568, var5);
    }
  }

  public synchronized int method583() {
    return anInt568 < 0 ? -anInt568 : anInt568;
  }

  boolean method584() {
    int var1 = anInt378;
    int var2;
    int var3;
    if (var1 == Integer.MIN_VALUE) {
      var2 = 0;
      var3 = 0;
      var1 = 0;
    } else {
      var3 = method601(var1, anInt377);
      var2 = method590(var1, anInt377);
    }

    if (var1 == anInt574 && var3 == anInt112 && var2 == anInt375) {
      if (anInt378 == Integer.MIN_VALUE) {
        anInt378 = 0;
        anInt375 = 0;
        anInt112 = 0;
        anInt574 = 0;
        unlink();
        return true;
      }
      method592();
      return false;
    }
    if (anInt574 < var1) {
      anInt386 = 1;
      anInt367 = var1 - anInt574;
    } else if (anInt574 > var1) {
      anInt386 = -1;
      anInt367 = anInt574 - var1;
    } else {
      anInt386 = 0;
    }

    if (anInt112 < var3) {
      anInt366 = 1;
      if (anInt367 == 0 || anInt367 > var3 - anInt112) {
        anInt367 = var3 - anInt112;
      }
    } else if (anInt112 > var3) {
      anInt366 = -1;
      if (anInt367 == 0 || anInt367 > anInt112 - var3) {
        anInt367 = anInt112 - var3;
      }
    } else {
      anInt366 = 0;
    }

    if (anInt375 < var2) {
      anInt696 = 1;
      if (anInt367 == 0 || anInt367 > var2 - anInt375) {
        anInt367 = var2 - anInt375;
      }
    } else if (anInt375 > var2) {
      anInt696 = -1;
      if (anInt367 == 0 || anInt367 > anInt375 - var2) {
        anInt367 = anInt375 - var2;
      }
    } else {
      anInt696 = 0;
    }

    return false;
  }

  int method596(int[] var1, int var2, int var3, int var4, int var5) {
    while (true) {
      if (anInt367 > 0) {
        int var6 = var2 + anInt367;
        if (var6 > var4) {
          var6 = var4;
        }

        anInt367 += var2;
        if (anInt568 == 256 && (anInt380 & 255) == 0) {
          if (AudioSystem.useTwoChannels) {
            var2 = method567(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, anInt366, anInt696, var6, var3, this);
          } else {
            var2 = method572(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, anInt386, var6, var3, this);
          }
        } else if (AudioSystem.useTwoChannels) {
          var2 = method568(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, anInt366, anInt696, var6, var3, this, anInt568, var5);
        } else {
          var2 = method564(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, anInt386, var6, var3, this, anInt568, var5);
        }

        anInt367 -= var2;
        if (anInt367 != 0) {
          return var2;
        }

        if (!method584()) {
          continue;
        }

        return var4;
      }

      if (anInt568 == 256 && (anInt380 & 255) == 0) {
        if (AudioSystem.useTwoChannels) {
          return method595(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, var4, var3, this);
        }

        return method600(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, var4, var3, this);
      }

      if (AudioSystem.useTwoChannels) {
        return method566(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt112, anInt375, var4, var3, this, anInt568, var5);
      }

      return method573(((RawAudioOverride) aVorbisNode_667).samples, var1, anInt380, var2, anInt574, var4, var3, this, anInt568, var5);
    }
  }

  public synchronized int method578() {
    return anInt378 == Integer.MIN_VALUE ? 0 : anInt378;
  }

  public synchronized void method585(int var1) {
    anInt372 = var1;
  }

  public int method305() {
    return anInt378 == 0 && anInt367 == 0 ? 0 : 1;
  }

  public synchronized void method587(int var1, int var2) {
    method576(var1, var2, method599());
  }

  public synchronized void method302(int var1) {
    method575(var1 << 6, method599());
  }
}
