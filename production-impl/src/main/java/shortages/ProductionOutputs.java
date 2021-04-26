package shortages;

import java.time.LocalDate;
import java.util.Map;

public class ProductionOutputs {
    private final String productRefNo;
    private final Map<LocalDate, Long> outputs;

    public ProductionOutputs(Map<LocalDate, Long> outputs, String productRefNo) {
        this.productRefNo = productRefNo;
        this.outputs = outputs;
    }

    public long get(LocalDate day) {
        return outputs.getOrDefault(day, 0L);
    }

    public String getProductRefNo() {
        return productRefNo;
    }
}
