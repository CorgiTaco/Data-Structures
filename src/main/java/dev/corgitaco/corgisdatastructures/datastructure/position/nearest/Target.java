package dev.corgitaco.corgisdatastructures.datastructure.position.nearest;

import dev.corgitaco.corgisdatastructures.coord.position.Position;

import java.util.function.Supplier;

public interface Target<POINT extends Position, VALUE> extends Supplier<VALUE> {

    POINT position();

    VALUE value();

    @Override
    default VALUE get() {
        return value();
    }
}