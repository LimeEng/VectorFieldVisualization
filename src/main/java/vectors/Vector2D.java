package vectors;

import java.io.Serializable;
import java.util.Objects;

public class Vector2D implements Serializable, Comparable<Vector2D> {

    private static final long serialVersionUID = 1L;

    private static final Vector2D ZERO = new Vector2D(0, 0);

    public final double x;
    public final double y;

    private Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D of(double x, double y) {
        return new Vector2D(x, y);
    }

    public static Vector2D zero() {
        return ZERO;
    }

    public Vector2D addScaled(Vector2D other, double factor) {
        double nx = x + (other.x * factor);
        double ny = y + (other.y * factor);
        return of(nx, ny);
    }

    public Vector2D add(Vector2D other) {
        double nx = x + other.x;
        double ny = y + other.y;
        return of(nx, ny);
    }

    public Vector2D subtract(Vector2D other) {
        double nx = x - other.x;
        double ny = y - other.y;
        return of(nx, ny);
    }

    public Vector2D multiply(Vector2D other) {
        double nx = x * other.x;
        double ny = y * other.y;
        return of(nx, ny);
    }

    public Vector2D multiply(double factor) {
        double nx = x * factor;
        double ny = y * factor;
        return of(nx, ny);
    }

    public Vector2D divide(Vector2D other) {
        double nx = x / other.x;
        double ny = y / other.y;
        return of(nx, ny);
    }

    public Vector2D divide(double factor) {
        double nx = x / factor;
        double ny = y / factor;
        return of(nx, ny);
    }

    public double dot(Vector2D other) {
        return x * other.x + y * other.y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public Vector2D normalize() {
        return divide(length());
    }

    public double distanceFrom(Vector2D other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }

    public Vector2D cross(Vector2D other) {
        double nx = x * other.y;
        double ny = y * other.x;
        return of(nx, ny);
    }

    public Vector2D proj(Vector2D base) {
        return base.multiply(dot(base) / base.lengthSquared());
    }

    public Vector2D orth(Vector2D base) {
        return proj(base).multiply(-1.0)
                .add(this);
    }

    @Override
    public int compareTo(Vector2D other) {
        return Double.compare(lengthSquared(), other.lengthSquared());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2D) {
            Vector2D other = (Vector2D) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector: (" + x + ", " + y + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
