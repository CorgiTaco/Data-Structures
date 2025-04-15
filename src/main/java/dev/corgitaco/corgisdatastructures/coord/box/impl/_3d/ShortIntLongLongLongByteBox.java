       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.ShortLongLongByteBox;


       public class ShortIntLongLongLongByteBox implements Box {

           private final short minX;
           private final int minY;
           private final long minZ;
           private final long maxX;
           private final long maxY;
           private final byte maxZ;

           public ShortIntLongLongLongByteBox(short minX, int minY, long minZ, long maxX, long maxY, byte maxZ) {
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
               return new ShortIntLongLongLongByteBox((short) minX, (int) minY, (long) minZ, (long) maxX, (long) maxY, (byte) maxZ);
           }

           @Override
           public Box as2D() {
               return new ShortLongLongByteBox((short) minX, (long) minZ, (long) maxX, (byte) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new ShortIntLongLongLongByteBox((short) minX, (int) minY, (long) minZ, (long) maxX, (long) maxY, (byte) maxZ);
           }
       }
