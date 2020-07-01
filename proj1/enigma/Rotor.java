package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Joseph Yeh
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _settings = 0;
        _ringSettings = 0;
    }


    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _settings;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _settings = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        _settings = _permutation.alphabet().toInt(cposn);
    }

    /** set the ring setting to character CPOSN. */
    void setRing(char cposn) {
        _ringSettings = _permutation.alphabet().toInt(cposn);
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {

        int num = _permutation.permute(_permutation.wrap(p + setting()
                - _ringSettings));
        return _permutation.wrap(num - setting() + _ringSettings);
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int num = _permutation.invert(_permutation.wrap(e + setting()
                - _ringSettings));
        return _permutation.wrap(num - setting() + _ringSettings);

    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;
    /** the settings of the rotors. */
    private int _settings;
    /** the ring settings. */
    private int _ringSettings;



}
