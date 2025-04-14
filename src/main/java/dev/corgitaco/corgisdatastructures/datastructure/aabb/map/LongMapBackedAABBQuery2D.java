package dev.corgitaco.corgisdatastructures.datastructure.aabb.map;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class LongMapBackedAABBQuery2D<VALUE> implements AABBQuery<VALUE> {
    private final Long2ObjectMap<IntList> map = new Long2ObjectOpenHashMap<>();
    private final Int2ObjectOpenHashMap<Entry<VALUE>> entryLookup = new Int2ObjectOpenHashMap<>();
    private final int bitShift;
    private Box bound;
    private int entryCounter = 0;

    public LongMapBackedAABBQuery2D() {
        this(4);
    }

    public LongMapBackedAABBQuery2D(int bitShift) {
        this.bitShift = bitShift;
    }


    @Override
    public void insertDirect(Box bound, VALUE value) {
        if (this.bound == null) {
            this.bound = bound;
        } else {
            this.bound = this.bound.encapsulate(bound);
        }
        double minX = bound.minX();
        double minY = bound.minY();
        double minZ = bound.minZ();
        double maxX = bound.maxX();
        double maxY = bound.maxY();
        double maxZ = bound.maxZ();
        int minChunkX = toChunk(minX);
        int minChunkZ = toChunk(minZ);
        int maxChunkX = toChunk(maxX);
        int maxChunkZ = toChunk(maxZ);
        int entryId = entryCounter++;
        for (int x = minChunkX; x <= maxChunkX; x++) {
            for (int z = minChunkZ; z <= maxChunkZ; z++) {
                long key = pack(x, z);
                IntList entries = map.computeIfAbsent(key, k -> new IntArrayList());
                Entry<VALUE> entry = createEntry(bound, value);
                entries.add(entryCounter);
                entryLookup.put(entryCounter, entry);
            }
        }
    }


    int toChunk(double coord) {
        return ((int) coord) >> this.bitShift;
    }

    int toWorld(double coord) {
        return ((int) coord) << this.bitShift;
    }

    long pack(int x, int z) {
        return (long) x & 0xffffffffL | ((long) z & 0xffffffffL) << 32;
    }


    @Override
    public boolean removeValue(@Nullable Box bound, VALUE value, OverlapFunction overlapFunction, BiPredicate<VALUE, VALUE> test) {
        if (bound != null) {
            double minX = bound.minX();
            double minY = bound.minY();
            double minZ = bound.minZ();
            double maxX = bound.maxX();
            double maxY = bound.maxY();
            double maxZ = bound.maxZ();
            int minChunkX = toChunk(minX);
            int minChunkZ = toChunk(minZ);
            int maxChunkX = toChunk(maxX);
            int maxChunkZ = toChunk(maxZ);
            for (int x = minChunkX; x <= maxChunkX; x++) {
                for (int z = minChunkZ; z <= maxChunkZ; z++) {
                    long key = pack(x, z);
                    IntList entries = map.get(key);
                    if (entries != null) {
                        entries.removeIf(i -> {
                            Entry<VALUE> e = entryLookup.get(i);
                            return overlapFunction.test(bound, e.bound()) && test.test(e.value(), value);
                        });
                    }
                }
            }
        } else {
            for (IntList entries : map.values()) {
                entries.removeIf(i -> {
                    Entry<VALUE> e = entryLookup.get(i);
                    return test.test(e.value(), value);
                });
            }
        }
        return false;
    }

    @Override
    public boolean removeArea(Box bound, OverlapFunction overlapFunction) {
        if (this.bound == null) {
            return false;
        }

        if (!this.bound.intersects(bound)) {
            return false;
        }
        double minX = bound.minX();
        double minY = bound.minY();
        double minZ = bound.minZ();
        double maxX = bound.maxX();
        double maxY = bound.maxY();
        double maxZ = bound.maxZ();
        int minChunkX = toChunk(minX);
        int minChunkZ = toChunk(minZ);
        int maxChunkX = toChunk(maxX);
        int maxChunkZ = toChunk(maxZ);
        for (int x = minChunkX; x <= maxChunkX; x++) {
            for (int z = minChunkZ; z <= maxChunkZ; z++) {
                long key = pack(x, z);
                IntList entries = map.get(key);
                if (entries != null) {
                    entries.removeIf(i -> {
                        Entry<VALUE> e = entryLookup.get(i);
                        return overlapFunction.test(bound, e.bound());
                    });
                }
            }
        }
        return false;
    }

    @Override
    public boolean query(@Nullable Box bound, OverlapFunction overlapFunction, Predicate<Entry<VALUE>> test) {
        if (this.bound == null) {
            return false;
        }
        if (bound != null && !this.bound.intersects(bound)) {
            return false;
        }
        if (bound != null) {
            double minX = bound.minX();
            double minZ = bound.minZ();
            double maxX = bound.maxX();
            double maxZ = bound.maxZ();
            int minChunkX = toChunk(minX);
            int minChunkZ = toChunk(minZ);
            int maxChunkX = toChunk(maxX);
            int maxChunkZ = toChunk(maxZ);
            for (int x = minChunkX; x <= maxChunkX; x++) {
                for (int z = minChunkZ; z <= maxChunkZ; z++) {
                    long key = pack(x, z);
                    IntList entries = map.get(key);
                    if (entries != null) {
                        for (int i : entries) {
                            Entry<VALUE> entry = entryLookup.get(i);
                            if (overlapFunction.test(bound, entry.bound()) && test.test(entry)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            for (IntList entries : map.values()) {
                for (int i : entries) {
                    Entry<VALUE> entry = entryLookup.get(i);
                    if (test.test(entry)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void nodeView(Box bound, BiFunction<Box, Box, Boolean> overlapFunction, int maxDepth, BiConsumer<Integer, Box> consumer) {

    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Box bound() {
        return this.bound;
    }
}
