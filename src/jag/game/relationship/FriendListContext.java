package jag.game.relationship;

import jag.ClientParameter;
import jag.commons.collection.LinkableDeque;
import jag.opcode.Buffer;
import jag.opcode.FriendLoginUpdate;

public class FriendListContext extends ChatterContext<BefriendedPlayer> {

    public final ClientParameter nameLengthParameter;
    public final LinkableDeque<FriendLoginUpdate> loginUpdates;

    public int anInt833;

    public FriendListContext(ClientParameter var1) {
        super(400);
        anInt833 = 1;
        loginUpdates = new LinkableDeque<>();
        nameLengthParameter = var1;
    }

    public void decode(Buffer buffer, int available) {
        while (true) {
            if (buffer.pos < available) {
                boolean var3 = buffer.g1() == 1;
                NamePair displayName = new NamePair(buffer.gstr(), nameLengthParameter);
                NamePair previousName = new NamePair(buffer.gstr(), nameLengthParameter);
                int var6 = buffer.g2();
                int var7 = buffer.g1();
                int var8 = buffer.g1();
                boolean var9 = (var8 & 2) != 0;
                boolean var10 = (var8 & 1) != 0;
                if (var6 > 0) {
                    buffer.gstr();
                    buffer.g1();
                    buffer.g4();
                }

                buffer.gstr();
                if (displayName.isFormattedPresent()) {
                    BefriendedPlayer var11 = getChatterByDisplayName(displayName);
                    if (var3) {
                        BefriendedPlayer var12 = getChatterByDisplayName(previousName);
                        if (var12 != null && var11 != var12) {
                            if (var11 != null) {
                                remove(var12);
                            } else {
                                var11 = var12;
                            }
                        }
                    }

                    if (var11 != null) {
                        update(var11, displayName, previousName);
                        if (var6 != var11.world) {
                            boolean var13 = true;

                            for (FriendLoginUpdate update = loginUpdates.current(); update != null; update = loginUpdates.next()) {
                                if (update.namePair.equals(displayName)) {
                                    if (var6 != 0 && update.world == 0) {
                                        update.unlink();
                                        var13 = false;
                                    } else if (var6 == 0 && update.world != 0) {
                                        update.unlink();
                                        var13 = false;
                                    }
                                }
                            }

                            if (var13) {
                                loginUpdates.insert(new FriendLoginUpdate(displayName, var6));
                            }
                        }
                    } else {
                        if (getMemberCount() >= 400) {
                            continue;
                        }

                        var11 = addAndCache(displayName, previousName);
                    }

                    if (var6 != var11.world) {
                        var11.index = ++anInt833 - 1;
                        if (var11.world == -1 && var6 == 0) {
                            var11.index = -(var11.index);
                        }

                        var11.world = var6;
                    }

                    var11.rank = var7;
                    var11.aBoolean745 = var9;
                    var11.aBoolean746 = var10;
                    continue;
                }

                throw new IllegalStateException();
            }

            sort();
            return;
        }
    }

    BefriendedPlayer newChatter() {
        return new BefriendedPlayer();
    }

    public boolean isCached(NamePair var1, boolean requireLoggedIn) {
        BefriendedPlayer friend = getChatterByAnyName(var1);
        if (friend == null) {
            return false;
        }
        return !requireLoggedIn || friend.world != 0;
    }

    BefriendedPlayer[] newArray(int size) {
        return new BefriendedPlayer[size];
    }
}
