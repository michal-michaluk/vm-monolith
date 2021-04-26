package glue;

import entities.DemandEntity;
import shortages.Demands;
import tools.Util;

import java.util.List;
import java.util.stream.Collectors;

public class DemandsClient {

    // in future replace with
    // private final DemandDao demands
    private final List<DemandEntity> demands;

    public DemandsClient(List<DemandEntity> demands) {
        this.demands = demands;
    }

    public Demands get(String productionRefNo) {
        return new Demands(demands.stream()
                .collect(Collectors.toMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(Util.getLevel(demand), Util.getDeliverySchema(demand))
                ))
        );
    }
}
