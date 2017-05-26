package infrastructure.monitoring.stub;

import infrastructure.monitoring.api.MonitoringService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * stub - look at something like Dropwizard Metrics w/ integration into external monitoring
 */

public class MonitoringServiceStub implements MonitoringService {
    private Map<String, AtomicInteger> responseCountMetrics = new HashMap<>();

    public void incrementResponseCode(int code) {
        responseCountMetrics.getOrDefault(code, new AtomicInteger(0)).getAndIncrement();
    }
}