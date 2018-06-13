package core;

import java.util.List;

import core.view.ColorStrategy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.MathUtil;
import vectors.Vector2D;

public class FxCanvas extends Canvas {

    private List<Vector2D> points;
    private List<Vector2D> previous;

    private double fillOpacity = 0.05;
    private ColorStrategy strategy = ColorStrategy.ANGLE;

    private double width = getWidth();
    private double height = getHeight();

    public void load(List<Vector2D> points, List<Vector2D> previous) {
        this.points = points;
        this.previous = previous;
    }

    public void draw(double minX, double maxX, double minY, double maxY) {
        GraphicsContext g = getGraphicsContext2D();
        Color fillColor = Color.color(0, 0, 0, fillOpacity);
        clearScreen(fillColor);

        width = getWidth();
        height = getHeight();

        for (int i = 0; i < points.size(); i++) {
            Vector2D vector = points.get(i);
            Vector2D prev = previous.get(i);
            double screenX = calculateScreenWidth(vector.x, minX, maxX);
            double screenY = calculateScreenHeight(vector.y, minY, maxY);

            double prevX = calculateScreenWidth(prev.x, minX, maxX);
            double prevY = calculateScreenHeight(prev.y, minY, maxY);

            Color pixelColor = getColorFor(vector, prev, strategy);
            g.setStroke(pixelColor);
            g.strokeLine(screenX, screenY, prevX, prevY);
        }
    }

    private double calculateScreenWidth(double point, double minX, double maxX) {
        return MathUtil.normalize(point, minX, maxX, 0.0, width);
    }

    private double calculateScreenHeight(double point, double minY, double maxY) {
        return height - MathUtil.normalize(point, minY, maxY, 0.0, height);
    }

    public void clearScreen(Color color) {
        GraphicsContext g = getGraphicsContext2D();
        g.setFill(color);
        g.fillRect(0, 0, width, height);
    }

    public void clearScreen() {
        clearScreen(Color.BLACK);
    }

    private Color getColorFor(Vector2D vector, Vector2D prev, ColorStrategy strategy) {
        if (strategy == ColorStrategy.DISTANCE) {
            return strategy.apply(vector);
        }
        return strategy.apply(vector.subtract(prev));
    }

    public void setFillOpacity(double opacity) {
        this.fillOpacity = MathUtil.clamp(opacity, 0, 1);
    }

    public void setColorStrategy(ColorStrategy strategy) {
        this.strategy = strategy;
    }

}
