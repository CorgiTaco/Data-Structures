package dev.corgitaco.corgisdatastructures.aabb;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.box.SimpleBox2D;
import dev.corgitaco.corgisdatastructures.box.Util;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.list.ListBackedAABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.map.LongMapBackedAABBQuery2D;
import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.IntFunction;


@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 2, time = 5)
@BenchmarkMode(Mode.Throughput)
public class AABBQueryTest {

    private static final Map<String, IntFunction<AABBQuery<Box>>> AABB_QUERY_MAP = Util.make(HashMap::new, map -> {
        map.put("list", i -> AABBQuery.floodAABBQueryBoxes2D(new ListBackedAABBQuery<>(), 42, i, 1000000, 1, 50000, true));
        map.put("bvh", i -> AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 42, i, 1000000, 1, 5000, true));
        map.put("longMap", i -> AABBQuery.floodAABBQueryBoxes2D(new LongMapBackedAABBQuery2D<>(), 42, i, 1000000, 1, 50000, true));
    });

    @Param(
            {
                    "1", "5", "10", "25", "50", "100", "250", "500", "1000", "2500", "5000", "10000", "25000", "50000", "100000", "250000", "500000", "1000000", "2500000", "5000000",
                    "1", "5", "10", "25", "50", "100", "250", "500", "1000", "2500", "5000", "10000", "25000", "50000", "100000", "250000", "500000", "1000000", "2500000", "5000000",
                    "1", "5", "10", "25", "50", "100", "250", "500", "1000", "2500", "5000", "10000", "25000", "50000", "100000", "250000", "500000", "1000000", "2500000", "5000000",

            })
    public int entries;

    @Param({
            "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list", "list",
            "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh", "bvh",
            "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap", "longMap",
    })
    public String queryType;

    AABBQuery<Box> aabbQuery = AABB_QUERY_MAP.get(queryType).apply(entries);
    Random random = new Random(42);

    @Benchmark
    public void getAllEntries() {
        aabbQuery.query(null, entry -> false);
    }

    @Benchmark
    public void query1000RandomAreas() {
        for (int i = 0; i < 1000; i++) {
            SimpleBox2D bound = randomBox();
            aabbQuery.query(bound, entry -> false);
        }
    }

    @Benchmark
    public void hasAnyIn1000Boxes() {
        for (int i = 0; i < 1000; i++) {
            SimpleBox2D bound = randomBox();
            aabbQuery.hasAny(bound);
        }
    }

    @Benchmark
    public void remove1000RandomAreas() {
        int removedAreas = 0;
        for (int i = 0; i < 1000; i++) {
            SimpleBox2D bound = randomBox();
            if(aabbQuery.removeArea(bound)) {
                removedAreas++;
            }
        }
        System.out.println("Removed areas: " + removedAreas);
    }

    private @NotNull SimpleBox2D randomBox() {
        Box aabbQueryBounds = this.aabbQuery.bound();
        if (aabbQueryBounds == null) {
            return new SimpleBox2D(0, 0, 0, 0);
        }

        double minX = aabbQueryBounds.minX() + random.nextDouble() * (aabbQueryBounds.maxX() - aabbQueryBounds.minX());
        double minZ = aabbQueryBounds.minZ() + random.nextDouble() * (aabbQueryBounds.maxZ() - aabbQueryBounds.minZ());
        double boxWidthX = random.nextDouble() * (aabbQueryBounds.maxX() - minX);
        double boxWidthZ = random.nextDouble() * (aabbQueryBounds.maxZ() - minZ);
        double maxX = minX + boxWidthX;
        double maxZ = minZ + boxWidthZ;

        return new SimpleBox2D(minX, maxZ, maxX, maxZ);
    }
}
