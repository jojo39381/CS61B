/** A collection of bit twiddling exercises.
 *  @author
 */

public class BitExercise {

    /** Fill in the function below so that it returns
    * the value of the argument x with all but its last 
    * (least significant) 1-bit set to 0.
    * For example, 100 in binary is 0b1100100, so lastBit(100)
    * should return 4, which in binary is 0b100.
    */
    public static int lastBit(int x) {
        return x & -x;
    }

    /** Fill in the function below so that it returns 
    * True iff x is a power of two, otherwise False.
    * For example: 2, 32, and 8192 are powers of two.
    */
    public static boolean powerOfTwo(int x) {
        if (x == 0) {
            return false;
        }
        return ((x & -x ) == x);          
    }

    public static int x = 222;
    public static int y = 111;
    public static boolean mogrify(int k) {
        int y = 16777218;
        boolean x = (y * 144 == ((y << 4) ^ (y << 7)));
        return x;
    }
    
    /** Fill in the function below so that it returns 
    * the absolute value of x WITHOUT USING ANY IF 
    * STATEMENTS OR CALLS TO MATH.
    * For example, absolute(1) should return 1 and 
    * absolute(-1) should return 1.
    */
    public static int absolute(int x) {
        return (x ^ (x >> 31)) - (x >> 31);
    } 
}