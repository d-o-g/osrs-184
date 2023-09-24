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
    int var1 = -1640531527;
    int var2 = -1640531527;
    int var3 = -1640531527;
    int var4 = -1640531527;
    int var5 = -1640531527;
    int var6 = -1640531527;
    int var7 = -1640531527;
    int var8 = -1640531527;

    int var9;
    for (var9 = 0; var9 < 4; ++var9) {
      var8 ^= var7 << 11;
      var5 += var8;
      var7 += var6;
      var7 ^= var6 >>> 2;
      var4 += var7;
      var6 += var5;
      var6 ^= var5 << 8;
      var3 += var6;
      var5 += var4;
      var5 ^= var4 >>> 16;
      var2 += var5;
      var4 += var3;
      var4 ^= var3 << 10;
      var1 += var4;
      var3 += var2;
      var3 ^= var2 >>> 4;
      var8 += var3;
      var2 += var1;
      var2 ^= var1 << 8;
      var7 += var2;
      var1 += var8;
      var1 ^= var8 >>> 9;
      var6 += var1;
      var8 += var7;
    }

    for (var9 = 0; var9 < 256; var9 += 8) {
      var8 += results[var9];
      var7 += results[var9 + 1];
      var6 += results[var9 + 2];
      var5 += results[var9 + 3];
      var4 += results[var9 + 4];
      var3 += results[var9 + 5];
      var2 += results[var9 + 6];
      var1 += results[var9 + 7];
      var8 ^= var7 << 11;
      var5 += var8;
      var7 += var6;
      var7 ^= var6 >>> 2;
      var4 += var7;
      var6 += var5;
      var6 ^= var5 << 8;
      var3 += var6;
      var5 += var4;
      var5 ^= var4 >>> 16;
      var2 += var5;
      var4 += var3;
      var4 ^= var3 << 10;
      var1 += var4;
      var3 += var2;
      var3 ^= var2 >>> 4;
      var8 += var3;
      var2 += var1;
      var2 ^= var1 << 8;
      var7 += var2;
      var1 += var8;
      var1 ^= var8 >>> 9;
      var6 += var1;
      var8 += var7;
      memory[var9] = var8;
      memory[var9 + 1] = var7;
      memory[var9 + 2] = var6;
      memory[var9 + 3] = var5;
      memory[var9 + 4] = var4;
      memory[var9 + 5] = var3;
      memory[var9 + 6] = var2;
      memory[var9 + 7] = var1;
    }

    for (var9 = 0; var9 < 256; var9 += 8) {
      var8 += memory[var9];
      var7 += memory[var9 + 1];
      var6 += memory[var9 + 2];
      var5 += memory[var9 + 3];
      var4 += memory[var9 + 4];
      var3 += memory[var9 + 5];
      var2 += memory[var9 + 6];
      var1 += memory[var9 + 7];
      var8 ^= var7 << 11;
      var5 += var8;
      var7 += var6;
      var7 ^= var6 >>> 2;
      var4 += var7;
      var6 += var5;
      var6 ^= var5 << 8;
      var3 += var6;
      var5 += var4;
      var5 ^= var4 >>> 16;
      var2 += var5;
      var4 += var3;
      var4 ^= var3 << 10;
      var1 += var4;
      var3 += var2;
      var3 ^= var2 >>> 4;
      var8 += var3;
      var2 += var1;
      var2 ^= var1 << 8;
      var7 += var2;
      var1 += var8;
      var1 ^= var8 >>> 9;
      var6 += var1;
      var8 += var7;
      memory[var9] = var8;
      memory[var9 + 1] = var7;
      memory[var9 + 2] = var6;
      memory[var9 + 3] = var5;
      memory[var9 + 4] = var4;
      memory[var9 + 5] = var3;
      memory[var9 + 6] = var2;
      memory[var9 + 7] = var1;
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
