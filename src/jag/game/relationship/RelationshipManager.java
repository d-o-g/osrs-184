package jag.game.relationship;

import jag.ClientParameter;
import jag.commons.time.Clock;
import jag.game.client;
import jag.game.scene.entity.PlayerEntity;
import jag.opcode.Buffer;
import jag.opcode.FriendLoginUpdate;
import jag.opcode.OutgoingPacket;
import jag.opcode.ClientProt;
import jag.statics.Statics53;
import jag.worldmap.WorldMapCacheFeature;

public class RelationshipManager {

    public final IgnoreListContext ignoreListContext;
    public final FriendListContext friendListContext;
    public final ClientParameter nameLengthParameter;

    public int mainState;

    public RelationshipManager(ClientParameter nameLengthParameter) {
        mainState = 0;
        this.nameLengthParameter = nameLengthParameter;
        friendListContext = new FriendListContext(nameLengthParameter);
        ignoreListContext = new IgnoreListContext(nameLengthParameter);
    }

    public static void method843() {
        for (ChatLine var1 : Statics53.CHAT_LINE_TABLE) {
            var1.method592();
        }

        if (client.friendChat != null) {
            client.friendChat.method1386();
        }

    }

    public final boolean isFriendLoggedIn(NamePair name) {
        BefriendedPlayer friend = friendListContext.getChatterByAnyName(name);
        return friend != null && friend.isLoggedIn();
    }

    public final boolean isIgnoreListFull() {
        return ignoreListContext.isFull() || ignoreListContext.getMemberCount() >= 100 && client.relationshipSystemState != 1;
    }

    public final boolean isFriendsListFull() {
        return friendListContext.isFull() || friendListContext.getMemberCount() >= 200 && client.relationshipSystemState != 1;
    }

    public final boolean isFriend(NamePair name) {
        if (name == null) {
            return false;
        }

        if (name.equals(PlayerEntity.local.namePair)) {
            return true;
        }

        return friendListContext.isCached(name, false);
    }

    public final boolean isIgnored(NamePair name) {
        if (name == null) {
            return false;
        }

        return ignoreListContext.isCached(name);
    }

    public final void decodeFriends(Buffer buffer, int var2) {
        friendListContext.decode(buffer, var2);
        mainState = 2;
        PlayerEntity.method1400();
    }

    public final void setLoading() {
        mainState = 1;
    }

    public boolean isReady() {
        return mainState == 2;
    }

    public final void befriend(String displayName) {
        if (displayName != null) {
            NamePair name = new NamePair(displayName, nameLengthParameter);
            if (name.isFormattedPresent()) {
                String message;
                if (isFriendsListFull()) {
                    message = "Your friend list is full. Max of 200 for free users, and 400 for members";
                    ChatHistory.messageReceived(30, "", message);
                } else if (PlayerEntity.local.namePair.equals(name)) {
                    message = "You can't add yourself to your own friend list";
                    ChatHistory.messageReceived(30, "", message);
                } else if (isFriend(name)) {
                    message = displayName + " is already on your friend list";
                    ChatHistory.messageReceived(30, "", message);
                } else if (isIgnored(name)) {
                    message = "Please remove " + displayName + " from your ignore list first";
                    ChatHistory.messageReceived(30, "", message);
                } else {
                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ADD_FRIEND, client.stream.encryptor);
                    packet.buffer.p1(Buffer.stringLengthPlusOne(displayName));
                    packet.buffer.pcstr(displayName);
                    client.stream.writeLater(packet);
                }
            }
        }
    }

    public final void unbefriend(String displayName) {
        if (displayName != null) {
            NamePair name = new NamePair(displayName, nameLengthParameter);
            if (name.isFormattedPresent()) {
                if (friendListContext.remove(name)) {
                    client.anInt1065 = client.anInt1075;
                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.REMOVE_FRIEND, client.stream.encryptor);
                    packet.buffer.p1(Buffer.stringLengthPlusOne(displayName));
                    packet.buffer.pcstr(displayName);
                    client.stream.writeLater(packet);
                }

                PlayerEntity.method1400();
            }
        }
    }

    public final void ignore(String var1) {
        if (var1 != null) {
            NamePair var2 = new NamePair(var1, nameLengthParameter);
            if (var2.isFormattedPresent()) {
                if (isIgnoreListFull()) {
                    WorldMapCacheFeature.method178();
                } else {
                    String message;
                    if (PlayerEntity.local.namePair.equals(var2)) {
                        message = "You can't add yourself to your own ignore list";
                        ChatHistory.messageReceived(30, "", message);
                    } else if (isIgnored(var2)) {
                        message = var1 + " is already on your ignore list";
                        ChatHistory.messageReceived(30, "", message);
                    } else if (isFriend(var2)) {
                        message = "Please remove " + var1 + " from your friend list first";
                        ChatHistory.messageReceived(30, "", message);
                    } else {
                        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.ADD_TO_IGNORE_LIST, client.stream.encryptor);
                        packet.buffer.p1(Buffer.stringLengthPlusOne(var1));
                        packet.buffer.pcstr(var1);
                        client.stream.writeLater(packet);
                    }
                }
            }
        }
    }

    public final void unignore(String displayName) {
        if (displayName != null) {
            NamePair name = new NamePair(displayName, nameLengthParameter);
            if (name.isFormattedPresent()) {
                if (ignoreListContext.remove(name)) {
                    client.anInt1065 = client.anInt1075;
                    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.REMOVE_FROM_IGNORE_LIST, client.stream.encryptor);
                    packet.buffer.p1(Buffer.stringLengthPlusOne(displayName));
                    packet.buffer.pcstr(displayName);
                    client.stream.writeLater(packet);
                }

                method843();
            }
        }
    }

    public final void clear() {
        mainState = 0;
        friendListContext.clear();
        ignoreListContext.clear();
    }

    public final void processFriendLogins() {
        for (FriendLoginUpdate update = friendListContext.loginUpdates.current(); update != null; update = friendListContext.loginUpdates.next()) {
            if ((long) update.time < Clock.now() / 1000L - 5L) {
                if (update.world > 0) {
                    ChatHistory.messageReceived(5, "", update.namePair + " has logged in.");
                }

                if (update.world == 0) {
                    ChatHistory.messageReceived(5, "", update.namePair + " has logged out.");
                }

                update.unlink();
            }
        }

    }
}
