import org.junit.Test;
import static org.junit.Assert.*;

/** Tests of BitExercise
 *  @author Zoe Plaxco
 */
public class BitExerciseTest {

    @Test
    public void testLastBit() {
        int four = BitExercise.lastBit(100);
        assertEquals(4, four);
    }

    @Test
    public void testPowerOfTwo() {
        boolean powOfTwo = BitExercise.powerOfTwo(32);
        assertTrue(powOfTwo);
        boolean powOfTwo2 = BitExercise.powerOfTwo(25);
        assertFalse(powOfTwo2);
        boolean notPower = BitExercise.powerOfTwo(7);
        assertFalse(notPower);
    }

    @Test
    public void testAbsolute() {
        int hundred = BitExercise.absolute(100);
        assertEquals(100, hundred);
        int negative = BitExercise.absolute(-100);
        assertEquals(100, negative);
        int zero = BitExercise.absolute(0);
        assertEquals(0,zero);
    }
    @Test
    public void testMogrify() {
        int hundred = BitExercise.mogrify(3);
        System.out.println(Integer.toBinaryString(hundred));
        assertEquals(6, hundred);

    }



    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(BitExerciseTest.class));
    }
}

