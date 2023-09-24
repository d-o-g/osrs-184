package jagex.oldscape.client.social;

import jagex.oldscape.ClientParameter;
import jagex.oldscape.LocalPlayerNameProvider;
import jagex.core.stringtools.Base37;
import jagex.messaging.Buffer;

public class FriendChat extends ChatterContext<FriendChatUser> {

  public final LocalPlayerNameProvider localPlayerNameProvider;
  public final ClientParameter gameType;

  public String channelName;
  public String channelOwner;

  public int localPlayerRank;
  public int memberCount;

  public byte channelRank;

  public FriendChat(ClientParameter gameType, LocalPlayerNameProvider localPlayerNameProvider) {
    super(100);
    this.gameType = gameType;
    this.localPlayerNameProvider = localPlayerNameProvider;
    channelName = null;
    channelOwner = null;
    memberCount = 1;
  }

  private static String filterName(String entry) {
    long encoded = Base37.encode(entry);
    String decoded = Base37.decode(encoded);
    if (decoded == null) {
      decoded = "";
    }

    return decoded;
  }

  public final void setChannelOwner(String owner) {
    channelOwner = filterName(owner);
  }

  FriendChatUser newChatter() {
    return new FriendChatUser();
  }

  public final void setChannelName(String channelName) {
    this.channelName = filterName(channelName);
  }

  FriendChatUser[] newArray(int size) {
    return new FriendChatUser[size];
  }

  public final void method1392() {
    for (int index = 0; index < getMemberCount(); ++index) {
      getChatter(index).method710();
    }
  }

  public final void method1386() {
    for (int var1 = 0; var1 < getMemberCount(); ++var1) {
      getChatter(var1).method707();
    }
  }

  public final void setLocalPlayerRankFrom(FriendChatUser member) {
    if (member.getDisplayName().equals(localPlayerNameProvider.getNamePair())) {
      localPlayerRank = member.rank;
    }
  }

  public final void decodeUpdate(Buffer buffer) {
    NamePair name = new NamePair(buffer.gstr(), gameType);
    int world = buffer.g2();
    byte rank = buffer.g1b();
    boolean var5 = rank == -128;

    FriendChatUser member;
    if (var5) {
      if (getMemberCount() == 0) {
        return;
      }

      member = getChatterByDisplayName(name);
      if (member != null && member.getWorld() == world) {
        remove(member);
      }
    } else {
      buffer.gstr();
      member = getChatterByDisplayName(name);
      if (member == null) {
        if (getMemberCount() > super.capacity) {
          return;
        }

        member = addAndCache(name);
      }

      member.set(world, ++memberCount - 1);
      member.rank = rank;
      setLocalPlayerRankFrom(member);
    }

  }

  public final void decode(Buffer buffer) {
    setChannelOwner(buffer.gstr());
    long encoded = buffer.g8();
    long var4 = encoded;
    String decodedBase37;
    if (encoded > 0L && encoded < 6582952005840035281L) {
      if (encoded % 37L == 0L) {
        decodedBase37 = null;
      } else {
        int length = 0;

        for (long var11 = encoded; 0L != var11; var11 /= 37L) {
          ++length;
        }

        StringBuilder processed = new StringBuilder(length);

        while (var4 != 0L) {
          long old = var4;
          var4 /= 37L;
          processed.append(Base37.CHARSET[(int) (old - 37L * var4)]);
        }

        decodedBase37 = processed.reverse().toString();
      }
    } else {
      decodedBase37 = null;
    }

    setChannelName(decodedBase37);
    channelRank = buffer.g1b();
    int count = buffer.g1();
    if (count != 255) {
      clear();

      for (int i = 0; i < count; ++i) {
        FriendChatUser member = addAndCache(new NamePair(buffer.gstr(), gameType));
        int world = buffer.g2();
        member.set(world, ++memberCount - 1);
        member.rank = buffer.g1b();
        buffer.gstr();
        setLocalPlayerRankFrom(member);
      }
    }
  }
}
