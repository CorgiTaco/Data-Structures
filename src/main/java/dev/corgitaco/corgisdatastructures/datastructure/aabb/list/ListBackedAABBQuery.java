package dev.corgitaco.corgisdatastructures.datastructure.aabb.list;

import dev.corgitaco.corgisdatastructures.coord.box.Box;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ListBackedAABBQuery<VALUE> implements AABBQuery<VALUE> {

    private final List<Entry<VALUE>> entries = new ArrayList<>();
    private Box bound;

    @Override
    public void insertDirect(Box bound, VALUE value) {
        if (this.bound == null) {
            this.bound = bound;
        } else {
            this.bound = this.bound.encapsulate(bound);
        }
        entries.add(createEntry(bound, value));
    }

    @Override
    public boolean removeValue(@Nullable Box bound, VALUE value, OverlapFunction overlapFunction, BiPredicate<VALUE, VALUE> test) {
        if (bound != null) {
            if (this.bound == null || !this.bound.intersects(bound)) {
                return false;
            }
        }
        return entries.removeIf(e -> {
            if (bound == null) {
                return test.test(e.value(), value);
            }
            if (overlapFunction.test(bound, e.bound())) {
                return test.test(e.value(), value);
            }

            return false;
        });
    }

    @Override
    public boolean removeArea(Box bound, OverlapFunction overlapFunction) {
        if (this.bound == null || !this.bound.intersects(bound)) {
            return false;
        }
        return entries.removeIf(e -> overlapFunction.test(bound, e.bound()));
    }

    @Override
    public boolean query(@Nullable Box bound, OverlapFunction overlapFunction, Predicate<Entry<VALUE>> test) {
        if (this.bound == null || (bound != null && !this.bound.intersects(bound))) {
            return false;
        }
        if (bound == null) {
            for (Entry<VALUE> entry : entries) {
                if (test.test(entry)) {
                    return true;
                }
            }
        } else {
            for (Entry<VALUE> entry : entries) {
                if (overlapFunction.test(bound, entry.bound()) && test.test(entry)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.bound = null;
        entries.clear();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public void nodeView(Box bound, BiFunction<Box, Box, Boolean> overlapFunction, int maxDepth, BiConsumer<Integer, Box> consumer) {

    }

    @Override
    public int size() {
        return this.entries.size();
    }

    @Override
    public Box bound() {
        return this.bound;
    }
}
