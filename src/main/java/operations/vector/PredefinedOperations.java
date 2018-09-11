package operations.vector;

import vectors.Vector2D;

public final class PredefinedOperations {

    private PredefinedOperations() {}

    public static VectorOperation switchCoords() {
        return vector -> Vector2D.of(vector.y, vector.x);
    }

    public static VectorOperation mul(double factor) {
        return v -> v.multiply(factor);
    }

    public static VectorOperation divide(double factor) {
        return v -> v.divide(factor);
    }

    public static VectorOperation normalize() {
        return Vector2D::normalize;
    }

    public static VectorOperation setVector(double x, double y) {
        return v -> Vector2D.of(x, y);
    }

    public static VectorOperation nop() {
        return v -> v;
    }

    public static VectorOperation zero() {
        return v -> Vector2D.zero();
    }
}
