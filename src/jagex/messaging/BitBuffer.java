package jagex.messaging;

public class BitBuffer extends Buffer {

  static final int[] BITMASKS = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1};

  IsaacCipher cipher;
  int bitpos;

  public BitBuffer(int size) {
    super(size);
  }

  public void bitAccess() {
    bitpos = pos * 8;
  }

  public int g(int bits) {
    int index = bitpos >> 3;
    int shift = 8 - (bitpos & 7);
    int value = 0;

    for (bitpos += bits; bits > shift; shift = 8) {
      value += (payload[index++] & BITMASKS[shift]) << bits - shift;
      bits -= shift;
    }

    if (bits == shift) {
      value += payload[index] & BITMASKS[shift];
    } else {
      value += payload[index] >> shift - bits & BITMASKS[bits];
    }

    return value;
  }

  public boolean nextIsSmart() {
    return (payload[pos] - cipher.peek() & 255) >= 128;
  }

  public void byteAccess() {
    pos = (bitpos + 7) / 8;
  }

  public int remaining(int bytes) {
    return bytes * 8 - bitpos;
  }

  public void setCipher(IsaacCipher cipher) {
    this.cipher = cipher;
  }

  public int gheader() {
    return payload[++pos - 1] - cipher.pop() & 255;
  }

  public void pheader(int opcode) {
    payload[++pos - 1] = (byte) (opcode + cipher.pop());
  }

  public void ge(byte[] buffer, int pos, int len) {
    for (int i = 0; i < len; ++i) {
      buffer[i + pos] = (byte) (payload[++pos - 1] - cipher.pop());
    }
  }

  public int gesmart() {
    int value = payload[++pos - 1] - cipher.pop() & 255;
    return value < 128 ? value : (value - 128 << 8) + (payload[++pos - 1] - cipher.pop() & 255);
  }

  public void setCipher(int[] seed) {
    cipher = new IsaacCipher(seed);
  }
}
