package com.dslmeinte.nqueens;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Mutable and stateful data structure holding the coordinates for a number of queens on a chessboard,
 * together with a few bit masks to efficiently compute valid configurations using bitwise operators,
 * and a static solver method that uses this to compute all solutions to the (n, m)-Queens Problem.
 * 
 * A standard recursive (backtracking) implementation is more simple, but also consumes a lot of memory.
 * For a good performance, the backtracking algorithm is effectively unrolled using a fixed amount of memory
 * and no stack (assuming that calls to the #iterate, #nextLegalColumn and BitMask methods are appropriately inlined by the JVM),
 * so everything should be JIT'ed by the JVM to run natively and without cache misses.
 * 
 * @author meinte
 */
public class Chessboard {

    /** Number of queens to-be-placed. */
    public final int n;
    /** Length of both sides of board. */
    public final int m;

    /** Number of queens placed. */
    private int k = 0;
    /** c[i] denotes the row the i-th queen is placed on, with -1 meaning an unset counter. */
    private int[] queenColumn;

    /** The bit masks indicating column-wise coverage by any queen on first i rows, for index i. */
    private int[] columnBitMask;
    /** The bit masks indicating left/right-diagonal-wise coverage by any queen on first i rows, for index i. */
    private int[] leftDiagonalBitMask;
    private int[] rightDiagonalBitMask;
    /** The bit masks indicating coverage by any queen on first i rows, for index i. */
    private int[] coverageBitMask;
    // (All these arrays are effectively 1-based w.r.t. queen numbers so array index and sequence index coincide.)

    private int iteration = 0;


    // Methods having default/package-private visibility do so for testing purposes.

    Chessboard(int numberOfQueens, int lengthOfBoardSides) {
        if (lengthOfBoardSides < 1 || lengthOfBoardSides >= 32) {
            throw new IllegalArgumentException("length of sides of board must lie in the range 1..31");
        }
        if (numberOfQueens < 1 || numberOfQueens > lengthOfBoardSides) {
            throw new IllegalArgumentException("number of queens must lie in the range 1..(lengthOfBoardSides)");
        }
        this.n = numberOfQueens;
        this.m = lengthOfBoardSides;
        queenColumn = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            queenColumn[i] = -1;
        }
        columnBitMask = new int[n + 1];
        leftDiagonalBitMask = new int[n + 1];
        rightDiagonalBitMask = new int[n + 1];
        coverageBitMask = new int[n + 2];
    }


    int nQueensPlaced() {
        return k;
    }

    /**
     * @return the legal column for the queen {@code k + 1} which isn't already considered.
     */
    int nextLegalColumn() {
        return BitMask.firstZeroBit(coverageBitMask[k + 1], m, queenColumn[k + 1]);
    }

    /**
     * Places the next queen in row {@code k + 1} and the given @param column,
     * and updates the bit masks to maintain the invariants.
     */
    void place(int column) {
        if (k == n) {
            throw new IllegalStateException(String.format("cannot place more than %d queens", k + 1));
        }
        queenColumn[k + 1] = column;
        // compute and update invariants:
        int nextQueenBitMask = BitMask.bits[column];
        columnBitMask[k + 1] = columnBitMask[k] | nextQueenBitMask;
        leftDiagonalBitMask[k + 1] = (leftDiagonalBitMask[k] << 1) | nextQueenBitMask;
        rightDiagonalBitMask[k + 1] = (rightDiagonalBitMask[k] >> 1) | nextQueenBitMask;
        k++;
        coverageBitMask[k + 1] = columnBitMask[k] | (leftDiagonalBitMask[k] << 1) | (rightDiagonalBitMask[k] >> 1);
    }

    /**
     * Performs one iteration w.r.t. the current chessboard, calling the given action when a solution has been found.
     * @param solutionFunction - The function called for each solution encountered, <em>which is supposed to act on this synchronously</em>.
     * @return whether the whole iteration is done.
     */
    private boolean iterate(Function<Chessboard, Boolean> solutionFunction) {
        iteration++;
        if (k == n) {
            boolean quit = solutionFunction.apply(this);
            if (quit) {
                return true;
            }
            k--;
        } else {
            int columnToPlace = nextLegalColumn();
            if (columnToPlace == -1) {
                if (k == 0) {
                    return true;
                }
                queenColumn[k + 1] = -1;    // unset counter for next queen
                k--;
            } else {
                place(columnToPlace);
            }
        }
        return false;
    }


    /**
     * @return the (0-based) column numbers for the n queens on this chessboard.
     */
    public int[] queens() {
        return Arrays.copyOfRange(queenColumn, 1, n + 1);
    }

    /**
     * @return a simple String representation of the n queens on this chessboard.
     */
    public String toString() {
        return Arrays.toString(queens());
    }

    /**
     * Calls the given function for each solution of the n-Queens Problem on a chessboard with side lengths m.
     * @return the number of iterations performed.
     */
    public static int mapSolutions(int n, int m, Function<Chessboard, Boolean> solutionFunction) {
        Chessboard chessboard = new Chessboard(n, m);
        for (boolean done = false; !done; done = chessboard.iterate(solutionFunction)) {}
        return chessboard.iteration;
    }
    /*
     * TODO  provide possibility for parallelisation by sharding in ranges on the column of the first queen
     */

}
