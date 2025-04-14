package dev.corgitaco.corgisdatastructures;

public record SimplePosition(double x, double y, double z) implements Position {
    @Override
    public boolean is2D() {
        return false;
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
