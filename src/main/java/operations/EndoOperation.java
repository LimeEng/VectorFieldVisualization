package operations;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

public interface EndoOperation<R> extends Operation<R, R> {

    static <R> Operation<R, R> loop(Operation<R, R> operation, int times) {
        return value -> {
            R result = value;
            for (int i = 0; i < times; i++) {
                result = operation.execute(result);
            }
            return result;
        };
    }

    static <R> Operation<R, R> loopWhile(Operation<R, R> operation, Predicate<R> pred) {
        return value -> {
            R result = value;
            while (pred.test(result)) {
                result = operation.execute(result);
            }
            return result;
        };
    }

    static <R> Operation<R, R> conditional(Predicate<R> pred, Operation<R, R> truth, Operation<R, R> falsey) {
        return value -> {
            if (pred.test(value)) {
                return truth.execute(value);
            }
            return falsey.execute(value);
        };
    }

    static <R> EndoOperation<R> conditional(List<Entry<Predicate<R>, EndoOperation<R>>> list,
            EndoOperation<R> defaultOp) {
        return value -> {
            return list.stream()
                    .filter(e -> e.getKey().test(value))
                    .map(Entry::getValue)
                    .findFirst()
                    .orElse(defaultOp)
                    .execute(value);
        };
    }
}
