package services;

import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.*;

@Singleton
public class AtomicCounter implements Counter {

    private final AtomicInteger atomicCounter = new AtomicInteger();

    @Override
    public int nextCount() {
       return atomicCounter.getAndIncrement();
    }

}
