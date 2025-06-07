//package dev.corgitaco.corgisdatastructures;
//
//
//import it.unimi.dsi.fastutil.longs.Long2IntMap;
//import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
//import org.openjdk.jmh.annotations.*;
//
//@State(Scope.Benchmark)
//@Fork(1)
//@Warmup(iterations = 2, time = 5)
//@Measurement(iterations = 2, time = 5)
//@BenchmarkMode(Mode.Throughput)
//public class SquareStorageInsertTest {
//
//    @Param({"5", "25", "100", "50000", "100000", "500000"})
//    public int entries;
//
////    @Benchmark
////    public void bvhCreation() {
////        BVH2D<Box> bvh2D = new BVH2D<>();
////
////
////
////        int side = (int) Math.ceil(Math.sqrt(entries));
////        int inserted = 0;
////        for (int x = 0; x < side && inserted < entries; x++) {
////            for (int y = 0; y < side && inserted < entries; y++) {
////                int x0 = x * 32;
////                int y0 = y * 32;
////                Box simpleBox2D = BoxFactory.createBox2D(true, x0, y0, x0 + 32, y0 + 32);
////                bvh2D.insert(simpleBox2D, simpleBox2D);
////                inserted++;
////            }
////        }
////    }
//
//    @Benchmark
//    public void long2ObjMap() {
//        Long2IntMap long2IntMap = new Long2IntOpenHashMap();
//
//        int side = (int) Math.ceil(Math.sqrt(entries));
//
//
//        for (int x = 0; x < side; x++) {
//            for (int y = 0; y < side; y++) {
//                long2IntMap.put(pack(x, y), 1);
//            }
//        }
//        String s = long2IntMap.toString();
//    }
//
////    @Benchmark
////    public void nearestPoint() {
////        QuadTreeNearestPosition<SimplePosition2D> nearestPosition = new QuadTreeNearestPosition<>();
////
////        int side = (int) Math.ceil(Math.sqrt(entries));
////
////
////        for (int x = 0; x < side; x++) {
////            for (int y = 0; y < side; y++) {
////                SimplePosition2D position = new SimplePosition2D(x, y);
////                nearestPosition.setPosition(position);
////            }
////        }
////    }
//
//    long pack(int x, int z) {
//        return (long) x & 0xffffffffL | ((long) z & 0xffffffffL) << 32;
//    }
//}
