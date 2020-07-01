package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
    }



    /* ***** TESTS ***** */

    @Test
    public void testDerangement() {
        Permutation p = new Permutation("(CBDE)", new Alphabet("BCDE"));
        assertEquals(true,  p.derangement());
        Permutation p1 = new Permutation("(BC) (E)       ",
                new Alphabet("BCDE"));
        assertEquals(false,  p1.derangement());

    }

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }
    @Test
    public void testInvertChar() {
        Permutation p = new Permutation("(CBDE)", new Alphabet("BCDE"));
        assertEquals('C', p.invert('B'));
        assertEquals('E', p.invert('C'));

        Permutation p1 = new Permutation("(BC) (E) (D)", new Alphabet("BCDE"));
        assertEquals('E', p1.invert('E'));

        Permutation p2 = new Permutation("(BC) (E)", new Alphabet("BCDE"));
        assertEquals('D', p2.invert('D'));

        Permutation p3 = new Permutation(" (BC) (E) ", new Alphabet("BCDE"));
        assertEquals('B', p3.invert('C'));

        Permutation p4 = new Permutation("(!@)", new Alphabet("@!"));
        assertEquals('@', p4.invert('!'));

        Permutation p5 = new Permutation(" (BC)             (E) ",
                new Alphabet("BCDE"));
        assertEquals('B', p5.invert('C'));


    }

    @Test
    public void testInvertInt() {
        Permutation p = new Permutation("(CBDE)", new Alphabet("BCDE"));
        assertEquals(3, p.invert(1));
        assertEquals(0, p.invert(6));

        Permutation p1 = new Permutation("(BC) (E) (D)", new Alphabet("BCDE"));
        assertEquals(3, p1.invert(3));






    }
    @Test
    public void testPermuteInt() {
        Permutation p = new Permutation("(CBDE)", new Alphabet("BCDE"));
        assertEquals(2, p.permute(0));
        assertEquals(3, p.permute(6));

        Permutation p1 = new Permutation("(BC) (E) (D)", new Alphabet("BCDE"));
        assertEquals(3, p1.permute(3));
        assertEquals(2, p1.permute(-2));
        assertEquals(0, p1.permute(1));




    }

    @Test
    public void sizeCheck() {
        Permutation p = new Permutation("(CBDE)", new Alphabet("BCDE"));
        assertEquals(4, p.size());
    }

    @Test
    public void testPermuteChar() {
        Permutation p = new Permutation("(CBDE)", new Alphabet("BCDE"));
        assertEquals('D', p.permute('B'));
        assertEquals('C', p.permute('E'));

        Permutation p1 = new Permutation("(BC) (E) (D)",
                new Alphabet("BCDE"));
        assertEquals('E', p1.permute('E'));
        Permutation p2 = new Permutation("(BC) (E)", new Alphabet("BCDE"));
        assertEquals('D', p2.permute('D'));

        Permutation p3 = new Permutation(" (BC) (E) ", new Alphabet("BCDE"));
        assertEquals('B', p3.permute('C'));

        Permutation p4 = new Permutation("(!@)", new Alphabet("@!"));
        assertEquals('@', p4.permute('!'));

        Permutation p5 = new Permutation(" (BC)             (E) ",
                new Alphabet("BCDE"));
        assertEquals('B', p5.permute('C'));




    }

}
