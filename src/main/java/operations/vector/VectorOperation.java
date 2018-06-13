package operations.vector;

import operations.EndoOperation;
import operations.Operation;
import vectors.Vector2D;

public interface VectorOperation extends EndoOperation<Vector2D> {

    static VectorOperation of(VectorOperation source) {
        return source;
    }

    static VectorOperation of(Operation<Vector2D, Vector2D> operation) {
        return value -> operation.execute(value);
    }
    
}
