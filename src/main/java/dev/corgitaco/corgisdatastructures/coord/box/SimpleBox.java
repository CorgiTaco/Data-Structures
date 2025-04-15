package dev.corgitaco.corgisdatastructures.coord.box;

public record SimpleBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) implements Box {

    @Override
    public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new SimpleBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public Box as2D() {
        return new SimpleBox2D(minX, minZ, maxX, maxZ);
    }
}
