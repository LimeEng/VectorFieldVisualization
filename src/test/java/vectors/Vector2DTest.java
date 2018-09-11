package vectors;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Vector2DTest {

    @Test
    public void testConstructor() {
        for (double i = -5; i < 5; i += 0.3) {
            for (double k = -5; k < 5; k += 0.3) {
                testConstructor(i, k);
                testConstructor(Vector2D.of(i, k));
            }
        }
    }

    @Test
    public void testEmptyConstructor() {
        Vector2D vector = Vector2D.zero();
        assertEquals(0, vector.x, 0);
        assertEquals(0, vector.y, 0);
        assertEquals(0, vector.getX(), 0);
        assertEquals(0, vector.getY(), 0);
    }

    private void testConstructor(double x, double y) {
        Vector2D vector = Vector2D.of(x, y);
        assertEquals(x, vector.x, 0);
        assertEquals(y, vector.y, 0);
        assertEquals(x, vector.getX(), 0);
        assertEquals(y, vector.getY(), 0);
    }

    private void testConstructor(Vector2D vector) {
        testConstructor(vector.x, vector.y);
    }
}
