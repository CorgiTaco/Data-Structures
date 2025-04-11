package dev.corgitaco.datastructures;

public interface Position {

    double x();
    double y();
    double z();

    boolean is2D();

    Position create(double x, double y, double z);

    default Position add(double x, double y, double z) {
        return create(this.x() + x, this.y() + y, this.z() + z);
    }

    default Position add(Position position) {
        return add(position.x(), position.y(), position.z());
    }

    default Position subtract(double x, double y, double z) {
        return create(this.x() - x, this.y() - y, this.z() - z);
    }

    default Position subtract(Position position) {
        return subtract(position.x(), position.y(), position.z());
    }

    default Position multiply(double x, double y, double z) {
        return create(this.x() * x, this.y() * y, this.z() * z);
    }

    default Position multiply(Position position) {
        return multiply(position.x(), position.y(), position.z());
    }

    default Position divide(double x, double y, double z) {
        return create(this.x() / x, this.y() / y, this.z() / z);
    }

    default Position divide(Position position) {
        return divide(position.x(), position.y(), position.z());
    }
}
