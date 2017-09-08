package com.dslmeinte.nqueens;

import static com.dslmeinte.nqueens.BitMask.bits;
import static com.dslmeinte.nqueens.BitMask.firstOneBit;
import static com.dslmeinte.nqueens.BitMask.firstZeroBit;
import static com.dslmeinte.nqueens.BitMask.ones;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BitMaskTest {

    @Test
    public void test_bits() {
        assertEquals(1, bits[0]);
        assertEquals(2, bits[1]);
        assertEquals(4, bits[2]);
        assertEquals(8, bits[3]);
        assertEquals(32768, bits[15]);
        assertEquals(1073741824, bits[30]);
    }

    @Test
    public void test_firstZeroBit() {
        int nrBits = 2;
        assertEquals(0, firstZeroBit(0, nrBits, 0));
        assertEquals(1, firstZeroBit(0, nrBits, 1));
        assertEquals(-1, firstZeroBit(0, nrBits, 2));
        assertEquals(0, firstZeroBit(2, nrBits, 0));
        assertEquals(-1, firstZeroBit(2, nrBits, 1));
        assertEquals(-1, firstZeroBit(3, nrBits, 0));
        assertEquals(2, firstZeroBit(3, 8, 0));
    }

    @Test
    public void test_toString() {
        assertEquals("0", BitMask.toString(0, 1));
        assertEquals("1", BitMask.toString(1, 1));
        assertEquals("00", BitMask.toString(0, 2));
        assertEquals("10", BitMask.toString(1, 2));
        assertEquals("01", BitMask.toString(2, 2));
        assertEquals("11", BitMask.toString(3, 2));
    }

    @Test
    public void test_ones() {
        for (int i = 0; i < 32; i++) {
            assertEquals(0, ones(i, i));
        }
        assertEquals(bits[0], ones(0, 1));
        assertEquals(bits[1] + bits[2], ones(1, 3));
        assertEquals(Integer.MAX_VALUE, ones(0, 31));
    }

    @Test
    public void test_firstOneBit() {
        assertEquals(-1, firstOneBit(0, 31));
        assertEquals(0, firstOneBit(1, 31));
        assertEquals(2, firstOneBit(bits[2] + bits[30], 31));
        for (int i = 0; i < 31; i++) {
            assertEquals(i, firstOneBit(bits[i], 31));
        }
    }

}
