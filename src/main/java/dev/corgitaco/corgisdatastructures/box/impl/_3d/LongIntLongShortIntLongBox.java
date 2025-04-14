       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.LongLongShortLongBox;


       public class LongIntLongShortIntLongBox implements Box {

           private final long minX;
           private final int minY;
           private final long minZ;
           private final short maxX;
           private final int maxY;
           private final long maxZ;

           public LongIntLongShortIntLongBox(long minX, int minY, long minZ, short maxX, int maxY, long maxZ) {
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
               return new LongIntLongShortIntLongBox((long) minX, (int) minY, (long) minZ, (short) maxX, (int) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongLongShortLongBox((long) minX, (long) minZ, (short) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongIntLongShortIntLongBox((long) minX, (int) minY, (long) minZ, (short) maxX, (int) maxY, (long) maxZ);
           }
       }
