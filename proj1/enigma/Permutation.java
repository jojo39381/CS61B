package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Joseph Yeh
 */

class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    private String[] cycleList;
    /** initiate permutation class.
     * @param cycles permutation
     * @param alphabet the alphabet */

    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        cycles = cycles.replaceAll(" ", "");
        cycles = cycles.replaceAll("\\(", "");
        cycleList = cycles.split("\\)");







    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        String str = inCycle(p);
        String letter = String.valueOf(_alphabet.toChar(wrap(p)));
        int ind = str.indexOf(letter);
        if (str.length() == 1) {
            return _alphabet.toInt(str.charAt(0));
        }
        if (ind == str.length() - 1) {
            return _alphabet.toInt(str.toCharArray()[0]);
        }
        return _alphabet.toInt(str.toCharArray()[ind + 1]);

    }

    /** if the int is in the cycle.
     * @param p is the number passed in
     * @return the String that it is in*/

    String inCycle(int p) {
        String letter = String.valueOf(_alphabet.toChar(wrap(p)));
        for (String str : cycleList) {
            if (str.contains(letter)) {
                return str;
            }
        }

        return letter;

    }
    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        String str = inCycle(c);
        String letter = String.valueOf(_alphabet.toChar(wrap(c)));
        int ind = str.indexOf(letter);
        if (str.length() == 1) {
            return _alphabet.toInt(str.charAt(0));
        }
        if (ind == 0) {
            return _alphabet.toInt(str.toCharArray()[str.length() - 1]);
        }
        return _alphabet.toInt(str.toCharArray()[ind - 1]);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        String str = inCycle(_alphabet.toInt(p));
        String letter = String.valueOf(p);

        int ind = str.indexOf(letter);
        if (str.length() == 1) {
            return str.charAt(0);
        }
        if (ind == str.length() - 1) {
            return str.toCharArray()[0];
        }

        return str.toCharArray()[ind + 1];
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        String str = inCycle(_alphabet.toInt(c));
        String letter = String.valueOf(c);

        int ind = str.indexOf(letter);
        if (str.length() == 1) {
            return str.charAt(0);
        }
        if (ind == 0) {
            return str.toCharArray()[str.length() - 1];
        }

        return str.toCharArray()[ind - 1];
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i++) {
            if (inCycle(i).length() == 1) {
                return false;
            }
        }
        return true;
    }


    /** Alphabet of this permutation. */
    private Alphabet _alphabet;



}

