package jag.opcode;

import jag.EnumType;
import jag.URLRequestProcessor;
import jag.audi.AudioSystem;
import jag.audi.ObjectSound;
import jag.game.*;
import jag.game.option.AttackOptionPriority;
import jag.game.type.*;
import jag.graphics.DefaultMaterialProvider;
import jag.graphics.JagGraphics3D;
import jag.js5.Archive;
import jag.statics.*;

import java.io.*;
import java.net.Socket;

public final class OldConnection extends Connection implements Runnable {
    public static InterfaceComponent hoveredComponent;
    final int anInt871;
    final int anInt870;
    final OldConnectionTaskProcessor aOldConnectionTaskProcessor_878;
    final Socket socket;
    int anInt874;
    boolean aBoolean879;
    int anInt881;
    boolean aBoolean872;
    InputStream input;
    byte[] aByteArray877;
    OldConnectionTask aOldConnectionTask_876;
    OutputStream output;

    public OldConnection(Socket var1, OldConnectionTaskProcessor var2, int var3) throws IOException {
        aBoolean879 = false;
        anInt874 = 0;
        anInt881 = 0;
        aBoolean872 = false;
        aOldConnectionTaskProcessor_878 = var2;
        socket = var1;
        anInt871 = var3;
        anInt870 = var3 - 100;
        socket.setSoTimeout(30000);
        socket.setTcpNoDelay(true);
        socket.setReceiveBufferSize(65536);
        socket.setSendBufferSize(65536);
        input = this.socket.getInputStream();
        output = this.socket.getOutputStream();
    }

    public static void method712(int var0) {
        SubInterface.process();

        for (ObjectSound var1 = ObjectSound.OBJECT_SOUNDS.head(); var1 != null; var1 = ObjectSound.OBJECT_SOUNDS.next()) {
            if (var1.definition != null) {
                var1.method254();
            }
        }

        int value = VarDefinition.get(var0).value;
        if (value != 0) {
            int varp = Vars.values[var0];
            if (value == 1) {
                if (varp == 1) {
                    JagGraphics3D.method634(0.9D);
                    ((DefaultMaterialProvider) JagGraphics3D.materialProvider).setBrightness(0.9D);
                }

                if (varp == 2) {
                    JagGraphics3D.method634(0.8D);
                    ((DefaultMaterialProvider) JagGraphics3D.materialProvider).setBrightness(0.8D);
                }

                if (varp == 3) {
                    JagGraphics3D.method634(0.7D);
                    ((DefaultMaterialProvider) JagGraphics3D.materialProvider).setBrightness(0.7D);
                }

                if (varp == 4) {
                    JagGraphics3D.method634(0.6D);
                    ((DefaultMaterialProvider) JagGraphics3D.materialProvider).setBrightness(0.6D);
                }

                ItemDefinition.sprites.clear();
            }

            if (value == 3) {
                short var4 = 0;
                if (varp == 0) {
                    var4 = 255;
                }

                if (varp == 1) {
                    var4 = 192;
                }

                if (varp == 2) {
                    var4 = 128;
                }

                if (varp == 3) {
                    var4 = 64;
                }

                if (varp == 4) {
                    var4 = 0;
                }

                if (var4 != client.anInt900) {
                    if (client.anInt900 == 0 && client.anInt898 != -1) {
                        Statics51.method344(Archive.audioTracks, client.anInt898, 0, var4, false);
                        client.aBoolean904 = false;
                    } else if (var4 == 0) {
                        Statics57.method533();
                        client.aBoolean904 = false;
                    } else if (AudioSystem.state != 0) {
                        AudioSystem.volume = var4;
                    } else {
                        Statics50.aClass5_Sub6_Sub3_326.method703(var4);
                    }

                    client.anInt900 = var4;
                }
            }

            if (value == 4) {
                if (varp == 0) {
                    client.anInt901 = 127;
                }

                if (varp == 1) {
                    client.anInt901 = 96;
                }

                if (varp == 2) {
                    client.anInt901 = 64;
                }

                if (varp == 3) {
                    client.anInt901 = 32;
                }

                if (varp == 4) {
                    client.anInt901 = 0;
                }
            }

            if (value == 5) {
                client.varpControlledInt1 = varp;
            }

            if (value == 6) {
                client.anInt1045 = varp;
            }

            if (value == 9) {
                client.anInt1054 = varp;
            }

            if (value == 10) {
                if (varp == 0) {
                    client.anInt897 = 127;
                }

                if (varp == 1) {
                    client.anInt897 = 96;
                }

                if (varp == 2) {
                    client.anInt897 = 64;
                }

                if (varp == 3) {
                    client.anInt897 = 32;
                }

                if (varp == 4) {
                    client.anInt897 = 0;
                }
            }

            if (value == 17) {
                client.anInt1053 = varp & 65535;
            }

            if (value == 18) {
                client.playerAttackOptionPriority = (AttackOptionPriority) EnumType.getByOrdinal(AttackOptionPriority.getValues(), varp);
                if (client.playerAttackOptionPriority == null) {
                    client.playerAttackOptionPriority = AttackOptionPriority.DEPENDS;
                }
            }

            if (value == 19) {
                if (varp == -1) {
                    client.varpControlledInt2 = -1;
                } else {
                    client.varpControlledInt2 = varp & 2047;
                }
            }

            if (value == 22) {
                client.npcAttackOptionPriority = (AttackOptionPriority) EnumType.getByOrdinal(AttackOptionPriority.getValues(), varp);
                if (client.npcAttackOptionPriority == null) {
                    client.npcAttackOptionPriority = AttackOptionPriority.DEPENDS;
                }
            }

        }
    }

    public static String method714(String var0) {
        int var1 = var0.length();
        char[] var2 = new char[var1];
        byte var3 = 2;

        for (int var4 = 0; var4 < var1; ++var4) {
            char var5 = var0.charAt(var4);
            if (var3 == 0) {
                var5 = Character.toLowerCase(var5);
            } else if (var3 == 2 || Character.isUpperCase(var5)) {
                var5 = URLRequestProcessor.toTitleCase(var5);
            }

            if (Character.isLetter(var5)) {
                var3 = 0;
            } else if (var5 != '.' && var5 != '?' && var5 != '!') {
                if (Character.isSpaceChar(var5)) {
                    if (var3 != 2) {
                        var3 = 1;
                    }
                } else {
                    var3 = 1;
                }
            } else {
                var3 = 2;
            }

            var2[var4] = var5;
        }

        return new String(var2);
    }

    void method715(byte[] var1, int var2, int var3) throws IOException {
        if (!this.aBoolean879) {
            if (this.aBoolean872) {
                this.aBoolean872 = false;
                throw new IOException();
            }
            if (this.aByteArray877 == null) {
                this.aByteArray877 = new byte[this.anInt871];
            }

            synchronized (this) {
                for (int var5 = 0; var5 < var3; ++var5) {
                    this.aByteArray877[this.anInt881] = var1[var5 + var2];
                    this.anInt881 = (this.anInt881 + 1) % this.anInt871;
                    if ((this.anInt870 + this.anInt874) % this.anInt871 == this.anInt881) {
                        throw new IOException();
                    }
                }

                if (this.aOldConnectionTask_876 == null) {
                    this.aOldConnectionTask_876 = this.aOldConnectionTaskProcessor_878.method697(this, 3);
                }

                this.notifyAll();
            }
        }
    }

    public void stop() {
        if (!this.aBoolean879) {
            synchronized (this) {
                this.aBoolean879 = true;
                this.notifyAll();
            }

            if (this.aOldConnectionTask_876 != null) {
                while (this.aOldConnectionTask_876.state == 0) {
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException ignored) {
                    }
                }

                if (this.aOldConnectionTask_876.state == 1) {
                    try {
                        ((Thread) this.aOldConnectionTask_876.result).join();
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            this.aOldConnectionTask_876 = null;
        }
    }

    public boolean available(int amount) throws IOException {
        if (this.aBoolean879) {
            return false;
        }
        return this.input.available() >= amount;
    }

    public int read(byte[] payload, int caret, int length) throws IOException {
        if (this.aBoolean879) {
            return 0;
        }
        int var4;
        int var5;
        for (var4 = length; length > 0; length -= var5) {
            var5 = this.input.read(payload, caret, length);
            if (var5 <= 0) {
                throw new EOFException();
            }

            caret += var5;
        }

        return var4;
    }

    public void write(byte[] payload, int caret, int length) throws IOException {
        this.method715(payload, caret, length);
    }

    public int available() throws IOException {
        return this.aBoolean879 ? 0 : this.input.available();
    }

    public int read() throws IOException {
        return this.aBoolean879 ? 0 : this.input.read();
    }

    protected void finalize() {
        this.stop();
    }

    public void run() {
        try {
            while (true) {
                label84:
                {
                    int var3;
                    int var4;
                    synchronized (this) {
                        if (this.anInt874 == this.anInt881) {
                            if (this.aBoolean879) {
                                break label84;
                            }

                            try {
                                this.wait();
                            } catch (InterruptedException ignored) {
                            }
                        }

                        var3 = this.anInt874;
                        if (this.anInt881 >= this.anInt874) {
                            var4 = this.anInt881 - this.anInt874;
                        } else {
                            var4 = this.anInt871 - this.anInt874;
                        }
                    }

                    if (var4 <= 0) {
                        continue;
                    }

                    try {
                        this.output.write(this.aByteArray877, var3, var4);
                    } catch (IOException var9) {
                        this.aBoolean872 = true;
                    }

                    this.anInt874 = (var4 + this.anInt874) % this.anInt871;

                    try {
                        if (this.anInt881 == this.anInt874) {
                            this.output.flush();
                        }
                    } catch (IOException var8) {
                        this.aBoolean872 = true;
                    }
                    continue;
                }

                try {
                    if (this.input != null) {
                        this.input.close();
                    }

                    if (this.output != null) {
                        this.output.close();
                    }

                    if (this.socket != null) {
                        this.socket.close();
                    }
                } catch (IOException ignored) {
                }

                this.aByteArray877 = null;
                break;
            }
        } catch (Exception var12) {
            client.sendError(null, var12);
        }

    }
}
