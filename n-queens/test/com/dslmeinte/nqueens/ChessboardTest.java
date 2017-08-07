package com.dslmeinte.nqueens;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

public class ChessboardTest {

    /*
     * Test the following scenario:
     * 
     *   01234567
     *  1....Q...
     *  2Q..xxx..
     *  3xxx.x.xQ
     *  4xxx.x.xx
     * 
     * Q = (newly-placed) queen.
     * x = covered by placed queen.
     * . = free.
     */
    @Test
    public void test_place_some_queens() {
        int n = 8;
        Chessboard chessboard = new Chessboard(n, n);
        assertEquals(0, chessboard.nQueensPlaced());
        assertEquals(0, chessboard.nextLegalColumn());
        chessboard.place(4);
        assertEquals(1, chessboard.nQueensPlaced());
        assertEquals(0, chessboard.nextLegalColumn());
        chessboard.place(0);
        assertEquals(2, chessboard.nQueensPlaced());
        assertEquals(3, chessboard.nextLegalColumn());
        chessboard.place(8);
        assertEquals(3, chessboard.nQueensPlaced());
        assertEquals(3, chessboard.nextLegalColumn());
    }

    @Test
    public void test_some_small_cases() {
        assertEquals(1, numberOfSolutionsFor(1, 1));
        Chessboard.mapSolutions(1, 1, chessboard -> {
            assertArrayEquals(new int[] { 0 }, chessboard.queens());
            return false;
        });
        assertEquals(2, numberOfSolutionsFor(1, 2));
        assertEquals(0, numberOfSolutionsFor(2, 2));
        assertEquals(2, numberOfSolutionsFor(2, 3));
        assertEquals(0, numberOfSolutionsFor(3, 3));
    }

    @Test
    public void test_four_case() {
        final boolean[] solutionsSeen = new boolean[2];
        Chessboard.mapSolutions(4, 4, chessboard -> {
            if (Arrays.equals(chessboard.queens(), new int[] { 2, 0, 3, 1 })) {
                solutionsSeen[0] = true;
            } else if (Arrays.equals(chessboard.queens(), new int[] { 1, 3, 0, 2 })) {
                solutionsSeen[1] = true;
            } else {
                fail("solution function called for non-solution: " + chessboard.toString());
            }
            return false;
        });
        assertArrayEquals(new boolean[] { true, true }, solutionsSeen);
    }

    @Test
    public void test_larger_cases() {
        // from literature:
        assertEquals( 10, numberOfSolutionsFor( 5,  5));
        assertEquals(  4, numberOfSolutionsFor( 6,  6));
        assertEquals( 40, numberOfSolutionsFor( 7,  7));
        assertEquals( 92, numberOfSolutionsFor( 8,  8));
        assertEquals(352, numberOfSolutionsFor( 9,  9));
        assertEquals(724, numberOfSolutionsFor(10, 10));
    }

    @Test
    public void test_large_cases_printing_first_solutions() {
        for (int n = 11; n < 32; n++) {
            final int n_ = n;
            Chessboard.mapSolutions(n, n, chessboard -> {
                System.out.println(String.format("first solution for n = m = %d: %s", n_, chessboard.toString()));
                return true;
            });
        }
    }

    // (TODO  test exception throwing)

    @Test
    @Ignore("for profiling")
    public void test_large_cases_just_running_them() {
        for (int n = 11; n < 16; n++) {
            numberOfSolutionsFor(n, n);
        }
    }


    /**
     * Convenience method to just count number of solutions, as well as print (n, m, #iterations).
     */
    public static int numberOfSolutionsFor(int n, int m) {
        final WrappedInteger wrappedInteger = new WrappedInteger();
        int iterations = Chessboard.mapSolutions(n, m, chessboard -> { wrappedInteger.n++; return false; });
        int nSolutions = wrappedInteger.n;
        System.out.println(String.format("n=%d, m=%d => %d solution%s in %d iterations", n, m, nSolutions, nSolutions == 1 ? "" : "s", iterations));
        return nSolutions;
    }

    static class WrappedInteger {
        int n;
    }

}
