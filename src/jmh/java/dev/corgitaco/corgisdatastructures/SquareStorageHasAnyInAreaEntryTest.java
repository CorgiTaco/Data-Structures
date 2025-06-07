package dev.corgitaco.corgisdatastructures;


import dev.corgitaco.corgisdatastructures.coord.box.Box;
import dev.corgitaco.corgisdatastructures.coord.box.BoxFactory;
import dev.corgitaco.corgisdatastructures.coord.box.Util;
import dev.corgitaco.corgisdatastructures.coord.position.SimplePosition2D;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import dev.corgitaco.corgisdatastructures.datastructure.position.nearest.QuadTreeNearestPosition;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 2, time = 5)
@BenchmarkMode(Mode.Throughput)
public class SquareStorageHasAnyInAreaEntryTest {
    private static boolean results = false;

    private Box searchArea;


    @Param({"5", "25", "100", "50000", "100000", "500000"})
    public int entries;

    public Long2IntMap L2I_STORAGE;
    public QuadTreeNearestPosition<SimplePosition2D> QUAD_TREE_NEAREST_POSITION;
    public BVH2D<Box> BVH_2D;

    @Setup
    public void setup() {
        int side = (int) Math.ceil(Math.sqrt(entries));

        L2I_STORAGE = Util.make(Long2IntOpenHashMap::new, map -> {
            System.out.println("Creating Long2IntMap with " + entries + " entries");
            for (int x = 0; x < side; x++) {
                for (int y = 0; y < side; y++) {
                    map.put(pack(x, y), 1);
                }
            }
        });

        QUAD_TREE_NEAREST_POSITION = Util.make(QuadTreeNearestPosition::new, quadTree -> {
            System.out.println("Creating QuadTree with " + entries + " entries");
            for (int x = 0; x < side; x++) {
                for (int y = 0; y < side; y++) {
                    SimplePosition2D position = new SimplePosition2D(x, y);
                    quadTree.setPosition(position);
                }
            }
        });

        BVH_2D = Util.make(BVH2D::new, bvh -> {
            System.out.println("Creating BVH with " + entries + " entries");
            for (int x = 0; x < side; x++) {
                for (int y = 0; y < side; y++) {
                    int x0 = x * 32;
                    int y0 = y * 32;
                    Box simpleBox2D = BoxFactory.createBox2D(true, x0, y0, x0 + 32, y0 + 32);
                    bvh.insert(simpleBox2D, simpleBox2D);
                }
            }
        });

        searchArea = BoxFactory.createBox2D(true, 0, 0, (double) entries / 4, (double) entries / 4);
    }

    @Benchmark
    public void bvhContains() {
        boolean b = BVH_2D.hasAny(searchArea);
        if (b) {
            results = true;
        }
    }

    @Benchmark
    public void long2ObjMapContains() {
        int count = 0;
        for (int x = (int) searchArea.minX(); x < searchArea.maxX(); x++) {
            for (int y = (int) searchArea.minZ(); y < searchArea.maxZ(); y++) {
                long key = pack(x, y);
                if (L2I_STORAGE.containsKey(key)) {
                    count++;
                    results = true;
                }
            }
        }
        if (count > 0) {
            results = false;
        }
    }

    @Benchmark
    public void nearestPointContains() {
        boolean b = QUAD_TREE_NEAREST_POSITION.hasTargetsInBox(searchArea);
        if (b) {
            results = true;
        }
    }

    static long pack(int x, int z) {
        return (long) x & 0xffffffffL | ((long) z & 0xffffffffL) << 32;
    }
}
