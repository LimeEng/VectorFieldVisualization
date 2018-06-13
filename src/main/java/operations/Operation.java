package operations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public interface Operation<I, O> {

    O execute(I value);

    default <R> Operation<I, R> pipe(Operation<O, R> source) {
        return value -> source.execute(execute(value));
    }

    static <A, B> Function<A, B> convert(Operation<A, B> operation) {
        return value -> operation.execute(value);
    }

    static <A, B> Operation<A, B> convert(Function<A, B> function) {
        return value -> function.apply(value);
    }

    static <I, O> Operation<I, O> of(Operation<I, O> source) {
        return source;
    }

    static <R> Operation<R, R> of(List<? extends Operation<R, R>> operations) {
        Operation<R, R> operation = operations.remove(0);
        while (!operations.isEmpty()) {
            operation = operation.pipe(operations.remove(0));
        }
        return operation;
    }

    static <R> Operation<R, R> of(Operation<R, R>... operations) {
        return of(new LinkedList<>(Arrays.asList(operations)));
    }
}