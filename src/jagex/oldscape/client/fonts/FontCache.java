package jagex.oldscape.client.fonts;

import jagex.oldscape.client.social.FriendChatUser;
import jagex.jagex3.js5.ReferenceTable;

import java.util.HashMap;

public class FontCache {

  final ReferenceTable table1;
  final ReferenceTable table2;

  final HashMap<NamedFont, BaseFont> cache;

  public FontCache(ReferenceTable var1, ReferenceTable var2) {
    table1 = var1;
    table2 = var2;
    cache = new HashMap<>();
  }

  public HashMap<NamedFont, BaseFont> lookup(NamedFont[] collection) {
    HashMap<NamedFont, BaseFont> map = new HashMap<>();

    for (NamedFont named : collection) {
      if (this.cache.containsKey(named)) {
        map.put(named, this.cache.get(named));
      } else {
        ReferenceTable table = this.table1;
        String name = named.name;
        int group = table.getGroup(name);
        int file = table.getFile(group, "");
        Font font = FriendChatUser.method708(table, table2, group, file);
        if (font != null) {
          this.cache.put(named, font);
          map.put(named, font);
        }
      }
    }

    return map;
  }
}
