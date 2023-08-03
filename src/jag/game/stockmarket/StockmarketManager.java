package jag.game.stockmarket;

import jag.opcode.Buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StockmarketManager {

    public static final Comparator<StockmarketEvent> lifetimeComparator = new StockmarketListingLifetimeComparator();
    public static final Comparator<StockmarketEvent> priceComparator = new StockmarketListingPriceComparator();
    public static final Comparator<StockmarketEvent> nameComparator = new StockmarketListingNameComparator();
    public static final Comparator<StockmarketEvent> quantityComparator = new StockmarketListingQuantityComparator();

    public final List<StockmarketEvent> events;

    public StockmarketManager(Buffer buffer) {
        int value = buffer.g2();
        boolean sell = buffer.g1() == 1;
        byte type;
        if (sell) {
            type = 1;
        } else {
            type = 0;
        }

        int size = buffer.g2();
        this.events = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            this.events.add(new StockmarketEvent(buffer, type, value));
        }
    }

    public void sort(Comparator<StockmarketEvent> comparator, boolean ascending) {
        if (ascending) {
            this.events.sort(comparator);
        } else {
            this.events.sort(Collections.reverseOrder(comparator));
        }

    }
}
