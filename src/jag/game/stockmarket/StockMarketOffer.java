package jag.game.stockmarket;

import jag.game.InterfaceComponent;
import jag.opcode.Buffer;

public class StockMarketOffer {

    public static InterfaceComponent[] draggingInterface;

    public static StockMarketMediator mediator;

    public static long ageAdjustment;

    public int itemPrice;
    public int itemQuantity;
    public int itemId;
    public int transferred;
    public int spent;

    public byte state;

    public StockMarketOffer() {
    }

    public StockMarketOffer(Buffer var1) {
        this.state = var1.g1b();
        this.itemId = var1.g2();
        this.itemPrice = var1.g4();
        this.itemQuantity = var1.g4();
        this.transferred = var1.g4();
        this.spent = var1.g4();
    }

    public void method228() {
        this.state &= -8;
        this.state = (byte) (this.state | 2 & 7);
    }

    public void method227(int var1) {
        this.state &= -9;
        if (var1 == 1) {
            this.state = (byte) (this.state | 8);
        }

    }

    public int method229() {
        return (this.state & 8) == 8 ? 1 : 0;
    }

    public int method230() {
        return this.state & 7;
    }
}
