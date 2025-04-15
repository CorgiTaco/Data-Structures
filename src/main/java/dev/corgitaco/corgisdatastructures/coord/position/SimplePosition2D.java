package dev.corgitaco.corgisdatastructures.coord.position;

public record SimplePosition2D(double x, double z) implements Position {
    @Override
    public double y() {
        return 0;
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
        return new FlooredSimplePosition2D(this.floorX(), this.floorZ());
    }
}
