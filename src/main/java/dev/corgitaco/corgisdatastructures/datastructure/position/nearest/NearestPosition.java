/*
 * Copyright (c) 2025 Corgi Taco.
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.corgitaco.corgisdatastructures.datastructure.position.nearest;

import dev.corgitaco.corgisdatastructures.coord.position.Position;
import dev.corgitaco.corgisdatastructures.coord.position.SimplePosition;
import dev.corgitaco.corgisdatastructures.coord.box.Box;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface NearestPosition<POINT extends Position, VALUE> {
    int[][][] SPIRAL_FAST_2D = spiral2D(32);
    int[][][] SPIRAL_FAST_3D = spiral3D(32);

    SimplePosition NEGATIVE_INFINITE = new SimplePosition(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    NearestPosition<POINT, VALUE> makeLeaf(POINT point, VALUE o);

    Target<POINT, VALUE> setPosition(POINT point, VALUE o);

    Target<POINT, VALUE> removePosition(POINT point);

    Target<POINT, VALUE> getTarget(POINT point);

    Target<POINT, VALUE> getNearestTarget(Position point, DistanceFunction distanceFunction);

    void getNearbyTargets(Position point, int maxEntries, Collection<Target<POINT, VALUE>> collector, DistanceFunction distanceFunction);

    void getTargetsWithinRange(Position point, double radius, Collection<Target<POINT, VALUE>> collector, DistanceFunction distanceFunction);

    default Collection<Target<POINT, VALUE>>  getTargetsWithinRange(Position point, double radius, DistanceFunction distanceFunction) {
        List<Target<POINT, VALUE>> pointsWithinRange = new ArrayList<>();
        getTargetsWithinRange(point, radius, pointsWithinRange, distanceFunction);
        Collections.sort(pointsWithinRange, targetSorted(point));
        return pointsWithinRange;
    }

    void getTargetsInBox(Collection<Target<POINT, VALUE>> data, double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

    boolean hasTargetsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

    boolean hasTargetsWithinRange(Position point, double radius, DistanceFunction distanceFunction);

    boolean isEmpty();

    Collection<Target<POINT, VALUE>> getTargets();

    boolean clear();

    /**
     * @return true if the point changed
     */
    default boolean didSetPosition(POINT point, VALUE o) {
        Target<POINT, VALUE> pointvalueTarget = setPosition(point, o);
        if (pointvalueTarget == null) {
            return true;
        }
        return !pointvalueTarget.value().equals(o) && !pointvalueTarget.position().equals(point);
    }

    @Nullable
    default <TARGET extends Target<POINT, VALUE> & NearestPosition<POINT, VALUE>> TARGET targetFactory(POINT point, VALUE o) {
        return (TARGET) new StandardTarget<>(point, o);
    }

    default Collection<Target<POINT, VALUE>> getNearbyTargets(Position point, int maxEntries, DistanceFunction distanceFunction) {
        List<Target<POINT, VALUE>> pointsWithinRange = new ArrayList<>();
        getNearbyTargets(point, maxEntries, pointsWithinRange, distanceFunction);
        return pointsWithinRange.stream().sorted(targetSorted(point)).toList();
    }


    default void removePositionsWithinRange(Target<POINT, VALUE> point, int range, DistanceFunction distanceFunction) {
        Collection<Target<POINT, VALUE>> pointsWithinRange = new ArrayList<>();
        getTargetsWithinRange(point.position(), range, pointsWithinRange, distanceFunction);
        for (Target<POINT, VALUE> vec3i : pointsWithinRange) {
            removePosition(vec3i.position());
        }
    }


    default boolean hasTargetsInBox(Position min, Position max) {
        return hasTargetsInBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    default boolean hasTargetsInBox(Box box) {
        return hasTargetsInBox(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ());
    }

    default boolean hasTargetsInBox(Position center, int radius) {
        return hasTargetsInBox(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius);
    }

    default Collection<Target<POINT, VALUE>> getTargetsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        Collection<Target<POINT, VALUE>> pointsWithinRange = new ArrayList<>();
        getTargetsInBox(pointsWithinRange, minX, minY, minZ, maxX, maxY, maxZ);
        return pointsWithinRange.stream().sorted(Comparator.comparingDouble(value -> value.position().squaredManhattanDistance(NEGATIVE_INFINITE))).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    default Collection<Target<POINT, VALUE>> getTargetsInBox(Position min, Position max) {
        return getTargetsInBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    default Collection<Target<POINT, VALUE>> getTargetsInBox(Box box) {
        return getTargetsInBox(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ());
    }

    default Collection<Target<POINT, VALUE>> getTargetsInBox(Position center, double radius) {
        return getTargetsInBox(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius);
    }

    default void removePositionsInBox(Position min, Position max) {
        Collection<Target<POINT, VALUE>> pointsWithinRange = getTargetsInBox(min, max);
        for (Target<POINT, VALUE> vec3i : pointsWithinRange) {
            removePosition(vec3i.position());
        }
    }

    default void removePositionsInBox(Box box) {
        removePositionsInBox(box.min(), box.max());
    }

    default void removePositionsInBox(Position center, int radius) {
        removePositionsInBox(new SimplePosition(center.getX() - radius, center.getY() - radius, center.getZ() - radius), new SimplePosition(center.getX() + radius, center.getY() + radius, center.getZ() + radius));
    }

    @Nullable
    default <OP extends Position> POINT getNearestPosition(OP point, DistanceFunction distanceFunction) {
        Target<POINT, VALUE> nearestTarget = getNearestTarget(point, distanceFunction);
        if (nearestTarget == null) {
            return null;
        }
        return nearestTarget.position();
    }

    default Collection<POINT> getPositionsInBox(POINT min, POINT max) {
        return getPositions(getTargetsInBox(min, max));
    }

    default Collection<POINT> getPositionsInBox(Position center, int radius) {
        return getPositions(getTargetsInBox(center, radius));
    }

    static double chebyshevDistance(Position min, Position max) {
        return chebyshevDistance(min.getX(), min.getZ(), max.getX(), max.getZ());
    }

    static double chebyshevDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        double dz = Math.abs(z2 - z1);
        return Math.max(dx, Math.max(dy, dz));
    }

    static double chebyshevDistance(double x1, double y1, double x2, double y2) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        return Math.max(dx, dy);
    }

    private List<POINT> getPositions(Collection<Target<POINT, VALUE>> pointsInBox) {
        List<POINT> points = new ArrayList<>();
        for (Target<POINT, VALUE> inBox : pointsInBox) {
            points.add(inBox.position());
        }
        return Collections.unmodifiableList(points);
    }

    static int[][][] spiral2D(int size) {
        Map<Integer, List<int[]>> distanceMap = new TreeMap<>();
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                int distance = (int) NearestPosition.chebyshevDistance(0, 0, x, z);
                distanceMap.computeIfAbsent(distance, dist -> new ArrayList<>()).add(new int[]{x, z});
            }
        }

        List<int[][]> offsets = new ArrayList<>();

        for (List<int[]> value : distanceMap.values()) {
            offsets.add(value.toArray(int[][]::new));
        }
        return offsets.toArray(new int[offsets.size()][][]);
    }

    static int[][][] spiral3D(int size) {
        Map<Integer, List<int[]>> distanceMap = new TreeMap<>();
        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                for (int z = -size; z <= size; z++) {
                    int distance = (int) NearestPosition.chebyshevDistance(0, 0, 0, x, y, z);
                    distanceMap.computeIfAbsent(distance, dist -> new ArrayList<>()).add(new int[]{x, y, z});
                }
            }
        }

        List<int[][]> offsets = new ArrayList<>();

        for (List<int[]> value : distanceMap.values()) {
            offsets.add(value.toArray(int[][]::new));
        }
        return offsets.toArray(new int[offsets.size()][][]);
    }


    static <P extends Position, T> Comparator<Target<P, T>> targetSorted(Position point) {
        return Comparator.comparingDouble((Target<P, T> value) -> value.position().squaredEuclideanDistance(NEGATIVE_INFINITE)).thenComparingDouble(value -> value.position().squaredEuclideanDistance(point));
    }

    static Comparator<Position> pointsSorted(Position point) {
        return Comparator.comparingDouble((Position value) -> value.squaredEuclideanDistance(NEGATIVE_INFINITE)).thenComparingDouble(value -> value.squaredEuclideanDistance(point));
    }

    @FunctionalInterface
    interface DistanceFunction {
        double apply(Position point1, Position point2);
    }
}
