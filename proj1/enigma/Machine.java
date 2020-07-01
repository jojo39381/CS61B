package enigma;


import java.util.Collection;
import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Joseph Yeh
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }
    /** Return the number of ordered rotors I have. */
    int numOrderedRotors() {
        return _orderedRotors.size();
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }


    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {

        _orderedRotors = new ArrayList<Rotor>();
        for (String rotor : rotors) {
            for (Rotor r : _allRotors) {
                if (r.name().equals(rotor)) {
                    _orderedRotors.add(r);
                }
            }

        }

        if (!_orderedRotors.get(0).reflecting()) {
            throw error("no reflector at front");
        }
        if (numOrderedRotors() != rotors.length) {
            throw new EnigmaException("wrong rotors");
        }

    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
         
        if (setting.length() == numRotors() - 1) {
            for (int i = 1; i < numOrderedRotors(); i++) {
                _orderedRotors.get(i).set(setting.charAt(i - 1));
            }
        }
    }

    /** Set rotors to setting of ring according to SETTING.*/
    void setRotorsRing(String setting) {
        for (int i = 1; i < numRotors(); i++) {
            _orderedRotors.get(i).setRing(setting.charAt(i - 1));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        for (int i = 0; i < numOrderedRotors(); i++) {
            if (i == numOrderedRotors() - 1) {
                _orderedRotors.get(numOrderedRotors() - 1).advance();
            } else if (_orderedRotors.get(i + 1).atNotch()
                    && _orderedRotors.get(i).rotates()) {
                _orderedRotors.get(i + 1).advance();
                _orderedRotors.get(i).advance();
                i++;
            }
        }

        int p1 = _plugboard.permute(c);
        for (int i = numOrderedRotors() - 1; i >= 0; i--) {
            p1 = _orderedRotors.get(i).convertForward(p1);
        }
        for (int i = 1; i < numOrderedRotors(); i++) {
            p1 = _orderedRotors.get(i).convertBackward(p1);
        }
        int p2 =  _plugboard.permute(p1);

        return p2;


    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {

        String str = msg.replaceAll(" ", "");
        char[] charList = str.toCharArray();
        ArrayList<Integer> intList = new ArrayList<Integer>();
        for (char c : charList) {
            intList.add(_alphabet.toInt(c));

        }
        ArrayList<Character> result = new ArrayList<Character>();
        for (int i : intList) {
            result.add(_alphabet.toChar(convert(i)));

        }

        String message = "";

        for (char i : result) {
            message = message + i;
        }

        return message;

    }

    /** Common alphabet of my rotors. */
    /** the alphabet. */
    private final Alphabet _alphabet;
    /** number of rotors. */
    private int _numRotors;
    /** pawls. */
    private int _pawls;
    /** collections rotors. */
    private Collection<Rotor> _allRotors;
    /** array of rotors. */
    private ArrayList<Rotor> _orderedRotors;
    /** the plugboard. */
    private Permutation _plugboard;
}
