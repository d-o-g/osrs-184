package jag.opcode.login;

import jag.Bounds;
import jag.opcode.OldConnectionTask;
import jag.opcode.OutgoingPacketProtocol;

public class LoginProt implements OutgoingPacketProtocol {

    public static final LoginProt HANDSHAKE = new LoginProt(14);
    public static final LoginProt LOGIN = new LoginProt(16);
    public static final LoginProt CHANGE_SERVERS = new LoginProt(18);
    public static final LoginProt JS5 = new LoginProt(15);
    //Note: 19 was added which is ProofOfWork
    public static final LoginProt A_LOGIN_PROT_1995 = new LoginProt(27);
    public static final LoginProt[] VALUES;

    public static OldConnectionTask task;
    public static Bounds aBounds_846;

    static {
        VALUES = new LoginProt[32];
        LoginProt[] var0 = values();

        for (LoginProt aVar0 : var0) {
            VALUES[aVar0.value] = aVar0;
        }

    }

    public final int value;

    LoginProt(int value) {
        this.value = value;
    }

    public static LoginProt[] values() {
        return new LoginProt[]{LoginProt.LOGIN, LoginProt.JS5, LoginProt.A_LOGIN_PROT_1995, LoginProt.CHANGE_SERVERS, LoginProt.HANDSHAKE};
    }

}
