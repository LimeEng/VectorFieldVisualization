package operations.vector;

import vectors.Vector2D;

public final class PredefinedOperations {

    private PredefinedOperations() {}

    public static VectorOperation switchCoords() {
        return vector -> new Vector2D(vector.y, vector.x);
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
        return v -> new Vector2D(x, y);
    }

    public static VectorOperation nop() {
        return v -> v;
    }

    public static VectorOperation zero() {
        return Vector2D::new;
    }
}
