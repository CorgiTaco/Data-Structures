       package dev.corgitaco.corgisdatastructures.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.box.Box;
       import dev.corgitaco.corgisdatastructures.box.impl._2d.LongLongByteIntBox;


       public class LongShortLongByteIntIntBox implements Box {

           private final long minX;
           private final short minY;
           private final long minZ;
           private final byte maxX;
           private final int maxY;
           private final int maxZ;

           public LongShortLongByteIntIntBox(long minX, short minY, long minZ, byte maxX, int maxY, int maxZ) {
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
               return new LongShortLongByteIntIntBox((long) minX, (short) minY, (long) minZ, (byte) maxX, (int) maxY, (int) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongLongByteIntBox((long) minX, (long) minZ, (byte) maxX, (int) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongShortLongByteIntIntBox((long) minX, (short) minY, (long) minZ, (byte) maxX, (int) maxY, (int) maxZ);
           }
       }
