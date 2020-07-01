/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static loa.Piece.*;


/** An automated Player.
 *  @author Joseph Yeh
 */
class MachinePlayer extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new MachinePlayer with no piece or controller (intended to produce
     *  a template). */
    MachinePlayer() {
        this(null, null);
    }

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice;

        assert side() == getGame().getBoard().turn();
        int depth;

        choice = searchForMove();
        getGame().reportMove(choice);

        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }



    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private Move searchForMove() {

        Board work = getBoard();
        int value;
        int max = 4;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            for (int i = 1; i <= max; i++) {
                value = findMove(work, i, true, 1, -INFTY, INFTY);
                if (value == WINNING_VALUE) {
                    break;
                }
            }

        } else {
            for (int i = 1; i <= max; i++) {
                value = findMove(work, i, true, -1, -INFTY, INFTY);
                if (value == -WINNING_VALUE) {
                    break;
                }
            }
        }

        return _foundMove;
    }





    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    public int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {

        Board copy = new Board();
        copy.copyFrom(board);
        Move bestMove = null;

        if (depth == 0 || board.gameOver()) {
            return value(board);
        }
        if (sense == 1) {
            int bestScore = -INFTY;
            for (Move i : copy.legalMoves()) {
                copy.makeMove(i);
                int score = findMove(copy, depth - 1, false, -1, alpha, beta);
                copy.retract();
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
                alpha = max(alpha, score);
                if (beta <= alpha) {
                    break;
                }

            }
            if (saveMove) {
                _foundMove = bestMove;
            }

            return bestScore;
        } else {
            int bestScore = INFTY;
            for (Move i : copy.legalMoves()) {
                copy.makeMove(i);
                int score = findMove(copy, depth - 1, false, 1, alpha, beta);
                copy.retract();
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
                beta = min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }

            if (saveMove) {
                _foundMove = bestMove;
            }

            return bestScore;
        }



    }



    /** gets the value of the specified move.
     *  takes in BOARD as the move and @returns the value.
     */
    public int value(Board board) {
        int value = 0;
        List<Integer> white = board.getRegionSizes(WP);
        List<Integer> black = board.getRegionSizes(BP);
        if (white.size() == 1) {
            return WINNING_VALUE;
        }
        if (black.size() == 1) {
            return -WINNING_VALUE;
        }
        if (white.size() < black.size()) {
            value += getGame().randInt(magic1);
        } else if (white.size() > black.size()) {
            value -= getGame().randInt(magic1);
        }
        if (white.get(0) > black.get(0)) {
            value += getGame().randInt(magic2);
        } else if (white.get(0) < black.get(0)) {
            value -= getGame().randInt(magic2);
        }
        int[] whiteInfo = board.getNum(WP);
        int[] blackInfo = board.getNum(BP);
        if (whiteInfo[0] > blackInfo[0]) {
            value += getGame().randInt(magic3);
        } else if (whiteInfo[0] < blackInfo[0])  {
            value -= getGame().randInt(magic3);
        }

        if (whiteInfo[1] < blackInfo[1]) {
            value += getGame().randInt(8000);
        }
        else if (whiteInfo[1] > blackInfo[1]) {
            value -= getGame().randInt(8000);
        }





        return value;

    }
    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 3;
    }



    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

    /** cool number1. */
    private final int magic1 = 18000;
    /** cool number2. */
    private final int magic2 = 10000;
    /** cool number3. */
    private final int magic3 = 6000;

}
