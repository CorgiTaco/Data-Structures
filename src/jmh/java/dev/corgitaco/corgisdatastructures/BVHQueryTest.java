package dev.corgitaco.corgisdatastructures;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.box.SimpleBox2D;
import dev.corgitaco.corgisdatastructures.datastructure.bvh.BVH2D;
import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.*;

import java.util.Random;


@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 2, time = 5)
@BenchmarkMode(Mode.All)
public class BVHQueryTest {

    @Param({ "25", "50", "100", "250", "500", "1000", "2500", "5000", "10000", "25000", "50000", "100000", "250000", "500000", "1000000", "2500000", "5000000", "10000000", "25000000", "50000000" })
    public int entries;
    BVH2D<Box> boxBVH2D = BVH2D.floodedBVH(42, entries, Integer.MAX_VALUE, 1, 5000000, true);
    Random random = new Random(42);

    @Benchmark
    public void getAllEntries() {
        boxBVH2D.query(null, entry -> false);
    }

    @Benchmark
    public void queryRandomAreas() {
        SimpleBox2D bound = randomBox();
        boxBVH2D.query(bound, entry -> false);
    }

    @Benchmark
    public void hasAnyAtPosition() {
        SimpleBox2D bound = randomBox();
        boxBVH2D.hasAny(bound);
    }

    private @NotNull SimpleBox2D randomBox() {
        int minBoxSize = 1;
        int maxBoxSize = 5000000;
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
