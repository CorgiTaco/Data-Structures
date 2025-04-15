package dev.corgitaco.corgisdatastructures.coord.box;

public record SimpleBox2D(double minX, double minZ, double maxX, double maxZ) implements Box {
    @Override
    public double minY() {
        return 0;
    }

    @Override
    public double maxY() {
        return 0;
    }

    @Override
    public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new SimpleBox2D(minX, minZ, maxX, maxZ);
    }

    @Override
    public Box as2D() {
        return this;
    }
}
