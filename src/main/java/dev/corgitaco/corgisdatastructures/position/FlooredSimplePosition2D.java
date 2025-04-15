package dev.corgitaco.corgisdatastructures.position;

public record FlooredSimplePosition2D(int xVal, int zVal) implements Position {
    @Override
    public double x() {
        return this.xVal;
    }
    @Override
    public double y() {
        return 0;
    }

    @Override
    public double z() {
        return this.zVal;
    }

    @Override
    public Position as2D() {
        return this;
    }

    @Override
    public Position create(double x, double y, double z) {
        return new SimplePosition2D(x, z);
    }

    @Override
    public Position floor() {
        return this;
    }
}
