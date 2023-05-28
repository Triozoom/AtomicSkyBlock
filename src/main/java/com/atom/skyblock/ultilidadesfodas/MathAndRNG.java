package com.atom.skyblock.ultilidadesfodas;

import java.util.*;

public class MathAndRNG
{
    private static final Random b = new Random(); // literally just optimized a lot

    public static int generateInteger(final int max) {
        return b.nextInt(max) + 1;
    }

    public static float turnIntoMinutes(final long ticks) {
        return (float) ticks / 20 / 60;
    }
}
