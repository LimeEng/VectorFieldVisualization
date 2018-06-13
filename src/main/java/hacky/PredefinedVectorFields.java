package hacky;

import noise.OpenSimplexNoise;
import operations.Operation;
import vectors.Vector2D;

public enum PredefinedVectorFields {
    
    

    CIRCLE_DETAIL(vector -> {
        double length = vector.length();
        double nx = Math.sin(length * Math.cos(vector.y));
        double ny = Math.cos(length * Math.sin(vector.x));
        return new Vector2D(nx, ny);
    }),

    TESTING(vector -> {
        double nx = Math.sin(vector.y);
        double ny = Math.sin(Math.cos(vector.x) * vector.y * vector.x);
        return new Vector2D(nx, ny);
    }),

    TESTING2(vector -> {
        double nx = Math.cos(vector.y) * Math.sin(vector.x);
        double ny = vector.y / vector.x;
        return new Vector2D(nx, ny);
    }),

    NOISE(vector -> {
        double n = 2 * Math.PI * Noise.eval(vector.x, vector.y);
        return new Vector2D(Math.sin(n), Math.cos(n));
    }),

    WEIRD_CUBES(vector -> {
        double nx = Math.sin(Math.min(Math.min((vector.x - vector.x), vector.y),
                (vector.x - Math.max(Math.cos(vector.y), vector.y))));
        double ny = (Math.cos(vector.y) + Math.cos(vector.x));
        return new Vector2D(nx, ny);
    }),

    RIVER_AND_STONES(vector -> {
        double nx = vector.x * Math.cos((Math.sin(vector.length()) - Math.cos(vector.y)));
        double ny = Math.max(Math.sin(vector.x), (vector.y + vector.y));
        return new Vector2D(nx, ny);
    }),

    DEBUG(vector -> {
        if (vector.x < 10 && vector.x > -10 && vector.y < 10 && vector.y > -10) {
            return vector;
        }
        double length = vector.length();
        double nx = Math.sin(length * Math.cos(vector.y));
        double ny = Math.cos(length * Math.sin(vector.x));
        return new Vector2D(nx, ny);
    });

    public final Operation<Vector2D, Vector2D> vectorField;

    private PredefinedVectorFields(Operation<Vector2D, Vector2D> vectorField) {
        this.vectorField = vectorField;
    }

}
