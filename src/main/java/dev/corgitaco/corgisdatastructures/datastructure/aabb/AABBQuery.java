package dev.corgitaco.corgisdatastructures.datastructure.aabb;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.box.BoxFactory;
import dev.corgitaco.corgisdatastructures.box.SimpleBox2D;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.*;

public interface AABBQuery<VALUE> {

    void insertDirect(Box bound, VALUE value);

    default void insert(Box bound, VALUE value) {
        insertDirect(convert(bound), value);
    }

    default void insert(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, VALUE value) {
        insertDirect(convert(minX, minY, minZ, maxX, maxY, maxZ), value);
    }

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

    default Box convert(Box box) {
        return convert(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ());
    }

    default Box convert(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return BoxFactory.createBox3D(false, minX, minY, minZ, maxX, maxY, maxZ);
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

    int size();

    @Nullable
    Box bound();


    static <T extends AABBQuery<Box>> T floodAABBQueryBoxes2D(T aabbStorage, long seed, int numberOfBoxes, int size, int minBoxSize, int maxBoxSize, boolean skipChecks) {
        return floodAABBQueryBoxes2D(aabbStorage, seed, numberOfBoxes, size, minBoxSize, maxBoxSize, skipChecks, false, i -> false);
    }

    static <T extends AABBQuery<Box>> T floodAABBQueryBoxes2D(T aabbStorage, long seed, int numberOfBoxes, int size, int minBoxSize, int maxBoxSize, boolean skipChecks, boolean useSimpleBox, IntPredicate test) {
        Random random = new Random(seed);
        for (int i = 0; i < numberOfBoxes; i++) {
            double minX = random.nextDouble() * size - size / 2.0;
            double minY = random.nextDouble() * size - size / 2.0;
            double boxWidth = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double boxHeight = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double maxX = minX + boxWidth;
            double maxY = minY + boxHeight;

            Box box2D = useSimpleBox ? new SimpleBox2D(minX, minY, maxX, maxY) : BoxFactory.createBox2D(true, minX, minY, maxX, maxY);
            if (skipChecks || !aabbStorage.hasAny(box2D)) {
                aabbStorage.insertDirect(box2D, box2D);
            }
            if (test.test(aabbStorage.size())) {
                break;
            }
        }

        return aabbStorage;
    }


    static <T extends AABBQuery<Box>> T floodAABBQueryBoxes3D(T aabbStorage, long seed, int numberOfBoxes, int size, int minBoxSize, int maxBoxSize, boolean skipChecks) {
        return floodAABBQueryBoxes3D(aabbStorage, seed, numberOfBoxes, size, minBoxSize, maxBoxSize, skipChecks, false, i -> false);
    }

    static <T extends AABBQuery<Box>> T floodAABBQueryBoxes3D(T aabbStorage, long seed, int numberOfBoxes, int size, int minBoxSize, int maxBoxSize, boolean skipChecks, boolean useSimpleBox, IntPredicate test) {
        Random random = new Random(seed);
        for (int i = 0; i < numberOfBoxes; i++) {
            double minX = random.nextDouble() * size - size / 2.0;
            double minY = random.nextDouble() * size - size / 2.0;
            double minZ = random.nextDouble() * size - size / 2.0;
            double boxWidth = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double boxHeight = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double boxDepth = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
            double maxX = minX + boxWidth;
            double maxY = minY + boxHeight;
            double maxZ = minZ + boxDepth;

            Box box3D = useSimpleBox ? new SimpleBox2D(minX, minY, maxX, maxY) : BoxFactory.createBox3D(true, minX, minY, minZ, maxX, maxY, maxZ);
            if (skipChecks || !aabbStorage.hasAny(box3D)) {
                aabbStorage.insertDirect(box3D, box3D);
            }
            if (test.test(aabbStorage.size())) {
                break;
            }
        }

        return aabbStorage;
    }

    interface Entry<VALUE> {

        Box bound();

        VALUE value();
    }

    record SimpleEntry<VALUE>(Box bound, VALUE value) implements Entry<VALUE> {
    }

    @FunctionalInterface
    interface OverlapFunction extends BiPredicate<Box, Box> {
    }
}