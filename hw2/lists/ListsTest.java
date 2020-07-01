package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author FIXME
 */

public class ListsTest {
    /** FIXME
     */
    @Test
    public void testNaturalRuns() {
        int[][] arr = {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
        IntList test1 = lists.IntList.list(1, 2, 3, 2, 3, 4, 3, 4, 5);
        assertEquals(lists.IntListList.list(arr), Lists.naturalRuns(test1));
        int[][] arr2 = {{1, 2, 7, 9}, {1, 5}, {2, 7}, {3}};
        IntList test2 = lists.IntList.list(1, 2, 7, 9, 1, 5, 2, 7, 3);


    }
    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
