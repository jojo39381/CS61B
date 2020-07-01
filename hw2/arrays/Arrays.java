package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    static int[] catenate(int[] A, int[] B) {
        /* *Replace this body with the solution. */
        int[] result = new int[A.length + B.length];

        System.arraycopy(
                A,
                0,
                result,
                0,
                A.length);
        System.arraycopy(
                B,
                0,
                result,
                A.length,
                B.length);


        return result;
    }

    /* C2. */

    /**
     * Returns the array formed by removing LEN items from A,
     * beginning with item #START.
     */
    static int[] remove(int[] A, int start, int len) {
        /* *Replace this body with the solution. */
        int[] begin = new int[A.length - len];

        for (int i = 0; i < start; i++) {
            begin[i] = A[i];
        }
        System.arraycopy(A, start + len, begin, start, A.length - start - len);
        return begin;
    }

    /* C3. */

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     * For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     * returns the three-element array
     * {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */
        int count = 1;
        for (int i = 0; i < A.length; i++){
            if (i+1 < A.length && A[i+1] < A[i]) {
                count += 1;

            }
        }


        int[][] result = new int[count][];

        int track = 0;

        while (track != count) {
            for (int j = 0; j < A.length; j++) {
                if (j + 1 < A.length && A[j + 1] < A[j]) {
                    result[track] = Utils.subarray(A, 0, j + 1);
                    track += 1;
                    A = remove(A, 0, j + 1);
                    break;
                }
                if (j == A.length - 1) {

                    result[track] = Utils.subarray(A, 0, j+ 1);
                    track += 1;
                }

            }

        }

        return result;
    }
}
