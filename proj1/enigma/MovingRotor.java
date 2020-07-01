package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Joseph Yeh
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
        _permutation = perm;
    }



    @Override
    boolean atNotch() {
        String str = String.valueOf(_permutation.alphabet().toChar(setting()));
        return _notches.contains(str);
    }

    @Override
    boolean rotates() {
        return true;
    }
    @Override
    void advance() {
        super.set(_permutation.wrap(setting() + 1));
    }




    /** the notches. */
    private String _notches;
    /** the permutation cycle. */
    private Permutation _permutation;



}
