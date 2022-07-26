package jag;

import jag.commons.time.Clock;
import jag.game.option.ClientPreferences;
import jag.game.type.VarcInteger;
import jag.js5.Archive;
import jag.js5.DiskFile;
import jag.opcode.Buffer;
import jag.statics.Statics55;

import java.io.EOFException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Varcs {

    public static int clientTickIndex;

    final String[] strings;

    final Map<Integer, Object> values;

    final boolean[] persistentIntegers;

    boolean persistent;

    long lastUpdateTime;

    public Varcs() {
        persistent = false;
        values = new HashMap<>();

        int count = Archive.config.getFileCount(19);
        persistentIntegers = new boolean[count];

        for (int i = 0; i < count; ++i) {
            VarcInteger varc = VarcInteger.cache.get(i);
            if (varc == null) {
                byte[] data = VarcInteger.table.unpack(19, i);
                varc = new VarcInteger();
                if (data != null) {
                    varc.decode(new Buffer(data));
                }

                VarcInteger.cache.put(varc, i);
            }

            persistentIntegers[i] = varc.persists;
        }

        int stringCount = 0;
        if (Archive.config.isPresent(15)) {
            stringCount = Archive.config.getFileCount(15);
        }

        strings = new String[stringCount];
        decode();
    }

    DiskFile getPreferences(boolean createNew) {
        return ClientPreferences.getFile("2", Statics55.gameType.name, createNew);
    }

    public boolean persist() {
        return persistent;
    }

    public void update() {
        DiskFile preferences = getPreferences(true);

        try {
            int payloadSize = 3;
            int entrySize = 0;

            for (Map.Entry<Integer, Object> entry : values.entrySet()) {
                int id = entry.getKey();
                if (persistentIntegers[id]) {
                    Object value = entry.getValue();
                    payloadSize += 3;
                    if (value instanceof Integer) {
                        payloadSize += 4;
                    } else if (value instanceof String) {
                        payloadSize += Buffer.stringLengthPlusOne((String) value);
                    }

                    ++entrySize;
                }
            }

            Buffer buffer = new Buffer(payloadSize);
            buffer.p1(2);
            buffer.p2(entrySize);

            for (Map.Entry<Integer, Object> entry : values.entrySet()) {
                int id = entry.getKey();
                if (persistentIntegers[id]) {
                    buffer.p2(id);
                    Object obj = entry.getValue();
                    SerializableProcessor processor = SerializableProcessor.valueOf(obj.getClass());
                    buffer.p1(processor.ordinal);
                    Serializable value = processor.value;
                    value.encode(obj, buffer);
                }
            }

            preferences.write(buffer.payload, 0, buffer.pos);
        } catch (Exception ignored) {

        } finally {
            try {
                preferences.close();
            } catch (Exception ignored) {

            }
        }

        persistent = false;
        lastUpdateTime = Clock.now();
    }

    void decode() {
        DiskFile preferences = getPreferences(false);

        label227:
        {
            try {
                byte[] payload = new byte[(int) preferences.length()];

                int read;
                for (int i = 0; i < payload.length; i += read) {
                    read = preferences.read(payload, i, payload.length - i);
                    if (read == -1) {
                        throw new EOFException();
                    }
                }

                Buffer buffer = new Buffer(payload);
                if (buffer.payload.length - buffer.pos < 1) {
                    return;
                }

                int type = buffer.g1();
                if (type >= 0 && type <= 2) {
                    if (type >= 2) {
                        int count = buffer.g2();
                        int ptr = 0;

                        while (true) {
                            if (ptr >= count) {
                                break label227;
                            }

                            int index = buffer.g2();
                            int ordinal = buffer.g1();
                            SerializableProcessor processor = (SerializableProcessor) EnumType.getByOrdinal(SerializableProcessor.getValues(), ordinal);
                            Object value = processor.decode(buffer);
                            if (persistentIntegers[index]) {
                                values.put(index, value);
                            }

                            ++ptr;
                        }
                    } else {
                        int count = buffer.g2();
                        for (int i = 0; i < count; ++i) {
                            int key = buffer.g2();
                            int value = buffer.g4();
                            if (persistentIntegers[key]) {
                                values.put(key, value);
                            }
                        }

                        int len = buffer.g2();
                        int ptr = 0;
                        while (true) {
                            if (ptr >= len) {
                                break label227;
                            }

                            buffer.g2();
                            buffer.gstr();
                            ++ptr;
                        }
                    }
                }
            } catch (Exception e) {
                break label227;
            } finally {
                try {
                    preferences.close();
                } catch (Exception ignored) {
                }

            }
            return;
        }

        persistent = false;
    }

    public String getRawString(int index) {
        return strings[index];
    }

    public int getInteger(int id) {
        Object value = values.get(id);
        return value instanceof Integer ? (Integer) value : -1;
    }

    public String getString(int id) {
        Object value = values.get(id);
        return value instanceof String ? (String) value : "";
    }

    public void put(int id, int value) {
        values.put(id, value);
        if (persistentIntegers[id]) {
            persistent = true;
        }
    }

    public void putRawString(int index, String value) {
        strings[index] = value;
    }

    public void put(int id, String value) {
        values.put(id, value);
    }

    public void clear() {
        for (int i = 0; i < persistentIntegers.length; ++i) {
            if (!persistentIntegers[i]) {
                values.remove(i);
            }
        }
        Arrays.fill(strings, null);
    }

    public void updateIfRequired() {
        if (persistent && lastUpdateTime < Clock.now() - 60000L) {
            update();
        }
    }
}