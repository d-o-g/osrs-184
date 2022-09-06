package jag;

import jag.opcode.Buffer;

public class DefaultRouteStrategy extends RouteStrategy {

    public DefaultRouteStrategy() {

    }

    public static int convertHslToRgb(int hue, int sat, int light) {
        if (light > 179) {
            sat /= 2;
        }

        if (light > 192) {
            sat /= 2;
        }

        if (light > 217) {
            sat /= 2;
        }

        if (light > 243) {
            sat /= 2;
        }

        return (sat / 32 << 7) + (hue / 4 << 10) + light / 2;
    }

    public static String method294(Buffer var0) {
        return method1502(var0);
    }

    public static String method1502(Buffer buffer) {
        try {
            int len = buffer.gSmarts();
            if (len > 32767) {
                len = 32767;
            }

            byte[] data = new byte[len];
            buffer.pos += Huffman.instance.decompress(buffer.payload, buffer.pos, data, 0, len);
            return Buffer.readStringFromBytes(data, 0, len);
        } catch (Exception var6) {
            return "Cabbage";
        }
    }

    public boolean isDestination(int x, int y) {
        return x == destinationX && y == destinationY;
    }
}
