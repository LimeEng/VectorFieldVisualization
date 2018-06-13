package math;

import java.util.Arrays;

public class RollingAverage {

    private int size;
    private double total;
    private int index;
    private double samples[];

    public RollingAverage(int size) {
        this.size = size;
        samples = new double[size];
        reset();
    }

    public void add(double x) {
        total -= samples[index];
        samples[index] = x;
        total += x;
        index++;
        if (index == size) {
            index = 0; 
        }
    }

    public double getAverage() {
        return total / size;
    }

    public void reset() {
        this.total = 0;
        this.index = 0;
        Arrays.fill(samples, 0);
    }
}