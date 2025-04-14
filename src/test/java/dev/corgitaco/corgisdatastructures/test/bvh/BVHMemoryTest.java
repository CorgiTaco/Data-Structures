package dev.corgitaco.corgisdatastructures.test.bvh;

import dev.corgitaco.corgisdatastructures.box.Box;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.AABBQuery;
import dev.corgitaco.corgisdatastructures.datastructure.aabb.bvh.BVH2D;
import org.junit.jupiter.api.Test;

public class BVHMemoryTest {


    @Test
    public void testMemorySmartBoxes() {
        int minBoxSize = 1;
        int maxBoxSize = 5000000;
        int size = Integer.MAX_VALUE - (maxBoxSize + minBoxSize);
        int numberOfBoxes = Integer.MAX_VALUE;
        long[] startTime = {System.nanoTime()};
        BVH2D<Box> bvh2D = AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 32, numberOfBoxes, size, minBoxSize, maxBoxSize, true, false, inserted -> {
            if (inserted % 100000 == 0) {
                long nanoTime = System.nanoTime();
                double elapsedTime = (nanoTime - startTime[0]) / 1_000_000.0;
                System.out.printf("Total smart boxes inserted so far = %d, used memory = %dMB/%dMB. Took %.3fms inserting 100k boxes%n", inserted, usedMemory(), totalMemory(), elapsedTime);
                startTime[0] = nanoTime;
            }
            if (totalMemory() - usedMemory() < 1) {
                System.out.println("Total boxes inserted = " + inserted);
                return true;
            }
            return false;
        });
    }

    @Test
    public void testMemorySimpleBoxes() {
        int minBoxSize = 1;
        int maxBoxSize = 5000000;
        int size = Integer.MAX_VALUE - (maxBoxSize + minBoxSize);
        int numberOfBoxes = Integer.MAX_VALUE;
        long[] startTime = {System.nanoTime()};
        BVH2D<Box> bvh2D = AABBQuery.floodAABBQueryBoxes2D(new BVH2D<>(), 32, numberOfBoxes, size, minBoxSize, maxBoxSize, true, true, inserted -> {
            if (inserted % 100000 == 0) {
                long nanoTime = System.nanoTime();
                double elapsedTime = (nanoTime - startTime[0]) / 1_000_000.0;
                System.out.printf("Total simple boxes inserted so far = %d, used memory = %dMB/%dMB. Took %.3fms inserting 100k boxes%n", inserted, usedMemory(), totalMemory(), elapsedTime);
                startTime[0] = nanoTime;
            }
            if (totalMemory() - usedMemory() < 1) {
                System.out.println("Total boxes inserted = " + inserted);
                return true;
            }
            return false;
        });
    }

    private static long usedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
    }

    private static long totalMemory() {
        return Runtime.getRuntime().totalMemory() / (1024 * 1024);
    }
}
