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
        map.put("list", i -> AABBQuery.floodAABBQueryBoxes2D(new ListBackedAABBQuery<>(), 42, i, Integer.MAX_VALUE, 1, 5000000, true));
        map.put("bvh", i -> AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 42, i, Integer.MAX_VALUE, 1, 5000000, true));
        map.put("longMap", i -> AABBQuery.floodAABBQueryBoxes2D(new LongMapBackedAABBQuery2D<>(), 42, i, Integer.MAX_VALUE, 1, 5000000, true));
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

    private @NotNull SimpleBox2D randomBox() {
        int maxBoxSize = 5000000;
        int minBoxSize = maxBoxSize / 3;

        int size = Integer.MAX_VALUE - (maxBoxSize + minBoxSize);
        double minX = random.nextDouble() * size - size / 2.0;
        double minY = random.nextDouble() * size - size / 2.0;
        double boxWidth = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
        double boxHeight = minBoxSize + random.nextDouble() * (maxBoxSize - minBoxSize);
        double maxX = minX + boxWidth;
        double maxY = minY + boxHeight;

        return new SimpleBox2D(minX, minY, maxX, maxY);
    }
}
