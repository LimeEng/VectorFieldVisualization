package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import operations.Operation;
import vectors.Vector2D;

public class FieldSimulation {

    private final List<Vector2D> initialPosition = new ArrayList<>();
    private final List<Vector2D> previousPosition = new ArrayList<>();
    private final List<Vector2D> points = new ArrayList<>();
    private final List<Integer> pointAge = new ArrayList<>();

    private static final int MAX_AGE = 100;

    public double MAX_VALUE_X;
    public double MIN_VALUE_X;
    public double MAX_VALUE_Y;
    public double MIN_VALUE_Y;
    private double STEP;
    private final double DISTORT = 0.003;

    private final Random rnd = new Random();

    private boolean randomAge;
    private double vectorScale = 0.01;
    private Operation<Vector2D, Vector2D> vectorField;

    public FieldSimulation(double minX, double maxX, double minY, double maxY, int nbrOfPoints, boolean randomAge,
            Operation<Vector2D, Vector2D> vectorField) {
        reset(minX, maxX, minY, maxY, nbrOfPoints, randomAge, vectorField);
    }

    public void reset(double minX, double maxX, double minY, double maxY, int nbrOfPoints, boolean randomAge,
            Operation<Vector2D, Vector2D> vectorField) {
        MIN_VALUE_X = minX;
        MAX_VALUE_X = maxX;
        MIN_VALUE_Y = minY;
        MAX_VALUE_Y = maxY;
        this.randomAge = randomAge;
        this.vectorField = vectorField;
        // Evenly distribute the number of points in the rectangle
        double width = MAX_VALUE_X - MIN_VALUE_X;
        double height = MAX_VALUE_Y - MIN_VALUE_Y;
        STEP = calculateSteppingDistance(width, height, nbrOfPoints);

        generatePoints();
    }

    private double calculateSteppingDistance(double width, double height, int nbrOfPoints) {
        double totalArea = width * height;
        double pointArea = totalArea / nbrOfPoints;
        return Math.sqrt(pointArea);
    }

    private void generatePoints() {
        reset();

        for (double x = MIN_VALUE_X; x <= MAX_VALUE_X; x += STEP) {
            for (double y = MIN_VALUE_Y; y <= MAX_VALUE_Y; y += STEP) {
                Vector2D distort = Vector2D.of(rnd.nextGaussian() * DISTORT, rnd.nextGaussian() * DISTORT);
                Vector2D vector = Vector2D.of(x, y);
                vector = vector.add(distort);
                initialPosition.add(vector);
                points.add(vector);
                pointAge.add(generateNewAge());
                previousPosition.add(vector);
            }
        }
    }

    public void update() {
        int index = 0;
        for (Vector2D vector : points) {
            Vector2D v = vectorField.execute(vector)
                    .multiply(vectorScale);
            previousPosition.set(index, vector.subtract(v));
            vector = vector.add(v);
            points.set(index, vector);
            pointAge.set(index, pointAge.get(index) + 1);
            if (pointAge.get(index) > MAX_AGE) {
                pointAge.set(index, generateNewAge());
                Vector2D initalVector = initialPosition.get(index);
                points.set(index, initalVector);
                previousPosition.set(index, initalVector);
            }
            index++;
        }
    }

    private void reset() {
        initialPosition.clear();
        points.clear();
        pointAge.clear();
        previousPosition.clear();
    }

    private int generateNewAge() {
        if (randomAge) {
            return getRandomAge();
        }
        return 0;
    }

    private int getRandomAge() {
        return ThreadLocalRandom.current()
                .nextInt(0, MAX_AGE);
    }

    public List<Vector2D> getPoints() {
        return points;
    }

    public List<Vector2D> getPreviousPosition() {
        return previousPosition;
    }

}
