package tools;

import entities.DemandEntity;
import entities.ProductionEntity;
import entities.ShortageEntity;
import external.CurrentStock;
import glue.DemandsClient;
import glue.ProductionClient;
import glue.ShortageForecastMonolithRepository;
import glue.StockClient;
import shortages.ShortageForecast;
import shortages.ShortageForecastRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ShortageFinder {

    private ShortageFinder() {
    }

    /**
     * Production at day of expected delivery is quite complex:
     * We are able to produce and deliver just in time at same day
     * but depending on delivery time or scheme of multiple deliveries,
     * we need to plan properly to have right amount of parts ready before delivery time.
     * <p/>
     * Typical schemas are:
     * <li>Delivery at prod day start</li>
     * <li>Delivery till prod day end</li>
     * <li>Delivery during specified shift</li>
     * <li>Multiple deliveries at specified times</li>
     * Schema changes the way how we calculate shortages.
     * Pick of schema depends on customer demand on daily basis and for each product differently.
     * Some customers includes that information in callof document,
     * other stick to single schema per product. By manual adjustments of demand,
     * customer always specifies desired delivery schema
     * (increase amount in scheduled transport or organize extra transport at given time)
     */
    public static List<ShortageEntity> findShortages(LocalDate today, int daysAhead, CurrentStock stock,
                                                     List<ProductionEntity> productionEntities, List<DemandEntity> demandEntities) {

        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .collect(toList());
        String productRefNo = productionEntities.stream()
                .map(production -> production.getForm().getRefNo()).findAny()
                .orElse(null);

        // temporal manual dependencies creation to keep unchanged api of findShortages method
        // in future replace with proper dependency injection:
        ProductionClient productions = new ProductionClient(productionEntities);
        DemandsClient demands = new DemandsClient(demandEntities);
        StockClient stocks = new StockClient(stock);
        ShortageForecastRepository repository = new ShortageForecastMonolithRepository(productions, demands, stocks);

        ShortageForecast forecast = repository.get(productRefNo);
        return forecast.predictShortages(dates);
    }

}
