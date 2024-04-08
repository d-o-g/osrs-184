package jagex.messaging;

public final class IsaacCipher {

  public final int[] memory;
  public final int[] results;
  public int last;
  public int count;
  public int ptr;
  public int accumulator;

  public IsaacCipher(int[] seed) {
    memory = new int[256];
    results = new int[256];

    System.arraycopy(seed, 0, results, 0, seed.length);

    initializeKeySet();
  }

  public void proceed() {
    last += ++ptr;

    for (int i = 0; i < 256; ++i) {
      int value = memory[i];
      if ((i & 2) == 0) {
        if ((i & 1) == 0) {
          accumulator ^= accumulator << 13;
        } else {
          accumulator ^= accumulator >>> 6;
        }
      } else if ((i & 1) == 0) {
        accumulator ^= accumulator << 2;
      } else {
        accumulator ^= accumulator >>> 16;
      }

      accumulator += memory[128 + i & 255];
      int var3;
      memory[i] = var3 = memory[(value & 1020) >> 2] + last + accumulator;
      results[i] = last = memory[(var3 >> 8 & 1020) >> 2] + value;
    }

  }

  public void initializeKeySet() {
    int mem7 = -1640531527;
    int mem6 = -1640531527;
    int mem5 = -1640531527;
    int mem4 = -1640531527;
    int mem3 = -1640531527;
    int mem2 = -1640531527;
    int mem1 = -1640531527;
    int mem0 = -1640531527;

    for (int i = 0; i < 4; ++i) {
      mem0 ^= mem1 << 11;
      mem3 += mem0;
      mem1 += mem2;
      mem1 ^= mem2 >>> 2;
      mem4 += mem1;
      mem2 += mem3;
      mem2 ^= mem3 << 8;
      mem5 += mem2;
      mem3 += mem4;
      mem3 ^= mem4 >>> 16;
      mem6 += mem3;
      mem4 += mem5;
      mem4 ^= mem5 << 10;
      mem7 += mem4;
      mem5 += mem6;
      mem5 ^= mem6 >>> 4;
      mem0 += mem5;
      mem6 += mem7;
      mem6 ^= mem7 << 8;
      mem1 += mem6;
      mem7 += mem0;
      mem7 ^= mem0 >>> 9;
      mem2 += mem7;
      mem0 += mem1;
    }

    for (int i = 0; i < 256; i += 8) {
      mem0 += results[i];
      mem1 += results[i + 1];
      mem2 += results[i + 2];
      mem3 += results[i + 3];
      mem4 += results[i + 4];
      mem5 += results[i + 5];
      mem6 += results[i + 6];
      mem7 += results[i + 7];
      mem0 ^= mem1 << 11;
      mem3 += mem0;
      mem1 += mem2;
      mem1 ^= mem2 >>> 2;
      mem4 += mem1;
      mem2 += mem3;
      mem2 ^= mem3 << 8;
      mem5 += mem2;
      mem3 += mem4;
      mem3 ^= mem4 >>> 16;
      mem6 += mem3;
      mem4 += mem5;
      mem4 ^= mem5 << 10;
      mem7 += mem4;
      mem5 += mem6;
      mem5 ^= mem6 >>> 4;
      mem0 += mem5;
      mem6 += mem7;
      mem6 ^= mem7 << 8;
      mem1 += mem6;
      mem7 += mem0;
      mem7 ^= mem0 >>> 9;
      mem2 += mem7;
      mem0 += mem1;
      memory[i] = mem0;
      memory[i + 1] = mem1;
      memory[i + 2] = mem2;
      memory[i + 3] = mem3;
      memory[i + 4] = mem4;
      memory[i + 5] = mem5;
      memory[i + 6] = mem6;
      memory[i + 7] = mem7;
    }

    for (int i = 0; i < 256; i += 8) {
      mem0 += memory[i];
      mem1 += memory[i + 1];
      mem2 += memory[i + 2];
      mem3 += memory[i + 3];
      mem4 += memory[i + 4];
      mem5 += memory[i + 5];
      mem6 += memory[i + 6];
      mem7 += memory[i + 7];
      mem0 ^= mem1 << 11;
      mem3 += mem0;
      mem1 += mem2;
      mem1 ^= mem2 >>> 2;
      mem4 += mem1;
      mem2 += mem3;
      mem2 ^= mem3 << 8;
      mem5 += mem2;
      mem3 += mem4;
      mem3 ^= mem4 >>> 16;
      mem6 += mem3;
      mem4 += mem5;
      mem4 ^= mem5 << 10;
      mem7 += mem4;
      mem5 += mem6;
      mem5 ^= mem6 >>> 4;
      mem0 += mem5;
      mem6 += mem7;
      mem6 ^= mem7 << 8;
      mem1 += mem6;
      mem7 += mem0;
      mem7 ^= mem0 >>> 9;
      mem2 += mem7;
      mem0 += mem1;
      memory[i] = mem0;
      memory[i + 1] = mem1;
      memory[i + 2] = mem2;
      memory[i + 3] = mem3;
      memory[i + 4] = mem4;
      memory[i + 5] = mem5;
      memory[i + 6] = mem6;
      memory[i + 7] = mem7;
    }

    proceed();
    count = 256;
  }

  public int pop() {
    if (count == 0) {
      proceed();
      count = 256;
    }

    return results[--count];
  }

  public int peek() {
    if (count == 0) {
      proceed();
      count = 256;
    }

    return results[count - 1];
  }
}
