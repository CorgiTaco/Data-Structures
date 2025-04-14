       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.ShortLongLongLongBox;


       public class ShortIntLongLongShortLongBox implements Box {

           private final short minX;
           private final int minY;
           private final long minZ;
           private final long maxX;
           private final short maxY;
           private final long maxZ;

           public ShortIntLongLongShortLongBox(short minX, int minY, long minZ, long maxX, short maxY, long maxZ) {
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
               return new ShortIntLongLongShortLongBox((short) minX, (int) minY, (long) minZ, (long) maxX, (short) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortLongLongLongBox((short) minX, (long) minZ, (long) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortIntLongLongShortLongBox((short) minX, (int) minY, (long) minZ, (long) maxX, (short) maxY, (long) maxZ);
           }
       }
