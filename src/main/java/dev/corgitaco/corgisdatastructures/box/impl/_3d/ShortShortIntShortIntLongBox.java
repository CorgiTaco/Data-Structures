       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.ShortIntShortLongBox;


       public class ShortShortIntShortIntLongBox implements Box {

           private final short minX;
           private final short minY;
           private final int minZ;
           private final short maxX;
           private final int maxY;
           private final long maxZ;

           public ShortShortIntShortIntLongBox(short minX, short minY, int minZ, short maxX, int maxY, long maxZ) {
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
               return new ShortShortIntShortIntLongBox((short) minX, (short) minY, (int) minZ, (short) maxX, (int) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortIntShortLongBox((short) minX, (int) minZ, (short) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortShortIntShortIntLongBox((short) minX, (short) minY, (int) minZ, (short) maxX, (int) maxY, (long) maxZ);
           }
       }
