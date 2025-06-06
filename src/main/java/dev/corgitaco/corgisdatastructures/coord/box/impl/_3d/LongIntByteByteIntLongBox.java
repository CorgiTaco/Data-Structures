       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.LongByteByteLongBox;


       public class LongIntByteByteIntLongBox implements Box {

           private final long minX;
           private final int minY;
           private final byte minZ;
           private final byte maxX;
           private final int maxY;
           private final long maxZ;

           public LongIntByteByteIntLongBox(long minX, int minY, byte minZ, byte maxX, int maxY, long maxZ) {
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
               return new LongIntByteByteIntLongBox((long) minX, (int) minY, (byte) minZ, (byte) maxX, (int) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new LongByteByteLongBox((long) minX, (byte) minZ, (byte) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new LongIntByteByteIntLongBox((long) minX, (int) minY, (byte) minZ, (byte) maxX, (int) maxY, (long) maxZ);
           }
       }
