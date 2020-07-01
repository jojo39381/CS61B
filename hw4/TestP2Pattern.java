import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the P2 Pattern
 *  @author Josh Hug & Vivant Sakore
 */

public class TestP2Pattern {

    /** Returns true if the string s matches pattern p */
    private static boolean checkMatch(Pattern p, String s) {
        Matcher mat = p.matcher(s);
        return mat.matches();
    }

    @Test
    public void testP1() {
        Pattern p = Pattern.compile(P2Pattern.P1);

        String good1 = "3/4/2012";
        String good2 = "03/4/2012";
        String good3 = "3/04/2012";
        String good4 = "03/04/2012";
        String good5 = "12/12/2012";
        assertTrue(checkMatch(p, good1));
        assertTrue(checkMatch(p, good2));
        assertTrue(checkMatch(p, good3));
        assertTrue(checkMatch(p, good4));
        assertTrue(checkMatch(p, good5));

        String bad1 = "15/2/2012";
        String bad2 = "12/39/2012";
        String bad3 = "007/13/2012";
        String bad4 = "07/013/2012";
        String bad5 = "07/13/02012";
        assertFalse(checkMatch(p, bad1));
        assertFalse(checkMatch(p, bad2));
        assertFalse(checkMatch(p, bad3));
        assertFalse(checkMatch(p, bad4));
        assertFalse(checkMatch(p, bad5));
    }

    @Test
    public void testP2() {
        Pattern p = Pattern.compile(P2Pattern.P2);

        String good1 = "(1, 2, 33, 1, 63)";
        String good2 = "(1, 0, 3, 4, 5, 6, 7, 12312, 41)";
        String good3 = "(512,    41, 7,     2, 9)";
        assertTrue(checkMatch(p, good1));
        assertTrue(checkMatch(p, good2));
        assertTrue(checkMatch(p, good3));

        String bad1 = "6, 1, 4, 1, 2, 3";
        String bad2 = "(6, 1, 4, 1, 2, 3,)";
        String bad3 = "(, 6, 1, 4, 1, 2, 3)";
        String bad4 = "(,6, 1, 4, 1, 2, 3)";
        String bad5 = "()";
        assertFalse(checkMatch(p, bad1));
        assertFalse(checkMatch(p, bad2));
        assertFalse(checkMatch(p, bad3));
        assertFalse(checkMatch(p, bad4));
        assertFalse(checkMatch(p, bad5));
    }

    @Test
    public void testP3() {
        Pattern p = Pattern.compile(P2Pattern.P3);

        String good1 = "www.support.spotify.net";
        String good2 = "www.telegraph.co.de";
        String good3 = "www.ucb-login.edu";
        String good4 = "www.facebook-login.co.uk";
        String good5 = "x.museum";
        assertTrue(checkMatch(p, good1));
        assertTrue(checkMatch(p, good2));
        assertTrue(checkMatch(p, good3));
        assertTrue(checkMatch(p, good4));
        assertTrue(checkMatch(p, good5));


        String bad1 = "-google.com";
        String bad2 = "google-.com";
        String bad3 = "go-ogle.newyorker";
        String bad4 = ".google.com";
        String bad5 = "facebook-login.com.";
        assertFalse(checkMatch(p, bad1));
        assertFalse(checkMatch(p, bad2));
        assertFalse(checkMatch(p, bad3));
        assertFalse(checkMatch(p, bad4));
        assertFalse(checkMatch(p, bad5));
    }

    @Test
    public void testP4() {
        Pattern p = Pattern.compile(P2Pattern.P4);

        String good1 = "_tes_$t13$";
        String good2 = "__tes_$t13$$";
        String good3 = "$$test$_13__";
        String good4 = "__";
        String good5 = "$";
        assertTrue(checkMatch(p, good1));
        assertTrue(checkMatch(p, good2));
        assertTrue(checkMatch(p, good3));
        assertTrue(checkMatch(p, good4));
        assertTrue(checkMatch(p, good5));

        String bad1 = "9test";
        String bad2 = "99";
        String bad3 = "99#test";
        String bad4 = "9($)";
        String bad5 = "9 test";
        assertFalse(checkMatch(p, bad1));
        assertFalse(checkMatch(p, bad2));
        assertFalse(checkMatch(p, bad3));
        assertFalse(checkMatch(p, bad4));
        assertFalse(checkMatch(p, bad5));
    }


    @Test
    public void testP6() {
        Pattern p = Pattern.compile(P2Pattern.P6);

        String good1 = "23:00";
        String good2 = "11:00";
        String good3 = "02:30";
        String good4 = "01:59";
        String good5 = "5:20";
        assertTrue(checkMatch(p, good1));
        assertTrue(checkMatch(p, good2));
        assertTrue(checkMatch(p, good3));
        assertTrue(checkMatch(p, good4));
        assertTrue(checkMatch(p, good5));

        String bad1 = "24:33";
        String bad2 = "111:11";
        String bad3 = "001:22";
        String bad4 = "25:26";
        String bad5 = "11:100";
        assertFalse(checkMatch(p, bad1));
        assertFalse(checkMatch(p, bad2));
        assertFalse(checkMatch(p, bad3));
        assertFalse(checkMatch(p, bad4));
        assertFalse(checkMatch(p, bad5));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TestP2Pattern.class));
    }
}
