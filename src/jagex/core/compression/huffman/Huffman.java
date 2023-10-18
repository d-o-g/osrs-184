package jagex.core.compression.huffman;

public class Huffman {

  private static final int BITS_PER_BYTE = 8;

  public static Huffman instance;

  public final int[] masks;
  public final byte[] bits;
  public int[] keys;

  public Huffman(byte[] bits) {
    this.masks = new int[bits.length];
    this.keys = new int[8];
    this.bits = bits;

    buildTree(bits);
  }

  private void buildTree(byte[] bits) {
    int[] cache = new int[33];
    int maxKey = 0;

    for (int i = 0; i < bits.length; ++i) {
      byte bit = bits[i];

      if (bit != 0) {
        int mask = cache[bit];
        masks[i] = mask;
        int newMask;

        if ((mask & (1 << (32 - bit))) != 0) {
          newMask = cache[bit - 1];
        } else {
          newMask = mask | (1 << (32 - bit));

          for (int j = bit - 1; j >= 1; --j) {
            int temp = cache[j];

            if (mask != temp) {
              break;
            }

            int bitMask = 1 << (32 - j);

            if ((temp & bitMask) != 0) {
              cache[j] = cache[j - 1];
              break;
            }

            cache[j] = temp | bitMask;
          }
        }

        cache[bit] = newMask;

        for (int j = bit + 1; j <= 32; ++j) {
          if (mask == cache[j]) {
            cache[j] = newMask;
          }
        }

        int key = assignKey(bit, mask);
        keys[key] = ~i;

        if (key >= maxKey) {
          maxKey = key + 1;
        }
      }
    }
  }

  private int assignKey(int bit, int mask) {
    int key = 0;

    for (int j = 0; j < bit; ++j) {
      int bitMask = Integer.MIN_VALUE >>> j;

      if ((mask & bitMask) != 0) {
        if (keys[key] == 0) {
          keys[key] = keys.length;
        }

        key = keys[key];
      } else {
        ++key;

        if (key >= keys.length) {
          expandKeysArray();
        }
      }
    }

    return key;
  }

  private void expandKeysArray() {
    int[] newKeys = new int[keys.length * 2];
    System.arraycopy(keys, 0, newKeys, 0, keys.length);
    keys = newKeys;
  }

  public int decompress(byte[] input, int inputOffset, byte[] output, int outputOffset, int length) {
    int inputBitOffset = inputOffset * BITS_PER_BYTE;
    int outputBitOffset = outputOffset * BITS_PER_BYTE;

    int bytesRead = 0;

    while (bytesRead < length) {
      int currentByte = inputBitOffset / BITS_PER_BYTE;
      int currentBit = inputBitOffset % BITS_PER_BYTE;

      int code = 0;
      int codeLength = 0;

      while (true) {
        if (currentByte >= input.length) {
          break;
        }

        code = (code << 1) | ((input[currentByte] >> (7 - currentBit)) & 1);
        currentBit++;

        if (currentBit == BITS_PER_BYTE) {
          currentBit = 0;
          currentByte++;
        }

        codeLength++;

        int symbol = masks[code];
        if (symbol != 0) {
          int bitsToRead = codeLength - bits[symbol];
          code = symbol;
          outputBitOffset = writeBits(output, outputBitOffset, code, bitsToRead);
          bytesRead++;
          break;
        }
      }

      inputBitOffset += codeLength;
    }

    return bytesRead;
  }

  public int compress(byte[] input, int inputOffset, int length, byte[] output, int outputOffset) {
    int inputBitOffset = inputOffset * BITS_PER_BYTE;
    int outputBitOffset = outputOffset * BITS_PER_BYTE;

    int bytesWritten = 0;

    while (bytesWritten < length) {
      int currentByte = inputBitOffset / BITS_PER_BYTE;
      int currentBit = inputBitOffset % BITS_PER_BYTE;

      int code = 0;
      int codeLength = 0;

      while (codeLength < 32) {
        int nextBit = ((input[currentByte] >> (7 - currentBit)) & 1);
        code = (code << 1) | nextBit;
        currentBit++;

        if (currentBit == BITS_PER_BYTE) {
          currentBit = 0;
          currentByte++;
        }

        codeLength++;

        int symbol = masks[code];
        if (symbol != 0) {
          int bitsToWrite = codeLength - bits[symbol];
          outputBitOffset = writeBits(output, outputBitOffset, symbol, bitsToWrite);
          bytesWritten++;
          break;
        }
      }

      inputBitOffset += codeLength;
    }

    return (outputBitOffset + BITS_PER_BYTE - 1) / BITS_PER_BYTE - outputOffset;
  }

  private int writeBits(byte[] buffer, int bitOffset, int value, int numBits) {
    for (int i = numBits - 1; i >= 0; i--) {
      int bit = (value >> i) & 1;
      buffer[bitOffset / BITS_PER_BYTE] |= (bit << (7 - bitOffset % BITS_PER_BYTE));
      bitOffset++;
    }

    return bitOffset;
  }
}
