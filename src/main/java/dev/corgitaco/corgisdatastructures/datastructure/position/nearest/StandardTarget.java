package dev.corgitaco.corgisdatastructures.datastructure.position.nearest;

import dev.corgitaco.corgisdatastructures.position.Position;

import java.util.Collection;
import java.util.Collections;

public record StandardTarget<POSITION extends Position, VALUE>(POSITION position,
                                                               VALUE value) implements NearestPosition<POSITION, VALUE>, Target<POSITION, VALUE> {

    @Override
    public Target<POSITION, VALUE> setPosition(POSITION position, VALUE o) {
        throw new IllegalArgumentException("Cannot set lowest level position, use constructor.");
    }

    @Override
    public Target<POSITION, VALUE> removePosition(POSITION position) {
        throw new IllegalArgumentException("Cannot remove lowest level position.");
    }

    @Override
    public Target<POSITION, VALUE> getTarget(POSITION position) {
        return this;
    }

    @Override
    public NearestPosition<POSITION, VALUE> makeLeaf(POSITION position, VALUE o) {
        throw new IllegalArgumentException("Cannot create leaf on low level position, use constructor.");
    }

    @Override
    public Target<POSITION, VALUE> getNearestTarget(Position position, DistanceFunction distanceFunction) {
        return this;
    }

    @Override
    public void getNearbyTargets(Position position, int maxEntries, Collection<Target<POSITION, VALUE>> collector, DistanceFunction distanceFunction) {
        if (collector.size() < maxEntries) {
            collector.add(this);
        }
    }

    @Override
    public void getTargetsWithinRange(Position position, double radius, Collection<Target<POSITION, VALUE>> collector, DistanceFunction distanceFunction) {
        if (distanceFunction.apply(position, this.position()) <= radius) {
            collector.add(this);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Collection<Target<POSITION, VALUE>> getTargets() {
        return Collections.singleton(this);
    }

    @Override
    public boolean clear() {
        throw new IllegalArgumentException("Cannot clear lowest level position");
    }

    @Override
    public void getTargetsInBox(Collection<Target<POSITION, VALUE>> data, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
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
    public boolean hasTargetsWithinRange(Position position, double radius, DistanceFunction distanceFunction) {
        return distanceFunction.apply(position, this.position()) <= radius;
    }
}