import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Joseph Yeh
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    private Reader input;
    private String before;
    private String after;
    public TrReader(Reader str, String from, String to) throws IOException {
        assert from.length() == to.length();
        input = str;
        before = from;
        after = to;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int count = input.read(cbuf, off, len);
        for (int i = off; i < count + off; i++) {
            int n = before.length();
            for (int j = 0; j < n; j++)
                if (cbuf[i] == before.charAt(j)) {
                    cbuf[i] = after.charAt(j);
                    break;
                }
        }
        return count;
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

    /* TODO: IMPLEMENT ANY MISSING ABSTRACT METHODS HERE
     * NOTE: Until you fill in the necessary methods, the compiler will
     *       reject this file, saying that you must declare TrReader
     *       abstract. Don't do that; define the right methods instead!
     */

}
