package jagex.oldscape.stockmarket;

import jagex.messaging.Buffer;

public class StockmarketListing {

  public static final int STATE_MASK = 0x7;
  public static final int TYPE_MASK = 0x8;
  public static final int ACTIVE_TYPE_MASK = -0x9;
  public static StockmarketManager manager;
  public static long ageAdjustment;
  public int unitPrice;
  public int quantity;
  public int itemId;
  public int transferred;
  public int spent;
  public byte state;

  public StockmarketListing() {

  }

  public StockmarketListing(Buffer var1) {
    this.state = var1.g1b();
    this.itemId = var1.g2();
    this.unitPrice = var1.g4();
    this.quantity = var1.g4();
    this.transferred = var1.g4();
    this.spent = var1.g4();
  }

  public void setInitialState() {
    this.state &= -TYPE_MASK;
    this.state |= STATE_MASK & 0x2;
  }

  public int getType() {
    return (this.state & TYPE_MASK) == TYPE_MASK ? 1 : 0;
  }

  public void setType(int type) {
    this.state &= ACTIVE_TYPE_MASK;
    if (type == 1) {
      this.state |= TYPE_MASK;
    }
  }

  public int getState() {
    return this.state & STATE_MASK;
  }
}
