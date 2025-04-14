       package dev.corgitaco.corgisdatastructures.box.impl._2d;

       import dev.corgitaco.corgisdatastructures.box.Box;

       public class LongByteShortLongBox implements Box {

           private final long minX;
           private final byte minZ;
           private final short maxX;
           private final long maxZ;

           public LongByteShortLongBox(long minX, byte minZ, short maxX, long maxZ) {
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
               return new LongByteShortLongBox((long) minX, (byte) minZ, (short) maxX, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return this;
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongByteShortLongBox((long) minX, (byte) minZ, (short) maxX, (long) maxZ);
           }
       }
