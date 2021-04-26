package glue;

import entities.ProductionEntity;
import shortages.ProductionOutputs;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionClient {

    // in future replace with
    // private final ProductionDao productions
    private final List<ProductionEntity> productions;

    public ProductionClient(List<ProductionEntity> productions) {
        this.productions = productions;
    }

    public ProductionOutputs get(String productRefNo) {
        Map<LocalDate, Long> outputs = productions.stream()
                .collect(Collectors.groupingBy(
                        production -> production.getStart().toLocalDate(),
                        Collectors.summingLong(ProductionEntity::getOutput)
                ));
        return new ProductionOutputs(outputs, productRefNo);
    }
}
