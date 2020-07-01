/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import org.junit.Test;

import static loa.Piece.*;


import static org.junit.Assert.*;

/** Tests of the Board class API.
 *  @author
 */
public class MachinePlayerTest {

    /** A "general" position. */
    static final Piece[][] BOARD1 = {
        { EMP, BP,  EMP,  BP,  BP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP,  BP,  BP, EMP, WP  },
        { WP,  EMP,  BP, EMP, EMP,  WP, EMP, EMP  },
        { WP,  EMP,  WP,  WP, EMP,  WP, EMP, EMP  },
        { WP,  EMP, EMP, EMP,  BP, EMP, EMP, WP  },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
        { EMP, BP,  BP,  BP,  EMP,  BP,  BP, EMP }
    };

    /** A position in which black, but not white, pieces are contiguous. */
    static final Piece[][] BOARD2 = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP, EMP, EMP },
        { EMP,  WP,  BP,  WP,  WP, EMP, EMP, EMP },
        { EMP, EMP,  BP,  BP,  WP,  WP, EMP,  EMP },
        { EMP,  WP,  WP,  BP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, BP, EMP, WP },
    };

    /** A position in which black, but not white, pieces are contiguous. */
    static final Piece[][] BOARD3 = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  WP, EMP, EMP, EMP },
        { EMP,  WP,  BP,  WP,  WP, EMP, EMP, EMP },
        { EMP, EMP,  BP,  BP,  WP,  WP,  WP, EMP },
        { EMP,  WP,  WP,  WP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
    };


    static final String BOARD1_STRING =
        "===\n"
        + "    - b b b - b b - \n"
        + "    - - - - - - - - \n"
        + "    w - - - b - - w \n"
        + "    w - w w - w - - \n"
        + "    w - b - - w - - \n"
        + "    w - - - b b - w \n"
        + "    w - - - - - - w \n"
        + "    - b - b b - - - \n"
        + "Next move: black\n"
        + "===";
    private static final int INFTY = Integer.MAX_VALUE;







    static final Piece[][] WIN = {
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { BP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { EMP, BP, BP, WP, EMP, EMP, EMP, EMP },
            { EMP,  EMP,  EMP,  EMP,  EMP,  BP, WP, EMP },
            { EMP,  EMP,  EMP,  EMP,  EMP, BP, EMP, EMP },
            { EMP, EMP,  BP,  BP,  EMP,  EMP, EMP,  EMP },
            { EMP,  BP,  EMP,  EMP, EMP, EMP, EMP, EMP },
            { EMP, EMP, EMP,  BP, EMP, EMP, WP, EMP },
    };


    /** Copy Test */
    @Test
    public void testFindMoves() {
        Board b1 = new Board(WIN, WP);
        System.out.println(WIN);
        System.out.println(b1);
        MachinePlayer mp = new MachinePlayer();
        mp.findMove(b1, 5, true, 1, -INFTY, INFTY);
    }
}
