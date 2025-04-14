package dev.corgitaco.corgisdatastructures.aabb;


import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.list.ListBackedAABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.map.LongMapBackedAABBQuery2D;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 2, time = 5)
@BenchmarkMode(Mode.Throughput)
public class AABBQueryInsertionPerformanceTest {

    @Param({ "1000", "2000", "5000", "10000", "25000", "50000", "100000", "250000", "500000" })
    public int entries;

    @Benchmark
    public void bvhEntriesNoChecks() {
        BVH2D<Box> boxBVH2D = AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 32, entries, 1000000, 1, 5000, true);
    }

    @Benchmark
    public void bvhEntriesWithChecks() {
        BVH2D<Box> boxBVH2D = AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 32, entries, 1000000, 1, 5000, false);
    }

    @Benchmark
    public void listEntriesNoChecks() {
        AABBQuery<Box> boxList = AABBQuery.floodAABBQueryBoxes2D(new ListBackedAABBQuery<>(), 32, entries, 1000000, 1, 5000, true);
    }

    @Benchmark
    public void listEntriesChecks() {
        AABBQuery<Box> boxList = AABBQuery.floodAABBQueryBoxes2D(new ListBackedAABBQuery<>(), 32, entries, 1000000, 1, 5000, false);
    }

    @Benchmark
    public void longMapEntriesNoChecks() {
        AABBQuery<Box> boxList = AABBQuery.floodAABBQueryBoxes2D(new LongMapBackedAABBQuery2D<>(), 32, entries, 1000000, 1, 5000, true);
    }

    @Benchmark
    public void longMapEntriesWithChecks() {
        AABBQuery<Box> boxList = AABBQuery.floodAABBQueryBoxes2D(new ListBackedAABBQuery<>(), 32, entries, 1000000, 1, 5000, false);
    }
}
