package dev.corgitaco.corgisdatastructures.coord.box;

import dev.corgitaco.corgisdatastructures.coord.position.Position;
import dev.corgitaco.corgisdatastructures.coord.position.SimplePosition;

import java.text.DecimalFormat;

public interface Box {

    double minX();

    double minY();

    double minZ();

    double maxX();

    double maxY();

    double maxZ();

    default Position min() {
        return new SimplePosition(minX(), minY(), minZ());
    }

    default Position max() {
        return new SimplePosition(maxX(), maxY(), maxZ());
    }

    Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

    Box as2D();

    default double xSpan() {
        return maxX() - minX();
    }

    default double ySpan() {
        return maxY() - minY();
    }

    default double zSpan() {
        return maxZ() - minZ();
    }

    default Position center() {
        return new SimplePosition(
                (minX() + maxX()) / 2,
                (minY() + maxY()) / 2,
                (minZ() + maxZ()) / 2
        );
    }

    default Box floor() {
        return create(Math.floor(minX()), Math.floor(minY()), Math.floor(minZ()), Math.ceil(maxX()), Math.ceil(maxY()), Math.ceil(maxZ()));
    }


    default boolean intersects(Box other) {
        return this.minX() <= other.maxX() && this.maxX() >= other.minX() &&
                this.minY() <= other.maxY() && this.maxY() >= other.minY() &&
                this.minZ() <= other.maxZ() && this.maxZ() >= other.minZ();
    }
    default boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.minX() <= maxX && this.maxX() >= minX &&
                this.minY() <= maxY && this.maxY() >= minY &&
                this.minZ() <= maxZ && this.maxZ() >= minZ;
    }

    default boolean intersects2D(Box other) {
        return this.minX() <= other.maxX() && this.maxX() >= other.minX() &&
                this.minZ() <= other.maxZ() && this.maxZ() >= other.minZ();
    }

    default boolean intersects2D(double minX, double minZ, double maxX, double maxZ) {
        return this.minX() <= maxX && this.maxX() >= minX &&
                this.minZ() <= maxZ && this.maxZ() >= minZ;
    }

    default boolean contains(Box other) {
        return this.minX() <= other.minX() && this.maxX() >= other.maxX() &&
                this.minY() <= other.minY() && this.maxY() >= other.maxY() &&
                this.minZ() <= other.minZ() && this.maxZ() >= other.maxZ();
    }

    default boolean matches(Box other) {
        return this.minX() == other.minX() && this.maxX() == other.maxX() &&
                this.minY() == other.minY() && this.maxY() == other.maxY() &&
                this.minZ() == other.minZ() && this.maxZ() == other.maxZ();
    }

    default boolean matches2D(Box other) {
        return this.minX() == other.minX() && this.maxX() == other.maxX() &&
                this.minZ() == other.minZ() && this.maxZ() == other.maxZ();
    }

    default boolean contains(double x, double y, double z) {
        return x >= this.minX() && x <= this.maxX() &&
                y >= this.minY() && y <= this.maxY() &&
                z >= this.minZ() && z <= this.maxZ();
    }

    default boolean contains(Position position) {
        return contains(position.x(), position.y(), position.z());
    }

    default boolean isInside(int x, int y, int z) {
        return x >= this.minX() && x <= this.maxX() &&
                y >= this.minY() && y <= this.maxY() &&
                z >= this.minZ() && z <= this.maxZ();
    }


    default Box inflate(int amount) {
        return create(minX() - amount, minY() - amount, minZ() - amount, maxX() + amount, maxY() + amount, maxZ() + amount);
    }

    default Box shrink(int amount) {
        return create(minX() + amount, minY() + amount, minZ() + amount, maxX() - amount, maxY() - amount, maxZ() - amount);
    }

    default Box encapsulate(Box other) {
        return create(Math.min(minX(), other.minX()), Math.min(minY(), other.minY()), Math.min(minZ(), other.minZ()), Math.max(maxX(), other.maxX()), Math.max(maxY(), other.maxY()), Math.max(maxZ(), other.maxZ()));
    }

    default Box encapsulate(Position position) {
        return encapsulate(create(position.x(), position.y(), position.z(), position.x(), position.y(), position.z()));
    }

    default Box translate(double x, double y, double z) {
        return create(minX() + x, minY() + y, minZ() + z, maxX() + x, maxY() + y, maxZ() + z);
    }



    default double volume() {
        return (maxX() - minX()) * (maxY() - minY()) * (maxZ() - minZ());
    }

    default String areaString() {
        return areaString(new DecimalFormat("0.00"));
    }

    default String areaString(DecimalFormat format) {
        return "[" + format.format(minX()) + ", " + format.format(minY()) + ", " + format.format(minZ()) + "] to ["
                + format.format(maxX()) + ", " + format.format(maxY()) + ", " + format.format(maxZ()) + "]";
    }


}
