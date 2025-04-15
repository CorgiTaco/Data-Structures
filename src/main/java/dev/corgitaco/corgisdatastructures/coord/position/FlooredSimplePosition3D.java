package dev.corgitaco.corgisdatastructures.coord.position;

public record FlooredSimplePosition3D(int xVal, int yVal, int zVal) implements Position {
    @Override
    public double x() {
        return this.xVal;
    }
    @Override
    public double y() {
        return this.yVal;
    }

    @Override
    public double z() {
        return this.zVal;
    }

    @Override
    public Position as2D() {
        return new FlooredSimplePosition2D(this.xVal, this.zVal);
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
