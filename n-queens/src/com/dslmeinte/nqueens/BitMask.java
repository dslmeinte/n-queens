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
     * @return the index of the first zero bit in the @param mask (of size @param
     *         nrBits bits) with index >= @param fromBit.
     */
    public static int firstZeroBit(int mask, int nrBits, int fromBit) {
        assert 0 < nrBits && nrBits < 32;
        assert fromBit <= nrBits;
        int nrEffectiveBits = nrBits - fromBit;
        int shiftIndex = nrEffectiveBits == 0 ? -1 : firstOneBit((~(mask >>> fromBit)) & ones(0, nrEffectiveBits), nrEffectiveBits);
        return shiftIndex == -1 ? -1 : (shiftIndex + fromBit);
    }

    /**
     * @return the bit mask pattern with only the bits with indices [fromBit, toBit)
     *         set to 1.
     */
    /* package-visible for testing */ static int ones(int fromBit, int toBit) {
        assert 0 <= fromBit && fromBit < 32;
        assert 0 <= toBit && toBit < 32;
        assert fromBit <= toBit;
        return ((1 << (toBit - fromBit)) - 1) << fromBit;
    }

    /**
     * @return the index of the first one bit in the @param mask (of size @param
     *         nrBits bits).
     */
    /* package-visible for testing */ static int firstOneBit(int mask, int nrBits) {
        int bitNr = 0;
        while (bitNr < nrBits) {
            if (mask % 2 != 0) { // bit-inverted masks can be negative => test for == -1 || == 0 <==> != 0
                return bitNr;
            }
            mask >>= 1;
            bitNr++;
        }
        return -1;
    }

    /**
     * @return the representation of the bit mask, with the LSB to the left.
     *         (Included for testing purposes, but not actually used.)
     */
    public static String toString(int mask, int nrBits) {
        assert mask >= 0;
        assert 0 < nrBits && nrBits < 32;
        StringBuilder str = new StringBuilder();
        for (int bitNr = 0; bitNr < nrBits; bitNr++) {
            str.append(Math.abs(mask % 2));
            mask = mask >>> 1;
        }
        return str.toString();
    }

}
