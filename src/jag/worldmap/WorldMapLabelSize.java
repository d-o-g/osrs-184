package jag.worldmap;

import jag.ClientLocale;
import jag.audi.PcmStream_Sub1;
import jag.game.client;
import jag.game.relationship.ChatHistory;
import jag.game.relationship.NamePair;
import jag.game.scene.entity.PlayerEntity;
import jag.graphics.JagGraphics3D;
import jag.opcode.GPI;
import jag.opcode.OutgoingPacket;
import jag.opcode.ClientProt;
import jag.commons.Perlin;

public class WorldMapLabelSize {
    public static final WorldMapLabelSize SMALL = new WorldMapLabelSize(1, 0, 4);
    public static final WorldMapLabelSize MEDIUM = new WorldMapLabelSize(0, 1, 2);
    public static final WorldMapLabelSize LARGE = new WorldMapLabelSize(2, 2, 0);
    public static ClientLocale aClientLocale_525;
    public static PcmStream_Sub1 aClass5_Sub6_Sub1_528;

    public final int anInt527;
    final int anInt524;
    final int anInt529;

    WorldMapLabelSize(int var1, int var2, int var3) {
        this.anInt529 = var1;
        this.anInt527 = var2;
        this.anInt524 = var3;
    }

    public static WorldMapLabelSize[] method364() {
        return new WorldMapLabelSize[]{LARGE, SMALL, MEDIUM};
    }

    public static void method365(int var0, String var1) {
        int var2 = GPI.playerCount;
        int[] var3 = GPI.playerIndices;
        boolean var4 = false;
        NamePair var5 = new NamePair(var1, PreciseWorldMapAreaChunk.nameLengthParameter);

        for (int var6 = 0; var6 < var2; ++var6) {
            PlayerEntity var7 = client.players[var3[var6]];
            if (var7 != null && var7 != PlayerEntity.local && var7.namePair != null && var7.namePair.equals(var5)) {
                OutgoingPacket packet;
                if (var0 == 1) {
                    packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_0, client.netWriter.encryptor);
                    packet.buffer.p2a(var3[var6]);
                    packet.buffer.p1a(0);
                    client.netWriter.writeLater(packet);
                } else if (var0 == 4) {
                    packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_3, client.netWriter.encryptor);
                    packet.buffer.ip2a(var3[var6]);
                    packet.buffer.p1n(0);
                    client.netWriter.writeLater(packet);
                } else if (var0 == 6) {
                    packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_5, client.netWriter.encryptor);
                    packet.buffer.p2(var3[var6]);
                    packet.buffer.p1a(0);
                    client.netWriter.writeLater(packet);
                } else if (var0 == 7) {
                    packet = OutgoingPacket.prepare(ClientProt.PLAYER_ACTION_6, client.netWriter.encryptor);
                    packet.buffer.ip2(var3[var6]);
                    packet.buffer.p1a(0);
                    client.netWriter.writeLater(packet);
                }

                var4 = true;
                break;
            }
        }

        if (!var4) {
            ChatHistory.messageReceived(4, "", "Unable to find " + var1);
        }

    }

    public static int method363(int var0, int var1, int var2) {
        int var3 = var0 / var2;
        int var4 = var0 & var2 - 1;
        int var5 = var1 / var2;
        int var6 = var1 & var2 - 1;
        int var7 = Perlin.noise(var3, var5);
        int var8 = Perlin.noise(var3 + 1, var5);
        int var9 = Perlin.noise(var3, var5 + 1);
        int var10 = Perlin.noise(var3 + 1, var5 + 1);
        int var11 = 65536 - JagGraphics3D.COS_TABLE[var4 * 1024 / var2] >> 1;
        int var12 = ((65536 - var11) * var7 >> 16) + (var11 * var8 >> 16);
        int var13 = 65536 - JagGraphics3D.COS_TABLE[var4 * 1024 / var2] >> 1;
        int var14 = ((65536 - var13) * var9 >> 16) + (var10 * var13 >> 16);
        int var15 = 65536 - JagGraphics3D.COS_TABLE[var6 * 1024 / var2] >> 1;
        return ((65536 - var15) * var12 >> 16) + (var14 * var15 >> 16);
    }

    public boolean method366(float var1) {
        return var1 >= (float) this.anInt524;
    }
}
