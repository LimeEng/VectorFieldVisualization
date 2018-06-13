package hacky;

import noise.OpenSimplexNoise;

public final class Noise {

    private final static OpenSimplexNoise simplex = new OpenSimplexNoise(42);

    private Noise() {}

    public static double eval(double x, double y) {
        return simplex.eval(x, y);
    }

    public static double eval(double x, double y, double z) {
        return simplex.eval(x, y, z);
    }

    public static double eval(double x, double y, double z, double w) {
        return simplex.eval(x, y, z, w);
    }
}
