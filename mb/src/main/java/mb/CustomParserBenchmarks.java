package mb;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(50)
public class CustomParserBenchmarks {

    private static final Random RND = new Random();
    private String asString;

    @Setup(Level.Iteration)
    public void init() {
        asString = String.valueOf(RND.nextLong());
    }


    @Benchmark
    public long longParselong() {
        long result = Long.parseLong(asString);
        return result;
    }

    @Benchmark
    public long customParseLong() {
        long result = CustomParser.parseLong(asString);
        return result;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CustomParserBenchmarks.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
