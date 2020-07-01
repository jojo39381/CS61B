import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        assertEquals(1, CompoundInterest.numYears(2021));
        assertEquals(2, CompoundInterest.numYears(2022));


        String s = "hello";
        System.out.println(s.substring(0, s.length() - 1));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2022), tolerance);
        assertEquals(29.376, CompoundInterest.futureValue(17, 20, 2023), tolerance);
        assertEquals(17.15, CompoundInterest.futureValue(50, -30, 2023), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.8026496, CompoundInterest.futureValueReal(10, 12, 2022, 3), tolerance);
        assertEquals(25.186248, CompoundInterest.futureValueReal(17, 20, 2023, 5), tolerance);
        assertEquals(12.50235, CompoundInterest.futureValueReal(50, -30, 2023, 10), tolerance);

    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2022, 10), tolerance);
        assertEquals(87537.38, CompoundInterest.totalSavings(10000, 2025, 15), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(14936.375, CompoundInterest.totalSavingsReal(5000, 2022, 10, 5), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
