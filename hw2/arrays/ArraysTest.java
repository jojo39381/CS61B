package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    /** FIXME
     */
    @Test
    public void testCatenate() {
        int[] A = {1, 2, 3};
        int[] B = {4, 5, 6};
        int[] result = {1, 2, 3, 4, 5, 6};
        int[] C = {5, 6, 7, 8, 9};
        int[] D = {1, 2, 3};
        int[] result2 = {5, 6, 7, 8, 9, 1, 2, 3};
        assertArrayEquals(result, Arrays.catenate(A, B));
        assertArrayEquals(result2, Arrays.catenate(C, D));

    }

    @Test
    public void testRemove() {
        int[] A = {1,2,3,4,5};
        int[] result = {1,2,4,5};
        int[] B = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
        int[] result2 = {5,6,7,15,16,17,18,19};
        int[] C = {2,3,4,5,6};
        int[] result3 = {2};
        assertArrayEquals(result, Arrays.remove(A,2,1));
        assertArrayEquals(result2, Arrays.remove(B,3,7));
        assertArrayEquals(result3, Arrays.remove(C,1,4));
    }
    @Test
    public void testNaturalRuns() {
        int[] A = {1, 3, 7, 5, 4, 6, 9, 10};
        int[][] result = {{1, 3, 7}, {5}, {4, 6, 9, 10}};
        int[] B = {1, 2, 3, 2, 5, 3, 7, 8, 2, 1, 5};
        int[][] result2 = {{1, 2, 3}, {2, 5}, {3, 7, 8}, {2}, {1, 5}};
        assertArrayEquals(result, Arrays.naturalRuns(A));
        assertArrayEquals(result2, Arrays.naturalRuns(B));


    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
