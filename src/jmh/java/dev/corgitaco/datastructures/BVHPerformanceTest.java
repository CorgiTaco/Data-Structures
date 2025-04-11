package dev.corgitaco.datastructures;


import dev.corgitaco.datastructures.bvh.BVH;
import dev.corgitaco.datastructures.bvh.BVH2D;
import org.openjdk.jmh.annotations.*;

import java.util.Random;

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 2, time = 5)
@BenchmarkMode(Mode.All)
public class BVHPerformanceTest {

    @Param({ "1000", "2000", "5000", "10000", "25000", "50000", "100000", "250000", "500000" })
    public int entries;
    BVH2D<Box> boxBVH2D = BVH2D.floodedBVH(42, entries, Integer.MAX_VALUE, 500, 1000, true);


    @Benchmark
    public void entriesNoChecks() {
        BVH2D<Box> boxBVH2D = BVH2D.floodedBVH(42, entries, Integer.MAX_VALUE, 500, 1000, true);
    }

    @Benchmark
    public void entriesWithChecks() {
        BVH2D<Box> boxBVH2D = BVH2D.floodedBVH(42, entries, Integer.MAX_VALUE, 500, 1000, false);
    }

    @Benchmark
    public void getAllEntries() {
        boxBVH2D.query(null, entry -> false);
    }
}
