package mb;

public class CustomParser {

    /**
     * Parses the string argument as a signed {@code long} using ten as the radix.
     * The characters in the string must all be digits from 0 to 9,
     * except that the first character may be an
     * ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to
     * indicate a negative value.
     * <p>
     * This implementation doesn't perform any sanity check on the input value to minimize
     * the computational overhead.
     *
     * @param value the {@code String} containing the
     *              {@code long} representation to be parsed.
     * @return the {@code long} represented by the string argument.
     * @see java.lang.Long#parseLong(String)
     */
    public static long parseLong(String value) {
        //accumulate as negative to correctly process Long.MIN_VALUE
        long a = 0;
        //so the result must be negated...
        boolean shouldNegate = true;

        char c0 = value.charAt(0);
        if (c0 == '-') {
            //...unless it's < 0
            shouldNegate = false;
        } else {
            a = ('0' - c0);
        }

        for (int i = 1; i < value.length(); i++) {
            a = a * 10 + ('0' - value.charAt(i));
        }


        // accumulated as negative!
        return shouldNegate ? -a : a;
    }

}
