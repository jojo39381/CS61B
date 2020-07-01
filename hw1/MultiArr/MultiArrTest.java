import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int[][] arr = {{1, 3, 4}, {1}, {5, 6, 7, 8}, {7, 9}};
        int [][] arr2 = {{23, 34, 56}, {7, 89}, {10, 12, 29, 35}, {12, 99}};
        assertEquals(9, MultiArr.maxValue(arr));
        assertEquals(99, MultiArr.maxValue(arr2));
    }

    @Test
    public void testAllRowSums() {

        int[][] nonSquareArray = {{1, 3, 4}, {1}, {5, 6, 7, 8}, {7, 9}};
        int[] result = {8, 1, 26, 16};
        int[][] arr = {{5, 9, 7}, {2, 3, 6}, {7, 2, 5}};
        int[] result2 = {21, 11, 14};
        assertArrayEquals(result, MultiArr.allRowSums(nonSquareArray));
        assertArrayEquals(result2, MultiArr.allRowSums(arr));
    }



    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
