package core.view;

import java.util.function.Function;

import javafx.scene.paint.Color;
import math.MathUtil;
import vectors.Vector2D;

public enum ColorStrategy {

    UNIFORM(vector -> {
        return Color.web("#00AF5D");
    }),
    ANGLE(vector -> {
        double angle = Math.toDegrees(Math.atan2(vector.x, vector.y));
        if (angle < 0) {
            angle += 360;
        }
        return Color.hsb(angle, 1, 1, 1);
    }),
    VELOCITY(vector -> {
        double hue = MathUtil.normalize(vector.length(), 0, 1, 0, 360);
        return Color.hsb(hue, 1, 1, 1);
    }),

    DISTANCE(vector -> {
        return Color.hsb(vector.lengthSquared(), 1, 1, 1);
    });

    private Function<Vector2D, Color> color;

    private ColorStrategy(Function<Vector2D, Color> color) {
        this.color = color;
    }

    public Color apply(Vector2D vector) {
        return color.apply(vector);
    }
}
