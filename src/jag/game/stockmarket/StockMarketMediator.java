package jag.game.stockmarket;

import jag.opcode.Buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StockMarketMediator {

    public static final Comparator<StockMarketEvent> aComparator610 = new StockMarketOfferLifetimeComparator();
    public static final Comparator<StockMarketEvent> aComparator608 = new StockMarketOfferPriceComparator();
    public static final Comparator<StockMarketEvent> aComparator607 = new StockMarketOfferNameComparator();
    public static final Comparator<StockMarketEvent> aComparator606 = new StockMarketOfferQuantityComparator();

    public final List<StockMarketEvent> events;

    public StockMarketMediator(Buffer buffer) {
        int value = buffer.g2();
        boolean active = buffer.g1() == 1;
        byte bool;
        if (active) {
            bool = 1;
        } else {
            bool = 0;
        }

        int size = buffer.g2();
        this.events = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            this.events.add(new StockMarketEvent(buffer, bool, value));
        }
    }

    public void sort(Comparator<StockMarketEvent> comparator, boolean ascending) {
        if (ascending) {
            Collections.sort(this.events, comparator);
        } else {
            Collections.sort(this.events, Collections.reverseOrder(comparator));
        }

    }
}
