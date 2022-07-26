package jag.statics;

import jag.audi.AudioSystem;
import jag.commons.input.Keyboard;
import jag.js5.ReferenceTable;

import java.awt.*;
import java.math.BigInteger;

public class Statics51 {
    public static final BigInteger rsaExponent = new BigInteger("10001", 16);
    public static final BigInteger rsaModulus = new BigInteger("a8cda33f9c45f0b9d1675c38ec69da6be4143320190060c229bb35ed91677a4447e09e77031e824aed13bfab51ba180bbda7e279a128f3eb016e9b0dd752a948431798626fc36ac10e036d945f2752d0d874c65a86d3e001a17bf9d63d8bc263b07be4ebc613d01781023a07de698e75248b582e682f1751395f61b9ec1bcbb3", 16);
    public static int anInt495;

    public static void method343(Component var0) {
        var0.removeKeyListener(Keyboard.instance);
        var0.removeFocusListener(Keyboard.instance);
        Keyboard.meta = -1;
    }

    public static void method344(ReferenceTable var0, int var1, int var2, int var3, boolean var4) {
        AudioSystem.state = 1;
        AudioSystem.tracks = var0;
        AudioSystem.trackGroup = var1;
        AudioSystem.trackFile = var2;
        AudioSystem.volume = var3;
        AudioSystem.aBoolean620 = var4;
        AudioSystem.pcmSampleLength = 10000;
    }
}
