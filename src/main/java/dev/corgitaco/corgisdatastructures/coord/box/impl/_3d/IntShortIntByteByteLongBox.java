       package dev.corgitaco.corgisdatastructures.coord.box.impl._3d;

       import dev.corgitaco.corgisdatastructures.coord.box.Box;
       import dev.corgitaco.corgisdatastructures.coord.box.impl._2d.IntIntByteLongBox;


       public class IntShortIntByteByteLongBox implements Box {

           private final int minX;
           private final short minY;
           private final int minZ;
           private final byte maxX;
           private final byte maxY;
           private final long maxZ;

           public IntShortIntByteByteLongBox(int minX, short minY, int minZ, byte maxX, byte maxY, long maxZ) {
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
               return new IntShortIntByteByteLongBox((int) minX, (short) minY, (int) minZ, (byte) maxX, (byte) maxY, (long) maxZ);
           }

           @Override
           public Box as2D() {
               return new IntIntByteLongBox((int) minX, (int) minZ, (byte) maxX, (long) maxZ);
           }

           public static Box createStatically(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
               return new IntShortIntByteByteLongBox((int) minX, (short) minY, (int) minZ, (byte) maxX, (byte) maxY, (long) maxZ);
           }
       }
