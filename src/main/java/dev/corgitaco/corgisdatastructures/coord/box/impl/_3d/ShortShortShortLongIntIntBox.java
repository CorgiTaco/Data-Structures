       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ShortShortLongIntBox;


       public class ShortShortShortLongIntIntBox implements Box {

           private final short minX;
           private final short minY;
           private final short minZ;
           private final long maxX;
           private final int maxY;
           private final int maxZ;

           public ShortShortShortLongIntIntBox(short minX, short minY, short minZ, long maxX, int maxY, int maxZ) {
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
               return new ShortShortShortLongIntIntBox((short) minX, (short) minY, (short) minZ, (long) maxX, (int) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortShortLongIntBox((short) minX, (short) minZ, (long) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortShortShortLongIntIntBox((short) minX, (short) minY, (short) minZ, (long) maxX, (int) maxY, (int) maxZ);
           }
       }
