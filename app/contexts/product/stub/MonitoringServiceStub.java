package contexts.product.stub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitoringServiceStub {
    private Map<String, AtomicInteger> responseCountMetrics = new HashMap<>();

    public void incrementResponseCode(int code) {
        responseCountMetrics.getOrDefault(code, new AtomicInteger(0)).getAndIncrement();
    }
}