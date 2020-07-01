/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 * @author Joseph Yeh
 */
class Board {

    /**
     * Default number of moves for each side that results in a draw.
     */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /**
     * Pattern describing a valid square designator (cr).
     */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /**
     * A Board whose initial contents are taken from INITIALCONTENTS
     * and in which the player playing TURN is to move. The resulting
     * Board has
     * get(col, row) == INITIALCONTENTS[row][col]
     * Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     * <p>
     * CAUTION: The natural written notation for arrays initializers puts
     * the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /**
     * A new board in the standard initial position.
     */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /**
     * A Board whose initial contents and state are copied from
     * BOARD.
     */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /**
     * Set my state to CONTENTS with SIDE to move.
     */
    void initialize(Piece[][] contents, Piece side) {
        _subsetsInitialized = false;
        _winnerKnown = false;
        _winner = null;
        _moves.clear();
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < contents.length; j++) {
                Square sq = sq(j, i);
                set(sq, contents[i][j]);
            }
        }

        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;

    }

    /**
     * Set me to the initial configuration.
     */
    void clear() {

        initialize(INITIAL_PIECES, BP);
    }

    /**
     * Set my state to a copy of BOARD.
     */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        this._winner = board._winner;
        this._winnerKnown = board._winnerKnown;
        this._moveLimit = board._moveLimit;
        this._subsetsInitialized = board._subsetsInitialized;
        for (int i = 0; i < _board.length; i++) {
            _board[i] = board._board[i];
        }
        this._turn = board._turn;
        for (int i = 0; i < board._moves.size(); i++) {
            this._moves.add(board._moves.get(i));
        }


    }

    /**
     * Return the contents of the square at SQ.
     */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /**
     * Set the square at SQ to V and set the side that is to move next
     * to NEXT, if NEXT is not null.
     */
    void set(Square sq, Piece v, Piece next) {

        _board[sq.index()] = v;
        if (next != null) {
            _turn = next;
        }

    }

    /**
     * Set the square at SQ to V, without modifying the side that
     * moves next.
     */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /**
     * Set limit on number of moves by each side that results in a tie to
     * LIMIT, where 2 * LIMIT > movesMade().
     */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /**
     * Assuming isLegal(MOVE), make MOVE. This function assumes that
     * MOVE.isCapture() will return false.  If it saves the move for
     * later retraction, makeMove itself uses MOVE.captureMove() to produce
     * the capturing move.
     */
    void makeMove(Move move) {
        assert isLegal(move);
        if (_moves.size() <= _moveLimit * 2) {
            Piece original = _board[move.getFrom().index()];
            Piece destination = _board[move.getTo().index()];
            if (destination == original.opposite()) {
                move = move.captureMove();
            }
            set(move.getFrom(), EMP);
            set(move.getTo(), EMP);

            set(move.getTo(), original, original.opposite());
            _moves.add(move);
            _winnerKnown = false;
            _winner = null;
            _subsetsInitialized = false;
        }


    }

    /**
     * Retract (unmake) one move, returning to the state immediately before
     * that move.  Requires that movesMade () > 0.
     */
    void retract() {
        if (movesMade() > 0) {
            Move re = _moves.remove(movesMade() - 1);
            if (re.isCapture()) {
                Piece captured = _board[re.getTo().index()].opposite();
                set(re.getTo(), captured);
                set(re.getFrom(), captured.opposite(), captured.opposite());

            } else {
                Piece turn = _board[re.getTo().index()];
                set(re.getFrom(), _board[re.getTo().index()]);
                set(re.getTo(), EMP, turn);
            }
            _subsetsInitialized = false;
            _winner = null;
            _winnerKnown = false;
        }



    }

    /**
     * Return the Piece representing who is next to move.
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Return true iff FROM - TO is a legal move for the player currently on
     * move.
     */
    boolean isLegal(Square from, Square to) {
        if ((from.row() == to.row()) && (from.col() == to.col())) {
            return false;
        }
        if (lineOfAction(from, to) != from.distance(to)) {

            return false;
        }
        if (get(from) == EMP) {
            return false;
        }

        if (get(from) != turn()) {
            return false;
        }

        if (get(from) == get(to)) {
            return false;
        }

        if (inPath(from, to)) {
            return false;
        }

        return true;
    }

    /** check if anything is in path
     * takes in FROM and TO. @returns whether it is in path.
     * */
    boolean inPath(Square from, Square to) {
        int direction = from.direction(to);
        for (int i = 1; i < from.distance(to); i++) {
            if (from.moveDest(direction, i) != null) {
                if (get(from.moveDest(direction, i)) == get(from).opposite()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /** check how many pieces are in path
     * takes in FROM and TO. @returns the number of pieces in line.
     * */

    int lineOfAction(Square from, Square to) {
        int direction = from.direction(to);
        int count = 1;

        for (int i = 1; from.moveDest(direction, i) != null; i++) {
            if (_board[from.moveDest(direction, i).index()] != EMP) {
                count += 1;
            }
        }
        direction = to.direction(from);
        for (int j = 1; from.moveDest(direction, j) != null; j++) {
            if (_board[from.moveDest(direction, j).index()] != EMP) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Return true iff MOVE is legal for the player currently on move.
     * The isCapture() property is ignored.
     */
    boolean isLegal(Move move) {
        if (move != null) {
            return isLegal(move.getFrom(), move.getTo());
        }
        return false;
    }

    /**
     * Return a sequence of all legal moves from this position.
     */
    List<Move> legalMoves() {
        List<Move> legal = new ArrayList<>();
        for (Square i : ALL_SQUARES) {
            if (get(i) == _turn) {
                for (Square j : i.adjacent()) {
                    Square dest = i.moveDest(i.direction(j),
                            lineOfAction(i, j));
                    Move possible = Move.mv(i, dest);
                    if (isLegal(possible)) {
                        Piece original = _board[possible.getFrom().index()];
                        Piece destination = _board[possible.getTo().index()];
                        if (destination == original.opposite()) {
                            possible = possible.captureMove();
                        }
                        legal.add(possible);
                    }
                }
            }
        }
        return legal;
    }

    /**
     * Return true iff the game is over (either player has all his
     * pieces continguous or there is a tie).
     */
    boolean gameOver() {
        return winner() != null;
    }

    /**
     * Return true iff SIDE's pieces are continguous.
     */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /**
     * Return the winning side, if any.  If the game is not over, result is
     * null.  If the game has ended in a tie, returns EMP.
     */
    Piece winner() {
        if (!_winnerKnown) {
            if (piecesContiguous(_turn)) {
                _winnerKnown = true;
                _winner = _turn;
            }
            if (piecesContiguous(_turn.opposite())) {
                _winnerKnown = true;
                _winner = _turn.opposite();
            }
            if (!_winnerKnown) {
                if (movesMade() >= _moveLimit) {
                    _winnerKnown = true;
                    _winner = EMP;
                }
            }
        }

        return _winner;
    }

    /**
     * Return the total number of moves that have been made (and not
     * retracted).  Each valid call to makeMove with a normal move increases
     * this number by 1.
     */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /**
     * Return true if a move from FROM to TO is blocked by an opposing
     * piece or by a friendly piece on the target square.
     */
    private boolean blocked(Square from, Square to) {
        return false;
    }

    /**
     * Return the size of the as-yet unvisited cluster of squares
     * containing P at and adjacent to SQ.  VISITED indicates squares that
     * have already been processed or are in different clusters.  Update
     * VISITED to reflect squares counted.
     */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        if (p == EMP) {
            return 0;

        }
        if (get(sq) != p) {
            return 0;

        }
        if (visited[sq.row()][sq.col()]) {
            return 0;

        } else {
            int num = 1;
            Square[] adjacent = sq.adjacent();
            visitedSq[sq.row()][sq.col()] = true;
            for (Square i : adjacent) {
                num += numContig(i, visitedSq, p);
            }
            return num;
        }

    }
    /** stores the array of booleans of the
     * squares visited.
     * */
    private boolean[][] visitedSq = new boolean[BOARD_SIZE][BOARD_SIZE];

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {

        if (_subsetsInitialized) {
            return;
        } else {
            _whiteRegionSizes.clear();
            _blackRegionSizes.clear();
            visitedSq = new boolean[BOARD_SIZE][BOARD_SIZE];
            for (Square i : ALL_SQUARES) {
                if (!visitedSq[i.row()][i.col()]) {
                    Piece current = get(i);
                    if (current == WP) {
                        _whiteRegionSizes.add(numContig(i, visitedSq, WP));
                    }
                    if (current == BP) {
                        _blackRegionSizes.add(numContig(i, visitedSq, BP));
                    }
                }
            }
            Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
            Collections.sort(_blackRegionSizes, Collections.reverseOrder());


            _subsetsInitialized = true;
        }
    }
    /** gets the size of the length of the board. @returns size
     * */
    public int getSize() {
        return _board.length;
    }


    public Square middle() {
        int column = BOARD_SIZE/2;
        int row = BOARD_SIZE/2;
        return sq(column, row);
    }

    /** gets the number of pieces of side on board takes in SIDE.
     * @returns number pieces
     * */
    int[] getNum(Piece side) {
        int[] list = new int[2];
        Square mid = middle();
        for (Square i : ALL_SQUARES) {
            if (get(i) == side) {
                list[0] += 1;
                list[1] += i.distance(mid);
            }
        }
        return list;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }



    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of continguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();
}
