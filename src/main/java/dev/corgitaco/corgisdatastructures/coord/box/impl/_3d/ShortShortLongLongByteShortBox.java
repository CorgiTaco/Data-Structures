       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ShortLongLongShortBox;


       public class ShortShortLongLongByteShortBox implements Box {

           private final short minX;
           private final short minY;
           private final long minZ;
           private final long maxX;
           private final byte maxY;
           private final short maxZ;

           public ShortShortLongLongByteShortBox(short minX, short minY, long minZ, long maxX, byte maxY, short maxZ) {
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
               return new ShortShortLongLongByteShortBox((short) minX, (short) minY, (long) minZ, (long) maxX, (byte) maxY, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortLongLongShortBox((short) minX, (long) minZ, (long) maxX, (short) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortShortLongLongByteShortBox((short) minX, (short) minY, (long) minZ, (long) maxX, (byte) maxY, (short) maxZ);
           }
       }
