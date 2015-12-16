package mb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CustomParserTest {

    @Parameterized.Parameters(name = "{index}: parse({0})={1}")
    public static Iterable<Object[]> data() {
        final int n = 1000;
        final long[] src = new long[n];
        src[0] = 0;
        src[1] = Long.MAX_VALUE;
        src[2] = Long.MIN_VALUE;

        Random r = new Random();
        for (int i = 3; i < n; i++) {
            src[i] = r.nextLong();
        }

        return new Iterable<Object[]>() {
            public Iterator<Object[]> iterator() {
                return new Iterator<Object[]>() {
                    int pos = 0;

                    public boolean hasNext() {
                        return pos < n;
                    }

                    public Object[] next() {
                        long val = src[pos++];
                        return new Object[]{String.valueOf(val), val};
                    }
                };
            }
        };
    }

    private String input;
    private long expected;

    public CustomParserTest(String input, long expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void test() {
        assertEquals(expected, CustomParser.parseLong(input));
    }

}
