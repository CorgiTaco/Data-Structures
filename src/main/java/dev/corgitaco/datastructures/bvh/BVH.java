package dev.corgitaco.datastructures.bvh;

import dev.corgitaco.datastructures.Box;
import dev.corgitaco.datastructures.SimpleBox2D;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public interface BVH<VALUE> {

    void insert(Box bound, VALUE value);

    boolean removeValue(@Nullable Box bound, VALUE value, OverlapFunction overlapFunction, BiPredicate<VALUE, VALUE> test);

    boolean removeArea(Box bound, OverlapFunction overlapFunction);

    default boolean removeArea(@Nullable Box bound) {
        return removeArea(bound, overlapFunction());
    }

    default boolean removeValue(@Nullable Box bound, VALUE value, BiPredicate<VALUE, VALUE> test) {
        return removeValue(bound, value, overlapFunction(), test);
    }
    default boolean removeValue(VALUE value, BiPredicate<VALUE, VALUE> test) {
        return removeValue(null, value, overlapFunction(), test);
    }

    boolean query(@Nullable Box bound, OverlapFunction overlapFunction, Predicate<Entry<VALUE>> test);

    default boolean query(Box bound, Predicate<Entry<VALUE>> test) {
        return query(bound, overlapFunction(), test);
    }

    default List<Entry<VALUE>> query(Box bound) {
        return query(bound, overlapFunction());
    }

    default List<Entry<VALUE>> query(Box bound, OverlapFunction overlapFunction) {
        List<Entry<VALUE>> result = new ArrayList<>();
        query(bound, overlapFunction, entry -> {
            result.add(entry);
            return false;
        });
        return result;
    }

    default boolean hasAny(Box bound, OverlapFunction overlapFunction) {
        return query(bound, overlapFunction, entry -> true);
    }

    default boolean hasAny(Box bound) {
        return hasAny(bound, overlapFunction());
    }

    void clear();

    boolean isEmpty();

    default Entry<VALUE> createEntry(Box bound, VALUE value) {
        return new SimpleEntry<>(bound, value);
    }

    default OverlapFunction overlapFunction() {
        return Box::intersects;
    }

    void nodeView(Box bound, BiFunction<Box, Box, Boolean> overlapFunction, int maxDepth, BiConsumer<Integer, Box> consumer);




    interface Entry<VALUE> {

        Box bound();

        VALUE value();
    }

    class SimpleEntry<VALUE> implements Entry<VALUE> {
        private final Box bound;
        private final VALUE value;

        public SimpleEntry(Box bound, VALUE value) {
            this.bound = bound;
            this.value = value;
        }

        @Override
        public Box bound() {
            return bound;
        }

        @Override
        public VALUE value() {
            return value;
        }
    }

    @FunctionalInterface
    interface OverlapFunction extends BiPredicate<Box, Box> {}
}