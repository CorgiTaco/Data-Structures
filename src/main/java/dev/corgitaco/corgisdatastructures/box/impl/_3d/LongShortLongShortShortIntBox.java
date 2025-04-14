       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.LongLongShortIntBox;


       public class LongShortLongShortShortIntBox implements Box {

           private final long minX;
           private final short minY;
           private final long minZ;
           private final short maxX;
           private final short maxY;
           private final int maxZ;

           public LongShortLongShortShortIntBox(long minX, short minY, long minZ, short maxX, short maxY, int maxZ) {
               this.minX = minX;
               this.minY = minY;
               this.minZ = minZ;
               this.maxX = maxX;
               this.maxY = maxY;
               this.maxZ = maxZ;
           }

           @Override
           public double minX() {
               return this.minX;
           }

           @Override
           public double minY() {
               return this.minY;
           }

           @Override
           public double minZ() {
               return this.minZ;
           }

           @Override
           public double maxX() {
               return this.maxX;
           }

           @Override
           public double maxY() {
               return this.maxY;
           }

           @Override
           public double maxZ() {
               return this.maxZ;
           }

           @Override
           public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongShortLongShortShortIntBox((long) minX, (short) minY, (long) minZ, (short) maxX, (short) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongLongShortIntBox((long) minX, (long) minZ, (short) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongShortLongShortShortIntBox((long) minX, (short) minY, (long) minZ, (short) maxX, (short) maxY, (int) maxZ);
           }
       }
