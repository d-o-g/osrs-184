package jag.opcode.login.pow;

import jag.opcode.Buffer;

public class ProofOfWorkRequest {

    final int seedPrefix;
    final int requiredZeroBitCount;
    final String seedPostfix;

    public ProofOfWorkRequest(Buffer buffer) {
        this(buffer.g1(), buffer.g1(), buffer.gstr());
    }

    public ProofOfWorkRequest(int seedPrefix, int requiredZeroBitCount, String seedPostfix) {
        this.seedPrefix = seedPrefix;
        this.requiredZeroBitCount = requiredZeroBitCount;
        this.seedPostfix = seedPostfix;
    }

    public String getSeed() {
        return Integer.toHexString(seedPrefix) + Integer.toHexString(requiredZeroBitCount) + seedPostfix;
    }

    public int getRequiredZeroBitCount() {
        return requiredZeroBitCount;
    }
}
