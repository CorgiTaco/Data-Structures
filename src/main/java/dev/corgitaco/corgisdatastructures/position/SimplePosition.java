package dev.corgitaco.corgisdatastructures.position;

public record SimplePosition(double x, double y, double z) implements Position {


    @Override
    public Position as2D() {
        return new SimplePosition2D(x, z);
    }

    @Override
    public Position floor() {
        return new FlooredSimplePosition3D(this.floorX(), this.floorY(), this.floorZ());
    }

    @Override
    public Position create(double x, double y, double z) {
        return new SimplePosition(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("SimplePosition{x=%.2f, y=%.2f, z=%.2f}", x, y, z);
    }
}
