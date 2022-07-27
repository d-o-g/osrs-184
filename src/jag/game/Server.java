package jag.game;

import jag.URLRequest;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.PathingEntity;
import jag.opcode.Buffer;
import jag.opcode.OutgoingPacket;
import jag.opcode.ClientProt;

import java.net.URL;

public class Server {

    public static Server[] servers;

    public static int[] populationComparator = new int[]{1, 1, 1, 1};
    public static int[] indexComparator = new int[]{0, 1, 2, 3};

    public static int count = 0;
    public static int current = 0;
    public static int expectedRead;

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

    public static int method1351(int var0, int var1) {
        int var2 = var0 >>> 31;
        return (var0 + var2) / var1 - var2;
    }

    public static void method1343(String var0) {
        if (!var0.equals("")) {
            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.JOIN_CLANCHANNEL, client.netWriter.encryptor);
            packet.buffer.p1(Buffer.stringLengthPlusOne(var0));
            packet.buffer.pcstr(var0);
            client.netWriter.writeLater(packet);
        }
    }

    public static void absoluteToViewport(PathingEntity entity, int height) {
        SceneGraph.absoluteToViewport(entity.absoluteX, entity.absoluteY, height);
    }

    public static void sort(int indexType, int populationType) {
        int[] indexComparator = new int[4];
        int[] populationComparator = new int[4];
        indexComparator[0] = indexType;
        populationComparator[0] = populationType;

        int srcIndex = 1;
        for (int dstIndex = 0; dstIndex < 4; ++dstIndex) {
            if (Server.indexComparator[dstIndex] != indexType) {
                indexComparator[srcIndex] = Server.indexComparator[dstIndex];
                populationComparator[srcIndex] = Server.populationComparator[dstIndex];
                ++srcIndex;
            }
        }

        Server.indexComparator = indexComparator;
        Server.populationComparator = populationComparator;
        sort(servers, 0, servers.length - 1, Server.indexComparator, Server.populationComparator);
    }

    public static String method149(int var0) {
        String var1 = Integer.toString(var0);

        for (int var2 = var1.length() - 3; var2 > 0; var2 -= 3) {
            var1 = var1.substring(0, var2) + "," + var1.substring(var2);
        }

        if (var1.length() > 9) {
            return " " + client.getColorTags(65408) + var1.substring(0, var1.length() - 8) + "M" + " " + " (" + var1 + ")" + "</col>";
        }
        return var1.length() > 6 ? " " + client.getColorTags(16777215) + var1.substring(0, var1.length() - 4) + "K" + " " + " (" + var1 + ")" + "</col>" : " " + client.getColorTags(16776960) + var1 + "</col>";
    }

    public static void sort(Server[] array, int start, int end, int[] comparatorA, int[] comparatorB) {
        if (start < end) {
            int var5 = start - 1;
            int var6 = end + 1;
            int var7 = (end + start) / 2;
            Server var8 = array[var7];
            array[var7] = array[start];
            array[start] = var8;

            while (var5 < var6) {
                boolean var9 = true;

                int var10;
                int var11;
                int var12;
                do {
                    --var6;

                    for (var10 = 0; var10 < 4; ++var10) {
                        if (comparatorA[var10] == 2) {
                            var11 = array[var6].index;
                            var12 = var8.index;
                        } else if (comparatorA[var10] == 1) {
                            var11 = array[var6].population;
                            var12 = var8.population;
                            if (var11 == -1 && comparatorB[var10] == 1) {
                                var11 = 2001;
                            }

                            if (var12 == -1 && comparatorB[var10] == 1) {
                                var12 = 2001;
                            }
                        } else if (comparatorA[var10] == 3) {
                            var11 = array[var6].isMembers() ? 1 : 0;
                            var12 = var8.isMembers() ? 1 : 0;
                        } else {
                            var11 = array[var6].id;
                            var12 = var8.id;
                        }

                        if (var11 != var12) {
                            if ((comparatorB[var10] != 1 || var11 <= var12) && (comparatorB[var10] != 0 || var11 >= var12)) {
                                var9 = false;
                            }
                            break;
                        }

                        if (var10 == 3) {
                            var9 = false;
                        }
                    }
                } while (var9);

                var9 = true;

                do {
                    ++var5;

                    for (var10 = 0; var10 < 4; ++var10) {
                        if (comparatorA[var10] == 2) {
                            var11 = array[var5].index;
                            var12 = var8.index;
                        } else if (comparatorA[var10] == 1) {
                            var11 = array[var5].population;
                            var12 = var8.population;
                            if (var11 == -1 && comparatorB[var10] == 1) {
                                var11 = 2001;
                            }

                            if (var12 == -1 && comparatorB[var10] == 1) {
                                var12 = 2001;
                            }
                        } else if (comparatorA[var10] == 3) {
                            var11 = array[var5].isMembers() ? 1 : 0;
                            var12 = var8.isMembers() ? 1 : 0;
                        } else {
                            var11 = array[var5].id;
                            var12 = var8.id;
                        }

                        if (var12 != var11) {
                            if ((comparatorB[var10] != 1 || var11 >= var12) && (comparatorB[var10] != 0 || var11 <= var12)) {
                                var9 = false;
                            }
                            break;
                        }

                        if (var10 == 3) {
                            var9 = false;
                        }
                    }
                } while (var9);

                if (var5 < var6) {
                    Server var13 = array[var5];
                    array[var5] = array[var6];
                    array[var6] = var13;
                }
            }

            sort(array, start, var6, comparatorA, comparatorB);
            sort(array, var6 + 1, end, comparatorA, comparatorB);
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

                sort(servers, 0, servers.length - 1, indexComparator, populationComparator);
                request = null;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request = null;
        }

        return false;
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
