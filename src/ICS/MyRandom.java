package ICS;

import java.util.Random;

public class MyRandom {
    public static Random R = new Random();

    public static void setR(int seed) {
        R.setSeed(seed);
    }
}
