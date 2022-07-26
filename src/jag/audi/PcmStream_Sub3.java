package jag.audi;

import jag.URLRequest;
import jag.audi.vorbis.RawAudioOverride;
import jag.commons.collection.NodeTable;
import jag.js5.ReferenceTable;

public class PcmStream_Sub3 extends PcmStream {
    final int[] anIntArray781;
    final int[] anIntArray690;
    final NodeTable<AudioOverride> overrides;
    final int[] anIntArray1107;
    final int[] anIntArray692;
    final int[] anIntArray687;
    final Node_Sub19[][] aNode_Sub19ArrayArray1104;
    final int[] anIntArray1102;
    final MidiFileDecoder decoder;
    final PcmStream_Sub4 aClass5_Sub6_Sub4_1110;
    final int[] anIntArray1108;
    final int[] anIntArray785;
    final int[] anIntArray691;
    final int[] anIntArray1103;
    final int[] anIntArray747;
    final int[] anIntArray782;
    final int anInt378;
    final Node_Sub19[][] aNode_Sub19ArrayArray1099;
    final int[] anIntArray1100;
    final int[] anIntArray1101;
    final int[] anIntArray787;
    int anInt694;
    int anInt568;
    int anInt1105;
    long aLong1098;
    boolean aBoolean1109;
    long aLong1106;

    public PcmStream_Sub3() {
        anInt568 = 256;
        anInt378 = -727379968;
        anIntArray1108 = new int[16];
        anIntArray1107 = new int[16];
        anIntArray747 = new int[16];
        anIntArray687 = new int[16];
        anIntArray692 = new int[16];
        anIntArray691 = new int[16];
        anIntArray690 = new int[16];
        anIntArray1101 = new int[16];
        anIntArray1100 = new int[16];
        anIntArray1102 = new int[16];
        anIntArray787 = new int[16];
        anIntArray782 = new int[16];
        anIntArray1103 = new int[16];
        anIntArray781 = new int[16];
        anIntArray785 = new int[16];
        aNode_Sub19ArrayArray1104 = new Node_Sub19[16][128];
        aNode_Sub19ArrayArray1099 = new Node_Sub19[16][128];
        decoder = new MidiFileDecoder();
        aClass5_Sub6_Sub4_1110 = new PcmStream_Sub4(this);
        overrides = new NodeTable<>(128);
        method757();
    }

    void method759(int var1, int var2) {
        anIntArray687[var1] = var2;
        anIntArray691[var1] = var2 & -128;
        method777(var1, var2);
    }

    void method761(int var1, int var2) {
        Node_Sub19 var4 = aNode_Sub19ArrayArray1104[var1][var2];
        if (var4 != null) {
            aNode_Sub19ArrayArray1104[var1][var2] = null;
            if ((anIntArray1102[var1] & 2) != 0) {
                for (Node_Sub19 var5 = aClass5_Sub6_Sub4_1110.aNodeDeque373.head(); var5 != null; var5 = aClass5_Sub6_Sub4_1110.aNodeDeque373.next()) {
                    if (var4.anInt380 == var5.anInt380 && var5.anInt696 < 0 && var4 != var5) {
                        var4.anInt696 = 0;
                        break;
                    }
                }
            } else {
                var4.anInt696 = 0;
            }

        }
    }

    void method588(int var1) {
        for (Node_Sub19 var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.head(); var2 != null; var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.next()) {
            if (var1 < 0 || var2.anInt380 == var1) {
                if (var2.aClass5_Sub6_Sub2_1195 != null) {
                    var2.aClass5_Sub6_Sub2_1195.method589(URLRequest.audioSampleRate / 100);
                    if (var2.aClass5_Sub6_Sub2_1195.method577()) {
                        aClass5_Sub6_Sub4_1110.aClass5_Sub6_Sub1_1153.method312(var2.aClass5_Sub6_Sub2_1195);
                    }

                    var2.method499();
                }

                if (var2.anInt696 < 0) {
                    aNode_Sub19ArrayArray1104[var2.anInt380][var2.anInt112] = null;
                }

                var2.unlink();
            }
        }

    }

    public synchronized void method756() {
        decoder.method785();
        method757();
    }

    void method763(int var1) {
        if (var1 >= 0) {
            anIntArray1108[var1] = 12800;
            anIntArray1107[var1] = 8192;
            anIntArray747[var1] = 16383;
            anIntArray690[var1] = 8192;
            anIntArray1101[var1] = 0;
            anIntArray1100[var1] = 8192;
            method774(var1);
            method762(var1);
            anIntArray1102[var1] = 0;
            anIntArray787[var1] = 32767;
            anIntArray782[var1] = 256;
            anIntArray1103[var1] = 0;
            method778(var1, 8192);
        } else {
            for (var1 = 0; var1 < 16; ++var1) {
                method763(var1);
            }

        }
    }

    void method767() {
    }

    void method764(int var1, int var2, int var3) {
        method761(var1, var2);
        if ((anIntArray1102[var1] & 2) != 0) {
            for (Node_Sub19 var4 = aClass5_Sub6_Sub4_1110.aNodeDeque373.tail(); var4 != null; var4 = aClass5_Sub6_Sub4_1110.aNodeDeque373.previous()) {
                if (var4.anInt380 == var1 && var4.anInt696 < 0) {
                    aNode_Sub19ArrayArray1104[var1][var4.anInt112] = null;
                    aNode_Sub19ArrayArray1104[var1][var2] = var4;
                    int var5 = (var4.anInt368 * var4.anInt564 >> 12) + var4.anInt379;
                    var4.anInt379 += var2 - var4.anInt112 << 8;
                    var4.anInt564 = var5 - var4.anInt379;
                    var4.anInt368 = 4096;
                    var4.anInt112 = var2;
                    return;
                }
            }
        }

        AudioOverride var9 = overrides.lookup(anIntArray692[var1]);
        if (var9 != null) {
            RawAudioOverride var6 = var9.aClass5_Sub10_Sub1Array1143[var2];
            if (var6 != null) {
                Node_Sub19 var7 = new Node_Sub19();
                var7.anInt380 = var1;
                var7.aAudioOverride_1192 = var9;
                var7.aClass5_Sub10_Sub1_1194 = var6;
                var7.aAudioOverrideEffect_1193 = var9.aAudioOverrideEffectArray1139[var2];
                var7.anInt574 = var9.aByteArray572[var2];
                var7.anInt112 = var2;
                var7.anInt375 = var3 * var3 * var9.aByteArray1141[var2] * var9.anInt380 + 1024 >> 11;
                var7.anInt372 = var9.aByteArray1140[var2] & 255;
                var7.anInt379 = (var2 << 8) - (var9.aShortArray1142[var2] & 32767);
                var7.anInt367 = 0;
                var7.anInt386 = 0;
                var7.anInt366 = 0;
                var7.anInt696 = -1;
                var7.anInt702 = 0;
                if (anIntArray1103[var1] == 0) {
                    var7.aClass5_Sub6_Sub2_1195 = PcmStream_Sub2.method597(var6, method773(var7), method760(var7), method772(var7));
                } else {
                    var7.aClass5_Sub6_Sub2_1195 = PcmStream_Sub2.method597(var6, method773(var7), 0, method772(var7));
                    method771(var7, var9.aShortArray1142[var2] < 0);
                }

                if (var9.aShortArray1142[var2] < 0) {
                    var7.aClass5_Sub6_Sub2_1195.method585(-1);
                }

                if (var7.anInt574 >= 0) {
                    Node_Sub19 var8 = aNode_Sub19ArrayArray1099[var1][var7.anInt574];
                    if (var8 != null && var8.anInt696 < 0) {
                        aNode_Sub19ArrayArray1104[var1][var8.anInt112] = null;
                        var8.anInt696 = 0;
                    }

                    aNode_Sub19ArrayArray1099[var1][var7.anInt574] = var7;
                }

                aClass5_Sub6_Sub4_1110.aNodeDeque373.add(var7);
                aNode_Sub19ArrayArray1104[var1][var2] = var7;
            }
        }
    }

    void method757() {
        method588(-1);
        method763(-1);

        int var1;
        for (var1 = 0; var1 < 16; ++var1) {
            anIntArray692[var1] = anIntArray687[var1];
        }

        for (var1 = 0; var1 < 16; ++var1) {
            anIntArray691[var1] = anIntArray687[var1] & -128;
        }

    }

    void method777(int var1, int var2) {
        if (var2 != anIntArray692[var1]) {
            anIntArray692[var1] = var2;

            for (int var3 = 0; var3 < 128; ++var3) {
                aNode_Sub19ArrayArray1099[var1][var3] = null;
            }
        }

    }

    public synchronized void method311(int[] var1, int var2, int var3) {
        if (decoder.method791()) {
            int var4 = decoder.anInt1121 * anInt378 * 1000000 / URLRequest.audioSampleRate;

            do {
                long var5 = aLong1106 + (long) var4 * (long) var3;
                if (this.aLong1098 - var5 >= 0L) {
                    this.aLong1106 = var5;
                    break;
                }

                int var7 = (int) ((aLong1098 - aLong1106 + (long) var4 - 1L) / (long) var4);
                aLong1106 += (long) var7 * (long) var4;
                aClass5_Sub6_Sub4_1110.method311(var1, var2, var7);
                var2 += var7;
                var3 -= var7;
                method755();
            } while (decoder.method791());
        }

        aClass5_Sub6_Sub4_1110.method311(var1, var2, var3);
    }

    public synchronized void method303(int var1) {
        if (decoder.method791()) {
            int var2 = decoder.anInt1121 * anInt378 * 1000000 / URLRequest.audioSampleRate;

            do {
                long var3 = this.aLong1106 + (long) var2 * (long) var1;
                if (aLong1098 - var3 >= 0L) {
                    aLong1106 = var3;
                    break;
                }

                int var5 = (int) ((aLong1098 - aLong1106 + (long) var2 - 1L) / (long) var2);
                aLong1106 += (long) var5 * (long) var2;
                aClass5_Sub6_Sub4_1110.method303(var5);
                var1 -= var5;
                method755();
            } while (decoder.method791());
        }

        aClass5_Sub6_Sub4_1110.method303(var1);
    }

    void method758() {

    }

    void method769(int var1, int var2) {
        anIntArray690[var1] = var2;
    }

    int method773(Node_Sub19 var1) {
        int var2 = (var1.anInt564 * var1.anInt368 >> 12) + var1.anInt379;
        var2 += (anIntArray690[var1.anInt380] - 8192) * anIntArray782[var1.anInt380] >> 12;
        AudioOverrideEffect var3 = var1.aAudioOverrideEffect_1193;
        int var4;
        if (var3.anInt1133 > 0 && (var3.anInt1126 > 0 || anIntArray1101[var1.anInt380] > 0)) {
            var4 = var3.anInt1126 << 2;
            int var5 = var3.anInt1131 << 1;
            if (var1.anInt788 < var5) {
                var4 = var4 * var1.anInt788 / var5;
            }

            var4 += anIntArray1101[var1.anInt380] >> 7;
            double var6 = Math.sin((double) (var1.anInt666 & 511) * 0.01227184630308513D);
            var2 += (int) ((double) var4 * var6);
        }

        var4 = (int) ((double) (var1.aClass5_Sub10_Sub1_1194.sampleRate * 256) * Math.pow(2.0D, 3.255208333333333E-4D * (double) var2) / (double) URLRequest.audioSampleRate + 0.5D);
        return Math.max(var4, 1);
    }

    public synchronized PcmStream method308() {
        return null;
    }

    public synchronized boolean method776(AudioTrack var1, ReferenceTable var2, Class97 var3, int var4) {
        var1.method254();
        boolean var5 = true;
        int[] var6 = null;
        if (var4 > 0) {
            var6 = new int[]{var4};
        }

        for (AudioBuffer var7 = var1.aNodeTable1112.head(); var7 != null; var7 = var1.aNodeTable1112.next()) {
            int var8 = (int) var7.key;
            AudioOverride var9 = overrides.lookup(var8);
            if (var9 == null) {
                var9 = AudioOverride.method59(var2, var8);
                if (var9 == null) {
                    var5 = false;
                    continue;
                }

                overrides.put(var9, var8);
            }

            if (!var9.method805(var3, var7.payload, var6)) {
                var5 = false;
            }
        }

        if (var5) {
            var1.method23();
        }

        return var5;
    }

    boolean method754(Node_Sub19 var1) {
        if (var1.aClass5_Sub6_Sub2_1195 == null) {
            if (var1.anInt696 >= 0) {
                var1.unlink();
                if (var1.anInt574 > 0 && var1 == aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574]) {
                    aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574] = null;
                }
            }

            return true;
        }
        return false;
    }

    void method774(int var1) {
        if ((anIntArray1102[var1] & 2) != 0) {
            for (Node_Sub19 var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.head(); var2 != null; var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.next()) {
                if (var2.anInt380 == var1 && aNode_Sub19ArrayArray1104[var1][var2.anInt112] == null && var2.anInt696 < 0) {
                    var2.anInt696 = 0;
                }
            }
        }

    }

    public int method770() {
        return this.anInt568;
    }

    void method762(int var1) {
        if ((anIntArray1102[var1] & 4) != 0) {
            for (Node_Sub19 var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.head(); var2 != null; var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.next()) {
                if (var2.anInt380 == var1) {
                    var2.anInt1149 = 0;
                }
            }
        }

    }

    void method765(int var1) {
        int var2 = var1 & 240;
        int var3;
        int var4;
        int var5;
        if (var2 == 128) {
            var3 = var1 & 15;
            var4 = var1 >> 8 & 127;
            method761(var3, var4);
        } else if (var2 == 144) {
            var3 = var1 & 15;
            var4 = var1 >> 8 & 127;
            var5 = var1 >> 16 & 127;
            if (var5 > 0) {
                method764(var3, var4, var5);
            } else {
                method761(var3, var4);
            }

        } else if (var2 == 160) {
            method767();
        } else if (var2 == 176) {
            var3 = var1 & 15;
            var4 = var1 >> 8 & 127;
            var5 = var1 >> 16 & 127;
            if (var4 == 0) {
                anIntArray691[var3] = (var5 << 14) + (anIntArray691[var3] & -2080769);
            }

            if (var4 == 32) {
                anIntArray691[var3] = (var5 << 7) + (anIntArray691[var3] & -16257);
            }

            if (var4 == 1) {
                anIntArray1101[var3] = (var5 << 7) + (anIntArray1101[var3] & -16257);
            }

            if (var4 == 33) {
                anIntArray1101[var3] = var5 + (anIntArray1101[var3] & -128);
            }

            if (var4 == 5) {
                anIntArray1100[var3] = (var5 << 7) + (anIntArray1100[var3] & -16257);
            }

            if (var4 == 37) {
                anIntArray1100[var3] = var5 + (anIntArray1100[var3] & -128);
            }

            if (var4 == 7) {
                anIntArray1108[var3] = (var5 << 7) + (anIntArray1108[var3] & -16257);
            }

            if (var4 == 39) {
                anIntArray1108[var3] = var5 + (anIntArray1108[var3] & -128);
            }

            if (var4 == 10) {
                anIntArray1107[var3] = (var5 << 7) + (anIntArray1107[var3] & -16257);
            }

            if (var4 == 42) {
                anIntArray1107[var3] = var5 + (anIntArray1107[var3] & -128);
            }

            if (var4 == 11) {
                this.anIntArray747[var3] = (var5 << 7) + (anIntArray747[var3] & -16257);
            }

            if (var4 == 43) {
                anIntArray747[var3] = var5 + (anIntArray747[var3] & -128);
            }

            int[] var10000;
            if (var4 == 64) {
                if (var5 >= 64) {
                    var10000 = anIntArray1102;
                    var10000[var3] |= 1;
                } else {
                    var10000 = anIntArray1102;
                    var10000[var3] &= -2;
                }
            }

            if (var4 == 65) {
                if (var5 >= 64) {
                    var10000 = anIntArray1102;
                    var10000[var3] |= 2;
                } else {
                    this.method774(var3);
                    var10000 = anIntArray1102;
                    var10000[var3] &= -3;
                }
            }

            if (var4 == 99) {
                anIntArray787[var3] = (var5 << 7) + (anIntArray787[var3] & 127);
            }

            if (var4 == 98) {
                anIntArray787[var3] = (anIntArray787[var3] & 16256) + var5;
            }

            if (var4 == 101) {
                anIntArray787[var3] = (var5 << 7) + (anIntArray787[var3] & 127) + 16384;
            }

            if (var4 == 100) {
                anIntArray787[var3] = (anIntArray787[var3] & 16256) + var5 + 16384;
            }

            if (var4 == 120) {
                method588(var3);
            }

            if (var4 == 121) {
                method763(var3);
            }

            if (var4 == 123) {
                method768(var3);
            }

            int var6;
            if (var4 == 6) {
                var6 = anIntArray787[var3];
                if (var6 == 16384) {
                    anIntArray782[var3] = (var5 << 7) + (anIntArray782[var3] & -16257);
                }
            }

            if (var4 == 38) {
                var6 = this.anIntArray787[var3];
                if (var6 == 16384) {
                    anIntArray782[var3] = var5 + (anIntArray782[var3] & -128);
                }
            }

            if (var4 == 16) {
                anIntArray1103[var3] = (var5 << 7) + (anIntArray1103[var3] & -16257);
            }

            if (var4 == 48) {
                anIntArray1103[var3] = var5 + (anIntArray1103[var3] & -128);
            }

            if (var4 == 81) {
                if (var5 >= 64) {
                    var10000 = anIntArray1102;
                    var10000[var3] |= 4;
                } else {
                    this.method762(var3);
                    var10000 = anIntArray1102;
                    var10000[var3] &= -5;
                }
            }

            if (var4 == 17) {
                method778(var3, (var5 << 7) + (anIntArray781[var3] & -16257));
            }

            if (var4 == 49) {
                method778(var3, var5 + (anIntArray781[var3] & -128));
            }

        } else if (var2 == 192) {
            var3 = var1 & 15;
            var4 = var1 >> 8 & 127;
            method777(var3, var4 + anIntArray691[var3]);
        } else if (var2 == 208) {
            method758();
        } else if (var2 == 224) {
            var3 = var1 & 15;
            var4 = (var1 >> 8 & 127) + (var1 >> 9 & 16256);
            method769(var3, var4);
        } else {
            var2 = var1 & 255;
            if (var2 == 255) {
                method757();
            }
        }
    }

    public synchronized boolean method684() {
        return decoder.method791();
    }

    public synchronized void method775() {
        for (AudioOverride var1 = overrides.head(); var1 != null; var1 = overrides.next()) {
            var1.method23();
        }

    }

    public synchronized void method592() {
        for (AudioOverride var1 = overrides.head(); var1 != null; var1 = overrides.next()) {
            var1.unlink();
        }

    }

    boolean method753(Node_Sub19 var1, int[] var2, int var3, int var4) {
        var1.anInt563 = URLRequest.audioSampleRate / 100;
        if (var1.anInt696 < 0 || var1.aClass5_Sub6_Sub2_1195 != null && !var1.aClass5_Sub6_Sub2_1195.method586()) {
            int var5 = var1.anInt368;
            if (var5 > 0) {
                var5 -= (int) (16.0D * Math.pow(2.0D, 4.921259842519685E-4D * (double) anIntArray1100[var1.anInt380]) + 0.5D);
                if (var5 < 0) {
                    var5 = 0;
                }

                var1.anInt368 = var5;
            }

            var1.aClass5_Sub6_Sub2_1195.method588(method773(var1));
            AudioOverrideEffect var6 = var1.aAudioOverrideEffect_1193;
            boolean var7 = false;
            ++var1.anInt788;
            var1.anInt666 += var6.anInt1133;
            double var8 = 5.086263020833333E-6D * (double) ((var1.anInt112 - 60 << 8) + (var1.anInt564 * var1.anInt368 >> 12));
            if (var6.anInt1129 > 0) {
                if (var6.anInt1125 > 0) {
                    var1.anInt367 += (int) (128.0D * Math.pow(2.0D, (double) var6.anInt1125 * var8) + 0.5D);
                } else {
                    var1.anInt367 += 128;
                }
            }

            if (var6.aByteArray1132 != null) {
                if (var6.anInt1128 > 0) {
                    var1.anInt386 += (int) (128.0D * Math.pow(2.0D, (double) var6.anInt1128 * var8) + 0.5D);
                } else {
                    var1.anInt386 += 128;
                }

                while (var1.anInt366 * 2 < var6.aByteArray1132.length - 2 && var1.anInt386 > (var6.aByteArray1132[var1.anInt366 * 2 + 2] & 255) << 8) {
                    var1.anInt366 = var1.anInt366 * 4 + 4;
                }

                if (var1.anInt366 * 2 == var6.aByteArray1132.length - 2 && var6.aByteArray1132[var1.anInt366 * 2 + 1] == 0) {
                    var7 = true;
                }
            }

            if (var1.anInt696 >= 0 && var6.aByteArray1134 != null && (anIntArray1102[var1.anInt380] & 1) == 0 && (var1.anInt574 < 0 || var1 != aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574])) {
                if (var6.anInt1127 > 0) {
                    var1.anInt696 += (int) (128.0D * Math.pow(2.0D, (double) var6.anInt1127 * var8) + 0.5D);
                } else {
                    var1.anInt696 += 128;
                }

                while (var1.anInt702 * 2 < var6.aByteArray1134.length - 2 && var1.anInt696 > (var6.aByteArray1134[var1.anInt702 * 2 + 2] & 255) << 8) {
                    var1.anInt702 = var1.anInt702 * 4 + 4;
                }

                if (var1.anInt702 * 2 == var6.aByteArray1134.length - 2) {
                    var7 = true;
                }
            }

            if (var7) {
                var1.aClass5_Sub6_Sub2_1195.method589(var1.anInt563);
                if (var2 != null) {
                    var1.aClass5_Sub6_Sub2_1195.method311(var2, var3, var4);
                } else {
                    var1.aClass5_Sub6_Sub2_1195.method303(var4);
                }

                if (var1.aClass5_Sub6_Sub2_1195.method577()) {
                    aClass5_Sub6_Sub4_1110.aClass5_Sub6_Sub1_1153.method312(var1.aClass5_Sub6_Sub2_1195);
                }

                var1.method499();
                if (var1.anInt696 >= 0) {
                    var1.unlink();
                    if (var1.anInt574 > 0 && var1 == aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574]) {
                        aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574] = null;
                    }
                }

                return true;
            }
            var1.aClass5_Sub6_Sub2_1195.method576(var1.anInt563, method760(var1), method772(var1));
            return false;
        }
        var1.method499();
        var1.unlink();
        if (var1.anInt574 > 0 && var1 == aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574]) {
            aNode_Sub19ArrayArray1099[var1.anInt380][var1.anInt574] = null;
        }

        return true;
    }

    public synchronized void method703(int var1) {
        anInt568 = var1;
    }

    public synchronized PcmStream method307() {
        return aClass5_Sub6_Sub4_1110;
    }

    void method778(int var1, int var2) {
        anIntArray781[var1] = var2;
        anIntArray785[var1] = (int) (2097152.0D * Math.pow(2.0D, 5.4931640625E-4D * (double) var2) + 0.5D);
    }

    public synchronized void method766(AudioTrack var1, boolean var2) {
        method756();
        decoder.method793(var1.aByteArray503);
        aBoolean1109 = var2;
        aLong1106 = 0L;
        int var3 = decoder.method790();

        for (int var4 = 0; var4 < var3; ++var4) {
            decoder.method789(var4);
            decoder.method784(var4);
            decoder.method787(var4);
        }

        anInt694 = decoder.method786();
        anInt1105 = decoder.anIntArray1115[anInt694];
        aLong1098 = decoder.method780(anInt1105);
    }

    void method755() {
        int var1 = anInt694;
        int var2 = anInt1105;

        long var3;
        for (var3 = aLong1098; var2 == anInt1105; var3 = decoder.method780(var2)) {
            while (var2 == decoder.anIntArray1115[var1]) {
                decoder.method789(var1);
                int var5 = decoder.method792(var1);
                if (var5 == 1) {
                    decoder.method788();
                    decoder.method787(var1);
                    if (decoder.method779()) {
                        if (!aBoolean1109 || var2 == 0) {
                            method757();
                            decoder.method785();
                            return;
                        }

                        decoder.method782(var3);
                    }
                    break;
                }

                if ((var5 & 128) != 0) {
                    method765(var5);
                }

                decoder.method784(var1);
                decoder.method787(var1);
            }

            var1 = decoder.method786();
            var2 = decoder.anIntArray1115[var1];
        }

        anInt694 = var1;
        anInt1105 = var2;
        aLong1098 = var3;
    }

    void method771(Node_Sub19 var1, boolean var2) {
        int var3 = var1.aClass5_Sub10_Sub1_1194.samples.length;
        int var5;
        if (var2 && var1.aClass5_Sub10_Sub1_1194.aBoolean502) {
            int var4 = var3 + var3 - var1.aClass5_Sub10_Sub1_1194.start;
            var5 = (int) ((long) anIntArray1103[var1.anInt380] * (long) var4 >> 6);
            var3 <<= 8;
            if (var5 >= var3) {
                var5 = var3 + var3 - 1 - var5;
                var1.aClass5_Sub6_Sub2_1195.method593();
            }
        } else {
            var5 = (int) ((long) var3 * (long) anIntArray1103[var1.anInt380] >> 6);
        }

        var1.aClass5_Sub6_Sub2_1195.method582(var5);
    }

    public synchronized int method305() {
        return 0;
    }

    int method760(Node_Sub19 var1) {
        AudioOverrideEffect var2 = var1.aAudioOverrideEffect_1193;
        int var3 = anIntArray1108[var1.anInt380] * anIntArray747[var1.anInt380] + 4096 >> 13;
        var3 = var3 * var3 + 16384 >> 15;
        var3 = var3 * var1.anInt375 + 16384 >> 15;
        var3 = var3 * anInt568 + 128 >> 8;
        if (var2.anInt1129 > 0) {
            var3 = (int) ((double) var3 * Math.pow(0.5D, (double) var1.anInt367 * 1.953125E-5D * (double) var2.anInt1129) + 0.5D);
        }

        int var4;
        int var5;
        int var6;
        int var7;
        if (var2.aByteArray1132 != null) {
            var4 = var1.anInt386;
            var5 = var2.aByteArray1132[var1.anInt366 * 2 + 1];
            if (var1.anInt366 * 2 < var2.aByteArray1132.length - 2) {
                var6 = (var2.aByteArray1132[var1.anInt366 * 2] & 255) << 8;
                var7 = (var2.aByteArray1132[var1.anInt366 * 2 + 2] & 255) << 8;
                var5 += (var2.aByteArray1132[var1.anInt366 * 2 + 3] - var5) * (var4 - var6) / (var7 - var6);
            }

            var3 = var5 * var3 + 32 >> 6;
        }

        if (var1.anInt696 > 0 && var2.aByteArray1134 != null) {
            var4 = var1.anInt696;
            var5 = var2.aByteArray1134[var1.anInt702 * 2 + 1];
            if (var1.anInt702 * 2 < var2.aByteArray1134.length - 2) {
                var6 = (var2.aByteArray1134[var1.anInt702 * 2] & 255) << 8;
                var7 = (var2.aByteArray1134[var1.anInt702 * 2 + 2] & 255) << 8;
                var5 += (var4 - var6) * (var2.aByteArray1134[var1.anInt702 * 2 + 3] - var5) / (var7 - var6);
            }

            var3 = var3 * var5 + 32 >> 6;
        }

        return var3;
    }

    int method772(Node_Sub19 var1) {
        int var2 = anIntArray1107[var1.anInt380];
        return var2 < 8192 ? var2 * var1.anInt372 + 32 >> 6 : 16384 - ((128 - var1.anInt372) * (16384 - var2) + 32 >> 6);
    }

    public synchronized void method575(int var1, int var2) {
        method759(var1, var2);
    }

    void method768(int var1) {
        for (Node_Sub19 var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.head(); var2 != null; var2 = aClass5_Sub6_Sub4_1110.aNodeDeque373.next()) {
            if ((var1 < 0 || var2.anInt380 == var1) && var2.anInt696 < 0) {
                aNode_Sub19ArrayArray1104[var2.anInt380][var2.anInt112] = null;
                var2.anInt696 = 0;
            }
        }

    }
}
