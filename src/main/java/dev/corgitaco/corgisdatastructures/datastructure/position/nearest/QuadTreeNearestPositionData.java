/*
 * Copyright (c) 2025 Corgi Taco.
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.corgitaco.corgisdatastructures.datastructure.position.nearest;

import dev.corgitaco.corgisdatastructures.position.Position;
import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.box.SimpleBox2D;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class QuadTreeNearestPositionData<POINT extends Position, VALUE> implements NearestPosition<POINT, VALUE> {

    private Node2D<POINT, VALUE> root;

    private Box bounds;

    @Override
    public NearestPosition<POINT, VALUE> makeLeaf(POINT point, VALUE o) {
        throw new RuntimeException("Cannot create a leaf here.");
    }

    @Override
    public Target<POINT, VALUE> setPosition(POINT point, VALUE o) {
        point = (POINT) point.as2D().floor();
        if (this.root == null || this.bounds == null) {
            this.bounds = new SimpleBox2D(point.getX(), point.getZ(), point.getX(), point.getZ());
            this.root = Node2D.fromSize((int) Math.max(bounds.xSpan(), bounds.zSpan()));
        } else {
            // Min position moved, shift all positions.
            if (point.getX() < this.bounds.minX() || point.getZ() < this.bounds.minZ()) {
                Collection<Target<POINT, VALUE>> inGlobalCoords = getTargets();
                this.bounds = this.bounds.encapsulate(point);
                int highestShiftScale = getHighestShiftScale((int) Math.max(bounds.xSpan(), bounds.zSpan()));

                if (this.root.highestShiftScale < highestShiftScale) {
                    this.root = Node2D.fromSize((int) Math.max(bounds.xSpan(), bounds.zSpan()));
                }

                for (Target<POINT, VALUE> target : inGlobalCoords) {
                    this.root.setPosition(offsetLocal(target.position()), target.value());
                }
            } else if (point.getX() > this.bounds.maxX() || point.getZ() > this.bounds.maxZ()) {
                this.bounds.encapsulate(point);
                int highestShiftScale = getHighestShiftScale((int) Math.max(bounds.xSpan(), bounds.zSpan()));

                if (this.root.highestShiftScale < highestShiftScale) {
                    Node2D<POINT, VALUE> newRoot = Node2D.fromSize((int) Math.max(bounds.xSpan(), bounds.zSpan()));
                    newRoot.insertLeaf(this.root);
                    this.root = newRoot;
                }
            }
        }
        return this.root.setPosition(offsetLocal(point), o);
    }

    @Override
    public Target<POINT, VALUE> removePosition(POINT point) {
        if (this.root == null || this.bounds == null) {
            return null;
        }

        point = (POINT) point.as2D().floor();
        Target<POINT, VALUE> pointvalueTarget = this.root.removePosition(offsetLocal(point));

        if (pointvalueTarget != null) {
            if (this.root.isEmpty()) {
                this.root = null;
                this.bounds = null;
            } else {
                // Min position changed, shift all positions.
                if (pointvalueTarget.position().getX() == this.bounds.minX() || pointvalueTarget.position().getZ() == this.bounds.minZ()) {
                    Collection<Target<POINT, VALUE>> inGlobalCoords = getTargets();
                    Box newBounds = null;
                    for (Target<POINT, VALUE> target : inGlobalCoords) {
                        if (newBounds == null) {
                            newBounds = new SimpleBox2D(target.position().getX(), target.position().getZ(), target.position().getX(), target.position().getZ());
                        }
                        newBounds = newBounds.encapsulate(target.position());
                    }
                    this.bounds = newBounds;
                    int highestShiftScale = getHighestShiftScale((int) Math.max(this.bounds.xSpan(), this.bounds.zSpan()));
                    if (this.root.highestShiftScale < highestShiftScale) {
                        this.root = Node2D.fromSize((int) Math.max(this.bounds.xSpan(), this.bounds.zSpan()));
                        for (Target<POINT, VALUE> target : inGlobalCoords) {
                            this.root.setPosition(offsetLocal(target.position()), target.value());
                        }
                    }
                } else if (pointvalueTarget.position().getX() == this.bounds.maxX() || pointvalueTarget.position().getZ() == this.bounds.maxZ()) {
                    int highestShiftScale = getHighestShiftScale((int) Math.max(this.root.area.xSpan(), this.root.area.zSpan()));
                    if (this.root.highestShiftScale > highestShiftScale) {
                        boolean found = false;
                        for (NearestPosition<POINT, VALUE> leaf : this.root.leafs) {
                            if (leaf != null) {
                                if (!found) {
                                    found = true;
                                    this.root = (Node2D) leaf;
                                } else {
                                    throw new IllegalArgumentException("Multiple roots detected... this should not be possible");
                                }
                            }
                        }
                    }
                }
            }
        }
        return pointvalueTarget;
    }

    @Override
    public Target<POINT, VALUE> getTarget(POINT point) {
        point = (POINT) point.as2D().floor();
        if (this.root == null || this.bounds == null) {
            return null;
        }
        Target<POINT, VALUE> target = this.root.getTarget(offsetLocal(point));
        if (target == null) {
            return null;
        }
        target = targetFactory(offsetGlobal(target.position()), target.value());
        return target;
    }

    @Override
    public Target<POINT, VALUE> getNearestTarget(Position point, DistanceFunction distanceFunction) {
        if (this.root == null || this.bounds == null) {
            return null;
        }
        point = point.as2D();
        Target<POINT, VALUE> nearestTarget = this.root.getNearestTarget(offsetToLocal(point), distanceFunction);
        if (nearestTarget == null) {
            return null;
        }
        nearestTarget = targetFactory(offsetGlobal(nearestTarget.position()), nearestTarget.value());
        return nearestTarget;
    }

    @Override
    public void getNearbyTargets(Position point, int maxEntries, Collection<Target<POINT, VALUE>> collector, DistanceFunction distanceFunction) {
        if (this.root == null || this.bounds == null) {
            return;
        }
        point = point.as2D();
        this.root.getNearbyTargets(offsetToLocal(point), maxEntries, collector, distanceFunction);
        List<Target<POINT, VALUE>> globalPositions = new ArrayList<>(collector.size());
        for (Target<POINT, VALUE> target : collector) {
            globalPositions.add(targetFactory(offsetGlobal(target.position()), target.value()));
        }
        collector.clear();
        collector.addAll(globalPositions);
    }

    @Override
    public void getTargetsWithinRange(Position point, double radius, Collection<Target<POINT, VALUE>> collector, DistanceFunction distanceFunction) {
        if (this.root == null || this.bounds == null) {
            return;
        }
        point = point.as2D();
        this.root.getTargetsWithinRange(offsetToLocal(point), radius, collector, distanceFunction);
        List<Target<POINT, VALUE>> globalPositions = new ArrayList<>(collector.size());
        for (Target<POINT, VALUE> target : collector) {
            globalPositions.add(targetFactory(offsetGlobal(target.position()), target.value()));
        }

        collector.clear();
        collector.addAll(globalPositions);
    }

    @Override
    public void getTargetsInBox(Collection<Target<POINT, VALUE>> data, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        if (this.root == null || this.bounds == null) {
            return;
        }
        minX -= this.bounds.minX();
        minY -= this.bounds.minY();
        minZ -= this.bounds.minZ();
        maxX -= this.bounds.minX();
        maxY -= this.bounds.minY();
        maxZ -= this.bounds.minZ();

        this.root.getTargetsInBox(data, minX, minY, minZ, maxX, maxY, maxZ);
        List<Target<POINT, VALUE>> globalPositions = new ArrayList<>(data.size());
        for (Target<POINT, VALUE> target : data) {
            globalPositions.add(targetFactory(offsetGlobal(target.position()), target.value()));
        }
        data.clear();
        data.addAll(globalPositions);
    }

    @Override
    public boolean hasTargetsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        if (this.root == null || this.bounds == null) {
            return false;
        }
        minX -= this.bounds.minX();
        minY -= this.bounds.minY();
        minZ -= this.bounds.minZ();
        maxX -= this.bounds.minX();
        maxY -= this.bounds.minY();
        maxZ -= this.bounds.minZ();
        return this.root.hasTargetsInBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public boolean hasTargetsWithinRange(Position point, double radius, DistanceFunction distanceFunction) {
        if (this.root == null || this.bounds == null) {
            return false;
        }
        point = point.as2D();

        return this.root.hasTargetsWithinRange(offsetToLocal(point), radius, distanceFunction);
    }

    @Override
    public boolean isEmpty() {
        if (this.root == null || this.bounds == null) {
            return true;
        }
        return this.root.isEmpty();
    }

    @Override
    public Collection<Target<POINT, VALUE>> getTargets() {
        if (this.root == null || this.bounds == null) {
            return Collections.emptyList();
        }
        Collection<Target<POINT, VALUE>> targets = this.root.getTargets();
        List<Target<POINT, VALUE>> globalPositions = new ArrayList<>(targets.size());
        for (Target<POINT, VALUE> target : targets) {
            globalPositions.add(targetFactory(offsetGlobal(target.position()), target.value()));
        }
        return globalPositions;
    }

    @Override
    public boolean clear() {
        this.root = null;
        this.bounds = null;
        return true;
    }

    private POINT offsetLocal(POINT point) {
        Position position = point.create(point.getX() - this.bounds.minX(), point.getY() - this.bounds.minY(), point.getZ() - this.bounds.minZ());
        return (POINT) position;
    }

    private POINT offsetGlobal(POINT point) {
        Position position = point.create(point.getX() + this.bounds.minX(), point.getY() + this.bounds.minY(), point.getZ() + this.bounds.minZ());
        return (POINT) position;
    }

    private Position offsetToGlobal(Position point) {
        return point.create(point.getX() + this.bounds.minX(), point.getY() + this.bounds.minY(), point.getZ() + this.bounds.minZ());
    }

    private Position offsetToLocal(Position point) {
        return point.create(point.getX() - this.bounds.minX(), point.getY() - this.bounds.minY(), point.getZ() - this.bounds.minZ());
    }

    public static class Node2D<POINT extends Position, VALUE> implements NearestPosition<POINT, VALUE> {


        private final NearestPosition<POINT, VALUE>[] leafs;
        protected final byte bitShiftScale;
        protected final byte highestShiftScale;

        @Nullable
        private Box area = null;

        public Node2D(int highestShiftScale) {
            this((byte) 0, (byte) highestShiftScale, 2); // Highest level
        }


        public Node2D() {
            this((byte) 0, (byte) 31, 2); // Highest level
        }

        public static <POINT extends Position, VALUE> Node2D<POINT, VALUE> fromSize(int xzSize) {
            return new Node2D<>(getHighestShiftScale(xzSize));
        }

        public Node2D(byte bitShiftScale, byte highestShiftScale, int rowSize) {
            this.bitShiftScale = bitShiftScale;
            this.highestShiftScale = highestShiftScale;
            if (bitShiftScale < 0 || bitShiftScale > Integer.SIZE - 1) {
                throw new IllegalArgumentException("bitShiftScale must be between 0 and 31");
            }

            if (rowSize < 2) {
                throw new IllegalArgumentException("rowSize must be greater than 1");
            }

            int smallestEncompassingPowerOfTwo = Position.smallestEncompassingPowerOfTwo(rowSize);
            if (smallestEncompassingPowerOfTwo != rowSize) {
                System.out.println("rowSize is not a power of two, rounding up to the nearest power of two...");
                rowSize = smallestEncompassingPowerOfTwo;
            }

            this.leafs = new NearestPosition[rowSize * rowSize];
        }

        @Override
        public Target<POINT, VALUE> setPosition(POINT position, VALUE o) {
            position = (POINT) position.as2D().floor();
            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);
            if (area == null) {
                this.area = new SimpleBox2D(x, z, x, z);
            } else {
                this.area = this.area.encapsulate(position);
            }

            return setPositionRecursively(position, o, getIndex(xIndex, zIndex));
        }

        private Target<POINT, VALUE> setPositionRecursively(POINT position, VALUE o, int index) {
            if (bitShiftScale == this.highestShiftScale) {
                if (leafs[index] == null) {
                    leafs[index] = targetFactory(position, o);
                    return null;
                } else {
                    return (Target<POINT, VALUE>) leafs[index];
                }
            }

            if (leafs[index] == null) {
                leafs[index] = makeLeaf(position, o);
            }

            return leafs[index].setPosition(position, o);
        }

        @Override
        public NearestPosition<POINT, VALUE> makeLeaf(POINT position, VALUE o) {
            return new Node2D((byte) (bitShiftScale + 1), this.highestShiftScale, rowSize());
        }

        @Override
        public Target<POINT, VALUE> getNearestTarget(Position position, DistanceFunction distanceFunction) {
            if (this.area == null || !this.area.contains(position)) {
                return null;
            }
            position = position.as2D().floor();

            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);

            Target<POINT, VALUE> nearest = null;
            double nearestDistance = Double.MAX_VALUE;

            for (int i = 0; i < rowSize(); i++) {
                int[][] distance = SPIRAL_FAST_2D[i];
                boolean foundInThisRing = false;

                for (int[] offsetPosition : distance) {
                    int offsetXIndex = offsetPosition[0] + xIndex;
                    int offsetZIndex = offsetPosition[1] + zIndex;

                    // Skip out-of-bounds indices
                    if (offsetXIndex < 0 || offsetXIndex >= rowSize() ||
                            offsetZIndex < 0 || offsetZIndex >= rowSize()) {
                        continue;
                    }

                    NearestPosition<POINT, VALUE> offsetNearestPosition = leafs[getIndex(offsetXIndex, offsetZIndex)];
                    if (offsetNearestPosition == null) {
                        continue;
                    }

                    Target<POINT, VALUE> offsetNearest = offsetNearestPosition.getNearestTarget(position, distanceFunction);
                    if (offsetNearest == null) {
                        continue;
                    }

                    double distanceFromNewPosition = distanceFunction.apply(offsetNearest.position(), position);
                    if (nearest == null || distanceFromNewPosition < nearestDistance) {
                        nearest = offsetNearest;
                        nearestDistance = distanceFromNewPosition;
                        foundInThisRing = true;
                    }
                }

                // If we found a position in any ring beyond the first, return it immediately
                // This is a key optimization - positions in further rings will be farther away
                if (i > 0 && foundInThisRing) {
                    return nearest;
                }
            }

            return nearest;
        }

        @Override
        public void getNearbyTargets(Position position, int maxEntries, Collection<Target<POINT, VALUE>> dataCollector, DistanceFunction distanceFunction) {
            if (this.area == null || !this.area.contains(position)) {
                return;
            }
            position = position.as2D().floor();
            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);

            for (int i = 0; i < rowSize(); i++) {
                int[][] distance = SPIRAL_FAST_2D[i];
                for (int[] offsetPosition : distance) {
                    int offsetX = offsetPosition[0];
                    int offsetZ = offsetPosition[1];

                    int offsetXIndex = offsetX + xIndex;
                    int offsetZIndex = offsetZ + zIndex;
                    int index = getIndex(offsetXIndex, offsetZIndex);

                    if (offsetXIndex < 0 || offsetXIndex >= rowSize() || offsetZIndex < 0 || offsetZIndex >= rowSize()) {
                        continue;
                    }

                    NearestPosition<POINT, VALUE> offsetNearestPosition = leafs[index];
                    if (offsetNearestPosition != null) {
                        offsetNearestPosition.getNearbyTargets(position, maxEntries, dataCollector, distanceFunction);
                        if (dataCollector.size() >= maxEntries) {
                            return;
                        }
                    }
                }
            }
        }


        @Override
        public void getTargetsWithinRange(Position position, double radius, Collection<Target<POINT, VALUE>> collector, DistanceFunction distanceFunction) {
            if (this.area == null || !this.area.contains(position)) {
                return;
            }
            position = position.as2D().floor();

            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);

            for (int i = 0; i < rowSize(); i++) {
                int[][] distance = SPIRAL_FAST_2D[i];
                for (int[] offsetPosition : distance) {
                    int offsetX = offsetPosition[0];
                    int offsetZ = offsetPosition[1];

                    int offsetXIndex = offsetX + xIndex;
                    int offsetZIndex = offsetZ + zIndex;
                    if (offsetXIndex < 0 || offsetXIndex >= rowSize() || offsetZIndex < 0 || offsetZIndex >= rowSize()) {
                        continue;
                    }

                    NearestPosition<POINT, VALUE> offsetNearestPosition = leafs[getIndex(offsetXIndex, offsetZIndex)];
                    if (offsetNearestPosition != null) {
                        offsetNearestPosition.getTargetsWithinRange(position, radius, collector, distanceFunction);
                    }
                }
            }
        }

        private int getXZIndex(int coord) {
            return (coord >> (this.highestShiftScale - this.bitShiftScale)) & (rowSize() - 1);
        }

        public int rowSize() {
            return this.leafs.length >> 1;
        }


        private int getIndex(int x, int z) {
            return x * rowSize() + z;
        }

        @Override
        public boolean isEmpty() {
            for (NearestPosition<POINT, VALUE> leaf : leafs) {
                if (leaf != null && !leaf.isEmpty()) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public Target<POINT, VALUE> getTarget(POINT position) {
            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);

            int index = getIndex(xIndex, zIndex);
            NearestPosition<POINT, VALUE> nearestPosition = leafs[index];
            if (nearestPosition != null) {
                return nearestPosition.getTarget(position);
            }
            return null;
        }

        @Override
        public Collection<Target<POINT, VALUE>> getTargets() {
            List<Target<POINT, VALUE>> positions = new ArrayList<>();
            for (NearestPosition<POINT, VALUE> leaf : leafs) {
                if (leaf != null && !leaf.isEmpty()) {
                    positions.addAll(leaf.getTargets());
                }
            }

            return Collections.unmodifiableList(positions);
        }

        @Override
        public boolean clear() {
            boolean result = false;
            for (NearestPosition<POINT, VALUE> leaf : this.leafs) {
                if (leaf != null) {
                    result = true;
                    break;
                }
            }

            Arrays.fill(this.leafs, null);
            this.area = null;
            return result;
        }

        @Override
        public boolean hasTargetsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            if (this.area == null || !this.area.intersects2D(minX, minZ, maxX, maxZ)) {
                return false;
            }

            int minXIndex = getXZIndex((int) minX);
            int minZIndex = getXZIndex((int) minZ);

            int maxXIndex = getXZIndex((int) maxX);
            int maxZIndex = getXZIndex((int) maxZ);

            int startXIndex = Math.min(minXIndex, maxXIndex) - 1;
            int startZIndex = Math.min(minZIndex, maxZIndex) - 1;

            int endXIndex = Math.max(minXIndex, maxXIndex) + 1;
            int endZIndex = Math.max(minZIndex, maxZIndex) + 1;

            for (int xIndex = startXIndex; xIndex <= endXIndex; xIndex++) {
                for (int zIndex = startZIndex; zIndex <= endZIndex; zIndex++) {
                    if (xIndex < 0 || xIndex >= rowSize() || zIndex < 0 || zIndex >= rowSize()) {
                        continue;
                    }

                    NearestPosition<POINT, VALUE> offsetNearestPosition = leafs[getIndex(xIndex, zIndex)];
                    if (offsetNearestPosition != null && offsetNearestPosition.hasTargetsInBox(minX, minY, minZ, maxX, maxY, maxZ)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean hasTargetsWithinRange(Position position, double radius, DistanceFunction distanceFunction) {
            if (this.area == null || !this.area.contains(position)) {
                return false;
            }

            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);

            for (int i = 0; i < rowSize(); i++) {
                int[][] distance = SPIRAL_FAST_2D[i];
                for (int[] offsetPosition : distance) {
                    int offsetX = offsetPosition[0];
                    int offsetZ = offsetPosition[1];

                    int offsetXIndex = offsetX + xIndex;
                    int offsetZIndex = offsetZ + zIndex;
                    if (offsetXIndex < 0 || offsetXIndex >= rowSize() || offsetZIndex < 0 || offsetZIndex >= rowSize()) {
                        continue;
                    }

                    NearestPosition<POINT, VALUE> offsetNearestPosition = leafs[getIndex(offsetXIndex, offsetZIndex)];
                    if (offsetNearestPosition != null && offsetNearestPosition.hasTargetsWithinRange(position, radius, distanceFunction)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void getTargetsInBox(Collection<Target<POINT, VALUE>> data, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            if (this.area == null || !this.area.intersects2D(minX, minZ, maxX, maxZ)) {
                return;
            }

            int minXIndex = getXZIndex((int) minX);
            int minZIndex = getXZIndex((int) minZ);

            int maxXIndex = getXZIndex((int) maxX);
            int maxZIndex = getXZIndex((int) maxZ);

            int startXIndex = Math.min(minXIndex, maxXIndex) - 1;
            int startZIndex = Math.min(minZIndex, maxZIndex) - 1;

            int endXIndex = Math.max(minXIndex, maxXIndex) + 1;
            int endZIndex = Math.max(minZIndex, maxZIndex) + 1;


            for (int xIndex = startXIndex; xIndex <= endXIndex; xIndex++) {
                for (int zIndex = startZIndex; zIndex <= endZIndex; zIndex++) {
                    if (xIndex < 0 || xIndex >= rowSize() || zIndex < 0 || zIndex >= rowSize()) {
                        continue;
                    }

                    NearestPosition<POINT, VALUE> offsetNearestPosition = leafs[getIndex(xIndex, zIndex)];
                    if (offsetNearestPosition != null) {
                        offsetNearestPosition.getTargetsInBox(data, minX, minY, minZ, maxX, maxY, maxZ);
                    }
                }
            }
        }

        @Override
        public Target<POINT, VALUE> removePosition(POINT position) {
            int x = position.floorX();
            int z = position.floorZ();

            int xIndex = getXZIndex(x);
            int zIndex = getXZIndex(z);

            Target<POINT, VALUE> positionvalueTarget = removePositionRecursively(position, getIndex(xIndex, zIndex));

            if (positionvalueTarget != null) {
                recalculateArea();
            }
            return positionvalueTarget;
        }


        private void recalculateArea() {
            Collection<Target<POINT, VALUE>> targets = getTargets();
            this.area = null;
            for (Target<POINT, VALUE> target : targets) {
                if (this.area == null) {
                    this.area = new SimpleBox2D(target.position().getX(), target.position().getZ(), target.position().getX(), target.position().getZ());
                }
                this.area = this.area.encapsulate(target.position());
            }
        }

        private Target<POINT, VALUE> removePositionRecursively(POINT position, int index) {
            NearestPosition<POINT, VALUE> nearestPosition = leafs[index];
            if (nearestPosition != null) {
                if (bitShiftScale == this.highestShiftScale) {
                    leafs[index] = null;
                    return (Target<POINT, VALUE>) nearestPosition;
                }

                Target<POINT, VALUE> removedPosition = nearestPosition.removePosition(position);
                if (nearestPosition.isEmpty()) {
                    leafs[index] = null;
                }
                return removedPosition;
            }
            return null;
        }

        private boolean insertLeaf(Node2D<POINT, VALUE> leaf) {
            if (this.area == null) {
                this.area = leaf.area;
            } else {
                throw new RuntimeException("Attempted to set multiple roots");
            }
            Position min = this.area.min();
            Position max = this.area.max();

            int xIndex = getXZIndex(min.floorX());
            int zIndex = getXZIndex(min.floorZ());
            int index = getIndex(xIndex, zIndex);

            if (index != getIndex(leaf.getXZIndex(max.floorX()), leaf.getXZIndex(max.floorZ()))) {
                throw new RuntimeException("Occupying multiple leafs... this should be impossible");
            }
            this.leafs[index] = leaf;
            return true;
        }
    }

    private static byte getHighestShiftScale(int xzSize) {
        return (byte) (Integer.SIZE - Integer.numberOfLeadingZeros(xzSize));
    }
}
