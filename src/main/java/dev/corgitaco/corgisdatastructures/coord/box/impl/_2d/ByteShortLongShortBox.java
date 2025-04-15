       package dev.corgitaco.corgisdatastructures.coord.box.impl._2d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;

       public class ByteShortLongShortBox implements Box {

           private final byte minX;
           private final short minZ;
           private final long maxX;
           private final short maxZ;

           public ByteShortLongShortBox(byte minX, short minZ, long maxX, short maxZ) {
               this.minX = minX;
               this.minZ = minZ;
               this.maxX = maxX;
               this.maxZ = maxZ;
           }

           @Override
           public double minX() {
               return this.minX;
           }

           @Override
           public double minY() {
               return 0;
           }

           @Override
           public double minZ() {
               return this.minZ;
           }

           @Override
           public double maxY() {
               return 0;
           }

           @Override
           public double maxX() {
               return this.maxX;
           }

           @Override
           public double maxZ() {
               return this.maxZ;
           }

           @Override
           public Box create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ByteShortLongShortBox((byte) minX, (short) minZ, (long) maxX, (short) maxZ);
           }

           @Override
           public Box as2D() {
               return this;
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ByteShortLongShortBox((byte) minX, (short) minZ, (long) maxX, (short) maxZ);
           }
       }
