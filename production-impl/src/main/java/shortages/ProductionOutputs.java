package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionOutputs {
    private final String productRefNo;
    private final Map<LocalDate, List<ProductionEntity>> outputs;

    public ProductionOutputs(List<ProductionEntity> productions) {
        String productRefNo = null;
        Map<LocalDate, List<ProductionEntity>> outputs = new HashMap<>();
        for (ProductionEntity production : productions) {
            if (!outputs.containsKey(production.getStart().toLocalDate())) {
                outputs.put(production.getStart().toLocalDate(), new ArrayList<>());
            }
            outputs.get(production.getStart().toLocalDate()).add(production);
            productRefNo = production.getForm().getRefNo();
        }
        this.productRefNo = productRefNo;
        this.outputs = outputs;
    }

    public long get(LocalDate day) {
        long level = 0;
        for (ProductionEntity production : outputs.get(day)) {
            level = production.getOutput();
        }
        return level;
    }

    public String getProductRefNo() {
        return productRefNo;
    }
}
