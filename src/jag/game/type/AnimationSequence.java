package jag.game.type;

import jag.Login;
import jag.SerializableProcessor;
import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.game.scene.entity.DynamicObject;
import jag.game.scene.entity.Model;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class AnimationSequence extends DoublyLinkedNode {

    public static final ReferenceCache<AnimationSequence> cache = new ReferenceCache<>(64);
    public static final ReferenceCache<AnimationFrameGroup> frames = new ReferenceCache<>(100);

    public static java.awt.Font aFont1227;

    public static ReferenceTable aReferenceTable383;
    public static ReferenceTable aReferenceTable697;

    public int animatingPrecedence;
    public int[] frameIds;
    public int walkingPrecedence;
    public int loopOffset;
    public int[] frameLengths;
    public boolean stretch;
    public int offHand;
    public int priority;
    public int mainHand;
    public int maxLoops;
    public int replayMode;
    public int[] anIntArray691;
    public int[] interleaveOrder;
    public int[] anIntArray687;

    public AnimationSequence() {
        loopOffset = -1;
        stretch = false;
        priority = 5;
        offHand = -1;
        mainHand = -1;
        maxLoops = 99;
        animatingPrecedence = -1;
        walkingPrecedence = -1;
        replayMode = 2;
    }

    public static void method881(int var0) {
        DynamicObject.gc();
        switch (var0) {
            case 1:
                Login.step = 24;
                Login.setMessages("", "You were disconnected from the server.", "");
                break;
            case 2:
                SerializableProcessor.method450();
        }

    }

    public static void method1202(ReferenceTable var0, ReferenceTable var1, ReferenceTable var2) {
        aReferenceTable383 = var0;
        aReferenceTable697 = var1;
        AnimationFrameGroup.aReferenceTable1123 = var2;
    }

    public static AnimationSequence get(int var0) {
        AnimationSequence var2 = cache.get(var0);
        if (var2 != null) {
            return var2;
        }
        byte[] var3 = aReferenceTable383.unpack(12, var0);
        var2 = new AnimationSequence();
        if (var3 != null) {
            var2.method260(new Buffer(var3));
        }

        var2.method592();
        cache.put(var2, var0);
        return var2;
    }

    public void method259(Buffer var1, int var2) {
        int var3;
        int var4;
        if (var2 == 1) {
            var3 = var1.g2();
            frameLengths = new int[var3];

            for (var4 = 0; var4 < var3; ++var4) {
                frameLengths[var4] = var1.g2();
            }

            frameIds = new int[var3];

            for (var4 = 0; var4 < var3; ++var4) {
                frameIds[var4] = var1.g2();
            }

            for (var4 = 0; var4 < var3; ++var4) {
                frameIds[var4] += var1.g2() << 16;
            }
        } else if (var2 == 2) {
            loopOffset = var1.g2();
        } else if (var2 == 3) {
            var3 = var1.g1();
            interleaveOrder = new int[var3 + 1];

            for (var4 = 0; var4 < var3; ++var4) {
                interleaveOrder[var4] = var1.g1();
            }

            interleaveOrder[var3] = 9999999;
        } else if (var2 == 4) {
            stretch = true;
        } else if (var2 == 5) {
            priority = var1.g1();
        } else if (var2 == 6) {
            offHand = var1.g2();
        } else if (var2 == 7) {
            mainHand = var1.g2();
        } else if (var2 == 8) {
            maxLoops = var1.g1();
        } else if (var2 == 9) {
            animatingPrecedence = var1.g1();
        } else if (var2 == 10) {
            walkingPrecedence = var1.g1();
        } else if (var2 == 11) {
            replayMode = var1.g1();
        } else if (var2 == 12) {
            var3 = var1.g1();
            anIntArray687 = new int[var3];

            for (var4 = 0; var4 < var3; ++var4) {
                anIntArray687[var4] = var1.g2();
            }

            for (var4 = 0; var4 < var3; ++var4) {
                anIntArray687[var4] += var1.g2() << 16;
            }
        } else if (var2 == 13) {
            var3 = var1.g1();
            anIntArray691 = new int[var3];

            for (var4 = 0; var4 < var3; ++var4) {
                anIntArray691[var4] = var1.g3();
            }
        }

    }

    public Model applySequence(Model var1, int var2) {
        var2 = frameIds[var2];
        AnimationFrameGroup var3 = AnimationFrameGroup.get(var2 >> 16);
        var2 &= 65535;
        if (var3 == null) {
            return var1.method1291(true);
        }
        Model var4 = var1.method1291(!var3.isShowing(var2));
        var4.animate(var3, var2);
        return var4;
    }

    public Model applyStanceAndAnimation(Model var1, int frame, AnimationSequence stance, int stanceFrame) {
        frame = frameIds[frame];
        AnimationFrameGroup var5 = AnimationFrameGroup.get(frame >> 16);
        frame &= 65535;
        if (var5 == null) {
            return stance.applySequence(var1, stanceFrame);
        }
        stanceFrame = stance.frameIds[stanceFrame];
        AnimationFrameGroup var6 = AnimationFrameGroup.get(stanceFrame >> 16);
        stanceFrame &= 65535;
        Model var7;
        if (var6 == null) {
            var7 = var1.method1291(!var5.isShowing(frame));
            var7.animate(var5, frame);
            return var7;
        }
        var7 = var1.method1291(!var5.isShowing(frame) & !var6.isShowing(stanceFrame));
        var7.stanceAnimate(var5, frame, var6, stanceFrame, interleaveOrder);
        return var7;
    }

    public Model method877(Model var1, int var2, int var3) {
        var2 = frameIds[var2];
        AnimationFrameGroup var4 = AnimationFrameGroup.get(var2 >> 16);
        var2 &= 65535;
        if (var4 == null) {
            return var1.method1291(true);
        }
        Model var5 = var1.method1291(!var4.isShowing(var2));
        var3 &= 3;
        if (var3 == 1) {
            var5.method981();
        } else if (var3 == 2) {
            var5.method1288();
        } else if (var3 == 3) {
            var5.rotate90Y();
        }

        var5.animate(var4, var2);
        if (var3 == 1) {
            var5.rotate90Y();
        } else if (var3 == 2) {
            var5.method1288();
        } else if (var3 == 3) {
            var5.method981();
        }

        return var5;
    }

    public void method592() {
        if (animatingPrecedence == -1) {
            if (interleaveOrder != null) {
                animatingPrecedence = 2;
            } else {
                animatingPrecedence = 0;
            }
        }

        if (walkingPrecedence == -1) {
            if (interleaveOrder != null) {
                walkingPrecedence = 2;
            } else {
                walkingPrecedence = 0;
            }
        }

    }

    public Model method880(Model var1, int var2) {
        var2 = frameIds[var2];
        AnimationFrameGroup var3 = AnimationFrameGroup.get(var2 >> 16);
        var2 &= 65535;
        if (var3 == null) {
            return var1.method1294(true);
        }
        Model var4 = var1.method1294(!var3.isShowing(var2));
        var4.animate(var3, var2);
        return var4;
    }

    public void method260(Buffer var1) {
        while (true) {
            int var2 = var1.g1();
            if (var2 == 0) {
                return;
            }

            method259(var1, var2);
        }
    }

    public Model method879(Model var1, int var2) {
        int var3 = frameIds[var2];
        AnimationFrameGroup var4 = AnimationFrameGroup.get(var3 >> 16);
        var3 &= 65535;
        if (var4 == null) {
            return var1.method1291(true);
        }
        AnimationFrameGroup var5 = null;
        int var6 = 0;
        if (anIntArray687 != null && var2 < anIntArray687.length) {
            var6 = anIntArray687[var2];
            var5 = AnimationFrameGroup.get(var6 >> 16);
            var6 &= 65535;
        }

        Model var7;
        if (var5 != null && var6 != 65535) {
            var7 = var1.method1291(!var4.isShowing(var3) & !var5.isShowing(var6));
            var7.animate(var4, var3);
            var7.animate(var5, var6);
            return var7;
        }
        var7 = var1.method1291(!var4.isShowing(var3));
        var7.animate(var4, var3);
        return var7;
    }
}
