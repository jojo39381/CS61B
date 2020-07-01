package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Joseph Yeh
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for przocessed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine m = readConfig();
        String line = _input.nextLine();
        if (!line.contains("*")) {

            throw new EnigmaException("wrong format");
        }
        while (_input.hasNext()) {
            if (!line.contains("*")) {
                throw new EnigmaException("wrong format");
            }

            setUp(m, line);
            for (String next = _input.nextLine(); !next.contains("*"); ) {
                if (next.isEmpty()) {
                    _output.println();
                } else {
                    String[] words = next.split(" ");
                    ArrayList<String> output = new ArrayList<String>();
                    for (String str : words) {
                        output.add(m.convert(str));
                    }

                    String out = "";
                    for (String i : output) {
                        out = out + i;
                    }

                    printMessageLine(out);
                }
                if (!_input.hasNext()) {
                    while (_input.hasNextLine()) {
                        _output.println();
                        _input.nextLine();
                    }
                    break;
                }
                next = _input.nextLine();
                line = next;


            }


        }






    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alphabet = "";
            if (_config.hasNext()) {
                alphabet = _config.next();
            }
            String[] delimiters = {" ", ",", "(", ")", "*"};
            for (String de : delimiters) {
                if (alphabet.contains(de)) {
                    throw new EnigmaException("format wrong");
                }
            }

            _alphabet = new Alphabet(alphabet);
            int numRotors = 0;
            int numPawls = 0;
            if (_config.hasNextInt()) {
                numRotors = _config.nextInt();
            } else {
                throw new EnigmaException("format wrong");
            }
            if (_config.hasNextInt()) {
                numPawls = _config.nextInt();
            } else {
                throw new EnigmaException("format wrong");
            }
            ArrayList<Rotor> allRotors = new ArrayList<Rotor>();
            point = _config.next();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
            }

            return new Machine(_alphabet, numRotors, numPawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }
    /** the name. */
    private String point;
    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String name = point;
            String temp = _config.next();
            char type = temp.charAt(0);
            String notch = "";
            if (temp.length() > 1) {
                for (int i = 1; i < temp.length(); i++) {
                    notch = notch + temp.charAt(i);
                }
            }
            String perm = "";
            temp = _config.next();
            while (temp.contains("(") && _config.hasNext()) {
                perm = perm + temp;
                temp = _config.next();
            }
            if (!_config.hasNext()) {
                perm = perm + temp;
            }
            point = temp;

            if (type == 'M') {
                return new MovingRotor(name, new Permutation(perm, _alphabet),
                        notch);
            } else if (type == 'N') {
                return new FixedRotor(name, new Permutation(perm, _alphabet));
            } else {
                return new Reflector(name, new Permutation(perm, _alphabet));
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }
    /** ring setting. */
    private String ring;
    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] setting = settings.split(" ");
        if (setting.length - 1 < M.numRotors()) {
            throw new EnigmaException("Not enough arguments");
        }
        ArrayList<String> rotors = new ArrayList<String>();
        for (int i = 1; i < M.numRotors() + 1; i++) {
            rotors.add(setting[i]);
        }
        String[] inRotors = new String[rotors.size()];
        M.insertRotors(rotors.toArray(inRotors));
        String set1 = setting[M.numRotors() + 1];
        M.setRotors(set1);


        String plug = "";
        for (int i = M.numRotors() + 2; i < setting.length; i++) {
            if (i == M.numRotors() + 2 && !setting[i].contains("(")
                    && !setting[i].contains(" ")) {
                if (setting[i].length() == set1.length()) {
                    ring = setting[i];
                    M.setRotorsRing(ring);
                } else {
                    throw new EnigmaException("wrong number of ring");
                }
            } else {
                plug = plug + setting[i];
            }
        }

        M.setPlugboard(new Permutation(plug, _alphabet));

    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        ArrayList<String> message = new ArrayList<String>();
        for (int i = 0; i < msg.length(); i++) {
            if (i % 6 == 0) {
                msg = msg.substring(0, i) + " "
                        + msg.substring(i, msg.length());
                message.add(msg);
            }
        }
        for (int j = 0; j < message.size(); j++) {
            message.set(j, message.get(j).trim());
        }
        _output.println(message.get(message.size() - 1));


    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
}



