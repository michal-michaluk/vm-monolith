package glue;

import external.CurrentStock;

public class StockClient {

    // in future replace with
    // private final StockService stocks
    private final CurrentStock stock;

    public StockClient(CurrentStock stock) {
        this.stock = stock;
    }

    public long get(String productRefNo) {
        return stock.getLevel();
    }
}
