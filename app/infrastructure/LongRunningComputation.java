package infrastructure;

import play.libs.F;

import java.math.BigInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LongRunningComputation {

    public String computeFibonacci(int limit) {
        F.Tuple<BigInteger, BigInteger> seed = new F.Tuple<>(BigInteger.ONE, BigInteger.ONE);
        UnaryOperator<F.Tuple<BigInteger, BigInteger>> f = x -> new F.Tuple<>(x._2, x._1.add(x._2));
        Stream<BigInteger> fiboStream = Stream.iterate(seed, f)
                .map(x -> x._1)
                .limit(limit);
        return fiboStream.map(BigInteger::toString).collect(Collectors.joining(", "));
    }

}
