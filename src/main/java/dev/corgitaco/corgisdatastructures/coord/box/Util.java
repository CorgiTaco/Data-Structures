package dev.corgitaco.corgisdatastructures.coord.box;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Util {

    public static <T> T make(Supplier<T> supplier, Consumer<T> consumer) {
        T instance = supplier.get();
        consumer.accept(instance);
        return instance;
    }
}
