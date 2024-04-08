package jagex.oldscape.client;

import jagex.messaging.Buffer;
import jagex.oldscape.URLRequest;
import jagex.oldscape.client.type.ItemDefinition;

import java.net.URL;

public class Server {

  public static Server[] servers;

  public static int[] reverseFlags = new int[]{1, 1, 1, 1};
  public static int[] comparator = new int[]{0, 1, 2, 3};

  public static int count = 0;
  public static int current = 0;

  public static URLRequest request;

  public static String slu;

  public String domain;
  public String activity;

  public int mask;
  public int id;
  public int location;
  public int population;
  public int index;

  public Server() {

  }

  public static void sort(int indexType, int populationType) {
    int[] indexComparator = new int[4];
    int[] populationComparator = new int[4];
    indexComparator[0] = indexType;
    populationComparator[0] = populationType;

    int srcIndex = 1;
    for (int dstIndex = 0; dstIndex < 4; ++dstIndex) {
      if (Server.comparator[dstIndex] != indexType) {
        indexComparator[srcIndex] = Server.comparator[dstIndex];
        populationComparator[srcIndex] = Server.reverseFlags[dstIndex];
        ++srcIndex;
      }
    }

    Server.comparator = indexComparator;
    Server.reverseFlags = populationComparator;
    sort(servers, 0, servers.length - 1, Server.comparator, Server.reverseFlags);
  }

  public static void sort(Server[] array, int start, int end, int[] comparator, int[] reverseFlags) {
    if (start < end) {
      int leftIndex = start - 1;
      int rightIndex = end + 1;
      int pivotIndex = (end + start) / 2;
      Server pivotServer = array[pivotIndex];
      array[pivotIndex] = array[start];
      array[start] = pivotServer;

      while (leftIndex < rightIndex) {
        boolean leftShouldSwap = true;

        int leftComparatorValue;
        int rightComparatorValue;
        do {
          --rightIndex;

          for (int comparatorIndex = 0; comparatorIndex < 4; ++comparatorIndex) {
            leftComparatorValue = getComparatorValue(array[rightIndex], comparator[comparatorIndex]);
            rightComparatorValue = getComparatorValue(pivotServer, comparator[comparatorIndex]);

            if (leftComparatorValue != rightComparatorValue) {
              if ((reverseFlags[comparatorIndex] != 1 || leftComparatorValue <= rightComparatorValue) &&
                  (reverseFlags[comparatorIndex] != 0 || leftComparatorValue >= rightComparatorValue)) {
                leftShouldSwap = false;
              }
              break;
            }

            if (comparatorIndex == 3) {
              leftShouldSwap = false;
            }
          }
        } while (leftShouldSwap);

        boolean rightShouldSwap = true;

        do {
          ++leftIndex;

          for (int comparatorIndex = 0; comparatorIndex < 4; ++comparatorIndex) {
            leftComparatorValue = getComparatorValue(array[leftIndex], comparator[comparatorIndex]);
            rightComparatorValue = getComparatorValue(pivotServer, comparator[comparatorIndex]);

            if (leftComparatorValue != rightComparatorValue) {
              if ((reverseFlags[comparatorIndex] != 1 || leftComparatorValue >= rightComparatorValue) &&
                  (reverseFlags[comparatorIndex] != 0 || leftComparatorValue <= rightComparatorValue)) {
                rightShouldSwap = false;
              }
              break;
            }

            if (comparatorIndex == 3) {
              rightShouldSwap = false;
            }
          }
        } while (rightShouldSwap);

        if (leftIndex < rightIndex) {
          Server tempServer = array[leftIndex];
          array[leftIndex] = array[rightIndex];
          array[rightIndex] = tempServer;
        }
      }

      sort(array, start, rightIndex, comparator, reverseFlags);
      sort(array, rightIndex + 1, end, comparator, reverseFlags);
    }
  }


  private static int getComparatorValue(Server server, int comparator) {
    switch (comparator) {
      case 1:
        int population = server.population;
        return population == -1 ? 2001 : population;
      case 2:
        return server.index;
      case 3:
        return server.isMembers() ? 1 : 0;
      default: {
        return server.id;
      }
    }
  }

  public static Server next() {
    return current < count ? servers[++current - 1] : null;
  }

  public static Server first() {
    current = 0;
    return next();
  }

  public static boolean load() {
    try {
      if (request == null) {
        request = client.urlRequestProcessor.enqueue(new URL(slu));
      } else if (request.isComplete()) {
        byte[] data = request.getData();
        Buffer buffer = new Buffer(data);
        buffer.g4();
        count = buffer.g2();
        servers = new Server[count];

        int i = 0;
        while (i < count) {
          Server server = servers[i] = new Server();
          server.id = buffer.g2();
          server.mask = buffer.g4();
          server.domain = buffer.gstr();
          server.activity = buffer.gstr();
          server.location = buffer.g1();
          server.population = buffer.g2b();
          server.index = i++;
        }

        sort(servers, 0, servers.length - 1, comparator, reverseFlags);
        request = null;
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      request = null;
    }

    return false;
  }

  public static void setCurrent(Server server) {
    if (server.isMembers() != client.membersWorld) {
      client.membersWorld = server.isMembers();
      ItemDefinition.setLoadMembers(server.isMembers());
    }

    client.currentDomain = server.domain;
    client.currentWorld = server.id;
    client.currentWorldMask = server.mask;
    client.serverPort = client.gameTypeId == 0 ? 43594 : server.id + 40000;
    client.js5port = client.gameTypeId == 0 ? 443 : server.id + 50000;
    client.activePort = client.serverPort;
  }

  public boolean isMembers() {
    return (this.mask & 0x1) != 0;
  }

  public boolean method1350() {
    return (this.mask & 0x8) != 0;
  }

  public boolean method1346() {
    return (this.mask & 0x2) != 0;
  }

  public boolean isPVP() {
    return (this.mask & 0x4) != 0;
  }

  public boolean isTournament() {
    return (this.mask & 0x2000000) != 0;
  }

  public boolean isDeadman() {
    return (this.mask & 0x20000000) != 0;
  }
}
