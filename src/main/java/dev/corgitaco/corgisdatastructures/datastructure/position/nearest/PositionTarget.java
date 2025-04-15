package dev.corgitaco.corgisdatastructures.datastructure.position.nearest;

import dev.corgitaco.corgisdatastructures.position.Position;

import java.util.Collection;
import java.util.Collections;

public record PositionTarget<POINT extends Position>(
        POINT position
) implements NearestPosition<POINT, POINT>, Target<POINT, POINT> {

    @Override
    public Target<POINT, POINT> setPosition(POINT point, POINT o) {
        throw new IllegalArgumentException("Cannot set lowest level point, use constructor.");
    }

    @Override
    public Target<POINT, POINT> removePosition(POINT point) {
        throw new IllegalArgumentException("Cannot remove lowest level point.");
    }

    @Override
    public Target<POINT, POINT> getTarget(POINT point) {
        return this;
    }

    @Override
    public NearestPosition<POINT, POINT> makeLeaf(POINT point, POINT o) {
        throw new IllegalArgumentException("Cannot create leaf on low level point, use constructor.");
    }

    @Override
    public Target<POINT, POINT> getNearestTarget(Position point, DistanceFunction distanceFunction) {
        return this;
    }

    @Override
    public void getNearbyTargets(Position point, int maxEntries, Collection<Target<POINT, POINT>> collector, DistanceFunction distanceFunction) {
        if (collector.size() < maxEntries) {
            collector.add(this);
        }
    }

    @Override
    public void getTargetsWithinRange(Position point, double radius, Collection<Target<POINT, POINT>> collector, DistanceFunction distanceFunction) {
        if (distanceFunction.apply(point, this.position()) <= radius) {
            collector.add(this);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Collection<Target<POINT, POINT>> getTargets() {
        return Collections.singleton(this);
    }

    @Override
    public POINT value() {
        return this.position;
    }

    @Override
    public boolean clear() {
        throw new IllegalArgumentException("Cannot clear lowest level point");
    }

    @Override
    public void getTargetsInBox(Collection<Target<POINT, POINT>> data, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        boolean checkX = this.position.getX() >= minX && this.position.getX() <= maxX;
        boolean checkY = this.position.getY() >= minY && this.position.getY() <= maxY;
        boolean checkZ = this.position.getZ() >= minZ && this.position.getZ() <= maxZ;

        if (checkX && checkY && checkZ) {
            data.add(this);
        }
    }

    @Override
    public boolean hasTargetsInBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        boolean checkX = this.position.getX() >= minX && this.position.getX() <= maxX;
        boolean checkY = this.position.getY() >= minY && this.position.getY() <= maxY;
        boolean checkZ = this.position.getZ() >= minZ && this.position.getZ() <= maxZ;
        return checkX && checkY && checkZ;
    }

    @Override
    public boolean hasTargetsWithinRange(Position point, double radius, DistanceFunction distanceFunction) {
        return distanceFunction.apply(point, this.position()) <= radius;
    }
}