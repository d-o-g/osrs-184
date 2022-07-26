package jag.commons;

public class Powers {

    static final int[] table;

    static {
        table = new int[33];
        table[0] = 0;
        int pow = 2;

        for (int i = 1; i < 33; ++i) {
            table[i] = pow - 1;
            pow += pow;
        }

    }
}
