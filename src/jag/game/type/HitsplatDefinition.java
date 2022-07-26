package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.game.HealthBar;
import jag.game.Vars;
import jag.game.relationship.FriendsChatUser;
import jag.game.stockmarket.StockMarketOfferNameComparator;
import jag.graphics.Font;
import jag.graphics.Sprite;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class HitsplatDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<HitsplatDefinition> cache = new ReferenceCache<>(64);
    public static final ReferenceCache<Font> fonts = new ReferenceCache<>(20);
    public static final ReferenceCache<Sprite> sprites = new ReferenceCache<>(64);
    public static ReferenceTable table;
    public static int anInt1929;
    public static ReferenceTable aReferenceTable1515;

    public int textColor;
    public int comparisonType;
    public int duration;
    public int[] transformIds;
    public int offsetX;
    public int fade;
    public int offsetY;
    public int anInt1659;
    public String amount;
    public int varpbitIndex;
    public int iconId;
    public int leftSpriteId;
    public int rightSpriteId;
    public int middleSpriteId;
    public int fontId;
    public int varpIndex;

    public HitsplatDefinition() {
        fontId = -1;
        textColor = 16777215;
        duration = 70;
        iconId = -1;
        middleSpriteId = -1;
        leftSpriteId = -1;
        rightSpriteId = -1;
        offsetX = 0;
        offsetY = 0;
        fade = -1;
        amount = "";
        comparisonType = -1;
        anInt1659 = 0;
        varpbitIndex = -1;
        varpIndex = -1;
    }

    public void method988(Buffer var1, int var2) {
        if (var2 == 1) {
            fontId = var1.method1051();
        } else if (var2 == 2) {
            textColor = var1.g3();
        } else if (var2 == 3) {
            iconId = var1.method1051();
        } else if (var2 == 4) {
            leftSpriteId = var1.method1051();
        } else if (var2 == 5) {
            middleSpriteId = var1.method1051();
        } else if (var2 == 6) {
            rightSpriteId = var1.method1051();
        } else if (var2 == 7) {
            offsetX = var1.g2b();
        } else if (var2 == 8) {
            amount = var1.checkedgstr();
        } else if (var2 == 9) {
            duration = var1.g2();
        } else if (var2 == 10) {
            offsetY = var1.g2b();
        } else if (var2 == 11) {
            fade = 0;
        } else if (var2 == 12) {
            comparisonType = var1.g1();
        } else if (var2 == 13) {
            anInt1659 = var1.g2b();
        } else if (var2 == 14) {
            fade = var1.g2();
        } else if (var2 == 17 || var2 == 18) {
            varpbitIndex = var1.g2();
            if (varpbitIndex == 65535) {
                varpbitIndex = -1;
            }

            varpIndex = var1.g2();
            if (varpIndex == 65535) {
                varpIndex = -1;
            }

            int var3 = -1;
            if (var2 == 18) {
                var3 = var1.g2();
                if (var3 == 65535) {
                    var3 = -1;
                }
            }

            int var4 = var1.g1();
            transformIds = new int[var4 + 2];

            for (int var5 = 0; var5 <= var4; ++var5) {
                transformIds[var5] = var1.g2();
                if (transformIds[var5] == 65535) {
                    transformIds[var5] = -1;
                }
            }

            transformIds[var4 + 1] = var3;
        }

    }

    public void decode(Buffer var1) {
        while (true) {
            int var2 = var1.g1();
            if (var2 == 0) {
                return;
            }

            method988(var1, var2);
        }
    }

    public final HitsplatDefinition method1437() {
        int var1 = -1;
        if (varpbitIndex != -1) {
            var1 = Varbit.get(varpbitIndex);
        } else if (varpIndex != -1) {
            var1 = Vars.values[varpIndex];
        }

        int var2;
        if (var1 >= 0 && var1 < transformIds.length - 1) {
            var2 = transformIds[var1];
        } else {
            var2 = transformIds[transformIds.length - 1];
        }

        if (var2 != -1) {
            HitsplatDefinition var3 = cache.get(var2);
            HitsplatDefinition var4;
            if (var3 == null) {
                byte[] var5 = table.unpack(32, var2);
                var3 = new HitsplatDefinition();
                if (var5 != null) {
                    var3.decode(new Buffer(var5));
                }

                cache.put(var3, var2);
            }
            var4 = var3;

            return var4;
        }
        return null;
    }

    public Sprite getIcon() {
        if (iconId < 0) {
            return null;
        }
        Sprite var2 = sprites.get(iconId);
        if (var2 != null) {
            return var2;
        }
        var2 = Sprite.get(StockMarketOfferNameComparator.aReferenceTable480, iconId, 0);
        if (var2 != null) {
            sprites.put(var2, iconId);
        }

        return var2;
    }

    public Sprite getMiddleSprite() {
        if (middleSpriteId < 0) {
            return null;
        }
        Sprite var2 = sprites.get(middleSpriteId);
        if (var2 != null) {
            return var2;
        }
        var2 = Sprite.get(StockMarketOfferNameComparator.aReferenceTable480, middleSpriteId, 0);
        if (var2 != null) {
            sprites.put(var2, middleSpriteId);
        }

        return var2;
    }

    public Sprite getLeftSprite() {
        if (leftSpriteId < 0) {
            return null;
        }
        Sprite var2 = sprites.get(leftSpriteId);
        if (var2 != null) {
            return var2;
        }
        var2 = Sprite.get(StockMarketOfferNameComparator.aReferenceTable480, leftSpriteId, 0);
        if (var2 != null) {
            sprites.put(var2, leftSpriteId);
        }

        return var2;
    }

    public Sprite getRightSprite() {
        if (rightSpriteId < 0) {
            return null;
        }
        Sprite var2 = sprites.get(rightSpriteId);
        if (var2 != null) {
            return var2;
        }
        var2 = Sprite.get(StockMarketOfferNameComparator.aReferenceTable480, rightSpriteId, 0);
        if (var2 != null) {
            sprites.put(var2, rightSpriteId);
        }

        return var2;
    }

    public Font getFont() {
        if (fontId == -1) {
            return null;
        }
        Font font = fonts.get(fontId);
        if (font != null) {
            return font;
        }
        font = FriendsChatUser.method708(StockMarketOfferNameComparator.aReferenceTable480, aReferenceTable1515, fontId, 0);
        if (font != null) {
            fonts.put(font, fontId);
        }

        return font;
    }

    public String method1436(int var1) {
        String var2 = amount;

        while (true) {
            int var3 = var2.indexOf("%1");
            if (var3 < 0) {
                return var2;
            }

            var2 = var2.substring(0, var3) + HealthBar.toString(var1, false) + var2.substring(var3 + 2);
        }
    }
}
