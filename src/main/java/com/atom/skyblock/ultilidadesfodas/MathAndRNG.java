package com.atom.skyblock.ultilidadesfodas;

import java.util.*;

public class MathAndRNG
{
    public static int generateInteger(final int max) {
        final Random b = new Random();
        return b.nextInt(max) + 1;
    }

    public static float turnIntoMinutes(final long ticks) {
        return (float) ticks / 20 / 60;
    }
}
