package jag.game.relationship;

import jag.ClientParameter;
import jag.opcode.Buffer;

public class IgnoreListContext extends ChatterContext<IgnoredPlayer> {

    public final ClientParameter nameLengthParameter;

    public IgnoreListContext(ClientParameter var1) {
        super(400);
        nameLengthParameter = var1;
    }

    IgnoredPlayer newChatter() {
        return new IgnoredPlayer();
    }

    IgnoredPlayer[] newArray(int size) {
        return new IgnoredPlayer[size];
    }

    public void decode(Buffer var1, int var2) {
        while (true) {
            if (var1.pos < var2) {
                int var3 = var1.g1();
                boolean var4 = (var3 & 1) == 1;
                NamePair var5 = new NamePair(var1.gstr(), nameLengthParameter);
                NamePair var6 = new NamePair(var1.gstr(), nameLengthParameter);
                var1.gstr();
                if (var5.isFormattedPresent()) {
                    IgnoredPlayer var7 = getChatterByDisplayName(var5);
                    if (var4) {
                        IgnoredPlayer var8 = getChatterByDisplayName(var6);
                        if (var8 != null && var7 != var8) {
                            if (var7 != null) {
                                remove(var8);
                            } else {
                                var7 = var8;
                            }
                        }
                    }

                    if (var7 != null) {
                        update(var7, var5, var6);
                        continue;
                    }

                    if (getMemberCount() < 400) {
                        int var9 = getMemberCount();
                        var7 = addAndCache(var5, var6);
                        var7.anInt1147 = var9;
                    }
                    continue;
                }

                throw new IllegalStateException();
            }

            return;
        }
    }
}
