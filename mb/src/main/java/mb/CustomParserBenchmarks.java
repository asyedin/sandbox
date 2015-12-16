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
        System.err.println("setup");
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

    public static void main1(String... args) throws RunnerException {
        PrintWriter pw = new PrintWriter(System.out, true);
        for (int b = 1; b <= 1048576; b *= 2) {
            for (int t = 1; t <= Runtime.getRuntime().availableProcessors(); t++) {
                runWith(pw, t, b);
            }
        }
    }

    private static void runWith(PrintWriter pw, int threads, int backoff) throws RunnerException {
        Options opts = new OptionsBuilder()
//                .include(".*" + CustomParserBenchmarks.class.getName() + ".*")
                .include(CustomParserBenchmarks.class.getSimpleName())
                .threads(threads)
                .verbosity(VerboseMode.SILENT)
                .param("backoff", String.valueOf(backoff))
                .build();

        RunResult r = new Runner(opts).runSingle();
        double score = r.getPrimaryResult().getScore();
        double scoreError = r.getPrimaryResult().getStatistics().getMeanErrorAt(0.99);
        pw.printf("%6d, %3d, %11.3f, %10.3f%n", backoff, threads, score, scoreError);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CustomParserBenchmarks.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
