import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class ECHashStringSetTest  {

    @Test
    public void testPut() {
        String[] test = {"a", "b", "c"};
        ECHashStringSet hashSet = new ECHashStringSet();
        ECHashStringSet emptySet = new ECHashStringSet();
        for(String data : test){
            hashSet.put(data);
        }
        assertEquals(3,hashSet._size);
        emptySet.put(null);
        assertEquals(0,emptySet._size);
    }

    @Test
    public void testContains() {
        String[] test = {"a", "b", "c"};
        ECHashStringSet hashSet = new ECHashStringSet();
        for(String data : test)
            hashSet.put(data);
        for(String i : test) {
            assertTrue(hashSet.contains(i));
        }





    }
}
