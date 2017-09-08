package com.dslmeinte.nqueens;

/**
 * A number of convenience methods for working with bit masks stored in a
 * regular Java {@code int}.
 */
public class BitMask {

    /**
     * bits[i] produces the bit mask with just the i-th bit set, for i = 0, ..., 30.
     */
    public static final int[] bits = new int[32];

    static {
        int pow2 = 1;
        for (int exp = 0; exp < 32; exp++) {
            bits[exp] = pow2;
            pow2 <<= 1;
        }
    }

    /**
     * @return the index of the first zero bit in the @param mask (of size @param nrBits bits) with index >= @param fromBit.
     */
    public static int firstZeroBit(int mask, int nrBits, int fromBit) {
    		int shiftIndex = firstOneBit(~(mask >> fromBit), nrBits - fromBit);
    		return shiftIndex == -1 ? -1 : (shiftIndex + fromBit);
    }

    /**
     * @return the index of the first one bit in the @param mask (of size @param nrBits bits).
     */
    private static int firstOneBit(int mask, int nrBits) {
    		int bitNr = 0;
    		while (bitNr < nrBits) {
    			if (mask%2 != 0) {	// bit-inverted masks can be negative => test for == -1 || == 0 <==> != 0
    				return bitNr;
    			}
    			mask >>= 1;
    			bitNr++;
    		}
    		return -1;
    }
    // TODO  this can be made more efficient using a binary search

    /**
     * @return the representation of the bit mask, with the LSB to the left.
     *     (Included for testing purposes, but not actually used.)
     */
    public static String toString(int mask, int nrBits) {
        StringBuilder str = new StringBuilder();
        for (int bitNr = 0; bitNr < nrBits; bitNr++) {
            str.append(mask%2);
            mask = mask >> 1;
        }
        return str.toString();
    }

}
