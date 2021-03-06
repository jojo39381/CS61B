package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }

    @Test
    public void testInvertChar() {
        Permutation p = getNewPermutation("(CBDE)", getNewAlphabet("BCDE"));
        assertEquals('C', p.invert('B'));
        assertEquals('E', p.invert('C'));

        Permutation p1 = getNewPermutation("(BC) (E) (D)", getNewAlphabet("BCDE"));
        assertEquals('E', p1.invert('E'));

        Permutation p2 = getNewPermutation("(BC) (E)", getNewAlphabet("BCDE"));
        assertEquals('D', p2.invert('D'));

        Permutation p3 = getNewPermutation(" (BC) (E) ", getNewAlphabet("BCDE"));
        assertEquals('B', p3.invert('C'));

        Permutation p4 = getNewPermutation("(!@)", getNewAlphabet("@!"));
        assertEquals('@', p4.invert('!'));

        Permutation p5 = getNewPermutation(" (BC)             (E) ", getNewAlphabet("BCDE"));
        assertEquals('B', p5.invert('C'));


    }

    @Test
    public void testInvertInt() {
        Permutation p = getNewPermutation("(CBDE)", getNewAlphabet("BCDE"));
        assertEquals(3, p.invert(1));
        assertEquals(0, p.invert(6));

        Permutation p1 = getNewPermutation("(BC) (E) (D)", getNewAlphabet("BCDE"));
        assertEquals(3, p1.invert(3));






    }
    @Test
    public void testPermuteInt() {
        Permutation p = getNewPermutation("(CBDE)", getNewAlphabet("BCDE"));
        assertEquals(2, p.permute(0));
        assertEquals(3, p.permute(6));

        Permutation p1 = getNewPermutation("(BC) (E) (D)", getNewAlphabet("BCDE"));
        assertEquals(3, p1.permute(3));


    }

    @Test
    public void sizeCheck() {
        Permutation p = getNewPermutation("(CBDE)", getNewAlphabet("BCDE"));
        assertEquals(4, p.size());
    }

    @Test
    public void testPermuteChar() {


        Permutation p = getNewPermutation("(CBDE)", getNewAlphabet("BCDE"));
        assertEquals('D', p.permute('B'));
        assertEquals('C', p.permute('E'));

        Permutation p1 = getNewPermutation("(BC) (E) (D)", getNewAlphabet("BCDE"));
        assertEquals('E', p1.permute('E'));
        Permutation p2 = getNewPermutation("(BC) (E)", getNewAlphabet("BCDE"));
        assertEquals('D', p2.permute('D'));

        Permutation p3 = getNewPermutation(" (BC) (E) ", getNewAlphabet("BCDE"));
        assertEquals('B', p3.permute('C'));

        Permutation p4 = getNewPermutation("(!@)", getNewAlphabet("@!"));
        assertEquals('@', p4.permute('!'));

        Permutation p5 = getNewPermutation(" (BC)             (E) ", getNewAlphabet("BCDE"));
        assertEquals('B', p5.invert('C'));




    }
    @Test
    public void checkPermutations() {
        Permutation p = getNewPermutation("(CAT)", getNewAlphabet("ATC"));
        checkPerm("wrong", "CAT", "ATC", p, getNewAlphabet("ATC") );
    }




}
