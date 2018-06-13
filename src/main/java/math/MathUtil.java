package math;

public class MathUtil {

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static long clamp(long value, long min, long max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double normalize(double value, double valueMin, double valueMax, double newMin, double newMax) {
        return (((value - valueMin) * (newMax - newMin)) / (valueMax - valueMin)) + newMin;
    }
}
