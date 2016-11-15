package services;

import java.math.BigInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LongRunningComputation {

    public String computeFibonacci(int limit) {
        Tuple<BigInteger, BigInteger> seed = new Tuple<>(BigInteger.ONE, BigInteger.ONE);
        UnaryOperator<Tuple<BigInteger, BigInteger>> f = x -> new Tuple<>(x._2, x._1.add(x._2));
        Stream<BigInteger> fiboStream = Stream.iterate(seed, f)
                .map(x -> x._1)
                .limit(limit);
        return fiboStream.map(BigInteger::toString).collect(Collectors.joining(", "));
    }

    protected class Tuple<T, U> {
        public final T _1;
        public final U _2;
        public Tuple(T t, U u) {
            this._1 = t;
            this._2 = u;
        }
    }

}
