package enigma;
import static enigma.EnigmaException.*;
/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Joseph Yeh
 */
class Alphabet {
    /** alphabet.*/
    private String _alphabet;
    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        _alphabet = chars;
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _alphabet.length();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        for (char i : _alphabet.toCharArray()) {
            if (ch == i) {
                return true;
            }
        }
        return false;
    }



    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        if (index >= 0 && index < this.size()) {
            return _alphabet.charAt(index);
        }
        throw error("index out of range");

    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        if (this.contains(ch)) {
            return _alphabet.indexOf(ch);
        }
        throw error("char is not in alphabet");
    }

}
