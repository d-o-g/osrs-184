package jagex.jagex3.js5;

public class IdentityTable {

  private final int[] table;

  public IdentityTable(int[] values) {
    int tableSize = calculateTableSize(values.length);
    table = new int[tableSize];

    initializeTable(tableSize);

    for (int value : values) {
      int index = findEmptyIndex(value, tableSize);
      table[index] = value;
    }
  }

  public int get(int identifier) {
    int tableSize = (table.length >> 1) - 1;
    int position = identifier & tableSize;

    while (true) {
      int current = table[position + position + 1];
      if (current == -1) {
        return -1;
      }

      if (table[position + position] == identifier) {
        return current;
      }

      position = (position + 1) & tableSize;
    }
  }

  private int calculateTableSize(int length) {
    int size = 1;
    while (size <= (length >> 1) + length) {
      size <<= 1;
    }
    return size + size;
  }

  private void initializeTable(int size) {
    for (int i = 0; i < size; ++i) {
      table[i] = -1;
    }
  }

  private int findEmptyIndex(int value, int tableSize) {
    int position = value & (tableSize - 1);
    while (table[position + position + 1] != -1) {
      position = (position + 1) & (tableSize - 1);
    }

    return position + position;
  }
}