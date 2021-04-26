package glue;

import shortages.ShortageForecast;
import shortages.ShortageForecastRepository;

public class ShortageForecastMonolithRepository implements ShortageForecastRepository {
    private final ProductionClient productions;
    private final DemandsClient demands;
    private final StockClient stocks;

    public ShortageForecastMonolithRepository(ProductionClient productions, DemandsClient demands, StockClient stocks) {
        this.productions = productions;
        this.demands = demands;
        this.stocks = stocks;
    }

    @Override
    public ShortageForecast get(String refNo) {
        return new ShortageForecast(
                stocks.get(refNo),
                productions.get(refNo),
                demands.get(refNo)
        );
    }
}
