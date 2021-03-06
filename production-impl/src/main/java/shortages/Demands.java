package shortages;

import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DailyDemand> demandsPerDay;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demandsPerDay = demands;
    }

    public DailyDemand get(LocalDate day) {
        return demandsPerDay.getOrDefault(day, null);
    }

    public static class DailyDemand {
        private final long demand;
        private final DeliverySchema schema;

        public DailyDemand(long demand, DeliverySchema schema) {
            this.demand = demand;
            this.schema = schema;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            if (schema == DeliverySchema.atDayStart) {
                return level - demand;
            } else if (schema == DeliverySchema.tillEndOfDay) {
                return level - demand + produced;
            } else if (schema == DeliverySchema.every3hours) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
        }

        public long getLevel() {
            return demand;
        }
    }
}
